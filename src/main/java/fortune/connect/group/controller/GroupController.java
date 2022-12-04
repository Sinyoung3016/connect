package fortune.connect.group.controller;

import fortune.connect.group.controller.request.CreateGroupRequest;
import fortune.connect.group.domain.GroupMember;
import fortune.connect.group.controller.response.GroupResponse;
import fortune.connect.group.domain.Group;
import fortune.connect.group.service.GroupService;
import fortune.connect.group.service.dto.CreateGroupDto;
import fortune.connect.user.controller.request.UserRequest;
import fortune.connect.user.domain.Email;
import fortune.connect.user.domain.Sex;
import fortune.connect.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class GroupController {

  private final GroupService service;

  @PostMapping("/groups")
  public ResponseEntity<GroupResponse> save(@RequestBody CreateGroupRequest createGroupRequest) {
    CreateGroupDto createGroupDto = new CreateGroupDto(
        UUID.fromString(createGroupRequest.getOwnerId()),
        createGroupRequest.getTitle(),
        createGroupRequest.getSubtitle(),
        createGroupRequest.getInterestTag(),
        createGroupRequest.getNumOfMember(),
        createGroupRequest.getGroupMember()
            .stream().map(this::mapUserRequestToUser)
            .collect(Collectors.toList())
    );
    Group newGroup = service.createGroup(createGroupDto);
    GroupResponse response = new GroupResponse(
        newGroup.getGroupId(),
        newGroup.getOwnerId(),
        newGroup.getTitle(),
        newGroup.getSubtitle(),
        newGroup.getInterestTag(),
        newGroup.getNumOfMember(),
        newGroup.getGroupMember()
            .stream().map(this::mapUserToGroupMember)
            .collect(Collectors.toList()),
        newGroup.getCreatedAt(),
        newGroup.getUpdatedAt()
    );
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/groups/search")
  public ResponseEntity<List<GroupResponse>> searchAllGroups(
      @RequestParam(value = "num", required = false) int numOfMember) {
    List<Group> groups = service.getAllGroupsByNumberOfMember(numOfMember);
    List<GroupResponse> response = groups.stream().map(this::mapGroupToGroupResponse).collect(Collectors.toList());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/groups/{groupId}")
  public ResponseEntity<GroupResponse> getGroupById(@PathVariable("groupId") String groupId) {
    UUID groupId_ = UUID.fromString(groupId);
    Group returnedGroup = service.getGroupById(groupId_);
    GroupResponse response = new GroupResponse(
        returnedGroup.getGroupId(),
        returnedGroup.getOwnerId(),
        returnedGroup.getTitle(),
        returnedGroup.getSubtitle(),
        returnedGroup.getInterestTag(),
        returnedGroup.getNumOfMember(),
        returnedGroup.getGroupMember()
            .stream().map(this::mapUserToGroupMember)
            .collect(Collectors.toList()),
        returnedGroup.getCreatedAt(),
        returnedGroup.getUpdatedAt()
    );
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/groups")
  public ResponseEntity<List<GroupResponse>> getAllGroups() {
    List<Group> groups = service.getAllGroups();
    List<GroupResponse> response = groups.stream().map(this::mapGroupToGroupResponse).collect(Collectors.toList());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private User mapUserRequestToUser(UserRequest request) {
    return new User(
        UUID.fromString(request.getUserId()),
        UUID.fromString(request.getPictureId()),
        request.getUsername(),
        new Email(request.getEmail()),
        request.getStudentId(),
        Sex.valueOf(request.getSex()),
        request.getDepartment(),
        request.getIntroduction()
    );
  }

  private GroupResponse mapGroupToGroupResponse(Group group) {
    return new GroupResponse(
        group.getGroupId(),
        group.getOwnerId(),
        group.getTitle(),
        group.getSubtitle(),
        group.getInterestTag(),
        group.getNumOfMember(),
        group.getGroupMember()
            .stream().map(this::mapUserToGroupMember)
            .collect(Collectors.toList()),
        group.getCreatedAt(),
        group.getUpdatedAt()
    );
  }

  private GroupMember mapUserToGroupMember(User user) {
    return new GroupMember(
        user.getUserId().toString(),
        user.getUsername(),
        user.getStudentId(),
        user.getSex().toString(),
        user.getDepartment(),
        user.getIntroduction()
    );
  }
}
