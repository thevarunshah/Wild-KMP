# Wild KMP
The Knuth-Morris-Pratt substring search algorithm with wildcards.

# What is it?
This is a modification to the implementation of the [Knuth-Morris-Pratt substring search algorithm](https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm).<br>
I've added the ability to include wildcards in the pattern, represented by an asterisk: <b>*</b><br>
These wildcards allow the algorithm to match on any character in the text whenever it sees the wildcard in the pattern. 

# Implementation
The algorithm is implemented in <b>WildKMP.java</b> in the method <b>search</b>. <br>
Given the <b>text</b> and the <b>pattern</b>, the algorithm will return the starting index of the first instance of the pattern in the text if found. If the pattern is not in the text, it will return -1.

# Examples
<b>WildKMP.search(text, pattern)</b><br>
WildKMP.search("WildKMP", "KMP") //returns 4<br>
WildKMP.search("foobar", "f\*\*") //returns 0<br>
WildKMP.search("foocar", "foo\*ar") //returns 0<br>
WildKMP.search("pattern in text", "t\*rn") //returns 3<br>
WildKMP.search("pattern in text", "te\*t") //returns 11<br>
WildKMP.search("pattern not in text", "t\*st") //returns -1<br>
WildKMP.search("match on anything", "\*\*\*") //returns 0<br>
WildKMP.search("find the first 3 letter word", " \*\*\* ") //returns 4

# Complexity
The complexity of the original KMP algorithm is affected, but not significantly. <br>
Instead of the original <b>O(m + n)</b>, where <b>m</b> is the length of the text and <b>n</b> is the length of the pattern, there is an additional cost of k+1 based on the length of the string, where <b>k</b> is the number of wildcards in the pattern.<br>
This makes the new run-time <b>O((k+1)m + n)</b>.
