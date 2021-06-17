package stepic;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Mail {

    public static final String AUSTIN_POWERS = "Austin Powers";
    public static final String WEAPONS = "weapons";
    public static final String BANNED_SUBSTANCE = "banned substance";

    public static class UntrustworthyMailWorker  implements MailService {
        MailService[] msArr;
        RealMailService rms = new RealMailService();

        public UntrustworthyMailWorker  (MailService[] msArr) {
            this.msArr=msArr;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            for (MailService ms:msArr) {
                mail=ms.processMail(mail);
            }
            return rms.processMail(mail);
        }
        public RealMailService getRealMailService() {
            return rms;
        }
    }

    public static class Spy implements MailService {
        Logger log;

        public Spy(Logger log) {
            this.log = log;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailMessage) {
                if ((mail.getFrom().equals(AUSTIN_POWERS) || mail.getTo().equals(AUSTIN_POWERS)))
                    log.log(Level.WARNING, "Detected target mail correspondence: from {0} to {1}"+ " \"" + ((MailMessage) mail).getMessage()+"\"", new Object[] {mail.getFrom(), mail.getTo()});
                else
                    log.log(Level.INFO, "Usual correspondence: from {0} to {1}", new Object[] {mail.getFrom(), mail.getTo()});
            }
            return mail;
        }
    }

    public static class Thief implements MailService {
        int min;
        int stolenValue=0;

        public Thief (int min) {
            this.min=min;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailPackage) {
                int price = ((MailPackage) mail).getContent().getPrice();
                String content = ((MailPackage) mail).getContent().getContent();
                if (price >= min) {
                    stolenValue += price;
                    return new MailPackage(mail.getFrom(), mail.getTo(), new Package("stones instead of " + content, 0));
                }
            }
            return mail;
        }

        public int getStolenValue() {
            return stolenValue;
        }
    }

    public static class Inspector implements MailService {
        @Override
        public Sendable processMail(Sendable mail) {

            if (mail instanceof MailPackage) {
                String mes = ((MailPackage) mail).getContent().getContent();
                if (mes.contains("stones")) throw new StolenPackageException();
                if (mes.contains(WEAPONS)||mes.contains(BANNED_SUBSTANCE)) throw new IllegalPackageException();
            }
            return mail;
        }
    }

    public static class IllegalPackageException extends RuntimeException {

    }
    public static class StolenPackageException extends RuntimeException {

    }



    public static interface Sendable {
        String getFrom();
        String getTo();
    }
    public static abstract class AbstractSendable implements Sendable {

        protected final String from;
        protected final String to;

        public AbstractSendable(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String getFrom() {
            return from;
        }

        @Override
        public String getTo() {
            return to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AbstractSendable that = (AbstractSendable) o;

            if (!from.equals(that.from)) return false;
            if (!to.equals(that.to)) return false;

            return true;
        }

    }

    public static class MailMessage extends AbstractSendable {

        private final String message;

        public MailMessage(String from, String to, String message) {
            super(from, to);
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MailMessage that = (MailMessage) o;

            if (message != null ? !message.equals(that.message) : that.message != null) return false;

            return true;
        }

    }

    public static class MailPackage extends AbstractSendable {
        private final Package content;

        public MailPackage(String from, String to, Package content) {
            super(from, to);
            this.content = content;
        }

        public Package getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MailPackage that = (MailPackage) o;

            if (!content.equals(that.content)) return false;

            return true;
        }

    }

    public static class Package {
        private final String content;
        private final int price;

        public Package(String content, int price) {
            this.content = content;
            this.price = price;
        }

        public String getContent() {
            return content;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Package aPackage = (Package) o;

            if (price != aPackage.price) return false;
            if (!content.equals(aPackage.content)) return false;

            return true;
        }
    }

    public static interface MailService {
        Sendable processMail(Sendable mail);
    }

    public static class RealMailService implements MailService {

        @Override
        public Sendable processMail(Sendable mail) {
            // Здесь описан код настоящей системы отправки почты.
            return mail;
        }
    }

}
