package lossleaderproject.back.security.jwt;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private int code;
    private String message;


}
