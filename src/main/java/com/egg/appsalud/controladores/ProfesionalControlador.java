package com.egg.appsalud.controladores;

import com.egg.appsalud.Exception.MiException;

import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.entidades.Establecimiento;
import com.egg.appsalud.entidades.ObraSocial;
import com.egg.appsalud.repositorios.ProfesionalRepositorio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import com.egg.appsalud.servicios.UsuarioServicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @GetMapping("/registro")
    public String registro(ModelMap modelo) {

        Especialidad[] especialidades = Especialidad.values();
        modelo.addAttribute("especialidades",especialidades);
        return "registroProfesional";
    }

    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/registro")
    public String registrarProfesional(/*@RequestParam MultipartFile archivo,*/@RequestParam String nombreUsuario, @RequestParam String nombre,
            @RequestParam String apellido,@RequestParam Long dni, @RequestParam("fechaDeNacimiento") String fechaDeNacimientoStr,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2,@RequestParam Long matricula,
            /*List<ObraSocial> obrasocial, @RequestParam Establecimiento establecimiento,*/ @RequestParam Especialidad especialidad ,ModelMap modelo) throws MiException, ParseException {

            /*Usuario usuario = new Usuario();
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setPassword(password);
            usuario.setDNI(dni);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);*/
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaDeNacimiento = dateFormat.parse(fechaDeNacimientoStr);
            /*usuario.setFechaDeNacimiento(fechaDeNacimiento);*/

            Profesional profesional = new Profesional();
            profesional.setMatricula(matricula);
            profesional.setEspecialidad(especialidad);

            //profesional.setEstablecimiento(establecimiento);
            //profesional.setObraSocial(obrasocial);

            try {
            profesionalServicio.crearProfesional(nombreUsuario, password, password2, nombre, apellido, email, fechaDeNacimiento, dni,  profesional.getEspecialidad(), matricula/*, obrasocial*/);
            modelo.put("exito", "el profesional fue creado con exito");
            return "index.html";
        } catch (MiException e) {

            modelo.put("error", e.getMessage());

            return "redirect:/profesional/registro";
        }
    }


    @GetMapping("/database")
    public String cargarDataBase() throws MiException {

        String[] nombres = {"Juan", "José", "Antonio", "Manuel", "David", "Carlos", "Francisco", "Jesús", "Pedro", "Pablo", "Luis", "Diego", "Andrés", "Alejandro", "Miguel", "Fernando", "Jorge", "Óscar", "María", "Ana", "Laura", "Carmen", "Elena", "Isabel", "Marta", "Julia", "Sara", "Paula", "Inés", "Teresa", "Cristina", "Blanca", "Rocío", "Alba", "Daniela", "Andrea"};
        String[] apellidos = {"García", "López", "García", "López", "Martínez", "Rodríguez", "Sánchez", "González", "Pérez", "Álvarez", "Romero", "Gómez", "Fernández", "Hernández", "Díaz", "Muñoz", "Castro", "Jiménez", "Moreno", "Martínez", "Rodríguez", "Sánchez", "González", "Pérez", "Álvarez", "Romero"};
        String[] especialidades = {"Alergología", "Anestesia y reanimación", "cirugía vascular", "Cardiología", "Cirugía general", "Cirugía maxilofacial", "Cirugía pediátrica", "Dermatología", "nutrición", "Ginecología y obstetricia", "Hematología", "Infectología", "Medicina de familia", "Medicina general", "Traumatología", "Neurología", "Oftalmología", "Oncología", "Diabetología", "Pediatría"};

        Random random = new Random();

        int posName = random.nextInt(nombres.length);
        String nombre = nombres[posName];
        System.out.println("nombre = " + nombre);

        int posLastName = random.nextInt(apellidos.length);
        String apellido = apellidos[posLastName];
        System.out.println("apellido = " + apellido);

        String nombreUsuario = (nombre + apellido.charAt(0)).toLowerCase();
        System.out.println("nombreUsuario = " + nombreUsuario);

        Long dni = random.nextLong(5000000, 50000000);
        System.out.println("dni = " + dni);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, random.nextInt(2024 - 2000) + 2000);
        calendar.set(Calendar.MONTH, random.nextInt(12));
        calendar.set(Calendar.DAY_OF_MONTH, random.nextInt(31));

        Date fechaNacimiento = calendar.getTime();
        System.out.println("fechaNacimiento = " + fechaNacimiento);

        String email = nombre.toLowerCase() + apellido.substring(0, 1).toLowerCase() + "@mail.com";
        System.out.println("email = " + email);


        String password = "123123";
        System.out.println("password = " + password);

        try {
            usuarioServicio.crearUsuario(nombreUsuario, nombre, apellido, dni, fechaNacimiento, email, password, password);
        } catch (MiException e) {
            throw new RuntimeException(e);
        }

        var usuario = usuarioServicio.buscarPorDni(dni);

        int posSpecial = random.nextInt(especialidades.length);
        String especialidad = especialidades[posSpecial];
        System.out.println("especialidad = " + especialidad);

        return "index";
    }
    
    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificarProfesional(@PathVariable String id, ModelMap modelo){
        Profesional profesional = new Profesional();
        profesional = profesionalServicio.getOne(id);
        modelo.addAttribute( "profesional", profesional);
        return null;
    }
    
    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificarProfesional(@PathVariable String id, /*@RequestParam MultipartFile archivo,*/  @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String apellido,
                                       @RequestParam Long DNI, @RequestParam Date fechaDeNacimiento, @RequestParam String email, @RequestParam String password, @RequestParam String password2,
                                       @RequestParam Especialidad especialidad, @RequestParam Long matricula, ModelMap modelo) throws MiException {

        try {

            profesionalServicio.modificarProfesional(id,/* archivo,*/  nombreUsuario, nombre, apellido, DNI, fechaDeNacimiento, email, password, password2, true, especialidad, matricula);
            modelo.put("exito", "Profesional modificado con exito");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return null;

        }
        return null;
    }
    
    //@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminarProfesional(@PathVariable String id){
        profesionalRepositorio.deleteById(id);
        return "index";  
    }
}
