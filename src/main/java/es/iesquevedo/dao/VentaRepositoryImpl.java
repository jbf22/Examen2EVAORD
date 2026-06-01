package es.iesquevedo.dao;

import com.google.gson.reflect.TypeToken;
import es.iesquevedo.modelo.Venta;
import es.iesquevedo.util.GsonFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VentaRepositoryImpl implements VentaRepository {
    private final Path archivoVentas;
    private final Type tipoLista = new TypeToken<List<Venta>>(){}.getType();
    private List<Venta> ventas = new ArrayList<>();

    public VentaRepositoryImpl() {
        this.archivoVentas = Path.of("data", "ventas.json");
        cargarDatos();
    }

    @Override
    public void cargarDatos() {
        try {
            if (Files.notExists(archivoVentas.getParent())) {
                Files.createDirectories(archivoVentas.getParent());
            }
            if (Files.notExists(archivoVentas)) {
                Files.writeString(archivoVentas, "[]");
            }
            String contenidoJson = Files.readString(archivoVentas);
            List<Venta> listaCargada = GsonFactory.getGson().fromJson(contenidoJson, tipoLista);
            if (listaCargada != null) {
                ventas = listaCargada;
            }
        } catch (IOException error) {
            System.err.println("Error al cargar ventas: " + error.getMessage());
        }
    }

    @Override
    public void guardarDatos() {
        try {
            String jsonVentas = GsonFactory.getGson().toJson(ventas, tipoLista);
            Files.writeString(archivoVentas, jsonVentas);
        } catch (IOException error) {
            System.err.println("Error al guardar ventas: " + error.getMessage());
        }
    }

    @Override
    public List<Venta> obtenerTodas() {
        cargarDatos();
        List<Venta> resultado = new ArrayList<>();
        for (Venta v : ventas) {
            resultado.add(v);
        }
        return resultado;
    }

    @Override
    public Optional<Venta> buscarPorNumero(String numeroVenta) {
        cargarDatos();
        for (Venta v : ventas) {
            if (v.getNumeroVenta().equals(numeroVenta)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean insertar(Venta nuevaVenta) {
        cargarDatos();
        Optional<Venta> existente = buscarPorNumero(nuevaVenta.getNumeroVenta());
        if (existente.isPresent()) {
            return false;
        }
        ventas.add(nuevaVenta);
        guardarDatos();
        return true;
    }

    @Override
    public boolean eliminarPorNumero(String numeroVenta) {
        cargarDatos();
        boolean eliminado = false;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getNumeroVenta().equals(numeroVenta)) {
                ventas.remove(i);
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
