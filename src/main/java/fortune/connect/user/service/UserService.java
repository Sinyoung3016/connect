package fortune.connect.user.service;

import fortune.connect.user.domain.Picture;
import fortune.connect.user.domain.User;
import fortune.connect.user.repository.UserJdbcRepository;
import fortune.connect.user.service.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

  private final UserJdbcRepository repository;

  public User createUser(CreateUserDto createUserDto) {
    User newUser = new User(
        createUserDto.getPicture().getPictureId(),
        createUserDto.getUsername(),
        createUserDto.getPassword(),
        createUserDto.getEmail(),
        createUserDto.getStudentId(),
        createUserDto.getSex(),
        createUserDto.getDepartment(),
        createUserDto.getIntroduction()
    );
    Picture userPicture = createUserDto.getPicture();
    return repository.insert(newUser, userPicture);
  }

  public User getUserById(UUID userId) {
    return repository.findUserById(userId)
        .orElseThrow(() -> new RuntimeException("Nothing was found"));
  }

  public Picture getUserPictureById(UUID pictureId) {
    return repository.findUserPictureById(pictureId)
        .orElseThrow(() -> new RuntimeException("Nothing was found"));
  }
}
