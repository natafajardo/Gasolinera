# 07 — Ventas, líneas y pagos

Aplicar también `requirements/00-alcance.md`.

## Requerimientos y criterios de aceptación

- Registrar venta de tienda con referencia de ticket única global, fecha y hora, estación y empleado; cliente opcional para ventas anónimas.
- Conservar estación de la venta aunque cambie la adscripción del empleado.
- Una venta registrada tiene al menos una línea; carrito vacío aún no es venta. Cada línea corresponde a un producto y una venta, con número de orden único dentro del ticket.
- Guardar cantidad, precio unitario realmente aplicado e importe de cada línea; no recalcular tickets por cambios del catálogo ni reconstruir precios mediante historial.
- Integrar cobros con `Pagos` sin perder campos ni comportamientos previos ni transformar registros antiguos silenciosamente. Documentar integración.
- Cada cobro de tienda pertenece a una venta; admitir varios pagos por venta y guardar importe, fecha y hora, medio de pago.
- Distinguir cobro en curso de venta pagada: al confirmar pago, suma de pagos igual al importe de venta; definir estados.
- Demostrar ventas anónimas e identificadas, tickets multilínea y pago dividido.
