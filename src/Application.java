import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File(args[0]);
		Scanner scan = new Scanner(file);
		Scanner in = new Scanner(System.in);
		int num_words = 0, num_sentences = 0, num_characters = 0;
		int syllabels_count = 0; int polysyllabels_count = 0;
		String text = "";
		while (scan.hasNext()) {
			String line = scan.nextLine();
			text += line;
			String words[] = line.split(" +");
			num_words += line.split(" +").length;
			for (String word : words) {
				num_characters += word.length();
				int count = CountSyllables(word);
				syllabels_count += count;
				if (count > 2) polysyllabels_count ++;
				
			}
			String sentences[] = line.split("[!.?=]+");
			num_sentences += sentences.length;
		}
		String age[] = {" ", "6", "7", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "24", "24"};
		System.out.println("The text is:\n" + text +"\n");
		System.out.println("Words: " + num_words);
		System.out.println("Sentences: " + num_sentences);
		System.out.println("Characters: " + num_characters);
		System.out.println("Syllables: " + syllabels_count);
		System.out.println("Polysyllables: " + polysyllabels_count);
		System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
		String response = in.next();
		switch (response) {
		case "ARI":
			AutomatedReadabilityIndex(num_words, num_characters, num_sentences, age);
			break;
		case "FK":
			FleschKincaidReadability(num_words, syllabels_count, num_sentences, age);
			break;
		case "SMOG":
			SimpleMeasureOFGobbledygook(polysyllabels_count, num_sentences, age);
			break;
		case "CL":
			Coleman_Liau_index(num_characters, num_sentences, num_words, age);
			break;
		default:
			AutomatedReadabilityIndex(num_words, num_characters, num_sentences, age);
			FleschKincaidReadability(num_words, syllabels_count, num_sentences, age);
			SimpleMeasureOFGobbledygook(polysyllabels_count, num_sentences, age);
			Coleman_Liau_index(num_characters, num_sentences, num_words, age);
		}
	}
	
	
	public static int CountSyllables(String s) {
		int count = 0;
		s = s.toLowerCase();
		s = s.split("[!.?,:]")[0];
		String regexString = "[aiuoye]";
		s = s.charAt(s.length() - 1) == 'e' ? s.substring(0 , s.length() -1) : s;
		boolean b = true;
		for (int i = 0; i < s.length(); i++) {
			if(String.valueOf(s.charAt(i)).matches(regexString) && b) {
				count++;
				b = false;
			} else {
				b = true;
			}
			
		}
		return count == 0 || s.length() <= 3? 1 : count;
		
	}
	
	public static void AutomatedReadabilityIndex(int words , int characters , int sentences , String age[]) {
		double score = 4.71 * characters / words + 0.5 * words / sentences - 21.43;
		System.out.printf("Automated Readability Index: %.2f (about %s year olds).\n", score, age[(int) Math.round(score)]);
	}
	
	public static void FleschKincaidReadability(int words, int syllabels, int sentences, String age[]) {
		double score = 0.39 * words / sentences + 11.8 * syllabels / words - 15.59;
		System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s year olds).\n", score, age[(int) Math.round(score)]);
	}	
	
	public static void SimpleMeasureOFGobbledygook(int polysyllabels, int sentences, String age[]) {
		double d = (double) polysyllabels * 30 / sentences;
		double score = 1.043 * Math.sqrt(d) + 3.1291;
		System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s year olds).\n", score, age[(int) Math.round(score)]);
	}
	
	public static void Coleman_Liau_index(int characters, int sentences, int words, String age[]) {
		double l =(double) characters / words *100;
		double s = (double) sentences / words * 100;
		double score = 0.0588 * l - 0.296 * s -15.8;
		System.out.printf("Coleman–Liau index: %.2f (about %s year olds).\n", score, age[(int) Math.round(score)]);
	}
}
