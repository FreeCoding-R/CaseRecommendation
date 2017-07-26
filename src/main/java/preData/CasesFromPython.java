package preData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CasesFromPython {
    public static List<Integer> getIndex(File file){
        String cp = "src/main/python2.0/query.py";
        String data = "";
        try {
            System.out.println("start");
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

        List<Integer> result = new ArrayList<>();
        data = data.substring(1, data.length()-1);
        String dataset[] = data.split(", ");
        for(String item:dataset){
            int num = Integer.parseInt(item);
            result.add(num);
        }

        return result;
    }

}

