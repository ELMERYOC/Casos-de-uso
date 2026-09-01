# 1. Introducción

**Descripción**

El presente documento describe los pasos que el Usuario Externo deberá seguir para registrarse en el sistema informático hospitalario, proporcionando sus datos personales, de contacto y de seguro médico.

**Objetivo**

Permitir a los pacientes nuevos registrarse en el sistema del hospital para habilitar el proceso de agendamiento de citas médicas.

# 2. Definición Caso de Uso

## 2.1 Actores

•      Usuario Externo (Paciente)

•      Sistema informático

## 2.2 Precondiciones

•      El sistema debe estar disponible.

•      El Usuario Externo debe haber accedido al portal web del hospital.

## 2.3 Flujo Normal Básico

1.    El sistema muestra en pantalla el formulario de registro con los campos requeridos.

2.    El Usuario Externo ingresa su nombre completo. [[RN-CU02-01](Reglas_de_Negocio_Consolidadas.docx#RN_CU02_01)]

3.    El Usuario Externo ingresa su número de DPI. [[RN-GLOBAL-001](Reglas_de_Negocio_Consolidadas.docx#RN_GLOBAL_01)]

4.    El Usuario Externo ingresa su número de NIT. [[RN-GLOBAL-002](Reglas_de_Negocio_Consolidadas.docx#RN_GLOBAL_02)]

5.    El Usuario Externo ingresa su número de teléfono. [[RN-CU02-02](Reglas_de_Negocio_Consolidadas.docx#RN_CU02_02)]

6.    El Usuario Externo ingresa su número de afiliado del seguro médico (opcional). [[RN-CU02-03]](Reglas_de_Negocio_Consolidadas.docx#RN_CU02_03)

7.    El Usuario Externo ingresa su correo electrónico. [[RN-CU02-04](Reglas_de_Negocio_Consolidadas.docx#RN_CU02_04)]

8.    El Usuario Externo ingresa un nombre de usuario (entre 8 y 9 caracteres). [RN-[CU02-05](Reglas_de_Negocio_Consolidadas.docx#RN_CU02_05)]

9.    El Usuario Externo ingresa una contraseña (mínimo 12 caracteres). [[RN-CU02-06]](Reglas_de_Negocio_Consolidadas.docx#RN_CU02_06)

10. El Usuario Externo selecciona el botón "Registrarse". [FA01]

11. El sistema valida la información ingresada. [FA02] [FA03] [FA04]

12. El sistema registra al Usuario Externo en la base de datos.

13. El sistema muestra el mensaje: "¡Registro exitoso! Su cuenta ha sido creada. Ahora puede iniciar sesión con sus credenciales."

14. El sistema envía un correo electrónico de bienvenida al Usuario Externo con el asunto: "Bienvenido al Sistema de Citas - Hospital [Nombre]" y el cuerpo: "Estimado(a) [Nombre], su registro ha sido completado exitosamente. Ya puede agendar sus citas médicas a través de nuestro portal."

15. El sistema redirige al Usuario Externo a la pantalla de inicio de sesión para que ingrese con sus nuevas credenciales. [CU-00, paso 8]

16. Fin del caso de uso.

## 2.4 Flujos Alternos

**FA01 El Usuario Externo regresa al portal**

1.    El Usuario Externo selecciona el enlace "Volver al portal".

2.    El sistema descarta los datos del formulario sin mostrar confirmación.

3.    El Usuario Externo confirma la cancelación.

4.    El sistema descarta los datos y regresa al portal web. [CU-00]

5.    Fin del caso de uso.

**FA02 DPI ya registrado en el sistema**

1.    El sistema detecta que el DPI ingresado ya existe en la base de datos.

2.    El sistema muestra el mensaje: "Ya existe una cuenta registrada con este número de DPI. Si ya tiene cuenta, inicie sesión."

3.    El Usuario Externo debe dirigirse a la pantalla de inicio de sesión para autenticarse con su cuenta existente.

4.    Fin del caso de uso.

**FA03 Correo electrónico ya registrado**

1.    El sistema detecta que el correo electrónico ya está asociado a otra cuenta.

2.    El sistema muestra el mensaje: "Ya existe una cuenta registrada con este correo electrónico."

3.    El Usuario Externo corrige el correo electrónico.

4.    Se continúa en el paso 8 del flujo normal básico.

**FA04 Validación de campos fallida**

1.    El sistema detecta que uno o más campos no cumplen las reglas de negocio.

2.    El sistema muestra los mensajes de error específicos de cada campo (ver RN-CU02-01 a RN-CU02-06 y RN-GLOBAL-001, RN-GLOBAL-002).

3.    El sistema resalta los campos con error en color rojo.

4.    El Usuario Externo corrige los campos señalados.

5.    Se continúa en el paso 8 del flujo normal básico.

## 2.5 Postcondiciones

•      El Usuario Externo queda registrado en el sistema con estado activo.

•      Los datos del paciente quedan disponibles para el proceso de agendamiento de citas.

•      El Usuario Externo recibe un correo de confirmación de registro.