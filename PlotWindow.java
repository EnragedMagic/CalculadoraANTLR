import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlotWindow extends JPanel {

    private List<Double> xs;

    private List<Double> ys;
    private List<Double> ys2;

    private Double fixedYmin;
    private Double fixedYmax;

    public PlotWindow(
            List<Double> xs,
            List<Double> ys) {

        this.xs = xs;
        this.ys = ys;

        createWindow();
    }

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

    public PlotWindow(
            List<Double> xs,
            List<Double> ys,
            List<Double> ys2) {

        this.xs = xs;
        this.ys = ys;
        this.ys2 = ys2;

        createWindow();
    }

    private void createWindow() {

        JFrame frame =
                new JFrame(
                        "Scientific Calculator"
                );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setSize(800, 600);

        frame.add(this);

        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =
                (Graphics2D) g;

        if (xs.size() < 2) {
            return;
        }

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

        if (fixedYmin != null &&
                fixedYmax != null) {

            ymin = fixedYmin;
            ymax = fixedYmax;

        } else {

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

        drawFunction(
                g2,
                xs,
                ys,
                xmin,
                xmax,
                ymin,
                ymax
        );

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

    private void drawFunction(
            Graphics2D g2,
            List<Double> xValues,
            List<Double> yValues,
            double xmin,
            double xmax,
            double ymin,
            double ymax) {

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

            int px1 =
                    (int) (
                            (x1 - xmin)
                            / (xmax - xmin)
                            * getWidth()
                    );

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
