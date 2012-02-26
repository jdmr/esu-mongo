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
import mx.edu.um.esu.general.model.Articulo;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:esu.xml", "classpath:security.xml"})
@Transactional
public class ArticuloDaoTest {
    
    private static final Logger log = LoggerFactory.getLogger(ArticuloDaoTest.class);
    
    @Autowired
    private ArticuloDao instance;
    
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
    }

    /**
     * Test of logTodosLosArticulos method, of class ArticuloDao.
     */
    @Test
    public void debieraObtenerListaDeArticulos() {
        for(int i = 1; i <= 20; i++) {
            Articulo articulo = new Articulo("TEST"+i,"TEST"+i);
            instance.crea(articulo);
        }
        
        log.debug("Lista de articulos");
        List<Articulo> articulos = instance.lista();
        log.info("Total de articulos {}", articulos.size());
        for (Articulo articulo : articulos) {
            log.info("{}", articulo);
        }
    }

    /**
     * Test of crea method, of class ArticuloDao.
     */
    @Test
    public void testCrea() {
        Articulo articulo = new Articulo("TEST1","TEST1");
        articulo = instance.crea(articulo);
        assertNotNull(articulo);
        assertNotNull(articulo.getArticuloId());
    }
    
    @Test
    public void obtieneArticulo() {
        Articulo articulo = new Articulo("TEST1","TEST1");
        articulo = instance.crea(articulo);
        assertNotNull(articulo);
        assertNotNull(articulo.getArticuloId());
        
        Articulo prueba = instance.obtiene(articulo.getArticuloId());
        assertNotNull(prueba);
        assertEquals(articulo.getArticuloId(), prueba.getArticuloId());
    }

    /**
     * Test of elimina method, of class ArticuloDao.
     */
    @Test
    public void testElimina() {
        Articulo articulo = new Articulo("TEST1","TEST1");
        articulo = instance.crea(articulo);
        assertNotNull(articulo);
        assertNotNull(articulo.getArticuloId());
        
        instance.elimina(articulo.getArticuloId());
    }
}