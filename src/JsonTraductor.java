import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonTraductor {

    /*
     * Convierte todos los clientes en un único documento JSON.
     * El map convierte cada Cliente en su representación JSON.
     * El reduce une esas representaciones utilizando comas.
     */
    public static String clientesAJson(List<Cliente> clientes) {

        String contenido = clientes.stream()
                .map(cliente -> clienteAJson(cliente))
                .reduce("", (acumulado, actual) -> {

                    if (acumulado.isEmpty()) {
                        return actual;
                    }

                    return acumulado + "," + actual;
                });

        return "{\n" +
                "  \"clientes\": [" +
                contenido +
                "]\n" +
                "}";
    }

    /*
     * Convierte un cliente individual en JSON.
     */
    private static String clienteAJson(Cliente cliente) {

        return "{"
                + "\"id\":" + cliente.getId() + ","
                + "\"nombre\":\"" + escapar(cliente.getNombre()) + "\","
                + "\"telefono\":\"" + escapar(cliente.getTelefono()) + "\","
                + "\"matricula\":\"" + escapar(cliente.getMatricula()) + "\""
                + "}";
    }

    /*
     * Convierte todos los pagos en JSON.
     */
    public static String pagosAJson(List<Pago> pagos) {

        String contenido = pagos.stream()
                .map(pago -> pagoAJson(pago))
                .reduce("", (acumulado, actual) -> {

                    if (acumulado.isEmpty()) {
                        return actual;
                    }

                    return acumulado + "," + actual;
                });

        return "{\n" +
                "  \"pagos\": [" +
                contenido +
                "]\n" +
                "}";
    }

    /*
     * Convierte un pago individual en JSON.
     */
    private static String pagoAJson(Pago pago) {

        return "{"
                + "\"id\":" + pago.getId() + ","
                + "\"clienteId\":" + pago.getIdCliente() + ","
                + "\"fecha\":\"" + pago.getFecha() + "\","
                + "\"importe\":" + pago.getImporte().toPlainString() + ","
                + "\"litros\":" + pago.getLitros().toPlainString() + ","
                + "\"combustible\":\"" + escapar(pago.getCombustible()) + "\""
                + "}";
    }

    /*
     * Escapa los caracteres que tienen un significado especial
     * dentro de una cadena JSON.
     */
    private static String escapar(String texto) {

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    /*
     * Convierte el JSON de clientes en objetos Cliente.
     */
    public static List<Cliente> jsonAClientes(String json) throws java.io.IOException {

        validarInicio(json, "clientes");

        String contenido = obtenerArray(json, "clientes");

        List<String> objetos = separarObjetos(contenido);

        List<Cliente> clientes = new ArrayList<>();

        for (String objeto : objetos) {

            int id = Integer.parseInt(obtenerValor(objeto, "id"));
            String nombre = obtenerTexto(objeto, "nombre");
            String telefono = obtenerTexto(objeto, "telefono");
            String matricula = obtenerTexto(objeto, "matricula");

            clientes.add(
                    new Cliente(id, nombre, telefono, matricula)
            );
        }

        return clientes;
    }

    /*
     * Convierte el JSON de pagos en objetos Pago.
     */
    public static List<Pago> jsonAPagos(String json) throws java.io.IOException {

        validarInicio(json, "pagos");

        String contenido = obtenerArray(json, "pagos");

        List<String> objetos = separarObjetos(contenido);

        List<Pago> pagos = new ArrayList<>();

        for (String objeto : objetos) {

            int id = Integer.parseInt(
                    obtenerValor(objeto, "id")
            );

            int idCliente = Integer.parseInt(
                    obtenerValor(objeto, "clienteId")
            );

            LocalDate fecha = LocalDate.parse(
                    obtenerTexto(objeto, "fecha")
            );

            BigDecimal importe = new BigDecimal(
                    obtenerValor(objeto, "importe")
            );

            BigDecimal litros = new BigDecimal(
                    obtenerValor(objeto, "litros")
            );

            String combustible = obtenerTexto(
                    objeto,
                    "combustible"
            );

            pagos.add(
                    new Pago(
                            id,
                            idCliente,
                            fecha,
                            importe,
                            litros,
                            combustible
                    )
            );
        }

        return pagos;
    }

    private static void validarInicio(
            String json,
            String nombreArray) throws java.io.IOException {

        if (json == null || json.trim().isEmpty()) {
            throw new java.io.IOException(
                    "El JSON está vacío."
            );
        }

        String limpio = json.trim();

        if (!limpio.startsWith("{") ||
                !limpio.endsWith("}")) {

            throw new java.io.IOException(
                    "El JSON no tiene una estructura válida."
            );
        }

        if (!limpio.contains("\"" + nombreArray + "\"")) {

            throw new java.io.IOException(
                    "No se encuentra el apartado " + nombreArray + "."
            );
        }
    }

    /*
     * Extrae el contenido del array:
     * "clientes": [ ... ]
     */
    private static String obtenerArray(
            String json,
            String nombre) throws java.io.IOException {

        String buscado = "\"" + nombre + "\"";

        int posicionNombre = json.indexOf(buscado);

        if (posicionNombre == -1) {
            throw new java.io.IOException(
                    "No se encuentra " + nombre + "."
            );
        }

        int inicio = json.indexOf(
                "[",
                posicionNombre
        );

        if (inicio == -1) {
            throw new java.io.IOException(
                    "No se encuentra el array de " + nombre + "."
            );
        }

        int fin = encontrarCierreArray(json, inicio);

        if (fin == -1) {
            throw new java.io.IOException(
                    "El array de " + nombre + " no está cerrado."
            );
        }

        return json.substring(
                inicio + 1,
                fin
        ).trim();
    }

    /*
     * Busca el ] correspondiente al [.
     * Tiene en cuenta si estamos dentro de una cadena.
     */
    private static int encontrarCierreArray(
            String texto,
            int inicio) {

        int nivel = 0;
        boolean dentroCadena = false;
        boolean escapado = false;

        for (int i = inicio; i < texto.length(); i++) {

            char caracter = texto.charAt(i);

            if (escapado) {
                escapado = false;
                continue;
            }

            if (caracter == '\\' && dentroCadena) {
                escapado = true;
                continue;
            }

            if (caracter == '"') {
                dentroCadena = !dentroCadena;
                continue;
            }

            if (!dentroCadena) {

                if (caracter == '[') {
                    nivel++;
                }

                if (caracter == ']') {
                    nivel--;

                    if (nivel == 0) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }

    /*
     * Separa:
     *
     * {"id":1,...},{"id":2,...}
     *
     * en objetos individuales.
     */
    private static List<String> separarObjetos(
            String contenido) throws java.io.IOException {

        List<String> objetos = new ArrayList<>();

        if (contenido.isEmpty()) {
            return objetos;
        }

        int inicioObjeto = -1;
        int nivel = 0;
        boolean dentroCadena = false;
        boolean escapado = false;

        for (int i = 0; i < contenido.length(); i++) {

            char caracter = contenido.charAt(i);

            if (escapado) {
                escapado = false;
                continue;
            }

            if (caracter == '\\' && dentroCadena) {
                escapado = true;
                continue;
            }

            if (caracter == '"') {
                dentroCadena = !dentroCadena;
                continue;
            }

            if (dentroCadena) {
                continue;
            }

            if (caracter == '{') {

                if (nivel == 0) {
                    inicioObjeto = i;
                }

                nivel++;
            }

            if (caracter == '}') {

                nivel--;

                if (nivel == 0 && inicioObjeto != -1) {

                    objetos.add(
                            contenido.substring(
                                    inicioObjeto,
                                    i + 1
                            )
                    );

                    inicioObjeto = -1;
                }
            }
        }

        if (nivel != 0) {
            throw new java.io.IOException(
                    "Existe un objeto JSON mal cerrado."
            );
        }

        return objetos;
    }

    /*
     * Obtiene un valor que no está entre comillas.
     *
     * Ejemplo:
     * "id": 1
     */
    private static String obtenerValor(
            String objeto,
            String nombre) throws java.io.IOException {

        String buscado = "\"" + nombre + "\"";

        int posicion = objeto.indexOf(buscado);

        if (posicion == -1) {
            throw new java.io.IOException(
                    "Falta el campo " + nombre + "."
            );
        }

        int dosPuntos = objeto.indexOf(
                ":",
                posicion
        );

        if (dosPuntos == -1) {
            throw new java.io.IOException(
                    "El campo " + nombre + " no tiene valor."
            );
        }

        int inicio = dosPuntos + 1;

        int coma = objeto.indexOf(",", inicio);

        int fin;

        if (coma == -1) {
            fin = objeto.length() - 1;
        } else {
            fin = coma;
        }

        String valor = objeto.substring(
                inicio,
                fin
        ).trim();

        if (valor.startsWith("\"")) {
            throw new java.io.IOException(
                    "El campo " + nombre +
                            " debería ser numérico."
            );
        }

        return valor;
    }

    /*
     * Obtiene un valor de texto:
     *
     * "nombre":"Ana López"
     */
    private static String obtenerTexto(
            String objeto,
            String nombre) throws java.io.IOException {

        String buscado = "\"" + nombre + "\"";

        int posicion = objeto.indexOf(buscado);

        if (posicion == -1) {
            throw new java.io.IOException(
                    "Falta el campo " + nombre + "."
            );
        }

        int dosPuntos = objeto.indexOf(
                ":",
                posicion
        );

        if (dosPuntos == -1) {
            throw new java.io.IOException(
                    "El campo " + nombre + " no tiene valor."
            );
        }

        int inicio = dosPuntos + 1;

        while (inicio < objeto.length()
                && Character.isWhitespace(
                objeto.charAt(inicio))) {

            inicio++;
        }

        if (inicio >= objeto.length()
                || objeto.charAt(inicio) != '"') {

            throw new java.io.IOException(
                    "El campo " + nombre +
                            " debería ser texto."
            );
        }

        StringBuilder resultado = new StringBuilder();

        boolean escapado = false;

        for (int i = inicio + 1;
             i < objeto.length();
             i++) {

            char caracter = objeto.charAt(i);

            if (escapado) {

                if (caracter == 'n') {
                    resultado.append('\n');
                } else if (caracter == 'r') {
                    resultado.append('\r');
                } else if (caracter == '"') {
                    resultado.append('"');
                } else if (caracter == '\\') {
                    resultado.append('\\');
                } else {
                    throw new java.io.IOException(
                            "Escape JSON no válido."
                    );
                }

                escapado = false;

            } else if (caracter == '\\') {

                escapado = true;

            } else if (caracter == '"') {

                return resultado.toString();

            } else {

                resultado.append(caracter);
            }
        }

        throw new java.io.IOException(
                "Texto JSON sin cerrar."
        );
    }
}
