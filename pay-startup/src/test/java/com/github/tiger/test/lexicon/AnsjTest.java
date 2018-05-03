package distributed.crawler.extraction;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.recognition.impl.NatureRecognition;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * ansj分词
 */
public class AnsjTest {

    @Test
    public void testAnalysis() {
//        String str = "欢迎使用ansj_seg,(ansj中文分词)" +
//                "在这里如果你遇到什么问题都可以联系我." +
//                "我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!";
        String str = "软件/互联网开发/系统集成";
        System.out.println(BaseAnalysis.parse(str));
        System.out.println(ToAnalysis.parse(str));
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
    public void testNature() {

    }


}
