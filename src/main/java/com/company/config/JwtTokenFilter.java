package com.company.config;

import com.company.dto.JwtDTO;
import com.company.repository.CompanyRepository;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {


    private final ProfileRepository profileRepository;
    private final CompanyRepository companyRepository;
    private final CustomUserDetailService customUserDetailService;

    public JwtTokenFilter(ProfileRepository profileRepository, CompanyRepository companyRepository, UserDetailsService userDetailsService, CustomUserDetailService customUserDetailService) {
        this.profileRepository = profileRepository;
        this.companyRepository = companyRepository;

        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();
            JwtDTO dto = JwtUtil.decode(token);
            CustomUserDetails userDetails = new CustomUserDetails(dto);


            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader("Message", "Some Error");
            return;
        }


    }


}

