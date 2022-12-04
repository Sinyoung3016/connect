package fortune.connect.connection.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RequestConnection {
  private long requestId;
  private UUID groupIdFrom;
  private UUID groupIdTo;

  public RequestConnection(UUID groupIdFrom, UUID groupIdTo) {
    this.groupIdFrom = groupIdFrom;
    this.groupIdTo = groupIdTo;
  }
}
