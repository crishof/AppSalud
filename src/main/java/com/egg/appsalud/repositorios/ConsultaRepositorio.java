
package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Consulta;
import com.egg.appsalud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepositorio extends JpaRepository<Consulta, String>{
    
}
