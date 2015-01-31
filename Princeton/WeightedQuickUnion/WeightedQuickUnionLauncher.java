import java.util.*;
import java.io.*;

class WeightedQuickUnionLauncher {
    public static void main(String[] args) {
        WeightedQuickUnion instance = new WeightedQuickUnion();
        instance.run();
    }
}

class WeightedQuickUnion {
    private int N;
    private int[] id;
    private int[] sz;

    public void run() {
        File file = new File("input.txt");
        try {
            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(file));

            N = Integer.parseInt(br.readLine());
            System.out.printf("Got N = %d\n", N);

            id = new int[N];
            sz = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
            }
            System.out.printf("Initialized id[]: %s\n\n", Arrays.toString(id));
            System.out.printf("Initialized sz[]: %s\n\n", Arrays.toString(sz));

            String[] unionOperations = br.readLine().split(" ");

            for (int i = 0; i < unionOperations.length; i++) {
                String[] unionOperation = unionOperations[i].split("-");
                union(Integer.parseInt(unionOperation[0]), Integer.parseInt(unionOperation[1]));
            }
        } catch (Exception e) {
            return;
        }
    }

    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    public void union(int p, int q) {
        System.out.printf("Union operation: %d-%d\n", p, q);
        System.out.printf("Before id: %s\n", Arrays.toString(id));
        System.out.printf("Before sz: %s\n", Arrays.toString(sz));

        int i = root(p);
        int j = root(q);

        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }

        System.out.printf("After id: %s\n", Arrays.toString(id));
        System.out.printf("After sz: %s\n\n", Arrays.toString(sz));
    }
}
