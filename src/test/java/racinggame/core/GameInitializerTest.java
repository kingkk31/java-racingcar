package racinggame.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static racinggame.core.GameInitializer.DELIMITER;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import racinggame.exception.InvalidCarNameException;
import racinggame.exception.InvalidCarRegistrationException;

class GameInitializerTest {

    @ParameterizedTest
    @ValueSource(strings = "pobi,crong,honux")
    @DisplayName("주어진 명단대로 차를 생성하기")
    void init_cars(String carNames) {
        List<Car> cars = GameInitializer.initCars(carNames);

        assertAll(
                () -> assertThat(cars).hasSize(carNames.split(DELIMITER).length),
                () -> assertThat(cars.get(0).getName()).isEqualTo("pobi"),
                () -> assertThat(cars.get(1).getName()).isEqualTo("crong"),
                () -> assertThat(cars.get(2).getName()).isEqualTo("honux")
        );

    }

    @ParameterizedTest
    @NullSource
    @DisplayName("차량을 아예 등록하지 않으면 예외 발생.")
    void fail_to_register_no_car(String carNames) {
        assertThatThrownBy(() -> GameInitializer.initCars(carNames))
                .isInstanceOf(InvalidCarRegistrationException.class);

    }
    @ParameterizedTest
    @EmptySource
    @DisplayName("빈 이름을 등록하면 예외 발생.")
    void fail_to_register_empty_name_car(String carNames) {
        assertThatThrownBy(() -> GameInitializer.initCars(carNames))
                .isInstanceOf(InvalidCarNameException.class);

    }

    @ParameterizedTest
    @ValueSource(strings = "pobi,crong,looooooongname")
    @DisplayName("차 이름이 5자 초과이면 예외 발생.")
    void fail_to_register_long_name_car(String carNames) {
        assertThatThrownBy(() -> GameInitializer.initCars(carNames))
                .isInstanceOf(InvalidCarNameException.class);

    }

}