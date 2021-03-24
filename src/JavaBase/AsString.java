package JavaBase;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class AsString {
    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in).useLocale(Locale.forLanguageTag("en-US"));

        List <Integer> list = new ArrayList<>();
        List <Integer> res = new ArrayList<>();
        while (scanner.hasNext()) {
            try {
                list.add(Integer.valueOf(scanner.next()));
            } catch (InputMismatchException e) {
                    scanner.next();
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!(i%2==0)) res.add(list.get(i));
        }
        Collections.reverse(res);

        System.out.print(String.valueOf(res).replace("[", "").replace("]", "").replace(",",""));

    }


}
