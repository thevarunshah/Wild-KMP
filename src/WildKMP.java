import java.util.Scanner;

/**
 * 
 * A modified version of the original Knuth-Morris-Pratt substring search algorithm which allows wildcards.
 * 
 * @author Varun Shah
 *
 */
public class WildKMP {

	/**
	 * Given some text and a pattern, it searches for the first instance of the pattern in the text.<br>
	 * An asterisk (*) in the pattern tells the algorithm to match on any character at that location in the text.
	 * 
	 * @param text The text to be searched
	 * @param pattern The pattern to search for in the text
	 * @return The starting index of the pattern in the text. If not found, -1 is returned.  
	 */
	public static int search(String text, String pattern){
		
		int t = text.length();
		int p = pattern.length();
		//if pattern length is longer than text length
		if(p > t){
			return -1;
		}
		
		//create dfa
		int[] prefixTable = getDFA(pattern);
		
		int matchLength = 0;
		for(int i = 0; i < t; i++){
			
			boolean reset = false;
			//back-track on failure
			while(matchLength > 0 && pattern.charAt(matchLength) != text.charAt(i)){
				//check if fail was due to wildcard
				if(pattern.charAt(matchLength) == '*'){
					//loop-back with KMP - double check already matched pattern
					if(matchLength > 0 && reset){
						int kmpValue = WildKMP.search(text.substring(i-matchLength, i), pattern.substring(0, matchLength));
						if(kmpValue != 0){
							//reset match
							matchLength = 0;
						}
						//edge case for extra match on wildcard
						else if(pattern.charAt(matchLength-1) == '*'){
							matchLength++;
						}
					}
					break;
				}
				
				matchLength = prefixTable[matchLength-1]; //fall-back
				reset = true;
			}
			
			//edge case for extra match after the fall-back
			if(reset && pattern.charAt(matchLength) == text.charAt(i-1)){
				matchLength++;
			}
			
			//match or wildcard
			if(pattern.charAt(matchLength) == text.charAt(i) || pattern.charAt(matchLength) == '*'){
				matchLength++; //matched
			}
			
			//found the pattern
			if(matchLength == p){
				return i-(p-1);
			}
		}
		
		//couldn't find the pattern
		return -1;
	}
	
	/**
	 * Creates the DFA for the KMP algorithm.
	 * 
	 * @param pattern The pattern which is being searched in the text 
	 * @return The DFA.
	 */
	private static int[] getDFA(String pattern){
		
		int p = pattern.length();
		int[] dfa = new int[p];
		dfa[0] = 0;
		int longestPrefixIndex = 0;
		
		for(int i = 2; i < p; i++){
			
			//back-track
			while(longestPrefixIndex > 0 && pattern.charAt(longestPrefixIndex+1) != pattern.charAt(i)){
				longestPrefixIndex = dfa[longestPrefixIndex];
			}
			
			//match
			if(pattern.charAt(longestPrefixIndex+1) == pattern.charAt(i)){
				longestPrefixIndex++;
			}
			dfa[i] = longestPrefixIndex;
		}
		
		return dfa;
	}
	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the text: ");
		String text = sc.nextLine();
		System.out.println("Enter the pattern: ");
		String pattern = sc.nextLine();
		sc.close();
		
		int index = WildKMP.search(text, pattern);
		if(index != -1){
			System.out.println(index);
		}
		else{
			System.out.println("Couldn't find pattern in the text.");
		}
	}
}
