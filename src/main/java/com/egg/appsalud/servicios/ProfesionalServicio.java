package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.Enumeracion.Rol;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Consulta;

import com.egg.appsalud.entidades.Establecimiento;
import com.egg.appsalud.entidades.FichaMedica;
import com.egg.appsalud.entidades.Imagen;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    @Transactional
    public void crearProfesional(String id, Especialidad especialidad) throws MiException {

        //validarProfesional();

        Usuario usuario = buscarUsuarioPorID(id);
        Profesional profesional = new Profesional();

        profesional.setNombreUsuario(usuario.getNombreUsuario());
        profesional.setPassword(usuario.getPassword());
        profesional.setFechaDeAlta(usuario.getFechaDeAlta());
        
        profesional.setRol(Rol.PROFESIONAL);

        profesional.setNombre(usuario.getNombre());
        profesional.setApellido(usuario.getApellido());
        profesional.setEmail(usuario.getEmail());
        profesional.setFechaDeNacimiento(usuario.getFechaDeNacimiento());
        profesional.setActivo(usuario.getActivo());
        profesional.setDNI(usuario.getDNI());
        //profesional.setImagen(usuario.getImagen());
        profesional.setEspecialidad(especialidad);


        usuarioRepositorio.delete(usuario);
        profesionalRepositorio.save(profesional);
    }

    @Transactional
    public void modificarProfesional(String id, /*MultipartFile archivo, */ String nombreUsuario, String nombre, String apellido,
                                     Long DNI, Date fechaDeNacimiento, String email, String password, String password2, boolean activo) throws MiException {

        validar(nombreUsuario, password, password2, nombre, apellido, fechaDeNacimiento, DNI, email);
        //validarProfesional(especialidad);

        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            profesional.setNombre(nombre);
            profesional.setPassword(new BCryptPasswordEncoder().encode(password));
            profesional.setDNI(DNI);
            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setFechaDeNacimiento(fechaDeNacimiento);
            profesional.setEmail(email);
            profesional.setFechaDeAlta(new Date());
            profesional.setActivo(activo);
            profesional.setNombreUsuario(nombreUsuario);
            
            /*String idImagen = null;
            
            if(profesional.getImagen() != null){
                idImagen = profesional.getImagen().getId();
            }
            
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            
            profesional.setEspecialidad(especialidad);*/

            profesionalRepositorio.save(profesional);

        }

    }


    @Transactional
    public void eliminarProfesional(String id) {
        profesionalRepositorio.deleteById(id);
    }


    public Usuario buscarUsuarioPorID(String id) {

        Optional<Usuario> oUsuario = usuarioRepositorio.findById(id);
        Usuario Usuario = null;
        if (oUsuario.isPresent()) {

            Usuario = oUsuario.get();

        }
        return Usuario;
    }

    public List<Profesional> listarProfesional() {

        List<Profesional> profesional = new ArrayList();
        profesional = profesionalRepositorio.findAll();

        return profesional;
    }

    public void validar(String nombreUsuario, String password, String password2, String nombre, String apellido, Date fechaDeNacimiento, Long DNI, String email) throws MiException {

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

    /*public void validarProfesional(Especialidad especialidad) throws MiException {
        if (especialidad == null) {
            throw new MiException("La especialidad no puede ser nulo");
        }

    }*/


}
