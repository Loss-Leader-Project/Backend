package lossleaderproject.back.store.dto;

import lossleaderproject.back.store.entitiy.StoreImage;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class StoreImageRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String imageUrl;
    private String name;

    public StoreImage storeImageRequestEntitiy(){
        return new StoreImage(this.imageUrl,this.name);
    }
}
