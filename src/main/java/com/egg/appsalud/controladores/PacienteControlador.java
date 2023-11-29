package com.egg.appsalud.controladores;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return "historiaClinica";
    }

   @GetMapping("/modificar/{id}")
    public String modificarPaciente(@PathVariable String id, ModelMap modelo) {

        Paciente paciente = pacienteServicio.getOne(id);
        //modelo.addAttribute("paciente",paciente);
        modelo.addAttribute(paciente);

        return "paciente_modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modificarPaciente(@PathVariable String id, @RequestParam MultipartFile archivo, @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido,
                                 @RequestParam Long DNI, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password,
                                 @RequestParam String password2, ModelMap modelo) {

        Date fechaDeNacimiento;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "La fecha no puede venir vacía");
            return "redirect:/paciente/modificar";
        }

        try {
            pacienteServicio.modificarPacientes(id, archivo, nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2);
            modelo.put("exito", "cambios realizados con éxito");


        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/modificar/{id}";
        }
        return "redirect:/";
    }

}

