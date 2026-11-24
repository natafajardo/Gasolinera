# Estilo de código Java

## Nivel de desarrollo

- El código debe corresponder a un nivel básico o junior de Java.
- Priorizar soluciones sencillas, intuitivas y fáciles de explicar.
- Utilizar construcciones básicas e intermedias del lenguaje.
- Evitar implementaciones sofisticadas o excesivamente avanzadas.
- Priorizar la legibilidad sobre la optimización prematura.
- El código debe poder ser comprendido y mantenido por un desarrollador junior.

## Construcciones preferidas

- Bucles tradicionales `for`, `while` y `for-each`.
- Condicionales `if`, `else` y `switch`.
- Clases, objetos, constructores y métodos sencillos.
- Colecciones estándar como `ArrayList`, `HashMap` y `HashSet`.
- Interfaces y herencia únicamente cuando estén justificadas.
- Gestión de excepciones mediante `try-catch`.
- Variables intermedias cuando mejoren la comprensión.

## Construcciones que se deben evitar

- Streams y expresiones lambda complejas.
- Programación funcional avanzada.
- Reflexión y metaprogramación.
- Genéricos complejos.
- Programación reactiva.
- Concurrencia avanzada.
- Patrones de diseño innecesarios.
- Abstracciones y capas excesivas.
- Expresiones difíciles de leer.
- Dependencias externas innecesarias.

## Buenas prácticas

- Aplicar KISS, DRY y YAGNI.
- Aplicar SOLID de manera sencilla y proporcional.
- Utilizar nombres descriptivos.
- Usar camelCase para métodos y variables.
- Usar PascalCase para clases.
- Usar UPPER_SNAKE_CASE para constantes.
- Mantener métodos pequeños y cohesionados.
- Respetar la encapsulación.
- Evitar duplicaciones innecesarias.
- Gestionar correctamente las excepciones.
- Escribir pruebas sencillas cuando corresponda.
- Añadir comentarios únicamente cuando aporten valor.

## Regla fundamental

Ante varias soluciones igualmente correctas, elegir la más sencilla de comprender, implementar, mantener y explicar.

La simplicidad no debe comprometer la seguridad, la corrección ni el cumplimiento de los requerimientos.
