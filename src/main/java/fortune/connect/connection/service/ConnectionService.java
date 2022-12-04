package fortune.connect.connection.service;

import fortune.connect.connection.repository.entity.Connection;
import fortune.connect.connection.repository.entity.RequestConnection;
import fortune.connect.connection.repository.ConnectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ConnectionService {

  private final ConnectionRepository repository;

  public void requestConnection(UUID groupIdFrom, UUID groupIdTo) {
    RequestConnection requestConnection = new RequestConnection(
        groupIdFrom, groupIdTo
    );
    repository.requestConnection(requestConnection);
  }

  public List<RequestConnection> getAllRequestConnectionById(UUID groupIdFrom) {
    return repository.findAllRequestConnectionByGroupId(groupIdFrom);
  }

  public void acceptConnectionRequest(UUID groupId1, UUID groupId2) {
    Connection connection = new Connection(
        groupId1, groupId2
    );
    repository.acceptConnectionRequest(connection);
  }

  public List<Connection> getAllConnectionBy(UUID groupId){
    return repository.findAllConnectionByGroupId(groupId);
  }
}
