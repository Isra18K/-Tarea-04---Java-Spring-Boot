package com.ferreteria.controller;

import com.ferreteria.entity.Producto;
import com.ferreteria.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        List<Producto> productos = productoService.listarTodos();
        Map<String, Object> resp = new HashMap<>();
        resp.put("data", productos);
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Producto producto) {
        Producto nuevo = productoService.guardar(producto);
        Map<String, Object> resp = new HashMap<>();
        resp.put("id", nuevo.getId());
        resp.put("mensaje", "Producto creado exitosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        try {
            productoService.actualizar(id, producto);
            Map<String, String> resp = new HashMap<>();
            resp.put("mensaje", "Producto actualizado correctamente");
            return ResponseEntity.ok(resp);
        } catch (RuntimeException ex) {
            Map<String, String> err = new HashMap<>();
            err.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
    }
}