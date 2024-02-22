package ministryofeducation.sideprojectspring.unit.Personnel.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import ministryofeducation.sideprojectspring.personnel.application.WordIndex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WordIndexTest {

    @DisplayName("첫 글자로 자음이 입력되면 해당 자음으로 시작하는 모든 단어를 조회한다. (ㄱ / 가 - 깋)")
    @Test
    void searchWordRangeFirstConsonant() {
        //given
        String searchWord = "ㄱ";

        //when
        String[] searchWordRange = WordIndex.searchWordRange(searchWord);

        //then
        assertThat(searchWordRange[0]).isEqualTo("가");
        assertThat(searchWordRange[1]).isEqualTo("깋");
    }

    @DisplayName("끝 글자로 자음이 입력되면 해당 자음을 끝으로 시작하는 모든 단어를 조회한다. (홍길ㄷ / 홍길다 - 홍길딯)")
    @Test
    void searchWordRangeLastConsonant() {
        //given
        String searchWord = "홍길ㄷ";

        //when
        String[] searchWordRange = WordIndex.searchWordRange(searchWord);

        //then
        assertThat(searchWordRange[0]).isEqualTo("홍길다");
        assertThat(searchWordRange[1]).isEqualTo("홍길딯");
    }

    @DisplayName("온전한 구성의 단어가 입력되면 해당 단어를 반환한다.")
    @Test
    void searchWordRangeNoConsonant() {
        //given
        String searchWord = "홍길동";

        //when
        String[] searchWordRange = WordIndex.searchWordRange(searchWord);

        //then
        assertThat(searchWordRange[0]).isEqualTo("홍길동");
        assertThat(searchWordRange[1]).isEqualTo("홍길동");
    }


}