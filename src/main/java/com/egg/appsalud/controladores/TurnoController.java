package com.egg.appsalud.controladores;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Turno;
import com.egg.appsalud.servicios.ProfesionalServicio;
import com.egg.appsalud.servicios.TurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private TurnoServicio turnoServicio;

    @PreAuthorize("hasAnyRole('PACIENTE','ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/listar")
    public String obtenerTodosLosTurnos(ModelMap model, HttpSession session) {
        // Verifica si el usuario está autenticado utilizando la sesión
        if (session.getAttribute("usuariosession") == null) {
            // Si no está autenticado, redirige al usuario a la página de inicio de sesión
            return "login";
        } else {
            var turno = turnoServicio.obtenerTodosLosTurnos();
            model.addAttribute("turnos", turno);
            return "turno_List";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/listar/{profesionalId}")
    public String obtenerTurnosDeProfesional(@PathVariable("profesionalId") String profesionalId, ModelMap model, HttpSession session) {
        // Verifica si el usuario está autenticado utilizando la sesión
        if (session.getAttribute("usuariosession") == null) {
            // Si no está autenticado, redirige al usuario a la página de inicio de sesión
            return "login";
        } else {
            // Recupera el profesional por su ID
            var profesional = profesionalServicio.buscarUsuarioPorID(profesionalId);

            if (profesional != null) {
                // Utiliza el servicio para obtener los turnos del profesional y agregarlos al modelo
                List<Turno> turnos = turnoServicio.obtenerTurnosDeProfesionalOrdenados((Profesional) profesional);
                model.addAttribute("turnos", turnos);
                model.addAttribute("profesional", profesional);

                return "turno_List";
            } else {
                // El profesional no se encontró, maneja la lógica adecuada aquí
                return "error";
            }
        }
    }

    @PostMapping("/agendar-turno")
    public String agendarTurno(@RequestParam String turnoId, HttpSession session, ModelMap modelo) {
        try {
            // Verifica si el usuario está autenticado utilizando la sesión
            Paciente paciente = (Paciente) session.getAttribute("usuariosession");
            if (paciente == null) {
                modelo.addAttribute("error", "Debes estar logueado para agendar un turno.");
                return "login";
            }
            // Llama al servicio para asignar el turno al paciente
            turnoServicio.asignarTurnoAPaciente(turnoId, paciente);
            modelo.addAttribute("exito", "Turno agendado correctamente.");
            // Si no hay URL original, redirige al usuario a la página de lista de turnos
            return "redirect:/paciente/citas";
        } catch (Exception ex) {
            modelo.addAttribute("error", "Error al agendar el turno: " + ex.getMessage());
            return "turno_List"; // Otra página de error o manejo de errores según tus necesidades
        }
    }

    @GetMapping("/mis_turnos")
    public String obtenerMisTurnos(ModelMap model, HttpSession session) {
        // Verifica si el usuario está autenticado como paciente
        Paciente paciente = (Paciente) session.getAttribute("usuariosession");
        if (paciente == null) {
            // Si no está autenticado, redirige al usuario a la página de inicio de sesión
            return "login";
        }

        // Recupera los turnos del paciente desde la base de datos
        List<Turno> misTurnos = turnoServicio.obtenerTurnosDelPaciente(paciente);
        model.addAttribute("misTurnos", misTurnos);

        return "mis_turnos";
    }

    @PreAuthorize("hasRole('PROFESIONAL')")
    @GetMapping("/mis_turnos_profesional")
    public String obtenerMisTurnosProfesional(ModelMap model, HttpSession session) {
        // Verifica si el usuario está autenticado como paciente
        Profesional profesional = (Profesional) session.getAttribute("usuariosession");
        if (profesional == null) {
            // Si no está autenticado, redirige al usuario a la página de inicio de sesión
            return "login";
        }

        // Recupera los turnos del paciente desde la base de datos
        List<Turno> misTurnosProfesional = turnoServicio.obtenerTurnosDeProfesional(profesional);
        model.addAttribute("misTurnosProfesional", misTurnosProfesional);

        return "mis_turnos_profesional";
    }

}//Class
