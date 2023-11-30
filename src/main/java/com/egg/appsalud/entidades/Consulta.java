package com.egg.appsalud.entidades;

import com.egg.appsalud.Enumeracion.Provincias;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    private Paciente paciente;
    @OneToOne
    private Profesional profesional;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDeConsulta;
    
    private LocalTime horaInicio;
    private int duracionConsulta; // Duración en minutos

    private String diagnostico;
    private String tratamiento;

    //el horario y la duración
    public Consulta(LocalTime horaInicio, int duracionConsulta) {
        this.horaInicio = horaInicio;
        this.duracionConsulta = duracionConsulta;
    }
    
    @Enumerated(EnumType.STRING)
    private Provincias provincias;
    
    private String localidad;
    private String direccion;

    private int precioConsulta;
    
    private int puntuacion;
    
    private String motivoConsulta;

}
