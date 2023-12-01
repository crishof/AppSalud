package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.FichaMedica;
import com.egg.appsalud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaMedicaRepositorio extends JpaRepository<FichaMedica, String>{

    @Query("SELECT f FROM FichaMedica f WHERE f.paciente.id = :id")
    FichaMedica buscarIdPaciente(@Param("id") String id);
}
