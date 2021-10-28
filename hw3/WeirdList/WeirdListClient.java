/** Functions to increment and sum the elements of a WeirdList. */
class WeirdListClient {

    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        Adder A = new Adder(n);
        return L.map(A);
    }

    /** Return the sum of the elements in L. */
    static int sum(WeirdList L) {
        Summer S = new Summer();
        L.map(S);
        return S.n;
    }

    /* As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     */

    private static class Adder implements IntUnaryFunction {
        int n;

        public Adder(int n) {
            this.n = n;
        }

        @Override
        public int apply(int x) {
            return x + this.n;
        }
    }


    private static class Summer implements IntUnaryFunction {
        int n = 0;
        public Summer() {} {
        }

        @Override
        public int apply(int x) {
            this.n += x;
            return n;
        }
    }


}
