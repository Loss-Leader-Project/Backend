package lossleaderproject.back.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.store.entitiy.BaseEntity;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    @JsonIgnore
    private Review review;

    private String imageIdentify;

    public void setReview(Review review) {
        this.review = review;
    }

    public ReviewImage(String imageIdentify) {
        this.imageIdentify = imageIdentify;
    }
}
