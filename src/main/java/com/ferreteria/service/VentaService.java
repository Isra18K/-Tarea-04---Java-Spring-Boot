package com.ferreteria.service;

import com.ferreteria.dto.ItemVentaDTO;
import com.ferreteria.dto.VentaRequestDTO;
import com.ferreteria.dto.VentaResponseDTO;
import com.ferreteria.entity.Producto;
import com.ferreteria.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaService {

    private final ProductoRepository productoRepository;

    public VentaService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public VentaResponseDTO procesarVentaCarrito(VentaRequestDTO request) {
        double totalVenta = 0.0;

        for (ItemVentaDTO item : request.getItems()) {
            Producto prod = productoRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("El producto con ID " + item.getId() + " no existe"));

            if (prod.getStock() < item.getCantidad()) {
                // Al lanzar RuntimeException, Spring Boot realiza un ROLLBACK total automático
                throw new RuntimeException("Stock insuficiente para '" + prod.getNombre() + 
                                           "'. Disponible: " + prod.getStock() + ", Solicitado: " + item.getCantidad());
            }

            prod.setStock(prod.getStock() - item.getCantidad());
            productoRepository.save(prod);

            totalVenta += prod.getPrecio() * item.getCantidad();
        }

        return new VentaResponseDTO("Venta procesada con éxito", request.getItems().size(), totalVenta);
    }
}