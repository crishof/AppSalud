package com.egg.appsalud.controladores;

import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {

    @Autowired
    ProfesionalServicio profesionalServicio;

    @GetMapping("/lista")
    public String listarProfesionales(@Param("especialidad") String especialidad, ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesional(especialidad);
        modelo.addAttribute("profesional", profesionales);

        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);

        modelo.addAttribute("valorSeleccionado", especialidad);
        return "listaprofesional";
    }
}
