import java.io.IOException;
import java.util.List;

public interface Almacenamiento {

    List<Cliente> cargarClientes() throws IOException;

    void guardarClientes(List<Cliente> clientes) throws IOException;

    List<Pago> cargarPagos() throws IOException;

    void guardarPagos(List<Pago> pagos) throws IOException;
}
