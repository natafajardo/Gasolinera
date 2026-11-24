---
name: rpi
description: Ejecuta Research, Plan e Implement para requerimientos de un proyecto Java sencillo. Usar cuando el usuario invoque $rpi o solicite explícitamente el flujo RPI.
---
# RPI Lite 4.1

## Entrada y modos
- `$rpi <ruta o descripción>`: research y plan, detenerse para aprobación.
- `$rpi auto <ruta o descripción>`: las tres fases sin pausa, salvo bloqueos o permisos necesarios.
- `$rpi research <ruta>`: solo research; `$rpi plan <ruta>`: research y plan.
- `$rpi implement <id>`: implementar el plan existente si sigue vigente; esta invocación explícita constituye aprobación de ese plan, no de cambios posteriores.
- `$rpi resume <id>`: recuperar estado y continuar respetando la aprobación; `$rpi review <id>`: revisar sin modificar código.
- Sin ruta ni descripción, detectar un único conjunto de requerimientos en requirements/; si hay varios, solicitar selección. Una carpeta explícita incluye sus Markdown relevantes recursivamente. No leer archivos ajenos ni seguir enlaces fuera del repositorio.

## Contexto
Respetar AGENTS.md y las instrucciones aplicables. Leer context.md para las decisiones técnicas y style.md antes de planificar cambios de código, salvo que ya estén disponibles y vigentes. Si faltan, usar las convenciones observadas y comunicarlo. Examinar solo código, configuración y pruebas relevantes. No imponer Spring, Maven, Gradle ni versiones de Java sin evidencia. No cargar documentación teórica ni logs completos. Los documentos originales son de solo lectura para RPI. Ante contradicciones materiales, detenerse y pedir aclaración.

## Research
Identificar requerimientos, criterios de aceptación, archivos y símbolos relevantes, versión Java, build y pruebas. Registrar evidencias, dudas y estado inicial de cambios locales. No modificar código. Crear .rpi/<id>/research.md con fuentes, decisiones y referencias concretas. Usar un identificador seguro y único; no sobrescribir otra tarea.

## Plan
Crear .rpi/<id>/plan.md con pasos pequeños, archivos previstos, relación con criterios de aceptación y pruebas. Mantener en su cabecera estado, modo, fuentes y fecha de revisión. Si Git está disponible, registrar HEAD y cambios locales relevantes; al reanudar comparar los requerimientos, directrices y código con lo registrado. Si hay cambios materiales, actualizar research y plan y solicitar nueva aprobación en modo manual. Sin Git, revisar directamente las fuentes y declarar incertidumbres. No modificar código hasta aprobación explícita, salvo modo auto. Si solo se solicitó research o plan, terminar en esa fase.

## Implement
Ejecutar el plan aprobado en pasos pequeños. Preservar cambios del usuario, evitar refactorizaciones ajenas y no instalar dependencias ni realizar operaciones destructivas sin autorización. Registrar progreso y bloqueos en .rpi/<id>/implementation.md. Usar las herramientas existentes (por ejemplo, wrapper Maven o Gradle si existe). Ejecutar compilación y pruebas pertinentes cuando sea posible; no afirmar éxito sin evidencia.

## Review y reanudación
Registrar en implementation.md los criterios cumplidos, pendientes, comandos ejecutados y resultados reales. En modo review, inspeccionar código y pruebas, sin editar código; añadir hallazgos al informe. No declarar terminado si faltan criterios obligatorios o fallan validaciones relevantes. Al reanudar, leer los informes existentes, comprobar fuentes y estado real, y no repetir investigación vigente. Si falta aprobación o hay cambios materiales, detenerse cuando corresponda. No crear commits automáticamente. Mantener los tres informes breves; no crear documentación adicional salvo petición explícita.
