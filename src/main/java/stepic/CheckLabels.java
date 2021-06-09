package stepic;

import stepic.Mail.Mail;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class CheckLabels {

    public static void main(String[] args) {
        TextAnalyzer spam = new SpamAnalyzer(new String[]{"first", "second"});
        TextAnalyzer filter = new TooLongTextAnalyzer(10);
        CheckLabels cl = new CheckLabels();
        System.out.println(cl.checkLabels(new TextAnalyzer[]{spam}, "this is first test, and it is spam"));
        System.out.println(cl.checkLabels(new TextAnalyzer[]{filter}, "Это весело"));

        Mail.MailMessage mail = new Mail.MailMessage("Austin Powers", "X", "hello");
        Logger log= Logger.getLogger("log");
        Mail.MailService spy = new Mail.Spy(log);
        spy.processMail(mail);
        Handler h = new ConsoleHandler();
        log.addHandler(h);

        Mail.Sendable pack = new Mail.MailPackage("x", "y", new Mail.Package("weapons", 12));
        Mail.Inspector isp = new Mail.Inspector();
        isp.processMail(pack);

    }

    public Label checkLabels(TextAnalyzer[] analyzers, String text) {
        for (TextAnalyzer analyzer: analyzers) {
            Label res=analyzer.processText(text);
            if (!res.equals(Label.OK)) return res;
        }
        return Label.OK;


    }

    abstract static class  KeywordAnalyzer implements TextAnalyzer {
        abstract protected String [] getKeywords();
        abstract protected Label getLabel();
        public Label processText(String text) {
            for (String elem:text.split(" ")) {
                for (String keyword:getKeywords()) {
                    if (elem.equals(keyword)) return getLabel();
                }
            }
            for (String elem:text.split(", ")) {
                for (String keyword:getKeywords()) {
                    if (elem.equals(keyword)) return getLabel();
                }
            }
            return Label.OK;
        }

    }

    static class SpamAnalyzer extends KeywordAnalyzer {

        private String[] keywords;

        public SpamAnalyzer(String[] keywords) {
            this.keywords=keywords;
        }

        @Override
        protected String[] getKeywords() {
            return keywords;
        }

        @Override
        protected Label getLabel() {
            return Label.SPAM;
        }
    }

    static class NegativeTextAnalyzer extends KeywordAnalyzer {
        public NegativeTextAnalyzer() {
        }

        @Override
        protected String[] getKeywords() {
            return new String[]{":(", "=(", ":|"};
        }

        @Override
        protected Label getLabel() {
            return Label.NEGATIVE_TEXT;
        }
    }

    static class TooLongTextAnalyzer implements TextAnalyzer{
        private int maxLength;
        public TooLongTextAnalyzer(int maxLength) {
            this.maxLength=maxLength;
        }

        public Label processText (String text) {
            if (text.length()>maxLength) return Label.TOO_LONG;
            return Label.OK;
        }
    }

    public static int checkSumOfStream(InputStream inputStream) throws IOException {
        int checkSum=0;
        byte[] b= new byte[1];
        while (inputStream.read(b)>0) checkSum=Integer.rotateLeft(checkSum, 1)^(b[0]&0xFF);
        return checkSum;

    }

    public interface TextAnalyzer {
        CheckLabels.Label processText(String text);
    }

    public enum Label {
        SPAM, NEGATIVE_TEXT, TOO_LONG, OK
    }

}



