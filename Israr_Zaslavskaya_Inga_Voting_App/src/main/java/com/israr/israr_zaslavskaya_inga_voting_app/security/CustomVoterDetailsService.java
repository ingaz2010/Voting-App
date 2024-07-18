package com.israr.israr_zaslavskaya_inga_voting_app.security;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.VoterDao;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Role;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Voter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomVoterDetailsService implements UserDetailsService {

    private VoterDao voterDao;

    public CustomVoterDetailsService(VoterDao voterDao){
        this.voterDao = voterDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Voter voter = voterDao.findByEmail(email);

        if (voter != null) {
//            return new org.springframework.security.core.userdetails.User(artCollector.getEmail(),
//                    artCollector.getPassword(),
//                    mapRolesToAuthorities(artCollector.getRoles()));

            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) mapRolesToAuthorities(voter.getRoles());

            System.out.println("User: " + voter.getEmail() + ", Roles: " + authorities);
            System.out.println(new org.springframework.security.core.userdetails.User(voter.getEmail(),
                    voter.getSsn(),
                    authorities));
            return new org.springframework.security.core.userdetails.User(voter.getEmail(),
                    voter.getSsn(),
                    authorities);
        }else{
            throw new UsernameNotFoundException("Invalid email or password.");
        }

    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        Collection< ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}

