package com.egg.appsalud.controladores;

import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired
    PacienteServicio pacienteServicio;

    @Autowired
    ProfesionalServicio profesionalServicio;

    @GetMapping("/solicitar/{id}")
    public String solicitarCita(@PathVariable String id, ModelMap modelo) {

        var profesional = profesionalServicio.getOne(id);
        modelo.addAttribute("profesional",profesional);
        return "cita_solicitud";
    }

    @PostMapping("/solicitar/{id}")
    public String  reservarCita(@PathVariable String id, @RequestParam String idProfesional, ModelMap model){

        model.addAttribute("paciente",pacienteServicio.getOne(id));
        model.addAttribute("profesional",profesionalServicio.getOne(idProfesional));

        return "redirect:../citas";
    }

    @GetMapping("/historia")
    public String historiaClinica() {
        return "historia_clinica";
    }

//    MODIFICAR DATOS COMO ADMIN
    @GetMapping("/modificar/{id}")
    public String modificarPaciente() {
        return "usuarioModificar";
    }

}

