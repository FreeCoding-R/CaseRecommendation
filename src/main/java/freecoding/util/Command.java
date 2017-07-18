package freecoding.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by zjy on 2017/7/17.
 * 通过java代码执行控制台命令的工具类
 */
public class Command {
    /**
     * 执行命令，一定要注意操作系统环境，一般windows下运行执行的是cmd命令，mac和linux下运行是shell命令，
     * eg.  Command.exeCmd("ping www.baidu.com");
     * @param commandStr 被执行的命令
     * @return 控制台的输出
     * @apiNote 暂时还没处理编码格式的问题，默认按utf-8的格式读取输出
     */
    public static String exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "no output";
    }

    public static void main(String[] args) {
        String commandStr = "pwd";
        //String commandStr = "ipconfig";
        Command.exeCmd(commandStr);
    }
}
