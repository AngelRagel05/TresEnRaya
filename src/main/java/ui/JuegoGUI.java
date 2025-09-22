package ui;

import model.Ficha;
import model.Partida;

import javax.swing.*;
import java.awt.*;

public class JuegoGUI extends JFrame {

    private JButton[][] botones;
    private Partida partida;
    private JLabel lblTurno;
    private JLabel lblMarcador;
    private ImageIcon iconoVictoria;
    private ImageIcon iconoEmpate;

    private int victoriasX = 0;
    private int victoriasO = 0;

    public JuegoGUI() {
        partida = new Partida();
        botones = new JButton[3][3];

        iconoVictoria = cargarIconoEscalado("src/main/resources/victoria.png", 64, 64);
        iconoEmpate = cargarIconoEscalado("src/main/resources/empate.png", 64, 64);

        setTitle("Tres en Raya");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        mostrarMenuInicio();
        setVisible(true);
    }

    // ------------------- MENÚ INICIAL -------------------
    private void mostrarMenuInicio() {
        getContentPane().removeAll();

        victoriasX = 0;
        victoriasO = 0;

        JPanel menuPanel = new JPanel(new BorderLayout());

        JLabel bienvenida = new JLabel("¡Bienvenido a Tres en Raya!", SwingConstants.CENTER);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenida.setOpaque(true);
        bienvenida.setBackground(Color.WHITE);
        bienvenida.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        menuPanel.add(bienvenida, BorderLayout.NORTH);

        JPanel fondoBotonesPanel = new JPanel() {
            private Image fondo = new ImageIcon("src/main/resources/fondoInicial.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(255, 255, 255, 100));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        fondoBotonesPanel.setLayout(new BoxLayout(fondoBotonesPanel, BoxLayout.Y_AXIS));
        fondoBotonesPanel.add(Box.createVerticalGlue());

        JButton btnIniciar = new JButton("Iniciar partida");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.setMaximumSize(new Dimension(200, 50));
        btnIniciar.addActionListener(e -> iniciarJuego());
        fondoBotonesPanel.add(btnIniciar);

        fondoBotonesPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 18));
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(200, 50));
        btnSalir.addActionListener(e -> System.exit(0));
        fondoBotonesPanel.add(btnSalir);

        fondoBotonesPanel.add(Box.createVerticalGlue());
        menuPanel.add(fondoBotonesPanel, BorderLayout.CENTER);

        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // ------------------- INICIAR JUEGO -------------------
    private void iniciarJuego() {
        getContentPane().removeAll();
        partida = new Partida();

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTurno = new JLabel("Turno: " + fichaToString(Partida.turno), SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 20));
        lblTurno.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblMarcador = new JLabel("Marcador: X=" + victoriasX + "  O=" + victoriasO, SwingConstants.CENTER);
        lblMarcador.setFont(new Font("Arial", Font.BOLD, 16));
        lblMarcador.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelSuperior.add(lblTurno);
        panelSuperior.add(Box.createRigidArea(new Dimension(0, 5)));
        panelSuperior.add(lblMarcador);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel tableroPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        tableroPanel.setBackground(Color.BLACK);
        Font fuenteBoton = new Font("Arial", Font.BOLD, 50);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton boton = new JButton(" ");
                boton.setFont(fuenteBoton);
                boton.setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                final int fila = i, col = j;
                boton.addActionListener(e -> jugar(fila, col, boton));
                botones[i][j] = boton;
                tableroPanel.add(boton);
            }
        }
        add(tableroPanel, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        botonesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 16));
        btnReiniciar.addActionListener(e -> reiniciarPartida());
        botonesPanel.add(btnReiniciar);

        JButton btnSalirMenu = new JButton("Salir al menú");
        btnSalirMenu.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalirMenu.addActionListener(e -> mostrarMenuInicio());
        botonesPanel.add(btnSalirMenu);

        add(botonesPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void jugar(int fila, int col, JButton boton) {
        if (!partida.terminada() && boton.getText().equals(" ")) {
            boton.setText(fichaToString(Partida.turno));
            partida.jugar(fila, col);

            if (partida.terminada()) {
                if (partida.ganador() != null) {
                    if (partida.ganador() == Ficha.X) victoriasX++;
                    else if (partida.ganador() == Ficha.O) victoriasO++;

                    JOptionPane.showMessageDialog(this,
                            "Victoria de " + fichaToString(partida.ganador()) + "\n" +
                                    "Marcador: X=" + victoriasX + "  O=" + victoriasO,
                            "Fin de la partida",
                            JOptionPane.PLAIN_MESSAGE,
                            iconoVictoria);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Empate\nMarcador: X=" + victoriasX + "  O=" + victoriasO,
                            "Fin de la partida",
                            JOptionPane.PLAIN_MESSAGE,
                            iconoEmpate);
                }
                lblMarcador.setText("Marcador: X = " + victoriasX + "    O = " + victoriasO);
            }

            lblTurno.setText("Turno: " + fichaToString(Partida.turno));
        }
    }

    private void reiniciarPartida() {
        partida = new Partida();
        lblTurno.setText("Turno: " + fichaToString(Partida.turno));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText(" ");
                botones[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
            }
        }
        lblMarcador.setText("Marcador: X = " + victoriasX + "    O = " + victoriasO);
    }

    private String fichaToString(Ficha ficha) {
        if (ficha == Ficha.X) return "X";
        if (ficha == Ficha.O) return "O";
        return " ";
    }

    private ImageIcon cargarIconoEscalado(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(ruta);
        Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
}
