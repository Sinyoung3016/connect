package fortune.connect.user.controller;

import fortune.connect.user.controller.request.CreateUserRequest;
import fortune.connect.user.controller.response.UserResponse;
import fortune.connect.user.domain.*;
import fortune.connect.user.service.UserService;
import fortune.connect.user.service.dto.CreateUserDto;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class UserController {
  private final UserService userService;
  private final PictureHandler pictureHandler;
  private static final String USER_PICTURE_URI = "/user-pictures/";

  @PostMapping("/users")
  public ResponseEntity<UserResponse> save(
      @RequestPart(value = "data") CreateUserRequest request,
      @RequestPart(value = "file") MultipartFile picture) {
    Picture userPicture = pictureHandler.savePicture(picture, request.getUserPictureName());
    CreateUserDto createUserDto = new CreateUserDto(
        request.getUsername(),
        request.getPassword(),
        new Email(request.getEmail()),
        request.getStudentId(),
        Sex.valueOf(request.getSex()),
        request.getDepartment(),
        request.getIntroduction(),
        userPicture
    );
    User savedUser = userService.createUser(createUserDto);
    UserResponse response = new UserResponse(
        savedUser.getUserId().toString(),
        savedUser.getUsername(),
        savedUser.getPassword(),
        savedUser.getEmail().address(),
        savedUser.getStudentId(),
        savedUser.getSex().toString(),
        savedUser.getDepartment(),
        savedUser.getIntroduction(),
        USER_PICTURE_URI + userPicture.getPictureId().toString()
    );
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable String userId) {
    UUID userId_ = UUID.fromString(userId);
    User savedUser = userService.getUserById(userId_);
    UserResponse response = new UserResponse(
        savedUser.getUserId().toString(),
        savedUser.getUsername(),
        savedUser.getPassword(),
        savedUser.getEmail().address(),
        savedUser.getStudentId(),
        savedUser.getSex().toString(),
        savedUser.getDepartment(),
        savedUser.getIntroduction(),
        USER_PICTURE_URI + savedUser.getPictureId().toString()
    );
    return new ResponseEntity<>(response, HttpStatus.FOUND);
  }

  @GetMapping("/user-pictures/{userId}")
  public ResponseEntity<byte[]> getImage(@PathVariable String userId) throws IOException {
    UUID userId_ = UUID.fromString(userId);
    Picture savedUserPicture = userService.getUserPictureById(userId_);
    File file = new File(savedUserPicture.getFilePath());

    HttpHeaders header = new HttpHeaders();
    header.add("Content-type", Files.probeContentType(file.toPath()));

    return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
  }
}
