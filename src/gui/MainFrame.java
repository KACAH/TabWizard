package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import datastruct.TWSong;


public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	FrameScaleManager scaleManager;
	
	JButton SpecParam = new JButton();
	JButton SomeSugg = new JButton();
	JButton Settings = new JButton();
	JButton About = new JButton();

	RoundButton generate = new RoundButton();
	
	JLabel Name = new JLabel();

	public MainFrame()
	{
		super();
		this.setSize(600, 400);
		this.setTitle("Tab Wizard");
		this.getContentPane().setLayout(null);
		
		this.add(JButtonSpecParam());
		this.add(JButtonSomeSugg());
		this.add(JButtonSettings());
		this.add(JButtonAbout());
		
		this.add(RoundButtonGenerate());
		
		this.add(JLabelName());

		init();
	}

	private JButton JButtonSpecParam() 
	{
		SpecParam.setBounds(300, 170, 260, 30);
		SpecParam.setText("Write a song with specific parameters"); 
		SpecParam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.newtrackframe.setVisible(true);
				Main.mainframe.setVisible(false);
			}
		});
		
		return SpecParam;
	}
	
	private JButton JButtonSomeSugg() 
	{
		SomeSugg.setBounds(300, 200, 260, 30);
		SomeSugg.setText("Write a song with some suggestions"); 
		SomeSugg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somesuggestionframe.setVisible(true);
				Main.mainframe.setVisible(false);
			}
		});
		
		return SomeSugg;
	}
	
	private JButton JButtonSettings() 
	{
		Settings.setBounds(300, 230, 100, 30);
		Settings.setText("Settings"); 
		Settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					FrameScaleManager scaleManager = new FrameScaleManager();
					scaleManager.setVisible(true);
					
					Main.mainframe.setVisible(false);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		return Settings;
	}
	
	private JButton JButtonAbout() 
	{
		About.setBounds(300, 260, 80, 30);
		About.setText("About"); 
		About.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Created by Daniil and Vadim");
			}
		});
		
		return About;
	}

	private JLabel JLabelName() 					
	{
		Name.setBounds(250, 0, 260, 30);
		Name.setText("Tab Wizard 1.0"); 
		return Name;
	}
	
	private RoundButton RoundButtonGenerate()
	{
		generate.setBounds(50, 150, 150, 150);
		ImageIcon cup = new ImageIcon("media//Button.png");
		generate.setIcon(cup);
		generate.setToolTipText("Generate new song");
		
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					Main.Generate();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		return generate;
	}
	
	private void init()
	{	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	
}
