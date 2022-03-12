package com.mdtech.quickpoll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mdtech.quickpoll.domain.Vote;
import com.mdtech.quickpoll.repository.VoteRepository;

@RestController
public class VoteController {
	
	@Autowired
	private VoteRepository voteRepository;
	
	@PostMapping("/polls/{pollId}/votes")
	public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
		vote = voteRepository.save(vote);
		
		//Set the header for newly created source
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(vote.getId())
				.toUri()
			);
		
		return new ResponseEntity<> (null, responseHeaders, HttpStatus.CREATED);
	}
	
	@GetMapping("/polls/{pollId}/votes")
	public Iterable<Vote> getAllVote(@PathVariable Long pollId) {
		return voteRepository.findBypoll(pollId);
	}
	
}
