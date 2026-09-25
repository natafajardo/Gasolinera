import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        try {

            Path rutaClientes = Paths.get(
                    "data",
                    "clientes.csv"
            );

            ClienteRepository repository = new ClienteRepositoryCSV(rutaClientes);

            ClienteGestor clientegestor = new ClienteGestor(repository);

            Menu menu =
                    new Menu(clientegestor);

            menu.iniciar();

        } catch (Exception e) {

            System.out.println(
                    "No se pudo iniciar la aplicación."
            );

            System.out.println(
                    "Motivo: " + e.getMessage()
            );
        }
    }
}