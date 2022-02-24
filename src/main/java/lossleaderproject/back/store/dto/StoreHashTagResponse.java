package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreHashTagResponse {
    @NotNull(message = "아이디를 필수로 입력 하셔야 합니다.")
    private Long id;
    @NotNull(message = "이미지 태그를 필수로 입력하셔야됩니다.")
    private String name;
}
