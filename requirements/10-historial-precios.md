# 10 — Trigger MySQL del historial de precios

Aplicar también `requirements/00-alcance.md`.

## Requerimientos y criterios de aceptación

- Crear trigger MySQL que registre automáticamente producto, precio anterior, nuevo y fecha y hora ante un cambio real del precio de venta.
- Debe funcionar con actualizaciones SQL directas; Java no inserta historial.
- No generar historial al crear el producto, guardar el mismo precio o modificar exclusivamente otros campos.
- No ofrecer operaciones ordinarias para modificar o borrar historial.
- Usar InnoDB: rollback de un cambio revierte precio y entrada histórica; no se pide auditoría externa de intentos fallidos.
- Comprobar cambio real, mismo precio, cambio de descripción, precio de venta anterior intacto y rollback de precio e historial.
- Los triggers de existencias por devolución y bloqueo de líneas cerradas son ampliaciones opcionales futuras, no sustituyen al obligatorio ni se exigen ahora.
