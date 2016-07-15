package com.pgs.soft.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pgs.soft.domain.Hobby;
import com.pgs.soft.domain.User;
import com.pgs.soft.domain.UserProfile;
import com.pgs.soft.dto.UserProfileDTO;
import com.pgs.soft.repository.HobbyRepository;
import com.pgs.soft.repository.UserProfileRepository;
import com.pgs.soft.repository.UserRepository;
import com.pgs.soft.service.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	HobbyRepository hobbyRepository;

	@Override
	public void save(UserProfileDTO userProfileDTO) {
		User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findOneByEmail(loggedUser.getEmail()).get();
		UserProfile userProfile = user.getUserProfile();
		map(userProfileDTO, userProfile);
		userProfileRepository.save(userProfile);
	}
	
	private void map(UserProfileDTO userProfileDTO, UserProfile userProfile){
		userProfile.setName(userProfileDTO.getName());
		userProfile.setSurname(userProfileDTO.getSurname());
		userProfile.setCity(userProfileDTO.getCity());
		userProfile.setStreet(userProfileDTO.getStreet());
		userProfile.setHomeNumber(userProfileDTO.getHomeNumber());
		userProfile.setPostCode(userProfileDTO.getPostCode());	
		
		for(String hobby : userProfileDTO.getHobbies()){
			Optional<Hobby> optionalHobby = hobbyRepository.findOneByHobbyName(hobby);
			optionalHobby.ifPresent(h -> userProfile.getHobbies().add(h));
		}
	}
}
