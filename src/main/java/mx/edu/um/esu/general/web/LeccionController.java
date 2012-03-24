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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import mx.edu.um.esu.general.dao.*;
import mx.edu.um.esu.general.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/admin/leccion")
public class LeccionController {

    private static final Logger log = LoggerFactory.getLogger(LeccionController.class);
    @Autowired
    private LeccionDao leccionDao;
    @Autowired
    private EstatusDao estatusDao;
    @Autowired
    private CarpetaDao carpetaDao;
    @Autowired
    private EtiquetaDao etiquetaDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @RequestMapping
    public String lista(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = false) String filtro,
            Model modelo) {
        log.debug("Mostrando lista de lecciones");

        List<Leccion> lecciones = leccionDao.lista();
        modelo.addAttribute("lecciones", lecciones);

        return "admin/leccion/lista";
    }

    @RequestMapping("/ver/{id}")
    public String ver(@PathVariable String id, Model modelo) {
        log.debug("Mostrando leccion {}", id);
        Leccion leccion = leccionDao.obtiene(id);

        modelo.addAttribute("leccion", leccion);

        return "admin/leccion/ver";
    }

    @RequestMapping("/nuevo")
    public String nuevo(Model modelo) {
        log.debug("Nuevo leccion");
        Leccion leccion = new Leccion();
        leccion.setFechaPublicacion(new Date());
        modelo.addAttribute("leccion", leccion);
        List<Estatus> estados = estatusDao.lista();
        modelo.addAttribute("estados", estados);
        
        List<Usuario> autores = usuarioDao.autores();
        modelo.addAttribute("autores", autores);
        List<Usuario> editores = usuarioDao.editores();
        modelo.addAttribute("editores", editores);
        
        return "admin/leccion/nuevo";
    }

    @Transactional
    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public String crea(HttpServletRequest request, HttpServletResponse response, @Valid Leccion leccion, BindingResult bindingResult, Errors errors, Model modelo, RedirectAttributes redirectAttributes) {
        for (String nombre : request.getParameterMap().keySet()) {
            log.debug("Param: {} : {}", nombre, request.getParameterMap().get(nombre));
        }
        if (bindingResult.hasErrors()) {
            log.debug("Hubo algun error en la forma, regresando");
            List<Estatus> estados = estatusDao.lista();
            modelo.addAttribute("estados", estados);
        
            List<Usuario> autores = usuarioDao.autores();
            modelo.addAttribute("autores", autores);
            List<Usuario> editores = usuarioDao.editores();
            modelo.addAttribute("editores", editores);

            return "admin/leccion/nuevo";
        }

        try {
            Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            leccion.setCreador(usuario);
            leccion = leccionDao.crea(leccion);
        } catch (Exception e) {
            log.error("No se pudo crear al leccion", e);
            List<Estatus> estados = estatusDao.lista();
            modelo.addAttribute("estados", estados);
        
            List<Usuario> autores = usuarioDao.autores();
            modelo.addAttribute("autores", autores);
            List<Usuario> editores = usuarioDao.editores();
            modelo.addAttribute("editores", editores);

            modelo.addAttribute("message", "leccion.no.creado.message");
            modelo.addAttribute("messageStyle", "alert-error");
            modelo.addAttribute("messageAttrs", new String[]{leccion.getNombre()});
            return "admin/leccion/nuevo";
        }

        redirectAttributes.addFlashAttribute("message", "leccion.creado.message");
        redirectAttributes.addFlashAttribute("messageAttrs", new String[]{leccion.getNombre()});

        return "redirect:/admin/leccion/ver/" + leccion.getId();
    }

    @RequestMapping("/edita/{id}")
    public String edita(@PathVariable String id, Model modelo) {
        log.debug("Edita leccion {}", id);
        Leccion leccion = leccionDao.obtiene(id);
        modelo.addAttribute("leccion", leccion);
        List<Estatus> estados = estatusDao.lista();
        modelo.addAttribute("estados", estados);
        
        List<Usuario> autores = usuarioDao.autores();
        modelo.addAttribute("autores", autores);
        List<Usuario> editores = usuarioDao.editores();
        modelo.addAttribute("editores", editores);
        
        return "admin/leccion/edita";
    }

    @Transactional
    @RequestMapping(value = "/actualiza", method = RequestMethod.POST)
    public String actualiza(HttpServletRequest request, @Valid Leccion leccion, BindingResult bindingResult, Errors errors, Model modelo, RedirectAttributes redirectAttributes) {
        log.debug("Actualizando leccion");
        if (bindingResult.hasErrors()) {
            log.error("Hubo algun error en la forma, regresando");
            List<Estatus> estados = estatusDao.lista();
            modelo.addAttribute("estados", estados);
        
            List<Usuario> autores = usuarioDao.autores();
            modelo.addAttribute("autores", autores);
            List<Usuario> editores = usuarioDao.editores();
            modelo.addAttribute("editores", editores);

            return "admin/leccion/edita";
        }

        try {
            leccion = leccionDao.actualiza(leccion);
        } catch (Exception e) {
            log.error("No se pudo actualizar al leccion", e);
            List<Estatus> estados = estatusDao.lista();
            modelo.addAttribute("estados", estados);
        
            List<Usuario> autores = usuarioDao.autores();
            modelo.addAttribute("autores", autores);
            List<Usuario> editores = usuarioDao.editores();
            modelo.addAttribute("editores", editores);
        
            modelo.addAttribute("message", "leccion.no.actualizado.message");
            modelo.addAttribute("messageStyle", "alert-error");
            modelo.addAttribute("messageAttrs", new String[]{leccion.getNombre()});
            return "admin/leccion/edita";
        }

        redirectAttributes.addFlashAttribute("message", "leccion.actualizado.message");
        redirectAttributes.addFlashAttribute("messageAttrs", new String[]{leccion.getNombre()});

        return "redirect:/admin/leccion/ver/" + leccion.getId();
    }

    @Transactional
    @RequestMapping(value = "/elimina", method = RequestMethod.POST)
    public String elimina(@RequestParam String id, Model modelo, @ModelAttribute Leccion leccion, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.debug("Elimina leccion");
        try {
            leccionDao.elimina(id);
            redirectAttributes.addFlashAttribute("message", "leccion.eliminado.message");
        } catch (Exception e) {
            log.error("No se pudo eliminar el leccion " + id, e);
            bindingResult.addError(new ObjectError("leccion", new String[]{"leccion.no.eliminado.message"}, null, null));
            return "admin/leccion/ver";
        }

        return "redirect:/admin/leccion";
    }

    @RequestMapping(value = "/carpetas", params = "term", produces = "application/json")
    public @ResponseBody
    List<String> carpetas(HttpServletRequest request, @RequestParam("term") String filtro) {
        List<Carpeta> lista = carpetaDao.listarPorFiltro(filtro);
        List<String> resultado = new ArrayList<>();
        for (Carpeta carpeta : lista) {
            resultado.add(carpeta.getNombre());
        }
        return resultado;
    }

    @RequestMapping(value = "/etiquetas", params = "term", produces = "application/json")
    public @ResponseBody
    List<String> etiquetas(HttpServletRequest request, @RequestParam("term") String filtro) {
        List<Etiqueta> lista = etiquetaDao.listarPorFiltro(filtro);
        List<String> resultado = new ArrayList<>();
        for (Etiqueta etiqueta : lista) {
            resultado.add(etiqueta.getNombre());
        }
        return resultado;
    }
}
