package com.edu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edu.constant.SystemConstant;
import com.edu.dto.MyUser;
import com.edu.entity.RoleEntity;
import com.edu.entity.UserEntity;
import com.edu.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findOneByUserNameAndStatus(username, SystemConstant.ACTIVE_STATUS);
		if (userEntity==null) {
			throw new UsernameNotFoundException("User not found");
			//return null;
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (RoleEntity role: userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}	
		MyUser myuser = new MyUser(userEntity.getUserName() , userEntity.getPassword() , true, true, true, true, authorities);
		myuser.setfullName(userEntity.getFullName());
		myuser.setId(userEntity.getId());
		return myuser;
	
	}
}
