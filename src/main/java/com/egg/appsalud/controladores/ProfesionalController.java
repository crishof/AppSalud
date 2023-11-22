package com.egg.appsalud.controladores;

import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {

    @Autowired
    ProfesionalServicio profesionalServicio;

//    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")


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
