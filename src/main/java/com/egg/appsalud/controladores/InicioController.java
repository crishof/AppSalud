package com.egg.appsalud.controladores;

import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.Enumeracion.Provincias;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.servicios.ProfesionalServicio;
import com.egg.appsalud.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class InicioController {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ProfesionalServicio profesionalServicio;

    @GetMapping("/registroPaciente")
    public String registroPaciente() {
        return "paciente_registro";
    }

    @PostMapping("/registrarPaciente")
    public String registrarPaciente(/*@RequestParam MultipartFile archivo,*/@RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido, @RequestParam(required = false) Long dni, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo) throws MiException, ParseException {
        Date fechaDeNacimiento;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "la fecha no puede venir vacía");
            return "redirect:/portal/registroUsuario";
        }

        try {

            usuarioServicio.crearUsuario(/*archivo, */nombreUsuario, nombre, apellido, dni, fechaDeNacimiento, email, password, password2);

            modelo.put("exito", "el usuario fue creado con exito");
            return "index";
        } catch (MiException e) {

            modelo.put("error", e.getMessage());

            return "redirect:/portal/registroUsuario";
        }
    }

    @GetMapping("/registroProfesional")
    public String registroProfesional(ModelMap modelo) {

        Especialidad[] especialidades = Especialidad.values();
        Provincias[] provincias = Provincias.values();
        modelo.addAttribute("provincias",provincias);
        modelo.addAttribute("especialidades", especialidades);
        return "profesional_registro";
    }

    @PostMapping("/registrarProfesional")
    public String registrarProfesional(MultipartFile archivo, @RequestParam String nombreUsuario, @RequestParam String nombre,
                                       @RequestParam String apellido, @RequestParam(required = false) Long dni, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr,
                                       @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam(required = false) Long matricula,
            /*List<ObraSocial> obrasocial,*/ @RequestParam Especialidad especialidad, @RequestParam Provincias provincias, @RequestParam String localidad, @RequestParam String direccion, @RequestParam Set<String> horariosAtencion,
                                       @RequestParam int precioConsulta, ModelMap modelo) throws MiException, ParseException {

        System.out.println("EJECUTANDO POST REGISTRAR");
        Date fechaDeNacimiento;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);

        } catch (ParseException p) {
            modelo.put("error", "la fecha no puede venir vacía");
            return "redirect:/profesional/registrar";
        }

        try {
            profesionalServicio.crearProfesional( archivo, nombreUsuario, password, password2, nombre, apellido, email,
                    fechaDeNacimiento, dni, especialidad, provincias, localidad, direccion, matricula, horariosAtencion, precioConsulta/*, obrasocial*/);
            modelo.put("exito", "el profesional fue creado con exito");
            return "index";
        } catch (MiException e) {

            modelo.put("error", e.getMessage());

            return "redirect:/profesional/registrar";
        }
    }

    @GetMapping("/listaProfesionales")
    public String listarProfesionales(@Param("especialidad") String especialidad, 
            @Param("columna") String columna, ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesional(especialidad, columna);
        modelo.addAttribute("profesional", profesionales);

        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);
        modelo.addAttribute("valorSeleccionado", especialidad);
        return "profesional_lista";
    }
    
    

    @GetMapping("/terminos")
    public String terminos() {
        return "terminos";
    }

    @GetMapping("/privacidad")
    public String privacidad() {
        return "privacidad";
    }
}
