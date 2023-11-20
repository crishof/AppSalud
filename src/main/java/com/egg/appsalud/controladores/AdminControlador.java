package com.egg.appsalud.controladores;


import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.servicios.UsuarioServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/panelAdmin")
public class AdminControlador {


    @Autowired
    private UsuarioServicio us;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/admin")
    public String panelAdmin(ModelMap modelo) {

        List<Profesional> profesional = profesionalServicio.listarProfesional("");
        modelo.addAttribute("profesional", profesional);

        return "dashboard";
    }

    @GetMapping("/usuarios")
    public String usuarios(@Param("palabra") String palabra, ModelMap modelo, ModelMap modelo2) {

        List<Usuario> usuarios = us.listarUsuario(palabra);
        modelo.addAttribute("usuarios", usuarios);

        List<Profesional> profesionales = profesionalServicio.listarProfesional(palabra);
        modelo2.addAttribute("profesional", profesionales);
        
        modelo.addAttribute("palabra", palabra);

        return "usuarios.html";
    }
    
    @GetMapping("/profesionalList")
    public String profesionales(@Param("especialidad") String especialidad, ModelMap modelo){
        List<Profesional> profesionales = profesionalServicio.listarProfesional(especialidad);
        modelo.addAttribute("profesional", profesionales);
        
        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);
        
        modelo.addAttribute("valorSeleccionado", especialidad);
        return "listaprofesional.html";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUs(@PathVariable String id) {

        us.eliminarUsuario(id);

        return "redirect:/dashboard/usuarios";
    }
}
