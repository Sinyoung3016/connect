package fortune.connect.connection.repository;

import fortune.connect.connection.repository.entity.Connection;
import fortune.connect.connection.repository.entity.RequestConnection;
import fortune.connect.user.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fortune.connect.util.JdbcMapper.*;

@Repository
public class ConnectionJdbcRepository implements ConnectionRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public ConnectionJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void requestConnection(RequestConnection requestConnection) {
    int request = jdbcTemplate.update(
        "insert into request_connections (group_id_from, group_id_to)" +
            " values(UUID_TO_BIN(:groupIdFrom), UUID_TO_BIN(:groupIdTo))",
        toRequestConnectionParamMap(requestConnection));
    if (request != 1) {
      throw new RuntimeException("Nothing was inserted");
    }
  }

  @Override
  public List<RequestConnection> findAllRequestConnectionByGroupId(UUID groupIdFrom) {
    try {
      List<RequestConnection> connections = jdbcTemplate.query(
          "select * from request_connections where group_id_to = UUID_TO_BIN(:groupId)",
          Collections.singletonMap("groupId", groupIdFrom.toString().getBytes()),
          requestConnectionRowMapper);
      return connections;
    } catch (DataAccessException e) {
      return List.of();
    }
  }

  @Override
  public void acceptConnectionRequest(Connection connection) {
    int request = jdbcTemplate.update(
        "insert into connections (connection_id, group_id1, group_id2)" +
            " values(:connectionId, UUID_TO_BIN(:groupId1), UUID_TO_BIN(:groupId2))",
        toConnectionParamMap(connection));
    if (request != 1) {
      throw new RuntimeException("Nothing was inserted");
    }
  }

  @Override
  public List<Connection> findAllConnectionByGroupId(UUID groupId) {
    try {
      List<Connection> connections = jdbcTemplate.query(
          "select * from connections where group_id1 = UUID_TO_BIN(:groupId) or group_id2 = UUID_TO_BIN(:groupId) ",
          Collections.singletonMap("groupId", groupId.toString().getBytes()),
          connectionRowMapper);
      return connections;
    } catch (DataAccessException e) {
      System.out.println(e);
      return List.of();
    }
  }
}
