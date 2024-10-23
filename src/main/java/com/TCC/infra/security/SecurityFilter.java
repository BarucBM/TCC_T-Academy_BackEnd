    package com.TCC.infra.security;

    import com.TCC.repositories.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import java.io.IOException;

    @Component
    public class SecurityFilter extends OncePerRequestFilter {

        @Autowired
        private TokenService tokenService;

        @Autowired
        private UserRepository userRepository;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String token = tokenService.extractTokenFromRequest(request);


            if (token != null && !tokenService.isTokenBlacklisted(token)) {
                String email = tokenService.validateToken(token);
                UserDetails user = userRepository.findByEmail(email);


                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }


            filterChain.doFilter(request, response);
        }
    }
