package lolPing;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Lol extends JFrame {
	int ping,progreso;
	static String infor="";

	static JLabel lblPing = new JLabel("      Ping: ");
	static JLabel info = new JLabel(infor);

	static JProgressBar progressBar=new JProgressBar();
	static Interpreter interp;



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	private JPanel contentPane;

	/**
	 * Launchfdf the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lol frame = new Lol();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Lol() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Lol.class.getResource("/lolPing/LALolPing.jpg")));
		setResizable(false);
		
		
		
		setTitle("LALolPing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 437, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{136, 0, 131, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{87, 42, 72, 0, 23, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEnabled(false);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"LAS"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTHEAST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(255, 140, 0));
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 2;
		gbc_progressBar.gridy = 3;
		contentPane.add(progressBar, gbc_progressBar);
		
		lblPing = new JLabel("      Ping: ");
		lblPing.setForeground(new Color(0, 0, 255));
		lblPing.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblPing = new GridBagConstraints();
		gbc_lblPing.fill = GridBagConstraints.BOTH;
		gbc_lblPing.insets = new Insets(0, 0, 5, 5);
		gbc_lblPing.gridx = 2;
		gbc_lblPing.gridy = 1;
		contentPane.add(lblPing, gbc_lblPing);
		
		info = new JLabel(infor);
		GridBagConstraints gbc_info = new GridBagConstraints();
		gbc_info.gridwidth = 3;
		gbc_info.insets = new Insets(0, 0, 5, 5);
		gbc_info.gridx = 1;
		gbc_info.gridy = 2;
		contentPane.add(info, gbc_info);
		
		JButton btnProbar = new JButton("Probar");
		btnProbar.addMouseListener(new MouseAdapter() {
			@Override
			



			public void mouseClicked(MouseEvent arg0) {


				InterpreterManager interp= new InterpreterManager("138.0.12.100");
				interp.start();
				info.setText("Midiendo...");
				btnProbar.setEnabled(false);


			}
		});
		


		

	
		GridBagConstraints gbc_btnProbar = new GridBagConstraints();
		gbc_btnProbar.insets = new Insets(0, 0, 0, 5);
		gbc_btnProbar.anchor = GridBagConstraints.NORTH;
		gbc_btnProbar.gridx = 2;
		gbc_btnProbar.gridy = 4;
		contentPane.add(btnProbar, gbc_btnProbar);
		

		
	}
	public static void updateStatus(int Ping, boolean packetloss){

	/*	if (packetloss){info.setText("Perdida de Paquetes");
			lblPing.setForeground(Color.RED);}*/
		if (Ping>=70){lblPing.setForeground(Color.ORANGE);progressBar.setForeground(Color.ORANGE);}
		if (Ping>=100||Ping==0||Ping==3){lblPing.setForeground(Color.RED);progressBar.setForeground(Color.RED);} //o mas de 100 o no calculo o error
		if (Ping<70&&Ping>3){
			if(lblPing==null)
				System.out.println("es null");
			lblPing.setForeground(new Color(0, 100, 0));
			progressBar.setForeground(new Color(0, 100, 0));}//Verde oscuro
		if (Ping<=100){progressBar.setValue(Ping);}
		else if (Ping>100){progressBar.setValue(100);}
		lblPing.setText("      Ping: "+ Ping);

		//System.out.println(Ping);
	}

}
