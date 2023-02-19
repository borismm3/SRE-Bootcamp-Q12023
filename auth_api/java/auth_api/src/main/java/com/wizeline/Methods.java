package com.wizeline;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Methods {
  static MysqlDBClient mysqlDBClient = MysqlDBClient.getInstance();
  private static String token = "";
  public static String generateToken(String username, String password)  {
    String roleDb = mysqlDBClient.validationCredential(username, password);
    if (roleDb == null) return "ERROR 403 Invalid user";

    String secretKey = "my2w7wjd7yXF64FIADfJxNs1oupTGAuW";
    Claims claims = Jwts.claims();
    claims.put("role", roleDb);
    token = Jwts.builder()
            .setHeaderParam("alg", "HS256")
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
            .compact();
    return token;
  }

  public static String accessData(String authorization){
    if (authorization.equals("Bearer " + token)) return "You are under protected data";
    return "403 ERROR Invalid token";
  }
}

