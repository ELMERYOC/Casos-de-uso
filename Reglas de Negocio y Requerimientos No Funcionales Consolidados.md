# 1. Reglas de Negocio Globales

**RN-GLOBAL-001****: Validación de DPI**

•      Es obligatorio. Mensaje: "El campo DPI es obligatorio. Por favor, ingrese su número de DPI."

•      Exactamente 13 caracteres. Mensaje: "El DPI debe contener exactamente 13 dígitos. Usted ingresó [X] dígitos."

•      Numérico (solo dígitos). Mensaje: "El DPI debe contener únicamente números. No se permiten letras ni caracteres especiales."

•      Aplica a: CU-00, CU-02, CU-05, CU-06, CU-07, CU-09, CU-10, CU-16

**RN-GLOBAL-002****: Validación de NIT**

•      Obligatorio. Mensaje: "El campo NIT es obligatorio."

•      Entre 8 y 9 caracteres. Mensaje: "El NIT debe contener entre 8 y 9 caracteres. Usted ingresó [X] caracteres."

•      Alfanumérico. Mensaje: "El NIT debe contener únicamente caracteres alfanuméricos."

•      Aplica a: CU-01, CU-02

**RN-GLOBAL-003****: Disponibilidad del Sistema**

•      El sistema debe estar disponible como precondición para todos los casos de uso.

•      Si no disponible: "El sistema se encuentra en mantenimiento. Por favor, intente más tarde. Disculpe las molestias."

•      Si módulo específico no disponible: "El módulo [nombre] no está disponible temporalmente. Los demás servicios continúan operando."

•      Aplica a: Todos (CU-00 a CU-16)

**RN-GLOBAL-004****: Métodos de Pago Aceptados**

•      Efectivo en moneda local (Quetzales - GTQ).

•      Tarjeta de crédito: Visa, Mastercard.

•      Tarjeta de débito.

•      Para pagos en línea (CU-04) solo tarjetas.

•      Método no aceptado: "El método de pago seleccionado no está disponible. Los métodos aceptados son: efectivo (Quetzales), tarjeta de crédito (Visa/Mastercard) o tarjeta de débito."

•      Aplica a: CU-04, CU-06, CU-09, CU-10, CU-16

**RN-GLOBAL-005****: Contenido de Comprobantes de Pago**

Todo comprobante debe contener:

•      Número de transacción único.

•      Nombre completo del paciente.

•      Monto pagado y forma de pago.

•      Fecha y hora de la transacción.

•      Detalle del servicio (cita, exámenes o medicamentos).

•      Nombre de la sucursal.

•      Aplica a: CU-04, CU-06, CU-09, CU-10, CU-16

**RN-GLOBAL-006****: Contenido de Notificaciones por Correo**

Toda notificación por correo debe incluir:

•      Datos identificativos del paciente y detalle del servicio.

•      Pie de correo: "Este es un correo automático del Sistema Informático Hospitalario. No responda a este mensaje. Para consultas, comuníquese al teléfono [número]."

•      Si falla el envío: "Error al enviar notificación por correo electrónico al paciente [Nombre]. Se reintentará automáticamente."

•      Aplica a: CU-00, CU-02, CU-04, CU-11

**RN-GLOBAL-007****: Autenticación de Usuarios Internos**

•      Debe iniciar sesión con credenciales válidas.

•      Sesión expirada: "Su sesión ha expirado por inactividad. Por favor, inicie sesión nuevamente."

•      Credenciales incorrectas: "Las credenciales ingresadas son incorrectas. Tiene [X] intentos restantes antes del bloqueo temporal."

•      Cuenta bloqueada: "Su cuenta ha sido bloqueada temporalmente por múltiples intentos fallidos. Contacte al administrador del sistema."

•      Aplica a: CU-01, CU-05, CU-06, CU-07, CU-08, CU-09, CU-10, CU-11, CU-16

# 2. Reglas de Negocio por Caso de Uso

## CU-00: Visualización del Portal Web

**RN-CU00-01****: Validación de Cliente Registrado**

•      Si existe: "Bienvenido(a), [Nombre del paciente]. Será redirigido al formulario de agendamiento de cita."

•      Si no existe: "No se encontró un registro asociado a este DPI. Será redirigido al formulario de registro."

**RN-CU00-02****: Contenido de Correo de Confirmación de Cita**

•      Incluye: nombre paciente, especialidad, médico, fecha, hora, sucursal.

•      Asunto: "Confirmación de Cita Médica - Hospital [Nombre]"

•      Mensaje en pantalla: "Su cita ha sido agendada exitosamente. Se ha enviado una confirmación al correo [correo]."

**RN-CU00-03****: Bloqueo por Intentos Fallidos de Inicio de Sesión**

•      Máximo 5 intentos fallidos de inicio de sesión consecutivos.

•      Al alcanzar el límite, bloqueo temporal de 15 minutos.

•      Mensaje: “Cuenta bloqueada temporalmente. Intente de nuevo en 15 minutos.”

•      Los campos de usuario, contraseña y botón de inicio de sesión se deshabilitan durante el período de bloqueo.

## CU-01: Mantenimiento de Usuarios

**RN-CU01-01****: Filtros de Búsqueda**

•      Selector de campo (Usuario, Nombre, NIT, Rol, Sucursal).

•      Campo de texto: máx 25 caracteres. Mensaje: "El campo de búsqueda no puede exceder los 25 caracteres."

•      Sin resultados: "No se encontraron resultados para los criterios de búsqueda ingresados. Por favor, modifique los filtros e intente nuevamente."

**RN-CU01-02****: Campos de Resultado**

•      Columnas: Usuario (ordenable), Nombre (ordenable), Rol, NIT, Estado, Sucursal.

•      Paginación: 20 registros/página.

**RN-CU01-03****: Catálogo de Roles**

•      Obligatorio. Mensaje: "Debe seleccionar un rol para el usuario."

•      Opciones: Médico, Enfermero, Recepcionista, Cajero, Laboratorista, Farmacéutico, Administrador.

**RN-CU01-04****: Nombre**

•      Obligatorio. Mensaje: "El campo Nombre es obligatorio."

•      10-100 caracteres. Mensaje: "El nombre debe contener entre 10 y 100 caracteres. Usted ingresó [X] caracteres."

**RN-CU01-05****: Credenciales**

•      Obligatorio. Mensaje: "El campo Usuario es obligatorio."

•      8-9 caracteres. Mensajes: "El usuario no puede exceder los 9 caracteres." / "El usuario debe contener al menos 8 caracteres."

•      Alfanumérico. Mensaje: "El usuario debe contener únicamente caracteres alfanuméricos."

•      Único. Mensaje: "El nombre de usuario [usuario] ya se encuentra registrado. Por favor, elija otro."

**RN-CU01-06****: Sucursal**

•      Obligatorio. Mensaje: "Debe seleccionar una sucursal para el usuario."

**RN-CU01-07****: Documento de Identificación**

•      Opcional.

•      Si se ingresa: exactamente 13 dígitos numéricos (aplica validación DPI según RN-GLOBAL-001).

**RN-CU01-08****: Número de Teléfono**

•      Opcional.

•      Si se ingresa: exactamente 8 dígitos numéricos.

•      Mensaje: “El teléfono debe contener exactamente 8 dígitos.”

**RN-CU01-09****: Rol del Usuario**

•      Obligatorio en formulario de creación.

•      Dropdown con catálogo de roles activos del sistema (ver RN-CU01-03).

•      Mensaje: “Debe seleccionar un rol para el usuario.”

**RN-CU01-10****: Estado del Usuario**

•      Obligatorio. Valores: Activo (1), Inactivo (0).

•      Default en creación: Activo.

•      Mensaje: “Debe seleccionar un estado para el usuario.”

**RN-CU01-11****: NIT**

•      Opcional.

•      Si se ingresa: aplica validación según RN-GLOBAL-002 (8-9 caracteres alfanuméricos).

**RN-CU01-12****: Número de Seguro**

•      Opcional.

•      Si se ingresa: entre 5 y 50 caracteres.

•      Mensaje: “El número de seguro debe contener entre 5 y 50 caracteres.”

**RN-CU01-13****: Sucursal del Usuario**

•      Opcional en edición, obligatorio en creación ([ver RN-CU01-06](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_06)).

•      Dropdown con catálogo de sucursales activas.

**RN-CU01-14****: Especialidad**

•      Opcional. Visible únicamente cuando el rol seleccionado es Médico.

•      Dropdown con catálogo de especialidades activas.

•      Mensaje: “Debe seleccionar una especialidad para el médico.”

## CU-02: Registro de Usuarios Externos

**RN-CU02-01****: Nombre Completo**

•      Obligatorio.

•      10-100 caracteres.

•      Mensaje: "El nombre debe contener entre 10 y 100 caracteres. Usted ingresó [X] caracteres."

**RN-CU02-02****: Teléfono**

•      Obligatorio.

•      8 dígitos.

•      Mensaje: "El número de teléfono debe contener exactamente 8 dígitos numéricos."

**RN-CU02-03****: Seguro Médico**

•      Opcional.

•      5-50 caracteres si se ingresa.

**RN-CU02-04****: Correo Electrónico**

•      Obligatorio.

•      Formato email válido.

•      Mensaje: "El formato del correo electrónico no es válido. Ejemplo: usuario@dominio.com"

**RN-CU02-05****: Nombre de Usuario**

•      Obligatorio.

•      Entre 8 y 9 caracteres alfanuméricos.

•      Mensaje mínimo: “El usuario debe contener al menos 8 caracteres.”

•      Mensaje máximo: “El usuario no puede exceder los 9 caracteres.”

•      Único en el sistema. Mensaje: “El nombre de usuario ya se encuentra registrado.”

**RN-CU02-06****: Contraseña**

•      Obligatorio.

•      Mínimo 12 caracteres.

•      Mensaje: “La contraseña debe contener al menos 12 caracteres.”

## CU-03: Agendar Citas

**RN-CU03-01: Especialidad**

•      Obligatorio.

•      Mensaje: "Debe seleccionar una especialidad médica para continuar."

**RN-CU03-02: Sucursal**

•      Obligatorio.

•      Mensaje: "Debe seleccionar una sucursal para continuar."

**RN-CU03-03: Motivo de Visita**

•      Obligatorio.

•      10-2000 caracteres.

•      Mensaje: "El motivo debe contener entre 10 y 2000 caracteres. Usted ingresó [X] caracteres."

**RN-CU03-04: Documentos (Opcional)**

•      Formato: PDF.

•      No puede estar vacío.

•      No debe estar encriptado.

•      Máximo 2 MB.

•      Mensaje si inválido: "El documento debe ser un archivo PDF válido, no encriptado y con tamaño máximo de 2 MB."

**RN-CU03-05: Fecha y Hora**

•      Obligatorio.

•      Debe ser fecha futura.

•      Mensaje: "Debe seleccionar una fecha y hora futuras. Las citas no pueden agendarse en fechas pasadas o presentes."

## CU-04: Pago en Línea con Tarjeta

**RN-CU04-01: Número de Tarjeta**

•      Obligatorio.

•      13-19 dígitos.

•      Validación Luhn.

•      Mensaje: "El número de tarjeta debe contener entre 13 y 19 dígitos y ser válido."

**RN-CU04-02: Nombre Titular**

•      Obligatorio.

•      5-100 caracteres alfabéticos.

•      Sin caracteres especiales.

•      Mensaje: "El nombre del titular debe contener entre 5 y 100 caracteres alfabéticos sin especiales."

**RN-CU04-03: Fecha Vencimiento**

•      Obligatorio.

•      Formato MM/AA.

•      Tarjeta no vencida.

•      Mensaje: "La fecha de vencimiento debe estar en formato MM/AA y la tarjeta no debe estar vencida."

**RN-CU04-04: CVV**

•      Obligatorio.

•      3-4 dígitos.

•      Mensaje: "El CVV debe contener 3 ó 4 dígitos numéricos."

**RN-CU04-05: Contenido del Comprobante**

•      Número de transacción único.

•      Monto pagado.

•      Fecha y hora de la transacción.

•      Detalle de la cita.

•      Asunto correo: "Comprobante de Pago - Cita Médica - Hospital [Nombre]"

**RN-CU04-06: Mensajes de Rechazo de Pasarela**

•      Fondos insuficientes: "Su tarjeta fue rechazada por fondos insuficientes. Verifique su saldo e intente nuevamente."

•      Tarjeta inválida: "Su tarjeta fue rechazada. El número de tarjeta es inválido. Verifique los datos e intente nuevamente."

•      Tarjeta vencida: "Su tarjeta fue rechazada. La tarjeta está vencida. Utilice otra tarjeta de crédito o débito."

•      Error comunicación: "Error al procesar el pago. Por favor, intente nuevamente o contacte a su banco."

•      Sesión expirada: "Su sesión de pago ha expirado. Por favor, inicie el proceso de pago nuevamente."

## CU-05: Recepción y Verificación de Cita

**RN-CU05-01: Búsqueda de Cita**

•      Búsqueda por número de cita o DPI del paciente.

•      Al menos un campo obligatorio.

•      Mensaje: "Debe ingresar un número de cita o DPI para buscar."

•      Sin resultados: "No se encontró una cita asociada a los parámetros ingresados. Verifique los datos e intente nuevamente."

**RN-CU05-02: Estados de Cita**

•      Pagada (verde).

•      Pendiente de pago (amarillo).

•      Cancelada (rojo).

•      Mensaje para cada estado según disponibilidad de acciones.

## CU-06: Cobro de Consulta en Caja

**RN-CU06-01: Búsqueda para Cobro**

•      Búsqueda por número de cita o DPI.

•      Solo muestra citas con estado "Pendiente de pago".

•      Sin resultados: "No hay citas pendientes de pago bajo los parámetros indicados."

**RN-CU06-02: Formas de Pago**

•      Efectivo.

•      Tarjeta de crédito.

•      Tarjeta de débito.

•      Cambio: "Monto recibido: Q[monto]. Cambio a devolver: Q[cambio]."

**RN-CU06-03: Comprobante**

•      Número de transacción único.

•      Nombre completo del paciente.

•      Monto pagado.

•      Forma de pago.

•      Fecha y hora de la transacción.

•      Detalle de la cita.

•      Nombre de la sucursal.

## CU-07: Toma de Signos Vitales

**RN-CU07-01: Presión Arterial**

•      Obligatorio.

•      Formato: sistólica/diastólica.

•      Rango de captura: 60-250/40-150 mmHg.

•      Mensaje: "La presión arterial debe ingresarse en formato sistólica/diastólica (ej: 120/80) dentro de rangos válidos."

**RN-CU07-02: Temperatura**

•      Obligatorio.

•      1 decimal.

•      Rango: 34.0-42.0°C.

•      Mensaje: "La temperatura debe estar entre 34.0 y 42.0°C con un decimal."

**RN-CU07-03: Peso**

•      Obligatorio.

•      2 decimales.

•      Rango: 0.5-300 kg.

•      Mensaje: "El peso debe estar entre 0.5 y 300 kg con dos decimales."

**RN-CU07-04: Talla**

•      Obligatorio.

•      2 decimales.

•      Rango: 30-250 cm.

•      Mensaje: "La talla debe estar entre 30 y 250 cm con dos decimales."

**RN-CU07-05: Frecuencia Cardíaca**

•      Obligatorio.

•      Entero.

•      Rango: 30-220 lpm.

•      Mensaje: "La frecuencia cardíaca debe estar entre 30 y 220 latidos por minuto."

**RN-CU07-06: Rangos Clínicos para Alertas**

•      PA fuera 90/60-140/90: alerta "Presión arterial fuera de rango normal."

•      Temperatura fuera 36.0-37.5: alerta "Temperatura fuera de rango normal."

•      FC fuera 60-100: alerta "Frecuencia cardíaca fuera de rango normal."

•      El sistema debe alertar visualmente pero permitir continuar con registro.

## CU-08: Consulta Médica

**RN-CU08-01: Diagnóstico**

•      Obligatorio para cerrar la consulta.

•      10-5000 caracteres.

•      CIE-10 opcional.

•      Mensaje: "El diagnóstico es obligatorio. Debe contener entre 10 y 5000 caracteres."

**RN-CU08-02: Registro de Consulta**

•      Motivo de consulta (obligatorio).

•      Hallazgos clínicos (obligatorio).

•      Diagnóstico (obligatorio para cierre).

•      Plan de tratamiento (obligatorio).

•      Mensaje si incompleto: "Debe completar todos los campos obligatorios para cerrar la consulta."

**RN-CU08-03: Receta Médica**

•      Medicamento (obligatorio).

•      Dosis (obligatorio).

•      Frecuencia (obligatorio).

•      Duración (obligatorio).

•      Indicaciones especiales (opcional).

•      Mensaje: "Todos los campos de la receta son obligatorios excepto indicaciones especiales."

## CU-09: Gestión de Laboratorio

**RN-CU09-01: Cobro**

•      Debe realizarse antes de la toma de muestras.

•      Métodos: Efectivo, tarjeta de crédito, tarjeta de débito.

•      Comprobante requerido.

•      Mensaje: "El pago debe estar completado antes de proceder con la toma de muestras."

**RN-CU09-02: Resultados**

•      Todos los exámenes solicitados deben tener resultados.

•      Campos: nombre examen, valor, unidad de medida, rango de referencia.

•      Alerta si está fuera de rango.

•      Validación de supervisor antes de publicación.

•      Mensaje: "Los resultados están fuera del rango de referencia normal. Requiere revisión."

## CU-10: Despacho de Medicamentos

**RN-CU10-01: Verificación de Receta**

•      Medicamento debe existir en catálogo.

•      Dosis coherente con medicamento.

•      Receta no debe exceder 7 días de antigüedad.

•      Mensaje: "La receta es inválida. Verifique que el medicamento exista, la dosis sea correcta y la receta no sea anterior a 7 días."

**RN-CU10-02: Cobro Integrado en Farmacia**

•      El cobro se realiza directamente en el mostrador de farmacia al momento del despacho (ver RN-GLOBAL-004 para métodos aceptados).

•      El comprobante incluye detalle de medicamentos despachados, cantidades, precios y total (ver RN-GLOBAL-005).

•      Mensaje de éxito: "Despacho registrado exitosamente. [X] medicamento(s) despachado(s). Total: Q[monto]."

**RN-CU10-03: Stock Mínimo**

•      Nivel mínimo configurable por medicamento.

•      Alerta automática al alcanzar nivel mínimo.

•      Mensaje: "El stock del medicamento [nombre] ha alcanzado el nivel mínimo. Se requiere reorden."

## CU-11: Agendamiento de Cita de Seguimiento

**RN-CU11-01: Tipo Seguimiento**

•      Obligatorio.

•      Opciones: Monitoreo de condición / Revisión de resultados.

•      Mensaje: "Debe seleccionar el tipo de seguimiento."

**RN-CU11-02: Fecha y Hora**

•      Obligatorio.

•      Debe ser fecha futura.

•      Debe coincidir con horarios disponibles del médico.

•      Mensaje: "Seleccione una fecha futura dentro de los horarios disponibles del médico."

**RN-CU11-03: Observaciones**

•      Obligatorio.

•      10-2000 caracteres.

•      Mensaje: "Las observaciones son obligatorias. Deben contener entre 10 y 2000 caracteres."

**RN-CU11-04: Contenido de Notificación**

•      Asunto: "Cita de Seguimiento Agendada - Hospital [Nombre]"

•      Cuerpo incluye: fecha, hora, tipo de seguimiento, médico, sucursal, observaciones.

•      Pie de correo estándar (ver RN-GLOBAL-006).

**RN-CU11-05: Contenido de Recordatorio**

•      Asunto: "Recordatorio: Su Cita de Seguimiento Mañana"

•      Se envía 1-2 días antes de la cita.

•      Cuerpo incluye: fecha, hora, tipo seguimiento, médico, sucursal.

•      No se envía si la cita fue cancelada.

•      Pie de correo estándar (ver RN-GLOBAL-006).

## CU-12: Configuración de Sedes y Especialidades

**RN-CU12-01: Campos de Asignación Sede-Especialidad**

•      Sede: Obligatorio. Dropdown con sedes activas. Mensaje: “Debe seleccionar una sede.”

•      Especialidad: Obligatorio. Dropdown con especialidades activas. Mensaje: “Debe seleccionar una especialidad.”

•      Índice único: no puede existir la misma combinación (Sede, Especialidad) más de una vez.

•      Mensaje duplicado: “Esta combinación de sede y especialidad ya existe en el sistema.”

## CU-13: Bitácora de Movimientos de Inventario

**RN-CU13-01: Campos del Movimiento de Inventario**

•      Medicamento: Obligatorio. Mensaje: “Debe seleccionar un medicamento.”

•      Sucursal: Obligatorio. Mensaje: “Debe seleccionar una sucursal.”

•      Tipo de Movimiento: Obligatorio. Opciones: Entrada por compra, Salida por ajuste, Transferencia entre sucursales, Ajuste por inventario físico.

•      Cantidad: Obligatorio. Entero positivo mayor a 0.

•      Motivo: Obligatorio. Entre 10 y 1000 caracteres.

**RN-CU13-02: Validación de Stock Suficiente**

•      Para movimientos de tipo Salida o Transferencia, la cantidad no puede exceder el stock actual.

•      Se utiliza control de concurrencia optimista (RowVersion) para prevenir condiciones de carrera.

•      Mensaje: “Stock insuficiente. El stock actual es [X] unidades.”

## CU-14: Gestión de Agenda Médica

**RN-CU14-01: Campos del Evento de Agenda**

•      Título: Obligatorio. Entre 5 y 200 caracteres.

•      Fecha de inicio: Obligatorio. Debe ser fecha futura o actual.

•      Fecha de fin: Obligatorio. Debe ser posterior a la fecha de inicio.

•      Tipo de evento: Obligatorio. Opciones: Bloqueo de disponibilidad, Evento personal, Capacitación, Vacaciones.

•      Descripción: Opcional. Máximo 2000 caracteres.

•      Color: Opcional. Código hexadecimal válido.

**RN-CU14-02: Campos de Tarea**

•      Título: Obligatorio. Entre 5 y 200 caracteres.

•      Prioridad: Obligatorio. Opciones: Alta, Media, Baja.

•      Fecha límite: Opcional. Si se ingresa, debe ser fecha futura.

•      Estado: Pendiente (default), En progreso, Completada.

## CU-15: Mantenimiento de Catálogos del Sistema

**RN-CU15-01: Validaciones Comunes por Catálogo**

•      Nombre: Obligatorio en todos los catálogos. Longitud máxima según catálogo.

•      Descripción: Obligatoria en Especialidades y Medicamentos, opcional en el resto.

•      Estado: Obligatorio. Valores: 1=Activo, 0=Inactivo. Default: Activo.

•      Nombre único: no pueden existir dos registros activos con el mismo nombre en el mismo catálogo.

•      Mensaje: “Ya existe un registro con el nombre [nombre] en este catálogo.”

# 3. Requerimientos No Funcionales Consolidados

## 3.1 Rendimiento

•      RNF-001: El correo de confirmación de cita debe enviarse en un máximo de 5 minutos (CU-00, CU-02, CU-04, CU-11).

•      RNF-002: El sistema de búsqueda de citas debe retornar resultados en máximo 3 segundos (CU-05).

•      RNF-003: El expediente del paciente debe cargar en menos de 2 segundos (CU-08).

•      RNF-004: El catálogo CIE-10 debe soportar autocompletado con respuesta menor a 500 ms (CU-08).

•      RNF-005: El portal web debe soportar al menos 100 usuarios concurrentes sin degradación (CU-00).

•      RNF-006: El tiempo de carga de la página principal no debe exceder 3 segundos (CU-00).

•      RNF-007: La notificación al médico de resultados de laboratorio debe enviarse en máximo 2 minutos (CU-09).

•      RNF-008: Los signos vitales deben sincronizarse con el expediente en máximo 2 segundos (CU-07).

•      RNF-009: El dispositivo POS debe responder en máximo 15 segundos por transacción (CU-06).

## 3.2 Seguridad

•      RNF-010: La comunicación con la pasarela de pago debe usar HTTPS con TLS 1.2 o superior (CU-04).

•      RNF-011: El sistema no debe almacenar datos sensibles de tarjeta - cumplimiento PCI DSS (CU-04).

•      RNF-012: El número de tarjeta debe mostrarse enmascarado (solo últimos 4 dígitos visibles) (CU-04).

•      RNF-013: El código CVV debe estar enmascarado visualmente (asteriscos) (CU-04).

•      RNF-014: Los datos sensibles del paciente (DPI, NIT) deben almacenarse encriptados en reposo (CU-02).

•      RNF-015: Las contraseñas temporales deben cumplir estándares de seguridad (mín 12 caracteres, combinación) (CU-01).

•      RNF-016: Se debe implementar idempotency key para evitar cobros duplicados (CU-04).

•      RNF-017: Los medicamentos controlados deben tener flujo de autorización adicional con auditoría (CU-10).

## 3.3 Disponibilidad y Resiliencia

•      RNF-018: La sesión de pago en línea debe expirar después de 10 minutos de inactividad (CU-04).

•      RNF-019: El temporizador de reserva temporal de cita debe ser configurable (default 5 min) (CU-03).

•      RNF-020: El scheduler de recordatorios debe ser resiliente a reinicios del sistema (CU-11).

•      RNF-021: La pantalla de recepción debe actualizarse automáticamente cuando cambie un estado de cita (CU-05).

•      RNF-022: El calendario de disponibilidad debe actualizar horarios en tiempo real (CU-03).

## 3.4 Integridad de Datos

•      RNF-023: Las operaciones CRUD de usuarios deben registrar log de auditoría inmutable (CU-01).

•      RNF-024: Los resultados de laboratorio publicados deben ser inmutables sin autorización de supervisor (CU-09).

•      RNF-025: El inventario de farmacia debe usar control de concurrencia optimista (CU-10).

•      RNF-026: El historial de consultas debe mantener versionamiento para auditoría (CU-08).

•      RNF-027: La unicidad de DPI y correo debe validarse a nivel de base de datos con índices únicos (CU-02).

•      RNF-028: Los documentos adjuntos deben pasar validación antivirus antes de almacenarse (CU-03).

## 3.5 Escalabilidad y Futuras Integraciones

•      RNF-029: El sistema debe soportar integración con dispositivos de medición IoT (CU-07).

•      RNF-030: El sistema debe soportar integración con analizadores de laboratorio LIS (CU-09).

•      RNF-031: El sistema debe implementar firma digital del médico para documentos clínicos (CU-08).

•      RNF-032: Se debe implementar cola de mensajes para envío asíncrono de notificaciones (CU-11).

•      RNF-033: Los comprobantes de pago deben poder reimprimirse sin límite (CU-06).