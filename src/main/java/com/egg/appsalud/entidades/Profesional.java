package com.egg.appsalud.entidades;

import com.egg.appsalud.Enumeracion.Rol;

import java.util.Date;

import com.egg.appsalud.Enumeracion.Especialidad;
import com.egg.appsalud.Enumeracion.Provincias;

import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
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


    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    
    @Enumerated(EnumType.STRING)
    private Provincias provincias;
    
    private String localidad;
    private String direccion;

    
    @ElementCollection
    @CollectionTable(name = "HORARIOS_ATENCION", joinColumns = @JoinColumn(name = "profesional_id"))
    @Column(name = "horario_atencion")
    private Set<String> horariosAtencion;

    private int PrecioConsulta;
    
    
    @OneToMany
    private List<Consulta> consultas;

    @ManyToOne
    private FichaMedica fichaMedica;

    private int valoracionProfesional = 10;
    
    
        public void recibirPuntuacion(Consulta consulta, int puntuacion) {
        if (consultas.contains(consulta)) {
            if (puntuacion >= 1 && puntuacion <= 10) {
                consulta.setPuntuacion(puntuacion);
                actualizarValoracion();
            } else {
                throw new IllegalArgumentException("La puntuación debe estar en el rango de 1 a 10");
            }
        } else {
            throw new IllegalArgumentException("La consulta no pertenece a este profesional");
        }
    }

    private void actualizarValoracion() {
        if (consultas != null && !consultas.isEmpty()) {
            int totalPuntuacion = 0;
            int totalConsultasConPuntuacion = 0;

            for (Consulta consulta : consultas) {
                if (consulta.getPuntuacion() > 0) {
                    totalPuntuacion += consulta.getPuntuacion();
                    totalConsultasConPuntuacion++;
                }
            }

            if (totalConsultasConPuntuacion > 0) {
                valoracionProfesional = totalPuntuacion / totalConsultasConPuntuacion;
            } else {
                valoracionProfesional = 0;
            }
        }
    }

    
}
