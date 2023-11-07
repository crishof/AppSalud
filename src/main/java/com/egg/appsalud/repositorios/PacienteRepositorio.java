package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepositorio extends JpaRepository<Paciente,String> {
}
