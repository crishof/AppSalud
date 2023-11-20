package com.egg.appsalud.entidades;

import com.egg.appsalud.Enumeracion.Rol;

import java.util.Date;

import com.egg.appsalud.Enumeracion.Especialidad;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Data
@Table(name = "PROFESIONAL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PROFESIONAL")
public class Profesional extends Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private Long matricula;

    @OneToMany
    private List<ObraSocial> ObraSocial;
    @OneToMany
    private List<Paciente> pacientes;
    @OneToOne
    private Establecimiento establecimiento;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @OneToMany
    private List<Consulta> consultas;

    @ManyToOne
    private FichaMedica fichaMedica;

    private int valoracionProfesional;


}
