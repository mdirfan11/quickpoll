package com.mdtech.quickpoll.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mdtech.quickpoll.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {

	@Query(value="select v.* from option o, vote v where o.poll_id = ?1 and v.option_id = o.option_id", nativeQuery = true)
	public Iterable<Vote> findBypoll(Long pollId);
}
