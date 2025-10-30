import java.util.Arrays;

public class SolutionTest {
    public static void main(String[] args) {
        Solution s = new Solution();

        // 예제 1
        {
            String[] orders = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"};
            int[] course = {2, 3, 4};
            String[] result = s.solution(orders, course);
            System.out.println("EX1 = " + Arrays.toString(result));
        }

        // 예제 2
        {
            String[] orders = {"ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"};
            int[] course = {2, 3, 5};
            String[] result = s.solution(orders, course);
            System.out.println("EX2 = " + Arrays.toString(result));
        }

        // 예제 3
        {
            String[] orders = {"XYZ", "XWY", "WXA"};
            int[] course = {2, 3, 4};
            String[] result = s.solution(orders, course);
            System.out.println("EX3 = " + Arrays.toString(result));
        }
    }
}
