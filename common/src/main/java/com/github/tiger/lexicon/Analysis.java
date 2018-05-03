package com.github.tiger.pay.common.lexicon.analysis;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.domain.TermNature;
import org.ansj.domain.TermNatures;
import org.ansj.recognition.impl.DicRecognition;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.util.TermUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuhongming
 */
public class Analysis {

    static {
        Library.loadDicLibrary();
    }

    /**
     * 自定义分词
     *
     * @param content 待分词内容
     * @return
     */
    public static List<String> doDicAnalysis(String content) {
        Result result = DicAnalysis.parse(content).recognition(new NewTermRecognition());
        List<Term> terms = result.getTerms();
        List<String> ret = new ArrayList<>();
        for (Term term : terms) {
            // 当前词
            String name = term.getName();
            // 词性
            String natureStr = term.getNatureStr();
            ret.add(String.format("%s=%s", name, natureStr));
        }
        return ret;
    }

    static class NewTermRecognition extends DicRecognition {

        @Override
        public void recognition(Result result) {
            List<Term> terms = new ArrayList<>();

            List<Term> newTerms = new ArrayList<>();
            List<Term> oldTerms = result.getTerms();
            for (Term term : oldTerms) {
                String natureStr = term.getNatureStr();
                if ("ind".equals(natureStr)) {
                    newTerms.add(term);
                } else {
                    terms.add(term);
                }
            }

            int pair = 2;

            for (int i = 1, size = newTerms.size();
                 i <= size; i++) {
                if (i % pair == 0) {
                    Term term = TermUtil.makeNewTermNum(
                            newTerms.get(i - 2),
                            newTerms.get(i - 1),
                            new TermNatures(
                                    new TermNature(
                                            "com-ind",
                                            1000)));
                    terms.add(term);
                }
            }

            result.setTerms(terms);
        }
    }

    public static void main(String[] args) {
        String content = "北京 互联网/电子商务,文字媒体/出版/法律,财务/审计/税务";
        List<String> ret = Analysis.doDicAnalysis(content);
        System.out.println(ret);
    }
}
