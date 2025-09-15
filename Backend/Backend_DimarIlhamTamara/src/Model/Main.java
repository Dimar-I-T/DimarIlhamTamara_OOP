package Model;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Dimar");
        Player player2 = new Player("Ramid");

        Score score1 = new Score(player1.getPlayerId(), 1500, 250, 5000);
        Score score2 = new Score(player2.getPlayerId(), 3200, 750, 12000);
        Score score3 = new Score(player1.getPlayerId(), 1800, 300, 6000);

        player1.updateHighScore(score1.getValue());
        player1.updateHighScore(score3.getValue());
        player1.addCoins(score1.getCoinsCollected());
        player1.addDistance(score1.getDistance());
        player1.addCoins(score3.getCoinsCollected());
        player1.addDistance(score3.getDistance());

        player2.updateHighScore(score2.getValue());
        player2.addCoins(score2.getCoinsCollected());
        player2.addDistance(score2.getDistance());

        Player[] players = {player1, player2};
        System.out.print("================= Player Details =================\n");
        for (Player player : players) {
            player.showDetail();
            System.out.println("\n");
        }
    }
}