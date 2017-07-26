package preData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CasesFromPython {
    public static List<Integer> getIndex(File file){
        String cp = "src/main/python2.0/query.py";
        String data = "";
        String pythonV = "";

        try {
            if (System.getProperty("os.name").startsWith("Mac")){
                pythonV = "python3";
            }else{
                pythonV = "python";
            }
            System.out.println("start");
            Process process = Runtime.getRuntime().exec(pythonV+" " + cp+" "+file);
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

