package lossleaderproject.back.store.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
@Entity
@Getter
@RequiredArgsConstructor
public class StoreFoodImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeDetailId")
    @JsonIgnore
    private StoreDetail storeDetail;

    @Column(updatable = false)
    private String image;
    private String name;

    //private ModelMapper modelMapper = new ModelMapper();

    public void setStoreDetail(StoreDetail storeDetail) {
        this.storeDetail = storeDetail;
    }

    public StoreFoodImage(String image, String name){
        this.image = image;
        this.name = name;
    }

}
