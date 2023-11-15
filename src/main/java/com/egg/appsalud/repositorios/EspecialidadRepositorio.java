package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Especialidad;
import com.egg.appsalud.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepositorio extends JpaRepository<Especialidad, String> {

    @Query("SELECT e FROM Especialidad e WHERE e.nombre = :nombre")
    Especialidad buscarPorNombre(@Param("nombre") String nombre);

}
