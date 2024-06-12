package com.ensa.jibi.service;


import com.ensa.jibi.request.AuthenticationRequest;
import com.ensa.jibi.request.AuthenticationResponse;
import com.ensa.jibi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationResponse signInAgent(AuthenticationRequest authRequest) throws Exception {
        return signIn(authRequest);
    }

    public AuthenticationResponse signInBackOffice(AuthenticationRequest authRequest) throws Exception {
        return signIn(authRequest);
    }

    private AuthenticationResponse signIn(AuthenticationRequest authRequest) throws Exception {
        System.out.println(" im in the sign in finction");
        authenticate(authRequest.getUsername(), authRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");
        return new AuthenticationResponse(jwt,role);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }
    }
}
