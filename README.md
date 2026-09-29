# Práctica 1 - Gestión de clientes y repostajes de una gasolinera

## Descripción
Aplicación Java de consola para registrar, consultar y buscar clientes y para registrar/consultar pagos correspondientes a repostajes. Los datos persisten en CSV UTF-8 entre ejecuciones.

## Menú
1. Registrar cliente
2. Listar clientes
3. Buscar clientes
4. Procesar pago de repostaje
5. Consultar pagos
0. Salir

## Modelo
### Cliente
- id: entero positivo, automático
- nombre: obligatorio
- telefono: obligatorio
- matricula: obligatoria, almacenada en mayúsculas y única

- ### Pago
- id: entero positivo, automático e independiente del ID de cliente
- idCliente: debe corresponder a un cliente existente
- fecha: dd/MM/yyyy; vacía = fecha actual
- importe: positivo, máximo 2 decimales
- litros: positivos, máximo 2 decimales
- combustible: obligatorio

## Persistencia
Se utilizan `data/clientes.csv` y `data/pagos.csv`. El formato es CSV UTF-8 con `;` como separador y cabecera. 
Se utilizan `Path`, `Files`, `BufferedReader` y `BufferedWriter`.
Los archivos se crean automáticamente. Cada alta se guarda antes de mostrar la confirmación de éxito. 

## Organización
- `Cliente`, `Pago`: modelo de dominio.
- `ClienteGestor`, `PagoGestor`: lógica de negocio y listas en memoria.
- `ClienteRepository`, `PagoRepository`: contratos de persistencia.
- `ClienteRepositoryCSV`, `PagoRepositoryCSV`: implementación CSV.
- `Menu`: interacción con usuario.
- `Main`: crea y conecta las dependencias.

## Decisiones de diseño
Se aplica separación de responsabilidades y bajo acoplamiento. Repository separa almacenamiento y lógica. Los gestores evitan que el menú conozca los detalles de los archivos.

## Pruebas manuales
- alta de cliente y comprobación del ID
- matrícula duplicada
- listado y búsqueda
- cierre y reinicio para comprobar persistencia
- alta de pago con fecha, importe, litros y combustible
- cliente inexistente
- consulta de pagos
- reinicio y continuidad de IDs

  
## Ejecución
Ejecutar la clase `Main` con JDK 21. La carpeta `data` se crea automáticamente en el directorio de trabajo de IntelliJ.
