import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.y == that.y) {
            if (this.x == that.x) {
                return Double.NEGATIVE_INFINITY;
            }
            return +0.0;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            return (double) (that.y - this.y) / (that.x - this.x);
        }
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double diff = Point.this.slopeTo(a) - Point.this.slopeTo(b);
            if (diff > 0.0) return 1;
            if (diff < 0.0) return -1;
            return 0;
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point a = new Point(3000, 7000);
        Point b = new Point(6000, 7000);
        Point c = new Point(0, 10000);
        StdOut.println(a.slopeTo(b));
        StdOut.println(b.slopeTo(c));
    }
}
