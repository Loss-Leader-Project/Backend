package lossleaderproject.back.security.oauth.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lossleaderproject.back.security.auth.PrincipalDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {
    public String create(PrincipalDetails principalDetails) {
        PrincipalDetails principal = principalDetails;
        int sec = 1800000;
        String token = JWT.create()
                .withSubject("lossleader")
                .withExpiresAt(new Date(System.currentTimeMillis() + (sec)))
                .withClaim("id", principal.getUser().getId())
                .withClaim("loginId", principal.getUser().getLoginId())
                .sign(Algorithm.HMAC512("lossleader"));
        return token;
    }

    public String emailCreate(int num) {
        int sec = 60000 * 3 + 30000;
        String token = JWT.create()
                .withSubject("lossleader")
                .withExpiresAt(new Date(System.currentTimeMillis() + (sec)))
                .withClaim("emailNumber",num)
                .sign(Algorithm.HMAC512("lossleader"));
        return token;
    }

}