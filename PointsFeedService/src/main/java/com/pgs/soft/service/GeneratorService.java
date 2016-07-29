package com.pgs.soft.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pgs.soft.domain.Message;
import com.pgs.soft.repository.CardRepository;

@Service
public class GeneratorService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CardRepository cardRepository;

	@Scheduled(fixedRate = 3600000)
	public void send() {

		List<String> allCardsNumbers = StreamSupport.stream(cardRepository.findAll().spliterator(), false)
				.map(c -> c.getNumber()).collect(Collectors.toList());

		Random rand = new Random();
		Message message = new Message(allCardsNumbers.get(rand.nextInt(allCardsNumbers.size())),
				rand.nextInt(1901) + 100);
		restTemplate.postForObject("http://localhost:8080/new_points", message, String.class);
	}

}
