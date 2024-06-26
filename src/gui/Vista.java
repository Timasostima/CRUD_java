package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bd.BaseQueries;
import logica.Usuario;

public class Vista extends JFrame {

	// LOS COLORES VAN DE OSCURO A CLARO
	final static int COLOR1 = 0x022b3a;
	final static int COLOR2 = 0x1f7a8c;
	final static int COLOR3 = 0xbfdbf7;
	final static int COLOR4 = 0xe1e5f2;
	final static int COLOR5 = 0xffffff;
	static ArrayList<String> errores = new ArrayList<String>();

	public Vista() {

		ImageIcon icono = new ImageIcon("images//miniLogoNoodle.png");

		this.setIconImage(icono.getImage());
		this.setTitle("Ñoodle");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350, 350);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		MiPanel logIn = new MiPanel();
		this.add(logIn);

		this.setVisible(true);

	}

	public static JButton crearBoton(String texto, char mnemonic) {
		JButton b = new JButton(texto);
		b.setFocusable(false);
		b.setForeground((new Color(Vista.COLOR1)));
		b.setFont(new Font("Consolas", Font.PLAIN, 14));
		b.setMnemonic(mnemonic);
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b.setBackground(new Color(Vista.COLOR3));
		b.setBorder(BorderFactory.createLineBorder(new Color(Vista.COLOR1), 2, true));
		return b;
	}

	public static boolean comprobarNombre(String nombreUsuario) {

		boolean longitudAdecuada = nombreUsuario.length() >= 4 && nombreUsuario.length() <= 16;

		if (nombreUsuario.isEmpty() || !longitudAdecuada) {
			errores.add("El nombre debe tener entre 4 y 16 caracteres.");
			return false;
		}

		boolean may1 = nombreUsuario.charAt(0) >= 'A' && nombreUsuario.charAt(0) <= 'Z';
		boolean adecuado = nombreUsuario.substring(1).matches("[a-z]*");

		if (longitudAdecuada && may1 && adecuado)
			return true;

		if (!may1) {
			errores.add("El nombre debe comenzar por mayúscula.");
		} else if (!adecuado) {
			errores.add("El nombre debe comenzar por letra mayúscula.");
			errores.add("El resto de letras deben minúsculas.");
		}
		return false;
	}

	public static boolean comprobarContrasena(String contrasena) {

		boolean longitudAdecuada = contrasena.length() >= 4 && contrasena.length() <= 16;

		if (contrasena.isEmpty() || !longitudAdecuada) {
			errores.add("La contraseña debe tener entre 4 y 16 caracteres.");
			return false;
		}

		boolean hayMin = contrasena.matches(".*[a-z].*");
		boolean hayMay = contrasena.matches(".*[A-Z].*");
		boolean hayNum = contrasena.matches(".*[1-9].*");

		if (longitudAdecuada && hayMin && hayMay && hayNum)
			return true;

		if (!hayMin)
			errores.add("La contraseña debe tener al menos una letra minúscula.");
		if (!hayMay)
			errores.add("La contraseña debe tener al menos una letra mayúscula.");
		if (!hayNum)
			errores.add("La contraseña debe tener al menos un número.");

		return false;
	}

	class MiPanel extends JPanel implements ActionListener {

		JButton signIn, logIn;
		JTextField nombre;
		JPasswordField contrasena;
		Image logo, logoU, logoC;
		JLabel errorUsuario, errorContrasena;

		public MiPanel() {

			this.setLayout(null);
			this.setBackground(new Color(Vista.COLOR4));
			this.setBorder(BorderFactory.createLineBorder(new Color(Vista.COLOR1), 3));

			nombre = crearCampoTexto();
			nombre.setBounds(55, 130, 225, 25);
			contrasena = crearCampoContrasena();
			contrasena.setBounds(55, 180, 225, 25);

			this.add(nombre);
			this.add(contrasena);

			signIn = crearBoton("Sign in", 's');
			signIn.setBounds(55, 230, 80, 30);
			signIn.addActionListener(this);
			logIn = crearBoton("Log in", 'l');
			logIn.setBounds(200, 230, 80, 30);
			logIn.addActionListener(this);
			this.add(signIn);
			this.add(logIn);

			this.setOpaque(true);

		}

		private JTextField crearCampoTexto() {
			JTextField t = new JTextField();
			t.setForeground(Color.GRAY);
			t.setFont(new Font("Consolas", Font.PLAIN, 18));
			t.setBorder(BorderFactory.createLineBorder(new Color(Vista.COLOR1), 2));
			return t;
		}

		private JPasswordField crearCampoContrasena() {
			JPasswordField contrasena = new JPasswordField();
			contrasena.setForeground(Color.GRAY);
			contrasena.setFont(new Font("Consolas", Font.PLAIN, 18));
			contrasena.setBorder(BorderFactory.createLineBorder(new Color(Vista.COLOR1), 2));
			return contrasena;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			logo = new ImageIcon("images//logoNoodle.png").getImage();
			logoU = new ImageIcon("images//usuario.png").getImage();
			logoC = new ImageIcon("images//contrasena.png").getImage();
			g.drawImage(logo, 87, 45, 150, 50, null);
			g.drawImage(logoU, 30, 133, 18, 18, null);
			g.drawImage(logoC, 30, 183, 18, 18, null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			VistaError ventanaError;

			if (e.getSource() == logIn) {
				String nombreUsusario = nombre.getText();
				String contrasenaUsuario = String.valueOf(contrasena.getPassword());
				Usuario u = BaseQueries.buscarUsuario(nombreUsusario, contrasenaUsuario);
				boolean datosCorrectos = u != null;

				if (datosCorrectos) {
					Programa programa = new Programa(u);
					Vista.this.dispose();
				} else {
					errores.add("El nombre de usuario y/o contraseña no coinciden.");
					ventanaError = new VistaError(errores);
					return;
				}

			} else if (e.getSource() == signIn) {
				String nombreUsuario = nombre.getText();
				String contrasenaUsuario = String.valueOf(contrasena.getPassword());

				boolean nombreOcupado = BaseQueries.hayNombreUsuario(nombreUsuario);
				if (nombreOcupado) {
					errores.add("Nombre de usuario no disponible");
					ventanaError = new VistaError(errores);
					return;
				}

				boolean nombreAdecuado = comprobarNombre(nombreUsuario);
				if (!nombreAdecuado) {
					ventanaError = new VistaError(errores);
					return;
				}

				boolean contrasenaAdecuada = comprobarContrasena(contrasenaUsuario);
				if (!contrasenaAdecuada) {
					ventanaError = new VistaError(errores);
					return;
				}

				if (nombreAdecuado && contrasenaAdecuada) {
					BaseQueries.signIn(nombreUsuario, contrasenaUsuario);
					nombre.setText("");
					contrasena.setText("");
					ventanaError = new VistaError(errores);
				}

			}
		}

	}

}
