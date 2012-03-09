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

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import mx.edu.um.esu.general.dao.RolDao;
import mx.edu.um.esu.general.dao.UsuarioDao;
import mx.edu.um.esu.general.model.Rol;
import mx.edu.um.esu.general.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@Controller
@RequestMapping("/admin/usuario")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolDao rolDao;

    @RequestMapping
    public String lista(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = false) String filtro,
            Model modelo) {
        log.debug("Mostrando lista de usuarios");

        List<Usuario> usuarios = usuarioDao.lista();
        modelo.addAttribute("usuarios", usuarios);

        return "admin/usuario/lista";
    }
    
    @RequestMapping("/nuevo")
    public String nuevo(Model modelo) {
        log.debug("Nuevo usuario");
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        List<Rol> roles = rolDao.lista();
        modelo.addAttribute("roles", roles);
        
        return "admin/usuario/nuevo";
    }

    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public String crea(HttpServletRequest request, HttpServletResponse response, @Valid Usuario usuario, BindingResult bindingResult, Errors errors, Model modelo, RedirectAttributes redirectAttributes) {
        for (String nombre : request.getParameterMap().keySet()) {
            log.debug("Param: {} : {}", nombre, request.getParameterMap().get(nombre));
        }
        if (bindingResult.hasErrors()) {
            log.debug("Hubo algun error en la forma, regresando");
            List<Rol> roles = rolDao.lista();
            modelo.addAttribute("roles", roles);
            return "admin/usuario/nuevo";
        }

        try {
            log.debug("Evaluando roles {}", request.getParameterValues("roles"));
            String[] roles = request.getParameterValues("roles");
            if (roles == null || roles.length == 0) {
                log.debug("Asignando ROLE_USUARIO por defecto");
                roles = new String[]{"ROLE_USUARIO"};
            }
            usuario = usuarioDao.crea(usuario, roles);

        } catch (ConstraintViolationException e) {
            log.error("No se pudo crear al usuario", e);
            errors.rejectValue("username", "campo.duplicado.message", new String[]{"username"}, null);
            List<Rol> roles = rolDao.lista();
            modelo.addAttribute("roles", roles);
            return "admin/usuario/nuevo";
        }

        redirectAttributes.addFlashAttribute("message", "usuario.creado.message");
        redirectAttributes.addFlashAttribute("messageAttrs", new String[]{usuario.getUsername()});

        return "redirect:/admin/usuario/ver/" + usuario.getUsername();
    }

    @RequestMapping("/ver/{username}")
    public String ver(@PathVariable String username, Model modelo) {
        log.debug("Mostrando usuario {}", username);
        Usuario usuario = usuarioDao.obtiene(username);
        log.debug("Encontre a {}", usuario);
        modelo.addAttribute("usuario", usuario);
        
        List<Rol> roles = rolDao.lista();
        modelo.addAttribute("roles", roles);

        return "admin/usuario/ver";
    }

    @RequestMapping(value = "/elimina", method = RequestMethod.POST)
    public String elimina(@RequestParam String username, Model modelo, @ModelAttribute Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.debug("Elimina usuario");
        try {
            usuarioDao.elimina(username);
            redirectAttributes.addFlashAttribute("message", "usuario.eliminado.message");
            redirectAttributes.addFlashAttribute("messageAttrs", new String[] {usuario.getUsername()});
        } catch (Exception e) {
            log.error("No se pudo eliminar el usuario " + username, e);
            bindingResult.addError(new ObjectError("usuario", new String[]{"usuario.no.eliminado.message"}, null, null));
            return "admin/usuario/ver";
        }

        return "redirect:/admin/usuario";
    }
    
    @RequestMapping("/edita/{username}")
    public String edita(@PathVariable String username, Model modelo) {
        log.debug("Edita usuario {}", username);
        List<Rol> roles = rolDao.lista();
        Usuario usuario = usuarioDao.obtiene(username);
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("roles", roles);
        return "admin/usuario/edita";
    }

    @RequestMapping(value = "/actualiza", method = RequestMethod.POST)
    public String actualiza(HttpServletRequest request, @Valid Usuario usuario, BindingResult bindingResult, Errors errors, Model modelo, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.error("Hubo algun error en la forma, regresando");
            List<Rol> roles = rolDao.lista();
            modelo.addAttribute("roles", roles);
            return "admin/usuario/edita";
        }

        try {
            String[] roles = request.getParameterValues("roles");
            if (roles == null || roles.length == 0) {
                roles = new String[]{"ROLE_USUARIO"};
            }
            usuario = usuarioDao.actualiza(usuario, roles);
        } catch (Exception e) {
            log.error("No se pudo crear al usuario", e);
            
            List<Rol> roles = rolDao.lista();
            modelo.addAttribute("roles", roles);
            return "admin/usuario/edita";
        }

        redirectAttributes.addFlashAttribute("message", "usuario.actualizado.message");
        redirectAttributes.addFlashAttribute("messageAttrs", new String[]{usuario.getUsername()});

        return "redirect:/admin/usuario/ver/" + usuario.getUsername();
    }

}