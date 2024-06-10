package com.ensa.jibi.service;

import com.ensa.jibi.model.Agent;
import com.ensa.jibi.model.BackOffice;
import com.ensa.jibi.repository.AgentRepository;
import com.ensa.jibi.repository.BackOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AgentDetailsService implements UserDetailsService {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private BackOfficeRepository backOfficeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Agent agent = agentRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Agent not found"));
//        return new org.springframework.security.core.userdetails.User(agent.getUsername(), agent.getPassword(), new ArrayList<>());
        // Vérifie d'abord dans le repository des agents
        Optional<Agent> agentOpt = agentRepository.findByUsername(username);
        System.out.println("Agent  "+ agentOpt.isPresent());
        System.out.println(agentOpt);
        if (agentOpt.isPresent()) {
            System.out.println("In the loop lmao  ");
            Agent agent=agentOpt.get();
           // List<GrantedAuthority> authorities = new ArrayList<>();
           // authorities.add(new SimpleGrantedAuthority(agent.getRole()));
            return new org.springframework.security.core.userdetails.User(
                    agent.getUsername(),
                    agent.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_AGENT"))
            );
        }
        System.out.println("next part ????");
        try {
            Optional<BackOffice> backOfficeOpt = backOfficeRepository.findByUsername(username);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Optional<BackOffice> backOfficeOpt = backOfficeRepository.findByUsername(username);

        System.out.println("after checking backoffcieopt");

        // Si aucun agent n'est trouvé, vérifie dans le repository des backoffices
        System.out.println("BackOffice  "+ backOfficeOpt.isPresent());
        System.out.println(backOfficeOpt);
        if (backOfficeOpt.isPresent()) {
            BackOffice backOffice=backOfficeOpt.get();
           // List<GrantedAuthority> authorities = new ArrayList<>();
           // authorities.add(new SimpleGrantedAuthority(backOffice.getRole()));
            return new org.springframework.security.core.userdetails.User(
                    backOffice.getUsername(),
                    backOffice.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_BACKOFFICE"))
            );
        }


        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
