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

    @Query("SELECT p FROM Profesional p ORDER BY p.apellido ASC")
    List<Profesional> listarOrdenadoPorApellido();

    @Query("SELECT p FROM Profesional p ORDER BY p.especialidad ASC")
    List<Profesional> listarOrdenadoPorEspecialidad();
    @Query("SELECT p FROM Profesional p WHERE p.especialidad LIKE ?1% ORDER BY p.apellido ASC")
    List<Profesional> buscarProfesionalPorEspecialidad(String especialidad);
}