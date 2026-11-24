# 05 — Proveedores y acuerdos de suministro

Aplicar también `requirements/00-alcance.md`.

## Requerimientos y criterios de aceptación

- Registrar proveedores con código interno único, identificación fiscal, razón social y contacto. Excluir contactos aún en negociación.
- Relación muchos a muchos con productos: cada acuerdo conserva referencia del proveedor, precio de compra acordado y plazo habitual de entrega.
- No duplicar acuerdos vigentes del mismo proveedor y producto. No se exige historial completo de condiciones.
- Todo proveedor debe suministrar al menos un producto; todo producto debe tener al menos un proveedor, incluidos retirados (conservar al menos su último acuerdo).
- No confirmar altas incompletas ni borrar el último acuerdo dejando aislado un producto o proveedor. Explicar cómo organizar altas y cambios en varios pasos.
- Demostrar proveedor con varios productos y producto con varios proveedores, sin aislados.
