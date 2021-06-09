package stepic;

import java.util.*;

public class AsString {
    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in).useLocale(Locale.forLanguageTag("en-US"));

        List <Integer> list = new ArrayList<>();
        while (scanner.hasNext()) {
            try {
                String temp = scanner.next();
                if (temp.equals("exit")) break;
                list.add(Integer.valueOf(temp));
            } catch (NumberFormatException ignored) {
            }
        }

        List <Integer> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!(i%2==0)) res.add(list.get(i));
        }
        Collections.reverse(res);

        System.out.println(String.valueOf(res).replace("[", "")
                .replace("]", "")
                .replace(",",""));


    }

}

