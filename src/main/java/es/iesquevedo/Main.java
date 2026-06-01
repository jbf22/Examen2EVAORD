package es.iesquevedo;

import es.iesquevedo.dao.*;
import es.iesquevedo.modelo.Venta;
import es.iesquevedo.service.*;
import es.iesquevedo.ui.ControladorConsola;

import java.util.Scanner;

public class Main {
    private static final Scanner entrada = new Scanner(System.in);
    private static ClienteService ClienteService;
    private static VentaService VentaService;
    private static VideojuegoService VideojuegoService;
    private static final ControladorConsola controlador = new ControladorConsola(entrada,ClienteService, VentaService, VideojuegoService);
    public static final ClienteRepository clienteRepository = new ClienteRepositoryImpl();
    private static final VentaRepository ventaRepository = new VentaRepositoryImpl();
    private static final VideojuegoRepository videojuegoRepository = new VideojuegoRepositoryImpl();
    private static final ClienteService servicioCliente= new ClienteServiceImpl(clienteRepository);
    private static final VentaService servicioVenta= new VentaServiceImpl(ventaRepository);
    private static final VideojuegoService servicioVideojuegos = new VideojuegoServiceImpl(videojuegoRepository);

    public static void main(String[] args) {
        boolean ejecutando = true;
        while (ejecutando) {
            System.out.println("\n=== Tienda de Videojuegos ===");
            System.out.println("1) Gestión de Videojuegos");
            System.out.println("2) Gestión de Clientes");
            System.out.println("3) Gestión de Ventas");
            System.out.println("0) Salir");
            System.out.print("Seleccione opción: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1" -> controlador.menuVideojuegos();
                case "2" -> controlador.menuClientes();
                case "3" -> controlador.menuVentas();
                case "0" -> ejecutando = false;
                default -> System.out.println("Opción no válida");
            }
        }
        System.out.println("Hasta pronto!");
    }
}



