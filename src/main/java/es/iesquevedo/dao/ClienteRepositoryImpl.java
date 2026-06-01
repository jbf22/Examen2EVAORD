package es.iesquevedo.dao;

import com.google.gson.reflect.TypeToken;
import es.iesquevedo.modelo.Cliente;
import es.iesquevedo.util.GsonFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepositoryImpl implements ClienteRepository {
    private final Path archivoClientes;
    private final Type tipoLista = new TypeToken<List<Cliente>>(){}.getType();
    private List<Cliente> clientes = new ArrayList<>();

    public ClienteRepositoryImpl() {
        this.archivoClientes = Path.of("data", "clientes.json");
        cargarDatos();
    }

    @Override
    public void cargarDatos() {
        try {
            if (Files.notExists(archivoClientes.getParent())) {
                Files.createDirectories(archivoClientes.getParent());
            }
            if (Files.notExists(archivoClientes)) {
                Files.writeString(archivoClientes, "[]");
            }
            String contenidoJson = Files.readString(archivoClientes);
            List<Cliente> listaCargada = GsonFactory.getGson().fromJson(contenidoJson, tipoLista);
            if (listaCargada != null) {
                clientes = listaCargada;
            }
        } catch (IOException error) {
            System.err.println("Error al cargar clientes: " + error.getMessage());
        }
    }

    @Override
    public void guardarDatos() {
        try {
            String jsonClientes = GsonFactory.getGson().toJson(clientes, tipoLista);
            Files.writeString(archivoClientes, jsonClientes);
        } catch (IOException error) {
            System.err.println("Error al guardar clientes: " + error.getMessage());
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        cargarDatos();
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente c : clientes) {
            resultado.add(c);
        }
        return resultado;
    }

    @Override
    public Optional<Cliente> buscarPorCodigo(String codigo) {
        cargarDatos();
        for (Cliente c : clientes) {
            if (c.getCodigo().equals(codigo)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean insertar(Cliente nuevoCliente) {
        cargarDatos();
        Optional<Cliente> existente = buscarPorCodigo(nuevoCliente.getCodigo());
        if (existente.isPresent()) {
            return false;
        }
        clientes.add(nuevoCliente);
        guardarDatos();
        return true;
    }

    @Override
    public boolean eliminarPorCodigo(String codigo) {
        cargarDatos();
        boolean eliminado = false;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCodigo().equals(codigo)) {
                clientes.remove(i);
                eliminado = true;
                break;
            }
        }
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }
}
