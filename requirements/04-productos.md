# 04 — Productos envasados y a granel

Aplicar también `requirements/00-alcance.md`.

## Requerimientos y criterios de aceptación

- Registrar referencia comercial única, descripción, precio vigente obligatorio mayor que cero y estado de comercialización. Mantener identificables los productos retirados.
- Cada producto tiene una única modalidad estable; un mismo alimento envasado y a granel son referencias diferentes.
- Envasado: formato de envase y cantidades enteras positivas. Granel: cantidades positivas que pueden ser decimales y respetan la precisión de pesada definida.
- Implementar en Java validación de cantidad y cálculo de importe según modalidad. La preparación del ticket debe tratar ambas modalidades sin repetir todas sus decisiones de venta.
- Definir criterio común de redondeo monetario y tipos adecuados de precios, cantidades e importes. Rechazar 1,5 unidades de un producto envasado.
- Justificar el diseño Java de modalidades estables y mostrar en una demostración sin MySQL ambas modalidades y sus cálculos.
