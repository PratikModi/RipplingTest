package com.rippling.test.data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class VoteRepository {

    Map<String, Set<String>> voteMap;

    public VoteRepository() {
        this.voteMap = new ConcurrentHashMap<>();
    }

    public void addVote(String questionId, String userId) {
        voteMap.putIfAbsent(questionId,new HashSet<>());
        voteMap.get(questionId).add(userId);
    }

    public Integer totalVotes(String questionId) {
        if(voteMap.containsKey(questionId)) {
            return voteMap.get(questionId).size();
        }else{
            return 0;
        }
    }

}
