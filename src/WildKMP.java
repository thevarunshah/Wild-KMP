import java.util.Scanner;

/**
 * A modified version of the original Knuth-Morris-Pratt substring search algorithm which allows wildcards.
 *
 * @author Varun Shah
 */
public class WildKMP {

    /**
     * Given some text and a pattern, it searches for the first instance of the pattern in the text.<br>
     * An asterisk (*) in the pattern tells the algorithm to match on any character at that location in the text.
     *
     * @param text    The text to be searched
     * @param pattern The pattern to search for in the text
     * @return The starting index of the pattern in the text. If not found, -1 is returned.
     */
    public static int search(String text, String pattern) {

        final int textLength = text.length();
        final int patternLength = pattern.length();
        if (patternLength > textLength) {
            return -1;
        }

        // create dfa
        final int[] prefixTable = getDFA(pattern);

        int matchLength = 0;
        Character wildLetter = null;
        for (int i = 0; i < textLength; i++) {
            // back-track on failure
            while (matchLength > 0 && pattern.charAt(matchLength) != text.charAt(i)) {
                // check if fail was due to wildcard
                if (pattern.charAt(matchLength) == '*') {
                    // if initial wildcard, set it
                    if (wildLetter == null) {
                        wildLetter = text.charAt(i);

                        // loop-back with KMP - double check already matched pattern
                        final int kmpValue = search(text.substring(i - matchLength, i),
                                                                    pattern.substring(0, matchLength));
                        if (kmpValue != 0) {
                            matchLength = 0; // reset match
                        } else if (pattern.charAt(matchLength - 1) == '*') {
                            wildLetter = text.charAt(i - 1); // reset wildcard
                        }
                        break;
                    } else if (wildLetter == text.charAt(i)) {
                        break; // wildcard matches
                    }
                }

                matchLength = prefixTable[matchLength - 1]; // fall-back
                wildLetter = null;

                // edge case - match previous seen for proper shift
                if (matchLength == 0 && pattern.charAt(matchLength + 1) == '*'
                        && text.charAt(i - 1) == pattern.charAt(matchLength)) {
                    matchLength++;
                }
            }

            // match or wildcard
            if (pattern.charAt(matchLength) == text.charAt(i) || pattern.charAt(matchLength) == '*') {
                // wildcard
                if (pattern.charAt(matchLength) == '*') {
                    if (wildLetter == null) {
                        wildLetter = text.charAt(i); // set wildcard
                    } else if (wildLetter != text.charAt(i)) {
                        // doesn't match current wildcard
                        if (matchLength == 1) {
                            wildLetter = text.charAt(i); // edge case, new wildcard
                            continue;
                        }
                        // reset
                        wildLetter = null;
                        matchLength = 0;
                        continue;
                    }
                } else {
                    wildLetter = null; // reset wildcard
                }
                matchLength++; // matched
            }

            // found the pattern
            if (matchLength == patternLength) {
                return i - (patternLength - 1);
            }
        }

        // couldn't find the pattern
        return -1;
    }

    /**
     * Creates the DFA for the KMP algorithm.
     *
     * @param pattern The pattern which is being searched in the text
     * @return The DFA.
     */
    private static int[] getDFA(String pattern) {

        final int length = pattern.length();
        final int[] dfa = new int[length];
        dfa[0] = 0;
        int longestPrefixIndex = 0;

        for (int i = 2; i < length; i++) {
            // back-track
            while (longestPrefixIndex > 0 && pattern.charAt(longestPrefixIndex + 1) != pattern.charAt(i)) {
                longestPrefixIndex = dfa[longestPrefixIndex];
            }

            // match
            if (pattern.charAt(longestPrefixIndex + 1) == pattern.charAt(i)) {
                longestPrefixIndex++;
            }
            dfa[i] = longestPrefixIndex;
        }
        return dfa;
    }

    public static void main(String[] args) {

        final Scanner sc = new Scanner(System.in);
        System.out.println("Enter the text: ");
        final String text = sc.nextLine();
        System.out.println("Enter the pattern: ");
        final String pattern = sc.nextLine();
        sc.close();

        final int index = search(text, pattern);
        if (index != -1) {
            System.out.println(index);
        } else {
            System.out.println("Couldn't find pattern in the text.");
        }
    }
}
