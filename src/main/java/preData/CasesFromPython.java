package preData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CasesFromPython {
    static String pythonV;

    static{
        if (System.getProperty("os.name").startsWith("Windows")){
            pythonV = "python";
        }else{
            pythonV = "python3";
        }
    }

    public static List<Integer> getSecondIndex(int location){
        String cp = "src/main/python2.0/query2.py";
        String data = "";
        Process process;
        try {
            process = Runtime.getRuntime().exec(pythonV+" " + cp+" "+location);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null){
                data+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(data);

        List<Integer> result = new ArrayList<>();
        data = data.substring(1, data.length()-1);
        String dataset[] = data.split(", ");
        for(String item:dataset){
            int num = Integer.parseInt(item);
            result.add(num);
        }

        return result;
    }

    public static List<Integer> getIndex(File file){
        String cp = "src/main/python2.0/query.py";
        String data = "";
        try {
            Process process = Runtime.getRuntime().exec(pythonV+" " + cp+" "+file);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null){
                data+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(data);
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

