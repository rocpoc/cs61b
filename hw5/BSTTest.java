import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class BSTTest {

    @Test
    public void testContains() {
        BSTStringSet B = new BSTStringSet();
        B.put("A");
        B.put("B");
        B.put("C");
        B.put("D");
        B.put("Z");
        B.put("R");
        assertEquals(true, B.contains("A"));
        assertEquals(true, B.contains("B"));
        assertEquals(true, B.contains("C"));
        assertEquals(true, B.contains("D"));
        assertEquals(false, B.contains("FUCK"));
        assertEquals(true, B.contains("R"));
    }


    @Test
    public void testList() {
        List<String> L = new ArrayList<String>(){};
        BSTStringSet B = new BSTStringSet();
        L.add("Am");
        L.add("Berkeley");
        L.add("I");
        L.add("List");
        L.add("Zoo");

        B.put("I");
        B.put("Am");
        B.put("Berkeley");
        B.put("List");
        B.put("Zoo");

        assertEquals(L,B.asList());
    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(NybblesTest.class));
//        testContains();
//        testList();

    }
}