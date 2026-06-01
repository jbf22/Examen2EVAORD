package es.iesquevedo.service;

import es.iesquevedo.dao.*;
import es.iesquevedo.modelo.Venta;
import es.iesquevedo.modelo.Videojuego;
import es.iesquevedo.modelo.Cliente;

import java.util.List;
import java.util.Optional;

public class VentaServiceImpl implements VentaService {
    private final VentaRepository repositorioVentas;
    private final VideojuegoRepository repositorioVideojuegos;
    private final ClienteRepository repositorioClientes;



    public VentaServiceImpl(VentaRepository repositorioVentas, VideojuegoRepository repositorioVideojuegos, ClienteRepository repositorioClientes) {

        this.repositorioVentas = repositorioVentas;
        this.repositorioVideojuegos = repositorioVideojuegos;
        this.repositorioClientes = repositorioClientes;
    }

    @Override
    public List<Venta> obtenerTodas() {
        return repositorioVentas.obtenerTodas();
    }

    @Override
    public Optional<Venta> buscarPorNumero(String numeroVenta) {
        return repositorioVentas.buscarPorNumero(numeroVenta);
    }

    @Override
    public boolean procesarVenta(Venta venta) {
        if (venta.getNumeroVenta() == null || venta.getNumeroVenta().isBlank()) {
            return false;
        }
        if (venta.getCantidadUnidades() <= 0) {
            return false;
        }

        Optional<Cliente> clienteOpt = repositorioClientes.buscarPorCodigo(venta.getCodigoCliente());
        if (clienteOpt.isEmpty()) {
            return false;
        }

        Optional<Videojuego> videojuegoOpt = repositorioVideojuegos.buscarPorCodigo(venta.getCodigoVideojuego());
        if (videojuegoOpt.isEmpty()) {
            return false;
        }

        Videojuego videojuego = videojuegoOpt.get();
        if (videojuego.getStock() < venta.getCantidadUnidades()) {
            return false;
        }

        int nuevoStock = videojuego.getStock() - venta.getCantidadUnidades();
        videojuego.setStock(nuevoStock);
        repositorioVideojuegos.modificar(videojuego);

        return repositorioVentas.insertar(venta);
    }

    @Override
    public boolean cancelarVenta(String numeroVenta) {
        Optional<Venta> ventaOpt = repositorioVentas.buscarPorNumero(numeroVenta);
        if (ventaOpt.isEmpty()) {
            return false;
        }

        Venta venta = ventaOpt.get();
        Optional<Videojuego> videojuegoOpt = repositorioVideojuegos.buscarPorCodigo(venta.getCodigoVideojuego());
        if (videojuegoOpt.isPresent()) {
            Videojuego videojuego = videojuegoOpt.get();
            int stockRecuperado = videojuego.getStock() + venta.getCantidadUnidades();
            videojuego.setStock(stockRecuperado);
            repositorioVideojuegos.modificar(videojuego);
        }

        return repositorioVentas.eliminarPorNumero(numeroVenta);
    }
}
