import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Classification {

    public Classification(HashMap<String, List<List<Double>>> trainingData) {
        classify(trainingData);
    }

    private void classify(HashMap<String, List<List<Double>>> trainingData) {
        Set<String> set = trainingData.keySet();
        int k = 3;
        int counterCorrect = 0;
        int counter = 0;

        try {
            Scanner scan = new Scanner(new FileReader("iris_test.txt"));

            while (scan.hasNext()) {
                String[] line = scan.nextLine().split(" \t ");
                List<Double> list = IntStream.range(0, line.length - 1)
                        .mapToObj(i -> line[i].split(","))
                        .map(s -> Double.parseDouble(s[0] + "." + s[1]))
                        .collect(Collectors.toList());
                List<Point> data = new ArrayList<>();

                set.forEach(e -> {
                    List<List<Double>> tempList = trainingData.get(e);
                    tempList.forEach(e1 -> {
                        double pow_distance = 0;
                        for (int i = 0; i < e1.size(); i++) {
                            pow_distance += Math.pow((e1.get(i) - list.get(i)), 2);
                        }
                        double distance = Math.sqrt(pow_distance);
                        data.add(new Point(e, distance));
                    });
                });
                data.sort(Comparator.comparingDouble(d -> d.distance));

                String result = assignToClass(k, data);
                if (result.equals(line[line.length - 1])) {
                    counterCorrect++;
                }
                counter++;
            }
            printAccuracy(k, counterCorrect, counter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printAccuracy(int k, int counterCorrect, int counter) {
        double accuracy = (double) counterCorrect / (double) counter;
        System.out.println("Accuracy of the program for " + k + " neighbours= " + accuracy);
    }

    private String assignToClass(int k, List<Point> data) {
        int class1 = 0, class2 = 0, class3 = 0;
        for (int i = 0; i < k; i++) {
            if (data.get(i).name.equals("Iris-versicolor")) {
                class1++;
            }
            if (data.get(i).name.equals("Iris-virginica")) {
                class2++;
            }
            if (data.get(i).name.equals("Iris-setosa")) {
                class3++;
            }
        }
        String result;
        if (class1 > class2) {
            if (class1 > class3)
                result = "Iris-versicolor ";
            else
                result = "Iris-setosa ";
        } else {
            if (class2 > class3)
                result = "Iris-virginica ";
            else
                result = "Iris-setosa ";
        }
        return result;
    }

    private static class Point {
        String name;
        double distance;

        Point(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }
    }
}