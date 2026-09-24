import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PagoGestor {

    private List<Pago> pagos;
    private ClienteGestor clienteGestor;

    public PagoGestor(ClienteGestor clienteGestor) {
        this.clienteGestor = clienteGestor;
        this.pagos = new ArrayList<>();
    }

    private int siguienteId() {
        int max = 0;

        for (Pago pago : pagos) {
            if (pago.getId() > max) {
                max = pago.getId();
            }
        }

        return max + 1;
    }

    public Pago registrar(
            int idCliente,
            LocalDate fecha,
            BigDecimal importe,
            BigDecimal litros,
            String combustible) {

        Cliente cliente = clienteGestor.buscarPorId(idCliente);

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no existe.");
        }

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha es obligatoria.");
        }

        if (importe == null ||
                importe.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "El importe debe ser mayor que cero.");
        }

        if (litros == null ||
                litros.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Los litros deben ser mayores que cero.");
        }

        combustible = combustible.trim();

        if (combustible.isEmpty()) {
            throw new IllegalArgumentException(
                    "El combustible es obligatorio.");
        }

        int id = siguienteId();

        Pago nuevoPago = new Pago(
                id,
                idCliente,
                fecha,
                importe,
                litros,
                combustible
        );

        pagos.add(nuevoPago);

        return nuevoPago;
    }

    public List<Pago> listar() {

        List<Pago> resultado =
                new ArrayList<>(pagos);

        resultado.sort(Comparator.comparingInt(Pago::getId)
        );

        return resultado;
    }

    public Pago buscarPorId(int id) {

        for (Pago pago : pagos) {

            if (pago.getId() == id) {
                return pago;
            }
        }

        return null;
    }

}

