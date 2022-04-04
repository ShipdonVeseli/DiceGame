package com.example.dicegame;

import com.example.dicegame.game.*;
import com.example.dicegame.gameSatistic.StatisticSuspect;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Player extends StatisticSuspect {
    public static final int timeoutInSeconds = 30;


    private String playerName;
    private ArrayList<Dice> dices = new ArrayList<>();
    private ArrayList<Resource> resources = new ArrayList<>();
    private int savedResources = 0;
    private boolean isAI = false;
    private boolean hasRolledDices = false;
    private Timer timer = new Timer();
    private Game game;
    private VotingHistory votingHistory=new VotingHistory();
    private String token=null;

    public Player(String username) {
        playerName = username;
        addDice(new Dice());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token)throws IllegalStateException {
        if(this.token==null) {
            this.token = token;
        }else {
            throw new IllegalStateException();
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
        try {
            Player activePlayer = game.getActivePlayer();
           // System.out.println("active" + activePlayer.toString() + "\n" + "this=" + this.toString());
            if (AI && activePlayer.equals(this)) {
                game.aiRound();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isHasRolledDices() {
        return hasRolledDices;
    }

    public void setHasRolledDices(boolean hasRolledDices) {
        this.hasRolledDices = hasRolledDices;
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void addResources(ArrayList<Resource> additionalResources) {
        resources.addAll(additionalResources);
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void emptyResources() {
        resources = new ArrayList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void addDice(Dice dice) {
        dices.add(dice);
    }

    public void removeDice(UUID diceID) throws NoSuchElementException {
        for (Dice dice : dices) {
            if (dice.getID().equals(diceID)) {
                dices.remove(dice);
                return;
            }
        }
        throw new NoSuchElementException("No Dice with ID= " + diceID);
    }

    public void removeDice(Dice dice) {
        dices.remove(dice);
    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    public Dice getDice(Dice dice) throws NoSuchElementException {
        for (Dice diceInPlayer : dices) {
            if (dice.getID().equals(diceInPlayer.getID())) {
                return diceInPlayer;
            }
        }
        throw new NoSuchElementException();
    }

    public Dice getDice(int index) {
        return dices.get(index);
    }

    public void rollAllDices() {
        dices.forEach(e -> {
            e.roll();
        });
        saveRolledDiceValue(playerName, getSummOfDiceValues());
        hasRolledDices = true;
    }

    public int getSummOfDiceValues() {
        int result = 0;
        for (Dice dice : dices) {
            result += dice.getValue();
        }
        return result;
    }

    public String getDiceValues(){
        String result = "[";
        for (int i = 0; i < dices.size(); i++){
            result += dices.get(i).getValue();
            if(i != dices.size()-1) result+= ",";
        }
        result += "]";
        return result;
    }

    public void addedResources(int amount) {
        savedResources += amount;
    }

    public void saveMovedResources() {
        saveNumberOfMovedResources(playerName, savedResources);
        savedResources = 0;
    }

    public ArrayList<Resource> getResources(int amount) {
        ArrayList<Resource> result = new ArrayList<>();
        amount = checkSize(amount);
        for (int i = 0; i < amount; i++) {
            result.add(resources.get(i));
        }
        return result;
    }

    public int getBlueResources() {
        int counter = 0;
        for (Resource resource : resources) {
            if (resource.isBlueResource()) counter++;
        }
        return counter;
    }

    public int getNormalResources() {
        int counter = 0;
        for (Resource resource : resources) {
            if (!resource.isBlueResource()) counter++;
        }
        return counter;
    }

    private int checkSize(int amount) {
        if (amount > resources.size()) {
            amount = resources.size();
        }
        return amount;
    }

    public void removeResources(int amount) {
        amount = checkSize(amount);
        for (int i = 0; i < amount; i++) {
            resources.remove(0);
            //saveNumberOfMovedResources(playerName, amount);
        }
    }

    public void reset() {
        savedResources = 0;
        resources = new ArrayList<>();
        resetVotingHistory();
    }

    public void setDiceRanges(int min, int max) {
        dices.forEach(e -> e.setRange(min, max));
    }

    public void resetTimer() {
        try {
            timer.cancel();
            startTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        try {
            timer = new Timer();
            timer.schedule(timerTask(), Player.createDate(timeoutInSeconds));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Date createDate(int secounds) {
        LocalDateTime localDateTime = LocalDateTime.now().plus(Duration.of(secounds, ChronoUnit.SECONDS));
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    private TimerTask timerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                setAI(true);
                timer.cancel();
                timer = new Timer();
            }
        };
    }

    public String convertToJSON() {
        String result = "[{";
        result += "\"playerName\": " + "\"" + playerName + "\",";
        result += "\"dices\": [";

        for (int i = 0; i < dices.size(); i++) {
            result += dices.get(i).convertToJSON();
            if (i < dices.size() - 1) {
                result += ",";
            }
        }
        result += "],";

        result += "\"resources\": [";
        result = Game.printResourcesJson(result, resources);
        result += "}]";
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", dices=" + dices +
                ", resources= \n" + resources +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerName, player.playerName) && Objects.equals(dices, player.dices) && Objects.equals(resources, player.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, dices, resources);
    }

    public void vote(int round, int indexOfWeakestLink) {
        Vote vote=new Vote(indexOfWeakestLink,round);
        votingHistory.addVote(vote);
    }

    public void resetVotingHistory(){
        votingHistory.setVotes(new ArrayList<>());
    }

    public String getVotesInJson(){
            String result = "[{";
            result += "\"playerName\": " + "\"" + playerName + "\",";

            result += "\"votingHistory\": ";
            result +=votingHistory.convertToJSON();
            result += "}]";
            return result;
    }



}
