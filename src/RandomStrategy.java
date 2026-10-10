import java.util.Random;

/**
 * RandomStrategy.java - Random strategy implementation
 */
public class RandomStrategy implements Strategy
{
    /**
     * Random strategy - randomly choose a move
     * @param playerMove - the player's move
     * @return - the computer's move
     */
    @Override
    public String getMove(String playerMove)
    {
        java.util.Random randGen = new java.util.Random();
        String computerMove = "";
        int randNum = randGen.nextInt(3);
        switch (randNum)
        {
            case 0:
                computerMove = "R";
                break;
            case 1:
                computerMove = "P";
                break;
            case 2:
                computerMove = "S";
                break;
            default:
                computerMove = "X";
                break;
        }
        return computerMove;
    }
}
