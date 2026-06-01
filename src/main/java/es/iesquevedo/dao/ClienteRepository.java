package es.iesquevedo.dao;

import es.iesquevedo.modelo.Cliente;
import es.iesquevedo.service.ClienteService;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends ClienteService {
    void cargarDatos();

    void guardarDatos();

    List<Cliente> obtenerTodos();

    Optional<Cliente> buscarPorCodigo(String codigo);

    @Override
    default boolean registrarCliente(Cliente cliente) {
        return false;
    }

    @Override
    default boolean eliminarCliente(String codigo) {
        return false;
    }

    boolean insertar(Cliente nuevoCliente);

    boolean eliminarPorCodigo(String codigo);
}
