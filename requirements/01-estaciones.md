# 01 — Estaciones, facturación y surtidores

Aplicar también `requirements/00-alcance.md`.

## Requerimientos y criterios de aceptación

- Registrar estaciones con código interno único, nombre comercial, dirección, teléfono y fecha de apertura.
- Registrar fichas de facturación con código único, serie de tickets, siguiente número y texto al pie; distinguirlas de los datos fiscales comunes.
- Relación obligatoria uno a uno estación–ficha: no admitir altas confirmadas incompletas, fichas independientes o compartidas. Explicar el procedimiento de alta conjunta.
- Registrar surtidores con código de equipo único global, número visible, combustible y estado de servicio.
- Cada surtidor pertenece a una sola estación; número visible único dentro de ella, reutilizable en otras. Un surtidor averiado sigue adscrito.
- Cada estación tiene al menos un surtidor; no retirar el último ni registrar equipos sin destino. Documentar cómo garantizar estas reglas.
- Comprobar varias estaciones con fichas y surtidores, incluida una con varios equipos.
