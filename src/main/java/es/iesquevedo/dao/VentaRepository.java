package es.iesquevedo.dao;

import es.iesquevedo.modelo.Venta;
import es.iesquevedo.service.VentaService;

import java.util.List;
import java.util.Optional;

public interface VentaRepository extends VentaService {
    void cargarDatos();

    void guardarDatos();

    List<Venta> obtenerTodas();

    Optional<Venta> buscarPorNumero(String numeroVenta);

    @Override
    default boolean procesarVenta(Venta venta) {
        return false;
    }

    @Override
    default boolean cancelarVenta(String numeroVenta) {
        return false;
    }

    boolean insertar(Venta nuevaVenta);

    boolean eliminarPorNumero(String numeroVenta);
}
