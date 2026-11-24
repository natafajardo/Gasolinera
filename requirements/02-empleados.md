# 02 — Empleados, funciones y supervisión

Aplicar también `requirements/00-alcance.md`.

## Requerimientos y criterios de aceptación

- Registrar número interno único, documento de identidad, nombre, contacto y fecha de incorporación; cada empleado está adscrito a una estación.
- Conservar identidad y antigüedad ante cambios de estación o responsabilidades; las ventas anteriores conservan su estación.
- Representar funciones de cajero (límite de devolución), expendedor (número y caducidad de acreditación), responsable de tienda (presupuesto de compras) y jefe (nivel de autorización excepcional).
- Permitir ninguna, una o varias funciones simultáneas diferentes, asignadas y retiradas independientemente. Conservar inicio, fin si termina y condiciones específicas de cada periodo.
- Permitir recuperar una función con otras condiciones sin modificar el historial ni la autoría; prohibir solapamientos de la misma función por persona. Prever nuevas funciones y justificar el diseño Java.
- Supervisión actual: cero o un supervisor empleado por persona, cero o varios subordinados; prohibir autosupervisión y ciclos directos o indirectos. No confundir supervisión con jefatura; no exigir historial de supervisión.
- Documentar el tratamiento futuro de ciclos y solapamientos.
- Ejemplos: empleado sin funciones, con varias simultáneas y con función recuperada; personal sin supervisor, con supervisor y supervisor de varias personas.
