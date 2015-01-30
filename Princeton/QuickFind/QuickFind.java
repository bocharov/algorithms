import java.util.*;
import java.io.*;

class QuickFind {
    public static void main(String[] args) {
        File file = new File("input.txt");
        try {
            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(file));

            int N = Integer.parseInt(br.readLine());
            System.out.printf("Got N = %d\n", N);

            int[] id = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
            }
            System.out.printf("Initialized id[]: %s\n\n", Arrays.toString(id));

            String[] unionOperations = br.readLine().split(" ");

            for (int i = 0; i < unionOperations.length; i++) {
                String[] unionOperation = unionOperations[i].split("-");
                QFUnion(id, Integer.parseInt(unionOperation[0]), Integer.parseInt(unionOperation[1]));
            }
        } catch (Exception e) {
            return;
        }
    }

    public static void QFUnion(int[] id, int p, int q) {
        System.out.printf("Union operation: %d-%d\n", p, q);
        System.out.printf("Before: %s\n", Arrays.toString(id));

        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }

        System.out.printf("After: %s\n\n", Arrays.toString(id));
    }
}
