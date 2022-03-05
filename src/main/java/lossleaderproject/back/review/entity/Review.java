package lossleaderproject.back.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.order.entity.Orders;
import lossleaderproject.back.store.entitiy.BaseEntity;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.user.entity.User;

import javax.persistence.*;
import java.util.List;
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    @JsonIgnore
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Orders orders;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    private Float star;


    private String title;

    private String content;

    public void setStore(Store store) {
        this.store = store;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review(Float star,String title, String content) {
        this.star = star;
        this.title = title;
        this.content = content;
    }
}
