package com.mdtech.quickpoll.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdtech.quickpoll.domain.Poll;
import com.mdtech.quickpoll.exception.ResourceNotFoundException;
import com.mdtech.quickpoll.repository.PollRepository;

@Service
public class PollService {
	
	@Autowired
	private PollRepository pollRepository;

	public Poll verifyPoll(Long pollId) {
		Optional<Poll> poll = pollRepository.findById(pollId);
		if(!poll.isPresent()) {
			throw new ResourceNotFoundException("Poll with id "+pollId+" not found");
		}
		return poll.get();
	}
	
}
