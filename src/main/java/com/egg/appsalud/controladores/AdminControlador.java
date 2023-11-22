package com.egg.appsalud.controladores;

import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.servicios.UsuarioServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/panelAdmin")
public class AdminControlador {

    @Autowired
    private UsuarioServicio us;

    @Autowired
    private ProfesionalServicio profesionalServicio;
    
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

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

        return "usuarios";
    }

//    @GetMapping("/profesionalList")
//public String profesionales(@RequestParam(value = "especialidad", required = false) String especialidad,
//                            ModelMap modelo) {
//    List<Profesional> profesionales;
//
//    if (especialidad != null && !especialidad.isEmpty()) {
//        profesionales = profesionalServicio.listarProfesionalesPorEspecialidad(especialidad);
//    } else {
//        profesionales = profesionalServicio.listarProfesional();
//    }
//
//    modelo.addAttribute("profesional", profesionales);
//    modelo.addAttribute("valorSeleccionado", especialidad); // Para mantener la especialidad seleccionada en la vista
//
//    modelo.put("exito", "el profesional fue modificado con exito");
//    return "listaprofesional.html";
//}
    
    @GetMapping("/profesionalList")
public String profesionales(@RequestParam(value = "especialidad", required = false) String especialidad,
                            ModelMap modelo) {
    List<Profesional> profesionales;

    if (especialidad != null && !especialidad.isEmpty()) {
        profesionales = profesionalServicio.listarProfesionalesPorEspecialidad(especialidad);
    } else {
        profesionales = profesionalServicio.listarProfesional();
    }

    List<String> especialidades = profesionalRepositorio.obtenerEspecialidades();

    modelo.addAttribute("profesional", profesionales);
    modelo.addAttribute("valorSeleccionado", especialidad); // Para mantener la especialidad seleccionada en la vista
    modelo.addAttribute("especialidades", especialidades); // Agregar la lista de especialidades al modelo

    modelo.put("exito", "el profesional fue modificado con exito");
    return "listaprofesional.html";
}

    
    @GetMapping("/eliminar/{id}")
    public String eliminarUs(@PathVariable String id) {

        us.eliminarUsuario(id);

        return "redirect:/dashboard/usuarios";
    }
}
