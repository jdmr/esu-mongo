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
import java.util.List;
import mx.edu.um.esu.general.dao.ArticuloDao;
import mx.edu.um.esu.general.dao.LeccionDao;
import mx.edu.um.esu.general.model.Articulo;
import mx.edu.um.esu.general.model.Carpeta;
import mx.edu.um.esu.general.model.Leccion;
import mx.edu.um.esu.general.utils.ArticuloNoEncontradoException;
import mx.edu.um.esu.general.utils.LeccionNoEncontradaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@Controller
public class InicioController {

    private static final Logger log = LoggerFactory.getLogger(InicioController.class);
    @Autowired
    private LeccionDao leccionDao;
    @Autowired
    private ArticuloDao articuloDao;
    
    @RequestMapping({"/", "/inicio"})
    public String inicio() {
        log.debug("Cargando pagina de inicio");
        StringBuilder sb = new StringBuilder();
        sb.append("redirect:/inicio/2012/t1/l12/sabado");
        return sb.toString();
    }

    @RequestMapping({"/inicio/{anio}/{trimestre}/{leccion}/{dia}"})
    public String dia(@PathVariable String anio, @PathVariable String trimestre, @PathVariable String leccion, @PathVariable String dia, Model modelo) {
        log.debug("Cargando pagina de inicio");
        log.debug("TAGS: {} {} {} {}", new String[]{anio, trimestre, leccion, dia});
        List<String> ubicaciones = new ArrayList<>();
        ubicaciones.add(anio);
        ubicaciones.add(trimestre);
        ubicaciones.add(leccion);
        ubicaciones.add(dia);
        Leccion l;
        try {
            l = leccionDao.buscaPorCarpetas(ubicaciones);
            log.debug("Leccion {}", l);
            modelo.addAttribute("leccion", l);
        } catch (LeccionNoEncontradaException e) {
            log.error("No se encontro la leccion", e);
        }
        
        List<Articulo> articulos;
        try {
            ubicaciones.set(3, "dialoga");
            articulos = articuloDao.buscaPorCarpetas(ubicaciones);
            log.debug("Dialoga {}", articulos);
            for(Articulo articulo : articulos) {
                StringBuilder sb = new StringBuilder();
                sb.append("/").append(anio);
                sb.append("/").append(trimestre);
                sb.append("/").append(leccion);
                for(Carpeta label : articulo.getUbicaciones()) {
                    if (label.getNombre().startsWith("tema")) {
                        sb.append("/").append(label);
                        break;
                    }
                }
                articulo.setUrl(sb.toString());
            }
            modelo.addAttribute("articulosDialoga", articulos);
        } catch(ArticuloNoEncontradoException e) {
            log.error("No se encontro ningun articulo", e);
        }
        
        try {
            ubicaciones.set(3, "comunica");
            articulos = articuloDao.buscaPorCarpetas(ubicaciones);
            log.debug("Comunica {}", articulos);
            for(Articulo articulo : articulos) {
                StringBuilder sb = new StringBuilder();
                sb.append("/").append(anio);
                sb.append("/").append(trimestre);
                sb.append("/").append(leccion);
                for(Carpeta label : articulo.getUbicaciones()) {
                    if (label.getNombre().startsWith("tema")) {
                        sb.append("/").append(label);
                        break;
                    }
                }
                articulo.setUrl(sb.toString());
            }
            modelo.addAttribute("articulosComunica", articulos);
        } catch(ArticuloNoEncontradoException e) {
            log.error("No se encontro ningun articulo", e);
        }
        return "inicio/index";
    }
}
