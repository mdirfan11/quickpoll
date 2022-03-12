package com.mdtech.quickpoll.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mdtech.quickpoll.domain.Vote;
import com.mdtech.quickpoll.dto.OptionCount;
import com.mdtech.quickpoll.dto.VoteResult;

@Service
public class ComputeResultService {
	
	public VoteResult voteCountResult(Iterable<Vote> votes) {
		VoteResult voteResult = new VoteResult();
		int totalVotes = 0;
		Map<Long, OptionCount> tempMap = new HashMap<Long, OptionCount> ();
		for (Vote v : votes) {
			totalVotes++;
			//Get the option count according to the option
			OptionCount optionCount = tempMap.get(v.getOption().getId());
			if (optionCount == null) {
				optionCount = new OptionCount();
				optionCount.setOptionId(v.getOption().getId());
				tempMap.put(v.getOption().getId(), optionCount);
			}
			optionCount.setCount(optionCount.getCount()+1);
		}
		voteResult.setTotalVote(totalVotes);
		voteResult.setResults(tempMap.values());
		
		return voteResult;
	}
	
}
