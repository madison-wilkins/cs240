package hangman;

import java.io.File;
import java.util.*;

public class EvilHangman {

    public static void main(String[] args){
        File input = new File(args[0]);
        String wordLength = args[1];
        String guess = args[2];
        Set<String> possibles = new HashSet<>();
        int leng = Integer.parseInt(wordLength);
        int guesses = Integer.parseInt(guess);
        EvilHangmanGame hang = new EvilHangmanGame();
        SortedSet<Character> guessed = new TreeSet<>();
        try{
            hang.startGame(input, leng);
        }
        catch(Exception e){
            System.out.println("error");
        }
        Character c = ' ';
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        char[] word = new char[leng];
        boolean won = false;


        while(guesses > 0 && !won){
            System.out.println("You have " + guesses + " guesses left");
            System.out.print("Used Letters: ");
            guessed = hang.getGuessedLetters();
            Iterator<Character> itr = guessed.iterator();
            if(!guessed.isEmpty()){
                while(itr.hasNext()){
                    c = itr.next();
                    System.out.print(c + " ");
                }
            }
            System.out.println();
            System.out.print("Word: ");
            word = hang.getString();
            for(int i = 0 ; i < leng; i++){
                System.out.print(word[i] + " ");
            }
            System.out.println();
            boolean rightInput = false;
            while(!rightInput){
                System.out.print("Enter guess: ");
                if(sc.hasNextLine()){
                    String s = sc.next();
                    //System.out.println();
                    if(s.length() > 1 || s.length() <= 0){
                        System.out.println("Invalid input");
                        continue;
                    }
                    else{
                        c = s.charAt(0);
                    }
                    /*else if(sc.hasNext()){
                        System.out.println("Invalid input");
                    }*/
                    if(Character.isSpaceChar(c)){
                        System.out.println("Invalid input");
                    }
                    else if(!guessed.contains(c) && Character.isAlphabetic(c)){
                        rightInput = true;
                        //System.out.print('\n');
                    }
                    else if(!Character.isAlphabetic(c)){
                        System.out.println("Invalid input");
                    }
                    else{
                        //System.out.print('\n');
                        System.out.println("You already used that letter");
                    }
                }
                else{
                    //System.out.print('\n');
                    System.out.println("Invalid input");
                }
            }
            try{
                possibles = hang.makeGuess(c);
            }
            catch(Exception e){
                continue;
            }

            Iterator<String> it = possibles.iterator();
            String w = it.next();
            int count = 0;
            for(int i = 0; i < leng; i++){
                if(w.charAt(i) == c){
                    count++;
                }
            }
            if(count == 0){
                System.out.println("Sorry, there are no " + c + "'s");
            }
            else{
                System.out.println("Yes, there is " + count + " " + c);
            }
            word = hang.getString();
            int dashes = 0;
            for(int i = 0 ; i < leng; i++){
                //System.out.print(word[i]);
                if(word[i] == '-'){
                    dashes++;
                }
            }
            if(dashes == 0){
                System.out.println("You win!");
                won = true;
            }
            System.out.println('\n');
            guesses--;
        }
        if(won == false){
            System.out.println("You lose!");
            String random = hang.getRandomString();
            System.out.println("The word was: " + random);
        }
        else{
            System.out.print("The word was: ");
            word = hang.getString();
            for(int i = 0 ; i < leng; i++){
                System.out.print(word[i]);
            }
            System.out.println();
        }


    }

}
