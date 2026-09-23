import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public Pago registrar(int idCliente,
                          LocalDate fecha,
                          BigDecimal importe,
                          BigDecimal litros,
                          String combustible) {

        Cliente cliente = new ClienteGestor().buscarPorId(idCliente);


        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe !");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        if (importe == null) {

            throw new IllegalArgumentException("Importe obligatorio");
        }

        if (litros == null || litros.compareTo(BigDecimal.ZERO) <=0) {

            throw new IllegalArgumentException("Litros debe ser mayor a 0");
        }

        if (combustible.isEmpty()) {

            throw new IllegalArgumentException("El tipo de combustible es obligatorio");
        }



        return null;
    }

}

