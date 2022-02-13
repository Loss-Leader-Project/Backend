package lossleaderproject.back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderResponse {
    private String userName;
    private String phoneNumber;
    private LocalTime visitTime;
    private Integer visitCount;
}
