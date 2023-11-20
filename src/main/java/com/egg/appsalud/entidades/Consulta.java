package com.egg.appsalud.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

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

    @ManyToOne
    private Establecimiento establecimiento;

    private int precioConsulta;

    private int valoracion;
}
