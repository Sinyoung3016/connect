package fortune.connect.group.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupUser {
  private int groupUserId;
  private UUID groupId;
  private UUID userId;

  public GroupUser(UUID groupId, UUID userId) {
    this.groupId = groupId;
    this.userId = userId;
  }
}
