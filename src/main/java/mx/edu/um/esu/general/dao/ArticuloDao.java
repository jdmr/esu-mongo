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
import java.util.UUID;
import mx.edu.um.esu.general.model.Articulo;
import mx.edu.um.esu.general.model.Carpeta;
import mx.edu.um.esu.general.model.Etiqueta;
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

    public void reiniciaColeccion() {
        if (mongoTemplate.collectionExists(Articulo.class)) {
            mongoTemplate.dropCollection(Articulo.class);
        }
    }

    public Articulo crea(Articulo articulo) {
        for (Carpeta carpeta : articulo.getUbicaciones()) {
            carpetaDao.crea(carpeta);
        }
        for (Etiqueta etiqueta : articulo.getEtiquetas()) {
            etiquetaDao.crea(etiqueta);
        }
        articulo.setId(UUID.randomUUID().toString());
        Date fecha = new Date();
        articulo.setFechaCreacion(fecha);
        articulo.setFechaModificacion(fecha);
        mongoTemplate.insert(articulo);
        return articulo;
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
}
