import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener{

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    JButton restartButton = new JButton("Restart");
    Board board = new Board();
    boolean player1_turn;


    TicTacToe(){

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());


        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(Color.GREEN);
        textfield.setFont(new Font("New Times Roman", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800,100);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(150, 150, 150));

        restartButton.setFont(new Font("Arial", Font.PLAIN, 30));
        restartButton.setFocusable(false);
        restartButton.setVisible(false); // Hidden until game ends
        restartButton.addActionListener(e -> restartGame());

        for (int i=0; i<9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);
        frame.add(restartButton, BorderLayout.SOUTH);
        title_panel.add(textfield);

        frame.setVisible(true);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                char mark = player1_turn ? 'X' : 'O';
                if (board.place(i, mark)) {
                    buttons[i].setForeground(player1_turn ? Color.RED : Color.BLUE);
                    buttons[i].setText(String.valueOf(mark));
                    player1_turn = !player1_turn;
                    textfield.setText((player1_turn ? "X" : "O") + " turn");
                    check();
                }
            }
        }
    }

    public void firstTurn() {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(random.nextInt(2)==0) {
            player1_turn = true;
            textfield.setText("X turn");
        }
        else {
            player1_turn = false;
            textfield.setText("O turn");
        }
    }

    public void check() {
        int[] line = board.winningLine();
        if (line != null) {
            endGame(board.winner() + " Wins", line);
        } else if (board.isDraw()) {
            endGame("Draw", new int[0]);
        }
    }

    private void endGame(String message, int[] highlight) {
        textfield.setText(message);
        for (int cell : highlight) {
            buttons[cell].setBackground(Color.YELLOW);
        }
        for(int i=0;i<9;i++) {
            buttons[i].setEnabled(false);
        }
        restartButton.setVisible(true);
    }

    public void restartGame() {
        board.reset();
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackground(null);
        }
        restartButton.setVisible(false);
        firstTurn();
    }
}
