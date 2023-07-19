package ntt.com.example.demo_SpringBoot_Web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "Category")
public class CategoryEntity  extends BaseEntity {
	
//	@JsonIgnore

	
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "image")
	private String image;
}
