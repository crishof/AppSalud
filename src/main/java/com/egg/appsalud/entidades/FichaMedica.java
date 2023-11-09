package com.egg.appsalud.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FichaMedica {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    private ObraSocial obraSocial;
    
    @OneToOne
    private Paciente paciente;
    
    @OneToMany
    private List<Profesional> profesionales;
    
    @OneToMany
    private List<Consulta> consultas;
    
    @ManyToOne
    @JoinColumn
    private Profesional creador; 
}
