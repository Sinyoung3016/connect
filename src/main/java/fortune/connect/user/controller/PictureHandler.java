package fortune.connect.user.controller;

import fortune.connect.user.domain.Picture;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class PictureHandler {

  private final static String absolutePath = new File("").getAbsolutePath() + "/pictures/";

  public Picture savePicture(MultipartFile file, String userPhotoName) {
    String extension = "." + userPhotoName.split("\\.")[1];
    File rootPath = new File(absolutePath);
    UUID newFileId = UUID.randomUUID();

    if (!rootPath.exists()) {
      rootPath.mkdirs();
    }

    String filepath = absolutePath + newFileId + extension;
    File picture = new File(filepath);

    try {
      file.transferTo(picture);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return new Picture(newFileId, filepath);
  }
}
