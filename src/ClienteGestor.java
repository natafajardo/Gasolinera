import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



    public class ClienteGestor {

        private final ClienteRepository repository;
        private List<Cliente> clientes;

        public ClienteGestor(ClienteRepository repository) {

            this.repository = repository;

            this.clientes = repository.cargar();
        }

        public List<Cliente> listar() {

            List<Cliente> resultado =
                    new ArrayList<>(clientes);

            resultado.sort(
                    Comparator.comparing(
                            Cliente::getNombre,
                            String.CASE_INSENSITIVE_ORDER
                    ).thenComparing(Cliente::getId)
            );

            return resultado;
        }



        public boolean existeMatricula(String matricula) {

            for (Cliente cliente : clientes) {

                if (cliente.getMatricula()
                        .equalsIgnoreCase(matricula)) {

                    return true;
                }
            }

            return false;
        }

        private int siguienteId() {

            int maximo = 0;

            for (Cliente cliente : clientes) {

                if (cliente.getId() > maximo) {
                    maximo = cliente.getId();
                }
            }

            return maximo + 1;
        }

        public Cliente registrar(
                String nombre,
                String telefono,
                String matricula) {

            nombre = nombre.trim();
            telefono = telefono.trim();
            matricula = matricula.trim().toUpperCase();

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

            clientes.add(nuevoCliente);

            return nuevoCliente;
        }



        public List<Cliente> buscar(String texto) {

            texto = texto.trim();

            List<Cliente> resultado = new ArrayList<>();

            for (Cliente cliente : clientes) {

                if (cliente.getNombre()
                        .toLowerCase()
                        .contains(texto.toLowerCase())

                        || cliente.getTelefono()
                        .toLowerCase()
                        .contains(texto.toLowerCase())

                        || cliente.getMatricula()
                        .toLowerCase()
                        .contains(texto.toLowerCase())) {

                    resultado.add(cliente);
                }
            }

            resultado.sort(
                    Comparator
                            .comparing(
                                    Cliente::getNombre,
                                    String.CASE_INSENSITIVE_ORDER)
                            .thenComparingInt(Cliente::getId)
            );

            return resultado;
        }

        public Cliente buscarPorId(int id) {

            for (Cliente cliente : clientes) {

                if (cliente.getId() == id) {
                    return cliente;
                }
            }

            return null;

        }


    }