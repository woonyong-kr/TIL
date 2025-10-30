import java.util.*;

public class MenuRenewalApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // orders 입력
        int n = Integer.parseInt(sc.nextLine().trim());
        String[] orders = new String[n];
        for (int i = 0; i < n; i++) {
            orders[i] = sc.nextLine().trim();
        }

        // course 입력
        int m = Integer.parseInt(sc.nextLine().trim());
        int[] course = new int[m];
        {
            String[] parts = sc.nextLine().trim().split("\\s+");
            for (int i = 0; i < m; i++) {
                course[i] = Integer.parseInt(parts[i]);
            }
        }

        Solution s = new Solution();
        String[] result = s.solution(orders, course);

        // 출력
        System.out.println("=== RESULT ===");
        for (String r : result) {
            System.out.println(r);
        }
    }
}
