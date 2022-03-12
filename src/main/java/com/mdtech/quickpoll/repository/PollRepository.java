package com.mdtech.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.mdtech.quickpoll.domain.Poll;

public interface PollRepository extends CrudRepository<Poll, Long> {

}
