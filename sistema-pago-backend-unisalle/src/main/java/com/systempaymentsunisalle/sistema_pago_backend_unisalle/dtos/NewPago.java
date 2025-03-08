package com.systempaymentsunisalle.sistema_pago_backend_unisalle.dtos;
import java.time.LocalDate;

import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.TypePago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPago {
    private double cantidad;
    private TypePago tipoPago;
    private LocalDate fecha;
    private String codigoEstudiante;
}
