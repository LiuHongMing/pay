package com.github.tiger.lexicon;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.domain.TermNature;
import org.ansj.domain.TermNatures;
import org.ansj.recognition.Recognition;
import org.ansj.recognition.impl.DicRecognition;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.util.TermUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Result result = DicAnalysis.parse(content);
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

    /**
     * 过滤词性
     */
    public static Result filter(final String content, final String regex) {
        Result result = DicAnalysis.parse(content);

        result.recognition(new Recognition() {
            @Override
            public void recognition(Result result) {
                char delimiter = '#';

                StringBuilder natureBuilder = new StringBuilder();
                for (int i = 0; i < result.size(); i++) {
                    Term term = result.get(i);
                    String natureStr = term.getNatureStr();
                    if (i > 0) {
                        natureBuilder.append(delimiter).append(natureStr);
                    } else {
                        natureBuilder.append(natureStr);
                    }
                }

                String natureTarget = natureBuilder.toString();
                String natureRegex = regex;

                List<Term> terms = new ArrayList<>();

                Pattern pattern = Pattern.compile(natureRegex);
                Matcher matcher = pattern.matcher(natureTarget);
                if (matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();

                    boolean startDelimiter = start > 0
                            && natureTarget.charAt(start - 1) == delimiter;
                    boolean endDelimiter = (end > 0 && end < natureTarget.length())
                            && natureTarget.charAt(end) == delimiter;

                    boolean check = (start == 0 && endDelimiter)
                            || (startDelimiter && endDelimiter)
                            || (startDelimiter && end == natureTarget.length());
                    if (!check) {
                        return;
                    }

                    String headStr = natureTarget.substring(0, start);
                    String midStr = natureTarget.substring(start, end);
                    String tailStr = natureTarget.substring(end);

                    int head = 0, mid = 0, tail = 0;

                    if (StringUtils.isNoneEmpty(headStr)) {
                        for (char ch : headStr.toCharArray()) {
                            if (ch == delimiter) {
                                head++;
                            }
                        }
                    }

                    if (StringUtils.isNoneEmpty(tailStr)) {
                        for (char ch : tailStr.toCharArray()) {
                            if (ch == delimiter) {
                                tail++;
                            }
                        }
                    }

                    mid = result.size() - (head + tail);

                    for (int i = 0; i < head; i++) {
                        terms.add(result.get(i));
                    }

                    for (int i = head + mid; i < result.size(); i++) {
                        terms.add(result.get(i));
                    }

                    result.setTerms(terms);
                }
            }
        });

        return result;
    }

    public static void main(String[] args) {
        String content = "北京 互联网/电子商务,文字媒体/出版/法律,财务/审计/税务";
        List<String> ret = Analysis.doDicAnalysis(content);
        System.out.println(ret);
    }
}
