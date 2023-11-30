package com.egg.appsalud.entidades;

import com.egg.appsalud.Exception.MiException;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FichaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Paciente paciente;

//    @OneToMany
//    private List<Profesional> profesionales;

//    @OneToMany
//    private List<Consulta> consultas;
    
//    private String observaciones;

//    @ManyToOne
//    @JoinColumn
//    private Profesional creador;

    private String obraSocial;
    private long numeroAfiliado;
    private String grupoSanguineo;
    private Double altura;
    private Double peso;
    private String antecedentes;

    public FichaMedica(Paciente paciente, String antecedentes, String obraSocial, long numeroAfiliado, String grupoSanguineo, Double altura, Double peso) {
        this.paciente = paciente;
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
        this.grupoSanguineo = grupoSanguineo;
        this.altura = altura;
        this.peso = peso;
        this.antecedentes = antecedentes;
    }
}
