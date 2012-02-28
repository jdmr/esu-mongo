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
import mx.edu.um.esu.general.Constantes;
import mx.edu.um.esu.general.model.Articulo;
import mx.edu.um.esu.general.model.Estatus;
import mx.edu.um.esu.general.model.Rol;
import mx.edu.um.esu.general.model.Usuario;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:esu.xml"})
public class ArticuloDaoTest {

    private static final Logger log = LoggerFactory.getLogger(ArticuloDaoTest.class);
    @Autowired
    private ArticuloDao instance;
    @Autowired
    private MongoTemplate mongoTemplate;

    public ArticuloDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance.reiniciaColeccion();
    }

    @After
    public void tearDown() {
        instance.reiniciaColeccion();
    }

    @Test
    public void debieraObtenerListaDeArticulos() {
        Estatus estatus = new Estatus(Constantes.PUBLICADO);
        mongoTemplate.insert(estatus);
        Rol rol = new Rol(Constantes.ROL_ADMIN);
        mongoTemplate.insert(rol);
        Usuario autor = new Usuario("TEST", "TEST", "TEST", "TEST");
        autor.addRol(rol);
        mongoTemplate.insert(autor);
        for (int i = 1; i <= 20; i++) {
            Articulo articulo = new Articulo("TEST" + i, "TEST" + i, "TEST" + i, estatus, autor);
            instance.crea(articulo);
        }

        log.debug("Lista de articulos");
        List<Articulo> articulos = instance.lista();
        log.info("Total de articulos {}", articulos.size());
        for (Articulo articulo : articulos) {
            log.info("{}", articulo);
        }
        assertNotNull(articulos);
        assertEquals(20, articulos.size());
    }

    /**
     * Test of crea method, of class ArticuloDao.
     */
    @Test
    public void testCrea() {
        log.debug("Creando articulo");
        Estatus estatus = new Estatus(Constantes.PUBLICADO);
        mongoTemplate.insert(estatus);
        Rol rol = new Rol(Constantes.ROL_ADMIN);
        mongoTemplate.insert(rol);
        Usuario autor = new Usuario("TEST", "TEST", "TEST", "TEST");
        autor.addRol(rol);
        mongoTemplate.insert(autor);
        Articulo articulo = new Articulo("TEST1", "TEST1", "TEST1", estatus, autor);
        articulo = instance.crea(articulo);
        assertNotNull(articulo);
        assertNotNull(articulo.getArticuloId());
    }

    @Test
    public void obtieneArticulo() {
        log.debug("Obtiene articulo por id");
        Estatus estatus = new Estatus(Constantes.PUBLICADO);
        mongoTemplate.insert(estatus);
        Rol rol = new Rol(Constantes.ROL_ADMIN);
        mongoTemplate.insert(rol);
        Usuario autor = new Usuario("TEST", "TEST", "TEST", "TEST");
        autor.addRol(rol);
        mongoTemplate.insert(autor);
        Articulo articulo = new Articulo("TEST1", "TEST1", "TEST1", estatus, autor);
        articulo = instance.crea(articulo);
        assertNotNull(articulo);
        assertNotNull(articulo.getArticuloId());

        Articulo prueba = instance.obtiene(articulo.getArticuloId());
        assertNotNull(prueba);
        assertEquals(articulo.getArticuloId(), prueba.getArticuloId());
    }

    @Test
    public void debieraModificarArticulo() {
        log.debug("Obtiene articulo por id");
        Estatus estatus = new Estatus(Constantes.PUBLICADO);
        mongoTemplate.insert(estatus);
        Rol rol = new Rol(Constantes.ROL_ADMIN);
        mongoTemplate.insert(rol);
        Usuario autor = new Usuario("TEST", "TEST", "TEST", "TEST");
        autor.addRol(rol);
        mongoTemplate.insert(autor);
        Articulo articulo = new Articulo("TEST1", "TEST1", "TEST1", estatus, autor);
        articulo = instance.crea(articulo);
        assertNotNull(articulo);
        assertNotNull(articulo.getArticuloId());

        Articulo prueba = instance.obtiene(articulo.getArticuloId());
        assertNotNull(prueba);
        assertEquals(articulo.getArticuloId(), prueba.getArticuloId());

        prueba.setDescripcion("TEST");
        instance.actualiza(prueba);

        Articulo test = instance.obtiene(articulo.getArticuloId());
        assertNotNull(test);
        assertEquals("TEST", test.getDescripcion());
    }

    /**
     * Test of elimina method, of class ArticuloDao.
     */
    @Test
    public void testElimina() {
        log.debug("Elimina articulo por id");
        Estatus estatus = new Estatus(Constantes.PUBLICADO);
        mongoTemplate.insert(estatus);
        Rol rol = new Rol(Constantes.ROL_ADMIN);
        mongoTemplate.insert(rol);
        Usuario autor = new Usuario("TEST", "TEST", "TEST", "TEST");
        autor.addRol(rol);
        mongoTemplate.insert(autor);
        Articulo articulo = new Articulo("TEST1", "TEST1", "TEST1", estatus, autor);
        articulo = instance.crea(articulo);
        assertNotNull(articulo);
        assertNotNull(articulo.getArticuloId());

        instance.elimina(articulo.getArticuloId());
    }
}
