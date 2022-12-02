package fortune.connect.user.repository;

import fortune.connect.user.domain.Picture;
import fortune.connect.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  User insert(User newUser, Picture userPicture);
  Optional<User> findUserById(UUID userId);
  Optional<Picture> findUserPictureById(UUID pictureId);
}
