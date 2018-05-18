package com.github.tiger.test.lexicon;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.Graph;
import org.junit.Assert;
import org.junit.Test;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ansj分词
 */
public class AnsjTest {

    @Test
    public void testAnalysis() {
        String str = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我." +
                "我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!人力资源等";
        System.out.println(ToAnalysis.parse(str));

        String word1 = "这只皮靴号码大了。那只号码合适";
        String word2 = "这只皮靴号码不小，那只更合适";

        List<Term> terms1 = ToAnalysis.parse(word1).getTerms();
        List<Term> terms2 = ToAnalysis.parse(word2).getTerms();

        Set<String> termSet = new LinkedHashSet<>();
        terms1.forEach(term -> termSet.add(term.getName()));
        terms2.forEach(term -> termSet.add(term.getName()));
    }

    @Test
    public void testKeywordComputer() {
        KeyWordComputer kwc = new KeyWordComputer(20);
        String content = "一个黑客想要使其与SimpliSafe基站相通，" +
                "需要的只是约合250美元（175英镑）价值的硬件来打造一个微控制器，" +
                "然后再打上几百行代码。";
        Collection<Keyword> result = kwc.computeArticleTfidf(content);

        System.out.println(result);

        for (Object obj : result.toArray()) {
            Keyword keyword = (Keyword) obj;
            System.out.println(keyword.getName()
                    + "\t" + keyword.getFreq()
                    + "\t" + keyword.getScore());
        }
    }

    @Test
    public void testDicLibrary() {
        DicLibrary.insert(DicLibrary.DEFAULT, "互联网",
                "industry", 1000);
        Result parse = DicAnalysis.parse("这是用户自定义词典互联网增加新词的例子");
        System.out.println(parse);
        boolean flag = false;
        for (Term term : parse) {
            flag = flag || "互联网".equals(term.getName());
        }
        Assert.assertTrue(flag);
    }

    @Test
    public void testGraph() {
        Graph graph = new Graph("这是用户自定义词典互联网增加新词的例子");
        graph.printGraph();
    }

}
