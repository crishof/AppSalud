package com.egg.appsalud.entidades;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="paciente")
public class Paciente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;
    private String nombre;
    private String apellido;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String email;
}
