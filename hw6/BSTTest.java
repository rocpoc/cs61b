import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
public class BSTTest {

    @Test
    public void testContains() {
        BSTStringSet B = new ECHashStringSet();
        B.put("A");
        B.put("B");
        B.put("C");
        B.put("D");
        B.put("Z");
        B.put("R");
        B.put("Hello");
        B.put("World");
        B.put("You");
        B.put("Are");
        B.put("Getting");
        B.put("Close to");
        B.put("The");
        B.put("Load Factor!");
        B.put("random");
        B.put("words");
        B.put("everywhere");
        B.put("i");
        B.put("am");
        B.put("getting");
        B.put("bored");
        B.put("this isnt fun");

        assertEquals(true, B.contains("A"));
        assertEquals(true, B.contains("B"));
        assertEquals(true, B.contains("C"));
        assertEquals(true, B.contains("D"));
        assertEquals(false, B.contains("FUCK"));
        assertEquals(true, B.contains("R"));
        assertEquals(true, B.contains("Hello"));
        assertEquals(true, B.contains("World"));
        assertEquals(true, B.contains("You"));
        assertEquals(true, B.contains("Are"));
        assertEquals(true, B.contains("Getting"));
        assertEquals(true, B.contains("Close to"));
        assertEquals(true, B.contains("The"));
        assertEquals(true, B.contains("Load Factor!"));
        assertEquals(true, B.contains("random"));
        assertEquals(true, B.contains("words"));
        assertEquals(true, B.contains("everywhere"));
        assertEquals(true, B.contains("i"));
        assertEquals(true, B.contains("am"));
        assertEquals(true, B.contains("getting"));
        assertEquals(true, B.contains("bored"));
        assertEquals(true, B.contains("this isnt fun"));
    }


    @Test
    public void testList() {
        List<String> L = new ArrayList<String>(){};
        BSTStringSet B = new ECHashStringSet();
        L.add("I");
        L.add("My bed is a mess");
        L.add("Guacamole");
        L.add("Am");
        L.add("Zoo");
        L.add("Holy Moly");
        L.add("List");
        L.add("Berkeley");
        L.add("This is a test");
        B.put("I");
        B.put("Am");
        B.put("Berkeley");
        B.put("List");
        B.put("Zoo");
        B.put("Holy Moly");
        B.put("Guacamole");
        B.put("This is a test");
        B.put("My bed is a mess");
        assertEquals(L, B.asList());
    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ECHashStringSetTest.class));

    }
}