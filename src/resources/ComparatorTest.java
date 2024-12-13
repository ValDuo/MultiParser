package resources;

import java.io.IOException;

public class ComparatorTest {
    public static void main(String[] args) throws IOException {
        String input_file = "C:\\Users\\d.kostukov\\IdeaProjects\\parcing-of-kadastrs\\srcFiles\\new_kadastr_28_10_2024.csv";
        String output_directory = "C:\\Users\\d.kostukov\\IdeaProjects\\parcing-of-kadastrs\\srcFiles\\zip1_test";
        ComparatorSentRecieved comparator = new ComparatorSentRecieved(input_file,output_directory);
        //comparator.compare();
        System.out.println(comparator.getResponseSet());
    }
}
