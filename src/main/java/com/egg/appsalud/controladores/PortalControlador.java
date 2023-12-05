package com.egg.appsalud.controladores;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.repositorios.UsuarioRepositorio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import com.egg.appsalud.servicios.UsuarioServicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portal")
public class PortalControlador {

    @Autowired
    public UsuarioServicio us;

    @Autowired
    public ProfesionalServicio profesionalServicio;

    @Autowired
    public UsuarioRepositorio ur;

<<<<<<< HEAD
=======
    @GetMapping("/registroUsuario")
    public String registroUsuario() {
        return "registroUsuario";
    }

    @PostMapping("/registrar")
    public String crearUsuario(/*@RequestParam MultipartFile archivo,*/@RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido, @RequestParam(required = false) Long dni, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo) throws MiException, ParseException {
        Date fechaDeNacimiento;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "la fecha no puede venir vacía");
            return "redirect:/portal/registroUsuario";
        }

        try {

            us.crearUsuario(/*archivo, */nombreUsuario, nombre, apellido, dni, fechaDeNacimiento, email, password, password2);

            modelo.put("exito", "el usuario fue creado con exito");
            return "index";
        } catch (MiException e) {

            modelo.put("error", e.getMessage());

            return "redirect:/portal/registroUsuario";
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @GetMapping("/registro")
    public String registro(ModelMap modelo) {

        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);
        return "registroProfesional.html    ";
    }

    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/registro")
    public String registrarProfesional(/*@RequestParam MultipartFile archivo,*/@RequestParam String nombreUsuario, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam(required = false) Long dni, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam(required = false) Long matricula,
            /*List<ObraSocial> obrasocial, @RequestParam Establecimiento establecimiento,*/ @RequestParam Especialidad especialidad, ModelMap modelo) throws MiException, ParseException {

        Date fechaDeNacimiento;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "la fecha no puede venir vacía");
            return "registroProfesional.html";
        }

        try {
            profesionalServicio.crearProfesional(nombreUsuario, password, password2, nombre, apellido, email, fechaDeNacimiento, dni, especialidad, matricula/*, obrasocial*/);
            modelo.put("exito", "el profesional fue creado con exito");
            return "/login";
        } catch (MiException e) {

            modelo.put("error", e.getMessage());

            //return "redirect:/profesional/registro";
            return "registroProfesional.html";
        }
    }
>>>>>>> alejandrod

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "El usuario o la contraseña son incorrectos");
        }
        return "login";
    }

    @GetMapping("/restablecerContrasena")
    public String restablecer(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "El usuario o la contraseña son incorrectos");
        }
        return "restablecer_contrasena";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificarUsuario(@PathVariable String id, ModelMap modelo) {
        Usuario usuario = new Usuario();
        usuario = us.getOne(id);
        modelo.addAttribute("usuario", usuario);
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificarUsuario(@RequestParam String id, /*MultipartFile archivo,*/@RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido, @RequestParam(required = false) Long DNI, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam boolean activo, ModelMap modelo) throws MiException {

        Date fechaDeNacimiento;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "la fecha no puede venir vacía");
            return "redirect:/portal/registroUsuario";
        }

        try {

            us.modificarUsuario(id,/* archivo,*/  nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2, true);
            modelo.put("exito", "Usuario modificado con exito");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return null;
        }
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id) {
        ur.deleteById(id);
        return "index";
    }
}
