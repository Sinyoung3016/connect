package fortune.connect.user.repository;

import fortune.connect.user.domain.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static fortune.connect.util.Utils.toLocalDateTime;
import static fortune.connect.util.Utils.toUUID;

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

  private Map<String, Object> toUserParamMap(User newUser) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("userId", newUser.getUserId().toString().getBytes());
    paramMap.put("pictureId", newUser.getPictureId().toString().getBytes());
    paramMap.put("username", newUser.getUsername());
    paramMap.put("password", newUser.getPassword());
    paramMap.put("email", newUser.getEmail().address());
    paramMap.put("studentId", newUser.getStudentId());
    paramMap.put("sex", newUser.getSex().toString());
    paramMap.put("department", newUser.getDepartment());
    paramMap.put("introduction", newUser.getIntroduction());
    paramMap.put("createdAt", newUser.getCreatedAt());
    paramMap.put("updatedAt", newUser.getUpdatedAt());
    return paramMap;
  }

  private Map<String, Object> toUserPictureParamMap(Picture userPicture) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pictureId", userPicture.getPictureId().toString().getBytes());
    paramMap.put("filePath", userPicture.getFilePath());
    return paramMap;
  }

  private static final RowMapper<User> userRowMapper = (resultSet, i) -> {
    UUID userId = toUUID(resultSet.getBytes("user_id"));
    UUID pictureId = toUUID(resultSet.getBytes("picture_id"));
    String username = resultSet.getString("username");
    String password = resultSet.getString("password");
    Email email = new Email(resultSet.getString("email"));
    String studentId = resultSet.getString("student_id");
    Sex sex = Sex.valueOf(resultSet.getString("sex"));
    String department = resultSet.getString("department");
    String introduction = resultSet.getString("introduction");
    LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
    LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
    return new User(userId, pictureId, username, password, email, studentId, sex, department, introduction, createdAt, updatedAt);
  };

  private static final RowMapper<Picture> pictureRowMapper = (resultSet, i) -> {
    UUID pictureId = toUUID(resultSet.getBytes("picture_id"));
    String filePath = resultSet.getString("file_path");
    return new Picture(pictureId, filePath);
  };

  private User makeUserId(User newUser) {
    newUser.setUserId(UUID.randomUUID());
    return newUser;
  }
}
