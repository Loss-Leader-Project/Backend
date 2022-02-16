package lossleaderproject.back.store.entitiy;

import javax.persistence.*;

public class StoreImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne()  // 지연 로딩 안하면 업체와 관련된 쿠폰도 select됨
    @JoinColumn(name = "storeDetailId")  // jpa에서 연관관계가 있으면 연관관계 주인 한테 joincolumn 필요
    private StoreDetail storeDetail;

    @Column(updatable = false)
    private String imageUrl;
    private String name;

    public StoreImage(String imageUrl, String name){
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
