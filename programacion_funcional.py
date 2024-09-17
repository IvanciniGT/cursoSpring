

def saluda():
    print("Hola mundo")

miFuncion = saluda

miFuncion()

# La pregunta evidente es PARA QUE VALE ESTA MIERDA !
# El tema no es lo que es la programación funcional... que es una gilipollez.
# El tema es lo que puedo hacer una vez que el lenguaje me permite hacer esto.... y aquí es donde explota la cabeza y se abre todo un mundo nuevo de posibilidades.
# Puedo empezar a:
# - Crear funciones que acepten funciones como parámetros
# - Crear funciones que devuelvan funciones

def generador_de_saludos(nombre):
    return "Hola " + nombre

def imprimir_saludo(funcion_generadora_de_saludos, nombre):
    print(funcion_generadora_de_saludos(nombre))
    # El problema es que en ocasiones cuando hago una función, parte de la lógica me es desconocida... es variable
    ## Y me la pueden pasar como argumento (parte de la lógica de mi función nueva)... mediante una función!

imprimir_saludo(generador_de_saludos, "Juan")
