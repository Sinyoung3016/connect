package fortune.connect.group.controller.response;

import fortune.connect.group.domain.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {
  private UUID groupId;
  private UUID ownerId;
  private String title;
  private String subtitle;
  private String interestTag;
  private int numOfMember;
  private List<GroupMember> groupUser;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
