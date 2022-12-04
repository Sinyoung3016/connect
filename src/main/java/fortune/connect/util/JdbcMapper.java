package fortune.connect.util;

import fortune.connect.connection.repository.entity.Connection;
import fortune.connect.group.domain.Group;
import fortune.connect.connection.repository.entity.RequestConnection;
import fortune.connect.group.repository.entity.GroupUser;
import fortune.connect.user.domain.*;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fortune.connect.util.Utils.toLocalDateTime;
import static fortune.connect.util.Utils.toUUID;

public class JdbcMapper {

  public static Map<String, Object> toUserParamMap(User newUser) {
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

  public static final RowMapper<User> userRowMapper = (resultSet, i) -> {
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

  public static Map<String, Object> toUserPictureParamMap(Picture userPicture) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pictureId", userPicture.getPictureId().toString().getBytes());
    paramMap.put("filePath", userPicture.getFilePath());
    return paramMap;
  }

  public static final RowMapper<Picture> pictureRowMapper = (resultSet, i) -> {
    UUID pictureId = toUUID(resultSet.getBytes("picture_id"));
    String filePath = resultSet.getString("file_path");
    return new Picture(pictureId, filePath);
  };

  public static Map<String, Object> toGroupParamMap(Group newGroup) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("groupId", newGroup.getGroupId().toString().getBytes());
    paramMap.put("ownerId", newGroup.getOwnerId().toString().getBytes());
    paramMap.put("title", newGroup.getTitle());
    paramMap.put("subtitle", newGroup.getSubtitle());
    paramMap.put("interestTag", newGroup.getInterestTag());
    paramMap.put("numOfMember", newGroup.getNumOfMember());
    paramMap.put("createdAt", newGroup.getCreatedAt());
    paramMap.put("updatedAt", newGroup.getUpdatedAt());
    return paramMap;
  }

  public static final RowMapper<Group> groupRowMapper = (resultSet, i) -> {
    UUID groupId = toUUID(resultSet.getBytes("group_id"));
    UUID ownerId = toUUID(resultSet.getBytes("owner_id"));
    String title = resultSet.getString("title");
    String subtitle = resultSet.getString("subtitle");
    String interestTag = resultSet.getString("interest_tag");
    int numOfMember = resultSet.getInt("num_of_member");
    LocalDateTime created_at = toLocalDateTime(resultSet.getTimestamp("created_at"));
    LocalDateTime updated_at = toLocalDateTime(resultSet.getTimestamp("updated_at"));
    return new Group(groupId, ownerId, title, subtitle, interestTag, numOfMember, created_at, updated_at);
  };

  public static Map<String, Object> toConnectionParamMap(Connection connection) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("connectionId", connection.getConnectionId());
    paramMap.put("groupId1", connection.getGroupId1().toString().getBytes());
    paramMap.put("groupId2", connection.getGroupId2().toString().getBytes());
    return paramMap;
  }

  public static final RowMapper<Connection> connectionRowMapper = (resultSet, i) -> {
    int connectionId = resultSet.getInt("connection_id");
    UUID groupId1 = toUUID(resultSet.getBytes("group_id1"));
    UUID groupId2 = toUUID(resultSet.getBytes("group_id2"));
    return new Connection(connectionId, groupId1, groupId2);
  };

  public static Map<String, Object> toRequestConnectionParamMap(RequestConnection connection) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("requestId", connection.getRequestId());
    paramMap.put("groupIdTo", connection.getGroupIdTo().toString().getBytes());
    paramMap.put("groupIdFrom", connection.getGroupIdFrom().toString().getBytes());
    return paramMap;
  }

  public static final RowMapper<RequestConnection> requestConnectionRowMapper = (resultSet, i) -> {
    int requestId = resultSet.getInt("request_id");
    UUID groupIdFrom = toUUID(resultSet.getBytes("group_id_from"));
    UUID groupIdTo = toUUID(resultSet.getBytes("group_id_to"));
    return new RequestConnection(requestId, groupIdFrom, groupIdTo);
  };

  public static Map<String, Object> toGroupUserParamMap(GroupUser groupUser) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("groupUserId", groupUser.getGroupUserId());
    paramMap.put("groupId", groupUser.getGroupId().toString().getBytes());
    paramMap.put("userId", groupUser.getUserId().toString().getBytes());
    return paramMap;
  }
}
