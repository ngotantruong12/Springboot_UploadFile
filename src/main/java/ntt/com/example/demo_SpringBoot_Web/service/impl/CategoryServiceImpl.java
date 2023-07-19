package ntt.com.example.demo_SpringBoot_Web.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ntt.com.example.demo_SpringBoot_Web.dto.request.RequestCategory;
import ntt.com.example.demo_SpringBoot_Web.dto.respone.ResponseCategory;
import ntt.com.example.demo_SpringBoot_Web.entity.CategoryEntity;
import ntt.com.example.demo_SpringBoot_Web.exception.NotFoundException;
import ntt.com.example.demo_SpringBoot_Web.mapper.CategoryMapper;
import ntt.com.example.demo_SpringBoot_Web.repository.CategoryRepo;
import ntt.com.example.demo_SpringBoot_Web.service.FilesStorageService;
import ntt.com.example.demo_SpringBoot_Web.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private FilesStorageService filesStorageService;
	
	@Override
	public List<ResponseCategory> getALl() {
		return CategoryMapper.ToCategoryDto(categoryRepo.findAll());
	}
//
	@Override
	public ResponseCategory getOneByID(Long id) {
		Optional<CategoryEntity> category = categoryRepo.findById(id);
		if(!category.isPresent()) {
			throw new NotFoundException("Not found Category with id: " + id);
		}
		return CategoryMapper.ToCategoryDto(category.get());
	}

//
	@Override
////	@Transactional
	public ResponseCategory updateCategory(RequestCategory dto, MultipartFile image ,Long id) {
		Optional<CategoryEntity> oldEntity = categoryRepo.findById(id);
		if(!oldEntity.isPresent()) {
			throw new NotFoundException("Not found Category with id: " + id);
		}
		else {	 
			String generatedFileName = filesStorageService.storeFile(image);
			oldEntity.get().setImage(generatedFileName);
			CategoryEntity entity = categoryRepo.save(CategoryMapper.ToCategoryEntity(oldEntity.get(), dto));
			return CategoryMapper.ToCategoryDto(entity);
		}
	}
////
	@Override
	public void deleteCategory(Long id) {
		Optional<CategoryEntity> oldEntity = categoryRepo.findById(id);
		if(!oldEntity.isPresent()) {
			throw new NotFoundException("Not found Category with id: " + id);
		}
		if(!filesStorageService.delete(oldEntity.get().getImage())) {
			throw new NotFoundException("Not found Image with name: " + oldEntity.get().getImage());
			
		}
		categoryRepo.deleteById(id);
	}
	
	@Override
	public ResponseCategory create(RequestCategory request , String imageName) {
		try {
			CategoryEntity newEntity = categoryRepo.save(CategoryMapper.ToCategoryEntity(request, imageName));
			return CategoryMapper.ToCategoryDto(newEntity);
		} catch (DataIntegrityViolationException e) {
			 throw new DataIntegrityViolationException("Name already exists");
		}
		
	}

	
	
	
//	@Override
//	public List<CategoryDTO> getALl() {
//		List<CategoryEntity> listCate = cateRepo.findAll();
//		//users.stream().map((user) -> AutoUserMapper.MAPPER.mapToUserDto(user))
////        .collect(Collectors.toList());
////		return listCate.stream().map((cate) -> AutoUserMapper.MAPPER.mapToCategoryDTO(cate))
////				.collect(Collectors.toList()); 
//		return listCate.stream().map(cate -> AutoUserMapper.MAPPER.categoryEntityToCategoryDTO(cate)).collect(Collectors.toList()) ; 
//	}

}
