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

import mx.edu.um.esu.general.Constantes;
import mx.edu.um.esu.general.dao.EstatusDao;
import mx.edu.um.esu.general.dao.RolDao;
import mx.edu.um.esu.general.dao.UsuarioDao;
import mx.edu.um.esu.general.model.Estatus;
import mx.edu.um.esu.general.model.Rol;
import mx.edu.um.esu.general.model.Usuario;
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
        Rol rolAdmin = new Rol(Constantes.ROL_ADMIN);
        rolDao.crea(rolAdmin);
        Rol rolEditor = new Rol(Constantes.ROL_EDITOR);
        rolDao.crea(rolEditor);
        Rol rolAutor = new Rol(Constantes.ROL_AUTOR);
        rolDao.crea(rolAutor);
        Rol rolUsuario = new Rol(Constantes.ROL_USUARIO);
        rolDao.crea(rolUsuario);
        
        Estatus pendiente = new Estatus(Constantes.PENDIENTE);
        estatusDao.crea(pendiente);
        Estatus publicado = new Estatus(Constantes.PUBLICADO);
        estatusDao.crea(publicado);

        Usuario usuario = new Usuario(username, password, nombre, apellido, correo);
        usuarioDao.crea(usuario, new String[]{Constantes.ROL_ADMIN});

        Usuario usuario2 = new Usuario("editor", "editor", "Usuario2", "Editor", "test1@test.com");
        usuarioDao.crea(usuario2, new String[]{Constantes.ROL_EDITOR});

        Usuario usuario3 = new Usuario("autor", "autor", "Usuario3", "Autor", "test2@test.com");
        usuarioDao.crea(usuario3, new String[]{Constantes.ROL_AUTOR});
        
        Usuario usuario4 = new Usuario("usuario", "usuario", "Usuario4", "Normal", "test3@test.com");
        usuarioDao.crea(usuario4, new String[]{Constantes.ROL_USUARIO});
        
        log.debug("Aplicacion inicializada");

        return "redirect:/";
    }
}
