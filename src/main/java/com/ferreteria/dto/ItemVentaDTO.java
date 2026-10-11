package com.ferreteria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemVentaDTO {
    @NotNull
    private Long id;

    @NotNull
    @Min(1)
    private Integer cantidad;

    public ItemVentaDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}