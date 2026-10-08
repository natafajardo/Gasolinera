import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MigraCSVToJson {

    private final AlmacenamientoCSV almacenamientoCSV;

    public MigraCSVToJson(Path carpetaCSV) {
        almacenamientoCSV =
                new AlmacenamientoCSV(carpetaCSV);
    }

    public void migrar(Path carpetaJSON)
            throws IOException {

        if (Files.exists(carpetaJSON)) {

            throw new IOException(
                    "El destino ya existe: "
                            + carpetaJSON
            );
        }

        Path archivoClientes =
                carpetaJSON.resolve("clientes.json");

        Path archivoPagos =
                carpetaJSON.resolve("pagos.json");

        if (Files.exists(archivoClientes)
                || Files.exists(archivoPagos)) {

            throw new IOException(
                    "El destino ya contiene archivos JSON."
            );
        }

        /*
         * Primero leemos TODO el origen.
         *
         * Si hay un error aquí, todavía no hemos escrito
         * ningún JSON.
         */
        List<Cliente> clientes =
                almacenamientoCSV.cargarClientes();

        List<Pago> pagos =
                almacenamientoCSV.cargarPagos();

        validarRelaciones(clientes, pagos);

        /*
         * Creamos el destino solo después de comprobar
         * que los datos de origen son válidos.
         */
        Files.createDirectories(carpetaJSON);

        AlmacenamientoJSON almacenamientoJSON =
                new AlmacenamientoJSON(carpetaJSON);

        try {

            almacenamientoJSON.guardarClientes(
                    clientes
            );

            almacenamientoJSON.guardarPagos(
                    pagos
            );

        } catch (IOException e) {

            /*
             * Si algo falla durante la escritura,
             * intentamos eliminar los archivos creados
             * para no dejar una migración parcial.
             */
            try {
                Files.deleteIfExists(
                        almacenamientoJSON.getArchivoClientes()
                );

                Files.deleteIfExists(
                        almacenamientoJSON.getArchivoPagos()
                );

                Files.deleteIfExists(
                        carpetaJSON
                );

            } catch (IOException ignorado) {
                // No ocultamos el error original.
            }

            throw new IOException(
                    "No se pudo completar la migración: "
                            + e.getMessage(),
                    e
            );
        }

        System.out.println(
                "Validación completada."
        );

        System.out.println(
                "Clientes migrados: "
                        + clientes.size()
        );

        System.out.println(
                "Pagos migrados: "
                        + pagos.size()
        );

        System.out.println(
                "Migración completada en "
                        + carpetaJSON
        );
    }

    private void validarRelaciones(
            List<Cliente> clientes,
            List<Pago> pagos) throws IOException {

        for (Pago pago : pagos) {

            boolean existeCliente = false;

            for (Cliente cliente : clientes) {

                if (cliente.getId()
                        == pago.getIdCliente()) {

                    existeCliente = true;
                    break;
                }
            }

            if (!existeCliente) {

                throw new IOException(
                        "El pago "
                                + pago.getId()
                                + " hace referencia al cliente "
                                + pago.getIdCliente()
                                + " que no existe."
                );
            }
        }
    }
}