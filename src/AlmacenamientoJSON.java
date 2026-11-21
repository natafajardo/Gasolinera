import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlmacenamientoJSON implements Almacenamiento {

    private final Path archivoClientes;
    private final Path archivoPagos;

    public AlmacenamientoJSON(Path carpeta) {
        this.archivoClientes = carpeta.resolve("clientes.json");
        this.archivoPagos = carpeta.resolve("pagos.json");
    }

    @Override
    public List<Cliente> cargarClientes() throws IOException {

        if (!Files.exists(archivoClientes)) {
            return new ArrayList<>();
        }

        String contenido = Files.readString(
                archivoClientes,
                StandardCharsets.UTF_8
        );

        if (contenido.trim().isEmpty()) {
            throw new IOException("El archivo de clientes está vacío.");
        }

        return JsonTraductor.jsonAClientes(contenido);
    }

    @Override
    public void guardarClientes(List<Cliente> clientes) throws IOException {

        crearCarpetaSiNoExiste();

        String json = JsonTraductor.clientesAJson(clientes);

        Files.writeString(
                archivoClientes,
                json,
                StandardCharsets.UTF_8
        );
    }

    @Override
    public List<Pago> cargarPagos() throws IOException {

        if (!Files.exists(archivoPagos)) {
            return new ArrayList<>();
        }

        String contenido = Files.readString(
                archivoPagos,
                StandardCharsets.UTF_8
        );

        if (contenido.trim().isEmpty()) {
            throw new IOException("El archivo de pagos está vacío.");
        }

        return JsonTraductor.jsonAPagos(contenido);
    }

    @Override
    public void guardarPagos(List<Pago> pagos) throws IOException {

        crearCarpetaSiNoExiste();

        String json = JsonTraductor.pagosAJson(pagos);

        Files.writeString(
                archivoPagos,
                json,
                StandardCharsets.UTF_8
        );
    }

    private void crearCarpetaSiNoExiste() throws IOException {

        Path carpeta = archivoClientes.getParent();

        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }
    }

    public Path getArchivoClientes() {
        return archivoClientes;
    }

    public Path getArchivoPagos() {
        return archivoPagos;
    }
}