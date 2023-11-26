package com.egg.appsalud.controladores;

import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/editar")
    public String editarPaciente(){
        return "paciente_edit";
    }

    @GetMapping("/citas")
    public String listarCitas() {
        return "citasPaciente";
    }

    @GetMapping("/historia")
    public String historiaClinica() {
        return "historiaClinica";
    }

    @GetMapping("/modificar")
    public String modificarPaciente() {
        return "usuarioModificar";
    }

}

