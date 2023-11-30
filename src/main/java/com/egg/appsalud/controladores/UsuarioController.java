package com.egg.appsalud.controladores;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.repositorios.UsuarioRepositorio;
import com.egg.appsalud.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/bajaUsuario/{id}")
    public String bajaUsuario(@PathVariable String id, ModelMap model) throws MiException {
        usuarioServicio.bajaUsuario(id);
        return "redirect:/admin/dashboard";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/altaUsuario/{id}")
    public String altaUsuario(@PathVariable String id, ModelMap model) throws MiException {
        usuarioServicio.altaUsuario(id);
        model.put("usuario",usuarioRepositorio.findById(id));
        return "redirect:/admin/dashboard";
    }
}
