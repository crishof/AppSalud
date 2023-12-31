package com.egg.appsalud.controladores;

import com.egg.appsalud.Exception.MiException;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.Enumeracion.Provincias;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.servicios.ProfesionalServicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

import com.egg.appsalud.servicios.TurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    TurnoServicio turnoServicio;

    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @GetMapping("/modificar/{id}")
    public String modificarProfesional(@PathVariable String id, ModelMap modelo) {

        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);
        Profesional profesional = profesionalServicio.getOne(id);
        Provincias[] provincias = Provincias.values();
        modelo.addAttribute("provincias", provincias);
        modelo.addAttribute(profesional);

        return "profesional_modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modificarProfesional(@PathVariable String id, MultipartFile archivo, @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido,
                                       @RequestParam(required = false) Long DNI, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password, @RequestParam String password2,
                                       @RequestParam Especialidad especialidad, @RequestParam Provincias provincias, @RequestParam String localidad, @RequestParam String direccion, @RequestParam int precioConsulta, @RequestParam Long matricula, ModelMap modelo) {

        Date fechaDeNacimiento;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "la fecha no puede venir vacía");
            return "redirect:/profesional/modificar/{id}";
        }

        try {

            profesionalServicio.modificarProfesional(id, archivo, nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2, true, especialidad, provincias, localidad, direccion, matricula, precioConsulta);
            modelo.put("exito", "Profesional modificado con exito");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/modificar/{id}";

        }
        return "redirect:../../listaProfesionales";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminarProfesional(@PathVariable String id) {
        profesionalRepositorio.deleteById(id);
        return "index";
    }

//    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN', 'ROLE_USER', 'ROLE_PACIENTE')")
//    @GetMapping("/profesionalList")
//    public String profesionales(@Param("especialidad") String especialidad, ModelMap modelo) {
//        List<Profesional> profesionales = profesionalServicio.listarProfesional(especialidad);
//        modelo.addAttribute("profesional", profesionales);
//
//        Especialidad[] especialidades = Especialidad.values();
//        modelo.addAttribute("especialidades", especialidades);
//
//        modelo.addAttribute("valorSeleccionado", especialidad);
//        return "listaprofesional";
//    }

    @GetMapping("/citasProfesional")
    public String listarCitas(ModelMap modelo, HttpSession session) {

        Profesional profesional = (Profesional) session.getAttribute("usuariosession");
        if ( profesional == null){
            return "redirect:/portal/login";
        }

        List<Turno> misTurnosProfesional = turnoServicio.obtenerTurnosDeProfesional(profesional);
        modelo.addAttribute("misTurnosProfesional",misTurnosProfesional);
        var pacientes = pacienteRepositorio.findAll();
        modelo.addAttribute("pacientes",pacientes);
        
        return "citasProfesional";
        
    }

    @GetMapping("/listaPacientes")
    public String listarPacientes(ModelMap modelMap, HttpSession session) {

        Profesional profesional = (Profesional) session.getAttribute("usuariosession");
        if ( profesional == null){
            return "redirect:/portal/login";
        }

        System.out.println("profesional" + profesional.getId());

        List<Paciente> misPacientes = profesionalServicio.listarPacientesDelProfesional(profesional);

        for (Paciente paciente:misPacientes
             ) {
            System.out.println("paciente.getNombre() = " + paciente.getNombre());
            System.out.println("paciente = " + paciente.getApellido());
        }

        modelMap.addAttribute("pacientes",misPacientes);


        return "listaPacientes";
    }


}
