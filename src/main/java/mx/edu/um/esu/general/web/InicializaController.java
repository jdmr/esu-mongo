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
import mx.edu.um.esu.general.dao.RolDao;
import mx.edu.um.esu.general.dao.UsuarioDao;
import mx.edu.um.esu.general.model.Rol;
import mx.edu.um.esu.general.model.Usuario;
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

    @Autowired
    private RolDao rolDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @RequestMapping
    public String inicia() {
        return "/inicializa/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String guarda(
            @RequestParam String username,
            @RequestParam String password) {
        Rol rolAdmin = new Rol(Constantes.ROL_ADMIN);
        rolDao.crea(rolAdmin);
        Rol rolEditor = new Rol(Constantes.ROL_EDITOR);
        rolDao.crea(rolEditor);
        Rol rolAutor = new Rol(Constantes.ROL_AUTOR);
        rolDao.crea(rolAutor);
        Rol rolUsuario = new Rol(Constantes.ROL_USUARIO);
        rolDao.crea(rolUsuario);

        Usuario usuario = new Usuario(username, password, "Usuario", "Administrador");
        usuarioDao.crea(usuario, new String[]{Constantes.ROL_ADMIN});

        return "redirect:/";
    }
}
