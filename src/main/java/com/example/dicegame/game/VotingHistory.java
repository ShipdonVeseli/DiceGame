package com.example.dicegame.game;

import java.util.ArrayList;

public class VotingHistory {
    private ArrayList<Vote> votes=new ArrayList<>();

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    public void addVote(Vote vote){
        votes.add(vote);
    }
}
