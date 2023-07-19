package ntt.com.example.demo_SpringBoot_Web.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ntt.com.example.demo_SpringBoot_Web.dto.request.RequestCategory;
import ntt.com.example.demo_SpringBoot_Web.dto.respone.ResponseCategory;

public interface ICategoryService {
	List<ResponseCategory> getALl();
	ResponseCategory getOneByID(Long id);
	ResponseCategory create(RequestCategory request , String imageName);
	ResponseCategory updateCategory(RequestCategory dto,MultipartFile image, Long id);
	void deleteCategory(Long id);
//	requestData create(requestData request);

}
