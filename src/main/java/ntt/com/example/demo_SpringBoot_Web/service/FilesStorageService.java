package ntt.com.example.demo_SpringBoot_Web.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
	
	public String storeFile(MultipartFile file);
    public Stream<Path> loadAll(); //load all file inside a folder
    public byte[] readFileContent(String fileName);
    public void deleteAllFiles();
    public boolean delete(String filename);
}
