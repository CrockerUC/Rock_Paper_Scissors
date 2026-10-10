/**
 * CheatStrategy.java - Cheat strategy
 */
public class CheatStrategy implements Strategy
{
    /**
     * Cheat strategy - always play the move that beats the player's move
     * @param playerMove - the player's move
     * @return - the computer's move
     */
    @Override
    public String getMove(String playerMove)
    {
        String computersMove = "";
        switch (playerMove)
        {
            case "R":
                computersMove = "P";
                break;
            case "P":
                computersMove = "S";
                break;
            case "S":
                computersMove = "R";
                break;
            default:
                computersMove = "X";
                break;
        }
        return computersMove;
    }
}
