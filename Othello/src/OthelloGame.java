
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




public class OthelloGame extends JFrame {
    public static final int size = 8;
    private GameState gameState = new GameState(size);
    private OthelloBoard othelloBoard = new OthelloBoard(gameState);
    public OthelloGame() {
        this.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(600, 600));


        // this.add(new JButton("North"), BorderLayout.NORTH);
        // this.add(new JButton("South"), BorderLayout.SOUTH);
        // this.add(new JButton("East"), BorderLayout.EAST);
        // this.add(new JButton("West"), BorderLayout.WEST);
        this.add(othelloBoard, BorderLayout.CENTER);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        
    }

}
