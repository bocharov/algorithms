import java.io.*;
import java.util.*;

public class QuickSortLauncher {
    public static void main(String[] args) {
        File file = new File("QuickSort.txt");
        int[] a = new int[10000];
        try {
            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            while ((line = br.readLine()) != null) {
                a[i++] = Integer.parseInt(line);
            }
        } catch (Exception e) {
            return;
        }

        QuickSort qs = new QuickSort();

        int count = qs.sortAndCountComparisons(a, 0, a.length - 1, QuickSort.PivotChooseStrategy.MEDIAN_OF_THREE);
        System.out.printf("a = %s\n", Arrays.toString(a));
        System.out.printf("Count = %d\n", count);
    }
}

class QuickSort {
    public enum PivotChooseStrategy {
        FIRST_ELEMENT,
        LAST_ELEMENT,
        MEDIAN_OF_THREE,
        RANDOM_ELEMENT,
    }

    private int choosePivot(int[] a, int l, int r, PivotChooseStrategy strategy) {
        int pi = l;
        switch (strategy) {
            case FIRST_ELEMENT:
                pi = l;
                break;

            case LAST_ELEMENT:
                pi = r;
                break;

            case MEDIAN_OF_THREE:
                int m = l + (r - l) / 2;

                int[] b = {a[l], a[m], a[r]};
                Arrays.sort(b);
                if (b[1] == a[l]) {
                    pi = l;
                } else if (b[1] == a[m]) {
                    pi = m;
                } else {
                    pi = r;
                }

                //System.out.printf("l = %d | m = %d | r = %d\n", l, m, r);
                //System.out.printf("a[l] = %d | a[m] = %d | a[r] = %d | p = %d\n", a[l], a[m], a[r], a[pi]);
                break;

            case RANDOM_ELEMENT:
                Random rand = new Random();
                pi = rand.nextInt((r - l) + 1) + l;
                break;
        }

        return pi;
    }

    public int sortAndCountComparisons(int[] a, int l, int r, PivotChooseStrategy strategy) {
        //System.out.printf("Before: a = %s\n", Arrays.toString(a));
        //System.out.printf("l = %d r = %d\n", l, r);

        if (l >= r) {
            return 0;
        }

        int pi = choosePivot(a, l, r, strategy);

        int p = a[pi];
        a[pi] = a[l];
        a[l] = p;

        //System.out.printf("Pivot = %d\n", p);
        int i = l + 1;
        int tmp;
        for (int j = l; j <= r; j++) {
            if (a[j] < p) {
                tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
                i++;
            }
            //System.out.printf("k = %d a[k] = %d j = %d a = %s\n", j, a[j], i, Arrays.toString(a));
        }
        a[l] = a[i - 1];
        a[i - 1] = p;
        //System.out.printf("After: a = %s\n", Arrays.toString(a));

        int count = r - l;
        count += sortAndCountComparisons(a, l, i - 2, strategy);
        count += sortAndCountComparisons(a, i, r, strategy);

        return count;
    }
}