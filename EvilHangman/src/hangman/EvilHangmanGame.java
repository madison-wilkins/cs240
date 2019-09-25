package hangman;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame{

    private Set<String> words;
    private int wordLeng;
    private SortedSet<Character> guessed;
    private int openSpaces;
    private char[] openStr;
    private HashMap<Integer, Set<String>> part;
    private String randomString;


    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        wordLeng = wordLength;
        words = new HashSet<String>();
        guessed = new TreeSet<>();
        part = new HashMap<Integer, Set<String>>();

        try{
            if(wordLength <= 1){
                throw new EmptyDictionaryException();
            }
            if(dictionary.length() == 0){
                throw new EmptyDictionaryException();
            }
            FileReader input = new FileReader(dictionary);
            Scanner s = new Scanner(input);
            s.useDelimiter("([^a-zA-Z+])");
            while(s.hasNext()){
                String word = s.next();
                if(word.length() == wordLeng){
                    words.add(word);
                }
            }
            if(words.isEmpty()){
                throw new EmptyDictionaryException();
            }
            openStr = new char[wordLeng];
            for(int i = 0 ; i < wordLeng; i++){
                openStr[i] = '-';
            }
            openSpaces = 0;
        }
        catch(IOException e){
            System.out.println("Problem when trying to read the file");
        }
        /*catch(EmptyDictionaryException d){
            System.out.println("Dicitonary is empty");
        }*/
    }

    public char[] getString(){
        return openStr;
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        part.clear();
        guess = Character.toLowerCase(guess);
        //System.out.println(guess);
        if(guessed.contains(guess)){
            throw new GuessAlreadyMadeException();
        }

        guessed.add(guess);
        //HashMap<Integer, Set<String>> part = new HashMap<Integer, Set<String>>();
        String w;
        Iterator<String> itr = words.iterator();
        while(itr.hasNext()){
            partition(itr.next(), part, guess);
        }

        Iterator<Integer> it = part.keySet().iterator();
        int best = it.next();
        while(it.hasNext()){
            int i = it.next();
            if(better(i, best, part, guess) == i){
                best = i;
            }
        }
        words = part.get(best);
        Iterator<String> rando = words.iterator();
        randomString = rando.next();
        updateGuess(best, guess);
        return words;
    }

    public String getRandomString(){
        return randomString;
    }


    private void updateGuess(int best, char guess){
        openSpaces &= best;
        int lastGuess = partLetters(best, guess);
        for(int i = 0; i < wordLeng; i++){
            if(best%2 == 1){
                openStr[i] = guess;
            }
            best /= 2;
        }
    }

    public boolean comparable(int i){
        if((i&openSpaces) == 0){
            return true;
        }
        return false;
    }

    public int better(int newInt, int best, HashMap<Integer, Set<String>> part, char c){
        if(best < 0){
            return newInt;
        }
        /*if(best == 0){
            return newInt;
        }*/
        int newIntSize = part.get(newInt).size();
        int bestSize = part.get(best).size();
        //if the partitions are the same size
        if(part.get(newInt).size() > part.get(best).size()){
            return newInt;
        }
        else if(part.get(newInt).size() < part.get(best).size()){
            return best;
        }
        //if there is a group where the letter does not appear at all
        else if(partLetters(newInt, c) == 0){
            return newInt;
        }
        else if(partLetters(best, c) == 0){
            return best;
        }
        //return one with the fewest of the letters
        else if(partLetters(newInt, c) < partLetters(best, c)){
            return newInt;
        }
        else if(partLetters(best,c) < partLetters(newInt,c)){
            return best;
        }
        //return the one with the rightmost letter
        else if(rightmost(newInt,best,c) == newInt){
            return newInt;
        }
        else if(rightmost(newInt, best, c) == best){
            return best;
        }

        return 0;
    }

    public int rightmost(int newInt, int best, char c){
        int num = 0;
        int index1 = 0;
        int index2 = 0;
        Set<String> w = part.get(newInt);
        Iterator<String> it1 = w.iterator();
        String word1 = it1.next();
        int i = wordLeng - 1;
        boolean notFound = false;
        while(i >= 0 && notFound == false){
            if(word1.charAt(i) == c){
                index1 = i;
                notFound = true;
            }
            i--;
        }

        Set<String> w2 = part.get(best);
        Iterator<String> it2 = w2.iterator();
        String word2 = it2.next();
        i = wordLeng - 1;
        notFound = false;
        while(i >= 0 && notFound == false){
            if(word2.charAt(i) == c){
                index2 = i;
                notFound = true;
            }
            i--;
        }

        if(index1 > index2){
            return newInt;
        }
        else if(index2 > index1){
            return best;
        }
        else{
            word1 = word1.substring(0, index1);
            word2 = word2.substring(0, index2);
            if(rightmostRec(word1, word2, c) == 1){
                return newInt;
            }
            else{
                return best;
            }
        }
    }

    public int rightmostRec(String w1, String w2, char c){
        if(w1.length() <= 0 || w2.length() <= 0){
            return 0;
        }
        int num = 0;
        int index1 = 0;
        int index2 = 0;
        int i = w1.length() - 1;
        boolean notFound = false;
        while(i >= 0 && notFound == false){
            if(w1.charAt(i) == c){
                index1 = i;
                notFound = true;
            }
            i--;
        }
        i = w2.length() - 1;
        notFound = false;
        while(i >= 0 && notFound == false){
            if(w2.charAt(i) == c){
                index2 = i;
                notFound = true;
            }
            i--;
        }

        if(index1 > index2){
            return 1;
        }
        else if(index2 > index1){
            return 2;
        }
        else{
            w1 = w1.substring(0, index1);
            w2 = w2.substring(0, index2);
            if(rightmostRec(w1, w2, c) == 1){
                return 1;
            }
            else if(rightmostRec(w1, w2, c) == 2){
                return 2;
            }
        }
        return 0;
    }

    public int partLetters(int partNum, char c){
        Set<String> w = part.get(partNum);
        Iterator<String> itr = w.iterator();
        int num = 0;
        String word = itr.next();
        for(int i = 0; i < wordLeng; i++){
            if(word.charAt(i) == c){
                num++;
            }
        }
        return num;
    }


    public void partition(String word, HashMap<Integer, Set<String>> part, char guess){
        int p = partitionNum(word, guess);
        if(!part.containsKey(p)){
            part.put(p, new HashSet<String>());
        }
        part.get(p).add(word);
    }

    public int partitionNum(String w, char guess){
        int num = 0;
        for(int i = w.length() - 1; i >=0; i--){
            num = num * 2;
            if(w.charAt(i) == guess){
                num++;
            }
        }
        return num;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessed;
    }

    private boolean checkChar(String word, Character guessed){
        Character curr;
        for(int i = 0; i < word.length(); i++){
            curr = word.charAt(i);
            if(curr == guessed){
                return true;
            }
        }
        return false;
    }
}
