# Calculadora Cientifica con ANTLR4

## Informacion del proyecto

**Estudiante:** Johan Steven Galeano Gonzalez  
**Universidad:** Universidad Sergio Arboleda  
**Materia:** Lenguajes de Programacion y Traduccion  

## Descripcion

Este proyecto consiste en una calculadora cientifica desarrollada en Java utilizando ANTLR4.

El objetivo fue aplicar los conceptos vistos en clase para construir un pequeño lenguaje capaz de reconocer y evaluar expresiones matematicas, variables, funciones, comandos y graficas.

El proyecto se desarrollo siguiendo la guia de ANTLR, comenzando con una gramatica basica y agregando progresivamente nuevas funcionalidades.

## Objetivo

Se busca comprender el proceso que ocurre desde que el usuario escribe una expresion hasta que el programa la interpreta y ejecuta.

El flujo principal es:

```text
Entrada
   |
   v
Lexer
   |
   v
Tokens
   |
   v
Parser
   |
   v
Arbol sintactico
   |
   v
Visitor
   |
   +----> Resultado
   |
   +----> PlotWindow
```

- **Lexer:** reconoce los elementos de la entrada y los convierte en tokens.
- **Parser:** verifica como estan organizados los tokens segun la gramatica.
- **Arbol sintactico:** representa la estructura de la expresion.
- **Visitor:** recorre el arbol y ejecuta las operaciones.
- **PlotWindow:** recibe puntos y los representa graficamente.

## Archivos principales

### ScientificCalc.g4

Contiene la gramatica de la calculadora.

Define las reglas para reconocer:

- Numeros
- Variables
- Operadores
- Funciones
- Constantes
- Comandos
- Graficas

ANTLR utiliza esta gramatica para generar automaticamente el Lexer, el Parser y las clases necesarias para utilizar el patron Visitor.

### Main.java

Es el punto de entrada del programa.

Su funcionamiento es:

```text
System.in
   |
   v
CharStream
   |
   v
ScientificCalcLexer
   |
   v
CommonTokenStream
   |
   v
ScientificCalcParser
   |
   v
ParseTree
   |
   v
ScientificEvalVisitor
```

Su funcion principal es conectar las diferentes partes de ANTLR.

### ScientificEvalVisitor.java

Contiene la logica de la calculadora.

Aqui se realizan operaciones como:

```text
visitAddSub       -> suma y resta
visitMulDiv       -> multiplicacion y division
visitPower        -> potencias
visitAssign       -> guardar variables
visitId           -> recuperar variables
visitFunctionCall -> funciones cientificas
visitClear        -> borrar memoria
visitShowVars     -> mostrar variables
visitPlotExpr     -> graficar
```

Las variables se almacenan utilizando:

```java
Map<String, Double> memory = new HashMap<>();
```

### PlotWindow.java

Se encarga de mostrar las graficas utilizando Swing y `Graphics2D`.

El Visitor calcula diferentes valores de X y Y y los envia a `PlotWindow`.

```text
Funcion
   |
   v
Valores de X
   |
   v
Valores de Y
   |
   v
PlotWindow
   |
   v
Conversion a pixeles
   |
   v
Grafica
```

## Operaciones disponibles

La calculadora reconoce:

```text
+   suma
-   resta
*   multiplicacion
/   division
^   potencia
```

Ejemplos:

```text
2+3
10-5
4*8
20/4
2^8
(2+3)*4
```

Tambien soporta operadores unarios:

```text
-5
+10
```

## Variables y memoria

Se pueden crear y utilizar variables:

```text
a=10
b=5
a+b
```

Para mostrar las variables guardadas:

```text
vars
```

Para borrar la memoria:

```text
clear
```

Si una variable no existe, el programa muestra:

```text
Variable no definida: nombre
```

## Funciones cientificas

Funciones de un argumento:

```text
sin(x)
cos(x)
tan(x)
asin(x)
acos(x)
atan(x)
sqrt(x)
log(x)
ln(x)
abs(x)
exp(x)
floor(x)
ceil(x)
```

Ejemplos:

```text
sqrt(25)
sin(pi/2)
log(100)
abs(-5)
ceil(3.2)
```

## Funciones de dos argumentos

Se implementaron:

```text
pow(a,b)
max(a,b)
min(a,b)
```

Ejemplos:

```text
pow(2,8)
max(10,25)
min(10,25)
```

## Constantes

La calculadora reconoce:

```text
pi
e
```

Internamente se utilizan:

```java
Math.PI
Math.E
```

## Graficacion

Se implementaron tres formas de utilizar `plot`.

### Grafica normal

```text
plot(x^2,-10,10)
```

Formato:

```text
plot(funcion, xmin, xmax)
```

### Grafica con rango Y

```text
plot(x^2,-10,10,-5,100)
```

Formato:

```text
plot(funcion, xmin, xmax, ymin, ymax)
```

### Dos funciones

```text
plot(sin(x),cos(x),-6.28,6.28)
```

Formato:

```text
plot(funcion1, funcion2, xmin, xmax)
```

Para realizar una grafica, el Visitor calcula **800 valores de X** dentro del intervalo.

Para cada X se calcula su valor de Y.

```text
800 valores de X
       |
       v
Calcular Y
       |
       v
Guardar puntos
       |
       v
PlotWindow
       |
       v
Unir puntos con lineas
```

Se utilizan varios puntos para que la curva pueda verse de forma continua.

## Retos implementados

Ademas de la calculadora base se implementaron varios retos de la guia.

### Reto 1

Nuevas funciones:

```text
asin
acos
atan
floor
ceil
```

### Reto 2

Funciones con dos argumentos:

```text
pow
max
min
```

### Reto 3

Rango vertical personalizado:

```text
plot(funcion, xmin, xmax, ymin, ymax)
```

### Reto 4

Dos funciones en una misma grafica:

```text
plot(funcion1, funcion2, xmin, xmax)
```

## Pruebas

Se creo una carpeta `pruebas` para comprobar el funcionamiento de cada parte de la calculadora.

```text
pruebas/
|
|-- 01_aritmetica.txt
|-- 02_precedencia_parentesis.txt
|-- 03_variables_memoria.txt
|-- 04_potencias_unarios.txt
|-- 05_funciones_cientificas.txt
|-- 06_funciones_dos_argumentos.txt
|-- 07_constantes.txt
|-- 08_clear_vars.txt
|-- 09_division_cero_y_limites.txt
|-- 10_errores_sintaxis.txt
|-- 11_plot_normal.txt
|-- 12_plot_rango_y.txt
|-- 13_plot_multiple.txt
|-- 14_casos_adicionales.txt
`-- todas.txt
```

### Que se busca con las pruebas

Las pruebas permiten verificar:

- Operaciones basicas
- Precedencia y parentesis
- Variables y memoria
- Potencias
- Operadores unarios
- Funciones cientificas
- Funciones de dos argumentos
- Constantes
- Comandos `clear` y `vars`
- Graficacion
- Division por cero
- Variables no definidas
- Dominios matematicos invalidos
- Errores de sintaxis

Tambien se incluyen casos que deben producir errores o resultados especiales.

Por ejemplo:

```text
1/0
0/0
sqrt(-1)
log(-10)
asin(2)
```

Java puede producir valores como:

```text
Infinity
-Infinity
NaN
```

Tambien se prueban errores de sintaxis:

```text
2+
sqrt()
pow(2)
plot()
```

El objetivo es observar como responde el Parser cuando una entrada no cumple con la gramatica.

## Estructura del proyecto

```text
CalculadoraANTLR/
|
|-- Main.java
|-- PlotWindow.java
|-- ScientificCalc.g4
|-- ScientificEvalVisitor.java
|-- README.md
|-- ejemplos.txt
|
`-- pruebas/
    |-- 01_aritmetica.txt
    |-- 02_precedencia_parentesis.txt
    |-- 03_variables_memoria.txt
    |-- 04_potencias_unarios.txt
    |-- 05_funciones_cientificas.txt
    |-- 06_funciones_dos_argumentos.txt
    |-- 07_constantes.txt
    |-- 08_clear_vars.txt
    |-- 09_division_cero_y_limites.txt
    |-- 10_errores_sintaxis.txt
    |-- 11_plot_normal.txt
    |-- 12_plot_rango_y.txt
    |-- 13_plot_multiple.txt
    |-- 14_casos_adicionales.txt
    `-- todas.txt
```

## Requisitos

Para ejecutar el proyecto se necesita:

- Java JDK
- ANTLR4
- `antlr-4.13.2-complete.jar`
- PowerShell

## Generacion, compilacion y ejecucion

Entrar a la carpeta del proyecto:

```powershell
cd C:\Users\INFINIX\Documents\CalculadoraANTLR
```

Generar los archivos de ANTLR:

```powershell
antlr4 -no-listener -visitor ScientificCalc.g4
```

Esto genera archivos como:

```text
ScientificCalcLexer.java
ScientificCalcParser.java
ScientificCalcVisitor.java
ScientificCalcBaseVisitor.java
```

Compilar:

```powershell
javac -cp ".;C:\Users\INFINIX\Documents\ANTLR\antlr-4.13.2-complete.jar" *.java
```

Ejecutar:

```powershell
java -cp ".;C:\Users\INFINIX\Documents\ANTLR\antlr-4.13.2-complete.jar" Main
```

## Ejecutar pruebas

Para ejecutar una prueba:

```powershell
Get-Content .\pruebas\01_aritmetica.txt | java -cp ".;C:\Users\INFINIX\Documents\ANTLR\antlr-4.13.2-complete.jar" Main
```

Para ejecutar la prueba general:

```powershell
Get-Content .\pruebas\todas.txt | java -cp ".;C:\Users\INFINIX\Documents\ANTLR\antlr-4.13.2-complete.jar" Main
```

## Conclusion

Este proyecto permitio aplicar de forma practica los conceptos vistos en Lenguajes de Programacion y Traduccion.

Primero se definio el lenguaje mediante `ScientificCalc.g4`. ANTLR utiliza esta gramatica para generar el Lexer y el Parser.

El Lexer reconoce los elementos de la entrada, el Parser verifica su estructura y construye el arbol sintactico.

Luego `ScientificEvalVisitor` recorre el arbol y ejecuta las operaciones, funciones, variables y comandos correspondientes.

Para las graficas, el Visitor calcula los puntos y `PlotWindow` se encarga de representarlos.

El proceso completo puede resumirse como:

```text
Entrada
   |
   v
Lexer
   |
   v
Parser
   |
   v
Arbol sintactico
   |
   v
Visitor
   |
   +----> Resultado
   |
   +----> Memoria
   |
   +----> Graficas
```

Finalmente, se agregaron pruebas para comprobar tanto los casos correctos como los errores y casos limite de la calculadora.
