package com.egg.appsalud.controladores;

import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/historia_clinica")
public class FichaMedicaControlador {

    @Autowired
    PacienteServicio pacienteServicio;


    @GetMapping("/ficha_medica")
    public String verFichaMedica( HttpSession session, ModelMap model){

        Paciente pacienteActualizado = (Paciente) session.getAttribute("pacienteActualizado");
        session.removeAttribute("pacienteActualizado");
        model.addAttribute("paciente", pacienteActualizado);


        return "paciente_fichaMedica";

    }
    
}
