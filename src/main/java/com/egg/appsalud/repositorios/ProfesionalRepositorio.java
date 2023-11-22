package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Profesional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {
    @Query("SELECT p FROM Profesional p WHERE p.nombre= :nombre")
    Profesional buscarNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Profesional p ORDER BY p.nombre DESC")
    List<Profesional> buscarOrdenado();

    @Query("SELECT p FROM Profesional p WHERE p.especialidad LIKE ?1%")
    List<Profesional> buscarProfesionalPorEspecialidad(String especialidad);
    
    @Query("SELECT DISTINCT p.especialidad FROM Profesional p")
    List<String> obtenerEspecialidades();
}

