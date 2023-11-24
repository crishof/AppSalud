package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.FichaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaMedicaRepositorio extends JpaRepository<FichaMedica, String>{
    
}
