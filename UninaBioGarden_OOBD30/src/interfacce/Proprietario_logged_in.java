package interfacce;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class Proprietario_logged_in extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller TheController;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Proprietario_logged_in(Controller TheController) {
		this.TheController = TheController;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Proprietario_logged_in.this.setVisible(false);
		setContentPane(contentPane);
	}

}
