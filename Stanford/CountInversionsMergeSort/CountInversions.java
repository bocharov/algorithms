import java.util.*;
import java.io.*;

class CountInversions {
    public static void main(String[] args) {
        File file = new File("IntegerArray.txt");
        int[] a = new int[100000];
        try {
            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            while ((line = br.readLine()) != null) {
                a[i++] = Integer.parseInt(line);
            }

            //System.out.println(Arrays.toString(a));
            System.out.println(sortAndCountInversions(a));
            //System.out.println(Arrays.toString(a));
        } catch (Exception e) {
            return;
        }
    }

    public static long sortAndCountInversions(int[] a) {
        if (a.length == 0 || a.length == 1) {
            return 0;
        }

        int[] left = new int[a.length / 2];
        int[] right = new int[a.length - left.length];
        for (int k = 0; k < left.length; k++) {
            left[k] = a[k];
        }

        for (int k = 0, m = left.length; k < right.length; k++, m++) {
            right[k] = a[m];
        }

        long countLeft = sortAndCountInversions(left);
        long countRight = sortAndCountInversions(right);

        int i = 0, j = 0, k = 0;
        long countTotal = countLeft + countRight;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                a[k++] = left[i++];
            } else {
                a[k++] = right[j++];
                countTotal += left.length - i;
            }
        }

        while (i < left.length) {
            a[k++] = left[i++];
        }

        while (j < right.length) {
            a[k++] = right[j++];
        }

        return countTotal;
    }
}
