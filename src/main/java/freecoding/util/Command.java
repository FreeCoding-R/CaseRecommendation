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
        String output="";
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            //不知道为什么windows下获取的系统默认编码格式是UTF-8，所以只能手动设置编码格式
            br = new BufferedReader(new InputStreamReader(p.getInputStream(),
                    System.getProperty("os.name").startsWith("Windows")?"GBK":"UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            output=sb.toString();

            //如果从标准输入流中获取不到输入，就从标准错误流中获取
            if("".equals(output)){
                br = new BufferedReader(new InputStreamReader(p.getErrorStream(),
                        System.getProperty("os.name").startsWith("Windows")?"GBK":"UTF-8"));
                line = null;
                sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                output=sb.toString();
            }

            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        return "Error";
    }
}
