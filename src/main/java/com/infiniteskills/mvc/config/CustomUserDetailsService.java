/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.config;


import com.infiniteskills.mvc.repository.UsersRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;


/**
 *
 * @author соколов
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{
        
        @Autowired
	private UsersRepository userService;
	
        
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		 com.infiniteskills.mvc.entity.Users user = userService.findUserByLogin(userName);   
                List<String> list = new ArrayList<String>();               
                list.add(user.getIdtypeuser().getName());   
		if(user == null){
                   
			throw new UsernameNotFoundException("UserName "+userName+" not found");
		}
                
                
                List<GrantedAuthority> authorities = buildUserAuthority(list);
		//return new SecurityUser(user);
                 
             return buildUserForAuthentication(user, authorities);
	}
        
        
        // Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(com.infiniteskills.mvc.entity.Users user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Collection<String> userRoles) {
                
                
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		// Build user's authorities
		for (String userRole : userRoles) {
                        System.out.println(userRole);
			setAuths.add(new SimpleGrantedAuthority(userRole));
		}
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
        
}
