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

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import mx.edu.um.esu.general.dao.ArticuloDao;
import mx.edu.um.esu.general.dao.CarpetaDao;
import mx.edu.um.esu.general.dao.EtiquetaDao;
import mx.edu.um.esu.general.model.Articulo;
import mx.edu.um.esu.general.model.Carpeta;
import mx.edu.um.esu.general.model.Etiqueta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/articulo")
public class ArticuloController {

    private static final Logger log = LoggerFactory.getLogger(ArticuloController.class);
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private CarpetaDao carpetaDao;
    @Autowired
    private EtiquetaDao etiquetaDao;

    @RequestMapping
    public String lista(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = false) String filtro,
            Model modelo) {
        log.debug("Mostrando lista de articulos");
        
        List<Articulo> articulos = articuloDao.lista();
        modelo.addAttribute("articulos", articulos);

        return "admin/articulo/lista";
    }

    @RequestMapping("/ver/{id}")
    public String ver(@PathVariable String id, Model modelo) {
        log.debug("Mostrando articulo {}", id);
        Articulo articulo = articuloDao.obtiene(id);

        modelo.addAttribute("articulo", articulo);

        return "admin/articulo/ver";
    }

    @RequestMapping("/nuevo")
    public String nuevo(Model modelo) {
        log.debug("Nuevo usuario");
        Articulo articulo = new Articulo();
        articulo.setFechaPublicacion(new Date());
        modelo.addAttribute("articulo", articulo);
        return "admin/articulo/nuevo";
    }

    @Transactional
    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public String crea(HttpServletRequest request, HttpServletResponse response, @Valid Articulo articulo, BindingResult bindingResult, Errors errors, Model modelo, RedirectAttributes redirectAttributes) {
        for (String nombre : request.getParameterMap().keySet()) {
            log.debug("Param: {} : {}", nombre, request.getParameterMap().get(nombre));
        }
        if (bindingResult.hasErrors()) {
            log.debug("Hubo algun error en la forma, regresando");
            return "admin/articulo/nuevo";
        }

        try {
            for(Carpeta carpeta : articulo.getUbicaciones()) {
                carpetaDao.crea(carpeta);
            }
            for(Etiqueta etiqueta : articulo.getEtiquetas()) {
                etiquetaDao.crea(etiqueta);
            }
            articulo = articuloDao.crea(articulo);
        } catch (Exception e) {
            log.error("No se pudo crear al articulo", e);
            modelo.addAttribute("message", "articulo.no.creado.message");
            modelo.addAttribute("messageStyle", "alert-error");
            modelo.addAttribute("messageAttrs", new String[]{articulo.getNombre()});
            return "admin/articulo/nuevo";
        }

        redirectAttributes.addFlashAttribute("message", "articulo.creado.message");
        redirectAttributes.addFlashAttribute("messageAttrs", new String[]{articulo.getNombre()});

        return "redirect:/admin/articulo/ver/" + articulo.getId();
    }

    @RequestMapping("/edita/{id}")
    public String edita(@PathVariable String id, Model modelo) {
        log.debug("Edita usuario {}", id);
        Articulo articulo = articuloDao.obtiene(id);
        modelo.addAttribute("articulo", articulo);
        return "admin/articulo/edita";
    }

    @Transactional
    @RequestMapping(value = "/actualiza", method = RequestMethod.POST)
    public String actualiza(HttpServletRequest request, @Valid Articulo articulo, BindingResult bindingResult, Errors errors, Model modelo, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.error("Hubo algun error en la forma, regresando");
            return "admin/articulo/edita";
        }

        try {
            articulo = articuloDao.actualiza(articulo);
        } catch (Exception e) {
            log.error("No se pudo actualizar al articulo", e);
            modelo.addAttribute("message", "articulo.no.actualizado.message");
            modelo.addAttribute("messageStyle", "alert-error");
            modelo.addAttribute("messageAttrs", new String[]{articulo.getNombre()});
            return "admin/articulo/nuevo";
        }

        redirectAttributes.addFlashAttribute("message", "articulo.actualizado.message");
        redirectAttributes.addFlashAttribute("messageAttrs", new String[]{articulo.getNombre()});

        return "redirect:/admin/articulo/ver/" + articulo.getId();
    }

    @Transactional
    @RequestMapping(value = "/elimina", method = RequestMethod.POST)
    public String elimina(@RequestParam String id, Model modelo, @ModelAttribute Articulo articulo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.debug("Elimina articulo");
        try {
            articuloDao.elimina(id);
            redirectAttributes.addFlashAttribute("message", "articulo.eliminado.message");
        } catch (Exception e) {
            log.error("No se pudo eliminar el articulo " + id, e);
            bindingResult.addError(new ObjectError("articulo", new String[]{"articulo.no.eliminado.message"}, null, null));
            return "admin/articulo/ver";
        }

        return "redirect:/admin/articulo";
    }
}
