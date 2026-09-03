
# 1. Introducción

**Descripción**

El presente documento describe los pasos que el Usuario Externo deberá seguir para visualizar los servicios ofrecidos dentro del portal web del Sistema Informático Hospitalario (HIS) y acceder al proceso de agendamiento de citas médicas.

**Objetivo**

Brindar un portal web informativo que permita a los usuarios externos visualizar los servicios ofrecidos por el Hospital, consultar especialidades disponibles y acceder al proceso de agendamiento de citas.

# 2. Definición Caso de Uso

## 2.1 Actores

•      Usuario Externo (Paciente)

•      Sistema informático

## 2.2 Precondiciones

•      El sistema debe estar disponible.

•      El Usuario Externo debe contar con un dispositivo con acceso a internet.

•      Catalogos Precargados en cache

## 2.3 Flujo Normal Básico

1.    El Usuario Externo ingresa al portal web a través del enlace del sitio.

2.    El sistema muestra la página principal con la información del portal web (servicios, especialidades, ubicaciones, horarios de atención).

3.    El Usuario Externo selecciona la opción "Agendar Cita".

4.    El sistema muestra un diálogo modal con título "Verificar Registro" y el campo de ingreso de DPI con el mensaje: "Ingrese su número de DPI para verificar si está registrado en el sistema." El campo muestra un contador de dígitos en tiempo real (X/13 dígitos).

5.    El Usuario Externo ingresa su número de DPI.

6.    El sistema valida el formato del DPI ingresado. [[RN-GLOBAL-001](Reglas_de_Negocio_Consolidadas.docx#RN_GLOBAL_01)] [FA01]

7.    El Usuario Externo selecciona el botón "Verificar DPI". El sistema muestra un indicador de carga con el texto "Verificando...". [FA02]

8.    El sistema confirma que el Usuario Externo está registrado con rol de paciente y lo redirige a la pantalla de inicio de sesión del portal, donde deberá autenticarse con su nombre de usuario y contraseña para acceder al agendamiento de citas. [[RN-CU00-01](Reglas_de_Negocio_Consolidadas.docx#RN_CU00_01)] [FA03] [FA04]

9.    El sistema redirige al Usuario Externo a la pantalla de inicio de sesión del portal (“Iniciar Sesión”). El Usuario Externo ingresa su nombre de usuario y contraseña y selecciona el botón “Iniciar Sesión”. El sistema valida las credenciales. [FA06] [FA07] [FA08] [FA09]

10. Una vez autenticado exitosamente, el sistema redirige al Usuario Externo al dashboard del portal donde puede acceder al formulario de agendamiento de cita. [CU-03] [CU-04]

11. (Paso eliminado: pertenece a CU-04 Pago en Línea.)

12. Fin del caso de uso.

## 2.4 Flujos Alternos

**FA01 Error en la validación del DPI**

1.    El Usuario Externo ingresa su DPI con formato inválido.

2.    El sistema muestra el mensaje de error específico según la validación que falló (ver RN-GLOBAL-001).

3.    El Usuario Externo corrige el dato.

4.    Se continúa en el paso 6 del flujo normal básico.

**FA02 El Usuario Externo cancela la operación**

5.    El sistema regresa al paso 2 del flujo normal básico.

**FA03 El Usuario Externo no está registrado en el sistema**

1.    El sistema no encuentra un registro asociado al DPI ingresado.

2.    El sistema muestra el mensaje: "No se encontró un registro asociado a este DPI. Será redirigido al formulario de registro."

3.    El sistema redirige al Usuario Externo al formulario de registro. [CU-02]

4.    Una vez completado el registro, se continúa en el paso 8 del flujo normal básico.

**FA04 El DPI pertenece a un usuario interno del sistema**

6.    El sistema detecta que el DPI ingresado pertenece a un usuario registrado en el sistema interno (no paciente).

7.    El sistema muestra el mensaje: "Este DPI pertenece a un usuario del sistema interno. Por favor, contacte a recepción."

8.    El Usuario Externo permanece en el diálogo de verificación.

**FA05 Error de conexión con el servidor**

9.    El sistema no puede conectarse con el servidor para verificar el DPI.

10. El sistema muestra el mensaje: "No se pudo conectar con el servidor. Intente de nuevo más tarde."

11. El Usuario Externo puede reintentar la operación.

1.    **FA06 Credenciales incorrectas**

1.    El Usuario Externo ingresa un nombre de usuario o contraseña incorrectos.

2.    El sistema muestra el mensaje: “Usuario o contraseña incorrectos. Intentos restantes: [N].” donde N es el número de intentos restantes (máximo 5 intentos). [RN-CU00-02]

3.    El Usuario Externo corrige sus credenciales y reintenta el inicio de sesión. Se continúa en el paso 9 del flujo normal básico.

2.    **FA07 Cuenta bloqueada por intentos fallidos**

12. El Usuario Externo alcanza el máximo de 5 intentos fallidos de inicio de sesión.

13. El sistema muestra el mensaje: “Cuenta bloqueada temporalmente. Intente de nuevo en 15 minutos.” [RN-CU00-03]

14. Los campos de usuario y contraseña se deshabilitan junto con el botón de inicio de sesión durante el período de bloqueo.

15. El Usuario Externo debe esperar 15 minutos antes de reintentar.

3.    **FA08 Error de conexión durante el inicio de sesión**

16. El sistema no puede conectarse con el servidor para validar las credenciales.

17. El sistema muestra el mensaje: “No se pudo conectar con el servidor. Intente de nuevo más tarde.”

18. El Usuario Externo puede reintentar el inicio de sesión.

4.    **FA09 Inicio de sesión con rol no autorizado**

19. El Usuario se autentica exitosamente pero su rol no es “Paciente” (por ejemplo, es personal del hospital).

20. El sistema muestra el mensaje: “Este acceso es exclusivo para pacientes. Si es personal del hospital, use el panel administrativo.”

21. La pantalla de login muestra un enlace “Acceso Panel Administrativo” para que el usuario pueda dirigirse al panel interno.

## 2.5 Postcondiciones

•      El Usuario Externo visualizó la información del portal web.

•      Si el Usuario Externo agendó cita, esta queda registrada en el sistema con estado "Pendiente de pago".

Si el Usuario Externo se registró durante el proceso, su cuenta queda activa en el sistema.