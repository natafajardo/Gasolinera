# Práctica 1 - Gestión de clientes y repostajes

## 1. Descripción

Este proyecto es una aplicación de consola hecha en Java para gestionar los clientes de una gasolinera y registrar los pagos de los repostajes.

La aplicación permite:

* Registrar clientes.
* Listar clientes.
* Buscar clientes.
* Registrar pagos.
* Consultar los pagos realizados.
* Guardar la información para que no se pierda al cerrar el programa.

Los datos se guardan en archivos CSV utilizando las clases de Java para trabajar con archivos.

---

## 2. Tecnologías utilizadas

* Java 21
* IntelliJ IDEA
* Archivos CSV
* UTF-8
* `Files` y `Path`
* `BufferedReader` y `BufferedWriter`
* `LocalDate`
* `BigDecimal`

---

## 3. Estructura del proyecto

```text
proyecto-gasolinera
│
├── src
│   ├── Cliente.java
│   ├── ClienteRepository.java
│   ├── ClienteRepositoryCSV.java
│   ├── ClienteGestor.java
│   ├── Pago.java
│   ├── PagoRepository.java
│   ├── PagoRepositoryCSV.java
│   ├── PagoGestor.java
│   ├── Menu.java
│   └── Main.java
│
├── data
│   ├── clientes.csv
│   └── pagos.csv
│
└── README.md
```

---

## 4. Responsabilidad de las clases

Intenté separar las clases para que cada una tenga una función concreta.

### `Cliente`

Representa los datos de un cliente:

* ID
* Nombre
* Teléfono
* Matrícula

No se encarga de guardar archivos ni de mostrar el menú.

### `ClienteGestor`

Se encarga de la parte relacionada con los clientes.

Por ejemplo:

* validar los datos;
* comprobar que la matrícula no esté repetida;
* generar el siguiente ID;
* registrar clientes;
* buscar clientes;
* listar y ordenar clientes.

### `ClienteRepository`

Es una interfaz que define las operaciones para cargar y guardar clientes.

### `ClienteRepositoryCSV`

Es la clase que se encarga de leer y escribir los clientes en `clientes.csv`.

---

### `Pago`

Representa los datos de un pago:

* ID
* ID del cliente
* Fecha
* Importe
* Litros
* Combustible

El campo `idCliente` permite relacionar el pago con el cliente correspondiente.

### `PagoGestor`

Se encarga de validar y registrar los pagos.

También comprueba que el cliente indicado exista y calcula el siguiente ID del pago.

### `PagoRepository`

Define las operaciones para cargar y guardar pagos.

### `PagoRepositoryCSV`

Se encarga de leer y escribir los pagos en `pagos.csv`.

---

### `Menu`

Es la parte que interactúa directamente con el usuario.

Muestra las opciones y pide los datos necesarios.

El `Menu` no guarda directamente los datos en los archivos, sino que utiliza los gestores.

### `Main`

Es donde comienza el programa.

Se encarga de crear los objetos necesarios y conectarlos para iniciar el menú.

---

## 5. Relación entre las clases

La idea general que seguí es:

```text
Main
  ↓
Menu
  ↓
Gestores
  ↓
Repositories
  ↓
Archivos CSV
```

Por ejemplo, para registrar un cliente:

```text
Menu
  ↓
ClienteGestor
  ↓
ClienteRepository
  ↓
ClienteRepositoryCSV
  ↓
clientes.csv
```

De esta manera, el menú no tiene que saber cómo funciona el archivo.

---

## 6. Guardado de los datos

Los datos se guardan en:

```text
data/clientes.csv
data/pagos.csv
```

Si los archivos no existen, el programa los crea automáticamente.

Cuando se registra un cliente o un pago, se guarda inmediatamente en el archivo.

Al iniciar nuevamente el programa, los archivos se leen para recuperar los datos anteriores.

---

## 7. Formato de `clientes.csv`

Se utiliza `;` como separador.

```text
id;nombre;telefono;matricula
```

Ejemplo:

```text
1;Ana;612345678;1234ABC
2;Jose;600111222;5678DEF
```

Los datos se guardan utilizando UTF-8.

---

## 8. Formato de `pagos.csv`

El archivo utiliza las siguientes columnas:

```text
id;idCliente;fecha;importe;litros;combustible
```

Ejemplo:

```text
1;1;15/09/2026;40.50;25.00;Gasolina
```

La fecha se guarda con el formato:

```text
dd/MM/yyyy
```

Los importes y litros se manejan con `BigDecimal`.

---

## 9. Algunas validaciones

### Clientes

Al registrar un cliente se comprueba que:

* el nombre no esté vacío;
* el teléfono no esté vacío;
* la matrícula no esté vacía;
* la matrícula no esté repetida.

La matrícula se guarda en mayúsculas.

### Pagos

Se comprueba que:

* el cliente exista;
* la fecha sea válida;
* el importe sea mayor que cero;
* los litros sean mayores que cero;
* importe y litros tengan como máximo dos decimales;
* el combustible no esté vacío.

---

## 10. Ordenación

Los clientes se muestran ordenados por nombre.

Si dos clientes tienen el mismo nombre, se utiliza el ID para decidir el orden.

Los pagos se muestran primero por la fecha más reciente y, si tienen la misma fecha, por el ID mayor.

Para realizar estas ordenaciones utilicé ciclos y comparaciones sencillas, en lugar de utilizar código que todavía me resulta más difícil de entender.

Esto también me permite modificar los métodos y explicar cómo funcionan.

---

## 11. Decisiones de diseño

Una de las decisiones principales fue separar la lógica de los archivos.

Por ejemplo, `ClienteGestor` sabe cómo validar y registrar un cliente, mientras que `ClienteRepositoryCSV` sabe cómo guardarlo en el archivo.

También utilicé interfaces para los repositorios. De esta forma, el gestor no depende directamente del archivo CSV.

La aplicación está dividida así:

```text
Cliente / Pago
    ↓
Datos

ClienteGestor / PagoGestor
    ↓
Lógica y validaciones

Repository
    ↓
Operaciones de guardar y cargar

RepositoryCSV
    ↓
Archivos

Menu
    ↓
Interacción con el usuario
```

Intenté mantener el código sencillo porque todavía estoy aprendiendo y quiero poder entender y modificar cada método.

---

## 12. Pruebas realizadas

Algunas pruebas que se pueden realizar son:

* Registrar un cliente.
* Intentar registrar una matrícula repetida.
* Listar clientes.
* Buscar un cliente.
* Registrar un pago.
* Intentar registrar un pago con un cliente que no existe.
* Introducir un importe incorrecto.
* Introducir una fecha incorrecta.
* Cerrar y volver a abrir el programa para comprobar que los datos permanecen.
* Comprobar que los siguientes IDs continúan desde los anteriores.

---

## 13. Conclusión

El objetivo de esta práctica fue crear una aplicación sencilla para gestionar clientes y pagos de una gasolinera, utilizando archivos para mantener los datos entre ejecuciones.

Durante el desarrollo intenté separar las responsabilidades de las clases y utilizar un código que pueda entender y modificar, especialmente en las partes de validación, lectura, escritura y ordenación.
