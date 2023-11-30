package com.egg.appsalud.controladores;

import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.Enumeracion.Provincias;
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
        String[] localidades = {"La Plata", "Mar del Plata", "Quilmes", "San Fernando del Valle de Catamarca", "San Isidro", "Andalgalá", "Resistencia", "Barranqueras ", "Quitilipi"};
        String[] direcciones = {"9 de Julio 1995", "Rivadavia 110", "San Martín 44", "Belgrano 5962", "Sarmiento 74", "Mitre 998", "Independencia 785", "Pueyrredón 404", "Córdoba 753", "Santa Fe 620"};

        Random random = new Random();

        String nombre = nombres[random.nextInt(nombres.length)];

        String apellido = apellidos[random.nextInt(apellidos.length)];

        String nombreUsuario = (nombre + apellido.charAt(0)).toLowerCase();

        nombreUsuario = nombreUsuario.replace("á", "a");
        nombreUsuario = nombreUsuario.replace("ú", "u");
        nombreUsuario = nombreUsuario.replace("é", "e");
        nombreUsuario = nombreUsuario.replace("í", "i");
        nombreUsuario = nombreUsuario.replace("ó", "o");
        nombreUsuario = nombreUsuario.replace("ñ", "n");

        Long dni = random.nextLong(5000000, 50000000);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, random.nextInt(2024 - 2000) + 2000);
        calendar.set(Calendar.MONTH, random.nextInt(12));
        calendar.set(Calendar.DAY_OF_MONTH, random.nextInt(31));
        Date fechaNacimiento = calendar.getTime();

        String email = nombre.toLowerCase() + apellido.substring(0, 1).toLowerCase() + "@mail.com";

        String password = "123123";

        Long matricula = random.nextLong(1000, 99999);

        Especialidad especialidad = Especialidad.values()[random.nextInt(Especialidad.values().length)];

        Provincias provincias = Provincias.values()[random.nextInt(Provincias.values().length)];
        System.out.println("provincias = " + provincias);

        String localidad = localidades[random.nextInt(localidades.length)];
        System.out.println("localidad = " + localidad);

        String direccion = direcciones[random.nextInt(direcciones.length)];
        System.out.println("direccion = " + direccion);

//        List<LocalTime> horariosAtencion = new ArrayList<>();

        // Agrega los horarios según tus necesidades
//        horariosAtencion.add(LocalTime.of(9, 0));   // 09:00
//        horariosAtencion.add(LocalTime.of(12, 30)); // 12:30
//        horariosAtencion.add(LocalTime.of(15, 45)); // 15:45
//        horariosAtencion.add(LocalTime.of(18, 0));  // 18:00

        int precioConsulta = 7000;

//        profesionalServicio.crearProfesional(null, nombreUsuario, password, password, nombre, apellido, email, fechaNacimiento, dni, especialidad, provincias, localidad, direccion, matricula, diasDisponibles, horarioEntrada, horarioSalida, precioConsulta);

        return "redirect:../listaProfesionales";
    }
}
