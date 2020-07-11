package swing_package;

import commons.*;
import program.MainClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Iterator;
import java.util.LinkedList;

public class MyComponent extends JComponent implements ActionListener {

    int WIDTH = 500;
    int HEIGHT = 500;
    double setX = (double) WIDTH / 2;
    double setY = (double) HEIGHT / 2;
    double dX = setX;
    double dY = setY;
    double scaleCount = 16.0;
    double scale = 2.0;
    int GRID_SIZE = 60;
    int GRID_NUMBER = GRID_SIZE / 4;
    boolean isDark = false;
    private java.util.List<VisualRoute> visualRouteList = new LinkedList<>();

    MyComponent() {
        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(listener);
        addComponentListener(new ResizeListener());

        Timer t = new Timer(50, this);
        t.start();
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
        drawShapes(g2d);
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
    }

    private void drawShapes(Graphics2D g2d) {
        Font f = new Font("Arial", Font.BOLD, 10);
        for (VisualRoute vr : visualRouteList) {
            vr.updateComponents(dX, dY, scale, scaleCount, GRID_SIZE);
            g2d.setPaint(vr.c);
            g2d.draw(vr.q);
            float[] dist = {0.0f, 1.0f};
            Color[] colors = {vr.c, Color.BLACK};
            RadialGradientPaint grad = new RadialGradientPaint(new Point2D.Float((float) vr.el1.getCenterX(), (float)vr.el1.getCenterY()), (float)Math.abs(vr.el1.getWidth())+10f,dist,colors);
            g2d.setPaint(grad);
            g2d.fill(vr.el1);
            grad = new RadialGradientPaint(new Point2D.Float((float) vr.el2.getCenterX(), (float)vr.el2.getCenterY()), (float)Math.abs(vr.el2.getWidth())+10f,dist,colors);
            g2d.setPaint(grad);
            g2d.fill(vr.el2);
            g2d.setPaint(isDark ? Color.white : Color.BLACK);
            g2d.setFont(f);
            FontMetrics metrics = g2d.getFontMetrics(f);
            if (vr.el2.getBounds().width >= vr.el1.getBounds().width)
                g2d.drawString("  "+vr.getRoute().getName(),(float)(vr.el2.getMaxX()),(float)(vr.el2.getCenterY() - metrics.getHeight()/2 + metrics.getAscent()));
            else
                g2d.drawString("  "+vr.getRoute().getName(),(float)(vr.el1.getMaxX()),(float)(vr.el1.getCenterY() - metrics.getHeight()/2 + metrics.getAscent()));

            //drawCenteredString(g2d, vr.getRoute().getName(),(float) (vr.el1.getCenterX()/4+vr.el2.getCenterX()/4+vr.q.getCtrlX()/2),(float) (vr.el1.getCenterY()/4+vr.el2.getCenterY()/4+vr.q.getCtrlY()/2), f);
        }
    }

    public void drawCenteredString(Graphics2D g, String text, float x, float y, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        float rx = x - (float) metrics.stringWidth(text) / 2;
        float ry = y - (float) metrics.getHeight() / 2 + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, rx, ry);
    }

    double rand() {
        return Math.random()*2-1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (User user : MainClient.collection.map.keySet()) {
            for (Route route : MainClient.collection.map.get(user)) {
                if (visualRouteList.stream().map(VisualRoute::getRoute).map(Route::getId).noneMatch(integer -> integer.equals(route.getId())))
                    visualRouteList.add(new VisualRoute(route,Color.getHSBColor(((float) Math.abs(Utils.sha1(user.login).hashCode())) / Integer.MAX_VALUE, 1.f, isDark ? 0.7f : 1.f)));
            }
        }
        boolean isNeedsToBeRepaint = false;
        for (Iterator<VisualRoute> iterator = visualRouteList.iterator(); iterator.hasNext(); ) {
            VisualRoute vr = iterator.next();
            if (!vr.dying) {
                boolean contains = false;
                myLabel:
                for (User user : MainClient.collection.map.keySet()) {
                    if (MainClient.collection.map.get(user).contains(vr.getRoute())) {
                        contains = true;
                        break;
                    } else {
                        for (Route route : MainClient.collection.map.get(user)) {
                            if (route.getId().equals(vr.getRoute().getId())) {
                                contains = true;
                                vr.setDesired(route);
                                break myLabel;
                            }
                        }
                    }
                }
                if (!contains) {
                    vr.kill();
                }
            }
            boolean updated = vr.update();
            if (!updated && vr.dying) {
                iterator.remove();
                isNeedsToBeRepaint = true;
            } else if (updated)
                isNeedsToBeRepaint = true;
        }
        if (isNeedsToBeRepaint)
            repaint();
    }

    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            setX += (double)e.getComponent().getWidth()/2 - (double) WIDTH/2;
            setY += (double)e.getComponent().getHeight()/2 - (double) HEIGHT/2;
            HEIGHT = e.getComponent().getHeight();
            WIDTH = e.getComponent().getWidth();
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
            VisualRoute vr = visualRouteList.stream().filter(visualRoute -> visualRoute.isTouching((e.getX()-dX)/scale*scaleCount/GRID_SIZE,-(e.getY()-dY)/scale*scaleCount/GRID_SIZE)).reduce((first, second) -> second).orElse(null);
            if (vr != null) {
                UpdateWindow o = new UpdateWindow();
                o.display(vr.route);
            }
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
                dX -= e.getPreciseWheelRotation() * (e.getX() - setX) / 100;
                dY -= e.getPreciseWheelRotation() * (e.getY() - setY) / 100;
                setX = dX;
                setY = dY;
                scale = 2.0;
                scaleCount *= 2;
            } else if (scale > 2.0) {
                dX -= e.getPreciseWheelRotation() * (e.getX() - setX) / 50;
                dY -= e.getPreciseWheelRotation() * (e.getY() - setY) / 50;
                setX = dX;
                setY = dY;
                scale = 1;
                scaleCount /= 2;
            }
            repaint();
        }
    }
}