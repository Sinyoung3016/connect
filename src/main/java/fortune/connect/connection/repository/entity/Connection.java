package fortune.connect.connection.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Connection {
  private long connectionId;
  private UUID groupId1;
  private UUID groupId2;

  public Connection(UUID groupId1, UUID groupId2) {
    this.groupId1 = groupId1;
    this.groupId2 = groupId2;
  }
}
