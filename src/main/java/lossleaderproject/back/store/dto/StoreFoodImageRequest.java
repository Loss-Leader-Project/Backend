package lossleaderproject.back.store.dto;

import lombok.*;
import lossleaderproject.back.store.entitiy.StoreFoodImage;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreFoodImageRequest {

    @NotNull(message = "이미지를 필수로 입력하셔야됩니다.")
    private String image;
    @NotNull(message = "이미지 태그를 필수로 입력하셔야됩니다.")
    private String name;


    public StoreFoodImage storeFoodImageRequestToEntity(){
        return new StoreFoodImage(this.image,this.name);
    }
}
