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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mx.edu.um.esu.general.model.Carpeta;
import mx.edu.um.esu.general.model.Etiqueta;
import mx.edu.um.esu.general.model.Leccion;
import mx.edu.um.esu.general.model.Usuario;
import mx.edu.um.esu.general.utils.LeccionNoEncontradaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@Repository
public class LeccionDao {

    private static final Logger log = LoggerFactory.getLogger(LeccionDao.class);
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CarpetaDao carpetaDao;
    @Autowired
    private EtiquetaDao etiquetaDao;

    public List<Leccion> lista() {
        log.debug("Lista de lecciones");
        List<Leccion> lecciones = mongoTemplate.findAll(Leccion.class);
        return lecciones;
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
            criteria.orOperator(Criteria.where("nombre").regex(filtro, "i")
                    , Criteria.where("descripcion").regex(filtro, "i")
                    , Criteria.where("contenido").regex(filtro, "i")
                    );
            query.addCriteria(criteria);
            params.put("cantidad", mongoTemplate.count(query, Usuario.class));
        } else {
            params.put("cantidad", mongoTemplate.count(null, Usuario.class));
        }
        query.skip(offset);
        query.limit(max);
        List<Leccion> lecciones = mongoTemplate.find(query, Leccion.class);
        params.put("lecciones", lecciones);
        return params;
    }

    public void reiniciaColeccion() {
        if (mongoTemplate.collectionExists(Leccion.class)) {
            mongoTemplate.dropCollection(Leccion.class);
        }
    }

    public Leccion crea(Leccion leccion) {
        for (Carpeta carpeta : leccion.getUbicaciones()) {
            carpeta.setNombre(carpeta.getNombre().toLowerCase());
            carpetaDao.crea(carpeta);
        }
        for (Etiqueta etiqueta : leccion.getEtiquetas()) {
            etiqueta.setNombre(etiqueta.getNombre().toLowerCase());
            etiquetaDao.crea(etiqueta);
        }
        leccion.setId(UUID.randomUUID().toString());
        Date fecha = new Date();
        leccion.setFechaCreacion(fecha);
        leccion.setFechaModificacion(fecha);
        mongoTemplate.insert(leccion);
        return leccion;
    }

    public Leccion actualiza(Leccion leccion) {
        Leccion nuevo = obtiene(leccion.getId());
        nuevo.setContenido(leccion.getContenido());
        nuevo.setDescripcion(leccion.getDescripcion());
        nuevo.setEstatus(leccion.getEstatus());
        nuevo.setEtiquetas(leccion.getEtiquetas());
        nuevo.setNombre(leccion.getNombre());
        nuevo.setUbicaciones(leccion.getUbicaciones());
        nuevo.setFechaModificacion(new Date());
        nuevo.setAutor(leccion.getAutor());
        nuevo.setEditor(leccion.getEditor());
        mongoTemplate.save(nuevo);
        return leccion;
    }

    public Leccion obtiene(String id) {
        Leccion leccion = mongoTemplate.findById(id, Leccion.class);
        return leccion;
    }

    public void elimina(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Leccion.class);
    }
    
    public Leccion buscaPorCarpetas(List<String> carpetas) throws LeccionNoEncontradaException {
        Query query = new Query(Criteria.where("ubicaciones.$id").all(carpetas));
        List<Leccion> lecciones = mongoTemplate.find(query, Leccion.class);
        if (lecciones != null) {
            return lecciones.get(0);
        } else {
            throw new LeccionNoEncontradaException("No se encontro la leccion "+carpetas);
        }
    }
}
