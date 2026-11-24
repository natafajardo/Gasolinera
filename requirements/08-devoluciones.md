# 08 — Devoluciones

Aplicar también `requirements/00-alcance.md`.

## Requerimientos y criterios de aceptación

- Registrar devolución de una línea concreta de una venta pagada, con cantidad, motivo, fecha y hora, empleado e importe reintegrado. No se exige documento agrupador.
- Admitir devoluciones parciales y sucesivas; suma de cantidades no superior a la comprada. Respetar unidades completas o precisión de pesada según modalidad.
- Calcular reintegro usando precio original, no vigente; redondear evitando que devoluciones parciales acumuladas superen el importe de línea.
- Registrar reintegro separado de pagos recibidos. No borrar ni modificar cantidades o precios de la venta original.
- Indicar si la mercancía es apta para volver a venderse; granel devuelto no implica reposición. No implementar existencias.
- Documentar control de acumulación y concurrencia para fase posterior; no se exige su implementación completa ahora.
- Demostrar devoluciones parciales sucesivas sin superar cantidad ni importe originales.
