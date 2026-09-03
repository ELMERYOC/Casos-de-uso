# 1. Introducción

**Descripción**

El presente documento describe los pasos que el Usuario Externo (paciente) deberá seguir para realizar el pago en línea de su cita médica mediante tarjeta de crédito o débito.

**Objetivo**

Permitir al paciente completar el pago de su cita médica de forma segura a través de una pasarela de pago en línea, garantizando la confirmación automática de la transacción y el envío de comprobante.

# 2. Definición Caso de Uso

## 2.1 Actores

•      Usuario Externo (Paciente)

•      Sistema informático

•      Pasarela de pago

## 2.2 Precondiciones

•      El sistema debe estar disponible.

•      El paciente debe tener una cita agendada previamente [CU-03].

•      El paciente debe contar con una tarjeta de crédito o débito válida.

•      La pasarela de pago debe estar operativa.

•      Catalogos previamente cargados en cache.

## 2.3 Flujo Normal Básico

1.    El sistema muestra la pantalla “Pago de Consulta” con un temporizador regresivo de 5 minutos (ReservationTimer) y el resumen de la cita: médico, especialidad, Sede, fecha, hora y total a pagar en Quetzales. [FA02]

2.    El sistema muestra el formulario “Datos de pago” con los campos: número de tarjeta, nombre del titular, vencimiento (MM/AA) y CVV.

3.    El paciente ingresa el número de tarjeta (13-19 dígitos, validación Luhn). Al perder el foco, el número se enmascara mostrando solo los últimos 4 dígitos. [RN-CU04-01]

4.    El paciente ingresa el nombre del titular de la tarjeta (mínimo 5, máximo 100 caracteres, se convierte a mayúsculas). [RN-CU04-02]

5.    El paciente ingresa la fecha de vencimiento en formato MM/AA (auto-formateado). [RN-CU04-03]

6.    El paciente ingresa el código de seguridad CVV (3-4 dígitos, campo tipo password). Nota de seguridad: Tu información de pago está protegida. El CVV nunca se almacena ni se envía. [RN-CU04-04]

7.    El paciente selecciona el botón “Pagar Q[monto]”. [FA01] [FA03]

8.    El sistema valida todos los campos del formulario. Si hay errores, muestra el mensaje de validación correspondiente debajo de cada campo. [FA01]

9.    El botón cambia a estado “Procesando pago...” y se deshabilita para evitar doble envío (idempotencia mediante UUID).

10. El sistema envía los datos a la pasarela de pago de forma encriptada.

11. La pasarela de pago procesa la transacción y retorna resultado exitoso.

12. El sistema confirma el pago y actualiza el estado de la cita a "Pagada". [FA03]

13. El sistema muestra el mensaje: "¡Pago realizado exitosamente! Número de transacción: [Número]. Su cita ha sido confirmada."

14. El sistema redirige a la pantalla “¡Pago Exitoso!” (ConfirmationPage) que muestra un comprobante de pago con: número de transacción, médico, especialidad, sucursal, fecha, hora y monto pagado. Además, muestra un aviso de que se ha enviado un correo de confirmación al email del paciente. [RN-CU04-05]

15. La pantalla de confirmación ofrece dos botones: “Volver al Portal” (redirige al dashboard) y “Ver Mis Citas” (redirige al listado de citas del paciente). Fin del caso de uso.

## 2.4 Flujos Alternos

**FA01 Validación de campos del formulario fallida**

1.    El sistema detecta que uno o más campos no cumplen las reglas de validación (ver RN-CU04-01 a RN-CU04-04).

2.    El sistema muestra los mensajes de error específicos debajo de cada campo que falló (ej. “El número de tarjeta no es válido”, “Formato inválido. Use MM/AA”, “La tarjeta está vencida”).

3.    El paciente corrige los campos señalados.

4.    Se continúa en el paso 8 del flujo normal básico.

5.    Fin del caso de uso.

**FA02 Temporizador de reserva expirado (5 minutos)**

1.    El temporizador regresivo de 5 minutos llega a cero sin que el paciente complete el pago.

2.    El sistema muestra un banner de error con el mensaje: "El tiempo para confirmar su cita ha expirado. El horario seleccionado ha sido liberado. Por favor, seleccione un nuevo horario. Será redirigido en unos segundos..."

3.    El sistema redirige automáticamente al paciente a la pantalla de reserva de citas después de 4 segundos.

4.    El paciente debe seleccionar un nuevo horario e iniciar el proceso de reserva nuevamente.

**FA03 Pago rechazado por la pasarela**

1.    La pasarela de pago rechaza la transacción.

2.    El sistema muestra un mensaje de error según el tipo de fallo:

3.    - Rechazo bancario: "La transacción con tarjeta fue rechazada por el banco. Por favor, verifique los datos de su tarjeta o intente con una tarjeta diferente."

4.    - Error de procesamiento: "El pago no pudo ser procesado. Por favor, intente nuevamente o utilice otra tarjeta."

5.    - Error de comunicación: "Error de comunicación con la pasarela de pago. Intente nuevamente en unos minutos."

6.    El formulario de pago permanece activo y el temporizador de reserva sigue corriendo.

7.    El paciente puede corregir los datos y reintentar el pago sin perder la reserva del horario.

8.    El sistema permite al paciente reintentar el pago.

9.    Se continúa en el paso 4 del flujo normal básico.

## 2.5 Postcondiciones

•      La cita queda registrada con estado "Pagada" en el sistema.

•      El paciente recibe un comprobante de pago en su correo electrónico.