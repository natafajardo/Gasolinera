import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryCSV implements ClienteRepository {

    private final Path archivo;

    public ClienteRepositoryCSV(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Cliente> cargar() {

        List<Cliente> clientes = new ArrayList<>();

        if (!Files.exists(archivo)) {
            return clientes;
        }

        try (
                BufferedReader reader = Files.newBufferedReader(
                        archivo,
                        StandardCharsets.UTF_8
                )
        ) {

            String linea;

            // Leer la cabecera
            reader.readLine();

            while ((linea = reader.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(";", -1);

                int id = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String telefono = datos[2];
                String matricula = datos[3];

                Cliente cliente = new Cliente(
                        id,
                        nombre,
                        telefono,
                        matricula
                );

                clientes.add(cliente);
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "No se pudieron cargar los clientes.",
                    e
            );
        }

        return clientes;
    }

    @Override
    public void guardar(List<Cliente> clientes) {

        try {

            Path carpeta = archivo.getParent();

            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }

            try (
                    BufferedWriter writer = Files.newBufferedWriter(
                            archivo,
                            StandardCharsets.UTF_8
                    )
            ) {

                writer.write(
                        "id;nombre;telefono;matricula"
                );

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

            throw new RuntimeException(
                    "No se pudieron guardar los clientes.",
                    e
            );
        }
    }
}