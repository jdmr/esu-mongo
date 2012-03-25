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
package mx.edu.um.esu.general.web;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mx.edu.um.esu.general.Constantes;
import mx.edu.um.esu.general.dao.*;
import mx.edu.um.esu.general.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@Controller
@RequestMapping("/inicializa")
public class InicializaController {

    private static final Logger log = LoggerFactory.getLogger(InicializaController.class);
    @Autowired
    private RolDao rolDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private EstatusDao estatusDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private LeccionDao leccionDao;
    @Autowired
    private EtiquetaDao etiquetaDao;
    @Autowired
    private CarpetaDao carpetaDao;

    @RequestMapping
    public String inicia() {
        log.debug("Mostrando pagina para inicializar");

        return "/inicializa/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String guarda(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo) {
        log.debug("Inicializando aplicacion");
        articuloDao.reiniciaColeccion();
        leccionDao.reiniciaColeccion();
        carpetaDao.reiniciaColeccion();
        etiquetaDao.reiniciaColeccion();
        rolDao.reiniciaColeccion();

        log.debug("Inicializando roles");
        Rol rolAdmin = new Rol(Constantes.ROL_ADMIN);
        rolDao.crea(rolAdmin);
        Rol rolEditor = new Rol(Constantes.ROL_EDITOR);
        rolDao.crea(rolEditor);
        Rol rolAutor = new Rol(Constantes.ROL_AUTOR);
        rolDao.crea(rolAutor);
        Rol rolUsuario = new Rol(Constantes.ROL_USUARIO);
        rolDao.crea(rolUsuario);

        log.debug("Inicializando estatus");
        estatusDao.reiniciaColeccion();
        Estatus pendiente = new Estatus(Constantes.PENDIENTE);
        estatusDao.crea(pendiente);
        Estatus publicado = new Estatus(Constantes.PUBLICADO);
        publicado = estatusDao.crea(publicado);

        log.debug("Inicializando usuarios");
        usuarioDao.reiniciaColeccion();
        Usuario admin = new Usuario(username, password, nombre, apellido, correo);
        usuarioDao.crea(admin, new String[]{Constantes.ROL_ADMIN});

        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql:old_portal", "tomcat", "tomcat00");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select screenname, emailaddress, firstname, lastname, createdate, password_ from user_, users_usergroups where user_.userid = users_usergroups.userid and users_usergroups.usergroupid = 27943");
            while (rs.next()) {
                correo = rs.getString("emailaddress");
                Usuario usuario = usuarioDao.obtienePorCorreo(correo);
                if (usuario == null) {
                    username = rs.getString("screenname");
                    password = rs.getString("password_");
                    nombre = rs.getString("firstname");
                    apellido = rs.getString("lastname");
                    usuario = new Usuario(username, password, nombre, apellido, correo);
                    usuarioDao.crea(usuario, new String[] {Constantes.ROL_EDITOR});
                }
            }
            
            rs = stmt.executeQuery("select screenname, emailaddress, firstname, lastname, createdate, password_ from user_ where companyid = 15686");
            while (rs.next()) {
                correo = rs.getString("emailaddress");
                Usuario usuario = usuarioDao.obtienePorCorreo(correo);
                if (usuario == null) {
                    username = rs.getString("screenname");
                    password = rs.getString("password_");
                    nombre = rs.getString("firstname");
                    apellido = rs.getString("lastname");
                    usuario = new Usuario(username, password, nombre, apellido, correo);
                    usuarioDao.crea(usuario, new String[] {Constantes.ROL_USUARIO});
                }
            }
            
            stmt2 = conn.createStatement();
            rs = stmt.executeQuery("select * from assetentry a, assetentries_assettags b, assettag c where a.entryid = b.entryid and b.tagid = c.tagid and c.name = '2012' and classnameid = 10084");
            while (rs.next()) {
                String titulo = rs.getString("title");
                log.debug("Titulo: {}", rs.getString("title"));
                rs2 = stmt2.executeQuery("select a.description, a.content, a.createdate, b.modifieddate, b.screenname from journalarticle a, user_ b where a.userid = b.userid and a.resourceprimkey = "+rs.getLong("classpk"));
                String descripcion = null;
                String contenido = null;
                String u = null;
                Date fechaCreacion = null;
                Date fechaModificacion = null;
                if (rs2.next()) {
                    descripcion = rs2.getString("description");
                    contenido = rs2.getString("content");
                    int p1 = contenido.indexOf("<![CDATA[");
                    int p2 = contenido.lastIndexOf("]]>");
                    contenido = contenido.substring(p1+9,p2);
                    u = rs2.getString("screenname");
                    fechaCreacion = rs2.getDate("createdate");
                    fechaModificacion = rs2.getDate("modifieddate");
                }
                rs2 = stmt2.executeQuery("select a.name from assettag a, assetentries_assettags b where a.tagid = b.tagid and b.entryid = "+rs.getLong("entryid"));
                List<Carpeta> tags = new ArrayList<>();
                while(rs2.next()) {
                    tags.add(new Carpeta(rs2.getString("name")));
                }
                log.debug("TAGS: {}", tags);
                Usuario usuario = usuarioDao.obtiene(u);
                if (tags.contains(new Carpeta("dialoga")) || tags.contains(new Carpeta("comunica"))) {
                    Articulo articulo = new Articulo();
                    articulo.setAutor(usuario);
                    articulo.setContenido(contenido);
                    articulo.setCreador(admin);
                    articulo.setDescripcion(descripcion);
                    articulo.setEditor(admin);
                    articulo.setFechaCreacion(fechaCreacion);
                    articulo.setFechaModificacion(fechaModificacion);
                    articulo.setFechaPublicacion(fechaCreacion);
                    articulo.setNombre(titulo);
                    articulo.setUbicaciones(tags);
                    articulo.setEstatus(publicado);
                    articuloDao.crea(articulo);
                } else {
                    Leccion leccion = new Leccion();
                    leccion.setAutor(usuario);
                    leccion.setContenido(contenido);
                    leccion.setCreador(admin);
                    leccion.setDescripcion(descripcion);
                    leccion.setEditor(admin);
                    leccion.setFechaCreacion(fechaCreacion);
                    leccion.setFechaModificacion(fechaModificacion);
                    leccion.setFechaPublicacion(fechaCreacion);
                    leccion.setNombre(titulo);
                    leccion.setUbicaciones(tags);
                    leccion.setEstatus(publicado);
                    leccionDao.crea(leccion);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            log.error("No se pudo obtener la lista de usuarios", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rs2 != null) {
                    rs2.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (stmt2 != null) {
                    stmt2.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                log.error("No se pudieron cerrar las conexiones", e);
            }
        }

//        Usuario usuario2 = new Usuario("editor", "editor", "Usuario2", "Editor", "test1@test.com");
//        usuarioDao.crea(usuario2, new String[]{Constantes.ROL_EDITOR});
//
//        Usuario usuario3 = new Usuario("autor", "autor", "Usuario3", "Autor", "test2@test.com");
//        usuarioDao.crea(usuario3, new String[]{Constantes.ROL_AUTOR});
//
//        Usuario usuario4 = new Usuario("usuario", "usuario", "Usuario4", "Normal", "test3@test.com");
//        usuarioDao.crea(usuario4, new String[]{Constantes.ROL_USUARIO});

        log.debug("Aplicacion inicializada");

        return "redirect:/";
    }
}
