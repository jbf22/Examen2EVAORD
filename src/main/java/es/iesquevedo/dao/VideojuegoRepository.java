package es.iesquevedo.dao;

import es.iesquevedo.modelo.Videojuego;
import es.iesquevedo.service.VideojuegoService;

import java.util.List;
import java.util.Optional;

public interface VideojuegoRepository extends VideojuegoService {
    void cargarDatos();

    void guardarDatos();

    List<Videojuego> obtenerTodos();

    Optional<Videojuego> buscarPorCodigo(String codigo);

    @Override
    default boolean agregarVideojuego(Videojuego videojuego) {
        return false;
    }

    @Override
    default boolean eliminarVideojuego(String codigo) {
        return false;
    }

    @Override
    default void actualizarVideojuego(Videojuego videojuego) {

    }

    boolean insertar(Videojuego nuevoVideojuego);

    boolean eliminarPorCodigo(String codigo);

    void modificar(Videojuego videojuegoModificado);
}
