import java.util.*;
import java.io.*;

/**
 * Name: Justin Ho
 *
 * Date: 04/19/2025
 *
 * Class: CSC 1120
 *
 * Pledge: I have neither given nor received unauthorized aid on this program.
 *
 * Description: This program scans a file, and then takes in the input and uses it as
 * coordinates for each dart each player shoots. In each round, each player has three shots
 * and the points from each are totaled.
 *
 * Input: The user enters the name of the file that contains the coordinates for the game.
 *
 * Output:The program will display the winner, if any, after each round, and the points of each
 * player that round. It will indicate if there is a tie. It will also indicate which round the
 * players are on. It will print out a final statistic indicating how many ties and wins there
 * were from each player. Then it will print out who the overall winner is, if there is one.
 *
 */
public class Bullseye {
    private static int round=1;
    private static int ties=0;
    private static int p1Win=0;
    private static int p2Win=0;

    /**
     * This is the main method that runs the program and where user input is entered.
     * @param args This is the user input
     * @throws FileNotFoundException the exception if the entered file name is invalid.
     */
    public static void main(String [] args) throws FileNotFoundException{
        Scanner s= new Scanner(System.in);
        System.out.println("Please enter coordinate text file:");
        String filename="";
        boolean valid=false;
        //this will loop until the user enters a valid file name
        while(!valid){
            filename=s.nextLine();
            if(exists(filename)){
                valid=true;
            }else{
                System.out.println("Not a valid file name, try again");
            }
        }
        readRounds((filename));
        finalResult();
    }

    /**
     * This method takes in the file name and then does through it round by round and
     * then gets the coordinates for each player's dart. Then it will calculate and compare their
     * scores using the score and compare scores methods.
     * @param file This is a string that is the name of the file to read and get coordinates from.
     * @throws FileNotFoundException the exception that will be thrown if the file is not found.
     */
    private static void readRounds(String file) throws FileNotFoundException{
        Scanner scan= new Scanner(new File(file));
        //Arrays that contain the score for each dart that player threw that round
        int [] p1Scores= new int[3];
        int [] p2Scores= new int[3];
        int total1=0, total2=0;
        //loop to ensure it goes through the entire file
        for(int i=0; i<numLines(file);i++){
            //this takes in the first like and splits it into a String array by ' ' becuase
            //each number is seperated by a space
            String [] cords=scan.nextLine().split(" ");
            //this will go through the first half of the array and calculate the total score
            //of player 1 that round.
            for (int j = 0; j<(cords.length-1)/2; j+=2) {
                //This converts the string value into a double
                double x = Double.parseDouble(cords[(j)]);
                double y = Double.parseDouble(cords[j + 1]);
                p1Scores[j/2] =score(x, y);
            }
            total1 = totalScore(p1Scores[0], p1Scores[1], p1Scores[2]);
            //this will go through the second half of the array and calculate the total score
            //of player 2 that round.
            for (int j =cords.length/2; j <cords.length-1; j+=2) {
                //converts the String into a double
                double x = Double.parseDouble(cords[(j)]);
                double y = Double.parseDouble(cords[j+1]);
                p2Scores[(j%(cords.length/2))/2] = score(x, y);
            }
            total2 = totalScore(p2Scores[0], p2Scores[1], p2Scores[2]);
            //Uses the compare scores method and then prints the result
            compareScores(total1, total2);
            round++;
        }
    }


    /**
     * This method calculates the score the player received from one dart by using its position
     * to find where it is on the dart board.
     * @param x This is a double that is the x coordinate of the dart.
     * @param y This is a double that is the y coordinate of the dart.
     * @return An integer that indicates the score received from that dart
     */
    private static int score(double x, double y){
        int score=0;
        // checks the darts coordinates and see where it is in the board using the formula.
        // for graphic a circle.
        if(((x*x)+(y*y)<=(3.0*3.0))){// If is lands in or on the inner ring this is a bullseye.
            score=100;
            // this means it landed on/in the second ring but not within the bullseye.
        }else if(((x*x)+(y*y)<=(6.0*6.0))){
            //This means it landed on/in the third ring but not within the other inner rings.
            score=80;
        }else if(((x*x)+(y*y)<=(9.0*9.0))){
            //This means that it landed on/in the fourth ring but not within the other inner rings.
            score=60;
        }else if(((x*x)+(y*y)<=(12.0*12.0))){
            //This means it landed in/on the fifth ring but not within the other inner rings.
            score=40;
        }else if(((x*x)+(y*y)<=(15.0*15.0))){
            //This means it landed in/on the outermost ring but not within the other inner rings.
            score=20;
        }
        //It returns 0 if the dart does not make it on the board
        return score;
    }

    /**
     * This method calculates the total score by adding the score of each dart a player threw.
     * @param d1 This is an integer which is the score of the first dart the player threw.
     * @param d2 This is an integer which is the score of the second dart the player threw.
     * @param d3 This is an integer which is the score of the third dart the player threw.
     * @return an integer that is the total score of the player that round
     */
    private static int totalScore(int d1, int d2, int d3){
        return d1+d2+d3;
    }

    /**
     * This method calculates the number of line there are in the file.
     * @param file This is the name of the file that is searched
     * @return An integer that is the number of lines in the file
     * @throws FileNotFoundException this is the exception thrown if the file with the name is not
     * found
     */
    private static int numLines(String file)throws FileNotFoundException{
        int numLine=0;
        Scanner lineReader= new Scanner(new File(file));
        while(lineReader.hasNextLine()){
            numLine++;
            lineReader.nextLine();
        }
        return numLine;
    }

    /**
     * This is a method that compares the score of the two players and prints out a message
     * indicating which is higher. It also increases the count of player 1 wins, player 2 wins,
     * or ties
     * @param p1 This is an integer that is the total score of player 1 that round.
     * @param p2 This is an integer that is the total score of player 2 that round.
     */
    private static void compareScores(int p1, int p2){
        if(p1<p2){
            System.out.println("Round "+round+" --"+" Score: "+p1+" to "+p2+", player 2 wins");
            p2Win++;
        }else if(p1>p2){
            System.out.println("Round "+round+" --"+" Score: "+p1+" to "+p2+", player 1 wins");
            p1Win++;
        }else{
            System.out.println("Round "+round+" --"+" Score: "+p1+" to "+p2+", tie");
            ties++;
        }
    }

    /**
     * This method checks the to see if the file name entered exists.
     * @param filename This is the name of the file that is going to be checked.
     * @return true if the file does exist and false if it does not.
     */
    private static Boolean exists(String filename){
        boolean doesExist;
        File test= new File(filename);
        doesExist=test.exists();
        return doesExist;
    }

    /**
     * This method prints out the final statistics of the file, which include the number
     * of each player's wins, number of ties, and what is the overall result.
     */
    private static void finalResult(){
        System.out.println();
        System.out.println("# of ties: "+ties);
        System.out.println("# of wins for player 1: "+p1Win);
        System.out.println("# of wins for player 2: "+p2Win);
        if(p1Win>p2Win){
            System.out.println("Overall winner is player 1");
        }else if(p2Win>p1Win){
            System.out.println("Overall winner is player 2");
        }else{
            System.out.println("Overall result is a tie");
        }
    }
}