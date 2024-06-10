package com.ensa.jibi.controller;


import com.ensa.jibi.request.AuthenticationRequest;
import com.ensa.jibi.request.AuthenticationResponse;
import com.ensa.jibi.service.AgentDetailsService;
import com.ensa.jibi.service.AuthService;
import com.ensa.jibi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AgentDetailsService agentDetailsService;
    @Autowired
    private AuthService authService;

//    @Autowired
//    private AgentDetailsService agentDetailsService;
//
//    @PostMapping("/authenticate")
//    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//        );
//
//        final UserDetails userDetails = agentDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails);
//
//        return new AuthenticationResponse(jwt);
//    }
@PostMapping("/authenticate")
//AuthenticationResponse
public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    System.out.println("Received authentication request for user: " + authenticationRequest.getUsername());
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        System.out.println("Authentication successful for user: " + authenticationRequest.getUsername());
    } catch (Exception e) {
        System.out.println("Authentication failed for user: " + authenticationRequest.getUsername());
        throw new Exception("Incorrect username or password", e);
    }


    final UserDetails userDetails = agentDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);
    System.out.println("Generated JWT: " + jwt);
    //get role
    String role = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse("");

   return new AuthenticationResponse(jwt,role);
}
    @PostMapping("/signin/agent")
    public ResponseEntity<AuthenticationResponse> signInAgent(@RequestBody AuthenticationRequest authRequest) throws Exception {
        AuthenticationResponse response = authService.signInAgent(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin/backoffice")
    public ResponseEntity<AuthenticationResponse> signInBackOffice(@RequestBody AuthenticationRequest authRequest) throws Exception {
        AuthenticationResponse response = authService.signInBackOffice(authRequest);
        return ResponseEntity.ok(response);
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
