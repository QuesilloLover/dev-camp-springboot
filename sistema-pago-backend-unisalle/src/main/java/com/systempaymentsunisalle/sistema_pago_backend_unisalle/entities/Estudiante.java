package com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    
    @Id
    private String id;

    private String nombre;
    private String apellido;

    //Indica que la columna codigo debe ser la unica en la DB
    @Column(unique = true)
    private String codigo;

    private String programaId;

    private String foto;
}
