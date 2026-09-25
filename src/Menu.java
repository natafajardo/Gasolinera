import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final ClienteGestor clientegestor;

    public Menu(ClienteGestor clientegestor) {
        this.scanner = new Scanner(System.in);
        this.clientegestor = clientegestor;
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
}