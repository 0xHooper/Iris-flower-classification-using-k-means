public class Main {
    public static void main(String[] args) {
        LoadTrainingSet trainingValues = new LoadTrainingSet("iris_training.txt");
        new Classification(trainingValues.getData());
    }
}
