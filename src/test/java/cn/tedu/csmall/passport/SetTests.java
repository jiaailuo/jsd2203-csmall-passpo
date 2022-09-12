package cn.tedu.csmall.passport;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SetTests {
    @Test
    public void test(){
        Set<String> set=new LinkedHashSet<>();
        set.add("1");
        set.add("4");
        set.add("2");
        set.add("3");
        set.add("5");

        for (String s : set) {
            System.out.println(s);
        }
    }
}
