package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Consulta;
import com.egg.appsalud.entidades.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepositorio extends JpaRepository<Consulta, String> {
    List<Consulta> findByPacienteId(String pacienteId);


    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :id")
    List<Consulta> listarPorIdPaciente(String id);
}
