package fortune.connect.user.service;

import fortune.connect.user.domain.*;
import fortune.connect.user.service.dto.CreateUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserServiceTest {

  @Autowired
  UserService service;

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
  void testCreateUser() {
    CreateUserDto createUserDto = new CreateUserDto(
        newUser.getUsername(),
        newUser.getPassword(),
        newUser.getEmail(),
        newUser.getStudentId(),
        newUser.getSex(),
        newUser.getDepartment(),
        newUser.getIntroduction(),
        userPicture
    );
    createdUser = service.createUser(createUserDto);
    Assertions.assertThat(createdUser.getUserId()).isInstanceOf(UUID.class);
  }

  @Test
  @Order(2)
  @DisplayName("[성공] 사용자를 아이디로 조회한다")
  void testGetUserById() {
    User returnedUser = service.getUserById(createdUser.getUserId());

    Assertions.assertThat(returnedUser.getStudentId()).isEqualTo(newUser.getStudentId());
    }

  @Test
  @Order(3)
  @DisplayName("[실패] 존재하지 않는 사용자를 아이디로 조회한다")
  void testGetUserById_noId() {
    Assertions.assertThatThrownBy(
            () -> service.getUserById(UUID.randomUUID()))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Nothing was found");
  }
}
