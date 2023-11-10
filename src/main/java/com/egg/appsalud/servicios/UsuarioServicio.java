package com.egg.appsalud.servicios;

import com.egg.appsalud.Enumeracion.Rol;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Especialidad;
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
public class UsuarioServicio implements UserDetailsService{
        @Autowired
    private UsuarioRepositorio ur;
    
    @Autowired
    private ImagenServicio imagenServicio;

    
     @Transactional
    public void crearUsuario(MultipartFile archivo, String nombreUsuario, String password, String password2, Long DNI, String nombre, String apellido, Date fechaNacimiento, String email) throws MiException {
         
        Usuario us = new Usuario();
        
        
        validar(nombreUsuario, password, password2, DNI, nombre, apellido, fechaNacimiento, email);

        us.setNombreUsuario(nombreUsuario);
        us.setPassword(new BCryptPasswordEncoder().encode(password));
        us.setDNI(DNI);
        us.setNombre(nombre);
        us.setApellido(apellido);
        us.setFechaNacimiento(fechaNacimiento);
        us.setEmail(email);
        us.setRol(Rol.USER);
        us.setFechaDeAlta(new Date());
        us.setActivo(true);
        
        
        Imagen imagen=imagenServicio.guardar(archivo);
        us.setImagen(imagen);

        ur.save(us);
        
    }

    @Transactional
    public void modificarUsuario(Usuario us){
        
        ur.save(us);
        
    }
    
    public void eliminarUsuario(String id){
        
        ur.deleteById(id);
    }
    
    
    public ArrayList<Usuario> listarUsuario(){
        
        ArrayList<Usuario> listaUsuarios=(ArrayList<Usuario>) ur.findAll();
        
        return listaUsuarios;
        
        
    }
    
    
    
    public Usuario buscarPorID(String id){
        
        Optional<Usuario> oUsuario = ur.findById(id);
        Usuario Usuario = null;
        if (oUsuario.isPresent()) {

            Usuario = oUsuario.get();

        }
        return Usuario;
    }
        
          @Transactional
    public void crearProfesional(String id, Especialidad especialidad) throws MiException{
             
        Usuario u = buscarPorID(id);
        Profesional p = new Profesional();
        
        p.setId("");
        p.setNombreUsuario(u.getNombreUsuario());
        p.setPassword(u.getPassword());
        p.setFechaDeAlta(u.getFechaDeAlta());
        p.setEspecialidad(especialidad);

        validarProfesional(especialidad);
        
        ur.delete(u);
        ur.save(p);
    }
    
    public void validarProfesional(Especialidad especialidad)throws MiException{
       if (especialidad == null){
           throw new MiException("Especialidad no puede estar vacio o Nulo");
       }
           
    }
    
    
    @Transactional
    public void modificarProfesional(String id, Integer sueldoMensual, Boolean activo) throws MiException{
             
        Usuario u = buscarPorID(id); 
        Profesional p = new Profesional();
        
        p.setId(u.getId());
        p.setNombreUsuario(u.getNombreUsuario());
        p.setPassword(u.getPassword());
        p.setFechaDeAlta(u.getFechaDeAlta());
        p.setActivo(activo);
        ur.save(p);
    }
    
    public List<Usuario> listarProfesional() {
        List<Usuario> profesional = new ArrayList();
        profesional = ur.buscarProfesional();
        System.out.println(profesional.toString());
    return profesional;
    
    }    

    private void validar(String nombreUsuario, String password, String password2, Long DNI, String nombre, String apellido, Date fechaNacimiento, String email) throws MiException {

        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El nombre de usuario no puede estar vacio o Nulo");

        }
 
      if (password.isEmpty() || password==null || password.length()<=5) {
            throw new MiException("Las contraseñas no pueden estar vacias y tener menos de 5 caracteres ");
      }
      
      if(!password.equals(password2)){
          throw new MiException("las contraseñas deben coincidir");
      }
        
      
        if (DNI == null) {
            throw new MiException("El DNI del usuario no puede estar vacio o Nulo");
        }
      
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre de usuario no puede estar vacio o Nulo");
        }
        
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido de usuario no puede estar vacio o Nulo");
        }
      
        if (fechaNacimiento == null) {
            throw new MiException("La fecha de nacimiento del usuario no puede ser menor a cero o Nulo");
        }
      
        if (email.isEmpty() || email == null) {
            throw new MiException("El email de usuario no puede estar vacio o Nulo");

        }
    }
    
    
    

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {

        Usuario u = ur.buscarPorNombre(nombreUsuario);

        if (u != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + u.getRol().toString());

            permisos.add(p);
            
            ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session= attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", u);
            User user = new User(u.getNombreUsuario(), u.getPassword(), permisos);

            return user;
        } else {
            return null;
        }

    }
}
