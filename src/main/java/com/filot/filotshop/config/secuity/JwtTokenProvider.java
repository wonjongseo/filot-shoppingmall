package com.filot.filotshop.config.secuity;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.user.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static String sampleSecretKey = "M@MK#KAKASKDASMDKASDKAMSKDMK!#NDJANCJSKQWMDKASD";
    private long tokenValidMiliseconds = 1000L * 60 * 60 * 24 * 365;
    private final UserDetailsService userDetailsService;
    private  final UserRepository userRepository;
    public static String getUserEmail(HttpServletRequest request){
        String token = resolveToken(request);
        return getUserPk(token);
    }


    public  User getUserByToken(HttpServletRequest request) {
        String userEmail = getUserEmail(request);

        return userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @PostConstruct
    public void init(){
        sampleSecretKey = Base64.getEncoder().encodeToString(sampleSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userPk, List<String > roles){

        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMiliseconds))
                .signWith(SignatureAlgorithm.HS256, sampleSecretKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public static String getUserPk(String token){
        return Jwts.parser().setSigningKey(sampleSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public static String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(sampleSecretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }
}
