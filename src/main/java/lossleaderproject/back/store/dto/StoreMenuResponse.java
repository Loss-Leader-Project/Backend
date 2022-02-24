package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreMenuResponse {
    @NotNull(message = "아이디를 필수로 입력 하셔야 합니다.")
    private Long id;
    @NotNull(message = "메뉴 이름을 필수로 입력하셔야됩니다.")
    private String name;
    @NotNull(message = "가격을필수로 입력하셔야됩니다.")
    private Integer price;

}
