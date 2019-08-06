package qa.utils;

public class EditDistance {
    public static void main(String[]args){
        int a=getDistance("请问桑切斯·瓦特在2012年有多少场比赛？","2012");
        double d=getEditSim("请问桑切斯·瓦特在2012年有多少场比赛？","2012");
        System.out.println(d);
        System.out.println(a);
    }
    public static double getEditSim(String s1,String s2){
    	s1=s1.replaceAll(" ", "").toLowerCase();
    	s2=s2.replaceAll(" ", "").toLowerCase();
    	int eidtDis=getDistance(s1, s2);
    	int maxlen=s1.length()>s2.length()?s1.length():s2.length();
    	double edit=1.0*eidtDis/maxlen;
    	return 1-edit*edit;
    }
    public static int getDistance(String s1,String s2){
         char[] str1=s1.toCharArray();
         char[] str2=s2.toCharArray();
         int n = str1.length;
         int m = str2.length;
         int[][] C = new int[n+1][m+1];
         int i, j, x, y, z;
         for (i = 0; i <= n; i++)
             C[i][0] = i;
         for (i = 1; i <= m; i++)
             C[0] [i] = i;
         for (i = 0; i < n; i++)
             for (j = 0; j < m; j++)
             {
                 x = C[i][j + 1] + 1;
                 y = C[i + 1][ j] + 1;
                 if (str1[i] == str2[j])
                     z = C[i][ j];
                 else
                     z = C[i][ j] + 1;
                 
                 C[i + 1][ j + 1] = Math.min(Math.min(x, y), z);
             }
         return C[n][ m];
     }

}
