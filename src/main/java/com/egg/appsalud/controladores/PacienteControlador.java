package com.egg.appsalud.controladores;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.repositorios.ConsultaRepositorio;
import com.egg.appsalud.servicios.ConsultaServicio;
import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired
    PacienteServicio pacienteServicio;

    @Autowired
    ProfesionalServicio profesionalServicio;

    @Autowired
    ConsultaServicio consultaServicio;

    @GetMapping("/solicitar/{id}")
    public String solicitarCita(@PathVariable String id, ModelMap modelo) {

        var profesional = profesionalServicio.getOne(id);
        modelo.addAttribute("profesional",profesional);
        return "cita_solicitud";
    }

    @PostMapping("/solicitar/{id}")
    public String  reservarCita(@PathVariable String id, @RequestParam String idProfesional, @RequestParam Date fecha, @RequestParam LocalTime horario, ModelMap model) throws MiException {


        var Paciente = pacienteServicio.getOne(id);
        var Profesional = profesionalServicio.getOne(idProfesional);

        consultaServicio.crearConsulta(Paciente,Profesional,fecha, horario);

        return "redirect:/citas";
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

