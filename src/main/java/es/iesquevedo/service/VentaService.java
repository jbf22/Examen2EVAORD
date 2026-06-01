package es.iesquevedo.service;

import es.iesquevedo.modelo.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaService {
    List<Venta> obtenerTodas();

    Optional<Venta> buscarPorNumero(String numeroVenta);

    boolean procesarVenta(Venta venta);

    boolean cancelarVenta(String numeroVenta);
}
