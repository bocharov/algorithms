import java.awt.*;
import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.show();

        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
        }

        Arrays.sort(points);

        Point pivot;
        Point[] pointsCopy;
        int nextToPivotIndex;
        double lastSlope;
        double currentSlope;
        int counter;

        for (int i = 0; i < N; i++) {
            pointsCopy = Arrays.copyOf(points, N);
            Arrays.sort(pointsCopy, points[i].SLOPE_ORDER);

            pivot = points[i];
            pivot.draw();
            if (N < 4) continue;

            nextToPivotIndex = 1;
            counter = 2;
            lastSlope = pivot.slopeTo(pointsCopy[nextToPivotIndex]);

            for (int j = 2; j < N; j++) {
                currentSlope = pivot.slopeTo(pointsCopy[j]);
                if (currentSlope == lastSlope) {
                    counter++;
                } else {
                    if (counter >= 4 && (pivot.compareTo(pointsCopy[nextToPivotIndex]) < 0)) {
                        StdOut.print(pivot + " -> ");
                        for (int k = nextToPivotIndex; k < j - 1; k++) {
                            StdOut.print(pointsCopy[k] + " -> ");
                        }
                        StdOut.print(pointsCopy[j - 1] + "\n");
                        pivot.drawTo(pointsCopy[j - 1]);
                    }
                    counter = 2;
                    nextToPivotIndex = j;
                    lastSlope = currentSlope;
                }
            }

            if (counter >= 4 && (pivot.compareTo(pointsCopy[nextToPivotIndex]) < 0)) {
                StdOut.print(pivot + " -> ");
                for (int k = nextToPivotIndex; k < N - 1; k++) {
                    StdOut.print(pointsCopy[k] + " -> ");
                }
                StdOut.print(pointsCopy[N - 1] + "\n");
                pivot.drawTo(pointsCopy[N - 1]);
            }
        }
    }
}
