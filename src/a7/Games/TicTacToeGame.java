package a7.Games;

import a7.Widgets.TicTacToeWidget;

import javax.swing.*;
import java.awt.*;

public class TicTacToeGame {
    public static void main(String[] args) {

        /* Create top level window. */

        JFrame main_frame = new JFrame();
        main_frame.setTitle("TicTacToe");
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Create panel for content. Uses BorderLayout. */
        JPanel top_panel = new JPanel();
        top_panel.setLayout(new BorderLayout());
        main_frame.setContentPane(top_panel);

        /* Create ExampleWidget component and put into center
         * of content panel.
         */

        TicTacToeWidget ttt = new TicTacToeWidget();
        top_panel.add(ttt, BorderLayout.CENTER);


        /* Pack main frame and make visible. */

        main_frame.pack();
        main_frame.setVisible(true);
    }

}
