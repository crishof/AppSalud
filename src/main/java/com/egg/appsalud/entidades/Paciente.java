package com.egg.appsalud.entidades;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "PACIENTE")

@DiscriminatorValue("PACIENTE")
@Getter
@Setter
@NoArgsConstructor
public class Paciente extends Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    @OneToMany
    private List<Consulta> consulta;

}