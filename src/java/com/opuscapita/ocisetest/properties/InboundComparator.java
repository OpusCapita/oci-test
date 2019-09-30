package com.opuscapita.ocisetest.properties;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InboundComparator {
    public static Comparator<String> comparator = new Comparator<String>() {
        public int compare(String str1, String str2) {
            int itemPositionFirstString = 0;
            int itemPositionSecondString = 0;
            Matcher matcher = Pattern.compile("\\[(\\d+)|_(\\d+)").matcher(str1);
            if (matcher.find()) {
                try {
                    itemPositionFirstString = Integer.parseInt(matcher.group(1) != null ? matcher.group(1) : matcher.group(2));

                } catch (Exception e) {
                    return str1.compareTo(str2);
                }
            }
            matcher = Pattern.compile("\\[(\\d+)|_(\\d+)").matcher(str2);
            if (matcher.find()) {
                try {
                    itemPositionSecondString = Integer.parseInt(matcher.group(1) != null ? matcher.group(1) : matcher.group(2));
                } catch (Exception e) {
                    return str1.compareTo(str2);
                }
            }
            if (itemPositionFirstString > itemPositionSecondString) {
                return 1;
            } else if (itemPositionFirstString == itemPositionSecondString) {
                return str1.compareTo(str2);
            } else {
                return -1;
            }
        }
    };
}
