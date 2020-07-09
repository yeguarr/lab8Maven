package swing_package;

import commons.Collection;
import commons.Route;
import commons.User;
import program.MainClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.HashMap;
import java.util.Map;

public class MyComponent extends JComponent implements ActionListener {

    int WIDTH = 500;
    int HEIGHT = 500;
    double setX = WIDTH / 2;
    double setY = HEIGHT / 2;
    double dX = setX;
    double dY = setY;
    double scaleCount = 16.0;
    double scale = 2.0;
    int GRID_SIZE = 60;
    int GRID_NUMBER = GRID_SIZE / 4;
    boolean isDark = true;
    private Map<Route, VisualRoute> map = new HashMap<>();

    MyComponent() {
        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(listener);
        addComponentListener(new ResizeListener());

        Timer t = new Timer(50, this);
        t.start();
    }

    public void update() {
        for (User user : MainClient.collection.map.keySet()) {
            for (Route route : MainClient.collection.map.get(user)) {
                if (!map.containsKey(route)) {
                    if (route.getFrom() != null) {
                        double x1 = route.getFrom().getX();
                        double y1 = route.getFrom().getY();
                        double r1 = (double) route.getFrom().getZ() / 10;
                        double x3 = route.getTo().getX();
                        double y3 = route.getTo().getY();
                        double r2 = (double) route.getTo().getZ() / 10;
                        double x2 = route.getCoordinates().getX();
                        double y2 = route.getCoordinates().getY();
                        Point2D first = new Point2D.Double();
                        first.setLocation(x1, y1);
                        Point2D second = new Point2D.Double();
                        second.setLocation(x2, y2);
                        Point2D third = new Point2D.Double();
                        third.setLocation(x3, y3);
                        Point2D radius = new Point2D.Double();
                        radius.setLocation(r1, r2);
                        map.put(route, new VisualRoute(new VisualRoute.PointPack(first, second, third, radius), Color.getHSBColor(((float) Math.abs(user.login.hashCode())) / Integer.MAX_VALUE, 1.f, isDark ? 0.7f : 1.f)));
                    } else {
                        double x1 = route.getTo().getX();
                        double y1 = route.getTo().getY();
                        double r1 = 0.0;
                        double x3 = route.getTo().getX();
                        double y3 = route.getTo().getY();
                        double r2 = (double) route.getTo().getZ() / 10;
                        double x2 = route.getTo().getX();
                        double y2 = route.getTo().getY();
                        Point2D first = new Point2D.Double();
                        first.setLocation(x1, y1);
                        Point2D second = new Point2D.Double();
                        second.setLocation(x2, y2);
                        Point2D third = new Point2D.Double();
                        third.setLocation(x3, y3);
                        Point2D radius = new Point2D.Double();
                        radius.setLocation(r1, r2);
                        map.put(route, new VisualRoute(new VisualRoute.PointPack(first, second, third, radius), Color.getHSBColor(((float) Math.abs(user.login.hashCode())) / Integer.MAX_VALUE, 1.f, isDark ? 0.7f : 1.f)));
                    }
                }
            }
        }
    }

    public static String fmt(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color background = isDark ? Color.BLACK : Color.WHITE;
        g2d.setPaint(background);
        Rectangle2D rect = new Rectangle2D.Float();
        rect.setFrame(0, 0, WIDTH, HEIGHT);
        g2d.fill(rect);

        BasicStroke b1 = new BasicStroke(1);
        BasicStroke b2 = new BasicStroke(2);
        Color gray = isDark ? new Color(60, 60, 60) : new Color(192, 192, 192);

        for (int i = -GRID_NUMBER * ((int) (dY / scale) / GRID_NUMBER); i * scale <= (HEIGHT - GRID_NUMBER * (dY / GRID_NUMBER)); i += GRID_NUMBER) {
            if (i % GRID_SIZE == 0) {
                g2d.setStroke(b2);
                g2d.setPaint(gray);
                Shape l = new Line2D.Double(0, i * scale + dY, WIDTH, i * scale + dY);
                g2d.draw(l);
            } else {
                g2d.setStroke(b1);
                g2d.setPaint(gray);
                Shape l = new Line2D.Double(0, i * scale + dY, WIDTH, i * scale + dY);
                g2d.draw(l);
            }
        }
        for (int i = -GRID_NUMBER * ((int) (dX / scale) / GRID_NUMBER); i * scale <= (WIDTH - GRID_NUMBER * (dX / GRID_NUMBER)); i += GRID_NUMBER) {
            if (i % GRID_SIZE == 0) {
                g2d.setStroke(b2);
                g2d.setPaint(gray);
                Shape l = new Line2D.Double(i * scale + dX, 0, i * scale + dX, HEIGHT);
                g2d.draw(l);
            } else {
                g2d.setStroke(b1);
                g2d.setPaint(gray);
                Shape l = new Line2D.Double(i * scale + dX, 0, i * scale + dX, HEIGHT);
                g2d.draw(l);
            }
        }
        g2d.setStroke(b2);
        g2d.setPaint(isDark ? Color.GRAY : Color.BLACK);
        Font f = new Font("Arial", Font.BOLD, 15);
        g2d.setFont(f);
        g2d.drawString("0", (float) (dX + 5), (float) (dY - 5));
        Shape axX = new Line2D.Double(Math.max(Math.min(dX, WIDTH - 5), 5), 0, Math.max(Math.min(dX, WIDTH - 5), 5), HEIGHT);
        Shape axY = new Line2D.Double(0, Math.max(Math.min(dY, HEIGHT - 5), 5), WIDTH, Math.max(Math.min(dY, HEIGHT - 5), 5));
        g2d.draw(axX);
        g2d.draw(axY);
        for (int i = -GRID_NUMBER * ((int) (dY / scale) / GRID_NUMBER); i * scale <= (HEIGHT - GRID_NUMBER * (dY / GRID_NUMBER)); i += GRID_NUMBER) {
            if (i % GRID_SIZE == 0 && i != 0) {
                String text = fmt(-i * scaleCount / GRID_SIZE);
                FontMetrics metrics = g2d.getFontMetrics(f);
                int strw = metrics.stringWidth(text);
                float rx = (float) Math.max(Math.min((dX + 5), WIDTH - strw - 10), 10);
                float ry = (float) (i * scale + dY) - (float) metrics.getHeight() / 2 + metrics.getAscent();
                g2d.setFont(f);
                g2d.drawString(text, rx, ry);
            }
        }
        for (int i = -GRID_NUMBER * ((int) (dX / scale) / GRID_NUMBER); i * scale <= (WIDTH - GRID_NUMBER * (dX / GRID_NUMBER)); i += GRID_NUMBER) {
            if (i % GRID_SIZE == 0 && i != 0) {
                drawCenteredString(g2d, fmt(i * scaleCount / GRID_SIZE), (float) (i * scale + dX), (float) Math.max(Math.min((dY - 10), HEIGHT - 15), 15), f);
            }
        }
        drawShapes(g2d);
    }

    private void drawShapes(Graphics2D g2d) {
        Font f = new Font("Arial", Font.BOLD, 10);
        for (Route r : map.keySet()) {
            VisualRoute vr = map.get(r);
            vr.updateComponents(dX, dY, scale, scaleCount, GRID_SIZE);
            g2d.setPaint(vr.c);
            g2d.draw(vr.q);
            g2d.fill(vr.el1);
            g2d.fill(vr.el2);
        }
    }

    public void drawCenteredString(Graphics2D g, String text, float x, float y, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        float rx = x - (float) metrics.stringWidth(text) / 2;
        float ry = y - (float) metrics.getHeight() / 2 + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, rx, ry);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean is = false;
        for (Route r : map.keySet()) {
            if (map.get(r).update()) {
                is = true;
            }
        }
        if (is)
            repaint();
    }

    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            HEIGHT = e.getComponent().getHeight();
            WIDTH = e.getComponent().getWidth();
            setX = WIDTH / 2;
            setY = HEIGHT / 2;
            dX = setX;
            dY = setY;
            repaint();
        }
    }

    class MyMouseListener extends MouseAdapter implements MouseWheelListener {
        int oldX = 0;
        int oldY = 0;

        public void mousePressed(MouseEvent e) {
            oldX = e.getX();
            oldY = e.getY();
        }

        public void mouseDragged(MouseEvent e) {
            dX = setX + e.getX() - oldX;
            dY = setY + e.getY() - oldY;
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            setX = dX;
            setY = dY;
            repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            dX += e.getPreciseWheelRotation() * (e.getX() - setX) / 50;
            dY += e.getPreciseWheelRotation() * (e.getY() - setY) / 50;
            setX = dX;
            setY = dY;
            scale += -e.getPreciseWheelRotation() * scale / 50;
            if (scale < 1.0) {
                scale = 2.0;
                scaleCount *= 2;
            } else if (scale > 2.0) {
                scale = 1;
                scaleCount /= 2;
            }
            repaint();
        }
    }
}