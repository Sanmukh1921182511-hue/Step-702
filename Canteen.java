import java.util.*;
public class Canteen {
    static String[] mostPopular(String[] orders) {
        HashMap<String, Integer> map = new HashMap<>();

        for (String item : orders) {
            map.put(item, map.getOrDefault(item, 0) + 1);
        }

        String best = orders[0];
        int maxCount = 0;

        for (String item : orders) {
            if (map.get(item) > maxCount) {
                maxCount = map.get(item);
                best = item;
            }
        }

        return new String[]{best, String.valueOf(maxCount)};
    }

    public static void main(String[] args) {
        String[] orders = {
            "dosa", "idli", "vada", "dosa",
            "idli", "dosa", "tea"
        };

        String[] result = mostPopular(orders);
        System.out.println("(" + result[0] + ", " + result[1] + ")");
    }
}

