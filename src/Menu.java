import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;


public class Menu {

    private final Scanner scanner;
    private final ClienteGestor clientegestor;
    private final PagoGestor pagoGestor;
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);



    public Menu(ClienteGestor clientegestor, PagoGestor pagoGestor) {
        this.scanner = new Scanner(System.in);
        this.clientegestor = clientegestor;
        this.pagoGestor = pagoGestor;
    }

    public void iniciar() {

        int opcion;

        do {
            mostrarMenu();

            System.out.print("Selecciona una opción: ");
            opcion = leerEntero();

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
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

            System.out.println();

        } while (opcion != 0);
    }

    private void mostrarMenu() {

        System.out.println("=================================");
        System.out.println("       GESTIÓN GASOLINERA");
        System.out.println("=================================");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Buscar clientes");
        System.out.println("4. Procesar pago de repostaje");
        System.out.println("4. 5. Consultar pagos");
        System.out.println("0. Salir");
        System.out.println("=================================");
    }

    private void registrarCliente() {

        System.out.println();
        System.out.println("--- REGISTRAR CLIENTE ---");

        String nombre;

        do {
            System.out.print("Nombre: ");
            nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("El nombre es obligatorio.");
            }

        } while (nombre.isEmpty());


        String telefono;

        do {
            System.out.print("Teléfono: ");
            telefono = scanner.nextLine().trim();

            if (telefono.isEmpty()) {
                System.out.println("El teléfono es obligatorio.");
            }

        } while (telefono.isEmpty());


        String matricula;

        do {
            System.out.print("Matrícula: ");
            matricula = scanner.nextLine().trim();

            if (matricula.isEmpty()) {
                System.out.println("La matrícula es obligatoria.");
            }

        } while (matricula.isEmpty());


        matricula = matricula.toUpperCase();


        if (clientegestor.existeMatricula(matricula)) {

            System.out.println(
                    "No se puede registrar el cliente."
            );

            System.out.println(
                    "La matrícula ya está registrada."
            );

            return;
        }


        try {

            Cliente cliente = clientegestor.registrar(
                    nombre,
                    telefono,
                    matricula
            );

            System.out.println();
            System.out.println("Cliente registrado correctamente.");
            System.out.println("ID asignado: " + cliente.getId());

        } catch (Exception e) {

            System.out.println(
                    "No se pudo guardar el cliente: "
                            + e.getMessage()
            );
        }
    }

    private void listarClientes() {

        System.out.println();
        System.out.println("--- LISTADO DE CLIENTES ---");

        List<Cliente> clientes = clientegestor.listar();

        mostrarClientes(clientes);
    }

    private void buscarClientes() {

        System.out.println();
        System.out.println("--- BUSCAR CLIENTES ---");

        String texto;

        do {

            System.out.print("Texto a buscar: ");
            texto = scanner.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println(
                        "La búsqueda no puede estar vacía."
                );
            }

        } while (texto.isEmpty());


        List<Cliente> clientes = clientegestor.buscar(texto);

        mostrarClientes(clientes);
    }

    private void mostrarClientes(List<Cliente> clientes) {

        if (clientes.isEmpty()) {

            System.out.println("No hay clientes.");

            return;
        }

        System.out.println();
        System.out.printf(
                "%-5s %-20s %-15s %-12s%n",
                "ID",
                "NOMBRE",
                "TELÉFONO",
                "MATRÍCULA"
        );

        System.out.println(
                "-------------------------------------------------------"
        );

        for (Cliente cliente : clientes) {

            System.out.printf(
                    "%-5d %-20s %-15s %-12s%n",
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getTelefono(),
                    cliente.getMatricula()
            );
        }
    }

    private int leerEntero() {

        while (true) {

            String texto = scanner.nextLine();

            try {

                return Integer.parseInt(texto);

            } catch (NumberFormatException e) {

                System.out.print(
                        "Introduce un número válido: "
                );
            }
        }
    }

    private void registrarPago() {

        System.out.println(
                "--- PROCESAR PAGO DE REPOSTAJE ---");


        // Primero comprobamos si existen clientes.

        List<Cliente> clientes =
                clientegestor.listar();

        if (clientes.isEmpty()) {

            System.out.println(
                    "No hay clientes registrados.");

            System.out.println(
                    "Registra primero un cliente.");

            return;
        }


        // Mostramos los clientes para
        // que el usuario pueda elegir uno.

        mostrarClientes(clientes);


        int idCliente;

        while (true) {

            idCliente =
                    leerEntero(
                            "Identificador del cliente: ");


            if (idCliente <= 0) {

                System.out.println(
                        "El identificador debe ser "
                                + "un número entero positivo.");

                continue;
            }


            Cliente cliente =
                    clientegestor.buscarPorId(
                            idCliente);


            if (cliente == null) {

                System.out.println(
                        "El cliente no existe.");

                return;
            }


            break;
        }


        // FECHA

        LocalDate fecha =
                leerFecha();


        // IMPORTE

        BigDecimal importe =
                leerDecimalPositivo(
                        "Importe: ");


        // LITROS

        BigDecimal litros =
                leerDecimalPositivo(
                        "Litros: ");


        // COMBUSTIBLE

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
                    clientegestor.buscarPorId(
                            idCliente);


            System.out.println();

            System.out.println(
                    "Pago registrado correctamente.");

            System.out.println(
                    "ID del pago: "
                            + pago.getId());

            System.out.println(
                    "Cliente: "
                            + cliente.getNombre());

            System.out.printf(
                    "Importe: %.2f%n",
                    pago.getImporte());


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
                "%-5s %-10s %-12s %-12s %-12s %-15s%n",
                "ID",
                "CLIENTE",
                "FECHA",
                "IMPORTE",
                "LITROS",
                "COMBUSTIBLE");


        System.out.println(
                "----------------------------------------------------------------");


        for (Pago pago : pagos) {

            Cliente cliente =
                    clientegestor.buscarPorId(
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
                    "%-5d %-10d %-12s %-12.2f %-12.2f %-15s%n",

                    pago.getId(),

                    pago.getIdCliente(),

                    pago.getFecha()
                            .format(FORMATO_FECHA),

                    pago.getImporte(),

                    pago.getLitros(),

                    pago.getCombustible());
        }
    }



    // MÉTODOS DE LECTURA


    private String leerTextoObligatorio(
            String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto =
                    scanner.nextLine().trim();


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
                    scanner.nextLine().trim();


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
                    scanner.nextLine().trim();


            texto =
                    texto.replace(',', '.');


            try {

                BigDecimal valor =
                        new BigDecimal(texto);


                if (valor.compareTo(
                        BigDecimal.ZERO) <= 0) {

                    System.out.println(
                            "El valor debe ser mayor que cero.");

                    continue;
                }


                if (valor.scale() > 2) {

                    System.out.println(
                            "El valor puede tener "
                                    + "como máximo 2 decimales.");

                    continue;
                }


                return valor;


            } catch (NumberFormatException e) {

                System.out.println(
                        "Introduce un número válido.");
            }
        }
    }


    private LocalDate leerFecha() {

        while (true) {

            System.out.print(
                    "Fecha (dd/MM/yyyy, Enter = hoy): ");

            String texto =
                    scanner.nextLine().trim();


            if (texto.isEmpty()) {

                return LocalDate.now();
            }


            try {

                return LocalDate.parse(
                        texto,
                        FORMATO_FECHA);


            } catch (DateTimeParseException e) {

                System.out.println(
                        "Fecha no válida.");
            }
        }
    }



}