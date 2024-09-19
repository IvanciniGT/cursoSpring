#language:es

  # Este fichero contiene una definición de requisitos del sistema
  # Pero los vamos a definir como ejemplos de uso del sistema

  # Y a su vez, este fichero son las pruebas que haremos al sistema
  # Lo que quiero decir, es que este fichero es el que voy a ejecutar!!!!
  # No un fichero de JUNIT... No un fichero JAVA
  # Este fichero, que vamos a escribir en ESPAÑOL

  Característica: Controlador Rest del Servicio de Animalitos V1

    Escenario: Recuperación de un animalito no existente

      Dado      Que tengo un servicio de animalitos en el que no existe el animalito con id 123
      Cuando    Hago una petición REST a "/api/v1/animalitos/123" por método "GET"
      Entonces  Recibo un código de respuesta "NO ENCONTRADO"

    Esquema del escenario: Recuperación de un animalito existente

      Dado      Que tengo un animalito en el servicio de animalito con id <id>
      Y         el animalito tiene por "nombre": "<nombre>"
      Y         el animalito tiene por "tipo": "<tipo>"
      Y         el animalito ha nacido hoy
      Cuando    Hago una petición REST a "/api/v1/animalitos/" por método "GET"
      Y         Pero en la subruta <id>
      Entonces  Recibo un código de respuesta "ENCONTRADO"
      Y         Se me entrega un JSON
      Y         En el json viene el campo "id" con valor <id>
      Y         En el json viene el campo "nombre" con valor "<nombre>"
      Y         En el json viene el campo "tipo" con valor "<tipo>"

      Ejemplos:
        | id    | nombre        | tipo      |
        | 123   | Firulais      | perro     |
        | 142   | Pipo          | gato      |














#    Escenario: Alta de un animalito
#
#      Dado      Que tengo un objeto JSON
#      Y         con "nombre": "Firulais"
#      Y         con "tipo": "perro"
#      Y         con "fechaNacimiento" ahora
#      Cuando    Hago una petición REST a "/api/v1/animalitos" por método "POST"
#      Y         Mando en el cuerpo el objeto JSON anterior
#      Entonces  Recibo un código de respuesta "CREADO"
#      Y         Se me entrega un JSON
#      Y         En el json viene el campo "id" con valor 123
#      Y         En el json viene el campo "nombre" con valor "Firulais"
#      Y         En el json viene el campo "tipo" con valor "perro"



