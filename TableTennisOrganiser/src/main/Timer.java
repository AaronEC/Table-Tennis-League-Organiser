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
                if (fixture.getWinner() != null) {
                    fixture.getWinner().incrementMatchesWon();
                    fixture.getWinner().addPoints(3);
                }
            }
	}
}
