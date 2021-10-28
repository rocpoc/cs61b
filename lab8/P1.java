import java.util.Scanner;
import java.util.ArrayList;

/** Collaboration with Cyan A-B */

public class P1 {

    public static void main(String... ignored){
        if (ignored.length > 0) {
            Scanner input = new Scanner(ignored[0]);
            countr(input);
        }
    }

    public static void countr(Scanner inp){
        ArrayList<Integer> spacelist = new ArrayList<>();
        int imgc = 1;
        outside:
        while (inp.hasNext()){
            int spcount = 0;
            while (inp.hasNext("X")) {
                inp.next();
            }
            while (inp.hasNext(" ")){
                spcount++;
                inp.next();
            }
            while (inp.hasNext("X")) {
                inp.next();
                if(inp.hasNextLine()) {
                    spacelist.add(spcount);
                    if (inp.nextLine().isEmpty()) {
                        process(spacelist, imgc);
                        break outside;
                    }
                    break;
                } else {
                    return;
                }
            }
        }
        countr(inp);
    }
    public static void process(ArrayList<Integer> L, int imgc){
        int min = L.get(0);
        for (int x : L){
            if (x < min){
                min = x;
            }
        }
        int answer = 0;
        for (int y : L){
            answer += y-min;
        }

        System.out.println("Image" + imgc +": " + answer );
    }
}
