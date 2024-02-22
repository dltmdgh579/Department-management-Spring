package ministryofeducation.sideprojectspring.personnel.application;

import java.util.List;
import org.springframework.util.StringUtils;

public class WordIndex {
    public static String[] searchWordRange(String searchWord) {
        if(!StringUtils.hasText(searchWord)) return null;
        String endLetter = searchWord.substring(searchWord.length()-1, searchWord.length());

        List<String> consonantList = List.of("ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ");
        List<String> startWordList = List.of("가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하");
        List<String> endWordList = List.of("깋", "닣", "딯", "맇", "밓", "빟", "싷", "잏", "짛", "칳", "킻", "팋", "핗", "힣");

        String startRange = searchWord;
        String endRange = searchWord;

        if (consonantList.contains(endLetter)) {
            startRange = searchWord.substring(0, searchWord.length()-1) + startWordList.get(consonantList.indexOf(endLetter));
            endRange = searchWord.substring(0, searchWord.length()-1) + endWordList.get(consonantList.indexOf(endLetter));
        }

        return new String[] {startRange, endRange};
    }
}
