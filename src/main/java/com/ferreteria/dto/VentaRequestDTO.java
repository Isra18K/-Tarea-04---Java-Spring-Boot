package com.ferreteria.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class VentaRequestDTO {
    @NotEmpty(message = "El carrito no puede estar vacío")
    private List<ItemVentaDTO> items;

    public VentaRequestDTO() {}

    public List<ItemVentaDTO> getItems() { return items; }
    public void setItems(List<ItemVentaDTO> items) { this.items = items; }
}