import java.util.*;

class Partition {
    public static void main(String[] args) {
        Integer[] a = {
79,55,81,94,11,70,86,59,54,57,90,67
        };
        partition(a, 0, a.length - 1);
        //dijkstra(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static void dijkstra(Comparable[] a, int lo, int hi) {
        int lt = lo, gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else              i++;
        }
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v))
                if (i == hi) break;

            while (less(v, a[--j]))
                if (j == lo) break;

            if (i >= j) break;

            exch(a, i, j);
        }

        exch(a, lo, j);

        return j;
    }
}
