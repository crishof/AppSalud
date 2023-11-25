package com.egg.appsalud.controladores;

import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.UsuarioServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panelAdmin")
public class AdminControlador {

    @Autowired
    private UsuarioServicio us;

    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdmin(ModelMap modelo) {

        var usuarios = usuarioServicio.listarTodos();

        modelo.addAttribute("usuarios",usuarios);

        return "dashboard";
    }

    @GetMapping("/usuarios")
    public String usuarios(@Param("palabra") String palabra, ModelMap modelo, ModelMap modelo2) {

        List<Usuario> usuarios = us.listarUsuario(palabra);
        modelo.addAttribute("usuarios", usuarios);

        List<Profesional> profesionales = profesionalServicio.listarProfesional(palabra);
        modelo2.addAttribute("profesional", profesionales);

        modelo.addAttribute("palabra", palabra);

        return "usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUs(@PathVariable String id) {

        us.eliminarUsuario(id);

        return "redirect:/dashboard/usuarios";
    }
}
