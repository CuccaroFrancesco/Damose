package damose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsPanel extends JPanel {

    private final Frame frame;
    private final JButton btnBack, btnCache, btnSpawnPoint, btnCentroSpawnPoint;
    private final JCheckBox btnFermatePreferiteToggle, btnCentroAutoSpawnPointToggle;
    private final JLabel lblImpostazioni, lblFermatePreferiteToggle, lblFermatePreferiteToggleInfo,
                                          lblCache, lblCacheInfo,
                                          lblSpawnPointSeparator, lblSpawnPoint, lblSpawnPointInfo,
                                          lblCentroAutoSpawnPoint, lblCentroAutoSpawnPointInfo,
                                          lblCentroSpawnPoint, lblCentroSpawnPointInfo,
                                          lblSpawnPointAttuale;
    private Utente utente;


    // Costruttore del pannello settingsPanel
    public SettingsPanel(Frame frame) {

        this.frame = frame;

        this.setBackground(new Color(130, 36, 51));
        this.setLayout(null);
        this.setVisible(false);


        // Pulsante per chiudere il settingsPanel
        btnBack = new JButton(" Torna indietro");

        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnBack.setFont(new Font("Arial Nova", Font.BOLD, 14));
        btnBack.setForeground(Color.WHITE);

        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setContentAreaFilled(false);

        btnBack.setBounds(-25, 5, 200, 30);

        ImageIcon iconBack = new ImageIcon(getClass().getResource("/assets/indietro.png"));
        Image scaledImageBack = iconBack.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon newIconBack = new ImageIcon(scaledImageBack);
        btnBack.setIcon(newIconBack);

        this.add(btnBack);


        // JLabel per la scritta "Impostazioni"
        lblImpostazioni = new JLabel("Impostazioni");

        lblImpostazioni.setForeground(Color.WHITE);
        lblImpostazioni.setFont(new Font("Arial Nova", Font.BOLD, 24));
        lblImpostazioni.setFocusable(false);

        lblImpostazioni.setBounds(20, 50, 160, 50);

        this.add(lblImpostazioni);


        // ---------------------------------------------------------------------------------------------


        // JLabel che contiene la scritta "Visualizza solo fermate preferite"
        lblFermatePreferiteToggle = new JLabel("Visualizza solo fermate preferite");

        lblFermatePreferiteToggle.setForeground(Color.WHITE);
        lblFermatePreferiteToggle.setFont(new Font("Arial Nova", Font.BOLD, 14));
        lblFermatePreferiteToggle.setFocusable(false);

        lblFermatePreferiteToggle.setBounds(20, 115, 250, 15);

        this.add(lblFermatePreferiteToggle);


        // JLabel che contiene informazioni aggiuntive sul funzionamento di btnFermatePreferiteToggle
        lblFermatePreferiteToggleInfo = new JLabel();

        lblFermatePreferiteToggleInfo.setForeground(new Color(210, 210, 210));
        lblFermatePreferiteToggleInfo.setFont(new Font("Arial Nova", Font.ITALIC, 10));
        lblFermatePreferiteToggleInfo.setFocusable(false);

        lblFermatePreferiteToggleInfo.setText("<html>Se selezionato, vengono mostrate sulla mappa solo le fermate preferite dell'utente</html>");

        lblFermatePreferiteToggleInfo.setBounds(20, 130, 250, 30);

        this.add(lblFermatePreferiteToggleInfo);


        // JCheckBox per scegliere se visualizzare solo le fermate preferite o meno
        btnFermatePreferiteToggle = new JCheckBox();

        btnFermatePreferiteToggle.setBackground(new Color(130, 36, 51));
        btnFermatePreferiteToggle.setBorderPainted(false);
        btnFermatePreferiteToggle.setFocusPainted(false);
        btnFermatePreferiteToggle.setContentAreaFilled(false);
        btnFermatePreferiteToggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnFermatePreferiteToggle.setBounds(285, 123, 34, 34);

        ImageIcon iconFermatePreferiteChecked = new ImageIcon(getClass().getResource("/assets/checked-checkbox.png"));
        Image scaledImageFermatePreferiteChecked = iconFermatePreferiteChecked.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        ImageIcon newIconFermatePreferiteChecked = new ImageIcon(scaledImageFermatePreferiteChecked);
        btnFermatePreferiteToggle.setSelectedIcon(newIconFermatePreferiteChecked);

        ImageIcon iconFermatePreferiteUnchecked = new ImageIcon(getClass().getResource("/assets/empty-checkbox.png"));
        Image scaledImageFermatePreferiteUnchecked = iconFermatePreferiteUnchecked.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        ImageIcon newIconFermatePreferiteUnchecked = new ImageIcon(scaledImageFermatePreferiteUnchecked);
        btnFermatePreferiteToggle.setIcon(newIconFermatePreferiteUnchecked);

        this.add(btnFermatePreferiteToggle);


        // ---------------------------------------------------------------------------------------------


        // JLabel che contiene la scritta "Pulire la cache"
        lblCache = new JLabel("Libera la cache");

        lblCache.setForeground(Color.WHITE);
        lblCache.setFont(new Font("Arial Nova", Font.BOLD, 14));
        lblCache.setFocusable(false);

        lblCache.setBounds(20, 180, 250, 15);

        this.add(lblCache);


        // JLabel che contiene informazioni aggiuntive sul funzionamento di btnCache
        lblCacheInfo = new JLabel();

        lblCacheInfo.setForeground(new Color(210, 210, 210));
        lblCacheInfo.setFont(new Font("Arial Nova", Font.ITALIC, 10));
        lblCacheInfo.setFocusable(false);

        lblCacheInfo.setText("<html>Premere il pulsante per liberare la cache dell'applicazione</html>");

        lblCacheInfo.setBounds(20, 195, 250, 30);

        this.add(lblCacheInfo);


        // JButton per liberare la cache dell'applicazione
        btnCache = new JButton();

        btnCache.setBackground(new Color(130, 36, 51));
        btnCache.setBorderPainted(false);
        btnCache.setFocusPainted(false);
        btnCache.setContentAreaFilled(false);
        btnCache.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnCache.setBounds(286, 183, 34, 34);

        ImageIcon iconCache = new ImageIcon(getClass().getResource("/assets/cache.png"));
        Image scaledImageCache = iconCache.getImage().getScaledInstance(24, 28, Image.SCALE_SMOOTH);
        ImageIcon newIconCache = new ImageIcon(scaledImageCache);
        btnCache.setIcon(newIconCache);

        this.add(btnCache);


        // ---------------------------------------------------------------------------------------------


        // JLabel che contiene la scritta "SpawnPoint" come separatore
        lblSpawnPointSeparator = new JLabel("SpawnPoint _______________________________________________");

        lblSpawnPointSeparator.setForeground(Color.WHITE);
        lblSpawnPointSeparator.setFont(new Font("Arial Nova", Font.BOLD, 10));
        lblSpawnPointSeparator.setFocusable(false);

        lblSpawnPointSeparator.setBounds(20, 280, 300, 15);

        this.add(lblSpawnPointSeparator);


        // JLabel che contiene la scritta "SpawnPoint"
        lblSpawnPoint = new JLabel("SpawnPoint");

        lblSpawnPoint.setForeground(Color.WHITE);
        lblSpawnPoint.setFont(new Font("Arial Nova", Font.BOLD, 14));
        lblSpawnPoint.setFocusable(false);

        lblSpawnPoint.setBounds(20, 310, 250, 15);

        this.add(lblSpawnPoint);


        // JLabel che contiene informazioni aggiuntive sul funzionamento di btnSpawnPoint
        lblSpawnPointInfo = new JLabel();

        lblSpawnPointInfo.setForeground(new Color(210, 210, 210));
        lblSpawnPointInfo.setFont(new Font("Arial Nova", Font.ITALIC, 10));
        lblSpawnPointInfo.setFocusable(false);

        lblSpawnPointInfo.setText("<html>Premere il pulsante per settare il nuovo SpawnPoint dell'utente</html>");

        lblSpawnPointInfo.setBounds(20, 325, 250, 30);

        this.add(lblSpawnPointInfo);


        // JButton per settare un nuovo SpawnPoint per l'utente TODO
        btnSpawnPoint = new JButton();

        this.add(btnSpawnPoint);


        // JLabel che contiene la scritta "Centra sullo SpawnPoint"
        lblCentroSpawnPoint = new JLabel("Centra sullo SpawnPoint");

        lblCentroSpawnPoint.setForeground(Color.WHITE);
        lblCentroSpawnPoint.setFont(new Font("Arial Nova", Font.BOLD, 14));
        lblCentroSpawnPoint.setFocusable(false);

        lblCentroSpawnPoint.setBounds(20, 375, 250, 15);

        this.add(lblCentroSpawnPoint);


        // JLabel che contiene informazioni aggiuntive sul funzionamento di btnCentroSpawnPoint
        lblCentroSpawnPointInfo = new JLabel();

        lblCentroSpawnPointInfo.setForeground(new Color(210, 210, 210));
        lblCentroSpawnPointInfo.setFont(new Font("Arial Nova", Font.ITALIC, 10));
        lblCentroSpawnPointInfo.setFocusable(false);

        lblCentroSpawnPointInfo.setText("<html>Premere il pulsante per centrare la mappa sullo SpawnPoint</html>");

        lblCentroSpawnPointInfo.setBounds(20, 390, 250, 30);

        this.add(lblCentroSpawnPointInfo);


        // JButton per centrare la mappa sullo SpawnPoint dell'utente TODO
        btnCentroSpawnPoint = new JButton();

        this.add(btnCentroSpawnPoint);


        // JLabel che contiene la scritta "Centramento automatico sullo SpawnPoint"
        lblCentroAutoSpawnPoint = new JLabel("Centro automatico sullo SpawnPoint");

        lblCentroAutoSpawnPoint.setForeground(Color.WHITE);
        lblCentroAutoSpawnPoint.setFont(new Font("Arial Nova", Font.BOLD, 14));
        lblCentroAutoSpawnPoint.setFocusable(false);

        lblCentroAutoSpawnPoint.setBounds(20, 440, 250, 15);

        this.add(lblCentroAutoSpawnPoint);


        // JLabel che contiene informazioni aggiuntive sul funzionamento di btnCentramentoSpawnPointToggle
        lblCentroAutoSpawnPointInfo = new JLabel();

        lblCentroAutoSpawnPointInfo.setForeground(new Color(210, 210, 210));
        lblCentroAutoSpawnPointInfo.setFont(new Font("Arial Nova", Font.ITALIC, 10));
        lblCentroAutoSpawnPointInfo.setFocusable(false);

        lblCentroAutoSpawnPointInfo.setText("<html>Se selezionato, in seguito al login l'utente viene centrato automaticamente sullo SpawnPoint</html>");

        lblCentroAutoSpawnPointInfo.setBounds(20, 455, 250, 30);

        this.add(lblCentroAutoSpawnPointInfo);


        // JCheckBox per scegliere se centrare l'utente sullo SpawnPoint dopo il login in modo automatico o meno
        btnCentroAutoSpawnPointToggle = new JCheckBox();

        btnCentroAutoSpawnPointToggle.setBackground(new Color(130, 36, 51));
        btnCentroAutoSpawnPointToggle.setBorderPainted(false);
        btnCentroAutoSpawnPointToggle.setFocusPainted(false);
        btnCentroAutoSpawnPointToggle.setContentAreaFilled(false);
        btnCentroAutoSpawnPointToggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnCentroAutoSpawnPointToggle.setBounds(285, 448, 34, 34);

        ImageIcon iconCentramentoChecked = new ImageIcon(getClass().getResource("/assets/checked-checkbox.png"));
        Image scaledImageCentramentoChecked = iconCentramentoChecked.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        ImageIcon newIconCentramentoChecked = new ImageIcon(scaledImageCentramentoChecked);
        btnCentroAutoSpawnPointToggle.setSelectedIcon(newIconCentramentoChecked);

        ImageIcon iconCentramentoUnchecked = new ImageIcon(getClass().getResource("/assets/empty-checkbox.png"));
        Image scaledImageCentramentoUnchecked = iconCentramentoUnchecked.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        ImageIcon newIconCentramentoUnchecked = new ImageIcon(scaledImageCentramentoUnchecked);
        btnCentroAutoSpawnPointToggle.setIcon(newIconCentramentoUnchecked);

        this.add(btnCentroAutoSpawnPointToggle);


        // JLabel che contiene la scritta "SpawnPoint attuale:"
        lblSpawnPointAttuale = new JLabel("SpawnPoint attuale:");

        
    }


// ---------------------------------------------------------------------------------------------


    // Metodo che gestisce la creazione del settingsPanel
    public void creaPannelloImpostazioni() {

        // Visualizzazione del settingsPanel e disattivazione di eventuali userPanel precedentemente visibili
        this.setVisible(true);

        frame.getUserPanel().setVisible(false);


        // Ottenimento dell'utente attualmente loggato
        if (frame.getUtente().getIsLogged()) this.utente = utente;


        // Rimozione di eventuali ActionListener precedenti dal btnBack (necessario per evitare overlap)
        for (ActionListener a : btnBack.getActionListeners()) { btnBack.removeActionListener(a); }
        for (ActionListener a : btnFermatePreferiteToggle.getActionListeners()) { btnFermatePreferiteToggle.removeActionListener(a); }
        for (ActionListener a : btnCache.getActionListeners()) { btnCache.removeActionListener(a); }
        for (ActionListener a : btnSpawnPoint.getActionListeners()) { btnSpawnPoint.removeActionListener(a); }
        for (ActionListener a : btnCentroSpawnPoint.getActionListeners()) { btnCentroSpawnPoint.removeActionListener(a); }
        for (ActionListener a : btnCentroAutoSpawnPointToggle.getActionListeners()) { btnCentroAutoSpawnPointToggle.removeActionListener(a); }


        // Funzionalità per il pulsante btnBack, che re-istanzia lo userPanel
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                SettingsPanel.this.setVisible(false);
                frame.getUserPanel().setVisible(true);
            }
        });


        // Funzionalità per il pulsante btnFermatePreferiteToggle
        btnFermatePreferiteToggle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });


        // Funzionalità per il pulsante btnCache
        btnCache.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
