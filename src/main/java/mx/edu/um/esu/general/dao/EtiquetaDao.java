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

import java.util.List;
import mx.edu.um.esu.general.model.Etiqueta;
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
public class EtiquetaDao {

    private static final Logger log = LoggerFactory.getLogger(EtiquetaDao.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    public void reiniciaColeccion() {
        if (mongoTemplate.collectionExists(Etiqueta.class)) {
            mongoTemplate.dropCollection(Etiqueta.class);
        }
    }

    public Etiqueta crea(Etiqueta etiqueta) {
        log.debug("Creando etiqueta {}", etiqueta);
        Update u = new Update();
        u.set("nombre", etiqueta.getNombre().toLowerCase());
        u.inc("asignaciones", 1);
        mongoTemplate.upsert(new Query(Criteria.where("nombre").is(etiqueta.getNombre().toLowerCase())), u, Etiqueta.class);
//        mongoTemplate.insert(etiqueta);
        return etiqueta;
    }

    public List<Etiqueta> listarPorFiltro(String filtro) {
        filtro = "^" + filtro;
        Query query = new Query(Criteria.where("nombre").regex(filtro, "i"));
        log.debug("Buscando por filtro {}", filtro);
        return mongoTemplate.find(query, Etiqueta.class);
    }
}
