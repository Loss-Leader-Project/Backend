package lossleaderproject.back.store.dto;

import lombok.*;
import lossleaderproject.back.store.entitiy.StoreFoodImage;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreFoodImageRequest {

    @NotNull(message = "이미지를 필수로 입력하셔야됩니다.")
    private MultipartFile image;

    private String imageIdentify;
    @NotNull(message = "이미지 태그를 필수로 입력하셔야됩니다.")
    private String name;


    public StoreFoodImage storeFoodImageRequestToEntity(){
        return new StoreFoodImage(this.imageIdentify,this.name);
    }
}
