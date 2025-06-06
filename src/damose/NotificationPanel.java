package damose;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class NotificationPanel extends JPanel {

    private JFrame frame;
    private JButton btnMessage, btnClose;


    // Override del metodo paintComponent, per disegnare ogni volta il notificationPanel con un rettangolo giallo decorativo
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(255, 180, 9));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);
    }


    // Costruttore del pannello NotificationPanel
    public NotificationPanel(Frame frame) {

        this.frame = frame;

        this.setBackground(new Color(130, 36, 51));
        this.setLayout(null);
        this.setVisible(false);
        this.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        this.setPreferredSize(new Dimension(720, 64));


        // JButton che ospiterà un'icona per il tipo di messaggio e il
        btnMessage = new JButton();

        btnMessage.setBackground(new Color(130, 36, 51));
        btnMessage.setForeground(Color.WHITE);
        btnMessage.setFont(new Font("Arial Nova", Font.BOLD, 16));
        btnMessage.setHorizontalAlignment(SwingConstants.LEADING);
        btnMessage.setText("Messaggio");

        btnMessage.setContentAreaFilled(false);
        btnMessage.setFocusPainted(false);
        btnMessage.setBorderPainted(false);
        btnMessage.setFocusable(false);

        btnMessage.setBounds(10, 8, 700, 48);

        this.add(btnMessage);
    }


// ---------------------------------------------------------------------------------------------


    // Metodo get per il btnMessage
    public JButton getBtnMessage() {
        return this.btnMessage;
    }


    // Metodo get per il btnClose
    public JButton getBtnClose() {
        return this.btnClose;
    }


// ---------------------------------------------------------------------------------------------


    // Metodo che attiva il notificationPanel e lo disattiva dopo 3 secondi
    public void attivaNotifica() {
        this.setVisible(true);

        Timer timer = new Timer(3000, (ActionEvent e) -> {
            this.setVisible(false);
            this.frame.revalidate();
            this.frame.repaint();
        });

        timer.setRepeats(false);
        timer.start();
    }
}
