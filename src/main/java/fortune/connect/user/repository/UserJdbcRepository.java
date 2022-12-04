package fortune.connect.user.repository;

import fortune.connect.user.domain.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static fortune.connect.util.JdbcMapper.*;

@Repository
public class UserJdbcRepository implements UserRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public UserJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Transactional
  public User insert(User user, Picture picture) {
    User newUser = makeUserId(user);
    int updatedUserPic = jdbcTemplate.update(
        "insert into user_pictures(picture_id, file_path)" +
            " values(UUID_TO_BIN(:pictureId), :filePath)",
        toUserPictureParamMap(picture));
    int updatedUser = jdbcTemplate.update(
        "insert into users(user_id, picture_id, username, password, email, student_id, sex, department, introduction, created_at, updated_at)" +
            " values(UUID_TO_BIN(:userId), UUID_TO_BIN(:pictureId), :username, :password, :email, :studentId, :sex, :department, :introduction, :createdAt, :updatedAt)",
        toUserParamMap(newUser));

    if (updatedUser != 1 || updatedUserPic != 1) {
      throw new RuntimeException("Nothing was inserted");
    }
    return newUser;
  }

  public Optional<User> findUserById(UUID userId) {
    try {
      User user = jdbcTemplate.queryForObject(
          "select * from users where user_id = UUID_TO_BIN(:userId)",
          Collections.singletonMap("userId", userId.toString().getBytes()),
          userRowMapper
      );
      return Optional.of(user);
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<Picture> findUserPictureById(UUID pictureId) {
    try {
      Picture picture = jdbcTemplate.queryForObject(
          "select * from user_pictures where picture_id = UUID_TO_BIN(:pictureId)",
          Collections.singletonMap("pictureId", pictureId.toString().getBytes()),
          pictureRowMapper
      );
      return Optional.of(picture);
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }

  private User makeUserId(User newUser) {
    newUser.setUserId(UUID.randomUUID());
    return newUser;
  }
}
