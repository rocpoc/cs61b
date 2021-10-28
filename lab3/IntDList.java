
public class IntDList {

    private DNode _front, _back;

    public IntDList() {
        _front = _back = null;
    }

    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    public int getFront() {
        return _front._val;
    }

    /** Returns the last item in the IntDList. */
    public int getBack() {
        return _back._val;
    }

    /** Return value #I in this list, where item 0 is the first, 1 is the
     *  second, ...., -1 is the last, -2 the second to last.... */
    public int get(int i) {
        int count = 0;
        DNode front = _front;
        DNode back = _back;

        if (front == null || back == null) {
            return 0;
        }

        if (i == 0) {
            return front._val;
        }

        if (i < 0){
            while (count > i+1){
                count --;
                back = back._prev;
            }
            return back._val;
        }

        else {
            while (count < i) {
                count ++;
                front = front._next;
            }
            return front._val;
        }
    }

    /** The length of this list. */
    public int size() {
        int counter = 0;
        DNode copy = _front;
        while (copy != null) {
            counter ++;
            copy = copy._next;
        }
        return counter;
    }

    /** Adds D to the front of the IntDList. */
    public void insertFront(int d) {
        DNode nufront = new DNode(d);

        if (_front == null){
            _front = nufront;
            _back = _front;
        }
        else {
            _front._prev = nufront;
            _front._prev._next = _front;
            _front = nufront;

        }
    }

    /** Adds D to the back of the IntDList. */
    public void insertBack(int d) {
        DNode nufront = new DNode(d);

        if (_front == null){
            _front = nufront;
            _back = _front;
        }
        else {
            _back._next = nufront;
            _back._next._prev = _back;
            _back = nufront;

        }
    }

    /** Removes the last item in the IntDList and returns it.
     * This is an extra challenge problem. */
    public int deleteBack() {
        return 0;   // Your code here

    }

    /** Returns a string representation of the IntDList in the form
     *  [] (empty list) or [1, 2], etc. 
     * This is an extra challenge problem. */
    public String toString() {
        return null;   // Your code here

    }

    /* DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information! */
    private static class DNode {
        protected DNode _prev;
        protected DNode _next;
        protected int _val;

        private DNode(int val) {
            this(null, val, null);
        }

        private DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
