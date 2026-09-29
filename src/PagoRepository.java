import java.util.List;

public interface PagoRepository {
    List<Pago> cargar();

    void guardar(List<Pago> pagos);
}