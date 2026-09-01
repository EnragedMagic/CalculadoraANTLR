// Aqui estan las clases principales de ANTLR

import org.antlr.v4.runtime.*;
// Aqui importa las herramientas para trabajar con el arbol sintactico
import org.antlr.v4.runtime.tree.*;

public class Main {
// Aqui empieza el metodo principal
    public static void main(String[] args) throws Exception {
    // Lee todo lo que el usuario escribe por consola, system in representa la entrada del teclado y char convierte esa entrada en una secuencia de caracteres
        // Que antlr procesa
        CharStream input =
                CharStreams.fromStream(System.in);
    // El Lexer recibe los caracteres de entrada (Analizador lexico, lee y clasifica en tokens)
        ScientificCalcLexer lexer =
                new ScientificCalcLexer(input);
    // Aqui se guardan los tokens que produjo el lexer, despues lo utilizara el parser
        CommonTokenStream tokens =
                new CommonTokenStream(lexer);
    // Aqui el parser recibe los tokens, y verifica que tengan una estructura valida, segun las reglas escritas
        ScientificCalcParser parser =
                new ScientificCalcParser(tokens);
    // Comienza a analizar desde la regla prog, de la gramatica, el resultado es el arbol sintactico
        ParseTree tree =
                parser.prog();
    // Se crea el visitor, este recorre el arbol y realiza operaciones reales
        ScientificEvalVisitor visitor =
                new ScientificEvalVisitor();
    // Se recorre el arbol sintactico, dependiendo de los nodos encontradors ANTLR llamara metodos
        visitor.visit(tree);
    }
}
