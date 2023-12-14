import javax.swing.*;

public class JuegoFrame extends JFrame {
    public JuegoFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Snake Game");
        setResizable(false);

        JuegoContenido juego = new JuegoContenido();
        add(juego);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JuegoFrame());
    }
}

