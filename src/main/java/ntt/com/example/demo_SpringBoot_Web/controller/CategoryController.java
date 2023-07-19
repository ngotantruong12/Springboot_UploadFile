package ntt.com.example.demo_SpringBoot_Web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import ntt.com.example.demo_SpringBoot_Web.dto.request.RequestCategory;
import ntt.com.example.demo_SpringBoot_Web.entity.ResponeObject;
import ntt.com.example.demo_SpringBoot_Web.exception.NotFoundException;
import ntt.com.example.demo_SpringBoot_Web.service.FilesStorageService;
import ntt.com.example.demo_SpringBoot_Web.service.impl.CategoryServiceImpl;
//import ntt.com.example.demo_SpringBoot_Web.service.impl.CategoryServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping (path = "/category")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryService;
	
	@Autowired
	private FilesStorageService filesStorageService ;
	
	@GetMapping("")
	ResponseEntity<ResponeObject> findAllCategory(){
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponeObject("OK", "Query Category Sucess ", categoryService.getALl()));
	}
	
	@GetMapping("/files/{fileName:.+}")
//      /files/06a290064eb94a02a58bfeef36002483.png
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = filesStorageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }
	 
//    @GetMapping("/test") // test MvcUriComponentsBuilder
//    public String test(String name) { // "http://localhost:8080/category/test?name=43dcf4e3a6ec4a309cc1bb7742c74edb.jpg"
//       return name;
//           
//    }
    
	@PostMapping()
	ResponseEntity<ResponeObject> image(@Valid @RequestPart("request")RequestCategory request,
										@RequestParam("image") MultipartFile image ){
		if(image == null || image.isEmpty()) {
			throw new NotFoundException("The multipart of http request is required.");
		}
		String generatedFileName = filesStorageService.storeFile(image);		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponeObject("OK", "Create Category Sucess ",categoryService.create(request, generatedFileName)));
	}

	@DeleteMapping("/{id}")
	ResponseEntity<ResponeObject> deleteCategory (@PathVariable("id") Long id){	
		categoryService.deleteCategory(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body(new ResponeObject("OK", "Update Category Sucess ", ""));
	}
	
	@PutMapping("/{id}")
	ResponseEntity<ResponeObject> updateCategory(@Valid @RequestPart("request") RequestCategory request,
			@RequestParam("image") MultipartFile image , @PathVariable("id") Long id){
		if(image == null || image.isEmpty()) {
			throw new NotFoundException("The multipart of http request is required.");
		}
//		String generatedFileName = filesStorageService.storeFile(image);	
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponeObject("OK", "Update Category Sucess ", categoryService.updateCategory(request,image, id)));
	}

}
