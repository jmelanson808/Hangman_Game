//Interactive Hangman game, utilizing generics.
//authored by Jesse Melanson

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Snowman {
	
	List<String> wordList = new ArrayList<String>();
	Set<Character> usedChars = new ArraySet<Character>();

	//imports text file to wordList
	public void readWords(String fileName) throws FileNotFoundException {
		Scanner transcribe = new Scanner(new File(fileName));

		while (transcribe.hasNext())
			wordList.add(transcribe.next());

		transcribe.close();
	}

	//returns a random word from wordList
	public String getWord() {
		Random r = new Random();
		return wordList.get(r.nextInt(wordList.size()));
	}
	
	//checks if there are any blank spaces left
	public boolean checkWin(char[] guess) {
		int incomplete = 0;
		
		for(char i : guess) {
			if (i == '_'){
				incomplete++;
			}
		}
		if (incomplete == 0){
			return true;
		}
		return false;
	}
	
	public void displayResults(char[] guess) {
		for (char i : guess) {
			System.out.print(" " + i + " ");
		}
	}
	
	public void playGame(String word) {
		int errorCount = 0;
		boolean correct;
		char nextChoice;
		char[] guess = new char[word.length()];
		char[] key = word.toCharArray();
		
		for (int i = 0; i < guess.length; i++) {
			guess[i] = '_';
		}
		
		Scanner input = new Scanner(System.in);
		
		while (errorCount < 6 && checkWin(guess) == false) {
			correct = false;
			
			displayResults(guess);

			System.out.print("\n\nYour choice: ");
			nextChoice = input.next().toLowerCase().charAt(0);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			//checks if input is a letter
			if(Character.toString(nextChoice).matches("[^a-z]")) {
				System.out.println("Incorrect guesses: " + errorCount);
				System.out.println("Invalid guess, choose a letter.");
				continue;
			}
			
			//checks if input has already been used
			if(usedChars.contains(nextChoice)) {
				System.out.println("Incorrect guesses: " + errorCount);
				System.out.println("Letter already used, try another.\n");
				continue;
			}
			
			for (int i = 0; i < key.length; i++) {
				if(key[i] == nextChoice) {
					guess[i] = nextChoice;
					correct = true;
				}
			}
			
			if(correct == false) {
				errorCount++;
				System.out.println(nextChoice + " does not appear.");
			}else {
				System.out.println(nextChoice + " is correct!");
			}
			
			usedChars.add(nextChoice);
			
			System.out.println("Incorrect guesses: " + errorCount + "\n");
			
			checkWin(guess);
		}
		
		if(errorCount == 6) {
			System.out.println("D'oh! You lost!");
			System.out.println("The correct word was " + word + ".");
		}
		
		if(checkWin(guess)) {
			displayResults(guess);
			System.out.println("\n\nWoohoo! You won!");
		}
		
		input.close();
	}

	public static void main(String[] args) {
		
		Snowman game = new Snowman();

		try {
			game.readWords("words.txt");
			
			String word = game.getWord();
			
			game.playGame(word);
		} catch (FileNotFoundException fnf) {
			System.err.println("words.txt file Not Found");
		}	
	}
}
