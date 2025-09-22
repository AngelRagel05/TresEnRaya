package ui;

import model.Ficha;
import model.Partida;

import javax.swing.*;
import java.awt.*;

/**
 * Interfaz gráfica del juego Tres en Raya con tablero, menú, marcador y control de turnos
 */
public class JuegoGUI extends JFrame implements IJuegoGUI {

    // -------------------- ATRIBUTOS --------------------
    private JButton[][] botones;
    private Partida partida;
    private JLabel lblTurno;
    private JLabel lblMarcador;
    private ImageIcon iconoVictoria, iconoEmpate;
    private ImageIcon iconoX, iconoO;
    private int victoriasX = 0, victoriasO = 0;
    private final Color colorFondo = new Color(24, 13, 43);

    // -------------------- CONSTRUCTOR --------------------
    public JuegoGUI() {
        // Inicialización de partida, botones e iconos
        partida = new Partida();
        botones = new JButton[3][3];
        iconoX = cargarIconoEscalado("src/main/resources/X.png", 110, 110);
        iconoO = cargarIconoEscalado("src/main/resources/O.png", 110, 110);

        // Icono de ventana y fin de partida
        setIconImage(new ImageIcon("src/main/resources/icono.png").getImage());
        iconoVictoria = cargarIconoEscalado("src/main/resources/victoria.png", 64, 64);
        iconoEmpate = cargarIconoEscalado("src/main/resources/empate.png", 64, 64);

        // Configuración ventana y mostrar menú inicial
        setTitle("Tres en Raya");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        mostrarMenuInicio();
        setVisible(true);
    }

    // -------------------- MÉTODOS PRINCIPALES --------------------

    /**
     * Muestra el menú inicial con botones de inicio y salida
     */
    public void mostrarMenuInicio() {
        getContentPane().removeAll();
        victoriasX = 0;
        victoriasO = 0;

        JPanel menuPanel = new JPanel(new BorderLayout());
        JLabel bienvenida = new JLabel("¡Bienvenido a Tres en Raya!", SwingConstants.CENTER);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenida.setOpaque(true);
        bienvenida.setBackground(colorFondo);
        bienvenida.setForeground(Color.WHITE);
        bienvenida.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        menuPanel.add(bienvenida, BorderLayout.NORTH);

        // Panel con fondo y botones
        JPanel fondoBotonesPanel = new JPanel() {
            private Image fondo = new ImageIcon("src/main/resources/fondoInicial.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
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

    /**
     * Inicializa la partida y muestra el tablero con marcador y botones
     */
    public void iniciarJuego() {
        getContentPane().removeAll();
        partida = new Partida();

        // Panel superior con turno y marcador
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.setBackground(colorFondo);

        lblTurno = new JLabel("Turno: " + fichaToString(Partida.turno), SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 20));
        lblTurno.setForeground(Color.WHITE);
        lblTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMarcador = new JLabel("Marcador: X = " + victoriasX + "    O = " + victoriasO, SwingConstants.CENTER);
        lblMarcador.setFont(new Font("Arial", Font.BOLD, 16));
        lblMarcador.setForeground(Color.WHITE);
        lblMarcador.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelSuperior.add(lblTurno);
        panelSuperior.add(Box.createRigidArea(new Dimension(0, 5)));
        panelSuperior.add(lblMarcador);
        add(panelSuperior, BorderLayout.NORTH);

        // Tablero de juego
        JPanel tableroPanel = new JPanel(new GridLayout(3, 3));
        tableroPanel.setBackground(colorFondo);
        Font fuenteBoton = new Font("Arial", Font.BOLD, 50);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton boton = new JButton(" ");
                boton.setFont(fuenteBoton);
                boton.setForeground(Color.WHITE);
                boton.setBackground(colorFondo);
                boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                final int fila = i, col = j;
                boton.addActionListener(e -> jugar(fila, col, boton));
                botones[i][j] = boton;
                tableroPanel.add(boton);
            }
        }
        add(tableroPanel, BorderLayout.CENTER);

        // Panel inferior con botones reiniciar y salir
        JPanel botonesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        botonesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botonesPanel.setBackground(colorFondo);
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

    /**
     * Ejecuta la jugada y actualiza tablero, turno y marcador
     */
    private void jugar(int fila, int col, JButton boton) {
        if (!partida.terminada() && boton.getIcon() == null) {
            Ficha turnoActual = Partida.turno;
            boton.setIcon(turnoActual == Ficha.X ? iconoX : iconoO);
            boton.setText(""); // Pone icono
            partida.jugar(fila, col); // Actualiza lógica

            if (partida.terminada()) { // Si fin de partida
                if (partida.ganador() != null) {
                    if (partida.ganador() == Ficha.X) victoriasX++;
                    else if (partida.ganador() == Ficha.O) victoriasO++;
                    JOptionPane.showMessageDialog(this, "Victoria de " + fichaToString(partida.ganador()) + "\nMarcador: X = " + victoriasX + "    O = " + victoriasO, "Fin de la partida", JOptionPane.PLAIN_MESSAGE, iconoVictoria);
                } else {
                    JOptionPane.showMessageDialog(this, "Empate\nMarcador: X = " + victoriasX + "    O = " + victoriasO, "Fin de la partida", JOptionPane.PLAIN_MESSAGE, iconoEmpate);
                }
                lblMarcador.setText("Marcador: X = " + victoriasX + "    O = " + victoriasO);
            }
            lblTurno.setText("Turno: " + fichaToString(Partida.turno)); // Actualiza turno
        }
    }

    /**
     * Reinicia la partida manteniendo el marcador
     */
    public void reiniciarPartida() {
        partida = new Partida();
        lblTurno.setText("Turno: " + fichaToString(Partida.turno));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText(" ");
                botones[i][j].setIcon(null);
                botones[i][j].setForeground(Color.WHITE);
                botones[i][j].setBackground(colorFondo);
            }
        }
        lblMarcador.setText("Marcador: X = " + victoriasX + "    O = " + victoriasO);
    }

    // -------------------- MÉTODOS AUXILIARES --------------------
    private String fichaToString(Ficha ficha) {
        return ficha == Ficha.X ? "X" : ficha == Ficha.O ? "O" : " ";
    }

    private ImageIcon cargarIconoEscalado(String ruta, int ancho, int alto) {
        return new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
    }
}
