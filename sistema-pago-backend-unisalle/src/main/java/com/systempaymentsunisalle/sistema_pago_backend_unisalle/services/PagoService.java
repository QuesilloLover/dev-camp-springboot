package com.systempaymentsunisalle.sistema_pago_backend_unisalle.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities.Estudiante;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.TypePago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.repository.EstudianteRepository;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class PagoService {

    // Inyeccion de dependencias de PagoRepository para interactuar con la base de
    // datos de pagos
    @Autowired
    private PagoRepository pagoRepository;

    // Inyeccion de dependencias de EstudianteRepository para poder acceder a
    // informacion de estudiantes
    @Autowired
    private EstudianteRepository estudianteRepository;

    /**
     * @param file             archivo pdf que se subira al servidor
     * @param cantidad         cantidad de pagos que se realizaran
     * @param type             tipo de pago que se realizara
     * @param date             fecha en la que se realizara el pago
     * @param codigoEstudiante codigo del estudiante que realizara el pago
     * @return objeto para guardado en la db
     * @ Throws IOException Excepcion lanzada en caso de que el archivo no pueda ser
     * leido
     */

    // Construir la ruta donde se guardara el archivo dentro del sistema

    public Pago savePago(MultipartFile file, double cantidad, TypePago type, LocalDate date,
            String codigoEstudiante) throws IOException {

        /**
         * system.getproperty("user.home") obtiene la ruta del directorio actual del
         * sistema en ejecucion
         * Paths.get() construye una ruta a partir de los parametros que se le pasan
         */

        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos");

        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String fileName = UUID.randomUUID().toString();

        // construimos la ruta completa agregando el tipo de archivo
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos", fileName + ".pdf");

        // guardamos el archivo en la ruta especificada
        Files.copy(file.getInputStream(), filePath);

        // buscamos el estudiante que realizo el pago por su codigo en la DB
        Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante);

        // creamos un objeto utilizando el patron de diseño builder
        Pago pago = Pago.builder()
                .tipoPago(type)
                .status(StatusPago.CREADO) // Estado inical del pago
                .fecha(date) // fecha en la que se realizo el pago
                .estudiante(estudiante) // relacion con el estudiante qeu realiza el pago
                .monto(cantidad) // cantidad de pagos que se realizaran
                .file(filePath.toUri().toString())
                .build(); // construccion final del objeto de pago

        return pagoRepository.save(pago); // guardamos el pago en la base de datos

    }

    public byte[] getArchivoPorId(Long id) throws IOException {

        // Buscamos el pago por su id
        Pago pago = pagoRepository.findById(id).get();
        
        //Obtener la URI del archivo guardado como una cadena de texto, y convertir esa cadena en un objeto URL
        //Convierte la URL en un objeto Path
        //Lee el archivo en bytes y lo retorna
        return Files.readAllBytes(Path.of(URI.create(pago.getFile())));
    }

    public Pago actualizarPagoPorStatus(StatusPago status, Long id){
        Pago pago = pagoRepository.findById(id).get();

        //Actualizamos el estado del pago
        pago.setStatus(status);
        return pagoRepository.save(pago);
    }

}
