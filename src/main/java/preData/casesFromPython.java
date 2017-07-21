package preData;

import java.io.*;

public class casesFromPython {
    public static int[] getIndex(File file){
        String cp = "python/caseRecommend.py";
        try {
            Process process = Runtime.getRuntime().exec("python3 " + cp+" "+file);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] result = {1,2};
        return result;
    }

    public static void main(String args[]){
        String path = "/Users/loick/Desktop/卓越工程师/天津/samples";
        File dire = new File(path);
        File file = dire.listFiles()[1];
        System.out.println(file);
        getIndex(file);
    }
}
