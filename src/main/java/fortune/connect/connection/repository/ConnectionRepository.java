package fortune.connect.connection.repository;

import fortune.connect.connection.repository.entity.Connection;
import fortune.connect.connection.repository.entity.RequestConnection;

import java.util.List;
import java.util.UUID;

public interface ConnectionRepository {

  void requestConnection(RequestConnection connection);

  List<RequestConnection> findAllRequestConnectionByGroupId(UUID groupIdFrom);

  void acceptConnectionRequest(Connection connection);

  List<Connection> findAllConnectionByGroupId(UUID groupId);
}
