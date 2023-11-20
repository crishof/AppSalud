package com.egg.appsalud.repositorios;

import com.egg.appsalud.entidades.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    Usuario buscarPorNombre(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'PROFESIONAL'")
    List<Usuario> buscarProfesional();

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'PACIENTE'")
    List<Usuario> buscarPaciente();

    @Query("SELECT u FROM Usuario u WHERE u.DNI = :dni")
    Usuario buscarPorDni(@Param("dni") Long dni);

    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE ?1%")
    List<Usuario> buscarUsuarioPorNombre(String palabra);
}
