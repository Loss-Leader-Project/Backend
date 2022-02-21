package lossleaderproject.back.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.user.entity.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class StoreOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders orders;

    public StoreOrder(Orders orders,Store store, User user) {
        this.orders = orders;
        this.store = store;
        this.user = user;
    }


}
