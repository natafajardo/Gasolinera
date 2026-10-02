import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoGestor {

    private List<Pago> pagos;
    private PagoRepository repository;
    private ClienteGestor clienteGestor;

    public PagoGestor(
            PagoRepository repository,
            ClienteGestor clienteGestor) {

        this.repository = repository;
        this.clienteGestor = clienteGestor;

        // Cargamos los pagos guardados.
        this.pagos = repository.cargar();
    }

    private int siguienteId() {

        int mayor = 0;

        for (Pago pago : pagos) {

            if (pago.getId() > mayor) {
                mayor = pago.getId();
            }
        }

        return mayor + 1;
    }

    public Pago registrar(
            int idCliente,
            LocalDate fecha,
            BigDecimal importe,
            BigDecimal litros,
            String combustible) {

        Cliente cliente =
                clienteGestor.buscarPorId(
                        idCliente);

        if (cliente == null) {

            throw new IllegalArgumentException(
                    "El cliente no existe.");
        }

        if (fecha == null) {

            throw new IllegalArgumentException(
                    "La fecha es obligatoria.");
        }

        if (importe == null) {

            throw new IllegalArgumentException(
                    "El importe es obligatorio.");
        }

        if (importe.compareTo(
                BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "El importe debe ser mayor que cero.");
        }

        if (importe.scale() > 2) {

            throw new IllegalArgumentException(
                    "El importe puede tener "
                            + "como máximo dos decimales.");
        }

        if (litros == null) {

            throw new IllegalArgumentException(
                    "Los litros son obligatorios.");
        }

        if (litros.compareTo(
                BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Los litros deben ser mayores que cero.");
        }

        if (litros.scale() > 2) {

            throw new IllegalArgumentException(
                    "Los litros pueden tener "
                            + "como máximo dos decimales.");
        }

        combustible =
                combustible.trim();

        if (combustible.isEmpty()) {

            throw new IllegalArgumentException(
                    "El combustible es obligatorio.");
        }

        int id = siguienteId();

        Pago nuevoPago =
                new Pago(
                        id,
                        idCliente,
                        fecha,
                        importe,
                        litros,
                        combustible);

        List<Pago> nuevaLista =
                new ArrayList<>(pagos);

        nuevaLista.add(nuevoPago);

        // Primero guardamos en el archivo.
        repository.guardar(nuevaLista);

        // Si no hubo error, actualizamos memoria.
        pagos = nuevaLista;

        return nuevoPago;
    }

    public Pago buscarPorId(int id) {

        for (Pago pago : pagos) {

            if (pago.getId() == id) {
                return pago;
            }
        }

        return null;
    }

    public List<Pago> listar() {

        List<Pago> resultado =
                new ArrayList<>(pagos);

        ordenarPagos(resultado);

        return resultado;
    }

    private void ordenarPagos(
            List<Pago> lista) {

        /*
         * Primero ordenamos por fecha descendente.
         * Si las fechas son iguales,
         * ordenamos por ID descendente.
         */
        for (int i = 0;
             i < lista.size() - 1;
             i++) {

            for (int j = i + 1;
                 j < lista.size();
                 j++) {

                Pago pago1 =
                        lista.get(i);

                Pago pago2 =
                        lista.get(j);

                boolean cambiar = false;

                /*
                 * compareTo devuelve:
                 *
                 * negativo -> fecha1 es anterior
                 * cero     -> fechas iguales
                 * positivo -> fecha1 es posterior
                 *
                 */
                if (pago1.getFecha()
                        .compareTo(
                                pago2.getFecha()) < 0) {

                    cambiar = true;

                } else if (
                        pago1.getFecha()
                                .equals(
                                        pago2.getFecha())
                                && pago1.getId()
                                < pago2.getId()) {

                    // Misma fecha:
                    // ID mayor primero.
                    cambiar = true;
                }

                if (cambiar) {

                    Pago temporal =
                            lista.get(i);

                    lista.set(
                            i,
                            lista.get(j));

                    lista.set(
                            j,
                            temporal);
                }
            }
        }
    }
}