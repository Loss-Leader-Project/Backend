package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lossleaderproject.back.store.entitiy.StoreHashTag;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreHashTagRequest {
    @NotNull(message = "이미지 태그를 필수로 입력하셔야됩니다.")
    private String name;


    public StoreHashTag storeHashTagRequestToEntity(){
        return new StoreHashTag(this.name);
    }
}
