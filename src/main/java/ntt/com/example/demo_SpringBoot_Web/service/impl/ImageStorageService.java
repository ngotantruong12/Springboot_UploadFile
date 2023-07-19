package ntt.com.example.demo_SpringBoot_Web.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ntt.com.example.demo_SpringBoot_Web.controller.CategoryController;
import ntt.com.example.demo_SpringBoot_Web.service.FilesStorageService;

@Service
public class ImageStorageService implements FilesStorageService {
	private final Path storageFolder = Paths.get("uploads");
    //constructor
    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException exception) {
            throw new RuntimeException("Cannot initialize storage", exception);
        }
    }
    private boolean isImageFile(MultipartFile file) {
//    	System.out.println("file >>>" + file.getOriginalFilename());
        //Let install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        // Phương thức này được sử dụng để tạo một đối tượng List từ một mảng có sẵn
        return Arrays.asList(new String[] {"png","jpg","jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase()); // arr k the check contain -> chuyen sang list
    }
    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("haha");
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            //check file is image ?
            if(!isImageFile(file)) {
                throw new RuntimeException("You can only upload image file");
            }
            //file must be <= 5Mb
//            System.out.println("size: " + file.getSize());
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if(fileSizeInMegabytes > 5.0f) {
                throw new RuntimeException("File must be <= 5Mb");
            }
            //File must be rename, why ?
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());// get type file
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName+"."+fileExtension;
            Path destinationFilePath = this.storageFolder.resolve( //destinationFilePath: E:\SpringBoot\Java_image\Uploads\1bf49d7d3a8b4490bbe28cb8302b7f2b.jpg
                            Paths.get(generatedFileName))
                    .normalize().toAbsolutePath(); // bỏ hàng này -> uploads\96196ffff06748a38acfe93aae383d3c.jpg
//            System.out.println(destinationFilePath.getParent());  E:\SpringBoot\Java_image\Uploads
//            System.out.println(this.storageFolder.toAbsolutePath()); E:\SpringBoot\Java_image\Uploads
//            System.out.println(this.storageFolder); uploads
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            // url file name
            // fromMethodName (controller,find readDetailFile method, argument ) 
            String urlPath = MvcUriComponentsBuilder.fromMethodName(CategoryController.class,
                    "readDetailFile", generatedFileName.toString()).build().toUri().toString();
            return urlPath;
        }
        catch (IOException exception) {
            throw new RuntimeException("Failed to store file.", exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            //list all files in storageFolder
            //How to fix this ?
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load stored files", e);
        }

    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
//            System.out.println(file); uploads\ed8468ea83c342ecbc4e44290ec1d788.jpg
            Resource resource = new UrlResource(file.toUri());
//            System.out.println(resource); URL [file:///E:/SpringBoot/TestImage/demo_SpringBoot_Web/uploads/ed8468ea83c342ecbc4e44290ec1d788.jpg]
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException(
                        "Could not read file: " + fileName);
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public void deleteAllFiles() {
    	
    }
	@Override
	public boolean delete(String filename) {
//		http://localhost:8080/api/v1/FileUpload/files/da019d208f1b4f389584209fc5476024.jpg
		// [0] : http://localhost:8080/api/v1/FileUpload/
		// [1] da019d208f1b4f389584209fc5476024.JPG
		try {
			String[] name = filename.split("files/");
	      Path file = this.storageFolder.resolve(name[1]);
//	      System.out.println(file); uploads\85066c424298427b9769b290a84153bf.jpg
	      return Files.deleteIfExists(file);
	    } catch (IOException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	}

}
