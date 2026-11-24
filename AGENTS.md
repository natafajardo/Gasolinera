# Instrucciones del proyecto

- Consultar context.md para decisiones técnicas y style.md para cambios de código; no releerlos si siguen disponibles y vigentes en contexto.
- Los requerimientos funcionales están en requirements/. Usar la skill rpi cuando se invoque $rpi.
- Preferir la solución más sencilla que cumpla los criterios de aceptación; respetar las convenciones existentes.
- No modificar requerimientos, directrices técnicas ni cambios ajenos sin autorización.
- No introducir frameworks o dependencias sin autorización.
- Comprobar compilación y pruebas disponibles con las herramientas existentes; informar de errores y validaciones no realizadas.
## Desarrollo incremental mediante RPI

- Cada ejecución RPI corresponde a un único fichero de requerimientos funcionales.
- No implementar funcionalidades pertenecientes a otros requerimientos.
- Consultar otros requerimientos únicamente cuando existan dependencias.
- Respetar las funcionalidades implementadas anteriormente.
- Reutilizar las clases y estructuras existentes.
- Actualizar el modelo relacional y el modelo Java de forma incremental.
- Mantener actualizados los scripts SQL correspondientes.
- Identificar las restricciones pendientes de funcionalidades futuras.
- Comprobar que el proyecto compila después de cada implementación.
- No introducir regresiones en CSV ni JSON.
- Mantener un nivel de programación básico o junior.
- Evitar refactorizaciones fuera del alcance del requerimiento actual.
- No implementar operaciones CRUD mediante JDBC durante la fase 1.

## Entregables del proyecto

- Consultar deliverables/SPEC.md durante cada ejecución RPI.
- Durante Research, identificar los entregables afectados.
- Durante Plan, incluir la actualización de dichos entregables como parte del plan.
- Durante Implement, actualizar la documentación y los scripts SQL correspondientes al requerimiento.
- Durante la validación final, comprobar la coherencia entre el código, SQL y documentación.
- Actualizar los entregables de forma incremental.
- No generar nuevamente toda la documentación en cada ejecución.
- No modificar entregables ajenos al requerimiento actual.
- No considerar completado un requerimiento si sus entregables correspondientes están desactualizados.
- Mantener separados los artefactos internos de .rpi/ de los entregables finales del proyecto.

