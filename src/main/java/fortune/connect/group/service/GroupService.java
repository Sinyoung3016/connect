package fortune.connect.group.service;

import fortune.connect.group.domain.Group;
import fortune.connect.group.repository.GroupRepository;
import fortune.connect.group.service.dto.CreateGroupDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class GroupService {
  private final GroupRepository repository;

  public Group createGroup(CreateGroupDto createGroupDto) {
    Group newGroup = new Group(
        createGroupDto.getOwnerId(),
        createGroupDto.getTitle(),
        createGroupDto.getSubtitle(),
        createGroupDto.getInterestTag(),
        createGroupDto.getNumOfMember(),
        createGroupDto.getGroupMember()
    );
    return repository.insertNewGroup(newGroup);
  }

  public Group getGroupById(UUID groupId) {
    return repository.findGroupById(groupId)
        .orElseThrow(() -> new RuntimeException("Nothing was found"));
  }

  public List<Group> getAllGroups() {
    return repository.findAllGroups();
  }

  public List<Group> getAllGroupsByNumberOfMember(int numOfMember) {
    return repository.findAllGroupsByNumOfMember(numOfMember);
  }
}
