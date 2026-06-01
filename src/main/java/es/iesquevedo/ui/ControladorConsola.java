package es.iesquevedo.ui;

import es.iesquevedo.modelo.Venta;
import es.iesquevedo.modelo.Videojuego;
import es.iesquevedo.modelo.Cliente;
import es.iesquevedo.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ControladorConsola {
    private final Scanner entrada;
    private final VideojuegoService servicioVideojuegos;
    private final ClienteService servicioClientes;
    private final VentaService servicioVentas;

    public ControladorConsola(Scanner entrada, ClienteService servicioClientes, VentaService servicioVentas, VideojuegoService servicioVideojuegos) {
        this.entrada = entrada;
        this.servicioClientes = servicioClientes;
        this.servicioVentas = servicioVentas;
        this.servicioVideojuegos = servicioVideojuegos;
    }

    public void menuVideojuegos() {
        System.out.println("\n--- Gestión de Videojuegos ---");
        System.out.println("1) Agregar videojuego");
        System.out.println("2) Eliminar videojuego");
        System.out.println("3) Mostrar catálogo");
        System.out.println("0) Regresar");
        System.out.print("Opción: ");
        String opcion = entrada.nextLine().trim();
        switch (opcion) {
            case "1" -> agregarVideojuego();
            case "2" -> eliminarVideojuego();
            case "3" -> mostrarCatalogo();
            case "0" -> {
            }
            default -> System.out.println("Opción no válida");
        }
    }

    public void agregarVideojuego() {
        System.out.print("Título del juego: ");
        String titulo = entrada.nextLine().trim();
        System.out.print("Desarrolladora: ");
        String desarrolladora = entrada.nextLine().trim();
        System.out.print("Plataforma (ej: PS5, Xbox, PC, Switch): ");
        String plataforma = entrada.nextLine().trim();
        System.out.print("Precio unitario: ");
        String precioStr = entrada.nextLine().trim();
        System.out.print("Stock inicial: ");
        String stockStr = entrada.nextLine().trim();
        
        try {
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);
            String codigo = UUID.randomUUID().toString();
            Videojuego juego = new Videojuego(codigo, titulo, desarrolladora, plataforma, precio, stock);
            if (servicioVideojuegos.agregarVideojuego(juego)) {
                System.out.println("Videojuego agregado con código: " + codigo);
            } else {
                System.out.println("No se pudo agregar el videojuego (datos inválidos)");
            }
        } catch (NumberFormatException error) {
            System.out.println("Error: precio o stock inválido");
        }
    }

    public void eliminarVideojuego() {
        System.out.print("Código del videojuego a eliminar: ");
        String codigo = entrada.nextLine().trim();
        if (servicioVideojuegos.eliminarVideojuego(codigo)) {
            System.out.println("Videojuego eliminado correctamente");
        } else {
            System.out.println("No existe videojuego con ese código");
        }
    }

    public void mostrarCatalogo() {
        List<Videojuego> catalogo = servicioVideojuegos.obtenerTodos();
        if (catalogo.isEmpty()) {
            System.out.println("No hay videojuegos en el catálogo");
        } else {
            for (Videojuego juego : catalogo) {
                System.out.println(juego);
            }
        }
    }

    public void menuClientes() {
        System.out.println("\n--- Gestión de Clientes ---");
        System.out.println("1) Registrar cliente");
        System.out.println("2) Eliminar cliente");
        System.out.println("3) Mostrar clientes");
        System.out.println("0) Regresar");
        System.out.print("Opción: ");
        String opcion = entrada.nextLine().trim();
        switch (opcion) {
            case "1" -> registrarCliente();
            case "2" -> eliminarCliente();
            case "3" -> mostrarClientes();
            case "0" -> {
            }
            default -> System.out.println("Opción no válida");
        }
    }

    public void registrarCliente() {
        System.out.print("Nombre completo: ");
        String nombre = entrada.nextLine().trim();
        System.out.print("Email: ");
        String email = entrada.nextLine().trim();
        System.out.print("Teléfono: ");
        String telefono = entrada.nextLine().trim();
        
        String codigo = UUID.randomUUID().toString();
        Cliente cliente = new Cliente(codigo, nombre, email, telefono);
        if (servicioClientes.registrarCliente(cliente)) {
            System.out.println("Cliente registrado con código: " + codigo);
        } else {
            System.out.println("No se pudo registrar el cliente (datos inválidos)");
        }
    }

    public void eliminarCliente() {
        System.out.print("Código del cliente a eliminar: ");
        String codigo = entrada.nextLine().trim();
        if (servicioClientes.eliminarCliente(codigo)) {
            System.out.println("Cliente eliminado correctamente");
        } else {
            System.out.println("No existe cliente con ese código");
        }
    }

    public void mostrarClientes() {
        List<Cliente> clientes = servicioClientes.obtenerTodos();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    public void menuVentas() {
        System.out.println("\n--- Gestión de Ventas ---");
        System.out.println("1) Realizar venta");
        System.out.println("2) Cancelar venta");
        System.out.println("3) Mostrar ventas");
        System.out.println("0) Regresar");
        System.out.print("Opción: ");
        String opcion = entrada.nextLine().trim();
        switch (opcion) {
            case "1" -> realizarVenta();
            case "2" -> cancelarVenta();
            case "3" -> mostrarVentas();
            case "0" -> {
            }
            default -> System.out.println("Opción no válida");
        }
    }

    public void realizarVenta() {
        System.out.print("Código del videojuego: ");
        String codigoJuego = entrada.nextLine().trim();
        System.out.print("Código del cliente: ");
        String codigoCliente = entrada.nextLine().trim();
        System.out.print("Cantidad de unidades: ");
        String cantidadStr = entrada.nextLine().trim();
        System.out.print("Fecha de venta (yyyy-MM-dd) o presione Enter para hoy: ");
        String fechaStr = entrada.nextLine().trim();
        
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            LocalDate fecha;
            if (fechaStr.isEmpty()) {
                fecha = LocalDate.now();
            } else {
                fecha = LocalDate.parse(fechaStr);
            }
            
            double precioTotal = 0.0;
            if (servicioVideojuegos.buscarPorCodigo(codigoJuego).isPresent()) {
                Videojuego juego = servicioVideojuegos.buscarPorCodigo(codigoJuego).get();
                precioTotal = juego.getPrecioUnitario() * cantidad;
            }
            
            String numeroVenta = UUID.randomUUID().toString();
            Venta venta = new Venta(numeroVenta, codigoJuego, codigoCliente, cantidad, precioTotal, fecha);
            
            if (servicioVentas.procesarVenta(venta)) {
                System.out.println("Venta realizada exitosamente. Número: " + numeroVenta);
                System.out.println("Total: $" + precioTotal);
            } else {
                System.out.println("No se pudo realizar la venta (stock insuficiente, cliente o juego no existe)");
            }
        } catch (NumberFormatException error) {
            System.out.println("Error: cantidad inválida");
        } catch (Exception error) {
            System.out.println("Error: formato de fecha inválido, use yyyy-MM-dd");
        }
    }

    public void cancelarVenta() {
        System.out.print("Número de venta a cancelar: ");
        String numeroVenta = entrada.nextLine().trim();
        if (servicioVentas.cancelarVenta(numeroVenta)) {
            System.out.println("Venta cancelada, stock recuperado");
        } else {
            System.out.println("No existe venta con ese número");
        }
    }

    public void mostrarVentas() {
        List<Venta> ventas = servicioVentas.obtenerTodas();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas");
        } else {
            for (Venta venta : ventas) {
                System.out.println(venta);
            }
        }
    }
}

