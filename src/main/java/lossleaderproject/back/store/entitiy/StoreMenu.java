package lossleaderproject.back.store.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
@Entity
@Getter
@RequiredArgsConstructor
public class StoreMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩 안하면 업체와 관련된 쿠폰도 select됨
    @JoinColumn(name = "storeDetailId")  // jpa에서 연관관계가 있으면 연관관계 주인 한테 joincolumn 필요
    @JsonIgnore
    private StoreDetail storeDetail;

    @Column(updatable = false)
    private String name;
    private Integer price;

    public void setStoreDetail(StoreDetail storeDetail) {
        this.storeDetail = storeDetail;
    }

    public StoreMenu(String name, Integer price){
        this.name = name;
        this.price = price;
    }
}