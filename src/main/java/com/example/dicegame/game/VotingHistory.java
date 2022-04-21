package com.example.dicegame.game;

import java.util.ArrayList;

public class VotingHistory {
    private ArrayList<Vote> votes = new ArrayList<>();

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public String convertToJSON() {
        String result = "[{";

        result += "\"votes\": [";

        for (int i = 0; i < votes.size(); i++) {
            result += votes.get(i).convertToJSON();
            if (i < votes.size() - 1) {
                result += ",";
            }
        }
        result += "]";

        result += "}]";
        return result;
    }
}
