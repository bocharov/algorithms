public class KdTree {
    private Node root;
    private int N;
    private int num;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(Point2D p) {
         if (N == 0) {
            root = new Node();
            root.p = p;
            root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            N++;
        }
        insert(root, p, true);
    }

    private void insert(Node x, Point2D p, boolean isVertical) {
        if (x.p.equals(p)) return;
        double cmp = isVertical ? p.x() - x.p.x() : p.y() - x.p.y();
        RectHV r = x.rect;
        if (cmp < 0) {
            if (x.lb == null) {
                Node n = new Node();
                n.p = p;
                if (isVertical) {
                    n.rect = new RectHV(r.xmin(), r.ymin(), x.p.x(), r.ymax());
                } else {
                    n.rect = new RectHV(r.xmin(), r.ymin(), r.xmax(), x.p.y());
                }
                x.lb = n;
                N++;
            } else {
                insert(x.lb, p, !isVertical);
            }
        } else {
            if (x.rt == null) {
                Node n = new Node();
                n.p = p;
                if (isVertical) {
                    n.rect = new RectHV(x.p.x(), r.ymin(), r.xmax(), r.ymax());
                } else {
                    n.rect = new RectHV(r.xmin(), x.p.y(), r.xmax(), r.ymax());
                }
                x.rt = n;
                N++;
            } else {
                insert(x.rt, p, !isVertical);
            }
        }
    }

    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private boolean contains(Node x, Point2D p, boolean isVertical) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        double cmp = isVertical ? p.x() - x.p.x() : p.y() - x.p.y();
        if (cmp < 0) return contains(x.lb, p, !isVertical);
        else         return contains(x.rt, p, !isVertical);
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node n, boolean isVertical) {
        if (n == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(n.p.x(), n.p.y());
        StdDraw.setPenRadius();
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }
        draw(n.lb, !isVertical);
        draw(n.rt, !isVertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> pointsInRect = new Queue<Point2D>();
        range(root, rect, pointsInRect);
        return pointsInRect;
    }

    private void range(Node n, RectHV r, Queue<Point2D> q) {
        if (n == null || !n.rect.intersects(r)) return;
        if (r.contains(n.p)) q.enqueue(n.p);
        range(n.lb, r, q);
        range(n.rt, r, q);
    }

    public Point2D nearest(Point2D p) {
        if (N == 0) return null;
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node n, Point2D p, Point2D currentNear, boolean isVertical) {
        double currentMinDist = currentNear.distanceSquaredTo(p);
        if (n == null || currentMinDist <= n.rect.distanceSquaredTo(p)) return currentNear;
        if (currentMinDist > n.p.distanceSquaredTo(p)) currentNear = n.p;
        num++;
        double cmp = isVertical ? p.x() - n.p.x() : p.y() - n.p.y();
        if (cmp < 0) {
            Point2D lbNear = nearest(n.lb, p, currentNear, !isVertical);
            if (n.rt == null || lbNear.distanceSquaredTo(p) <= n.rt.rect.distanceSquaredTo(p)) {
                return lbNear;
            } else {
                Point2D rtNear = nearest(n.rt, p, currentNear, !isVertical);
                return lbNear.distanceSquaredTo(p) <= rtNear.distanceSquaredTo(p) ? lbNear : rtNear;
            }
        } else {
            Point2D rtNear = nearest(n.rt, p, currentNear, !isVertical);
            if (n.lb == null || rtNear.distanceSquaredTo(p) <= n.lb.rect.distanceSquaredTo(p)) {
                return rtNear;
            } else {
                Point2D lbNear = nearest(n.lb, p, currentNear, !isVertical);
                return lbNear.distanceSquaredTo(p) <= rtNear.distanceSquaredTo(p) ? lbNear : rtNear;
            }
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
        StdOut.println(kdtree.nearest(new Point2D(.81, .30)));
        StdOut.println(kdtree.num);
    }
}
