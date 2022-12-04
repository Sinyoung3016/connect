package fortune.connect.group.domain;

import fortune.connect.user.domain.User;
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
public class Group {
  private UUID groupId;
  private UUID ownerId;
  private String title;
  private String subtitle;
  private String interestTag;
  private int numOfMember;
  private List<User> groupMember;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Group(UUID groupId, UUID ownerId, String title, String subtitle, String interestTag, int numOfMember, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.groupId = groupId;
    this.ownerId = ownerId;
    this.title = title;
    this.subtitle = subtitle;
    this.interestTag = interestTag;
    this.numOfMember = numOfMember;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Group(UUID ownerId, String title, String subtitle, String interestTag, int numOfMember, List<User> groupMember) {
    this.ownerId = ownerId;
    this.title = title;
    this.subtitle = subtitle;
    this.interestTag = interestTag;
    this.numOfMember = numOfMember;
    this.groupMember = groupMember;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
