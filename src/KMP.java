import java.util.Scanner;

public class KMP {

	public static int search(String text, String pattern){
		
		int t = text.length();
		int p = pattern.length();
		//if pattern length is longer than text length
		if(p > t){
			return -1;
		}
		
		//create dfa
		DFA prefixTable = new DFA(pattern);
		
		int matchLength = 0;
		for(int i = 0; i < t; i++){
			
			boolean reset = false;
			//back-track on failure
			while(matchLength > 0 && pattern.charAt(matchLength) != text.charAt(i)){
				//check if fail was due to wildcard
				if(pattern.charAt(matchLength) == '*'){
					//loop-back with KMP - double check already matched pattern
					if(matchLength > 0 && reset){
						int kmpValue = KMP.search(text.substring(i-matchLength, i), pattern.substring(0, matchLength));
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
				
				matchLength = prefixTable.table[matchLength-1]; //fall-back
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
	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the text: ");
		String text = sc.nextLine();
		System.out.println("Enter the pattern: ");
		String pattern = sc.nextLine();
		sc.close();
		
		int index = KMP.search(text, pattern);
		if(index != -1){
			System.out.println(index);
		}
		else{
			System.out.println("Couldn't find pattern in the text.");
		}
	}
}
