
# 1. Introducción

**Descripción**

El presente documento describe los pasos que el Usuario Externo deberá seguir para agendar una cita médica mediante un asistente de 5 pasos: selección de sucursal, especialidad, médico, fecha/hora y confirmación con motivo de consulta.

**Objetivo**

Permitir al paciente registrado agendar citas médicas de forma eficiente, con selección de especialidad, sucursal y horarios disponibles.

# 2. Definición Caso de Uso

## 2.1 Actores

•      Usuario Externo (Paciente)

•      Sistema informático

## 2.2 Precondiciones

•      El sistema debe estar disponible.

•      El Usuario Externo debe estar registrado en el sistema [CU-02].

•      Debe existir disponibilidad de horarios según la especialidad seleccionada.

•      Catalogos deben de estar previamente cargado en cache.

## 2.3 Flujo Normal Básico

1.    El sistema muestra en pantalla el asistente de agendamiento de cita con un indicador de progreso de 5 pasos.

2.    Paso 1 – Sucursal: El Usuario Externo selecciona una sucursal de la lista de sucursales activas. [RN-CU03-01]

3.    Paso 2 – Especialidad: El sistema carga las especialidades disponibles en la sucursal seleccionada. El Usuario Externo selecciona una especialidad. [RN-CU03-02] [FA02]

4.    Paso 3 – Médico: El sistema carga los médicos disponibles para la especialidad y sucursal seleccionadas. El Usuario Externo selecciona un médico. [RN-CU03-03]

5.    Paso 4 – Fecha y Hora: El sistema muestra el calendario (DynamicCalendar) con la disponibilidad de horarios del médico seleccionado. El Usuario Externo selecciona un día y hora disponible. [RN-CU03-04] [FA02]

6.    Paso 5 – Confirmar: El sistema muestra el resumen de la cita con los datos: sucursal, especialidad, médico, fecha y hora seleccionados.

7.    El Usuario Externo ingresa el motivo de la consulta (mínimo 10 caracteres, máximo 2000). [RN-CU03-05]

8.    El Usuario Externo selecciona el botón "Confirmar Cita". [FA04]

9.    El sistema registra la cita con estado "Pendiente de pago" y muestra el mensaje: "Su cita ha sido registrada exitosamente. Será redirigido al proceso de pago para confirmar la reserva."

10. El sistema redirige al apartado de pago en línea donde se inicia un temporizador de 5 minutos para completar el pago. [CU-04] [FA03]

11. El caso de uso finaliza cuando el Usuario Externo es redirigido a la pantalla de pago.

12. Nota: En cada paso del wizard el Usuario Externo puede regresar al paso anterior mediante el botón "Volver".

13. Fin del caso de uso.

## 2.4 Flujos Alternos

**FA01 No hay especialidades disponibles en la sucursal**

1.    La sucursal seleccionada no tiene especialidades configuradas.

2.    El sistema muestra el mensaje: "No hay especialidades disponibles para la sucursal [nombre sucursal]. Seleccione otra sucursal."

3.    El Usuario Externo regresa al paso 1 y selecciona otra sucursal.

4.    Se continúa en el paso 2 del flujo normal básico.

**FA02 No hay médicos o disponibilidad de horarios**

1.    El sistema no encuentra horarios disponibles para la combinación de especialidad y sucursal.

2.    El sistema muestra el mensaje: "No se encontraron horarios disponibles para la especialidad [Especialidad] en la Sede [Sede]. Por favor, seleccione otra especialidad o sede."

3.    El Usuario Externo modifica la selección.

4.    Se continúa en el paso 2 del flujo normal básico.

**FA03 Tiempo de reserva expirado**

1.    El temporizador llega a cero sin que el Usuario Externo confirme.

2.    El sistema libera el horario reservado temporalmente.

3.    El sistema muestra el mensaje: "El tiempo para confirmar su cita ha expirado. El horario seleccionado ha sido liberado. Por favor, seleccione un nuevo horario."

4.    El sistema regresa al paso 4 (selección de fecha/hora) del flujo normal básico.

**FA04 El Usuario Externo regresa a un paso anterior**

1.    El Usuario Externo selecciona el botón "Volver" en cualquier paso del wizard.

2.    El sistema regresa al paso anterior del wizard sin confirmación adicional.

3.    Las selecciones de los pasos posteriores se reinician.

4.    El sistema libera el horario reservado temporalmente.

5.    El sistema muestra el paso anterior del wizard y el Usuario Externo puede continuar desde allí.

## 2.5 Postcondiciones

•      La cita queda registrada en el sistema con estado "Pendiente de pago".

•      El horario seleccionado queda reservado hasta completar el pago.

•      El motivo de la consulta queda registrado y asociado a la cita.