package lossleaderproject.back.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    public StoreOrder(User user) {
        this.user = user;
    }

    /*
  private Long storeId -> Long을 업체 객체로 변경
  @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "storeId")
    private Long store;
*/

}
