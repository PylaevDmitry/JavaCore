package stepic;

import com.google.common.base.Strings;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        run();
    }

    public static void run () {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        code(scanner.next());
    }

    public static void code (String input) {
        String resultString = input;
        Map<String, Integer> map = new HashMap<>();
        for (String letter:input.split("")) {
            if (!map.containsKey(letter)) { map.put(letter, 1); }
            else { map.put(letter, map.get(letter)+1); }
        }
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet()) {
            @Override
            public String toString ( ) {
                String result = "";
                this.sort(Map.Entry.comparingByKey());
                for (int i = 0; i < this.size(); i++) {
                    if (i<this.size()-1) result+=this.get(i).getKey() + ": " + Strings.repeat("1", i) + "0" + "\n";
                    else result+=this.get(i).getKey() + ": " + Strings.repeat("1", i) + "\n";
                }
                return result;
            }
        };
        list.sort((o1, o2) -> {
            if (!o1.getValue().equals(o2.getValue())) return -Integer.compare(o1.getValue(), o2.getValue());
            else return CharSequence.compare(o1.getKey(), o2.getKey());
        });

        for (int i = 0; i < list.size(); i++) {
            if (i<list.size()-1) list.get(i).setValue(Integer.valueOf(Strings.repeat("1", i) + "0"));
            else list.get(i).setValue(Integer.valueOf(Strings.repeat("1", i) ));
        }

        for (Map.Entry<String, Integer> stringIntegerEntry : list) { resultString = resultString.replace(stringIntegerEntry.getKey(), String.valueOf(stringIntegerEntry.getValue())); }

        System.out.println(list.size() + " " + resultString.length());
        System.out.println(list);
        System.out.println(resultString);
    }
}





