package com.mdtech.quickpoll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdtech.quickpoll.domain.Vote;
import com.mdtech.quickpoll.dto.VoteResult;
import com.mdtech.quickpoll.repository.VoteRepository;
import com.mdtech.quickpoll.services.ComputeResultService;

@RestController
public class ComputeResultController {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private ComputeResultService computeResultService;
	
	@GetMapping("/computeresult")
	public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
		Iterable<Vote> allVotes = voteRepository.findBypoll(pollId);
		VoteResult voteResult = computeResultService.voteCountResult(allVotes);
		return new ResponseEntity<VoteResult> (voteResult, HttpStatus.OK);
	}
	
	
}
