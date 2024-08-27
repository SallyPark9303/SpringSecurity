package springCom.springJWT.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import springCom.springJWT.dto.CustomUserDetails;
import springCom.springJWT.entity.UserEntity;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    public JWTFilter(JWTUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request에서 Authorization  헤더 찾음
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("token null");
            filterChain.doFilter(request, response);

            // 조건이 해당되면 메소드 종료(필수)
            return;
        }

        String token = authorization.split(" ")[1];
        
        // 토큰 소멸 시간 검증
        if(jwtUtils.isExpired(token)){
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            // 조건이 해당되면 종료 (필수)
            return;
        
        }

        String username = jwtUtils.getUsername(token);
        String role = jwtUtils.getRole(token);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("temppassword");
        userEntity.setRole(role);

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
