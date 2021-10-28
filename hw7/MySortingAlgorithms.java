import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Distribution Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. CITE -- wikipedia insertionsort pseudocode. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 1; i < k; i++) {
                int index = array[i];
                int prev = i - 1;
                while (prev >=0 && array[prev] > index) {
                    array[prev+1] = array[prev];
                    prev = prev -1;
                }
                array[prev+1] = index;
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     * CITE - used Wikipedia selectionsort pseduocode.
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 0; i < k; i++) {
                int min = i;
                for (int j = i+1; j < k; j++) {
                    if (array[j] < array[min]) {
                        min = j;
                    }
                }
                if (min != i) {
                    swap(array, i,min);
                }
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Distribution Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class DistributionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME: to be implemented
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Distribution Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation. CITE - used Algolist.net as foundation for
     * algorithm design.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            quickSort(array, 0, k-1);
        }


        public void quickSort(int[] array, int l, int r) {
            int x = l, j = r;
            int temp;
            int pivot = array[l];
            while (x <= j) {
                while (array[x] < pivot) {
                    x++;
                }
                while (array[j] > pivot) {
                    j--;
                }
                if (x <= j) {
                    temp = array[x];
                    array[x] = array[j];
                    array[j] = temp;
                    x++;
                    j--;
                }
            }
            if (l < j)
                quickSort(array, l, j);
            if (x < r)
                quickSort(array, x, r);
        }


        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of k-bit numbers.  For
     * example, if you take k to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * k to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of k. */

    /**
     * LSD Sort implementation. Cite -- http://eddmann.com/ as foundation
     * for algorithm design
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            Queue<Integer>[] bux = new Queue[10];
            for (int i = 0; i < 10; i++) {
                bux[i] = new LinkedList<Integer>();
            }
            boolean s = false;
            int exp = 1;
            while(!s) {
                s = true;
                for (int i = 0; i < k; i++) {
                    int bucket = (a[i] / exp) % 10;
                    if (bucket > 0) {
                        s = false;
                    }
                    bux[bucket].add(a[i]);
                }
                exp = exp * 10;
                int i = 0;
                for (Queue<Integer> bucket : bux) {
                    while (!bucket.isEmpty()) {
                        a[i] = bucket.remove();
                        i++;
                    }
                }
                for (int j = 1; i < k; j++) {
                    if (a[j - 1] > a[j]) {
                        s = false;
                    }
                    else {
                        s = true;
                    }

                }

            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
