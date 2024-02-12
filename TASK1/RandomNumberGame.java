import java.util.*;
import java.util.Random;

class RandomNumberGame{
      public static void main(String[] args){
          Scanner scanner = new Scanner(System.in);
          Random random = new Random();

         int lowerBound =1;
         int upperBound = 100;
         int numofRounds = 0;
         int totalChances = 0;
         int maxChances = 10;

        System.out.println();
        System.out.println("                   RANDOM NUMBER GAME      ");


        do{
            int secretNum = random.nextInt(upperBound - lowerBound +1)+lowerBound;
            int chances =0;
            System.out.println("Round "+(numofRounds+1)+":" +" Guess the secret number between " + lowerBound + " and " +upperBound );
            
             while(chances < maxChances){
               System.out.print("Guess a number:");
               int guessNum = scanner.nextInt();
               chances++;
               totalChances++;
              

             if(guessNum<secretNum){
                  System.out.println("Your guess is too low. Give another try!");
              }
             else if(guessNum>secretNum){
                   System.out.println("Your guess is too high. Give another try!");
              }
             else{
                  System.out.println("Congratulations! What a perfect Guess!");
                  System.out.println("voila! You guessed in "+chances+" attempts");
                    break;
                }
               
            }

          if(chances == maxChances){
                 System.out.println("You don't have anymore chances left to continue!");
                 System.out.println("The random number was "+secretNum);
           }

          numofRounds++;

          System.out.print("Want to play again?(yes/no):");
          String userChoice = scanner.next().toLowerCase();

           if(!userChoice.equals("yes")){
                   break;
           }

       }while(true);


        System.out.println("\nGame Over! You completed " + numofRounds + " rounds with a total of " + totalChances + " attempts.");

          scanner.close();

    }

}
         