package com.egg.appsalud.controladores;

import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/util")
public class UtilController {

    @Autowired
    ProfesionalServicio profesionalServicio;

    @GetMapping("/database")
    public String cargarDataBase() throws MiException {

        String[] nombres = {"Juan", "José", "Antonio", "Manuel", "David", "Carlos", "Francisco", "Jesús", "Pedro", "Pablo", "Luis", "Diego", "Andrés", "Alejandro", "Miguel", "Fernando", "Jorge", "Óscar", "María", "Ana", "Laura", "Carmen", "Elena", "Isabel", "Marta", "Julia", "Sara", "Paula", "Inés", "Teresa", "Cristina", "Blanca", "Rocío", "Alba", "Daniela", "Andrea"};
        String[] apellidos = {"García", "López", "García", "López", "Martínez", "Rodríguez", "Sánchez", "González", "Pérez", "Álvarez", "Romero", "Gómez", "Fernández", "Hernández", "Díaz", "Muñoz", "Castro", "Jiménez", "Moreno", "Martínez", "Rodríguez", "Sánchez", "González", "Pérez", "Álvarez", "Romero"};
        String[] especialidades = {"Alergología", "Anestesia y reanimación", "cirugía vascular", "Cardiología", "Cirugía general", "Cirugía maxilofacial", "Cirugía pediátrica", "Dermatología", "nutrición", "Ginecología y obstetricia", "Hematología", "Infectología", "Medicina de familia", "Medicina general", "Traumatología", "Neurología", "Oftalmología", "Oncología", "Diabetología", "Pediatría"};

        Random random = new Random();

        String nombre = nombres[random.nextInt(nombres.length)];
        System.out.println("nombre = " + nombre);

        String apellido = apellidos[random.nextInt(apellidos.length)];
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

        Long matricula = random.nextLong(1000, 99999);

        Especialidad especialidad = Especialidad.values()[random.nextInt(Especialidad.values().length)];
        System.out.println("especialidad = " + especialidad);

        profesionalServicio.crearProfesional(nombreUsuario, password, password, nombre, apellido, email, fechaNacimiento, dni, especialidad, matricula);

        return "redirect:../profesional/lista";
    }
}
