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

import javax.validation.Valid;
import mx.edu.um.esu.general.dao.UsuarioDao;
import mx.edu.um.esu.general.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author J. David Mendoza <jdmendoza@um.edu.mx>
 */
@Controller
public class SignUpController {

    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);
    @Autowired
    private UsuarioDao usuarioDao;

    public SignUpController() {
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpForm(WebRequest request, Model modelo) {
        Usuario usuario = new Usuario();
        Connection<?> connection = ProviderSignInUtils.getConnection(request);
        if (connection != null) {
            modelo.addAttribute("message", "provider.signup.message");
            modelo.addAttribute("messageAttrs", new String[]{connection.getKey().getProviderId()});
            UserProfile userProfile = connection.fetchUserProfile();
            log.debug("Creando perfil para {}", userProfile.getName());
            usuario.setNombre(userProfile.getFirstName());
            usuario.setApellido(userProfile.getLastName());
            usuario.setUsername(userProfile.getUsername());
            usuario.setCorreo(userProfile.getEmail());
        }
        modelo.addAttribute("usuario", usuario);
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(WebRequest request, @Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        usuario = usuarioDao.crea(usuario, new String[]{"ROLE_USUARIO"});
        if (usuario != null) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities());
            auth.setDetails(usuario);
            SecurityContextHolder.getContext().setAuthentication(auth);
            ProviderSignInUtils.handlePostSignUp(usuario.getUsername(), request);
            return "redirect:/";
        }
        return null;
    }
}
