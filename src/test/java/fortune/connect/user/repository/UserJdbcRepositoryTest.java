package fortune.connect.user.repository;

import fortune.connect.user.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserJdbcRepositoryTest {

  @Container
  private static final MySQLContainer<?> mySqlContainer
      = new MySQLContainer<>(DockerImageName.parse("mysql"))
      .withInitScript("schema.sql");

  @Autowired
  UserJdbcRepository userJdbcRepository;

  static User newUser = new User(
      UUID.randomUUID(),
      "테스터",
      "password",
      new Email("test@naver.com"),
      "201900000",
      Sex.FEMALE,
      Department.ARCHITECTURE,
      "안녕하세요.",
      new Picture(UUID.randomUUID(), "filename", "./path"),
      LocalDateTime.now(),
      LocalDateTime.now());

  @Test
  @Order(1)
  @DisplayName("[성공] 사용자를 추가한다")
  void testInsertUser() {
    User returnedUser = userJdbcRepository.insert(newUser);
    Assertions.assertThat(returnedUser.getUserId()).isEqualTo(newUser.getUserId());
  }

  @Test
  @Order(2)
  @DisplayName("[성공] 사용자를 아이디로 조회한다")
  void testGetUserById() {
    User returnedUser = userJdbcRepository.findUserByID(newUser.getUserId()).get();
    Assertions.assertThat(returnedUser.getUserId()).isEqualTo(newUser.getUserId());
    Assertions.assertThat(returnedUser.getPicture().getPictureId()).isEqualTo(newUser.getPicture().getPictureId());
  }
}
