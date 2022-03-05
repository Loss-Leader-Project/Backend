package lossleaderproject.back.store.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class StoreHashTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "storeDetailId")
    @JsonIgnore
    private StoreDetail storeDetail;

    public void setStoreDetail(StoreDetail storeDetail) {
        this.storeDetail = storeDetail;
    }

    public StoreHashTag(String name){
        this.name = name;
    }
}