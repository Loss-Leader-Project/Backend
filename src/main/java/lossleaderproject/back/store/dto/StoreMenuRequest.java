package lossleaderproject.back.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lossleaderproject.back.store.entitiy.StoreMenu;

import javax.validation.constraints.NotNull;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreMenuRequest {
    @NotNull(message = "메뉴 이름을 필수로 입력하셔야됩니다.")
    private String name;
    @NotNull(message = "가격을필수로 입력하셔야됩니다.")
    private Integer price;


    public StoreMenu storeMenuRequestToEntity(){
        return new StoreMenu(this.name,this.price);
    }
}
