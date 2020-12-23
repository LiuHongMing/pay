package com.github.tiger.test.java.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(JUnit4.class)
public class RegexTest {

    @Test
    public void testRegex()  {
        String regex = "\\$\\!\\{[model|user]+\\.client(C\\.[userName|desireJd[|SubType|Industry]]+)?\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("@@@@@$!{model.client}#####$!{user.client}%%%%%$!{model.clientC.userName}*****$!{model.clientC.desireJdSubType}>>>>>$!{model.clientC.desireJdIndustry}");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

}
