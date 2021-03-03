package main;

/**
 * Not yet implemented
 * @author Aaron
 */
public class Timer extends User {

	private int seconds;

	protected void generateTeamStats(League league) {
            System.out.println(league.getName());
            
            for (Team team : league.getTeams()) {
                team.resetStats();
            }
            
            for (Fixture fixture : league.getFixtures()) {
                Team winner = fixture.getWinner();
                Team loser = fixture.getLoser();
                
                if (winner != null) {
                    winner.incrementMatchesWon();
                    winner.incrementMatchesPlayed();
                    winner.addPoints(3);
                }
                if (loser != null) {
                    loser.incrementMatchesPlayed();
                    loser.addPoints(1);
                }
            }
	}
}
