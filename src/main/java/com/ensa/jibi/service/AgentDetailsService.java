package com.ensa.jibi.service;

import com.ensa.jibi.model.Agent;
import com.ensa.jibi.model.BackOffice;
import com.ensa.jibi.model.Client;
import com.ensa.jibi.repository.AgentRepository;
import com.ensa.jibi.repository.BackOfficeRepository;
import com.ensa.jibi.repository.ClientRepository;
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

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Vérifie d'abord dans le repository des agents
        Optional<Agent> agentOpt = agentRepository.findByUsername(username);
        if (agentOpt.isPresent()) {
            Agent agent = agentOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    agent.getUsername(),
                    agent.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_AGENT"))
            );
        }

        // Vérifie dans le repository des backoffices
        Optional<BackOffice> backOfficeOpt = backOfficeRepository.findByUsername(username);
        if (backOfficeOpt.isPresent()) {
            BackOffice backOffice = backOfficeOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    backOffice.getUsername(),
                    backOffice.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_BACKOFFICE"))
            );
        }

        // Vérifie dans le repository des clients
        Optional<Client> clientOpt = clientRepository.findByPhone(username);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    client.getPhone(),
                    client.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT"))
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
