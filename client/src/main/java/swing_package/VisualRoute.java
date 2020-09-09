package swing_package;

import commons.Route;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.Objects;

public class VisualRoute {
    public Ellipse2D el1 = new Ellipse2D.Double();
    public Ellipse2D el2 = new Ellipse2D.Double();
    public QuadCurve2D q = new QuadCurve2D.Double();
    public Color c;
    public Route route;
    PointPack old;
    PointPack current;
    PointPack desired;
    double a = 0;
    boolean dying = false;

    public VisualRoute(PointPack old, PointPack desired, Route route, Color c) {
        this.route = route;
        this.c = c;
        this.old = old;
        this.current = new PointPack(old);
        this.desired = desired;
        el1.setFrameFromCenter(current.first.getX(), current.first.getY(), current.first.getX() + current.radius.getX(), current.first.getY() - current.radius.getX());
        el2.setFrameFromCenter(current.third.getX(), current.third.getY(), current.third.getX() + current.radius.getY(), current.third.getY() - current.radius.getY());
        q.setCurve(current.first, current.second, current.third);
    }


    public VisualRoute(Route route, Color c) {
        this.route = route;
        this.c = c;
        Point2D first = new Point2D.Double();
        first.setLocation(0, 0);
        Point2D second = new Point2D.Double();
        second.setLocation(0, 0);
        Point2D third = new Point2D.Double();
        third.setLocation(0, 0);
        Point2D radius = new Point2D.Double();
        radius.setLocation(0, 0);
        this.old = new PointPack(first, second, third, radius);
        this.current = new PointPack(old);
        this.desired = PointPack.createPointPack(route);
        el1.setFrameFromCenter(current.first.getX(), current.first.getY(), current.first.getX() + current.radius.getX(), current.first.getY() - current.radius.getX());
        el2.setFrameFromCenter(current.third.getX(), current.third.getY(), current.third.getX() + current.radius.getY(), current.third.getY() - current.radius.getY());
        q.setCurve(current.first, current.second, current.third);
    }

    public Route getRoute() {
        return route;
    }

    public void kill() {
        Point2D first = new Point2D.Double();
        first.setLocation(0, 0);
        Point2D second = new Point2D.Double();
        second.setLocation(0, 0);
        Point2D third = new Point2D.Double();
        third.setLocation(0, 0);
        Point2D radius = new Point2D.Double();
        radius.setLocation(0, 0);
        dying = true;
        a = 0.0;
        this.old = new PointPack(this.current);
        this.desired = new PointPack(first, second, third, radius);
    }

    public void setDesired(Route route) {
        a = 0.0;
        this.route = route;
        this.old = new PointPack(this.current);
        this.desired = PointPack.createPointPack(route);
    }

    public boolean update() {
        if (a < 1.0) {
            a += 0.02;
            double b = (Math.tanh(6 * (a - 0.5)) + 0.995055) / 1.99011;
            current.first.setLocation((1 - b) * old.first.getX() + b * desired.first.getX(), (1 - b) * old.first.getY() + b * desired.first.getY());
            current.second.setLocation((1 - b) * old.second.getX() + b * desired.second.getX(), (1 - b) * old.second.getY() + b * desired.second.getY());
            current.third.setLocation((1 - b) * old.third.getX() + b * desired.third.getX(), (1 - b) * old.third.getY() + b * desired.third.getY());
            current.radius.setLocation((1 - b) * old.radius.getX() + b * desired.radius.getX(), (1 - b) * old.radius.getY() + b * desired.radius.getY());
            return true;
        }
        return false;
    }

    public boolean isTouching(double X, double Y) {
        if (((current.first.getX() - X) * (current.first.getX() - X) + (current.first.getY() - Y) * (current.first.getY() - Y) - current.radius.getX() * current.radius.getX() < 0.0))
            return true;
        else
            return (((current.third.getX() - X) * (current.third.getX() - X) + (current.third.getY() - Y) * (current.third.getY() - Y) - current.radius.getY() * current.radius.getY() < 0.0));
    }

    public void updateComponents(double dX, double dY, double scale, double scaleCount, int grid) {
        double x1 = grid * current.first.getX() * scale / scaleCount + dX;
        double y1 = -grid * current.first.getY() * scale / scaleCount + dY;
        double r1 = grid * current.radius.getX() * scale / scaleCount;

        double x2 = grid * current.second.getX() * scale / scaleCount + dX;
        double y2 = -grid * current.second.getY() * scale / scaleCount + dY;

        double x3 = grid * current.third.getX() * scale / scaleCount + dX;
        double y3 = -grid * current.third.getY() * scale / scaleCount + dY;
        double r2 = grid * current.radius.getY() * scale / scaleCount;

        el1.setFrameFromCenter(x1, y1, x1 + r1, y1 - r1);
        el2.setFrameFromCenter(x3, y3, x3 + r2, y3 - r2);
        q.setCurve(x1, y1, x2, y2, x3, y3);
    }

    static class PointPack {
        Point2D first;
        Point2D second;
        Point2D third;
        Point2D radius;

        public PointPack(PointPack pp) {
            this.first = (Point2D) pp.first.clone();
            this.second = (Point2D) pp.second.clone();
            this.third = (Point2D) pp.third.clone();
            this.radius = (Point2D) pp.radius.clone();
        }

        public PointPack(Point2D first, Point2D second, Point2D third, Point2D radius) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.radius = radius;
        }

        public static PointPack createPointPack(Route route) {
            if (route.getFrom() != null) {
                double x1 = route.getFrom().getX();
                double y1 = route.getFrom().getY();
                double r1 = Math.log(Math.abs((double) route.getFrom().getZ()) + 1);
                double x3 = route.getTo().getX();
                double y3 = route.getTo().getY();
                double r2 = Math.log(Math.abs((double) route.getTo().getZ()) + 1);
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
                return new VisualRoute.PointPack(first, second, third, radius);
            } else {
                double x1 = route.getTo().getX();
                double y1 = route.getTo().getY();
                double r1 = 0.0;
                double x3 = route.getTo().getX();
                double y3 = route.getTo().getY();
                double r2 = Math.log(Math.abs((double) route.getTo().getZ()) + 1);
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
                return new PointPack(first, second, third, radius);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PointPack pointPack = (PointPack) o;
            return first.equals(pointPack.first) &&
                    second.equals(pointPack.second) &&
                    third.equals(pointPack.third) &&
                    Objects.equals(radius, pointPack.radius);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second, third, radius);
        }
    }

}

