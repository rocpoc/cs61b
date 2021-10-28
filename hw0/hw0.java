import java.util.List;    
import java.util.ArrayList;

public class hw0{

     public static void main(String []args){  
       	// int[] t = {45, 19, 1, 1000, 100, 2, 100000};
       	// max(t);
       	int[] s = {1, 2, 3};
       	threesum(s);
     }

     public static int max(int[] a){
     	int counter = 0;
     	int next = 1;
     	int max = a[counter];

     	while (next < a.length - 1) {
     		if (a[counter] <= a[next]) {
     			counter = next;
     			next ++;
     		} else {
     			next ++;
     		}      		
     	}
     	int new_max = a[counter];
     	System.out.println(new_max);
     	return new_max;
     }


     public static boolean threesum(int[] a){
        int[] firstlist = a;
     	for (int i = 0; i < a.length; i++) {
     		if (a[i] == 0) {
     			return true;
            }
            for (int n = 0; n < a.length; n++)
                firstlist[i] = (a[i] + a[n]);
        }
        // int[] finallist = firstlist;
        System.out.println(firstlist[0]);
        return false;
    }
 }


