package lossleaderproject.back.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.entitiy.BaseEntity;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@DynamicInsert
@Getter
public class ReviewImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageIdentify;

    public ReviewImage(String imageIdentify) {
        this.imageIdentify = imageIdentify;
    }
}
