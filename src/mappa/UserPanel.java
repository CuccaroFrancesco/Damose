package mappa;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextPane;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.jgoodies.forms.factories.Borders;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class UserPanel extends JPanel {
	
	public UserPanel() {
		
		setBackground(new Color(130, 36, 51));
		setLayout(null);
		
		JButton btnAccedi = new JButton("Accedi");
		btnAccedi.setBounds(75, 320, 250, 45);
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnAccedi);
		
		JButton btnRegistrati = new JButton("Registrati");
		btnRegistrati.setBounds(75, 400, 250, 45);
		btnRegistrati.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		add(btnRegistrati);
		
		// Scritta Ospite
		
		JLabel nomeUtente = new JLabel("Ospite");
		
		nomeUtente.setForeground(new Color(255, 255, 255));
		nomeUtente.setFont(new Font("Arial Nova", Font.BOLD, 24));
		
		nomeUtente.setHorizontalAlignment(SwingConstants.CENTER);
		nomeUtente.setFocusable(false);
		nomeUtente.setBounds(0, 200, 400, 50);
		
		add(nomeUtente);
		
		JTextField txtUsername = new JTextField();
		txtUsername.setBounds(75, 320, 250, 35);
		txtUsername.setVisible(false);
		add(txtUsername);
		
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setBounds(75, 400, 250, 35);
		txtPassword.setVisible(false);
		add(txtPassword);
		
		JButton btnConferma = new JButton("Login");
		btnConferma.setBounds(100, 480, 200, 45);
		btnConferma.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConferma.setVisible(false);
		add(btnConferma);
		
		JButton btnBack = new JButton("Torna indietro");
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setFont(new Font("Arial Nova", Font.BOLD, 14));
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(10, 11, 185, 23);
		btnBack.setVisible(false);
		add(btnBack);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblUsername.setFocusable(false);
		lblUsername.setVisible(false);
		lblUsername.setBounds(75, 276, 95, 35);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblPassword.setFocusable(false);
		lblPassword.setVisible(false);
		lblPassword.setBounds(75, 364, 95, 35);
		add(lblPassword);
		
		btnAccedi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(true);
				btnAccedi.setVisible(false);
				btnRegistrati.setVisible(false);
				txtUsername.setVisible(true);
				txtPassword.setVisible(true);
				btnConferma.setVisible(true);
				lblUsername.setVisible(true);
				lblPassword.setVisible(true);
			}
		});
		
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(false);
				txtUsername.setVisible(false);
				txtPassword.setVisible(false);
				btnConferma.setVisible(false);
				lblUsername.setVisible(false);
				lblPassword.setVisible(false);
				btnAccedi.setVisible(true);
				btnRegistrati.setVisible(true);
			}
		});

	}
}
