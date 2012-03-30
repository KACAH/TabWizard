package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import music.TWGenerate;


public class FragmentTypeFrame extends JFrame{

	private static final long serialVersionUID = 1291585879597341458L;

	private JRadioButton Riff = new JRadioButton();
	private JRadioButton Harmony = new JRadioButton();

	private JLabel Type = new JLabel();

	private JButton nextStep = new JButton();

	public FragmentTypeFrame()
	{
		init();
		this.setSize(300, 150);
		this.getContentPane().setLayout(null);
		this.setTitle("Music fragment type");
		this.add(LabeType());
		this.add(setRiff());
		this.add(setHarmony());
		this.add(nextStep());
	}

	private JLabel LabeType(){
		Type.setBounds(10, 10, 170, 15);
		Type.setText("Set type for music fragment");
		return Type;
	}

	private JRadioButton setRiff(){
		Riff.setBounds(10, 40, 100, 20);
		Riff.setText("Riff");
		Riff.setSelected(true);
		return Riff;
	}

	private JRadioButton setHarmony(){
		Harmony.setBounds(10, 60, 100, 20);
		Harmony.setText("Harmony");
		return Harmony;
	}

	private JButton nextStep() {
		nextStep.setBounds(170, 60, 100, 30);
		nextStep.setText("Next Step");
		nextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//System.out.println(Main.newtrackframe.TracklistModel.get(0).split(", ")[1].split(" tuning")[0]);

					if(Main.newtrackframe.TracklistModel.get(0).split(", ")[1].split(" tuning")[0].equals("Bass") && Main.newtrackframe.TracklistModel.getSize() == 1)
					{
						if(Riff.isSelected())
						{
							JOptionPane.showMessageDialog(null, "With only one bass track you can't make riffs");
							return;
						}
					}
				try {
					PlayingMethodsFrame plf = new PlayingMethodsFrame();
					Main.fragmenttypeframe.setVisible(false);
					plf.setVisible(true);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		return nextStep;
	}

	private void init()
	{	
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(setRiff());
		buttonGroup.add(setHarmony());

		this.setResizable(false);

		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event) {
				if (event.getID() == WindowEvent.WINDOW_CLOSING) 
				{
					Main.mainframe.setVisible(true);
					Main.newtrackframe.TracklistModel.clear();
					TWGenerate.readyTracks.clear();
				}
			}
		});
	}
	
	public boolean isRiffFragment(){
		return Riff.isSelected();
	}
	
	public boolean isHarmonyFragment(){
		return Harmony.isSelected();
	}
}
