# Contexto del proyecto

## Tecnología y ejecución

- Proyecto Java de consola, sin declaraciones `package`; todas las clases están en el paquete por defecto.
- La documentación del proyecto fija JDK 21 como versión objetivo. No hay `pom.xml`, `build.gradle` ni otro sistema de build: `Gasolinera.iml` configura `src/` como raíz de código y usa el JDK configurado en IntelliJ IDEA.
- No hay dependencias externas ni framework. Se usan únicamente APIs del JDK (`java.nio`, `java.time`, `java.math`, colecciones y `Scanner`).
- La clase de entrada es `Main`. El programa se puede compilar directamente con `javac -d out src/*.java` y ejecutar con `java -cp out Main`, desde la raíz del proyecto. `out/` es solo un directorio de salida local y no forma parte del código fuente.
- Las rutas de datos son relativas al directorio desde el que se ejecuta el programa: `datos/clientes.json` y `datos/pagos.json`.

## Arquitectura actual

- `Cliente` y `Pago` son entidades simples e inmutables, con campos `final` y getters.
- `ClienteGestor` contiene la lógica de clientes: altas, generación de identificadores, validación de campos y matrícula única, búsquedas y ordenación.
- `PagoGestor` contiene la lógica de pagos: altas asociadas a un cliente existente, validaciones, generación de identificadores y ordenación.
- `Menu` gestiona la interacción de consola y delega las operaciones en los gestores. `Main` crea el almacenamiento, los gestores y el menú.
- `Almacenamiento` es la abstracción común de lectura y escritura de clientes y pagos.
- `AlmacenamientoJSON` es la implementación activa y usa `JsonTraductor`, un traductor JSON manual sin biblioteca externa.
- `AlmacenamientoCSV` se conserva para leer y escribir el formato anterior. `MigraCSVToJson` valida los CSV, evita sobrescribir un destino existente y migra a JSON sin eliminar los originales.
- `ClienteRepository`, `ClienteRepositoryCSV`, `PagoRepository` y `PagoRepositoryCSV` pertenecen a la solución anterior. No están conectados al flujo actual de `Main` ni de los gestores; no eliminarlos ni reactivarlos sin una decisión explícita.

## Datos y comportamiento

- JSON es el almacenamiento habitual. En el primer arranque, si no existe ningún JSON, `Main` pregunta si se desea migrar una carpeta CSV o crear un almacenamiento vacío.
- Los CSV de ejemplo están en `data/clientes.csv` y `data/pagos.csv`; los JSON de ejemplo están en `datos/clientes.json` y `datos/pagos.json`.
- Los clientes se buscan por coincidencia parcial en nombre, teléfono o matrícula; la matrícula se normaliza a mayúsculas y no puede repetirse.
- Los clientes se listan por nombre ascendente y después por identificador ascendente.
- Los pagos se listan por fecha descendente y después por identificador descendente.
- Fechas usan `LocalDate`; los importes y litros usan `BigDecimal` y deben ser positivos, con como máximo dos decimales.
- La persistencia se realiza escribiendo listas completas en los archivos JSON. Los gestores actualizan su estado en memoria después de guardar correctamente.

## Restricciones para cambios

- Mantener la estructura de paquetes (paquete por defecto), las responsabilidades actuales y la compatibilidad con JDK 21.
- Priorizar cambios pequeños y sencillos. No añadir capas, frameworks, bibliotecas JSON, ORM, base de datos ni dependencias externas sin justificación y autorización.
- No sustituir el parser JSON manual ni cambiar el formato de los archivos sin revisar compatibilidad con los datos existentes y con la migración CSV.
- Respetar UTF-8 y las rutas relativas actuales. No asumir que el directorio de trabajo es `src/`.
- No modificar ni borrar los CSV originales durante una migración; no sobrescribir un destino JSON existente.
- No conectar los gestores directamente a una implementación concreta de almacenamiento: conservar la dependencia con `Almacenamiento`.
- No añadir funcionalidades de MySQL solo porque existe `docker-compose.yml`: ese compose describe una infraestructura separada y la aplicación Java actual no usa JDBC ni SQL.
- No hay una suite de pruebas automatizadas configurada. Después de cambios relevantes, como mínimo compilar todas las clases y verificar el flujo afectado con los datos de ejemplo.
