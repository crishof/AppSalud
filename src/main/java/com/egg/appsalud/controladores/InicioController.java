package com.egg.appsalud.controladores;

import com.egg.appsalud.Enumeracion.Especialidad;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InicioController {

    @GetMapping("/registroPaciente")
    public String registroPaciente() {
        return "paciente_registro";
    }

    @GetMapping("/registroProfesional")
    public String registroProfesional(ModelMap modelo) {

        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades", especialidades);
        return "profesional_registro";
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
