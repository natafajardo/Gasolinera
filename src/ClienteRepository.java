import java.util.List;

public interface ClienteRepository {

    List<Cliente> cargar();

    void guardar(List<Cliente> clientes);
}