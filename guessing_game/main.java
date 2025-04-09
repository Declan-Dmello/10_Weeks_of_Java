package guessing_game;
import java.util.Scanner;
import java.util.Random;
public class main {
    public static void main(String[] args) {


        Guessing g1 = new Guessing();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input No of Guesses: ");
        Random r1 = new Random();
        int random_number = r1.nextInt(10000);
        int no_of_guesses = scanner.nextInt();
        int j =0;
        for (int i = 0; i < no_of_guesses; i++)
        {

            if (i == (no_of_guesses -1)){
                System.out.println("This is Your final Guess");
            }
            System.out.println("\nInput the Guess: ");
            int guess = scanner.nextInt();
            g1.set_guesses(random_number, guess);
            g1.make_guess();

            if (g1.guess == g1.random_guess)
            {
                break;
            }
            j++;
            if (no_of_guesses == j ){
                System.out.println("You have Failed to Guess");
                System.out.println("The Random Number was  " + random_number);
            }

        }


    }
}
