package jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryJWT {
    public static void main(String[] args) {
        try {
            //token令牌
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoi5p2O5Y2O5piOIiwiZXhwIjoxNTU2MTk2MTcyLCJpYXQiOjE1NTYxOTYxMTIsInJvbGVzIjoicm9vdO-8mnN0dWRlbnQiLCJ0ZWwiOiIxNzYyMTAyMDUyMyJ9.t_8y8dF3TSzfSAGK81rLiHjlfxG1M287UJMWYtvJvI4";
            Claims body = Jwts.parser().setSigningKey("huahuaCommunity")
                    .parseClaimsJws(token).getBody();
            System.out.println("用户的id："+body.getId());
            System.out.println("用户的名称："+body.getSubject());
            System.out.println("用户的角色："+body.get("roles"));
            System.out.println("用户的手机："+body.get("tel"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("系统的时间："+simpleDateFormat.format(new Date()));
            System.out.println("过期的时间："+simpleDateFormat.format(body.getExpiration()));
            System.out.println("签发的时间："+simpleDateFormat.format(body.getIssuedAt()));
        } catch (Exception e) {
            System.out.println("签明认证失效，请重新获取");
        }
    }
}
