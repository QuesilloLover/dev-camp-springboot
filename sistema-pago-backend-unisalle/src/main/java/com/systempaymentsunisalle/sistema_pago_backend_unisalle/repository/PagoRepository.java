package com.systempaymentsunisalle.sistema_pago_backend_unisalle.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.TypePago;


public interface PagoRepository extends JpaRepository<Pago, Long>{
    
    //metodo personalizado para buscar pagos
    List<Pago> findByEstudianteCodigo(String codigo);

    //metodo personalizado para buscar pagos por su estado
    List<Pago> findByStatus(StatusPago status);

    //metodo personalizado para buscar pagos por su tipo (EFECTIVO,CHEQUE,TRANSFERENCIA,DEPOSITO)
    List<Pago> findByTipoPago(TypePago tipoPago);
}
