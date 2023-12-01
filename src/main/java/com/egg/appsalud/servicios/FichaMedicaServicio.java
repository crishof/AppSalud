package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.FichaMedica;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.repositorios.FichaMedicaRepositorio;
import org.springframework.stereotype.Service;


@Service
public class FichaMedicaServicio {

    final
    FichaMedicaRepositorio fichaMedicaRepositorio;

    public FichaMedicaServicio(FichaMedicaRepositorio fichaMedicaRepositorio) {
        this.fichaMedicaRepositorio = fichaMedicaRepositorio;
    }

    public void crearFichaMedica(Paciente paciente, String antecedentes, String obraSocial, Long afiliado,
                                 String grupoSanguineo, double altura, double peso) {
        System.out.println("EJECUTANDO CREAR FICHA MEDICA");
        FichaMedica ficha = new FichaMedica(paciente, antecedentes, obraSocial, afiliado, grupoSanguineo, altura, peso);
        fichaMedicaRepositorio.save(ficha);
    }
}
