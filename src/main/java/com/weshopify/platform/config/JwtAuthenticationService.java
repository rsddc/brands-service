package com.weshopify.platform.config;

import com.weshopify.platform.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class JwtAuthenticationService {

    private RedisTemplate<String,String> redisTemplate;
    private HashOperations<String,String,String> hashOperations;

    public JwtAuthenticationService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public Authentication authenticate(HttpServletRequest httpServletRequest) {

        String accessToken = getTokenFromRequest(httpServletRequest);
        Authentication authentication=null;
        if(isValidToken(accessToken)) {
            String subject = getUserName(accessToken);
            List<GrantedAuthority> roles = getRoles(accessToken);
            authentication = new UsernamePasswordAuthenticationToken(subject, null, roles);
        }else
            throw new ApiException("Token has expired. Get a new token and try again later !! ",
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

        return authentication;
    }

    private boolean isValidToken(String accessToken){
        String exipreTime = hashOperations.get(accessToken,"EXPIRE");
        assert exipreTime != null;
        return LocalDateTime.now().isBefore(LocalDateTime.parse(exipreTime));
        }
    private String getUserName(String accessToken) {
        return hashOperations.get(accessToken,"SUBJECT");
    }

    private List<GrantedAuthority> getRoles(String accessToken){
        String rolesAsString = hashOperations.get(accessToken,"ROLES");
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(rolesAsString);
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(simpleGrantedAuthority);
        return roles;

    }

    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String accessToken = httpServletRequest.getHeader("Authorization");
        if(!StringUtils.hasText(accessToken))
            throw HttpClientErrorException.Unauthorized.create("No Authorization Header",
                    HttpStatus.FORBIDDEN,"Authorization header required for this request",
                    null,null,null);

        return accessToken.replace("Bearer ","");
    }//from incoming request extract the token from request header
}
