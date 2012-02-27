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
import mx.edu.um.esu.general.model.Rol;
import mx.edu.um.esu.general.model.Usuario;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
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
public class UsuarioDaoTest {

    private static final Logger log = LoggerFactory.getLogger(UsuarioDaoTest.class);
    @Autowired
    private UsuarioDao instance;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        instance.reiniciaColeccion();
    }

    @After
    public void tearDown() {
        instance.reiniciaColeccion();
    }

    @Test
    public void debieraObtenerListaDeUsuario() {
        log.debug("Debiera obtener lista de articulos");
        Rol rol = mongoTemplate.findById("ROLE_ADMIN", Rol.class);
        if (rol != null) {
            log.debug("Creando rol");
            rol = new Rol("ROLE_ADMIN");
            mongoTemplate.insert(rol);
        }

        for (int i = 1; i <= 20; i++) {
            Usuario usuario = new Usuario("TEST" + i, "TEST" + i, "TEST" + i, "TEST" + i);
            instance.crea(usuario, new String[]{"ROLE_ADMIN"});
        }

        log.debug("Lista de usuarios");
        List<Usuario> usuarios = instance.lista();
        log.info("Total de usuarios {}", usuarios.size());
        assertNotNull(usuarios);
        assertEquals(20, usuarios.size());
    }

    @Test
    public void debieraObtenerUsuario() {
        log.debug("Debiera obtener usuario");
        Rol rol = mongoTemplate.findById("ROLE_ADMIN", Rol.class);
        if (rol != null) {
            log.debug("Creando rol");
            rol = new Rol("ROLE_ADMIN");
            mongoTemplate.insert(rol);
        }

        Usuario usuario = new Usuario("test.01@test.com", "test", "test.01", "test.01");
        instance.crea(usuario, new String[]{"ROLE_ADMIN"});

        Usuario prueba = instance.obtiene(usuario.getUsername());
        assertNotNull(prueba);
        assertEquals("test.01", prueba.getNombre());
        assertTrue(prueba.getEnabled());
        assertTrue(prueba.isAccountNonExpired());
        assertTrue(prueba.isAccountNonLocked());
        assertTrue(prueba.isCredentialsNonExpired());
    }

    @Test
    public void debieraEliminarUsuario() {
        log.debug("Debiera eliminar usuario");
        Rol rol = mongoTemplate.findById("ROLE_ADMIN", Rol.class);
        if (rol != null) {
            log.debug("Creando rol");
            rol = new Rol("ROLE_ADMIN");
            mongoTemplate.insert(rol);
        }

        Usuario usuario = new Usuario("test.01@test.com", "test", "test.01", "test.01");
        instance.crea(usuario, new String[]{"ROLE_ADMIN"});

        instance.elimina(usuario.getUsername());

        Usuario prueba = instance.obtiene(usuario.getUsername());
        assertNull(prueba);
    }
}
