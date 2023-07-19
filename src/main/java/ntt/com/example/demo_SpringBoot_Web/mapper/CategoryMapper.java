package ntt.com.example.demo_SpringBoot_Web.mapper;

import java.util.ArrayList;
import java.util.List;

import ntt.com.example.demo_SpringBoot_Web.dto.request.RequestCategory;
import ntt.com.example.demo_SpringBoot_Web.dto.respone.ResponseCategory;
import ntt.com.example.demo_SpringBoot_Web.entity.CategoryEntity;

public class CategoryMapper {
	
	//respone add , edit , findone (output)
	public static ResponseCategory ToCategoryDto(CategoryEntity entity) {
		ResponseCategory dto = new ResponseCategory();
		
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setImageName(entity.getImage());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setCreatedDate(entity.getCreatedDate());
		return dto;
		
	}
	
	// findall
	public static List<ResponseCategory> ToCategoryDto(List<CategoryEntity> entitys) {
		List<ResponseCategory> listCategory = new ArrayList<>();
		for (CategoryEntity entity : entitys) {
			listCategory.add(ToCategoryDto(entity));
		}	
		return listCategory;
		
	}
	
	// update 
	public static CategoryEntity ToCategoryEntity(CategoryEntity oldEntity, RequestCategory dto) {
		CategoryEntity newEntity = new CategoryEntity();
		newEntity.setCreatedDate(oldEntity.getCreatedDate());
		newEntity.setId(oldEntity.getId());
		newEntity.setName(dto.getName());
		newEntity.setDescription(dto.getDescription());
		newEntity.setImage(oldEntity.getImage());
		return newEntity;
		
	}
	
	// add 
	public static CategoryEntity ToCategoryEntity(RequestCategory dto, String imageName) {
		CategoryEntity newEntity = new CategoryEntity();
		newEntity.setName(dto.getName());
		newEntity.setDescription(dto.getDescription());
		newEntity.setImage(imageName);
		return newEntity;
		
	}

}
