package ui;

import model.Ficha;
import model.Partida;

import javax.swing.*;
import java.awt.*;

public class JuegoGUI extends JFrame {

    private JButton[][] botones;
    private Partida partida;
    private JLabel lblTurno;
    private ImageIcon iconoVictoria;
    private ImageIcon iconoEmpate;

    public JuegoGUI() {
        partida = new Partida();
        botones = new JButton[3][3];

        // 🔹 Cargar y reescalar los iconos
        iconoVictoria = cargarIconoEscalado("src/main/resources/victoria.png", 64, 64);
        iconoEmpate   = cargarIconoEscalado("src/main/resources/empate.png", 64, 64);

        setTitle("Tres en Raya");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // 🔹 Mostrar el menú inicial
        mostrarMenuInicio();

        setVisible(true);
    }

    // ------------------- MENÚ INICIAL -------------------
    private void mostrarMenuInicio() {
        getContentPane().removeAll();

        // Panel con imagen de fondo
        JPanel menuPanel = new JPanel() {
            private Image fondo = new ImageIcon("src/main/resources/fondoInicial.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen escalada al tamaño del panel
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                // Capa semitransparente para mejorar visibilidad
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(255, 255, 255, 100)); // blanca semitransparente
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Centrar contenido horizontalmente
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mensaje de bienvenida con fondo blanco
        JLabel bienvenida = new JLabel("¡Bienvenido a Tres en Raya!", SwingConstants.CENTER);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        bienvenida.setOpaque(true);
        bienvenida.setBackground(Color.WHITE);
        bienvenida.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        menuPanel.add(bienvenida);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50))); // espacio debajo del título

        // Botón Iniciar partida
        JButton btnIniciar = new JButton("Iniciar partida");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.setMaximumSize(new Dimension(200, 50));
        btnIniciar.addActionListener(e -> iniciarJuego());
        menuPanel.add(btnIniciar);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // espacio entre botones

        // Botón Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 18));
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(200, 50));
        btnSalir.addActionListener(e -> System.exit(0));
        menuPanel.add(btnSalir);

        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // ------------------- INICIAR JUEGO -------------------
    private void iniciarJuego() {
        getContentPane().removeAll();

        // Turno
        lblTurno = new JLabel("Turno: " + fichaToString(Partida.turno), SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTurno, BorderLayout.NORTH);

        // Tablero
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

        // Panel inferior para botones
        JPanel botonesPanel = new JPanel(new FlowLayout());

        // Botón Reiniciar
        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 16));
        btnReiniciar.addActionListener(e -> reiniciarPartida());
        botonesPanel.add(btnReiniciar);

        // Botón Salir al menú
        JButton btnSalirMenu = new JButton("Salir al menú");
        btnSalirMenu.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalirMenu.addActionListener(e -> mostrarMenuInicio());
        botonesPanel.add(btnSalirMenu);

        add(botonesPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }


    // ------------------- JUGAR -------------------
    private void jugar(int fila, int col, JButton boton) {
        if (!partida.terminada() && boton.getText().equals(" ")) {
            boton.setText(fichaToString(Partida.turno));
            partida.jugar(fila, col);

            if (partida.terminada()) {
                if (partida.ganador() != null) {
                    JOptionPane.showMessageDialog(this,
                            "🎉 ¡Victoria de " + fichaToString(partida.ganador()) + "! 🎉",
                            "Fin de la partida",
                            JOptionPane.PLAIN_MESSAGE,
                            iconoVictoria);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "¡Empate!",
                            "Fin de la partida",
                            JOptionPane.PLAIN_MESSAGE,
                            iconoEmpate);
                }
            }

            lblTurno.setText("Turno: " + fichaToString(Partida.turno));
        }
    }

    private void reiniciarPartida() {
        partida = new Partida(); // reinicia la partida
        lblTurno.setText("Turno: " + fichaToString(Partida.turno));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText(" ");
                // restaurar color tipo ajedrez
                botones[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
            }
        }
    }

    // ------------------- MÉTODO HELPER -------------------
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
