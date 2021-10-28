import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/** CITE ---Received help from Cyan Bastiaans. Discussed data structures.
 * Used hashcode method from stack-overflow. */

public class ECHashStringSet extends BSTStringSet {
    public ArrayList<LinkedList<String>> H;
    public int items;
    public int buckets;

    public ECHashStringSet() {
        H = new ArrayList<>(5);
        items = 0;
        buckets = 5;
        int i = 0;
        while (i < buckets) {
            H.add(null);
            i++;
        }
    }

    public int hasher(String s) {
        int hashed = 3;
        for (int i = 0; i < s.length(); i++) {
            hashed = hashed * 29 + s.charAt(i);
        }
        return compressor(hashed);
    }

    public int compressor(int i) {
        if (i < 0) {
            i = -i;
        }
        return i % buckets;
    }

    public void put(String s) {
        items++;
        if (items / buckets >= 5) {
            ArrayList<LinkedList<String>> R = new ArrayList<>(H.size() * 2);
            buckets = H.size() * 2;
            null_add(R);
            for (int i = 0; i < H.size(); i++) {
                if (H.get(i) != null) {
                    for (int j = 0; j < H.get(i).size(); j++) {
                        String hash_index = H.get(i).get(j);
                        int hash_s = hasher(hash_index);
                        if (R.get(hash_s) == null) {
                            LinkedList<String> L = new LinkedList<>();
                            L.add(hash_index);
                            R.set(hash_s, L);
                        } else {
                            R.get(hash_s).add(hash_index);
                        }
                    }
                }
            }
            H = R;
        }
        int hash_s = hasher(s);
        if (!H.contains(s)) {
            if (H.get(hash_s) == null) {
                LinkedList L = new LinkedList();
                L.add(s);
                H.set(hash_s, L);
            } else {
                H.get(hash_s).add(s);
            }
        }
    }

    public void null_add(ArrayList L) {
        int i = 0;
        while (i < buckets) {
            L.add(null);
            i++;
        }
    }

    public boolean contains(String s) {
        int hash_s = hasher(s);
        if (H.get(hash_s) == null) {
            return false;
        }
        if (H.get(hash_s).contains(s)) {
            return true;
        }
        return false;
    }

    public List<String> asList() {
        List<String> L = new ArrayList<>();
        for (int i = 0; i < H.size(); i++) {
            if (H.get(i) != null) {
                for (int j = 0; j < H.get(i).size(); j++) {
                    L.add(H.get(i).get(j));
                }
            }

        }
        return L;
    }
}
