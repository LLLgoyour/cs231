package labs.lab1.src;

import java.util.Random;

public class Shuffle {
    public static void main(String[] args) {
        ArrayList<Integer> arr0 = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            // add a random number between 0 and 99
            arr0.add(random.nextInt(99));
            // print the random number on different lines
            System.out.println(arr0.get(i));
        }
        for (int i = 0; i < 10; i++) {
            // pick a random index strictly less than current size
            int idx = random.nextInt(arr0.size());
            // print the removed value
            Integer removed = arr0.remove(idx);
            System.out.print("removed=" + removed + " remaining=");
            // print remaining elements on the same line
            System.out.print(arr0.toString());
            System.out.println();
        }
    }
}
