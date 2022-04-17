/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
public class SetOfNaturalsTest {
    private SetOfNaturals setA;
    private SetOfNaturals setB;
    private SetOfNaturals setC;
    private SetOfNaturals setD;
    private SetOfNaturals setE;

    @BeforeEach
    public void setUp() {
        setA = new SetOfNaturals();
        setB = SetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});

        setC = new SetOfNaturals();
        for (int i = 5; i < 50; i++) {
            setC.add(i * 10);
        }
        setD = SetOfNaturals.fromArray(new int[]{30, 40, 50, 60, 10, 20});
        setE = new SetOfNaturals();
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = setE = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        setB.add(11);
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(7, setB.size(), "add: elements count not as expected.");
    }

    @Test
    public void testAddBadArray() {
        int[] elems = new int[]{10, 20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }


    @Test
    public void testIntersectForNoIntersection() {
        assertFalse(setA.intersects(setB), "no intersection but was reported as existing");

    }


    @Test
    public void testSize() {
        assertTrue(setE.size() == 0, "size: should be 0 on empty set");
        assertTrue(setB.size() == 6, "size: should contain number of elements");
    }

    @Test
    public void testAddNonNatural() {
        assertThrows(IllegalArgumentException.class, () -> setE.add(-30));
        assertThrows(IllegalArgumentException.class, () -> setE.add(5/12));
    }

    @Test
    public void testContains() {
        setE.add(14); setE.add(31);
        assertFalse(setE.contains(15));
        assertTrue(setE.contains(31));
    }
}
