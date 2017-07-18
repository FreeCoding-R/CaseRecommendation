package freecoding.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zjy on 2017/7/18.
 */
public class CommandTest {
    /**
     * 一般的命令，截获标准输入流的信息
     */
    @Test
    public void normalCommandTest(){
        String output=Command.exeCmd("ping www.baidu.com");
        Assert.assertTrue(output.length()>0);
    }

    /**
     * 截获标准错误流的信息
     */
    @Test
    public void errorStreamCommandTest(){
        String output=Command.exeCmd("java -version");
        Assert.assertTrue(output.length()>0);
    }

    /**
     * 无输出的命令
     */
    @Test
    public void noOutputCommandTest(){
        String cmd = "java -cp "+getClass().getClassLoader().getResource("bin").getPath()+" NoOutputProgram";
        Assert.assertEquals(Command.exeCmd(cmd),"");
    }

    /**
     * 不存在的命令
     */
    @Test
    public void errorCommandTest(){
        System.out.println("try to exec a wrong command.");
        Assert.assertEquals(Command.exeCmd("gwggw"),"Error");
    }
}
