import javax.swing.*;
import java.awt.*;

public class Graphics {
    Frame frame;

    JMenuBar menuBar;
    JMenu fileMenu;

    JMenuItem newGame;
    JMenuItem quit;
    JMenu help;
    JMenuItem about;

    JLabel potLabel;
    JLabel statusLabel;

    String gameName;

    public Graphics(String gameName) {
        this.gameName = gameName;
        makeFrame();
        frame.setVisible(true);
    }

    private void makeFrame() {
        frame = new Frame();
        makeMenus();
    }

    private void makeMenus(){
        menuBar = new JMenuBar();
        menuBar.add(fileMenu = new JMenu("File"));
        fileMenu.add(newGame = new JMenuItem("New Game"));
        fileMenu.add(quit = new JMenuItem("Quit"));
        menuBar.add(help = new JMenu("Help"));
        help.add(about = new JMenuItem("About " + gameName));


        frame.add(menuBar);
    }

    public void setPotLabel(int pot) {
        potLabel.setText(String.valueOf(pot));
    }

    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }
}
