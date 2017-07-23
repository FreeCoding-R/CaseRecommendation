package preData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CasesFromPython {
    public static List<Integer> getIndex(File file){
        String cp = "python/caseRecommend.py";
        String data = "";
        try {
            Process process = Runtime.getRuntime().exec("python3 " + cp+" "+file);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
                data+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        char ch[] = data.toCharArray();
        boolean endFlag = false;
        List<Integer> result = new ArrayList<>();

        for(int i = 1; i<ch.length; i++){
            if(ch[i] == ']'){
                endFlag = true;
                break;
            }
            if('0'<=ch[i] && ch[i] <='9') {
                result.add(ch[i] - 48);
            }
        }
        return result;
    }

}

