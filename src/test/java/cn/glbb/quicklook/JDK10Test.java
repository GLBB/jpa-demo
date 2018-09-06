package cn.glbb.quicklook;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDK10Test {
    @Test
    public void test1(){

        var ss = "111";
        System.out.println(ss);
        System.out.println(ss instanceof String);

        var list = new ArrayList<String>();
        list.add("111");
        list.add("afd");
        System.out.println(list);
        System.out.println(list instanceof ArrayList);

        var a = new Integer(4);

        var map = new HashMap<>();

        var double1 = new Double(4);

        Integer i = new Integer(4);

        System.out.println("-----");
        Integer kk = 4;
        System.out.println(kk+"    ");

        boolean kkk = kk instanceof Integer;



    }
}
