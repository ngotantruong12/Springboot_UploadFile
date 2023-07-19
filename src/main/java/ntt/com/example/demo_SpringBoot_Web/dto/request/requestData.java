package ntt.com.example.demo_SpringBoot_Web.dto.request;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class requestData {

    @NotNull(message = "Object is required")
    private JSONObject object;

    @NotNull(message = "Image is required")
//    @Size(max = 5 * 1024 * 1024, message = "Image file size should be less than 5MB")
    private MultipartFile image;

}
