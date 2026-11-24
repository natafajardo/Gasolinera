# Especificación de entregables — Fase 1

## 1. Objetivo

Mantener actualizados los entregables exigidos en la ampliación de la aplicación de gasolinera con MySQL.

Los entregables se generarán de manera incremental durante la implementación de cada requerimiento funcional mediante RPI.

## 2. Documentación

### modelo-relacional.md

- Finalidad de cada tabla.
- Claves primarias y foráneas.
- Relaciones y cardinalidades.
- Restricciones de unicidad.
- Comportamiento ante modificaciones y eliminaciones.
- Representación textual, sin diagrama entidad-relación.

### diccionario-datos.md

Para cada tabla, documentar:

- Nombre y descripción de las columnas.
- Tipos de datos.
- Campos obligatorios y opcionales.
- Valores predeterminados.
- Claves y restricciones.

### modelo-java.md

- Clases del dominio y sus responsabilidades.
- Relaciones entre clases.
- Correspondencia con el modelo relacional.
- Justificación de las decisiones de diseño.
- Representación de las funciones cambiantes del personal.
- Representación de las modalidades de producto.
- Integración con Gasolinera y Pagos existentes.

### reglas-negocio.md

Para cada regla de negocio, indicar:

- Requerimiento funcional asociado.
- Descripción de la regla.
- Mecanismo que garantiza su cumplimiento.
- Estado: implementada o pendiente.
- Propuesta de implementación futura, cuando corresponda.

Diferenciar las reglas garantizadas mediante restricciones SQL, triggers y controles adicionales.

### comprobaciones.md

- Comprobaciones realizadas.
- Resultado esperado.
- Resultado obtenido.
- Evidencias de ejecución.
- Errores y limitaciones detectadas.
- Comprobación de compatibilidad con CSV y JSON.

### operaciones-jdbc.md

Describir tres operaciones futuras mediante JDBC, indicando:

- Objetivo.
- Datos de entrada.
- Información afectada.
- Reglas de negocio que deben respetarse.

No implementar estas operaciones durante la fase 1.

### auditoria-ia.md

Registrar intervenciones reales de la IA:

- Petición realizada.
- Propuesta de la IA.
- Solución aceptada, modificada o rechazada.
- Motivo de la decisión.
- Forma de verificación.
- Aspectos positivos y errores detectados.

Registrar al menos tres intervenciones relevantes, incluyendo una decisión de diseño y una comprobación de comportamiento.

Este documento servirá como base para elaborar AUDITORIA_IA.odt.

## 3. Scripts SQL

Mantener los siguientes archivos en el directorio sql/:

- 01_esquema.sql: base de datos, tablas, claves y restricciones.
- 02_triggers.sql: trigger obligatorio del historial de precios.
- 03_datos.sql: datos de ejemplo.
- 04_comprobaciones.sql: consultas y operaciones de validación, con resultados esperados.

Los tres primeros scripts deben poder ejecutarse en orden sobre una base de datos vacía.

Separar las comprobaciones que deban producir errores deliberadamente.

Incluir al menos cinco registros por tabla principal, salvo excepciones justificadas, y diez líneas de venta distribuidas entre varios tickets.

Los datos deben demostrar las relaciones y reglas de negocio exigidas en los requerimientos.

## 4. Proyecto Java

- Proyecto actualizado y compilable.
- Modelo de dominio coherente con el modelo relacional.
- Conservación de las funcionalidades CSV y JSON.
- Demostración de la validación de cantidades y cálculo de importes de las dos modalidades de producto.
- Conexión JDBC existente conservada.
- Sin implementar operaciones CRUD mediante JDBC durante esta fase.

## 5. Documentación general

Mantener en la raíz del proyecto:

### README.md

- Descripción del proyecto.
- Requisitos de ejecución.
- Configuración de MySQL y JDBC sin credenciales personales.
- Orden de ejecución de los scripts SQL.
- Instrucciones de compilación.
- Instrucciones para ejecutar la demostración Java.

### AGENTS.md

- Información e instrucciones para los agentes de IA.

### AUDITORIA_IA.odt

- Auditoría final de las intervenciones de IA.
- Ejemplos concretos de propuestas correctas e incorrectas.
- Decisiones adoptadas y comprobaciones realizadas.

## 6. Actualización incremental

Durante cada ejecución RPI:

1. Identificar los entregables afectados.
2. Incluir su actualización en el plan.
3. Actualizar únicamente las secciones correspondientes al requerimiento actual