package domain;

import exception.InvalidLadderHeightException;
import exception.InvalidLineWeightException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import util.RandomBooleanGenerator;

class MapTest {

    @DisplayName("높이와 참가자의 수가 조건에 맞는 경우 사다리를 생성한다.")
    @Test
    void createSuccess() {
        Map map = new Map("3", 3, new RandomBooleanGenerator());
        Assertions.assertThat(map.getLines()).hasSize(3);
    }

    @DisplayName("높이가 숫자가 아닌 경우 오류를 던진다.")
    @Test
    void heightNotDigit() {
        Assertions.assertThatThrownBy(() -> new Map("a", 2, new RandomBooleanGenerator()))
            .isExactlyInstanceOf(InvalidLadderHeightException.class);
    }

    @DisplayName("높이가 null 혹은 빈값으로 이루어져 있을 경우 오류를 던진다.")
    @ParameterizedTest
    @NullAndEmptySource
    void heightNullOrEmpty(String input) {
        Assertions.assertThatThrownBy(() -> new Map(input, 2, new RandomBooleanGenerator()))
                  .isExactlyInstanceOf(InvalidLadderHeightException.class);
    }

    @DisplayName("높이가 공백으로만 이루어져 있을 경우 오류를 던진다.")
    @Test
    void heightNullOrEmpty() {
        Assertions.assertThatThrownBy(() -> new Map("    ", 2, new RandomBooleanGenerator()))
                  .isExactlyInstanceOf(InvalidLadderHeightException.class);
    }

    @DisplayName("참가자 수가 10명보다 많은 경우 오류를 던진다.")
    @Test
    void lineCountOver10() {
        Assertions.assertThatThrownBy(() -> new Map("3", 11, new RandomBooleanGenerator()))
            .isExactlyInstanceOf(InvalidLineWeightException.class);
    }

    @DisplayName("참가자 수가 2명보다 적은 경우 오류를 던진다.")
    @Test
    void lineCountUnder1() {
        Assertions.assertThatThrownBy(() -> new Map("3", 1, new RandomBooleanGenerator()))
            .isExactlyInstanceOf(InvalidLineWeightException.class);
    }

    @DisplayName("참가자 수에서 1을 뺀 만큼의 라인을 가진사다리를 만든다.")
    @Test
    void generateMap() {
        Map ladder = new Map("3", 3, () -> true);
        List<Line> lines = ladder.getLines();
        Assertions.assertThat(lines).hasSize(3);
        Assertions.assertThat(lines.get(0).getStatus()).containsExactly(true, false);
        Assertions.assertThat(lines.get(1).getStatus()).containsExactly(true, false);
        Assertions.assertThat(lines.get(2).getStatus()).containsExactly(true, false);
    }
}
