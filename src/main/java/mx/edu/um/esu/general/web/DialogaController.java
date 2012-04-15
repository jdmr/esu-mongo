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
import mx.edu.um.esu.general.model.Articulo;
import mx.edu.um.esu.general.utils.ArticuloNoEncontradoException;
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
@RequestMapping("/dialoga")
public class DialogaController {

    private static final Logger log = LoggerFactory.getLogger(DialogaController.class);
    @Autowired
    private ArticuloDao articuloDao;

    @RequestMapping("/ver/{anio}/{trimestre}/{leccion}/{tema}")
    public String ver(@PathVariable String anio, @PathVariable String trimestre, @PathVariable String leccion, @PathVariable String tema, Model modelo) {
        List<String> ubicaciones = new ArrayList<>();
        ubicaciones.add(anio);
        ubicaciones.add(trimestre);
        ubicaciones.add(leccion);
        ubicaciones.add("dialoga");
        ubicaciones.add(tema);
        try {
            List<Articulo> articulos = articuloDao.buscaPorCarpetas(ubicaciones);
            Articulo articulo = articulos.get(0);
            modelo.addAttribute("articulo", articulo);
        } catch (ArticuloNoEncontradoException e) {
            log.error("No se encontro el articulo", e);
        }
        
        return "/dialoga/ver";
    }
}
