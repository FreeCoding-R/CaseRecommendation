package preData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class test {
    public static void main(String args[]){
        String cp = "src/main/python2.0/query.py";
        String data = "";
        String file = "userFiles/test.xml";
        try {
            Process process = Runtime.getRuntime().exec("python3"+" " + cp+" "+file);
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
    }
}
