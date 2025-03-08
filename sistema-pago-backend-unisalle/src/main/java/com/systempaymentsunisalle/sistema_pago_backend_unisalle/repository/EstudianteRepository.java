package com.systempaymentsunisalle.sistema_pago_backend_unisalle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities.Estudiante;


@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String>{

    //Metodo personalizado buscar por codigo
    Estudiante findByCodigo(String codigo);
    
    //Metodo personalizado buscar por programaId
    List<Estudiante> findByProgramaId(String programaId);
} 
