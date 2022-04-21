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
}
