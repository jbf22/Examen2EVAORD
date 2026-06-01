package es.iesquevedo.service;

import es.iesquevedo.modelo.Videojuego;

import java.util.List;
import java.util.Optional;

public interface VideojuegoService {
    List<Videojuego> obtenerTodos();

    Optional<Videojuego> buscarPorCodigo(String codigo);

    boolean agregarVideojuego(Videojuego videojuego);

    boolean eliminarVideojuego(String codigo);

    void actualizarVideojuego(Videojuego videojuego);
}
