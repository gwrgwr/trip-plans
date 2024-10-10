package br.com.murilo.tripplans.file;

import br.com.murilo.tripplans.file.VO.UploadFileVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Tag(name = "File Upload", description = "API for file upload")
@RestController
@RequestMapping("/api/file/v1")
public class FileUploadController {

    @Autowired
    private FileUploadServices fileUploadServices;

    @PostMapping("/upload/{connectionId}")
    public ResponseEntity<UploadFileVO> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("connectionId") UUID connectionId) {
        String fileName = fileUploadServices.storeFile(file, connectionId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/download/" + connectionId +"/")
                .path(fileName)
                .toUriString();

        UploadFileVO response = new UploadFileVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{connectionId}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable UUID connectionId, @PathVariable String fileName) {
        Path filePath = fileUploadServices.getFilePathByConnectionAndFileName(connectionId, fileName);
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found " + fileName, e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}