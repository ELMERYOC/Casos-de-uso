
# 1. Introducción

**Descripción**

El presente documento describe los pasos que el personal con permisos de administración deberá seguir para la gestión y mantenimiento de cuentas de usuarios internos del sistema informático hospitalario, incluyendo búsqueda, creación, actualización y eliminación de cuentas. El módulo “User” del menú lateral ofrece dos puntos de entrada independientes: “Listar Usuarios” y “Crear Usuario”.

**Objetivo**

Administrar las cuentas de usuarios internos del sistema informático para un control eficiente de accesos, roles y estados correspondientes.

# 2. Definición Caso de Uso

## 2.1 Actores

•  Usuario Interno (Administrador)

•  Sistema informático

## 2.2 Precondiciones

•       El sistema debe estar disponible. [[RN-GLOBAL-003](Reglas_de_Negocio_Consolidadas.docx#RN_CU00_03)]

•       El Usuario Interno debe haber iniciado sesión con permisos de administración. [[RN-GLOBAL-007](Reglas_de_Negocio_Consolidadas.docx#RN_CU00_07)]

·         Catalogos deben de estar previamente cargado en cache.

## 2.3 Flujo Normal Básico

1.    El Usuario Interno accede al módulo “Usuarios” en el menú lateral del sistema.

2.    El sistema despliega las opciones del módulo: “Listar Usuarios” y “Crear Usuario”. [[FA01](#FA01)]

3.    El Usuario Interno selecciona “Listar Usuarios”.

4.    El sistema muestra la pantalla “Listado de Usuarios” con un selector desplegable “Filtrar por campo” (opciones: ID, Nombre, Correo Electrónico, Rol, Nombre de Usuario, DPI), un campo de texto con placeholder “Buscar…” y un botón de búsqueda (🔍). Debajo se muestra la tabla de usuarios con paginación.

5.    El Usuario Interno selecciona el campo de filtro deseado e ingresa el criterio de búsqueda. [[RN-CU01-01]](Reglas_de_Negocio_Consolidadas.docx#RN_CU00_01)

6.    El Usuario Interno presiona el botón de búsqueda (🔍). [[FA02](#FA02)]

7.    El sistema ejecuta la búsqueda y muestra los resultados en formato de tabla paginada con las columnas:

ID,

Nombre,

Correo Electrónico,

Rol, Nombre de Usuario,

Estado y Acciones.

La paginación muestra “Elementos Por página” (configurable: 10, 25, 50) y el conteo de registros. [[RN-CU01-02]](Reglas_de_Negocio_Consolidadas.docx#RN_CU00_02) [[FA03](#FA03)]

8.    El Usuario Interno visualiza la información y puede ejecutar las siguientes acciones desde el menú de acciones (⋮) de cada registro: Editar [[FA04](#FA04)], Eliminar [[FA05](#FA05)].

9.    Fin del caso de uso.

## 2.4 Flujos Alternos

**FA01** **– Crear nuevo usuario (entrada directa desde menú)**

1.    El Usuario Interno selecciona “Crear Usuario” desde el menú lateral del módulo “User”.

2.    El sistema muestra la pantalla “Crear Usuario” con el formulario de creación que contiene los campos:

Nombre Completo [[RN-CU01-03](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_03)],

Correo Electrónico [[RN-CU01-04](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_03)],

Nombre de Usuario [[RN-CU01-05](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_05)],

Contraseña [[RN-CU01-06](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_06)],

Documento de Identificación (opcional) [[RN-CU01-07](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_07)],

Número de Teléfono (opcional) [[RN-CU01-08](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_08)],

Rol [[RN-CU01-09](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_09)],

NIT (opcional) [[RN-CU01-11](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_11)],

Número de Seguro (opcional) [[RN-CU01-12](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_12)],

Sucursal (opcional) [[RN-CU01-13](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_13)],

Especialidad – solo para médicos – (opcional) [[RN-CU01-14](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_14)] y

Estado [[RN-CU01-10](Reglas_de_Negocio_Consolidadas.docx#RN_CU01_10)].

3.    El Usuario Interno ingresa los datos del nuevo usuario en los campos correspondientes.

4.    El Usuario Interno selecciona el botón “Crear”. [FA06] [FA07]

5.    El sistema valida todos los campos del formulario.

6.    El sistema crea el usuario y muestra el mensaje: “Usuario creado correctamente.”

7.    Fin del caso de uso.

**FA02** **– Limpiar búsqueda**

1.    El Usuario Interno no ingresa criterio de búsqueda o desea reiniciar los filtros.

2.    El sistema muestra la tabla con todos los usuarios registrados sin filtro aplicado.

3.    Se continúa en el paso 7 del flujo normal básico.

**FA03** **– No se encontró información**

1.    El sistema no encontró información que cumpliera con los filtros de búsqueda.

2.    El sistema muestra la tabla vacía con el mensaje en color rojo: “No se encontraron datos usuarios.”

3.    El Usuario Interno modifica los filtros de búsqueda.

4.    Se continúa en el paso 5 del flujo normal básico.

**FA04** **– Editar usuario**

1.    El Usuario Interno selecciona “Editar”  en el menú de acciones (⋮) del usuario que desea modificar.

2.    El sistema navega a la pantalla “Editar Usuario” y muestra el formulario con los valores actuales precargados en los campos: Nombre Completo, Correo Electrónico, Nombre de Usuario, Nueva Contraseña (opcional), Documento de Identificación, Número de Teléfono, Rol, NIT, Número de Seguro, Sucursal (opcional), Especialidad (opcional) y Estado.

3.    El Usuario Interno modifica los campos necesarios.

4.    El Usuario Interno selecciona el botón “Actualizar”. [FA06] [FA07]

5.    El sistema valida los campos modificados.

6.    El sistema muestra el mensaje: “Usuario actualizado correctamente.”

7.    Fin del caso de uso.

**FA05** **– Eliminar usuario**

1.    El Usuario Interno selecciona “Eliminar” (texto en color naranja) en el menú de acciones (⋮) del usuario que desea eliminar.

2.    El sistema muestra el diálogo modal “Confirmar eliminación” con un ícono de advertencia  y el mensaje: “¿Está seguro que desea eliminar el usuario “[nombre de usuario]”? Esta acción no se puede deshacer.” Los botones disponibles son “Cancelar” (texto azul) y “Eliminar” (botón rojo). [FA07]

3.    El Usuario Interno selecciona el botón “Eliminar”.

4.    El sistema elimina el usuario y muestra el mensaje: “El usuario [nombre de usuario] ha sido eliminado correctamente.”

5.    El sistema actualiza la tabla de usuarios automáticamente.

6.    Fin del caso de uso.

**FA06 – Validación de guardado fallida**

1.    El sistema detecta que uno o más campos no cumplen las reglas de negocio (ver RN-CU01-03 a RN-CU01-14).

2.    El sistema muestra los mensajes de error específicos de cada campo que falló.

3.    El sistema resalta los campos con error en color rojo.

4.    El Usuario Interno corrige los campos señalados.

5.    Se continúa en el paso de “Guardar/Crear/Actualizar” del flujo correspondiente.

**FA07 – Cancelar operación**

1.    El Usuario Interno selecciona el botón “Cancelar” en cualquier formulario (creación, edición o diálogo de confirmación de eliminación).

2.    Si es formulario de creación o edición: el sistema descarta los datos ingresados y redirige al Usuario Interno a la pantalla “Listado de Usuarios”.

3.    Si es diálogo de eliminación: el sistema cierra el diálogo modal y el usuario permanece en la pantalla “Listado de Usuarios” sin cambios.

4.    Fin del caso de uso.

## 2.5 Postcondiciones

1.    Los cambios en usuarios (creación, actualización, eliminación) quedan registrados en el sistema.

2.    El sistema registra un log de auditoría con la acción realizada, el usuario que la ejecutó y la fecha/hora.