import ui.JuegoGUI;
import javax.swing.SwingUtilities;

public class Iniciar {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JuegoGUI::new);
    }
}