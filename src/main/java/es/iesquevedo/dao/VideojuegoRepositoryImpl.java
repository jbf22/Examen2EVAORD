package es.iesquevedo.dao;

import com.google.gson.reflect.TypeToken;
import es.iesquevedo.modelo.Videojuego;
import es.iesquevedo.util.GsonFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideojuegoRepositoryImpl implements VideojuegoRepository {
    private final Path archivoVideojuegos;
    private final Type tipoLista = new TypeToken<List<Videojuego>>(){}.getType();
    private List<Videojuego> videojuegos = new ArrayList<>();

    public VideojuegoRepositoryImpl() {
        this.archivoVideojuegos = Path.of("data", "videojuegos.json");
        cargarDatos();
    }

    @Override
    public void cargarDatos() {
        try {
            if (Files.notExists(archivoVideojuegos.getParent())) {
                Files.createDirectories(archivoVideojuegos.getParent());
            }
            if (Files.notExists(archivoVideojuegos)) {
                Files.writeString(archivoVideojuegos, "[]");
            }
            String contenidoJson = Files.readString(archivoVideojuegos);
            List<Videojuego> listaCargada = GsonFactory.getGson().fromJson(contenidoJson, tipoLista);
            if (listaCargada != null) {
                videojuegos = listaCargada;
            }
        } catch (IOException error) {
            System.err.println("Error al cargar videojuegos: " + error.getMessage());
        }
    }

    @Override
    public void guardarDatos() {
        try {
            String jsonVideojuegos = GsonFactory.getGson().toJson(videojuegos, tipoLista);
            Files.writeString(archivoVideojuegos, jsonVideojuegos);
        } catch (IOException error) {
            System.err.println("Error al guardar videojuegos: " + error.getMessage());
        }
    }

    @Override
    public List<Videojuego> obtenerTodos() {
        cargarDatos();
        List<Videojuego> resultado = new ArrayList<>();
        for (Videojuego v : videojuegos) {
            resultado.add(v);
        }
        return resultado;
    }

    @Override
    public Optional<Videojuego> buscarPorCodigo(String codigo) {
        cargarDatos();
        for (Videojuego v : videojuegos) {
            if (v.getCodigo().equals(codigo)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean insertar(Videojuego nuevoVideojuego) {
        cargarDatos();
        Optional<Videojuego> existente = buscarPorCodigo(nuevoVideojuego.getCodigo());
        if (existente.isPresent()) {
            return false;
        }
        videojuegos.add(nuevoVideojuego);
        guardarDatos();
        return true;
    }

    @Override
    public boolean eliminarPorCodigo(String codigo) {
        cargarDatos();
        boolean eliminado = false;
        for (int i = 0; i < videojuegos.size(); i++) {
            if (videojuegos.get(i).getCodigo().equals(codigo)) {
                videojuegos.remove(i);
                eliminado = true;
                break;
            }
        }
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }

    @Override
    public void modificar(Videojuego videojuegoModificado) {
        cargarDatos();
        for (int idx = 0; idx < videojuegos.size(); idx++) {
            if (videojuegos.get(idx).getCodigo().equals(videojuegoModificado.getCodigo())) {
                videojuegos.set(idx, videojuegoModificado);
                guardarDatos();
                break;
            }
        }
    }
}
