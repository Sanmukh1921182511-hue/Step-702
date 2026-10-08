
import java.util.*;

class MergingTwoTokenQueues {
    static ArrayList<Integer> mergeTokens(
            ArrayList<Integer> counterA,
            ArrayList<Integer> counterB) {

        ArrayList<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < counterA.size() && j < counterB.size()) {
            if (counterA.get(i) <= counterB.get(j)) {
                result.add(counterA.get(i));
                i++;
            } else {
                result.add(counterB.get(j));
                j++;
            }
        }

        while (i < counterA.size()) {
            result.add(counterA.get(i++));
        }

        while (j < counterB.size()) {
            result.add(counterB.get(j++));
        }

        return result;
    }

    public static void main(String[] args) {
        ArrayList<Integer> a =
            new ArrayList<>(Arrays.asList(3, 8, 15, 20));
        ArrayList<Integer> b =
            new ArrayList<>(Arrays.asList(5, 8, 12));

        System.out.println(mergeTokens(a, b));
    }
}
