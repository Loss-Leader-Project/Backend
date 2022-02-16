package lossleaderproject.back.store.entitiy;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class StoreDetail  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String storePhoneNumber;
    private String operatingTime;
    private String operatingPeriod;
    private String roadAddress;
    private Float latitude;
    private Float longitude;



    @OneToOne()  // 지연 로딩 안하면 업체와 관련된 쿠폰도 select됨
    @JoinColumn(name = "storeId")  // jpa에서 연관관계가 있으면 연관관계 주인 한테 joincolumn 필요
    private Store store;



    public StoreDetail(String storePhoneNumber, String operatingTime, String operatingPeriod, String roadAddress, Float latitude, Float longitude ) {
        this.storePhoneNumber = storePhoneNumber;
        this.operatingTime = operatingTime;
        this.operatingPeriod = operatingPeriod;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
