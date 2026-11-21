import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AlmacenamientoCSV implements Almacenamiento {

    private final Path archivoClientes;
    private final Path archivoPagos;

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AlmacenamientoCSV(Path carpeta) {

        this.archivoClientes =
                carpeta.resolve("clientes.csv");

        this.archivoPagos =
                carpeta.resolve("pagos.csv");
    }

    @Override
    public List<Cliente> cargarClientes() throws IOException {

        List<Cliente> clientes = new ArrayList<>();

        if (!Files.exists(archivoClientes)) {
            throw new IOException(
                    "No existe el archivo: "
                            + archivoClientes
            );
        }

        try (BufferedReader lector =
                     Files.newBufferedReader(
                             archivoClientes,
                             StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }
                if (linea.toLowerCase().startsWith("id;")) {
                    continue;
                }

                String[] partes = linea.split(
                        ";",
                        -1
                );

                if (partes.length != 4) {
                    throw new IOException(
                            "Registro de cliente inválido: "
                                    + linea
                    );
                }

                int id = Integer.parseInt(
                        partes[0]
                );

                clientes.add(
                        new Cliente(
                                id,
                                partes[1],
                                partes[2],
                                partes[3]
                        )
                );
            }
        }

        return clientes;
    }

    @Override
    public void guardarClientes(
            List<Cliente> clientes) throws IOException {

        crearCarpeta();

        try (BufferedWriter escritor =
                     Files.newBufferedWriter(
                             archivoClientes,
                             StandardCharsets.UTF_8)) {

            for (Cliente cliente : clientes) {

                escritor.write(
                        cliente.getId()
                                + ";"
                                + cliente.getNombre()
                                + ";"
                                + cliente.getTelefono()
                                + ";"
                                + cliente.getMatricula()
                );

                escritor.newLine();
            }
        }
    }

    @Override
    public List<Pago> cargarPagos() throws IOException {

        List<Pago> pagos = new ArrayList<>();

        if (!Files.exists(archivoPagos)) {
            throw new IOException(
                    "No existe el archivo: "
                            + archivoPagos
            );
        }

        try (BufferedReader lector =
                     Files.newBufferedReader(
                             archivoPagos,
                             StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }
                if (linea.toLowerCase().startsWith("id;")) {
                    continue;
                }


                String[] partes = linea.split(
                        ";",
                        -1
                );

                if (partes.length != 6) {
                    throw new IOException(
                            "Registro de pago inválido: "
                                    + linea
                    );
                }

                int id = Integer.parseInt(
                        partes[0]
                );

                int idCliente = Integer.parseInt(
                        partes[1]
                );

                LocalDate fecha =
                        LocalDate.parse(
                                partes[2],
                                formatoFecha
                        );

                BigDecimal importe =
                        new BigDecimal(partes[3]);

                BigDecimal litros =
                        new BigDecimal(partes[4]);

                String combustible = partes[5];

                pagos.add(
                        new Pago(
                                id,
                                idCliente,
                                fecha,
                                importe,
                                litros,
                                combustible
                        )
                );
            }
        }

        return pagos;
    }

    @Override
    public void guardarPagos(
            List<Pago> pagos) throws IOException {

        crearCarpeta();

        try (BufferedWriter escritor =
                     Files.newBufferedWriter(
                             archivoPagos,
                             StandardCharsets.UTF_8)) {

            for (Pago pago : pagos) {

                escritor.write(
                        pago.getId()
                                + ";"
                                + pago.getIdCliente()
                                + ";"
                                + pago.getFecha().format(formatoFecha)
                                + ";"
                                + pago.getImporte().toPlainString()
                                + ";"
                                + pago.getLitros().toPlainString()
                                + ";"
                                + pago.getCombustible()
                );

                escritor.newLine();
            }
        }
    }

    private void crearCarpeta() throws IOException {

        Path carpeta = archivoClientes.getParent();

        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }
    }
}