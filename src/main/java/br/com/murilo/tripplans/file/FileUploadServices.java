package br.com.murilo.tripplans.file;

import br.com.murilo.tripplans.config.FileStorageConfig;
import br.com.murilo.tripplans.connection.Connection;
import br.com.murilo.tripplans.connection.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServices {
    private final Path fileStorageLocation;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    public FileUploadServices(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public String storeFile(MultipartFile file, UUID connectionId) {
        String fileName = file.getOriginalFilename();
        try {
            Connection connection = connectionRepository.findById(connectionId)
                    .orElseThrow(() -> new RuntimeException("Connection not found"));

            // Define the file path
            assert fileName != null;
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            // Copy the file to the target location
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            MyFile fileEntity = new MyFile();
            fileEntity.setFileName(fileName);
            fileEntity.setFileType(file.getContentType());
            fileEntity.setData(file.getBytes());
            fileEntity.setFilePath(targetLocation.toString()); // Set the file path
            fileEntity.setConnection(connection);

            connection.getFiles().add(fileEntity);
            connectionRepository.save(connection);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String getFileStorageLocation() {
        return this.fileStorageLocation.toString();
    }

    public Path getFilePathByConnectionAndFileName(UUID connectionId, String fileName) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        MyFile fileEntity = connection.getFiles().stream()
                .filter(file -> file.getFileName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("File not found in connection"));

        return Paths.get(fileEntity.getFilePath()).normalize();
    }
}