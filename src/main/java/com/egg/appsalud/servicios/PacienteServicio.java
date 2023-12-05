package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumeracion.Rol;
import com.egg.appsalud.Exception.MiException;
<<<<<<< HEAD
import com.egg.appsalud.entidades.FichaMedica;
import com.egg.appsalud.entidades.Imagen;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.repositorios.FichaMedicaRepositorio;
=======
import com.egg.appsalud.entidades.Imagen;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Usuario;
>>>>>>> botonModificarAdmin
import com.egg.appsalud.repositorios.PacienteRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PacienteServicio {
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @Autowired
    ImagenServicio imagenServicio;

    @Autowired
    UtilServicio utilServicio;

    @Autowired
    FichaMedicaRepositorio fichaMedicaRepositorio;

    @Transactional
<<<<<<< HEAD
    public void crearPaciente(MultipartFile archivo, String nombreUsuario, String nombre, String apellido,
                              Long DNI, Date fechaDeNacimiento, String email, String password, String password2) throws MiException {

        utilServicio.validar(nombreUsuario, password, password2, nombre, apellido, fechaDeNacimiento, DNI, email);
=======
        public void crearPaciente(MultipartFile archivo, String nombreUsuario, String nombre, String apellido,
                             Long DNI, Date fechaDeNacimiento, String email, String password, String password2) throws MiException {

        validar(nombreUsuario, password, password2, nombre, apellido, fechaDeNacimiento, DNI, email);
>>>>>>> botonModificarAdmin

        System.out.println("EJECUTANDO  servicio crearUsuario REGISTRAR");
        Paciente paciente = new Paciente();

<<<<<<< HEAD
=======
        Imagen imagen = imagenServicio.guardar(archivo);
        
>>>>>>> botonModificarAdmin
        paciente.setNombreUsuario(nombreUsuario);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        paciente.setDNI(DNI);
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setFechaDeNacimiento(fechaDeNacimiento);
        paciente.setEmail(email);
        paciente.setFechaDeAlta(new Date());
        paciente.setRol(Rol.PACIENTE);
        paciente.setActivo(true);
<<<<<<< HEAD

        Imagen imagen = imagenServicio.guardar(archivo);

        paciente.setImagen(imagen);
=======
        paciente.setImagen(imagen);
        
        
>>>>>>> botonModificarAdmin

        pacienteRepositorio.save(paciente);


    }

    @Transactional(readOnly = true)
    public List<Paciente> listarPacientes() {

        return pacienteRepositorio.findAll();
    }

    @Transactional
<<<<<<< HEAD
    public void modificarPacientes(MultipartFile archivo, String id, String nombreUsuario, String nombre, String apellido,
                                   Long DNI, Date fechaNacimiento, String email, String password, String password2) throws MiException {

        utilServicio.validar(nombreUsuario, password, password2, nombre, apellido, fechaNacimiento, DNI, email);
=======
    public void modificarPacientes(String id, MultipartFile archivo, String nombreUsuario, String nombre, String apellido,
                                   Long DNI, Date fechaNacimiento,String email, String password, String password2) throws MiException {

        validar(nombreUsuario, password, password2, nombre, apellido, fechaNacimiento, DNI, email);
>>>>>>> botonModificarAdmin

        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            new BCryptPasswordEncoder().encode(password);
            paciente.setEmail(email);
            paciente.setFechaDeNacimiento(fechaNacimiento);
            paciente.setNombreUsuario(nombreUsuario);

<<<<<<< HEAD
            Imagen imagen = imagenServicio.guardar(archivo);

            paciente.setImagen(imagen);

=======
            String idImagen = null;
            
            if(paciente.getImagen() != null){
                idImagen = paciente.getImagen().getId();
            }
            
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
>>>>>>> botonModificarAdmin
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

<<<<<<< HEAD
=======
    private void validar(String nombreUsuario, String password, String password2, String nombre, String apellido, Date fechaDeNacimiento, Long DNI, String email) throws MiException {


        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El nombre de usuario no puede estar vacio o Nulo");

        }


        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }

        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede estar vacío o ser nulo");
        }

        if (DNI == null) {
            throw new MiException("El DNI no puede ser nulo");
        }

        if (fechaDeNacimiento == null) {
            throw new MiException("La fecha de nacimiento no puede ser nula");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede estar vacío o ser nulo");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("Las contraseñas no pueden estar vacias y tener menos de 5 caracteres ");
        }

        if (!password.equals(password2)) {
            throw new MiException("las contraseñas deben coincidir");
        }


    }
>>>>>>> botonModificarAdmin
}

