package a7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OthelloWidget extends JPanel implements ActionListener, SpotListener {

    private enum Player {BLACK, WHITE};

    private JSpotBoard _board;
    private JLabel _message;
    private boolean _gameWon;
    private Player _nextToPlay;
    private Color _winningColor;
    private boolean _stalemate;

    public OthelloWidget(){
        _board = new JSpotBoard(8, 8);
        _message = new JLabel();
        setLayout(new BorderLayout());
        add(_board, BorderLayout.CENTER);

        JPanel reset_message_panel = new JPanel();
        reset_message_panel.setLayout(new BorderLayout());
        JButton reset_button = new JButton("Restart");

        reset_button.addActionListener(this);
        reset_message_panel.add(reset_button, BorderLayout.EAST);
        reset_message_panel.add(_message, BorderLayout.CENTER);

        add(reset_message_panel, BorderLayout.SOUTH);

        _board.addSpotListener(this);
        resetGame();

    }
    private void resetGame(){
        for (Spot s : _board) {
            s.clearSpot();
            s.setSpotColor(Color.GREEN);
            s.unhighlightSpot();
        }
        _nextToPlay = Player.BLACK;
        _gameWon = false;
        _winningColor = null;
        _stalemate = false;
        _message.setText("Welcome to Othello. Black to play");
    }

    public void spotClicked(Spot spot) {

    }

    public void spotEntered(Spot spot) {

    }

    public void spotExited(Spot spot) {

    }

    public void actionPerformed(ActionEvent e) {

    }
}
