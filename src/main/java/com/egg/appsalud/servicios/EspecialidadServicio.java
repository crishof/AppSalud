package com.egg.appsalud.servicios;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Especialidad;
import com.egg.appsalud.repositorios.EspecialidadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadServicio {
    @Autowired
    private EspecialidadRepositorio especialidadRepositorio;

    public void crearEspecialidad(String nombre) throws MiException {
        validarEspecialidad(nombre);
        Especialidad especialidad = new Especialidad();
        especialidadRepositorio.save(especialidad);
    }

    public void validarEspecialidad(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre de usuario no puede estar vacio o Nulo");

        }
    }

}
