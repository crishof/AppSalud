package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumeracion.Rol;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.entidades.Imagen;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio ur;

    @Autowired
    private ImagenServicio imagenServicio;


    @Transactional
    public void crearUsuario(/*MultipartFile archivo, */String nombreUsuario, String nombre, String apellido,
                                                        Long DNI, Date fechaDeNacimiento, String email, String password, String password2) throws MiException {

        validar(nombreUsuario, password, password2, nombre, apellido, fechaDeNacimiento, DNI, email);

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(nombreUsuario);
        usuario.setNombre(nombre);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setDNI(DNI);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setFechaDeNacimiento(fechaDeNacimiento);
        usuario.setEmail(email);
        usuario.setFechaDeAlta(new Date());
        usuario.setRol(Rol.USER);
        usuario.setActivo(true);

        /*Imagen imagen = imagenServicio.guardar(archivo);
//        compararNombre(us,nombreUsuario);

        usuario.setImagen(imagen);*/

        ur.save(usuario);


    }

    @Transactional
    public void modificarUsuario(String id, /*MultipartFile archivo,*/ String nombreUsuario, String nombre, String apellido,
                                 Long DNI, Date fechaDeNacimiento, String email, String password, String password2, boolean activo) throws MiException {

        validar(nombreUsuario, password, password2, nombre, apellido, fechaDeNacimiento, DNI, email);

        Optional<Usuario> respuesta = ur.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setDNI(DNI);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setFechaDeNacimiento(fechaDeNacimiento);
            usuario.setEmail(email);
            usuario.setFechaDeAlta(new Date());
            usuario.setRol(Rol.USER);
            usuario.setActivo(activo);

            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            
            /*Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            
            usuario.setImagen(imagen);*/
            ur.save(usuario);
        }

    }

    @Transactional
    public void eliminarUsuario(String id) {

        ur.deleteById(id);
    }

    public List<Usuario> listarUsuario(String palabra) {

        if (palabra != null) {
            return ur.buscarUsuarioPorNombre(palabra);
        }
        List<Usuario> listaUsuarios = ur.findAll();

        return listaUsuarios;

    }

    public Usuario buscarPorID(String id) {

        Optional<Usuario> oUsuario = ur.findById(id);
        Usuario Usuario = null;
        if (oUsuario.isPresent()) {

            Usuario = oUsuario.get();

        }
        return Usuario;
    }


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

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {

        Usuario u = ur.buscarPorNombre(nombreUsuario);

        if (u != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + u.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", u);
            return new User(u.getNombreUsuario(), u.getPassword(), permisos);

        } else {

            return null;
        }

    }

    public Usuario buscarPorDni(Long dni) {
        return ur.buscarPorDni(dni);
    }

    public Usuario getOne(String id) {
        return ur.getOne(id);
    }

}
