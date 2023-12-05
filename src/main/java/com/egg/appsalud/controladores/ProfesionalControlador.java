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
<<<<<<< HEAD

import javax.servlet.http.HttpSession;
=======
>>>>>>> botonModificarAdmin

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
<<<<<<< HEAD
    TurnoServicio turnoServicio;
=======
    private ProfesionalRepositorio profesionalRepositorio;
    
//    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
//    @GetMapping("/registro")
//    public String registro(ModelMap modelo) {
//
//        Especialidad[] especialidades = Especialidad.values();
//        modelo.addAttribute("especialidades", especialidades);
//        return "registroProfesional.html    ";
//    }
//
//    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
//    @PostMapping("/registro")
//    public String registrarProfesional(/*@RequestParam MultipartFile archivo,*/@RequestParam String nombreUsuario, @RequestParam String nombre,
//            @RequestParam String apellido, @RequestParam(required = false) Long dni, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr,
//            @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam(required = false) Long matricula,
//            /*List<ObraSocial> obrasocial, @RequestParam Establecimiento establecimiento,*/ @RequestParam Especialidad especialidad, ModelMap modelo) throws MiException, ParseException {
//
//        Date fechaDeNacimiento;
//
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);
//
//        } catch (ParseException p) {
//            modelo.put("error", "la fecha no puede venir vacía");
//            return "registroProfesional.html";
//        }
//
//        try {
//            profesionalServicio.crearProfesional(nombreUsuario, password, password2, nombre, apellido, email, fechaDeNacimiento, dni, especialidad, matricula/*, obrasocial*/);
//            modelo.put("exito", "el profesional fue creado con exito");
//            return "registroProfesional.html";
//        } catch (MiException e) {
//
//            modelo.put("error", e.getMessage());
//
//            //return "redirect:/profesional/registro";
//            return "registroProfesional.html";
//        }
//    }
>>>>>>> alejandrod

    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @GetMapping("/modificar/{id}")
    public String modificarProfesional(@PathVariable String id, ModelMap modelo) {
<<<<<<< HEAD

        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);
        Profesional profesional = profesionalServicio.getOne(id);
        Provincias[] provincias = Provincias.values();
        modelo.addAttribute("provincias", provincias);
        modelo.addAttribute(profesional);

        return "profesional_modificar";
=======
        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);
        
        Profesional profesional = profesionalServicio.getOne(id);

        modelo.addAttribute("profesional", profesional);

        return "modificarProfesional.html";
>>>>>>> alejandrod
    }

    @PostMapping("/modificar/{id}")
<<<<<<< HEAD
<<<<<<< HEAD
    public String modificarProfesional(@PathVariable String id, MultipartFile archivo, @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido,
=======
    public String modificarProfesional(@PathVariable String id, @RequestParam MultipartFile archivo, @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido,
>>>>>>> botonModificarAdmin
                                       @RequestParam(required = false) Long DNI, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password, @RequestParam String password2,
                                       @RequestParam Especialidad especialidad, @RequestParam Provincias provincias, @RequestParam String localidad, @RequestParam String direccion, @RequestParam int precioConsulta, @RequestParam Long matricula, ModelMap modelo) {
=======
    public String modificarProfesional(@PathVariable String id, /*@RequestParam MultipartFile archivo,*/ @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam(required = false) Long DNI, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password, @RequestParam String password2,
            @RequestParam Especialidad especialidad, @RequestParam Long matricula, ModelMap modelo) throws MiException {
>>>>>>> alejandrod

        Date fechaDeNacimiento;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "la fecha no puede venir vacía");
            return "redirect:/profesional/modificar/{id}";
        }

        try {

<<<<<<< HEAD
<<<<<<< HEAD
            profesionalServicio.modificarProfesional(id, archivo, nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2, true, especialidad, provincias, localidad, direccion, matricula, precioConsulta);
=======
            profesionalServicio.modificarProfesional(id, archivo, nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2, true, especialidad, provincias, localidad, direccion, matricula, /*horariosAtencion,*/ precioConsulta);
>>>>>>> botonModificarAdmin
            modelo.put("exito", "Profesional modificado con exito");
=======
            profesionalServicio.modificarProfesional(id,/* archivo,*/ nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2, true, especialidad, matricula);
            modelo.put("exito", "el profesional fue modificado con exito");
//            return "redirect:/panelAdmin/profesionalList";
            return "redirect:/panelAdmin/profesionalList";
>>>>>>> alejandrod

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
<<<<<<< HEAD
            return "redirect:/modificar/{id}";

        }
        return "redirect:../../listaProfesionales";
=======
            return "modificarProfesional.html";

        }
        
    }


    
 @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarProfesional(@PathVariable String id, ModelMap modelo) {
        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);
        
        Profesional profesional = profesionalServicio.getOne(id);

        modelo.addAttribute("profesional", profesional);

        return "eliminarProfesional.html";
>>>>>>> alejandrod
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminarProfesional(@PathVariable String id, /*@RequestParam MultipartFile archivo,*/ @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam(required = false) Long DNI, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password, @RequestParam String password2,
            @RequestParam Especialidad especialidad, @RequestParam Long matricula, ModelMap modelo) throws MiException {

        Date fechaDeNacimiento;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "la fecha no puede venir vacía");
            return "redirect:/profesional/registro";
        }

        profesionalServicio.eliminarProfesional(id,/* archivo,*/ nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2, true, especialidad, matricula);
        return "redirect:/panelAdmin/profesionalList";   
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
