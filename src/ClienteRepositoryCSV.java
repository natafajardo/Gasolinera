import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryCSV implements ClienteRepository {

    private final Path ruta;

    private static final String CABECERA =
            "id;nombre;telefono;matricula";

    public ClienteRepositoryCSV(Path ruta) {
        this.ruta = ruta;
    }

    @Override
    public List<Cliente> cargar() {

        List<Cliente> clientes =
                new ArrayList<>();

        try {

            // Si el archivo todavía no existe,
            // creamos la carpeta y el archivo.
            if (!Files.exists(ruta)) {

                if (ruta.getParent() != null) {
                    Files.createDirectories(
                            ruta.getParent());
                }

                try (BufferedWriter writer =
                             Files.newBufferedWriter(
                                     ruta,
                                     StandardCharsets.UTF_8)) {

                    writer.write(CABECERA);
                    writer.newLine();
                }

                return clientes;
            }

            try (BufferedReader reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {

                // Leemos la cabecera.
                String linea = reader.readLine();

                // Leemos la primera línea de datos.
                linea = reader.readLine();

                while (linea != null) {

                    if (!linea.isBlank()) {

                        String[] campos =
                                linea.split(";", -1);

                        if (campos.length != 4) {
                            throw new IllegalStateException(
                                    "Registro de cliente incorrecto: "
                                            + linea);
                        }

                        int id =
                                Integer.parseInt(
                                        campos[0]);

                        String nombre =
                                campos[1];

                        String telefono =
                                campos[2];

                        String matricula =
                                campos[3];

                        Cliente cliente =
                                new Cliente(
                                        id,
                                        nombre,
                                        telefono,
                                        matricula);

                        clientes.add(cliente);
                    }

                    linea = reader.readLine();
                }
            }

        } catch (IOException e) {

            throw new IllegalStateException(
                    "No se pudieron cargar los clientes: "
                            + e.getMessage(),
                    e);

        } catch (RuntimeException e) {

            throw new IllegalStateException(
                    "Hay un problema en los datos "
                            + "de clientes: "
                            + e.getMessage(),
                    e);
        }

        return clientes;
    }



    @Override
    public void guardar(List<Cliente> clientes) {

        try {

            if (ruta.getParent() != null) {
                Files.createDirectories(ruta.getParent());
            }

            try (BufferedWriter writer =
                         Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {

                // Escribimos la cabecera.
                writer.write(CABECERA);
                writer.newLine();

                for (Cliente cliente : clientes) {

                    writer.write(
                            cliente.getId()
                                    + ";"
                                    + cliente.getNombre()
                                    + ";"
                                    + cliente.getTelefono()
                                    + ";"
                                    + cliente.getMatricula()
                    );

                    writer.newLine();
                }
            }

        } catch (IOException e) {

            throw new IllegalStateException(
                    "No se pudieron guardar los clientes: "
                            + e.getMessage(),
                    e);
        }
    }
}