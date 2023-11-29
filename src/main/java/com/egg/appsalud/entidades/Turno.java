/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Turno")
public class Turno {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String fecha;
    private String hora;
    private String consulta;
    private boolean disponibilidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente")
    private Paciente paciente;

    @OneToOne
    private Profesional profesional;

}
