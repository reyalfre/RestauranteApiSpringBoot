1 Práctica: API de Gestión de Reservas para un Restaurante con Spring Boot
Objetivo:


Desarrollar una API RESTful utilizando Spring Boot para manejar el sistema de reservas de un restaurante.


Implementar de la manera que se quiera alguna estructura de datos en la que simular la persistencia de los datos.


Requisitos Funcionales:


Gestión de Reservas:


• Implementar endpoints para crear, actualizar, cancelar (eliminar) y listar reservas.


• Cada reserva está relacionada con:

    -Identificador numérico único: generado al insertarse.


    -Un número de mesa. Tiene que ser válido según la gestión de mesas.


    -Fecha y hora de la reserva (por simplicidad, vamos a considerar sólo días -sin mes- y sólo horas):


            -Día


            -Hora de inicio


o Datos del cliente:

    -Nombre
    -Primer apellido
    -Teléfono de contacto.
o Número de comensales.


• Hay que asegurarse de que cada reserva contiene la información solicitada. Todos los datos son obligatorios.


Por simplicidad, se considera que las reservas duran 1h, por lo que no puede existir 2 reservas en la misma mesa para
el mismo día y misma hora.


Gestión de Mesas:


• Implementar endpoints para añadir, modificar, eliminar y listar mesas en el restaurante, las cuales serán usadas
para realizar las reservas.


• Cada mesa tendrá un identificador numérico único y una capacidad de personas (mínimo 1, máximo 8).


Informes:


Implementar endpoints para consultar:


• Las reservas existentes del día actual de la petición.


• Las mesas libres en una día y franja de horas dadas. Es decir, mesas que no tengan ninguna reserva asociada para
ese día y franja horaria. La información a recibir es:


    -Día
    -Hora de inicio
    -Hora de fin
Reglas de Negocio


Control de Disponibilidad:


Por simplicidad, se considera que las reservas duran 1h, por lo que no puede existir 2 reservas en la misma mesa para
el mismo día y misma hora.


Asegurar que no se puedan hacer dos reservas para la misma mesa y día/hora. Si una mesa ya está reservada, la API
debe rechazar nuevas reservas que se solapen con las existentes con esa premisa.


Control de comensales


No se puede permitir reservas que tengan un número de comensales mayor a la capacidad de la mesa especificada.
Tampoco se deberá permitir reservas de comensales con valores inválidos (menores a 1).


Control de mesas:


No se podrán realizar reservas en mesas que no estén cargadas en el sistema.


El sistema tiene un aforo limitado a 30 personas. Por lo tanto, no deben existir un número mesas que permitan
almacenar más de 30 comensales en total.


PARA TODOS AQUELLOS CASOS QUE EL API IMPIDA LA CREACIÓN DEL REGISTRO, SE DEBE OFRECER UN MENSAJE
DE RESPUESTA AL CLIENTE DEL API CON LA EXPLICACIÓN.


Detalle de peticiones y respuestas:


Para todos los métodos que no sean obtener información (GET), los datos devueltos del API tendrán que tener la
siguiente estructura:


    -Éxito: boolean.
    -Mensaje de error: si el éxito es false, un mensaje descriptivo con el error relacionado. Puede ser porque haya datos inválidos, o por algún conflicto.


Gestión de Reservas


• POST /reservas: Crear una nueva reserva.


    -Devolver 201 si se crea con éxito.
    -400 si los datos son inválidos (faltan datos obligatorios o no son válidos).
    -409 si hay conflicto de disponibilidad con otra reserva previa.


• GET /reservas: Listar todas las reservas.


    -Devolver 200 siempre.
    -Devolver 204 si no hay contenido.
• GET /reservas/{id}: Obtener detalles de una reserva específica.


    -Devolver 200 si se encuentra.
    -Devolver 404 si no existe ninguna reserva con ese id.


• PUT /reservas/{id}: Actualizar una reserva existente. Con los nuevos datos se modificará entera.


    -Devolver 200 si es exitoso.
    -400 si los datos son inválidos
    -404 si no se encuentra con ese id.
    -409 si hay conflicto en horas.
• DELETE /reservas/{id}: Cancelar una reserva.


    -Devolver 204 si se cancela con éxito.
    -404 si no se encuentra por ese id.
Gestión de Mesas (4 puntos):


POST /mesas: Añadir una nueva mesa.

    -Devolver 201 si se añade con éxito.
    -400 si los datos son inválidos.


• GET /mesas: Listar todas las mesas.


    -Devolver 200 si hay datos.
    -Devolver 204 si no hay contenido.


• GET /mesas/{id}: Ver detalles de una mesa específica.


    -Devolver 200 si se encuentra
    -404 si no.
• PUT /mesas/{id}: Modificar una mesa. Envía los datos y se modificará entera.


    -Devolver 200 si es exitoso
    -400 si los datos son inválidos
    -404 si no se encuentra por ese id.


• DELETE /mesas/{id}: Eliminar una mesa.


    -Devolver 204 si se elimina con éxito
    -404 si no se encuentra.


Consultas Especiales (2 puntos):


• GET /reservas/hoy: Obtener las reservas del día actual.


    -Devolver 200 si hay datos.
    -Devolver 204 si no hay contenido.


• GET /mesas/disponibles: Consultar mesas disponibles para un día y franja de horas (hora inicio, hora fin). Es
decir, mesas que existan para las cuales no haya reservas en el día y horas solicitadas.


    -Devolver 200 si se devuelven datos.
    -Devolver 204 si no hay contenido.


REQUISITOS DE CALIFICACIÓN


OBLIGATORIO INFORMAR DE LOS ERRORES DE VALIDACIÓN DE DATOS O DE REGLAS FUNCIONALES EN EL LOG DE LA
APLICACIÓN. Puede ser fichero o traza por pantalla. De no ofrecerse se restará 1 punto.


OBLIGATORIO OFRECER SWAGGER-UI CON EL API. De no ofrecerse se restará 1 punto.


OBLIGATORIO QUE LAS CLASES ESTÉN CON MAYÚSCULAS Y ATRIBUTOS Y MÉTODOS EN MINÚSCULAS. De no realizarse se
restará 1 punto.