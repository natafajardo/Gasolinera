# Gestión de Gasolinera — Práctica 2

## 1. Descripción del proyecto

Esta aplicación de consola permite gestionar los clientes de una gasolinera y registrar sus pagos de repostaje.

La Práctica 2 parte de la solución desarrollada en la **Práctica 1 (P1)**. Se conserva su funcionalidad y se modifica el sistema de almacenamiento para trabajar con archivos JSON, incorporando además un procedimiento para migrar los datos existentes en CSV.

El proyecto está desarrollado en Java y utiliza exclusivamente las clases y herramientas incluidas en el JDK, sin librerías externas.

## 2. Relación entre P1 y P2

Se mantienen las funcionalidades principales de la primera práctica:

- Dar de alta clientes.
- Listar los clientes registrados.
- Buscar clientes por nombre, teléfono o matrícula.
- Registrar pagos de repostaje asociados a un cliente existente.
- Consultar los pagos registrados.
- Conservar los datos al cerrar y volver a ejecutar el programa.

La principal modificación de P2 es la sustitución del almacenamiento habitual en CSV por JSON. También se incorpora un proceso para trasladar los datos de la versión anterior al nuevo formato.

| Aspecto | Práctica 1 (P1) | Práctica 2 (P2) |
|---|---|---|
| Almacenamiento habitual | CSV | JSON |
| Persistencia | Archivos locales | Archivos locales |
| Gestión de clientes | Se conserva | Se conserva |
| Gestión de pagos | Se conserva | Se conserva |
| Migración de datos | No incorporada | De CSV a JSON |
| Librerías externas | No necesarias | No utilizadas |

## 3. Funcionalidades y validaciones

### Gestión de clientes

Cada cliente contiene un identificador, nombre, teléfono y matrícula.

Se aplican las siguientes validaciones:

- El nombre, teléfono y matrícula son obligatorios.
- La matrícula se almacena en mayúsculas y no puede repetirse.
- Los identificadores se generan automáticamente.
- Los clientes se pueden buscar mediante coincidencias parciales en sus datos de búsqueda.

### Gestión de pagos

Cada pago contiene un identificador, el identificador del cliente, la fecha, el importe, los litros y el tipo de combustible.

Se comprueba que:

- El cliente exista.
- La fecha sea válida.
- El importe y los litros sean mayores que cero y tengan como máximo dos decimales.
- El combustible no esté vacío.
- El pago se guarde de forma persistente.

Los clientes se muestran ordenados por nombre y, si coinciden, por identificador. Los pagos se muestran por fecha descendente y, en caso de empate, por identificador descendente.

## 4. Diseño de la solución

### Abstracción del almacenamiento

Se utiliza la interfaz `Almacenamiento` como punto común para las operaciones de lectura y escritura de clientes y pagos.

Las implementaciones concretas son:

- `AlmacenamientoJSON`: permite cargar y guardar los datos en archivos JSON.
- `AlmacenamientoCSV`: permite leer los archivos CSV de la versión anterior para realizar la migración.

`ClienteGestor` y `PagoGestor` trabajan con la abstracción `Almacenamiento`, sin depender directamente del formato concreto de los archivos.

Esta decisión reduce el acoplamiento y facilita cambiar la implementación del almacenamiento sin modificar la lógica principal de gestión.

### Conversión a JSON

La clase `JsonTraductor` se encarga de transformar los datos al formato JSON y de interpretar el contenido para recuperar los objetos Java.

La conversión de clientes a texto utiliza las operaciones `map` y `reduce`, tal como se solicita en la práctica. La solución se implementa con funcionalidades de Java y sin bibliotecas externas de JSON.

### Migración de datos

La clase `MigraCSVToJson` permite trasladar los datos de la versión anterior al nuevo almacenamiento.

El procedimiento:

1. Lee los clientes y los pagos existentes en CSV.
2. Valida los datos y las relaciones entre los pagos y los clientes.
3. Comprueba que el destino JSON no exista para evitar sobrescribir información.
4. Guarda los datos en el nuevo formato cuando la validación se completa.

La migración no modifica ni elimina los archivos CSV originales. Si se detectan datos no válidos durante la validación, se cancela antes de guardar el resultado.

## 5. Estructura de archivos de datos

La aplicación utiliza archivos locales en UTF-8.

### Archivos de la Práctica 1

La carpeta de datos CSV contiene:

- `clientes.csv`
- `pagos.csv`

Estos archivos se conservan como origen de la migración.

### Archivos de la Práctica 2

La carpeta `datos` contiene:

- `clientes.json`
- `pagos.json`

Los archivos JSON son el almacenamiento habitual de la nueva versión. La aplicación utiliza este formato para cargar los datos al iniciar y guardar los cambios durante su ejecución.

## 6. Requisitos y ejecución

### Requisitos

- JDK 21.
- Un entorno de desarrollo Java, como IntelliJ IDEA, o una terminal.
- Los archivos fuente del proyecto.

No es necesario instalar dependencias externas ni configurar una base de datos.

### Ejecución desde el entorno de desarrollo

1. Abrir el proyecto en IntelliJ IDEA.
2. Comprobar que está configurado el JDK 21.
3. Ejecutar la clase `Main`.
4. Si todavía no existe el almacenamiento JSON, seguir las instrucciones para iniciar la migración desde CSV o comenzar con datos vacíos.
5. Utilizar las opciones del menú para gestionar clientes y pagos.

Es importante utilizar la carpeta de datos correcta cuando se solicite la ubicación de los archivos CSV.

## 7. Versiones y control de cambios

El proyecto se organiza en dos versiones comparables:

- **P1:** versión final de la primera práctica, conservada en la rama `develop`.
- **P2:** versión con almacenamiento JSON y migración, desarrollada en la rama `practica2-json`.

La rama de P2 parte de la versión final de P1, de forma que los cambios pueden revisarse respecto al código original.

Para consultar las ramas:

```bash
git branch
```

Para consultar el historial de cambios de P2:

```bash
git log --oneline practica2-json
```

Para comparar los archivos entre ambas versiones:

```bash
git diff develop..practica2-json
```

El historial de Git permite identificar los cambios realizados durante la adaptación y consultar las versiones anteriores del código.

## 8. Pruebas realizadas

Durante el desarrollo se ha comprobado:

- La migración de dos clientes y dos pagos desde CSV.
- La lectura y visualización de los clientes desde JSON.
- La consulta de pagos ordenados por fecha descendente.
- El inicio de la aplicación después de completar la migración.

También se debe comprobar el alta de nuevos clientes y pagos, su persistencia en los archivos JSON y la recuperación de los datos al reiniciar el programa.

## 9. Documentación complementaria

El archivo `CAMBIOS.md` recoge los cambios introducidos respecto a P1 y explica las decisiones de diseño, especialmente la interfaz común de almacenamiento, la implementación JSON, el uso de `map` y `reduce` y la migración desde CSV.

La documentación y el historial de Git permiten relacionar los cambios del código con los objetivos de la segunda práctica.
