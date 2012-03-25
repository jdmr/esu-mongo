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

import java.util.*;
import mx.edu.um.esu.general.Constantes;
import mx.edu.um.esu.general.model.Rol;
import mx.edu.um.esu.general.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@Repository("userDao")
public class UsuarioDao {

    private static final Logger log = LoggerFactory.getLogger(UsuarioDao.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MongoTemplate mongoTemplate;

    public UsuarioDao() {
        log.info("Se ha creado una nueva instancia de UsuarioDao");
    }

    public void reiniciaColeccion() {
        if (mongoTemplate.collectionExists(Usuario.class)) {
            mongoTemplate.dropCollection(Usuario.class);
        }
    }

    public List<Usuario> lista() {
        log.debug("Buscando lista de usuarios ");

        List<Usuario> usuarios = mongoTemplate.findAll(Usuario.class);

        return usuarios;
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
                    , Criteria.where("apellido").regex(filtro, "i")
                    , Criteria.where("correo").regex(filtro, "i")
                    , Criteria.where("_id").regex(filtro, "i")
                    );
            query.addCriteria(criteria);
            params.put("cantidad", mongoTemplate.count(query, Usuario.class));
        } else {
            params.put("cantidad", mongoTemplate.count(null, Usuario.class));
        }
        query.skip(offset);
        query.limit(max);
        List<Usuario> usuarios = mongoTemplate.find(query, Usuario.class);
        params.put("usuarios", usuarios);
        return params;
    }

    public Usuario obtiene(String id) {
        Usuario usuario = mongoTemplate.findById(id, Usuario.class);
        return usuario;
    }

    public Usuario obtienePorCorreo(String correo) {
        Query query = new Query(Criteria.where("correo").is(correo));
        Usuario usuario = mongoTemplate.findOne(query, Usuario.class);
        return usuario;
    }

    public Usuario obtienePorOpenId(String openId) {
        Query query = new Query(Criteria.where("openId").is(openId));
        Usuario usuario = mongoTemplate.findOne(query, Usuario.class);
        return usuario;
    }

    public Usuario crea(Usuario usuario, String[] nombreDeRoles) {
        usuario.setPassword(passwordEncoder.encodePassword(usuario.getPassword(), usuario.getUsername()));

        if (usuario.getRoles() != null) {
            usuario.getRoles().clear();
        } else {
            usuario.setRoles(new HashSet<Rol>());
        }
        for (String nombre : nombreDeRoles) {
            Query query = new Query(Criteria.where("authority").is(nombre));
            Rol rol = mongoTemplate.findOne(query, Rol.class);
            usuario.addRol(rol);
        }
        usuario.setFechaRegistro(new Date());
        mongoTemplate.insert(usuario);
        return usuario;
    }

    public Usuario actualiza(Usuario usuario, String[] nombreDeRoles) {
        Usuario nuevoUsuario = obtiene(usuario.getUsername());
        if (!nuevoUsuario.getPassword().equals(usuario.getPassword())) {
            usuario.setPassword(passwordEncoder.encodePassword(usuario.getPassword(), usuario.getUsername()));
        }

        if (usuario.getRoles() != null) {
            usuario.getRoles().clear();
        } else {
            usuario.setRoles(new HashSet<Rol>());
        }

        for (String nombre : nombreDeRoles) {
            Query query = new Query(Criteria.where("authority").is(nombre));
            Rol rol = mongoTemplate.findOne(query, Rol.class);
            usuario.addRol(rol);
        }
        mongoTemplate.save(usuario);
        return usuario;
    }

    public String elimina(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        mongoTemplate.remove(query, Usuario.class);
        return username;
    }

    public List<Usuario> autores() {
        log.debug("Buscando autores");
        List<String> roles = new ArrayList<>();
        roles.add(Constantes.ROL_ADMIN);
        roles.add(Constantes.ROL_EDITOR);
        roles.add(Constantes.ROL_AUTOR);
        Query query = new Query(Criteria.where("roles.$id").in(roles));
        List<Usuario> usuarios = mongoTemplate.find(query, Usuario.class);
        return usuarios;
    }

    public List<Usuario> editores() {
        List<String> roles = new ArrayList<>();
        roles.add(Constantes.ROL_ADMIN);
        roles.add(Constantes.ROL_EDITOR);
        Query query = new Query(Criteria.where("roles.$id").in(roles));
        List<Usuario> usuarios = mongoTemplate.find(query, Usuario.class);
        return usuarios;
    }
}
