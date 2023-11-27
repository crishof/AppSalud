package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumeracion.Provincias;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Consulta;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.repositorios.ConsultaRepositorio;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaServicio {

    @Autowired
    private ConsultaRepositorio consultaRepositorio;


    @Transactional
    public void crearConsulta(Paciente paciente, Profesional profesional, Provincias provincias, String localidad, String direccion, int precioConsulta) throws MiException {

        validar(paciente, profesional, provincias, localidad, direccion, precioConsulta);

        Consulta consulta = new Consulta();

        consulta.setPaciente(paciente);
        consulta.setProfesional(profesional);
        consulta.setProvincias(provincias);
        consulta.setLocalidad(localidad);
        consulta.setDireccion(direccion);
        consulta.setPrecioConsulta(precioConsulta);
        consulta.setFechaDeConsulta(new Date());
        consultaRepositorio.save(consulta);

    }

    @Transactional
    public void modificarConsulta(String id, Paciente paciente, Profesional profesional, Provincias provincias, String localidad, String direccion, int precioConsulta) throws MiException {
        validar(paciente, profesional, provincias, localidad, direccion, precioConsulta);

        Optional<Consulta> respuesta = consultaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Consulta consulta = respuesta.get();
            consulta.setPaciente(paciente);
            consulta.setProfesional(profesional);
            consulta.setProvincias(provincias);
            consulta.setLocalidad(localidad);
            consulta.setDireccion(direccion);
            
            consulta.setPrecioConsulta(precioConsulta);
            consulta.setFechaDeConsulta(new Date());
            consultaRepositorio.save(consulta);
        }

    }

    @Transactional
    public void eliminarConsulta(String id) {
        consultaRepositorio.deleteById(id);
    }


    private void validar(Paciente paciente, Profesional profesional, Provincias provincias, String localidad, String direccion, int precioConsulta) throws MiException {
        if (paciente == null) {
            throw new MiException("El paciente  no puede ser Nulo");
        }

        if (profesional == null) {
            throw new MiException("El profesional no puede ser nulo");
        }

        if (provincias == null) {
            throw new MiException("La provincia no puede ser nulo");
        }
        if (localidad == null) {
            throw new MiException("La localidad no puede ser nulo");
        }
        if (direccion == null) {
            throw new MiException("La direccion no puede ser nulo");
        }

        if (precioConsulta <= 0) {
            throw new MiException("El precio de la consulta no puede ser 0");
        }

    }


    public List<Consulta> listarConsultas() {

        return consultaRepositorio.findAll();
    }

    public Consulta getOne(String id) {
        return consultaRepositorio.getOne(id);
    }
}
