package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class TrangChu extends JFrame {

	private JPanel contentPane;
	private UILogin uiLogin;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public TrangChu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int tiLe = 60;
		setBounds(0, 0, 16 * tiLe, 9 * tiLe);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel textBG = new JLabel("");
		textBG.setBounds(0, 0, this.getWidth() - 14, this.getHeight() - 37);
		ImageIcon bg = new ImageIcon(
				TrangChu.class.getResource("/res/z4272273272369_948563127918085e5bf497bb68382044.jpg"));
		bg.setImage(bg.getImage().getScaledInstance(textBG.getWidth(), textBG.getHeight(), Image.SCALE_DEFAULT));
		textBG.setIcon(bg);
		contentPane.add(textBG);

		JLabel textBenhVien = new JLabel("B\u1EC6NH VI\u1EC6N");
		textBenhVien.setForeground(new Color(255, 0, 255));
		textBenhVien.setBounds(-23, 11, 270, 57);
		contentPane.add(textBenhVien);
		textBenhVien.setHorizontalAlignment(SwingConstants.CENTER);
		textBenhVien.setFont(new Font("RixLoveFool", Font.BOLD, 40));

		JButton btnLogin = new JButton("\u0110\u0103ng nh\u1EADp");
		btnLogin.setForeground(SystemColor.textHighlight);
		btnLogin.setBounds(20, 79, 122, 27);
		contentPane.add(btnLogin);
		btnLogin.setFont(new Font("UTM Alberta Heavy", Font.PLAIN, 15));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (uiLogin == null)
					uiLogin = new UILogin();
				else
					uiLogin.setVisible(true);
			}
		});

		JButton btnExit = new JButton("Tho\u00E1t");
		btnExit.setForeground(new Color(255, 0, 0));
		btnExit.setBounds(20, 117, 122, 27);
		contentPane.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("UTM Alberta Heavy", Font.PLAIN, 15));

		textBG.setIcon(bg);
		contentPane.add(textBG);

		contentPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				textBG.setBounds(0, 0, getWidth() - 14, getHeight() - 37);
				bg.setImage(
						bg.getImage().getScaledInstance(textBG.getWidth(), textBG.getHeight(), Image.SCALE_DEFAULT));
			}
		});

		this.setLocationRelativeTo(null);
		this.setVisible(true);
//		this.setResizable(false);
	}
}
