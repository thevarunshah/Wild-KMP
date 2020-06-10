import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for WildKMP.
 *
 * @author Varun Shah
 */
public class WildKMPTest {

    @Test
    public void testAll() {
        assertEquals(0, WildKMP.search("ACGT", "ACGT"));
        assertEquals(4, WildKMP.search("ACGTACAT", "ACAT"));
        assertEquals(0, WildKMP.search("ACGTACAT", "ACGT"));
        assertEquals(2, WildKMP.search("AAAAAT", "AAAT"));
        assertEquals(8, WildKMP.search("ACAT ACGACACAGT", "ACACAGT"));
        assertEquals(0, WildKMP.search("ACGTACAT", "AC*T"));
        assertEquals(4, WildKMP.search("AUGEHIGE", "H*GE"));
        assertEquals(0, WildKMP.search("AUGEHIGE", "*UGE"));
        assertEquals(4, WildKMP.search("AUGEHIGE", "HIG*"));
        assertEquals(0, WildKMP.search("HUGEHUGS", "HUG*"));
        assertEquals(-1, WildKMP.search("HUGEHUGS", "HIG*"));
        assertEquals(6, WildKMP.search("HUGEAFHUGEEE", "HU*E*E"));
        assertEquals(5, WildKMP.search("HOOLIHUUGEE", "H**GE"));
        assertEquals(0, WildKMP.search("HUUGE", "H**GE"));
        assertEquals(-1, WildKMP.search("HINGE", "H**GE"));
        assertEquals(-1, WildKMP.search("ACCCABABB", "A**B"));
        assertEquals(10, WildKMP.search("HIIIHINGGEHUUUGE", "H***GE"));
        assertEquals(2, WildKMP.search("AAACCBAAB", "A**B"));
        assertEquals(-1, WildKMP.search("AAACCBAAB", "***B"));
        assertEquals(-1, WildKMP.search("AAACCBAAB", "B***"));
        assertEquals(6, WildKMP.search("AAAACCABBBB", "A***B"));
        assertEquals(0, WildKMP.search("HUUGEE", "H**G**"));
        assertEquals(6, WildKMP.search("HUUUGEHUUGEE", "H**G**"));
        assertEquals(1, WildKMP.search("HUUUGEHUUGEE", "***"));
        assertEquals(5, WildKMP.search("AAABBAAABBB", "AAA***"));
        assertEquals(-1, WildKMP.search("HUUGGGCCB", "H**G**B"));
        assertEquals(3, WildKMP.search("AAACCCBAAB", "***B**"));
        assertEquals(8, WildKMP.search("AAACCCBABBBBCC", "***B**"));
        assertEquals(3, WildKMP.search("AACAAT", "A*T"));
        assertEquals(3, WildKMP.search("AAAAAT", "A*T"));
        assertEquals(9, WildKMP.search("AAAAAACCDAAAAAAD", "A**A**D"));
        assertEquals(-1, WildKMP.search("babcacabca", "aa*c"));
    }
}
