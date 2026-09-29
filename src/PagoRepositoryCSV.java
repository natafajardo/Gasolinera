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

public class PagoRepositoryCSV implements PagoRepository {

    private final Path ruta;

    private static final String CABECERA =
            "id;idCliente;fecha;importe;litros;combustible";

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public PagoRepositoryCSV(Path ruta) {
        this.ruta = ruta;
    }


    @Override
    public List<Pago> cargar() {

        List<Pago> pagos = new ArrayList<>();

        try {

            if (!Files.exists(ruta)) {

                Files.createDirectories(ruta.getParent());

                try (BufferedWriter writer =
                             Files.newBufferedWriter(
                                     ruta,
                                     StandardCharsets.UTF_8)) {

                    writer.write(CABECERA);
                    writer.newLine();
                }

                return pagos;
            }


            try (BufferedReader reader =
                         Files.newBufferedReader(
                                 ruta,
                                 StandardCharsets.UTF_8)) {

                String linea = reader.readLine();

                // Saltamos la cabecera
                linea = reader.readLine();

                while (linea != null) {

                    if (!linea.isBlank()) {

                        String[] campos = linea.split(";", -1);

                        if (campos.length != 6) {
                            throw new IllegalStateException(
                                    "Registro de pago incorrecto: "
                                            + linea);
                        }

                        int id =
                                Integer.parseInt(campos[0]);

                        int idCliente =
                                Integer.parseInt(campos[1]);

                        LocalDate fecha =
                                LocalDate.parse(
                                        campos[2],
                                        FORMATO_FECHA);

                        BigDecimal importe =
                                new BigDecimal(
                                        campos[3].replace(',', '.'));

                        BigDecimal litros =
                                new BigDecimal(
                                        campos[4].replace(',', '.'));

                        String combustible =
                                campos[5];

                        Pago pago = new Pago(
                                id,
                                idCliente,
                                fecha,
                                importe,
                                litros,
                                combustible
                        );

                        pagos.add(pago);
                    }

                    linea = reader.readLine();
                }
            }

        } catch (IOException | RuntimeException e) {

            throw new IllegalStateException(
                    "No se pudieron cargar los pagos: "
                            + e.getMessage(),
                    e);
        }

        return pagos;
    }


    @Override
    public void guardar(List<Pago> pagos) {

        try {

            if (ruta.getParent() != null) {
                Files.createDirectories(ruta.getParent());
            }

            try (BufferedWriter writer =
                         Files.newBufferedWriter(
                                 ruta,
                                 StandardCharsets.UTF_8)) {

                writer.write(CABECERA);
                writer.newLine();

                for (Pago pago : pagos) {

                    writer.write(
                            pago.getId()
                                    + ";"
                                    + pago.getIdCliente()
                                    + ";"
                                    + pago.getFecha()
                                    .format(FORMATO_FECHA)
                                    + ";"
                                    + pago.getImporte()
                                    .toPlainString()
                                    + ";"
                                    + pago.getLitros()
                                    .toPlainString()
                                    + ";"
                                    + pago.getCombustible()
                    );

                    writer.newLine();
                }
            }

        } catch (IOException e) {

            throw new IllegalStateException(
                    "No se pudieron guardar los pagos: "
                            + e.getMessage(),
                    e);
        }
    }
}