package lossleaderproject.back.smoke;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
@SpringBootTest
public class SmokeTest {

    @Test
    @DisplayName("Smoke Test")
    @Transactional
    public void SmokeTest() throws Exception {
        Assertions.assertThat(1).isEqualTo(1);

    }

    @Test
    @DisplayName("Smoke Test2")
    @Transactional
    public void SmokeTest2() throws Exception {
        Assertions.assertThat(1).isEqualTo(1);

    }

    @Test
    @DisplayName("Smoke Test3")
    @Transactional
    public void SmokeTest3() throws Exception {
        Assertions.assertThat(1).isEqualTo(1);

    }
}
