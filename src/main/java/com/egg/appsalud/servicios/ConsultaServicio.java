package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumeracion.Provincias;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Consulta;
import com.egg.appsalud.entidades.FichaMedica;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.repositorios.ConsultaRepositorio;

import java.time.LocalTime;
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

    @Autowired
    private FichaMedicaServicio fichaMedicaServicio;

    @Autowired
    private PacienteServicio pacienteServicio;


    @Transactional
    public void crearConsulta(Paciente paciente, Profesional profesional, String obraSocial, Long afiliado, String antecedentes, String grupoSanguineo, Double altura,
                              Double peso, String observaciones, String diagnostico, String tratamiento, Date fecha, LocalTime horario) throws MiException {

//        validar(paciente, profesional);


        Consulta consulta = new Consulta();

        consulta.setPaciente(paciente);
        consulta.setProfesional(profesional);
        consulta.setFechaDeConsulta(fecha);
        consulta.setHoraInicio(horario);
        consulta.setDiagnostico(diagnostico);
        consulta.setTratamiento(tratamiento);
        consulta.setObservaciones(observaciones);

        fichaMedicaServicio.modificarFichaMedica(paciente, antecedentes, obraSocial, afiliado, grupoSanguineo, altura, peso);
        consultaRepositorio.save(consulta);

    }

    @Transactional
    public void modificarConsulta(String id, Paciente paciente, Profesional profesional, Provincias provincias, String localidad, String direccion, int precioConsulta) throws MiException {
        validar(paciente, profesional);

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


    private void validar(Paciente paciente, Profesional profesional) throws MiException {
        if (paciente == null) {
            throw new MiException("El paciente  no puede ser Nulo");
        }

        if (profesional == null) {
            throw new MiException("El profesional no puede ser nulo");
        }
    }


    public List<Consulta> listarConsultas() {

        return consultaRepositorio.findAll();
    }

    public Consulta getOne(String id) {
        return consultaRepositorio.getOne(id);
    }
}
