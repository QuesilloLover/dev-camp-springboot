package com.systempaymentsunisalle.sistema_pago_backend_unisalle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;

import com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities.Estudiante;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.TypePago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.repository.EstudianteRepository;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.repository.PagoRepository;

@SpringBootApplication
public class SistemaPagoBackendUnisalleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaPagoBackendUnisalleApplication.class, args);
	};

	@Bean
	CommandLineRunner CommandLineRunner(EstudianteRepository estudianteRepository, PagoRepository pagoRepository) {
		return args -> {
			estudianteRepository.save(Estudiante.builder()
					.id("1")
					.nombre("Juan")
					.apellido("Perez")
					.build());
			estudianteRepository.save(Estudiante.builder()
					.id("2")
					.nombre("Maria")
					.apellido("Gomez")
					.build());
			estudianteRepository.save(Estudiante.builder()
					.id("3")
					.nombre("Pedro")
					.apellido("Rodriguez")
					.build());
			TypePago[] tiposPago = TypePago.values();

			Random random = new Random();

			estudianteRepository.findAll().forEach(estudiante -> {
				for(int i = 0; i < 10; i++){
					int index = random.nextInt(tiposPago.length);

					Pago pago = Pago.builder()
						.fecha(LocalDate.now())
						.monto(1000)
						.tipoPago(tiposPago[index])
						.status(StatusPago.CREADO)
						.estudiante(estudiante)
						.build();
					pagoRepository.save(pago);
				}
			});
		};
	}
}
