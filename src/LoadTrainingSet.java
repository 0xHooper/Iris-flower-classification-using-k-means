import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LoadTrainingSet {
    private HashMap<String, List<List<Double>>> data;

    LoadTrainingSet(String fileName){
        try {
            Scanner scanner = new Scanner(new FileReader(fileName));
            data = new HashMap<>();
            getRowValues(scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getRowValues(Scanner scan) {
        while(scan.hasNext()){
            String[] row_values = scan.nextLine().split(" \t");
            String irisType = row_values[row_values.length-1];

            List<Double> numeric_values = IntStream.range(0, row_values.length - 1)
                    .mapToObj(i -> row_values[i].split(","))
                    .map(s -> Double.parseDouble(s[0] + "." + s[1]))
                    .collect(Collectors.toList());

            if(!data.containsKey(irisType)){
                List<List<Double>> tempList;
                tempList = new ArrayList<>();
                tempList.add(numeric_values);

                data.put(irisType, tempList);
            }else {
                data.get(irisType).add(numeric_values);
            }
        }
    }

    public HashMap<String, List<List<Double>>> getData() {
        return data;
    }
}
