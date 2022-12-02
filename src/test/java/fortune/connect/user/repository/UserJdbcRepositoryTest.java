package fortune.connect.user.repository;

import fortune.connect.user.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserJdbcRepositoryTest {

  @Container
  private static final MySQLContainer<?> mySqlContainer
      = new MySQLContainer<>(DockerImageName.parse("mysql"))
      .withInitScript("schema.sql");

  @Autowired
  UserRepository userJdbcRepository;

  private final static Picture userPicture = new Picture(
      UUID.randomUUID(),
      "/path"
  );

  private final static User newUser = new User(
      userPicture.getPictureId(),
      "테스터",
      "password",
      new Email("test@naver.com"),
      "2019",
      Sex.FEMALE,
      "컴퓨터융합학부",
      "안녕하세요."
  );

  private static User createdUser;

  @Test
  @Order(1)
  @DisplayName("[성공] 사용자를 추가한다")
  void testInsertUser() {
    createdUser = userJdbcRepository.insert(newUser, userPicture);
    Assertions.assertThat(createdUser.getUserId()).isInstanceOf(UUID.class);
    Assertions.assertThat(createdUser.getPictureId()).isEqualTo(createdUser.getPictureId());
  }

  @Test
  @Order(2)
  @DisplayName("[성공] 사용자를 아이디로 조회한다")
  void testGetUserById() {
    User returnedUser = userJdbcRepository.findUserById(createdUser.getUserId()).get();

    Assertions.assertThat(returnedUser.getUserId()).isInstanceOf(UUID.class);
    Assertions.assertThat(returnedUser.getPictureId()).isEqualTo(createdUser.getUserId());
  }

  @Test
  @Order(3)
  @DisplayName("[실패] 존재하지 않은 사용자를 아이디로 조회한다")
  void testGetUserById_noUser() {
    UUID invalidUserId = UUID.randomUUID();
    Optional<User> returnedUser = userJdbcRepository.findUserById(invalidUserId);

    Assertions.assertThat(returnedUser.isPresent()).isFalse();
  }
}
