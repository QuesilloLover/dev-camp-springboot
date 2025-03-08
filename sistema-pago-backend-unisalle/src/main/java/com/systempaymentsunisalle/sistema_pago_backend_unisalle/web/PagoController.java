package com.systempaymentsunisalle.sistema_pago_backend_unisalle.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities.Estudiante;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.enums.TypePago;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.repository.EstudianteRepository;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.repository.PagoRepository;
import com.systempaymentsunisalle.sistema_pago_backend_unisalle.services.PagoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin(origins = "*") // permite que cualquier cliente se conecte a nuestro servidor
public class PagoController {
    // Inyeccion de dependencias de PagoRepository para interactuar con la base de
    // datos de pagos
    @Autowired
    private PagoRepository pagoRepository;

    // Inyeccion de dependencias de EstudianteRepository para poder acceder a
    // informacion de estudiantes
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoService pagoService;

    // Metodos para manejo de estudiantes

    // Metodo que devuelva una lista de todos los estudiantes
    @GetMapping("/estudiantes")
    public List<Estudiante> getEstudiantes() {
        return estudianteRepository.findAll();
    }

    // Metodo que devuelva un estudiante por su codigo
    @GetMapping("/estudiantes/{codigo}")
    public Estudiante getEstudianteByCodigo(@PathVariable String codigo) {
        return estudianteRepository.findByCodigo(codigo);
    }

    // Metodo que devuelva una lista de estudiantes por su programaId
    @GetMapping("/estudiantesporprograma/{programaId}")
    public List<Estudiante> listarEstudiantesPorPrograma(@RequestParam String programaId) {
        return estudianteRepository.findByProgramaId(programaId);
    }

    // Metodo que devuelva una lista con todos los pagos registrados
    @GetMapping("/pagos")
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    // Metodo que devuelva una pago en especifico por su id
    @GetMapping("/pagos/{id}")
    public Pago listarPagoPorId(@PathVariable Long id) {
        return pagoRepository.findById(id).get();
    }

    // Metodo que lista los pagos hechos por un estudiante segun su codigo
    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo) {
        return pagoRepository.findByEstudianteCodigo(codigo);
    }

    // Metodo que lista los pagos segun su estado
    @GetMapping("/pagosporstatus/{status}")
    public List<Pago> listarPagosPorStatus(@RequestParam StatusPago status) {
        return pagoRepository.findByStatus(status);
    }

    // Metodo que lista los pagos segun su tipo
    @GetMapping("/pagosportipo/{tipoPago}")
    public List<Pago> listarPagosPorType(@RequestParam TypePago tipoPago) {
        return pagoRepository.findByTipoPago(tipoPago);
    }

    // Metodo para actualizar estado de un pago
    @PutMapping("pagos/{pagoId}/actualizarpago")
    public Pago actualizarStatusDelPago(@RequestParam StatusPago status, @PathVariable Long pagoId) {
        return pagoService.actualizarPagoPorStatus(status, pagoId);
    }

    // Metodo para registrar un pago nuevo con un archivo adjunto(comprobante)
    @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pago guardarPago(
            @RequestParam("file") MultipartFile file, // Archivo adjunto
            double cantidad, // monto del pago
            TypePago type,
            LocalDate date,
            String codigoEstudiante) throws IOException {
        return pagoService.savePago(file, cantidad, type, date, codigoEstudiante); // Guardamos el pago en la base de
                                                                                   // datos
    }

    // Metodo para obtener el archivo adjunto de un pago por su id
    @GetMapping(value = "/pagoFile/{pagoId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] listarArchivoPorId(@PathVariable Long pagoId) throws IOException {
        return pagoService.getArchivoPorId(pagoId);
    }

}
