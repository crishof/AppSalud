package com.egg.appsalud.controladores;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Establecimiento;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.repositorios.ConsultaRepositorio;
import com.egg.appsalud.servicios.ConsultaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/consulta")
public class ConsultaControlador {
    
    @Autowired
    private ConsultaServicio cs;

    @Autowired
    private ConsultaRepositorio cr;
    
    
    @GetMapping("/crear")
    public String crearConsulta(ModelMap modelo){
        return null;  
    }
    
    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN', 'ROLE_PACIENTE')")
    @PostMapping("/crear")
    public String crearConsulta(@RequestParam Paciente paciente,@RequestParam Profesional profesional,@RequestParam Establecimiento establecimiento,@RequestParam int precioConsulta, ModelMap modelo) throws MiException {
        try{
            cs.crearConsulta(paciente, profesional, establecimiento, precioConsulta);
            modelo.put("exito", "La consulta fue creada con exito");
            return "index.html";
        }catch(MiException e){
            modelo.put("error", e.getMessage());

            return "redirect:/consulta/crear";
        }  
    }
    
    
    
}
