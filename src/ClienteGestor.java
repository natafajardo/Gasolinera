import java.util.ArrayList;
import java.util.List;

public class ClienteGestor {

    private List<Cliente> clientes;
    private ClienteRepository repository;

    public ClienteGestor(
            ClienteRepository repository) {

        this.repository = repository;

        // Al iniciar el programa cargamos
        // los clientes guardados en el archivo.
        this.clientes = repository.cargar();
    }

    private boolean existeMatricula(
            String matricula) {

        for (Cliente cliente : clientes) {

            if (cliente.getMatricula()
                    .equalsIgnoreCase(matricula)) {

                return true;
            }
        }

        return false;
    }

    private int siguienteId() {

        int mayor = 0;

        // Buscamos el ID más alto.
        for (Cliente cliente : clientes) {

            if (cliente.getId() > mayor) {
                mayor = cliente.getId();
            }
        }

        // El siguiente ID es el mayor + 1.
        return mayor + 1;
    }

    public Cliente registrar(
            String nombre,
            String telefono,
            String matricula) {

        nombre = nombre.trim();
        telefono = telefono.trim();
        matricula = matricula.trim()
                .toUpperCase();

        if (nombre.isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        if (telefono.isEmpty()) {
            throw new IllegalArgumentException(
                    "El teléfono es obligatorio.");
        }

        if (matricula.isEmpty()) {
            throw new IllegalArgumentException(
                    "La matrícula es obligatoria.");
        }

        if (existeMatricula(matricula)) {
            throw new IllegalArgumentException(
                    "La matrícula ya está registrada.");
        }

        int id = siguienteId();

        Cliente nuevoCliente =
                new Cliente(
                        id,
                        nombre,
                        telefono,
                        matricula);

        // Creamos una lista nueva para no modificar
        // la lista de memoria antes de guardar.
        List<Cliente> nuevaLista =
                new ArrayList<>(clientes);

        nuevaLista.add(nuevoCliente);

        // Primero intentamos guardar.
        repository.guardar(nuevaLista);

        // Solo si guardar ha funcionado,
        // actualizamos la lista en memoria.
        clientes = nuevaLista;

        return nuevoCliente;
    }

    public Cliente buscarPorId(int id) {

        for (Cliente cliente : clientes) {

            if (cliente.getId() == id) {
                return cliente;
            }
        }

        return null;
    }

    public List<Cliente> listar() {

        List<Cliente> resultado =
                new ArrayList<>(clientes);

        ordenarClientes(resultado);

        return resultado;
    }

    public List<Cliente> buscar(String texto) {

        texto = texto.trim()
                .toLowerCase();

        List<Cliente> resultado =
                new ArrayList<>();

        for (Cliente cliente : clientes) {

            String nombre =
                    cliente.getNombre()
                            .toLowerCase();

            String telefono =
                    cliente.getTelefono()
                            .toLowerCase();

            String matricula =
                    cliente.getMatricula()
                            .toLowerCase();

            if (nombre.contains(texto)
                    || telefono.contains(texto)
                    || matricula.contains(texto)) {

                resultado.add(cliente);
            }
        }

        ordenarClientes(resultado);

        return resultado;
    }

    private void ordenarClientes(
            List<Cliente> lista) {

        // Comparamos los clientes de dos en dos.

        for (int i = 0;
             i < lista.size() - 1;
             i++) {

            for (int j = i + 1;
                 j < lista.size();
                 j++) {

                Cliente cliente1 =
                        lista.get(i);

                Cliente cliente2 =
                        lista.get(j);

                String nombre1 =
                        cliente1.getNombre()
                                .toLowerCase();

                String nombre2 =
                        cliente2.getNombre()
                                .toLowerCase();

                boolean cambiar = false;

                // Primero ordenamos por nombre.
                if (nombre1.compareTo(nombre2) > 0) {

                    cambiar = true;

                } else if (
                        nombre1.equals(nombre2)
                                && cliente1.getId()
                                > cliente2.getId()) {

                    // Si los nombres son iguales,
                    // ordenamos por ID ascendente.
                    cambiar = true;
                }

                if (cambiar) {

                    lista.set(i, cliente2);
                    lista.set(j, cliente1);
                }
            }
        }
    }
}