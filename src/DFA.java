
public class DFA {

	public int[] table = null;
	
	public DFA(String pattern){
		
		int p = pattern.length();
		table = new int[p];
		table[0] = 0;
		int longestPrefixIndex = 0;
		
		for(int i = 2; i < p; i++){
			
			//back-track
			while(longestPrefixIndex > 0 && pattern.charAt(longestPrefixIndex+1) != pattern.charAt(i)){
				longestPrefixIndex = table[longestPrefixIndex];
			}
			
			//match
			if(pattern.charAt(longestPrefixIndex+1) == pattern.charAt(i)){
				longestPrefixIndex++;
			}
			table[i] = longestPrefixIndex;
		}
	}
	
	public void printTable(){
		
		for(int x = 0; x < table.length; x++){
			System.out.print(table[x] + "\t");
		}
		System.out.println();
	}
}
