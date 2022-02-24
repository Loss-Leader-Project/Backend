package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreFoodImageResponse {
    @NotNull(message = "아이디가 있어야 합니다")
    private Long id;
    @NotNull(message = "이미지를 필수로 입력하셔야됩니다.")
    private String image;
    @NotNull(message = "이미지 태그를 필수로 입력하셔야됩니다.")
    private String name;

}
