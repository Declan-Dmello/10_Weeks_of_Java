package guessing_game;
import java.util.Scanner;
public class Guessing {

    public int guess  ;
    public int random_guess ;

    public void set_guesses(int random_guess , int guess) {
        this.random_guess = random_guess;
        this.guess = guess;
    }

    public void make_guess()
    {
        if (this.guess == this.random_guess){
            System.out.println("The Guess is correct");
        } else if (this.guess < this.random_guess)
        {
            System.out.println("Guess Higher");

        } else {

            System.out.println("Guess Lower");


        }
    }
}
