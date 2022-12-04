package fortune.connect.group.repository;

import fortune.connect.group.domain.Group;
import fortune.connect.group.repository.entity.GroupUser;
import fortune.connect.user.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static fortune.connect.util.JdbcMapper.*;

@Repository
public class GroupJdbcRepository implements GroupRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public GroupJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  @Transactional
  public Group insertNewGroup(Group group) {
    Group newGroup = makeGroupId(group);
    int updatedGroup = jdbcTemplate.update(
        "insert into matching_groups(group_id, owner_id, title, subtitle, interest_tag, num_of_member, created_at, updated_at)" +
            " values(UUID_TO_BIN(:groupId), UUID_TO_BIN(:ownerId), :title, :subtitle, :interestTag, :numOfMember, :createdAt, :updatedAt)",
        toGroupParamMap(newGroup));
    group.getGroupMember()
        .forEach(item ->
            jdbcTemplate.update(
                "insert into group_users(group_id, user_id)" +
                    " values(UUID_TO_BIN(:groupId), UUID_TO_BIN(:userId))",
                toGroupUserParamMap(new GroupUser(newGroup.getGroupId(), item.getUserId())))
        );
    if (updatedGroup != 1) {
      throw new RuntimeException("Nothing was inserted");
    }
    return newGroup;
  }

  @Override
  @Transactional
  public Optional<Group> findGroupById(UUID groupId) {
    try {
      Group group = jdbcTemplate.queryForObject(
          "select * from matching_groups where group_id = UUID_TO_BIN(:groupId)",
          Collections.singletonMap("groupId", groupId.toString().getBytes()),
          groupRowMapper);
      List<User> users = jdbcTemplate.query(
          "select * from users where user_id = (select user_id from group_users where group_id = UUID_TO_BIN(:groupId))",
          Collections.singletonMap("groupId", groupId.toString().getBytes()),
          userRowMapper
      );
      group.setGroupMember(users);
      return Optional.of(group);
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public List<Group> findAllGroups() {
    try {
      List<Group> groups = jdbcTemplate.query(
          "select * from matching_groups",
          Collections.emptyMap(),
          groupRowMapper);
      groups.forEach(g -> {
        List<User> users = jdbcTemplate.query(
            "select * from users where user_id = (select user_id from group_users where group_id = UUID_TO_BIN(:groupId))",
            Collections.singletonMap("groupId", g.getGroupId().toString().getBytes()),
            userRowMapper
        );
        g.setGroupMember(users);
      });
      return groups;
    } catch (DataAccessException e) {
      System.out.println("e" + e);
      return List.of();
    }
  }

  @Override
  @Transactional
  public List<Group> findAllGroupsByNumOfMember(int num) {
    try {
      List<Group> groups = jdbcTemplate.query(
          "select * from matching_groups where num_of_member = :num",
          Collections.singletonMap("num", num),
          groupRowMapper);
      groups.forEach(g -> {
        List<User> users = jdbcTemplate.query(
            "select * from users where user_id = (select user_id from group_users where group_id = UUID_TO_BIN(:groupId))",
            Collections.singletonMap("groupId", g.getGroupId().toString().getBytes()),
            userRowMapper
        );
        g.setGroupMember(users);
      });
      return groups;
    } catch (DataAccessException e) {
      return List.of();
    }
  }

  private Group makeGroupId(Group newGroup) {
    newGroup.setGroupId(UUID.randomUUID());
    return newGroup;
  }
}
