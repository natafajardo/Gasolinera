import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final ClienteGestor clienteGestor;
    private final PagoGestor pagoGestor;

    private static final DateTimeFormatter
            FORMATO_FECHA =
            DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(
                            ResolverStyle.STRICT);

    public Menu(
            ClienteGestor clienteGestor,
            PagoGestor pagoGestor) {

        this.scanner =
                new Scanner(System.in);

        this.clienteGestor =
                clienteGestor;

        this.pagoGestor =
                pagoGestor;
    }

    public void iniciar() {

        int opcion;

        do {

            mostrarMenu();

            opcion =
                    leerEntero(
                            "Opción: ");

            switch (opcion) {

                case 1:
                    registrarCliente();
                    break;

                case 2:
                    listarClientes();
                    break;

                case 3:
                    buscarClientes();
                    break;

                case 4:
                    registrarPago();
                    break;

                case 5:
                    listarPagos();
                    break;

                case 0:
                    System.out.println(
                            "Hasta pronto.");
                    break;

                default:
                    System.out.println(
                            "Opción no válida.");
            }

            System.out.println();

        } while (opcion != 0);
    }

    private void mostrarMenu() {

        System.out.println(
                "====================================");

        System.out.println(
                "       GESTIÓN DE GASOLINERA");

        System.out.println(
                "====================================");

        System.out.println(
                "1. Dar de alta un cliente");

        System.out.println(
                "2. Listar clientes");

        System.out.println(
                "3. Buscar clientes");

        System.out.println(
                "4. Procesar un pago de repostaje");

        System.out.println(
                "5. Consultar pagos");

        System.out.println(
                "0. Salir");

        System.out.println(
                "====================================");
    }

    private void registrarCliente() {

        System.out.println(
                "--- DAR DE ALTA CLIENTE ---");

        String nombre =
                leerTextoObligatorio(
                        "Nombre: ");

        String telefono =
                leerTextoObligatorio(
                        "Teléfono: ");

        String matricula =
                leerTextoObligatorio(
                        "Matrícula: ");

        try {

            Cliente cliente =
                    clienteGestor.registrar(
                            nombre,
                            telefono,
                            matricula);

            System.out.println(
                    "Cliente registrado correctamente.");

            System.out.println(
                    "ID asignado: "
                            + cliente.getId());

        } catch (RuntimeException e) {

            System.out.println(
                    "No se pudo registrar el cliente: "
                            + e.getMessage());
        }
    }

    private void listarClientes() {

        System.out.println(
                "--- LISTADO DE CLIENTES ---");

        List<Cliente> clientes =
                clienteGestor.listar();

        mostrarClientes(clientes);
    }

    private void buscarClientes() {

        System.out.println(
                "--- BUSCAR CLIENTES ---");

        String texto =
                leerTextoObligatorio(
                        "Texto que buscar: ");

        List<Cliente> clientes =
                clienteGestor.buscar(texto);

        mostrarClientes(clientes);
    }

    private void mostrarClientes(
            List<Cliente> clientes) {

        if (clientes.isEmpty()) {

            System.out.println(
                    "No se han encontrado clientes.");

            return;
        }

        System.out.printf(
                "%-5s %-20s %-15s %-12s%n",
                "ID",
                "NOMBRE",
                "TELÉFONO",
                "MATRÍCULA");

        System.out.println(
                "------------------------------------------------");

        for (Cliente cliente : clientes) {

            System.out.printf(
                    "%-5d %-20s %-15s %-12s%n",
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getTelefono(),
                    cliente.getMatricula());
        }
    }

    private void registrarPago() {

        System.out.println(
                "--- PROCESAR PAGO DE REPOSTAJE ---");

        List<Cliente> clientes =
                clienteGestor.listar();

        if (clientes.isEmpty()) {

            System.out.println(
                    "No hay clientes registrados.");

            System.out.println(
                    "Registra primero un cliente.");

            return;
        }

        mostrarClientes(clientes);

        int idCliente;

        while (true) {

            idCliente =
                    leerEntero(
                            "ID del cliente: ");

            if (idCliente <= 0) {

                System.out.println(
                        "El ID debe ser positivo.");

                continue;
            }

            Cliente cliente =
                    clienteGestor.buscarPorId(
                            idCliente);

            if (cliente == null) {

                System.out.println(
                        "No existe un cliente con "
                                + "ese identificador.");

                return;
            }

            break;
        }

        LocalDate fecha =
                leerFecha();

        BigDecimal importe =
                leerDecimalPositivo(
                        "Importe (€): ");

        BigDecimal litros =
                leerDecimalPositivo(
                        "Litros: ");

        String combustible =
                leerTextoObligatorio(
                        "Combustible: ");

        try {

            Pago pago =
                    pagoGestor.registrar(
                            idCliente,
                            fecha,
                            importe,
                            litros,
                            combustible);

            Cliente cliente =
                    clienteGestor.buscarPorId(
                            idCliente);

            System.out.println();

            System.out.println(
                    "Pago "
                            + pago.getId()
                            + " registrado para "
                            + cliente.getNombre()
                            + ": "
                            + pago.getImporte()
                            .toPlainString()
                            + " €.");

        } catch (RuntimeException e) {

            System.out.println(
                    "No se pudo registrar el pago: "
                            + e.getMessage());
        }
    }

    private void listarPagos() {

        System.out.println(
                "--- CONSULTA DE PAGOS ---");

        List<Pago> pagos =
                pagoGestor.listar();

        if (pagos.isEmpty()) {

            System.out.println(
                    "No hay pagos registrados.");

            return;
        }

        System.out.printf(
                "%-5s %-20s %-12s %-12s %-12s %-15s%n",
                "ID",
                "CLIENTE",
                "FECHA",
                "IMPORTE",
                "LITROS",
                "COMBUSTIBLE");

        System.out.println(
                "--------------------------------------------------------------------------");

        for (Pago pago : pagos) {

            Cliente cliente =
                    clienteGestor.buscarPorId(
                            pago.getIdCliente());

            String nombreCliente;

            if (cliente != null) {

                nombreCliente =
                        cliente.getNombre();

            } else {

                nombreCliente =
                        "Desconocido";
            }

            System.out.printf(
                    "%-5d %-20s %-12s %-12.2f %-12.2f %-15s%n",

                    pago.getId(),

                    nombreCliente,

                    pago.getFecha()
                            .format(
                                    FORMATO_FECHA),

                    pago.getImporte(),

                    pago.getLitros(),

                    pago.getCombustible());
        }
    }

    private String leerTextoObligatorio(
            String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto =
                    scanner.nextLine()
                            .trim();

            if (!texto.isEmpty()) {

                return texto;
            }

            System.out.println(
                    "Este campo es obligatorio.");
        }
    }

    private int leerEntero(
            String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto =
                    scanner.nextLine()
                            .trim();

            try {

                return Integer.parseInt(texto);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Introduce un número entero válido.");
            }
        }
    }

    private BigDecimal leerDecimalPositivo(
            String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto =
                    scanner.nextLine()
                            .trim();

            // Permitimos coma o punto.
            texto =
                    texto.replace(',', '.');

            try {

                BigDecimal valor =
                        new BigDecimal(texto);

                if (valor.compareTo(
                        BigDecimal.ZERO) <= 0) {

                    System.out.println(
                            "Debe ser mayor que cero.");

                    continue;
                }

                if (valor.scale() > 2) {

                    System.out.println(
                            "Puede tener como máximo "
                                    + "dos decimales.");

                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Introduce una cantidad válida.");
            }
        }
    }

    private LocalDate leerFecha() {

        while (true) {

            System.out.print(
                    "Fecha (dd/MM/aaaa; "
                            + "Enter = hoy): ");

            String texto =
                    scanner.nextLine()
                            .trim();

            if (texto.isEmpty()) {

                return LocalDate.now();
            }

            try {

                return LocalDate.parse(
                        texto,
                        FORMATO_FECHA);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "La fecha no es válida.");
            }
        }
    }
}