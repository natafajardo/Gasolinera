# CAMBIOS.md — Práctica 2: Migración a JSON

## 1. Objetivo

La Práctica 2 parte de la versión final de la Práctica 1 (P1), conservada en la rama `develop`, con el commit de referencia `196eb83`.

La nueva versión se desarrolla en la rama `practica2-json`. Su objetivo es mantener las funcionalidades y validaciones de P1, cambiar el almacenamiento habitual de CSV a JSON e incorporar la migración de los datos anteriores.

## 2. Inventario de clases afectadas

| Clase o interfaz | Situación | Cambio o responsabilidad | Motivo |
|---|---|---|---|
| `ClienteGestor` | Modificada | Utiliza `Almacenamiento` para cargar y guardar clientes. | Separar la gestión del formato de almacenamiento. |
| `PagoGestor` | Modificada | Utiliza `Almacenamiento` para cargar y guardar pagos. | Mantener una abstracción común. |
| `Main` | Modificada | Inicializa JSON y contempla la migración en el primer arranque. | Integrar el nuevo almacenamiento. |
| `Almacenamiento` | Nueva | Define las operaciones de lectura y escritura de clientes y pagos. | Desacoplar los gestores del formato. |
| `AlmacenamientoJSON` | Nueva | Lee y guarda los archivos JSON. | Establecer JSON como almacenamiento habitual. |
| `AlmacenamientoCSV` | Nueva | Lee CSV para migrar y conserva las operaciones de escritura CSV. | Mantener la compatibilidad con P1 y el requisito de la práctica. |
| `JsonTraductor` | Nueva | Convierte objetos Java a JSON y JSON a objetos Java. | Centralizar la traducción del formato. |
| `MigraCSVToJson` | Nueva | Valida y migra los datos desde CSV a JSON. | Separar la migración de las operaciones habituales. |

Las clases `Cliente`, `Pago` y `Menu` conservan sus responsabilidades principales. Los repositorios antiguos de P1 permanecen en el código de P2, pero no aparecen referencias a ellos desde los gestores o desde `Main` en las comprobaciones realizadas.

## 3. Recuento comparativo

La comparación de archivos se ha obtenido mediante Git:

```bash
git --no-pager diff --name-status develop..practica2-json
```

El resultado muestra:

- 7 archivos añadidos: 5 archivos Java y 2 archivos JSON de datos.
- 4 archivos modificados: `.gitignore`, `ClienteGestor.java`, `Main.java` y `PagoGestor.java`.
- 0 archivos eliminados en la comparación actual.

En cuanto a clases e interfaces, el inventario anterior identifica 3 clases modificadas y 5 clases o interfaces nuevas. No se han eliminado ni renombrado clases en los cambios comprobados.

Este recuento distingue los archivos del recuento de clases e interfaces. No se incluyen como clases los archivos de configuración, documentación o datos.

## 4. Evidencias de Git

### Versiones comparadas

- P1: rama `develop`, commit de referencia `196eb83` (`Readme`).
- P2: rama `practica2-json`, con el último commit observado `d12d222` (`archivos de prueba`).

### Commits de P2

El historial contiene, entre otros, los siguientes cambios:

| Commit | Descripción |
|---|---|
| `ae8a720` | Configura Git para IntelliJ. |
| `4f8e780` | Adapta los gestores a la abstracción `Almacenamiento`. |
| `f018647` | Implementa la lectura y escritura JSON. |
| `a1ceed4` | Convierte objetos Java a JSON y viceversa. |
| `231d218` | Implementa la migración. |
| `a5cc46a` | Añade el control del primer arranque y la migración. |
| `d12d222` | Añade archivos de prueba. |

El historial contiene más de los tres commits exigidos por el profesor.

Los cambios se pueden revisar con:

```bash
git --no-pager log --oneline develop..practica2-json
git --no-pager diff --name-status develop..practica2-json
git --no-pager diff --stat develop..practica2-json
```

## 5. Decisiones de diseño

### Interfaz común de almacenamiento

Se utiliza `Almacenamiento` para que `ClienteGestor` y `PagoGestor` no dependan directamente de una implementación CSV o JSON. De esta forma, las operaciones de gestión se separan de los detalles de persistencia.

### Formato JSON

Se introduce `AlmacenamientoJSON` y se centraliza la conversión en `JsonTraductor`. No se utilizan bibliotecas externas: la traducción se implementa con Java.

Las operaciones `map` y `reduce` se utilizan para transformar y combinar los elementos durante la generación de la representación textual de los clientes, tal como solicita la práctica.

### Conservación del CSV

Los archivos CSV de P1 se mantienen como origen de los datos para la migración. El código de escritura CSV también se conserva porque el enunciado lo exige, aunque la ejecución habitual de P2 utiliza JSON.

### Migración independiente

`MigraCSVToJson` separa la migración de la gestión habitual. La operación valida los datos y las relaciones entre clientes y pagos, comprueba que el destino esté libre y conserva los CSV originales.

### Conservación de las funcionalidades

Se mantienen las operaciones de alta, listado, búsqueda y consulta, así como las validaciones y los criterios de ordenación. El objetivo es modificar la persistencia sin rehacer innecesariamente la lógica de negocio.

## 6. Pruebas realizadas

Se ha comprobado que:

- La migración desde CSV termina correctamente.
- Se migran dos clientes y dos pagos.
- Los clientes se pueden listar desde el almacenamiento JSON.
- Los pagos se pueden consultar y aparecen ordenados por fecha descendente.
- La aplicación muestra el menú después de completar la migración.

Durante las pruebas se detectó que el constructor de `ClienteGestor` no asignaba la referencia recibida al atributo `almacenamiento`. Se corrigió la asignación y se pudo continuar con las pruebas.

La prueba de alta de un nuevo cliente y la persistencia tras reiniciar deben considerarse pendientes de documentar hasta comprobar su resultado final.

## 7. Reflexión sobre el diseño

**1. ¿Dónde se conocía el formato CSV en P1?**

El formato CSV estaba relacionado con los repositorios concretos encargados de leer y escribir los datos. Los gestores dependían de esas interfaces de repositorio.

**2. ¿Qué cambios fueron necesarios para utilizar JSON y cuáles se introdujeron para permitir la migración?**

Para utilizar JSON se incorporaron la interfaz común `Almacenamiento`, la implementación `AlmacenamientoJSON` y la clase `JsonTraductor`. Para migrar se añadieron `AlmacenamientoCSV` y `MigraCSVToJson`, además del control del primer arranque en `Main`.

**3. ¿Fue necesario modificar el menú, las validaciones o las operaciones con clientes y pagos?**

Los cambios principales se realizaron en los gestores y en `Main` para utilizar la nueva abstracción de almacenamiento. Se mantuvieron las funcionalidades y validaciones de la aplicación. No fue necesario modificar el menú para cambiar el formato de persistencia.

**4. ¿Qué partes permanecieron intactas y qué decisiones lo hicieron posible?**

Las entidades y las operaciones principales se conservaron. La separación entre gestión y persistencia permite cambiar el formato sin rehacer las funcionalidades de la aplicación.

**5. ¿Qué cambiaría para facilitar una futura sustitución del formato?**

Mantendría las operaciones de almacenamiento detrás de una interfaz común y reforzaría las pruebas de lectura, escritura, migración y recuperación ante errores. También procuraría que la traducción del formato tuviera pruebas específicas para evitar pérdidas o interpretaciones incorrectas de los datos.

## 8. Conclusión

P2 conserva la funcionalidad principal de P1 y añade un almacenamiento habitual en JSON y un procedimiento de migración desde CSV. La comparación de ramas y el historial de Git permiten seguir la evolución de la solución y relacionar los cambios con las decisiones de diseño.
