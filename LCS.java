import java.util.*;

public class LCS {
   public static void main(String[] args) {
      String s1 = "AGGTAB";
      String s2 = "GXTXAYB";
      System.out.println(lcs(s1,s2));
      
   }
   
   public static int lcs(String s1, String s2) {
      int n1 = s1.length();
      int n2 = s2.length();
      if (n1 == 0 || n2 == 0) {
         return 0;
      }

      if (s1.charAt(n1 - 1) == s2.charAt(n2 - 1)) {
         return 1 + lcs(s1.substring(0, n1 - 1), s2.substring(0, n2 - 1));
      } else {
         return Math.max(lcs(s1.substring(0, n1 - 1), s2),
               lcs(s1, s2.substring(0, n2 - 1)));
      }
   }
}