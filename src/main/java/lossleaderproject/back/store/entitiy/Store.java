package lossleaderproject.back.store.entitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.entity.Address;
import lossleaderproject.back.user.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@RequiredArgsConstructor
@DynamicInsert
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String briefAddress;
    private String storeName;
    private String thumbnailImageUrl;

//    @ColumnDefault("0") //default 0
//    private Integer reviewCount;
//    private Float avgStar;

    @Column(columnDefinition = "integer default 0")
    private Integer reviewCount;

    @Column(columnDefinition = "Float default 0")
    private Float avgStar;

//    @ColumnDefault()

    @OneToOne()  // 지연 로딩 안하면 업체와 관련된 쿠폰도 select됨
    @JoinColumn(name = "couponId")  // jpa에서 연관관계가 있으면 연관관계 주인 한테 joincolumn 필요
    private Coupon coupon;

    @Embedded // 임베디드 타입을 사용하는곳에 사용(생략 가능)
    private  ServiceMethod serviceMethod;
//    private Coupon coupon;

    public void savedCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Store( String briefAddress, String storeName,String thumbnailImageUrl,ServiceMethod serviceMethod) {
        this.briefAddress = briefAddress;
        this.storeName = storeName;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.serviceMethod = serviceMethod;
    }
}
