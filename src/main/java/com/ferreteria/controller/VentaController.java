package com.ferreteria.controller;

import com.ferreteria.dto.VentaRequestDTO;
import com.ferreteria.dto.VentaResponseDTO;
import com.ferreteria.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping("/carrito")
    public ResponseEntity<?> procesarVenta(@Valid @RequestBody VentaRequestDTO request) {
        try {
            VentaResponseDTO respuesta = ventaService.procesarVentaCarrito(request);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException ex) {
            Map<String, String> err = new HashMap<>();
            err.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }
}