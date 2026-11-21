# AGENTS.md — Proyecto Gasolinera

## Propósito

Este repositorio contiene la aplicación Java de gestión de una gasolinera y la ampliación de la práctica de Acceso a Datos. La ampliación debe añadir el modelo de estaciones, personal, tienda, ventas, pagos, devoluciones, incidencias y proveedores, además de una persistencia MySQL preparada para una fase JDBC posterior.

La entrega debe conservar las funcionalidades existentes y ser explicable y verificable. Que una solución compile no demuestra por sí solo que respete las reglas del enunciado.

## Estado de partida

- El código Java está en `src/` y actualmente no utiliza paquetes.
- La persistencia existente se organiza mediante `Almacenamiento` y sus implementaciones CSV/JSON.
- Los datos heredados están en `data/` (`clientes.csv`, `pagos.csv`) y `datos/` (`clientes.json`, `pagos.json` y otros ficheros de la práctica anterior).
- Existen clases para clientes, pagos, gestores, repositorios CSV, almacenamiento JSON y conexión a BD.
- No hay que eliminar ni reinterpretar silenciosamente los campos o comportamientos existentes de `Cliente` y `Pago`.
- En esta fase se prepara el modelo MySQL y el dominio Java, pero no se implementan todavía los métodos JDBC CRUD.

## Reglas para agentes

1. Leer primero el código y la documentación antes de cambiar interfaces o clases compartidas.
2. Mantener separadas las responsabilidades de dominio, aplicación, persistencia y conexión.
3. Las clases de dominio no deben abrir conexiones ni ejecutar SQL.
4. Conservar CSV y JSON y comprobarlos después de cualquier cambio en el modelo anterior.
5. No añadir credenciales personales, contraseñas ni rutas absolutas de usuario al repositorio.
6. Usar `BigDecimal` para precios, importes y cantidades decimales, y documentar un redondeo monetario común, preferiblemente escala 2 y `RoundingMode.HALF_UP`.
7. Usar tipos de `java.time` para fechas y horas.
8. Diferenciar siempre el identificador técnico autogenerado de las referencias de negocio.
9. No afirmar que una regla queda protegida por SQL si solo la cumplen los datos de ejemplo o un control aún pendiente.
10. Compilar y ejecutar las comprobaciones aplicables después de cada cambio relevante.

## Entregables

La información debe quedar recogida, como mínimo, en:

```text
src/                    Código Java
sql/                    01_esquema.sql, 02_triggers.sql,
                        03_datos.sql y 04_comprobaciones.sql
README.md               Requisitos y ejecución
MODELO_RELACIONAL.md    Diccionario de datos y restricciones
JUSTIFICACION_JAVA.md   Modelo Java y correspondencia con MySQL
REGLAS_NEGOCIO.md       Reglas garantizadas, pendientes y evidencias
AUDITORIA_IA.odt        Tres intervenciones de IA y casos acertados/erróneos
AGENTS.md               Esta guía
```

## Modelo relacional

Todas las tablas, incluidas asociaciones, asignaciones, líneas e históricos, deben tener una clave primaria formada por un único entero autoincremental. Las referencias de negocio se protegen mediante `UNIQUE` y restricciones adicionales.

El esquema debe cubrir estaciones, configuración de facturación, surtidores, empleados, funciones y supervisión, clientes, tarjetas, campañas, productos envasados y a granel, proveedores y acuerdos, ventas, líneas, pagos, devoluciones, incidencias e historial de precios.

Decisiones que deben documentarse:

- estación y configuración: relación uno a uno obligatoria;
- surtidor: pertenece a una estación y su número visible es único solo dentro de ella;
- funciones: asignaciones independientes con fechas, fin opcional y condiciones específicas;
- producto: modalidad estable, envasada o a granel;
- línea: conserva el precio unitario realmente aplicado y el importe calculado;
- venta: puede ser anónima y tener varios pagos mientras se completa;
- devolución: se vincula a una línea concreta y no modifica la venta original;
- proveedor-producto: conserva referencia del proveedor, precio acordado y plazo;
- reglas que afectan a varios registros: deben marcarse como controles de transacción o aplicación si no las resuelve una restricción SQL.

Usar tablas `InnoDB`, claves foráneas y acciones referenciales justificadas. Preferir baja lógica o `RESTRICT` para no perder ventas, líneas, pagos, devoluciones, acuerdos e históricos. Usar `CASCADE` solo en dependencias puramente subordinadas y explicarlo.

## Trigger obligatorio de precios

`02_triggers.sql` debe crear un trigger MySQL que, al actualizar un producto, guarde producto, precio anterior, precio nuevo y fecha/hora solamente cuando el precio cambie realmente. No debe registrar la creación, el cambio de descripción ni guardar el mismo precio. Debe funcionar ante SQL directo y participar en la transacción InnoDB: un `ROLLBACK` debe revertir el precio y su histórico. Java no inserta manualmente estas entradas.

## Reglas que requieren especial atención

La documentación debe distinguir reglas garantizadas por `NOT NULL`, `CHECK`, `UNIQUE`, claves foráneas, triggers o controles de aplicación. Marcar como pendientes, si corresponde:

- alta conjunta estación-configuración y primer surtidor;
- impedir retirar el último surtidor;
- periodos solapados de una función;
- ciclos directos o indirectos de supervisión;
- proveedores o productos aislados;
- venta sin líneas o venta aún no pagada;
- devoluciones acumuladas superiores a la cantidad o importe originales, también con concurrencia;
- modificación de líneas de ventas cerradas.

No presentar como rechazo automático una protección que dependa todavía de JDBC o de una transacción de aplicación.

## Modelo Java

El dominio debe superar razonablemente las quince clases, sin crear clases vacías. Debe representar estaciones, configuración y surtidores; empleados y asignaciones de función; clientes, tarjetas, campañas e inscripciones; productos y modalidades; proveedores y acuerdos; ventas, líneas, pagos y devoluciones; incidencias e historial.

Las modalidades de producto deben validarse y calcular importes polimórficamente: envasados aceptan cantidades enteras positivas y los productos a granel cantidades positivas compatibles con su precisión. El código que prepara un ticket debe trabajar con ambas sin duplicar las decisiones de cada modalidad.

Permitir cliente anónimo, incidencia sin responsable, empleado sin supervisor y empleado temporalmente sin funciones. Los objetos deben poder construirse relacionados aunque aún no tengan el identificador generado por MySQL. Los constructores no deben depender de conexiones.

## Scripts y datos

Ejecutar siempre en este orden:

```text
01_esquema.sql -> 02_triggers.sql -> 03_datos.sql -> 04_comprobaciones.sql
```

Los tres primeros deben funcionar sobre un entorno vacío y dejar una base coherente. Las operaciones deliberadamente erróneas deben separarse o comentarse para no interrumpir la preparación inicial. Nunca desactivar claves foráneas.

Los ejemplos deben demostrar varias estaciones y surtidores; tarjetas asignadas y sin titular; personal con y sin supervisor, con varias funciones, sin funciones y con una función recuperada; modalidades de producto; proveedores y acuerdos en ambos sentidos; campañas vacías y participaciones; incidencias asignadas y sin asignar; ventas anónimas e identificadas, al menos diez líneas y una venta con varios pagos; devoluciones parciales sucesivas; y todas las comprobaciones del trigger, incluido `ROLLBACK`.

Usar al menos cinco registros en las tablas principales salvo justificación. No añadir filas de relleno a asociaciones o históricos.

## Verificación antes de entregar

- Compilar el proyecto Java con el JDK configurado.
- Ejecutar la demostración conjunta de unidades enteras y cantidades decimales.
- Comprobar lectura y escritura CSV y JSON.
- Ejecutar los scripts SQL en el orden indicado.
- Verificar el histórico tras cambio real, mismo precio, cambio de descripción y `ROLLBACK`.
- Ejecutar pruebas de duplicados, asociaciones repetidas y referencias inexistentes.
- Revisar que no haya credenciales reales.
- Registrar tres intervenciones de IA con petición, propuesta, decisión y verificación; una debe tratar diseño y otra comportamiento.

## Futura fase JDBC

No implementar CRUD JDBC salvo petición expresa. Documentar al menos tres operaciones futuras con entradas, transacción, validaciones y resultado: por ejemplo, alta completa de estación, cierre y cobro de una venta, y devolución parcial. La futura implementación deberá usar `PreparedStatement`, transacciones explícitas y cierre seguro de recursos.

## Comandos útiles

```powershell
rg --files
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out src\*.java
java -cp out Main
```

La configuración de MySQL debe documentarse sin contraseñas versionadas. No incluir secretos en código, scripts ni README.
