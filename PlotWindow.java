// Importa Swing, que usamos para crear la ventana grafica
import javax.swing.*;
// Importa herramientas graficas, graphics y graphics 2d
import java.awt.*;
// Este importa list para almacenar valores en x y y
import java.util.List;
// Aqui esta el componente de swing sobre el que se dibuja 
public class PlotWindow extends JPanel {
    // Lista de valores del eje x
    private List<Double> xs;
    // Valores de la primera funcion
    private List<Double> ys;
    // Valores de la segunda funcion, por si se grafican dos funciones
    private List<Double> ys2;
    // Esto limita verticales opcionales, por ejemplo cuando se especifica min y max
    private Double fixedYmin;
    private Double fixedYmax;
    // Ese constructor es para una grafica normal, que guarda los valores recibidos
    public PlotWindow(
            List<Double> xs,
            List<Double> ys) {

        this.xs = xs;
        this.ys = ys;

        createWindow();
    }
    // Este constructor es para una grafica con limites verticales
    public PlotWindow(
            List<Double> xs,
            List<Double> ys,
            double ymin,
            double ymax) {

        this.xs = xs;
        this.ys = ys;

        this.fixedYmin = ymin;
        this.fixedYmax = ymax;

        createWindow();
    }
    // Este es para graficar dos funciones
    public PlotWindow(
            List<Double> xs,
            List<Double> ys,
            List<Double> ys2) {

        this.xs = xs;
        this.ys = ys;
        this.ys2 = ys2;

        createWindow();
    }
    // Aqui se crea la ventana donde se mostrara la grafica
    private void createWindow() {

        JFrame frame =
                new JFrame(
                        "Scientific Calculator"
                );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setSize(800, 600);
        // Agrega jpanel a la ventana
        frame.add(this);
        // La hace visible
        frame.setVisible(true);
    }
    // Swing llama a este metodo para volver a dibujar o dibujar el panel
    @Override // Metodo solo puede usarse dentro de la clase y tambien por clases heredadas
    protected void paintComponent(Graphics g) {
    // Limpiar el panel antes de dibujar
        super.paintComponent(g);
    // Convierte graficos 
        Graphics2D g2 =
                (Graphics2D) g;
    // Si hay menos de dos puntos no se dibuja una linea
        if (xs.size() < 2) {
            return;
        }
    // Minimos y maximos, se busca el menor valor de x y mayor valor de x
        double xmin =
                xs.stream()
                        .mapToDouble(Double::doubleValue)
                        .min()
                        .orElse(-1);

        double xmax =
                xs.stream()
                        .mapToDouble(Double::doubleValue)
                        .max()
                        .orElse(1);

        double ymin;
        double ymax;
// Si el usuario especifico limites verticales se usan esos valores
        if (fixedYmin != null &&
                fixedYmax != null) {

            ymin = fixedYmin;
            ymax = fixedYmax;

        } else {
// Si no, se calcula automaticamente el menor o mayor Y
            ymin =
                    ys.stream()
                            .mapToDouble(Double::doubleValue)
                            .min()
                            .orElse(-1);

            ymax =
                    ys.stream()
                            .mapToDouble(Double::doubleValue)
                            .max()
                            .orElse(1);

            if (ys2 != null) {
// Esto es para segundas funciones, para tener en cuenta sus valores
                double ymin2 =
                        ys2.stream()
                                .mapToDouble(Double::doubleValue)
                                .min()
                                .orElse(-1);

                double ymax2 =
                        ys2.stream()
                                .mapToDouble(Double::doubleValue)
                                .max()
                                .orElse(1);

                ymin = Math.min(ymin, ymin2);
                ymax = Math.max(ymax, ymax2);
            }
        }
// Se dibuja la primera funcion
        drawFunction(
                g2,
                xs,
                ys,
                xmin,
                xmax,
                ymin,
                ymax
        );
// Segunda funcion
        if (ys2 != null) {

            drawFunction(
                    g2,
                    xs,
                    ys2,
                    xmin,
                    xmax,
                    ymin,
                    ymax
            );
        }
    }
// Este metodo recibe los puntos y los convierte a posiciones
    private void drawFunction(
            Graphics2D g2,
            List<Double> xValues,
            List<Double> yValues,
            double xmin,
            double xmax,
            double ymin,
            double ymax) {
    // Recorre todos los puntos de la funcion 
        for (int i = 1;
             i < xValues.size();
             i++) {

            double x1 =
                    xValues.get(i - 1);

            double y1 =
                    yValues.get(i - 1);

            double x2 =
                    xValues.get(i);

            double y2 =
                    yValues.get(i);
// Convierte x1 a horizontal en pixeles
            int px1 =
                    (int) (
                            (x1 - xmin)
                            / (xmax - xmin)
                            * getWidth()
                    );
// Lo mismo pero vertical
            int py1 =
                    getHeight()
                    - (int) (
                            (y1 - ymin)
                            / (ymax - ymin)
                            * getHeight()
                    );

            int px2 =
                    (int) (
                            (x2 - xmin)
                            / (xmax - xmin)
                            * getWidth()
                    );

            int py2 =
                    getHeight()
                    - (int) (
                            (y2 - ymin)
                            / (ymax - ymin)
                            * getHeight()
                    );

            g2.drawLine(
                    px1,
                    py1,
                    px2,
                    py2
            );
        }
    }
}
