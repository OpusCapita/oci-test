package com.opuscapita.ocisetest.comparator

import java.util.regex.Matcher
import java.util.regex.Pattern

class InboundComparator {
    static Comparator<String> comparator = new Comparator<String>() {
        @Override
        int compare(String str1, String str2) {
            int itemPositionFirstString = 0
            int itemPositionSecondString = 0
            Matcher matcher = Pattern.compile("\\[(\\d+)|_(\\d+)").matcher(str1)
            if (matcher.find()) {
                try {
                    itemPositionFirstString = Integer.parseInt(matcher.group(1) != null ? matcher.group(1) : matcher.group(2))
                } catch (e) {
                    return str1 <=> str2
                }
            }
            matcher = Pattern.compile("\\[(\\d+)|_(\\d+)").matcher(str2)
            if (matcher.find()) {
                try {
                    itemPositionSecondString = Integer.parseInt(matcher.group(1) != null ? matcher.group(1) : matcher.group(2))
                } catch (e) {
                    return str1 <=> str2
                }
            }
            if (itemPositionFirstString > itemPositionSecondString) {
                return 1
            } else if (itemPositionFirstString == itemPositionSecondString) {
                return str1 <=> str2
            } else {
                return -1
            }
        }
    }
}
