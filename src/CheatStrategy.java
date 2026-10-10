public class CheatStrategy implements Strategy
{
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
