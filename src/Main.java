import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner =
                new Scanner(System.in);

        Path carpetaJSON =
                Paths.get("datos");

        Path archivoClientes =
                carpetaJSON.resolve("clientes.json");

        Path archivoPagos =
                carpetaJSON.resolve("pagos.json");

        boolean existeJSON =
                Files.exists(archivoClientes)
                        || Files.exists(archivoPagos);

        /*
         * Solo preguntamos por migración cuando
         * todavía no existe almacenamiento JSON.
         */
        if (!existeJSON) {

            System.out.print(
                    "No existe almacenamiento JSON. "
                            + "¿Quieres migrar datos CSV? (s/n): "
            );

            String respuesta =
                    scanner.nextLine()
                            .trim()
                            .toLowerCase();

            if (respuesta.equals("s")) {

                System.out.print(
                        "Ruta de la carpeta con los CSV: "
                );

                String rutaCSV =
                        scanner.nextLine().trim();

                Path carpetaCSV =
                        Paths.get(rutaCSV);

                try {

                    MigraCSVToJson migrador =
                            new MigraCSVToJson(
                                    carpetaCSV
                            );

                    migrador.migrar(
                            carpetaJSON
                    );

                } catch (IOException e) {

                    System.out.println(
                            "Migración cancelada: "
                                    + e.getMessage()
                    );

                    return;
                }

            } else {

                try {

                    Files.createDirectories(
                            carpetaJSON
                    );

                    AlmacenamientoJSON almacenamiento =
                            new AlmacenamientoJSON(
                                    carpetaJSON
                            );

                    almacenamiento.guardarClientes(
                            new java.util.ArrayList<>()
                    );

                    almacenamiento.guardarPagos(
                            new java.util.ArrayList<>()
                    );

                } catch (IOException e) {

                    System.out.println(
                            "No se pudo crear "
                                    + "el almacenamiento JSON: "
                                    + e.getMessage()
                    );

                    return;
                }
            }
        }

        Almacenamiento almacenamiento =
                new AlmacenamientoJSON(
                        carpetaJSON
                );

        ClienteGestor clienteGestor =
                new ClienteGestor(
                        almacenamiento
                );

        PagoGestor pagoGestor =
                new PagoGestor(
                        almacenamiento,
                        clienteGestor
                );
        Menu menu =
                new Menu(
                        clienteGestor,
                        pagoGestor
                );

        menu.iniciar();

    }
}