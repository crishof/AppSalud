package com.egg.appsalud.servicios;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.repositorios.PacienteRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteServicio {
    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @Transactional
    public void crearPaciente(String nombre) throws MiException {

        validar(nombre);

        Paciente paciente = new Paciente();

        paciente.setNombre(nombre);

        pacienteRepositorio.save(paciente);

    }

    @Transactional(readOnly = true)
    public List<Paciente> listarPacientes() {

        List<Paciente> pacientes = new ArrayList();

        pacientes = pacienteRepositorio.findAll();

        return pacientes;
    }

    @Transactional
    public void modificarPacientes(String nombre, String id) throws MiException {

        validar(nombre);

        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();

            paciente.setNombre(nombre);

            pacienteRepositorio.save(paciente);

        }
    }

    @Transactional(readOnly = true)
    public Paciente getOne(String id) {
        return pacienteRepositorio.getOne(id);
    }


    @Transactional
    public void eliminar(String id) throws MiException {

        Paciente paciente = pacienteRepositorio.getById(id);

        pacienteRepositorio.delete(paciente);

    }

    private void validar(String nombre) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
    }
}
