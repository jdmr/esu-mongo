/*
 * The MIT License
 *
 * Copyright 2012 Universidad de Montemorelos A. C.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package mx.edu.um.esu.general.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mx.edu.um.esu.general.model.Articulo;
import mx.edu.um.esu.general.model.Carpeta;
import mx.edu.um.esu.general.model.Etiqueta;
import mx.edu.um.esu.general.model.Usuario;
import mx.edu.um.esu.general.utils.ArticuloNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@Repository
public class ArticuloDao {

    private static final Logger log = LoggerFactory.getLogger(ArticuloDao.class);
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CarpetaDao carpetaDao;
    @Autowired
    private EtiquetaDao etiquetaDao;

    public List<Articulo> lista() {
        log.debug("Lista de articulos");
        List<Articulo> articulos = mongoTemplate.findAll(Articulo.class);
        return articulos;
    }

    public Map<String, Object> lista(Map<String, Object> params) {
        int max;
        int offset = 0;
        if (!params.containsKey("max")) {
            max = 10;
            params.put("max", max);
        } else {
            max = (Integer) params.get("max");
        }

        if (params.containsKey("pagina")) {
            Integer pagina = (Integer) params.get("pagina");
            offset = (pagina - 1) * (Integer) params.get("max");
            params.put("offset", offset);
        }

        if (!params.containsKey("offset")) {
            params.put("offset", 0);
            offset = 0;
        }
        Query query = new Query();
        if (params.containsKey("filtro")) {
            String filtro = (String) params.get("filtro");

            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("nombre").regex(filtro, "i"), Criteria.where("descripcion").regex(filtro, "i"), Criteria.where("contenido").regex(filtro, "i"));
            query.addCriteria(criteria);
            params.put("cantidad", new Long(mongoTemplate.count(query, Articulo.class)).intValue());
        } else {
            params.put("cantidad", new Long(mongoTemplate.count(null, Articulo.class)).intValue());
        }
        query.skip(offset);
        query.limit(max);
        List<Articulo> articulos = mongoTemplate.find(query, Articulo.class);
        params.put("articulos", articulos);
        return params;
    }

    public void reiniciaColeccion() {
        if (mongoTemplate.collectionExists(Articulo.class)) {
            mongoTemplate.dropCollection(Articulo.class);
        }
    }

    public Articulo crea(Articulo articulo) {
        return this.crea(articulo, Boolean.FALSE);
    }

    public Articulo crea(Articulo articulo, Boolean esMigracion) {
        if (articulo.getAutor() != null) {
            for (Carpeta carpeta : articulo.getUbicaciones()) {
                carpeta.setNombre(carpeta.getNombre().toLowerCase());
                carpetaDao.crea(carpeta);
            }
            for (Etiqueta etiqueta : articulo.getEtiquetas()) {
                etiqueta.setNombre(etiqueta.getNombre().toLowerCase());
                etiquetaDao.crea(etiqueta);
            }
            articulo.setId(UUID.randomUUID().toString());
            if (!esMigracion) {
                Date fecha = new Date();
                articulo.setFechaCreacion(fecha);
                articulo.setFechaModificacion(fecha);
            }
            mongoTemplate.insert(articulo);
            Usuario usuario = articulo.getAutor();
            Update u = new Update();
            u.inc("publicaciones", 1);
            mongoTemplate.findAndModify(new Query(Criteria.where("username").is(usuario.getUsername())), u, Usuario.class);
            return articulo;
        } else {
            return null;
        }
    }

    public Articulo actualiza(Articulo articulo) {
        Articulo nuevo = obtiene(articulo.getId());
        nuevo.setContenido(articulo.getContenido());
        nuevo.setDescripcion(articulo.getDescripcion());
        nuevo.setEstatus(articulo.getEstatus());
        nuevo.setEtiquetas(articulo.getEtiquetas());
        nuevo.setNombre(articulo.getNombre());
        nuevo.setUbicaciones(articulo.getUbicaciones());
        nuevo.setFechaModificacion(new Date());
        nuevo.setAutor(articulo.getAutor());
        nuevo.setEditor(articulo.getEditor());
        mongoTemplate.save(nuevo);
        return articulo;
    }

    public Articulo obtiene(String id) {
        Articulo articulo = mongoTemplate.findById(id, Articulo.class);
        return articulo;
    }

    public void elimina(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Articulo.class);
    }

    public List<Articulo> buscaPorCarpetas(List<String> carpetas) throws ArticuloNoEncontradoException {
        Query query = new Query(Criteria.where("ubicaciones.$id").all(carpetas));
        List<Articulo> articulos = mongoTemplate.find(query, Articulo.class);
        if (articulos != null) {
            return articulos;
        } else {
            throw new ArticuloNoEncontradoException("No se encontraron articulos con carpetas " + carpetas);
        }
    }

    public Boolean existe(List<String> carpetas) {
        boolean resultado = false;
        Query query = new Query(Criteria.where("ubicaciones.$id").all(carpetas));
        List<Articulo> articulos = mongoTemplate.find(query, Articulo.class);
        if (articulos != null && articulos.size() > 0) {
            resultado = true;
        }
        return resultado;
    }

    public Boolean existePorCarpetas(List<Carpeta> carpetas) {
        boolean resultado = false;
        List<String> etiquetas = new ArrayList<>();
        for (Carpeta carpeta : carpetas) {
            etiquetas.add(carpeta.getNombre());
        }

        Query query = new Query(Criteria.where("ubicaciones.$id").all(etiquetas));
        List<Articulo> articulos = mongoTemplate.find(query, Articulo.class);
        if (articulos != null && articulos.size() > 0) {
            resultado = true;
        }
        log.debug("Se encontro el articulo: {}", resultado);
        return resultado;
    }

    public Articulo ver(List<String> carpetas) throws ArticuloNoEncontradoException {
        Query query = new Query(Criteria.where("ubicaciones.$id").all(carpetas));
        Update u = new Update();
        u.inc("vistas", 1);
        Articulo articulo = mongoTemplate.findAndModify(query, u, Articulo.class);
        if (articulo == null) {
            throw new ArticuloNoEncontradoException("No se encontro el articulo " + carpetas);
        }
        return articulo;
    }
}
