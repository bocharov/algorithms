import java.io.*;
import java.util.*;

public class MinCutLauncher {
    public static void main(String[] args) {
        String testFile = "kargerMinCut.txt";

        int min = Integer.MAX_VALUE;
        for (int i = 1; i < 1000; i++){
            MinCut test = new MinCut(testFile, i);
            int size = test.search();

            if (size < min) {
                min = size;
            }

            System.out.printf("i = %d | min = %d\r", i, min);
        }
        System.out.printf("\nAll done. min = %d\n", min);
    }
}

class MinCut {
    Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();

    Random randomGenerator;

    public MinCut(String fileSource, int seed){
        randomGenerator = new Random(seed);
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(fileSource));

            String nextLine;
            String[] numbersArray;

            while ((nextLine = reader.readLine()) != null) {
                numbersArray = nextLine.split("\t");
                int vertex = Integer.parseInt(numbersArray[0]);
                List<Integer> adjacentList = new ArrayList<Integer>();
                for (int i = 1; i < numbersArray.length; i++) {
                    int adjacent = Integer.parseInt(numbersArray[i]);

                    adjacentList.add(adjacent);


                }

                graph.put(vertex, adjacentList);

            }
        } catch (IOException e) {
            System.out.println("File not found");
        }

    }

    public int search() {
        while (graph.keySet().size() > 2) {
            fusion();
        }

        int minCutResult = 0;
        for (Integer i : graph.keySet()) {
            minCutResult = graph.get(i).size();
        }

        return minCutResult;
    }

    public void fusion() {
        List<Integer> verticesList = new ArrayList<Integer>(graph.keySet());

        int randomIndex = randomGenerator.nextInt(verticesList.size());
        int randomVertex1 = verticesList.get(randomIndex);
        List<Integer> adjacentList = graph.get(randomVertex1);

        randomIndex = randomGenerator.nextInt(adjacentList.size());
        int randomVertex2 = adjacentList.get(randomIndex);
        removeEdge(randomVertex1, randomVertex2);
    }

    private void removeEdge(int randomVertex1, int randomVertex2) {
        List<Integer> adjacentList1 = graph.get(randomVertex1);
        List<Integer> adjacentList2 = graph.get(randomVertex2);
        graph.remove(randomVertex1);

        adjacentList1 = removeLoops(adjacentList1, randomVertex2);
        adjacentList2 = removeLoops(adjacentList2, randomVertex1);
        for (Integer i : adjacentList1) {
            List<Integer> changeList = graph.get(i);
            for (int j = 0; j < changeList.size(); j++) {
                if (changeList.get(j) == randomVertex1) {
                    changeList.set(j, randomVertex2);
                }
            }

        }
        adjacentList2.addAll(adjacentList1);
        graph.put(randomVertex2, adjacentList2);

    }

    public List<Integer> removeLoops(List<Integer> sourceList, int vertex) {
        List<Integer> resultList = new ArrayList<Integer>();
        for (int i = 0; i < sourceList.size(); i++) {
            if (!(sourceList.get(i) == vertex)) {
                resultList.add(sourceList.get(i));
            }
        }

        return resultList;
    }
}