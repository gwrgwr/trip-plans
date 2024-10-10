package br.com.murilo.tripplans.file;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import br.com.murilo.tripplans.connection.Connection;

import java.util.UUID;

@Entity
public class MyFile {
    @Id
    @GeneratedValue
    @Column(name = "file_id", updatable = false, nullable = false)
    private UUID file_id;
    private String fileName;
    private String fileType;
    private String filePath;
    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "connection_id", nullable = false)
    @JsonBackReference
    private Connection connection;

    public UUID getFile_id() {
        return file_id;
    }

    public void setFile_id(UUID file_id) {
        this.file_id = file_id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    // Getters and setters
}