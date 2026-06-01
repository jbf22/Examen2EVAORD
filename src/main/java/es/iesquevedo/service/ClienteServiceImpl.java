package es.iesquevedo.service;

import es.iesquevedo.dao.ClienteRepositoryImpl;
import es.iesquevedo.modelo.Cliente;
import es.iesquevedo.dao.ClienteRepository;

import java.util.List;
import java.util.Optional;

import static es.iesquevedo.Main.clienteRepository;

public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repositorio;

    public ClienteServiceImpl( ClienteRepository repositorioCliente) {
        this.repositorio = repositorioCliente;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return repositorio.obtenerTodos();
    }

    @Override
    public Optional<Cliente> buscarPorCodigo(String codigo) {
        return repositorio.buscarPorCodigo(codigo);
    }

    @Override
    public boolean registrarCliente(Cliente cliente) {
        if (cliente.getCodigo() == null || cliente.getCodigo().isBlank()) {
            return false;
        }
        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().isBlank()) {
            return false;
        }
        if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            return false;
        }
        return repositorio.insertar(cliente);
    }

    @Override
    public boolean eliminarCliente(String codigo) {
        return repositorio.eliminarPorCodigo(codigo);
    }
}
