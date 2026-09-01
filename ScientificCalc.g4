// Nombre de la gramatica, aqui ANTLR usa este nombre para generar clases 
grammar ScientificCalc;
// Regla principal del programa, stat+ significa una o mas instrucciones, EOF pues es end of line
prog
    : stat+ EOF
    ;
// Aqui estan las distintas instrucciones que escribe el usuario
stat
    : expr NEWLINE // Expresion seguida de enter
        # printExpr
    | ID '=' expr NEWLINE // Asignacion de una expresion a una variable 
        # assign
    | 'clear' NEWLINE // Borra variables guardadas
        # clear
    | 'vars' NEWLINE // Muestra las variables guardadas
        # showVars
    | 'plot' '(' expr ',' expr ',' expr ')' NEWLINE // Grafica funciones con xmin y xmax
        # plotExpr
    | 'plot' '(' expr ',' expr ',' expr ',' expr ',' expr ')' NEWLINE
        # plotRangeExpr
    | 'plot' '(' expr ',' expr ',' expr ',' expr ')' NEWLINE // Grafica dos funciones en la misma ventana
        # plotMultiExpr
    | NEWLINE
        # blank
    ;
// Define las expresiones matematicas
expr
    : <assoc=right> expr '^' expr // Potencia, derecha a izquierda
        # power
    | op=('+'|'-') expr // Operador unario
        # unary
    | expr op=('*'|'/') expr // Multiplicacion o division
        # mulDiv
    | expr op=('+'|'-') expr // Suma o resta 
        # addSub
    | function '(' expr ')' // Funcion matematica de un argumento, tipo sqrt
        # functionCall
    | function2 '(' expr ',' expr ')' // Funcion matematica de dos argumentos
        # function2Call
    | constant // Constantes como pi y e
        # constantExpr
    | NUMBER
        # number
    | ID
        # id
    | '(' expr ')'
        # parens
    ;
// Funciones que reciben un solo argumento
function
    : 'sin'
    | 'cos'
    | 'tan'
    | 'asin'
    | 'acos'
    | 'atan'
    | 'sqrt'
    | 'log'
    | 'ln'
    | 'abs'
    | 'exp'
    | 'floor'
    | 'ceil'
    ;
// Funciones que reciben dos argumentos
function2
    : 'pow'
    | 'max'
    | 'min'
    ;
// Constantes relacionadas que se reconocen directamente
constant
    : 'pi'
    | 'e'
    ;
// Tokens de los operadores que pasan al lexer
MUL : '*';
DIV : '/';
ADD : '+';
SUB : '-';
POW : '^';
// Reconoce numeros enteros o decimales 
NUMBER
    : [0-9]+ ('.' [0-9]+)?
    ;
// Reconoce nombres de variables 
ID
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;
// Esto es cuando el usuario presiona enter
NEWLINE
    : '\r'? '\n'
    ;
// Reconoce espacios y tabulaciones, pero los ignora
WS
    : [ \t]+ -> skip
    ;
