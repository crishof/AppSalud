package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente,String> {
    
    @Query("SELECT p FROM Paciente p WHERE p.nombre= :nombre")
    public Paciente buscarNombre(@Param("nombre") String titulo);

    @Query("SELECT p FROM Paciente p ORDER BY p.id DESC")
    public List<Paciente> buscarOrdenado();
}
