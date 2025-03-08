package com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities;
import java.time.LocalDate;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.TypePago;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate fecha;
    private double monto;
    private TypePago tipoPago;
    private StatusPago status;

    private String file;

    @ManyToOne
    private Estudiante estudiante;
}
