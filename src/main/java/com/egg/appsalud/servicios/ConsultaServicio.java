package com.egg.appsalud.servicios;

import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Consulta;
import com.egg.appsalud.entidades.Establecimiento;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.repositorios.ConsultaRepositorio;
import java.util.ArrayList;
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
    public void crearConsulta(Paciente paciente, Profesional profesional, Establecimiento establecimiento, int precioConsulta) throws MiException {

        validar(paciente, profesional, establecimiento, precioConsulta);

        Consulta consulta = new Consulta();

        consulta.setPaciente(paciente);
        consulta.setProfesional(profesional);
        consulta.setEstablecimiento(establecimiento);
        consulta.setPrecioConsulta(precioConsulta);
        consulta.setFechaDeConsulta(new Date());
        consultaRepositorio.save(consulta);

    }

    @Transactional
    public void modificarConsulta(String id, Paciente paciente, Profesional profesional, Establecimiento establecimiento, int precioConsulta) throws MiException {
        validar(paciente, profesional, establecimiento, precioConsulta);

        Optional<Consulta> respuesta = consultaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Consulta consulta = respuesta.get();
            consulta.setPaciente(paciente);
            consulta.setProfesional(profesional);
            consulta.setEstablecimiento(establecimiento);
            consulta.setPrecioConsulta(precioConsulta);
            consulta.setFechaDeConsulta(new Date());
            consultaRepositorio.save(consulta);
        }

    }
    
    @Transactional
    public void eliminarConsulta(String id){
        consultaRepositorio.deleteById(id);
    }
    
    

    private void validar(Paciente paciente, Profesional profesional, Establecimiento establecimiento, int precioConsulta) throws MiException {
        if (paciente == null) {
            throw new MiException("El paciente  no puede ser Nulo");
        }

        if (profesional == null) {
            throw new MiException("El profesional no puede ser nulo");
        }

        if (establecimiento == null) {
            throw new MiException("El establecimiento no puede ser nulo");
        }

        if (precioConsulta <= 0) {
            throw new MiException("El precio de la consulta no puede ser 0");
        }

    }

    
        public List<Consulta> listarConsultas() {

        List<Consulta> consultas = new ArrayList();
        consultas = consultaRepositorio.findAll();

        return consultas;
    }
        
        public Consulta getOne(String id){
        return consultaRepositorio.getOne(id);
    }
}
