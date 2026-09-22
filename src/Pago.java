import java.math.BigDecimal;
import java.time.LocalDate;

public class Pago {
    private final int id;
    private final int idCliente;
    private final LocalDate fecha;
    private final BigDecimal importe;
    private final BigDecimal litros;
    private final String combustible;

    public Pago(int id, int idCliente, LocalDate fecha,
                BigDecimal importe, BigDecimal litros, String combustible) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.importe = importe;
        this.litros = litros;
        this.combustible = combustible;
    }

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public BigDecimal getLitros() {
        return litros;
    }

    public String getCombustible() {
        return combustible;

    }

}