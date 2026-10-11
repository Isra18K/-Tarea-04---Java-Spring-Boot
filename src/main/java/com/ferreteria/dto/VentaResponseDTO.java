package com.ferreteria.dto;

public class VentaResponseDTO {
    private String mensaje;
    private int articulosVendidos;
    private Double totalPagar;

    public VentaResponseDTO(String mensaje, int articulosVendidos, Double totalPagar) {
        this.mensaje = mensaje;
        this.articulosVendidos = articulosVendidos;
        this.totalPagar = totalPagar;
    }

    public String getMensaje() { return mensaje; }
    public int getArticulosVendidos() { return articulosVendidos; }
    public Double getTotalPagar() { return totalPagar; }
}