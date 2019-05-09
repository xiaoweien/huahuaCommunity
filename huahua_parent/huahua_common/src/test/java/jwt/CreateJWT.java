package jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJWT {
    /**
     * 功能描述：生成token(jwt)
     * @Author LiHuaMing
     * @Date 16:22 2019/4/25
     * @Param
     * @return
     */
    public static void main(String[] args) {
        JwtBuilder huahuaCommunity = Jwts.builder()//创建
                .setId("1")                        //设置id
                .setSubject("李华明")              //使用者
                .setExpiration(new Date(new Date().getTime()+60000))//设置过期时间
                .setIssuedAt(new Date())           //jwt的签发时间
                .claim("roles","root：student")
                .claim("tel","17621020523")
                .signWith(SignatureAlgorithm.HS256, "huahuaCommunity");
                //加密的算法是：HS256                                       //加密的签名是：huahuaCommunity
        System.out.println(huahuaCommunity.compact());
        //通过加盐的规则   huahuaCommunity.compact()   获取令牌
    }
}
