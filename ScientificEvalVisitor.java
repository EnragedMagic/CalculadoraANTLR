// HashMap y Map es para guardar variables
import java.util.HashMap;
import java.util.Map;
// ArrayList y List se usan para guardar los puntos de las graficas
import java.util.ArrayList;
import java.util.List;
// Esta clase hereda del visitor que antlr genera, double e sque los metodos devuelven numeros decimales 
public class ScientificEvalVisitor
        extends ScientificCalcBaseVisitor<Double> {
// Memoria de las variables 
    Map<String, Double> memory = new HashMap<>();
// Convierte un number reconocido por ANTLR de txt a numero double
    @Override
    public Double visitNumber(
            ScientificCalcParser.NumberContext ctx) {

        return Double.parseDouble(
                ctx.NUMBER().getText()
        );
    }
// Esto es para ejecutar suma o resta
    @Override
    public Double visitAddSub(
            ScientificCalcParser.AddSubContext ctx) {

        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));

        if (ctx.op.getType() == ScientificCalcParser.ADD) {
            return left + right;
        }

        return left - right;
    }
// Ejecuta multiplicacion o division
    @Override
    public Double visitMulDiv(
            ScientificCalcParser.MulDivContext ctx) {

        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));

        if (ctx.op.getType() == ScientificCalcParser.MUL) {
            return left * right;
        }

        if (right == 0) {
            System.err.println("Error: division por cero.");
        }

        return left / right;
    }
// Evalua lo que esta dentro de parentesis
    @Override
    public Double visitParens(
            ScientificCalcParser.ParensContext ctx) {

        return visit(ctx.expr());
    }
// Evalua una expresion y muestra su resultado
    @Override
    public Double visitPrintExpr(
            ScientificCalcParser.PrintExprContext ctx) {

        double value = visit(ctx.expr());

        System.out.println(value);

        return value;
    }
// Guardar variables 
    @Override
    public Double visitAssign(
            ScientificCalcParser.AssignContext ctx) {

        String id = ctx.ID().getText();

        double value = visit(ctx.expr());

        memory.put(id, value);

        return value;
    }
// Recupera el valor de una variable, comprueba si existe y si no, muestra error
    @Override
    public Double visitId(
            ScientificCalcParser.IdContext ctx) {

        String id = ctx.ID().getText();

        if (memory.containsKey(id)) {
            return memory.get(id);
        }

        System.err.println(
                "Variable no definida: " + id
        );

        return 0.0;
    }
// Calcular potencia
    @Override
    public Double visitPower(
            ScientificCalcParser.PowerContext ctx) {

        double base = visit(ctx.expr(0));
        double exponent = visit(ctx.expr(1));

        return Math.pow(base, exponent);
    }
// Maneja signos positivos y negativos 
    @Override
    public Double visitUnary(
            ScientificCalcParser.UnaryContext ctx) {
// - cambia signo
        double value = visit(ctx.expr());

        if (ctx.op.getText().equals("-")) {
            return -value;
        }

        return value;
    }
// Ejecuta funciones de un argumento
    @Override
    public Double visitFunctionCall(
            ScientificCalcParser.FunctionCallContext ctx) {

        String function =
                ctx.function().getText();

        double value =
                visit(ctx.expr());
// Decide que operacion matematica ejecutar 
        switch (function) {

            case "sin":
                return Math.sin(value);

            case "cos":
                return Math.cos(value);

            case "tan":
                return Math.tan(value);

            case "asin":
                return Math.asin(value);

            case "acos":
                return Math.acos(value);

            case "atan":
                return Math.atan(value);

            case "sqrt":
                return Math.sqrt(value);

            case "log":
                return Math.log10(value);

            case "ln":
                return Math.log(value);

            case "abs":
                return Math.abs(value);

            case "exp":
                return Math.exp(value);

            case "floor":
                return Math.floor(value);

            case "ceil":
                return Math.ceil(value);

            default:
                throw new RuntimeException(
                        "Funcion desconocida: " + function
                );
        }
    }
// Ejecuta funciones que reciben dos argumentos
    @Override
    public Double visitFunction2Call(
            ScientificCalcParser.Function2CallContext ctx) {

        String function =
                ctx.function2().getText();

        double value1 = visit(ctx.expr(0)); // 1 
        double value2 = visit(ctx.expr(1)); // 2

        switch (function) {

            case "pow":
                return Math.pow(value1, value2);

            case "max":
                return Math.max(value1, value2);

            case "min":
                return Math.min(value1, value2);

            default:
                throw new RuntimeException(
                        "Funcion desconocida: " + function
                );
        }
    }
// Convierte las constantes del lenguaje, en las constantes matematicas de java
    @Override
    public Double visitConstantExpr(
            ScientificCalcParser.ConstantExprContext ctx) {

        String constant =
                ctx.constant().getText();

        if (constant.equals("pi")) {
            return Math.PI;
        }

        if (constant.equals("e")) {
            return Math.E;
        }

        return 0.0;
    }
// Borra todas las variables guardadas
    @Override
    public Double visitClear(
            ScientificCalcParser.ClearContext ctx) {

        memory.clear();

        System.out.println(
                "Memoria eliminada."
        );

        return 0.0;
    }
// Muestra las variables guardadas
    @Override
    public Double visitShowVars(
            ScientificCalcParser.ShowVarsContext ctx) {

        if (memory.isEmpty()) {

            System.out.println(
                    "No hay variables definidas."
            );

            return 0.0;
        }
// Recorre todas las variables 
        for (Map.Entry<String, Double> entry :
                memory.entrySet()) {

            System.out.println(
                    entry.getKey()
                    + " = "
                    + entry.getValue()
            );
        }

        return 0.0;
    }
// Este es para graficar una explesion usando xmin y xmax
    @Override
    public Double visitPlotExpr(
            ScientificCalcParser.PlotExprContext ctx) {
// 2do parametro del plot
        double xmin = visit(ctx.expr(1));
// 2er parametro del plot
        double xmax = visit(ctx.expr(2));
// Cantidad de puntos que se calculan
        int samples = 800;
// Listas donde se guardan los puntos
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
// Guarda el valor anterior de x si existe
        Double oldX = memory.get("x");

        for (int i = 0; i < samples; i++) {
// Calcula 800 valores diferentes de x
            double x =
                    xmin
                    + i * (xmax - xmin)
                    / (samples - 1);
// Guarda temporalmente x
            memory.put("x", x);
// Evalua la funcion usando ese valor de x 
            double y = visit(ctx.expr(0));
// Solo guarda resultados validos
            if (Double.isFinite(y)) {
                xs.add(x);
                ys.add(y);
            }
        }
// Devuelve x al valor que tenia ntes 
        restoreX(oldX);
//Envia los puntos a plot
        new PlotWindow(xs, ys);

        return 0.0;
    }
// Esto es igual que el plot normal, pero permite especificar ymin y ymax
    @Override
    public Double visitPlotRangeExpr(
            ScientificCalcParser.PlotRangeExprContext ctx) {

        double xmin = visit(ctx.expr(1));
        double xmax = visit(ctx.expr(2));

        double ymin = visit(ctx.expr(3));
        double ymax = visit(ctx.expr(4));

        int samples = 800;

        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();

        Double oldX = memory.get("x");

        for (int i = 0; i < samples; i++) {

            double x =
                    xmin
                    + i * (xmax - xmin)
                    / (samples - 1);

            memory.put("x", x);

            double y = visit(ctx.expr(0));

            if (Double.isFinite(y)) {
                xs.add(x);
                ys.add(y);
            }
        }

        restoreX(oldX);
// Los envia a plotwindow
        new PlotWindow(
                xs,
                ys,
                ymin,
                ymax
        );

        return 0.0;
    }
// Grafica dos funciones al mismo tiempo
    @Override
    public Double visitPlotMultiExpr(
            ScientificCalcParser.PlotMultiExprContext ctx) {
// En este plot, aqui estan ambas funciones
        double xmin = visit(ctx.expr(2));
        double xmax = visit(ctx.expr(3));

        int samples = 800;

        List<Double> xs = new ArrayList<>();
// Valores de Y de cada funcion
        List<Double> ys1 = new ArrayList<>();
        List<Double> ys2 = new ArrayList<>();

        Double oldX = memory.get("x");

        for (int i = 0; i < samples; i++) {

            double x =
                    xmin
                    + i * (xmax - xmin)
                    / (samples - 1);

            memory.put("x", x);
// Evalua ambas fuciones con el mismo x
            double y1 = visit(ctx.expr(0));
            double y2 = visit(ctx.expr(1));

            xs.add(x);

            ys1.add(y1);
            ys2.add(y2);
        }

        restoreX(oldX);
// Envia dos funciones a plotwindow
        new PlotWindow(
                xs,
                ys1,
                ys2
        );

        return 0.0;
    }
// Restaura la variable x despues de graficar
    private void restoreX(Double oldX) {
// Si x no existia antes del plot, la elimina y si ya existia devuelve su valor anterior
        if (oldX == null) {
            memory.remove("x");
        } else {
            memory.put("x", oldX);
        }
    }
}
