package com.egg.appsalud.controladores;

import com.egg.appsalud.Enumeracion.Provincias;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Consulta;
import com.egg.appsalud.entidades.FichaMedica;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.repositorios.ConsultaRepositorio;
import com.egg.appsalud.repositorios.FichaMedicaRepositorio;
import com.egg.appsalud.repositorios.PacienteRepositorio;
import com.egg.appsalud.servicios.ConsultaServicio;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.egg.appsalud.servicios.FichaMedicaServicio;
import com.egg.appsalud.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/consulta")
public class ConsultaControlador {

    @Autowired
    PacienteRepositorio pacienteRepositorio;
    @Autowired
    PacienteServicio pacienteServicio;
    @Autowired
    FichaMedicaServicio fichaMedicaServicio;
    @Autowired
    private ConsultaServicio cs;
    @Autowired
    private ConsultaRepositorio cr;

    @GetMapping("/crear/{id}")
    public String crearConsulta(@PathVariable String id, ModelMap modelo) {

        Paciente paciente = pacienteServicio.getOne(id);
        FichaMedica fichaMedica = fichaMedicaServicio.buscarPorIdPaciente(id);

        modelo.put("fichaMedica",fichaMedica);

        modelo.put("paciente", paciente);


        return "profesional_consulta";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN', 'ROLE_PACIENTE')")
    @PostMapping("/crear/{id}")
    public String crearConsulta(@PathVariable String id, HttpSession session,
                                String obraSocial, Long afiliado,
                                @RequestParam String antecedentes, @RequestParam String grupoSanguineo,
                                @RequestParam Double altura, @RequestParam Double peso,
                                String observaciones, @RequestParam String diagnostico,
                                @RequestParam String tratamiento, ModelMap modelo) {

        Paciente paciente = pacienteServicio.getOne(id);
        Profesional profesional = (Profesional) session.getAttribute("session");

        Date fecha = new Date();
        LocalTime horario = LocalTime.now();

        try {
            cs.crearConsulta(paciente, profesional, obraSocial, afiliado, antecedentes, grupoSanguineo, altura, peso,
                    observaciones, diagnostico, tratamiento, fecha, horario);
            modelo.put("exito", "La consulta fue creada con exito");
            return "redirect:/profesional/citasProfesional";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("paciente", pacienteServicio.getOne(id));
            return "redirect:/consulta/crear/{id}";
        }
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listarConsultas() {
        List<Consulta> consultas = cs.listarConsultas();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificarConsulta(@PathVariable String id, ModelMap modelo) {
        new Consulta();

        Consulta consulta = cs.getOne(id);
        modelo.addAttribute("consulta", consulta);
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificarConsulta(@PathVariable String id, @RequestParam Paciente paciente, @RequestParam Profesional profesional,
                                    @RequestParam("fechaDeConsulta") String fechaDeConsultaStr, @RequestParam Provincias provincias, @RequestParam String localidad, @RequestParam String direccion,
                                    @RequestParam int precioConsulta, @RequestParam int valoracion, ModelMap modelo) {

        try {
            cs.modificarConsulta(id, paciente, profesional, provincias, localidad, direccion, precioConsulta);
            modelo.put("exito", "Consulta modificada con exito");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return null;
        }
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminarConsulta(@PathVariable String id) {
        cr.deleteById(id);
        return null;
    }
}
