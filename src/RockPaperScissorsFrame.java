import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RockPaperScissorsFrame extends JFrame
{
    JButton rockBtn, paperBtn, scissorsBtn, quitBtn;
    JPanel mainPnl, buttonPnl, statsPnl, displayPnl;
    JLabel playWinLbl, compWinLbl, tiesLbl;
    JTextField playWinTf, compWinTf, tiesTf;
    JTextArea resultsTA;
    JScrollPane resultsScroller;
    ActionListener moveListener;

    int compWins = 0, playWins = 0, ties = 0, compRock = 0, compPaper = 0, compScissors = 0, playRock = 0, playPaper = 0, playScissors = 0;

    String lastCompMove = "", lastPlayMove = "", compStrategy = "";

    CheatStrategy cheat = new CheatStrategy();
    RandomStrategy random = new RandomStrategy();

    class MostUsedStrategy implements Strategy
    {
        @Override
        public String getMove(String compMove)
        {
            int max = Math.max(playRock, Math.max(playPaper, playScissors));
            if (playRock == max)
                return "P";
            else if (playPaper == max)
                return "S";
            else
                return "R";
        }
    }

    class LeastUsedStrategy implements Strategy
    {
        @Override
        public String getMove(String playerMove)
        {
            int min = Math.min(playRock, Math.min(playPaper, playScissors));

            if (playRock == min)
                return "P";
            else if (playPaper == min)
                return "S";
            else
                return "R";
        }
    }

    LeastUsedStrategy leastUsed = new LeastUsedStrategy();

    MostUsedStrategy mostUsed = new MostUsedStrategy();

    class LastUsedStrategy implements Strategy
    {
        @Override
        public String getMove(String playerMove)
        {
            if (lastPlayMove.isEmpty())
            {
                return random.getMove(playerMove);
            } else
                return lastPlayMove;
        }
    }
    LastUsedStrategy lastUsed = new LastUsedStrategy();

    public RockPaperScissorsFrame()
    {
        super("Rock Paper Scissors Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);

        createButtonPanel();
        mainPnl.add(buttonPnl, BorderLayout.NORTH);

        createStatsPanel();
        mainPnl.add(statsPnl, BorderLayout.SOUTH);

        createDisplayPanel();
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void createButtonPanel()
    {
        buttonPnl = new JPanel();
        buttonPnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        moveListener = (ActionEvent ae) ->
        {
            if (ae.getSource() == rockBtn)
            {
                playRock++;
                resolveMove("R");
            }
            else if (ae.getSource() == paperBtn)
            {
                playPaper++;
                resolveMove("P");
            }
            else if (ae.getSource() == scissorsBtn)
            {
                playScissors++;
                resolveMove("S");
            }
        };
        rockBtn = new JButton("Rock", resizeIcon("src//rock.png"));
        rockBtn.setHorizontalTextPosition(JButton.CENTER);
        rockBtn.setVerticalTextPosition(JButton.BOTTOM);
        rockBtn.setFocusPainted(false);
        rockBtn.addActionListener(moveListener);

        paperBtn = new JButton("Paper", resizeIcon("src//paper.png"));
        paperBtn.setHorizontalTextPosition(JButton.CENTER);
        paperBtn.setVerticalTextPosition(JButton.BOTTOM);
        paperBtn.setFocusPainted(false);
        paperBtn.addActionListener(moveListener);


        scissorsBtn = new JButton("Scissors", resizeIcon("src//scissors.png"));
        scissorsBtn.setHorizontalTextPosition(JButton.CENTER);
        scissorsBtn.setHorizontalAlignment(JButton.CENTER);
        scissorsBtn.setFocusPainted(false);
        scissorsBtn.addActionListener(moveListener);

        quitBtn = new JButton("Quit", resizeIcon("src//quit.png"));
        quitBtn.setToolTipText("Quit");
        quitBtn.setHorizontalTextPosition(JButton.CENTER);
        quitBtn.setVerticalTextPosition(JButton.BOTTOM);
        quitBtn.setFocusPainted(false);
        quitBtn.addActionListener(ae -> System.exit(0));


        buttonPnl.add(rockBtn);
        buttonPnl.add(paperBtn);
        buttonPnl.add(scissorsBtn);
        buttonPnl.add(quitBtn);
    }

    private void createStatsPanel()
    {
        statsPnl = new JPanel();
        statsPnl.setLayout(new FlowLayout());

        playWinLbl = new JLabel("Player Wins:");
        compWinLbl = new JLabel("Computer Wins:");
        tiesLbl = new JLabel("Ties:");

        playWinTf = new JTextField("0", 5);
        compWinTf = new JTextField("0", 5);
        tiesTf = new JTextField("0", 5);

        playWinTf.setEditable(false);
        compWinTf.setEditable(false);
        tiesTf.setEditable(false);

        statsPnl.add(playWinLbl);
        statsPnl.add(playWinTf);

        statsPnl.add(compWinLbl);
        statsPnl.add(compWinTf);

        statsPnl.add(tiesLbl);
        statsPnl.add(tiesTf);
    }

    private void createDisplayPanel()
    {
        displayPnl = new JPanel();

        resultsTA = new JTextArea(15, 45);
        resultsTA.setEditable(false);

        resultsScroller = new JScrollPane(resultsTA);

        displayPnl.add(resultsScroller);
    }

    public String getCompMove(String playerMove)
    {
        java.util.Random rnd = new java.util.Random();
        int prob = rnd.nextInt(100) + 1;

        String compMove = "";

        if(prob <= 10)
        {
            compStrategy = "Cheat";
            compMove = cheat.getMove(playerMove);
        }
        else if(prob <= 30)
        {
            compStrategy = "Least Used";
            compMove = leastUsed.getMove(playerMove);
        }
        else if(prob <= 50)
        {
            compStrategy = "Most Used";
            compMove = mostUsed.getMove(playerMove);
        }
        else if(prob <= 70)
        {
            compStrategy = "Last Used";
            compMove = lastUsed.getMove(playerMove);
        }
        else
        {
            compStrategy = "Random";
            compMove = random.getMove(playerMove);
        }

        switch(compMove)
        {
            case "R":
                compRock++;
                lastCompMove = "R";
                break;
            case "P":
                compPaper++;
                lastCompMove = "P";
                break;
            case "S":
                compScissors++;
                lastCompMove = "S";
                break;
        }

        return compMove;
    }

    public void resolveMove(String playerMove)
    {
        String compMove = getCompMove(playerMove);
        String resultsStr = "";
        resultsTA.append("Player: " + playerMove + " Computer: " + compMove + " ");

        if(compMove.equals("R"))
        {
            if(playerMove.equals("R"))
            {
                resultsStr = "Rock vs Rock! It's a Tie!";
                ties++;
                tiesTf.setText(ties + "");
            }
            else if (playerMove.equals("P"))
            {
                resultsStr = "Rock vs Paper! Computer Wins!";
                compWins++;
                compWinTf.setText(compWins + "");
            }
            else
            {
                resultsStr = "Rock vs Scissors! Player Wins!";
                playWins++;
                playWinTf.setText(playWins + "");
            }
        }
        else if (compMove.equals("P"))
        {
            if(playerMove.equals("R"))
            {
                resultsStr = "Paper vs Rock! Player Wins!";
                playWins++;
                playWinTf.setText(playWins + "");
            }
            else if (playerMove.equals("P"))
            {
                resultsStr = "Paper vs Paper! It's a Tie!";
                ties++;
                tiesTf.setText(ties + "");
            }
            else
            {
                resultsStr = "Paper vs Scissors! Computer Wins!";
                compWins++;
                compWinTf.setText(compWins + "");
            }
        }
        else
        {
            if(playerMove.equals("R"))
            {
                resultsStr = "Scissors vs Rock! Computer Wins!";
                compWins++;
                compWinTf.setText(compWins + "");
            }
            else if (playerMove.equals("P"))
            {
                resultsStr = "Scissors vs Paper! Player Wins!";
                playWins++;
                playWinTf.setText(playWins + "");
            }
            else
            {
                resultsStr = "Scissors vs Scissors! It's a Tie!";
                ties++;
                tiesTf.setText(ties + "");
            }
        }

        resultsTA.append(resultsStr + " Computer: " + compStrategy + "\n");

        lastPlayMove = playerMove;
        lastCompMove = compMove;
    }

    private ImageIcon resizeIcon(String fileName)
    {
        ImageIcon original = new ImageIcon(fileName);
        Image image = original.getImage();
        Image resized = image.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }
}
