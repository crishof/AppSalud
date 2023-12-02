package com.egg.appsalud.controladores;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.servicios.PacienteServicio;
import com.egg.appsalud.servicios.TurnoServicio;
import com.egg.appsalud.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/paciente")
public class PacienteController {


    @Autowired
    PacienteServicio pacienteServicio;

    @Autowired
    TurnoServicio turnoServicio;

    //    MODIFICAR DATOS COMO PACIENTE
    @GetMapping("/editar")
    public String editarPaciente(ModelMap modelo, HttpSession session) {

        Paciente pacienteActualizado = (Paciente) session.getAttribute("pacienteActualizado");
        session.removeAttribute("pacienteActualizado");
        modelo.addAttribute("paciente", pacienteActualizado);

        return "paciente_edit";
    }

    @PostMapping("/editar/{id}")
    public String editarPaciente(@PathVariable String id, @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido,
                                 @RequestParam Long DNI, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password,
                                 @RequestParam String password2,
                                 MultipartFile archivo, ModelMap modelo, HttpSession session) {

        Date fechaDeNacimiento;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.addAttribute("error", "La fecha no puede venir vacía");
            return "redirect:/paciente/editar";
        }

        try {
            pacienteServicio.modificarPacientes(archivo, id, nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2);
            modelo.addAttribute("exito", "cambios realizados con éxito");

            Paciente pacienteActualizado = pacienteServicio.getOne(id);
            session.setAttribute("pacienteActualizado", pacienteActualizado);

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/paciente/editar";
        }
        return "redirect:/";
    }

    @GetMapping("/citas")
    public String listarCitas(ModelMap modelMap, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("usuariosession");
        if (paciente == null) {
            return "redirect:/portal/login";
        }

        var misTurnos = turnoServicio.obtenerTurnosDelPaciente(paciente);
        modelMap.addAttribute("misTurnos", misTurnos);
        return "paciente_citas";
    }


}
