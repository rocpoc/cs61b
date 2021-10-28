import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a BST based String Set.
 * @author Rocky Lubbers
 */
public class BSTStringSet implements StringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        root = null;
    }

    @Override
    public void put(String s) {
        root = putHelper(s, root);
    }
    private Node putHelper(String s, Node n) {
        if (n == null) {
            return new Node(s);
        } else if (s.compareTo(n.s) == 0) {
            return n;
        } else if (s.compareTo(n.s) < 0) {
            n.left = putHelper(s, n.left);
        } else {
            n.right = putHelper(s, n.right);
        }
        return n;
    }

    @Override
    public boolean contains(String s) {
        return containsHelper(s, root);
    }
    private boolean containsHelper(String sp, Node n) {
        if (n == null) {
            return false;
        }
        if (n.s.equals(sp)) {
            return true;
        }
        return containsHelper(sp, n.left) | containsHelper(sp, n.right);
    }


    @Override
    public List<String> asList() {
        asListHelper(root, L);
        return L;
    }

    public void asListHelper(Node n, List L) {
        if (n == null)
            return;
        asListHelper(n.left, L);
        L.add(n.s);
        asListHelper(n.right, L);
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;
        /** Creates a Node containing SP. */
        public Node(String sp) {
            s = sp;
        }

    }

    /** Root node of the tree. */
    private Node root;

    List<String> L = new ArrayList<>();
}
