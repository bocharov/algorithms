import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;

    public PointSET() {
        set = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        set.add(p);
    }

    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    public void draw() {
        Iterator<Point2D> points = set.iterator();
        while (points.hasNext()) {
            points.next().draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> pointsInRect = new Queue<Point2D>();
        Iterator<Point2D> points = set.iterator();
        while (points.hasNext()) {
            Point2D p = points.next();
            if (rect.contains(p)) pointsInRect.enqueue(p);
        }
        return pointsInRect;
    }

    public Point2D nearest(Point2D p) {
        if (set.isEmpty()) return null;
        Point2D currentNearest = null;
        double currentMinDist = Double.POSITIVE_INFINITY;
        Iterator<Point2D> points = set.iterator();
        while (points.hasNext()) {
            Point2D cp = points.next();
            double currentDist = p.distanceTo(cp);
            if (currentDist < currentMinDist) {
                currentMinDist = currentDist;
                currentNearest = cp;
            }
        }
        return currentNearest;
    }

    public static void main(String[] args) {
        PointSET pSet = new PointSET();
        StdOut.println(pSet.isEmpty());
        pSet.insert(new Point2D(0.4, 0.5));
        StdOut.println(pSet.isEmpty());
        StdOut.println(pSet.size());
        pSet.insert(new Point2D(0.3, 0.8));
        pSet.insert(new Point2D(0.7, 0.1));
        pSet.insert(new Point2D(0.5, 0.3));
        StdOut.println(pSet.size());
        pSet.draw();
        RectHV rect = new RectHV(0.2, 0.2, 0.6, 0.6);
        rect.draw();
        Iterable<Point2D> x = pSet.range(rect);
        for (Point2D p: x) StdOut.println(p);
        StdOut.println(pSet.nearest(new Point2D(0.5, 0.5)));
    }
}
