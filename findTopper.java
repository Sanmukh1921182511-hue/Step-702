public class findTopper {
    static int[] findTopp(int[][] marks) {
        int maxTotal = -1;
        int rowIndex = 0;

        for (int i = 0; i < marks.length; i++) {
            int total = 0;

            for (int j = 0; j < marks[i].length; j++) {
                total += marks[i][j];
            }

            if (total > maxTotal) {
                maxTotal = total;
                rowIndex = i;
            }
        }

        return new int[]{rowIndex, maxTotal};
    }

    public static void main(String[] args) {
        int[][] marks = {
            {78, 85, 90},
            {88, 92, 79},
            {65, 70, 95}
        };

        int[] result = findTopp(marks);
        System.out.println("(" + result[0] + ", " + result[1] + ")");
    }
}

