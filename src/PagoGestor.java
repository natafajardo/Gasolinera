import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PagoGestor {

    private List<Pago> pagos;
    private PagoRepository repository;
    private ClienteGestor clienteGestor;

    public PagoGestor(PagoRepository repository, ClienteGestor clienteGestor) {

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


    public Pago registrar(int idCliente, LocalDate fecha, BigDecimal importe, BigDecimal litros, String combustible) {
        Cliente cliente = clienteGestor.buscarPorId(idCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe.");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }

        if (importe == null) {
            throw new IllegalArgumentException("El importe es obligatorio.");
        }

        if (importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor que cero.");
        }

        if (importe.scale() > 2)
            throw new IllegalArgumentException("El importe puede tener " + "como máximo dos decimales.");

        if (litros == null) {
            throw new IllegalArgumentException("Los litros son obligatorios.");
        }

        if (litros.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Los litros deben ser mayores que cero.");
        }

        if (litros.scale() > 2) {
            throw new IllegalArgumentException("Los litros pueden tener " + "como máximo dos decimales.");
        }

        combustible = combustible.trim();

        if (combustible.isEmpty()) {
            throw new IllegalArgumentException("El combustible es obligatorio.");
        }

        int id = siguienteId();

        Pago nuevoPago = new Pago(id, idCliente, fecha, importe, litros, combustible);
        List<Pago> nuevaLista = new ArrayList<>(pagos);
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
        List<Pago> resultado = new ArrayList<>(pagos);
        ordenarPagos(resultado);
        return resultado;
    }



    private void ordenarPagos(List<Pago> lista) {
        for (int i = 1; i < lista.size(); i++) {
            Pago actual = lista.get(i);
            int j = i - 1;

            while (j >= 0 && debeIrDespues(lista.get(j), actual)) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }
    }

    private boolean debeIrDespues(Pago a, Pago b) {
        boolean fechaAnterior = a.getFecha().compareTo(b.getFecha()) < 0;
        boolean mismaFecha = a.getFecha().compareTo(b.getFecha()) == 0;
        boolean idMenor = a.getId() < b.getId();

        return fechaAnterior || (mismaFecha && idMenor); // true si 'a' es más antigua, o si tiene la misma fecha y menor ID
    }


}