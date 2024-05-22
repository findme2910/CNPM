package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class UILogin extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JTextField txtPassword;
	private JLabel textDangNhap;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UILogin frame = new UILogin();
////					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public UILogin() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int tiLe = 50;
		setBounds(0, 0, 16 * tiLe, 9 * tiLe);
//		setBounds(0, 0, 9 * tiLe, 9 * tiLe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel textBG = new JLabel("");
		textBG.setToolTipText("");
		textBG.setBounds(0, 0, getWidth() - 14, getHeight() - 37);
		ImageIcon bg = new ImageIcon(
				UILogin.class.getResource("/res/z4272673869722_1dc2f84a9d6a1d66fea3ef70541bbd9f.jpg"));
		bg.setImage(bg.getImage().getScaledInstance(textBG.getWidth(), textBG.getHeight(), Image.SCALE_DEFAULT));

		int doDaiField = 250;
		int doCaoField = 40;

		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(new Rectangle(doDaiField, doCaoField));
		textFieldUserName.setLocation(getWidth() / 2 - textFieldUserName.getBounds().width / 2,
				getHeight() / 2 - textFieldUserName.getBounds().height / 2 - doCaoField);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);
		textFieldUserName.setText("Username");

		txtPassword = new JTextField();
		txtPassword.setText("Password");
		txtPassword.setColumns(10);
		txtPassword.setBounds(new Rectangle(doDaiField, doCaoField));
		txtPassword.setLocation(textFieldUserName.getX(), textFieldUserName.getY() + doCaoField * 3 / 2);
		contentPane.add(txtPassword);

		textDangNhap = new JLabel("\u0110\u0103ng nh\u1EADp");
		textDangNhap.setHorizontalAlignment(SwingConstants.CENTER);
		textDangNhap.setFont(new Font("Tahoma", Font.BOLD, 40));
		textDangNhap.setForeground(new Color(255, 255, 255));
		textDangNhap.setBounds(203, 225, 358, 53);
		textDangNhap.setLocation(getWidth() / 2 - textDangNhap.getWidth() / 2,
				textFieldUserName.getY() - doCaoField * 3 / 2);
		contentPane.add(textDangNhap);

		textBG.setIcon(bg);

		contentPane.add(textBG);

		contentPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				textBG.setBounds(0, 0, getWidth() - 14, getHeight() - 37);
				bg.setImage(
						bg.getImage().getScaledInstance(textBG.getWidth(), textBG.getHeight(), Image.SCALE_DEFAULT));
				textFieldUserName.setLocation(getWidth() / 2 - textFieldUserName.getBounds().width / 2,
						getHeight() / 2 - textFieldUserName.getBounds().height / 2 - doCaoField);
				txtPassword.setLocation(textFieldUserName.getX(), textFieldUserName.getY() + doCaoField * 3 / 2);
				textDangNhap.setLocation(getWidth() / 2 - textDangNhap.getWidth() / 2,
						textFieldUserName.getY() - doCaoField * 2);
			}
		});
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}
}
