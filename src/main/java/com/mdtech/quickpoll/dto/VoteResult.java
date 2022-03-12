package com.mdtech.quickpoll.dto;

import java.util.Collection;

public class VoteResult {
	private int totalVote;
	private Collection<OptionCount> results;
	
	public int getTotalVote() {
		return totalVote;
	}
	public void setTotalVote(int totalVote) {
		this.totalVote = totalVote;
	}
	public Collection<OptionCount> getResults() {
		return results;
	}
	public void setResults(Collection<OptionCount> results) {
		this.results = results;
	}
	
}
