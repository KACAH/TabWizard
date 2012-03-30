package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import music.TWGenerate;
import music.TWScaleManager;

import datastruct.TWDataException;

public class PlayingMethodsFrame extends JFrame{

	private static final long serialVersionUID = 8874696486929822528L;

	public static DefaultListModel<String> MethodListModel = new DefaultListModel<String>();

	private JLabel setScale = new JLabel();
	private JLabel setMesCount = new JLabel();
	private JLabel setMethod = new JLabel();
	private JSpinner MeasureCount = new JSpinner(new SpinnerNumberModel(4, 1, 64, 1));
	private static JComboBox<String> scales;
	private JComboBox<String> methods;
	private static JList<String> list;
	private JButton confirm = new JButton();
	private JButton reset = new JButton();
	private JButton back = new JButton();
	private RoundButton generate = new RoundButton();

	private String[] DrumMethods = {"Simple Drums (slow)", "Simple Drums (middle)",
			"Simple Drums (fast)", "Double Hat Drums (slow)",
			"Double Hat Drums (middle)", "Double Bass Drums (middle)", 
			"Double Bass Drums(fast)", "Open Hat Drums (slow)", "Open Hat Drums (middle)"};

	private String[] BassMethods = {"Simple Bass Line", "Pause Bass Line", "Bass Line"};

	private String[] HarmonyMethods = {"Acoustic Arpeggio", "Sound Wall", "Pause Sound Wall",
			"Hard Sound Wall", "Harmony", "Simple Melody"};

	private String[] RiffMethods = {"Simple Riff", "Simple Power Riff", "Power Riff"};

	public PlayingMethodsFrame() throws IOException
	{
		init();

		this.setSize(700, 430);
		this.getContentPane().setLayout(null);
		this.setTitle("Set playing methods");
		this.add(setMeasureCount());
		this.add(confirmButton());
		this.add(resetButton());
		this.add(BackButton());
		this.add(RoundButtonGenerate());
		this.add(LabelSetMethod());
		this.add(LabelSetMesCount());

		if(Main.fragmenttypeframe.isRiffFragment())
			RiffInit();
		if(Main.fragmenttypeframe.isHarmonyFragment())
			HarmonyInit();
	}

	private JButton confirmButton() {
		confirm.setBounds(570, 350, 100, 30);
		confirm.setText("Confirm method");

		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(list.getSelectedIndex() == -1)
				{
					JOptionPane.showMessageDialog(null, "Select a track");
					return;
				}

				int DialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?" , "Confirm" , JOptionPane.YES_NO_OPTION);
				if (DialogResult == 0)
				{
					for(int i = 0; i < TWGenerate.readyTracks.size(); i++)
					{
						String TrackName = list.getSelectedValue().split("\\) ")[1].split(",")[0];
		
						if(TrackName.equals(TWGenerate.readyTracks.get(i).getHeaderName()))
						{
							TWGenerate.readyTracks.get(i).setMethod(methods.getSelectedItem().toString());
						}
						
						if(list.getSelectedValue().split(": ")[1].equals("Drums"))
							TWGenerate.readyPercussionTrack.setMethod(methods.getSelectedItem().toString());
					}

					String[] split = methods.getSelectedItem().toString().split(" ");
					int count = split.length;

					if(methods.getSelectedItem().toString().split(" ")[count-1].equals("Riff"))
					{			
						for(int i = 0; i < Main.newtrackframe.TracklistModel.getSize(); i++)
						{
							if(!Main.newtrackframe.TracklistModel.get(i).split(": ")[1].split("; ")[0].equals("Drums") )
							{
								if(!Main.newtrackframe.TracklistModel.get(i).equals(Main.newtrackframe.TracklistModel.get(list.getSelectedIndex())))
								{
									Main.newtrackframe.TracklistModel.remove(i);
									i = 0;
								}
							}
						}
						JOptionPane.showMessageDialog(null, "Only one track can use riff method");
					}

					Main.newtrackframe.TracklistModel.remove(list.getSelectedIndex());
					JOptionPane.showMessageDialog(null, "This track is added to song");

					TWGenerate.MeasureCount = (Integer) MeasureCount.getValue();
				}
			}
		});
		return confirm;
	}
	
	private JButton resetButton() {
		reset.setBounds(570, 310, 100, 30);
		reset.setText("Reset");

		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				resetWindow();
			}
		});
		return reset;
	}
	
	private void resetWindow()
	{
		this.setVisible(false); 
		System.out.println("Njam");
		this.setVisible(true);
	}
	
	private JButton BackButton() {
		back.setBounds(410, 350, 150, 30);
		back.setText("Back to type");

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		return back;
	}

	private RoundButton RoundButtonGenerate()
	{
		generate.setBounds(520, 30, 150, 150);
		ImageIcon cup = new ImageIcon("media//Button.png");
		generate.setIcon(cup);

		generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String name = JOptionPane.showInputDialog(null, "Enter a song part name", "Enter name", JOptionPane.QUESTION_MESSAGE);

				try {
					TWGenerate.createSongPart(name);

				} catch (TWDataException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		});
		return generate;
	}

	private JSpinner setMeasureCount() {
		MeasureCount.setBounds(10, 100, 50, 30);
		JFormattedTextField tf = ((JSpinner.DefaultEditor)MeasureCount.getEditor()).getTextField();
		tf.setEditable(false);

		return MeasureCount;
	}

	private JComboBox<String> setScale() throws IOException
	{
		scales = new JComboBox<String>();
		scales.setBounds(10, 40, 150, 30);

		DefaultComboBoxModel<String> ˝omboModel = new DefaultComboBoxModel<String>();
		scales.setModel(˝omboModel);

		for(int i = 0; i < TWScaleManager.StringScales.size(); i++)
		{
			String[] ScaleName = TWScaleManager.StringScales.get(i).split(" ");

			scales.addItem(ScaleName[0]);
		}
		return scales;
	}

	private void RiffInit() throws IOException
	{
		this.add(setScale());
		this.add(LabelScale());
		this.add(InstrumentList());
		this.add(setPlayingMethod());
	}

	private void HarmonyInit() throws IOException
	{
		this.add(InstrumentList());
		this.add(setPlayingMethod());
	}

	private JList<String> InstrumentList(){
		list = new JList<String>(Main.newtrackframe.TracklistModel);
		list.setBounds(10, 170, 350, 200);
		list.setLayoutOrientation(JList.VERTICAL);

		list.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				methods.removeAllItems();

				@SuppressWarnings("unchecked")
				JList<String> theList = (JList<String>) e.getSource();

				int index = theList.locationToIndex(e.getPoint());
				if (index >= 0) 
				{
					Object o = theList.getModel().getElementAt(index);

					if(o.toString().split(": ")[1].equals("Drums"))
					{
						if(Main.fragmenttypeframe.isRiffFragment())
							scales.setEnabled(false);

						for(int i = 0; i < DrumMethods.length; i++)
							methods.addItem(DrumMethods[i]);
					}				
					if(o.toString().split(", ")[1].equals("Bass tuning"))
					{
						if(Main.fragmenttypeframe.isRiffFragment())
						{
							methods.addItem("--");
							scales.setEnabled(false);
						}
						else
						{
							for(int i = 0; i < BassMethods.length; i++)
								methods.addItem(BassMethods[i]);
						}
					}
					if(!o.toString().split(", ")[1].equals("Bass tuning") && !o.toString().split(":")[1].equals(" Drums"))
					{
						if(Main.fragmenttypeframe.isHarmonyFragment())
						{
							for(int i = 0; i < HarmonyMethods.length; i++)
								methods.addItem(HarmonyMethods[i]);
						}
						if(Main.fragmenttypeframe.isRiffFragment())
						{
							scales.setEnabled(true);

							for(int i = 0; i < RiffMethods.length; i++)
								methods.addItem(RiffMethods[i]);
						}
					}
				}
			}
		});
		return list;
	}


	private JComboBox<String> setPlayingMethod() throws IOException
	{
		methods = new JComboBox<String>();
		methods.setBounds(370, 200, 150, 30);
		return methods;
	}

	private JLabel LabelScale(){
		setScale.setBounds(20, 20, 80, 15);
		setScale.setText("Choose scale:");
		return setScale;
	}

	private JLabel LabelSetMethod(){
		setMethod.setBounds(20, 150, 150, 15);
		setMethod.setText("Choose playing method:");
		return setMethod;
	}

	private JLabel LabelSetMesCount(){
		setMesCount.setBounds(20, 80, 110, 15);
		setMesCount.setText("Set measure count");
		return 	setMesCount;
	}

	public static String getSelectedMethod()
	{
		return list.getSelectedValue();
	}

	public static String getSelectedScale()
	{
		return scales.getSelectedItem().toString();
	}

	private void init()
	{
		TWScaleManager.loadScales("data//Scales.twd");

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
		for(int i = 0; i < DrumMethods.length; i++)
			MethodListModel.addElement(DrumMethods[i]);
		for(int i = 0; i < BassMethods.length; i++)
			MethodListModel.addElement(BassMethods[i]);
		for(int i = 0; i < HarmonyMethods.length; i++)
			MethodListModel.addElement(HarmonyMethods[i]);
		for(int i = 0; i < RiffMethods.length; i++)
			MethodListModel.addElement(RiffMethods[i]);
	}
}
