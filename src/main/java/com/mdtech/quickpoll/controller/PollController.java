package com.mdtech.quickpoll.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mdtech.quickpoll.domain.Poll;
import com.mdtech.quickpoll.repository.PollRepository;
import com.mdtech.quickpoll.services.PollService;

@RestController
public class PollController {

	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private PollService pollService;
	
	@GetMapping("/polls")
	public ResponseEntity<Iterable<Poll>> getAllPoll() {
		Iterable<Poll> allPolls = pollRepository.findAll();
		return new ResponseEntity<>(allPolls, HttpStatus.OK);
	}
	
	@PostMapping("/polls")
	public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {
		System.out.println(poll);
		poll = pollRepository.save(poll);
		
		//set the location header for newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(poll.getId())
							.toUri();
		
		responseHeaders.setLocation(newPollUri);
		
		return new ResponseEntity<> (null, responseHeaders, HttpStatus.CREATED);
	}
	
	@GetMapping("/polls/{pollId}")
	public ResponseEntity<?> getPoll(@PathVariable Long pollId) throws Exception {
		return new ResponseEntity<> (pollService.verifyPoll(pollId), HttpStatus.OK);
	}
	
	@PutMapping("/polls/{pollId}")
	public ResponseEntity<?> updatePoll(@Valid @PathVariable Long pollId, @RequestBody Poll poll) {
		pollService.verifyPoll(pollId);
		pollRepository.save(poll);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
	@DeleteMapping("/polls/{pollId}")
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
		pollService.verifyPoll(pollId);
		pollRepository.deleteById(pollId);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
}
