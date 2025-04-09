package file_information;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class text_analyzer {

    public int word_count;
    public int char_count;
    public int sentence_count;



    public void set_counters(int word_count , int char_count ,int sentence_count){
        this.word_count = word_count;
        this.sentence_count = sentence_count;
        this.char_count = char_count;
    }

    public void text_preprocesser(){
        String the_file = "src/data.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(the_file)))
        {
        String lines;
        while((lines = br.readLine()) != null) {
            sentence_count += 1;
            System.out.println(lines);

            String [] array1 = lines.split("\\s+");
            word_count+= array1.length;
            String [] array2 = lines.split("");
            char_count+= array2.length;


        }

            System.out.println("\n\n\n");
            System.out.println("The Number of sentences are : "+ sentence_count);
            System.out.println("The Number of words are : "+ word_count);
            System.out.println("The Number of chars are : "+ char_count);


        }
        catch(IOException E)
        {
            System.err.println("Some issue" + E.getMessage());
        }

    }
}
