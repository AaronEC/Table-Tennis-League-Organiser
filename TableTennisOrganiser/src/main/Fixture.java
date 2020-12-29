package main;

import java.io.Serializable;

/**
 * Not yet implemented
 * @author Aaron
 */
public class Fixture implements Serializable{

        private static final long serialVersionUID = 8604642400555460347L;
	private String winner;
	private SinglesSet[] singlesSets;
	private DoublesSet doublesSet;
	private int homeScore;
	private int awayScore;
	private Team[] teams;


	Team calculateWinner(DoublesSet[] doublesSet, SinglesSet[] singlesSet) {
		return null;
	}

	Team getWinner() {
		return null;
	}

	Team[] getTeams() {
		return null;
	}

	int getHomeScore() {
		return 0;
	}

	int getAwayScore() {
		return 0;
	}

        public void setTeams(Team[] teams) {
            this.teams = teams;
        }
 
	void setHomeScore(int score) {

	}

	void setAwayScore(int score) {

	}
        
        

}
