package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import music.TWGenerate;
import music.TWScaleManager;

import datastruct.TWDataException;

public class PlayingMethodsFrame extends JFrame{

	private static final long serialVersionUID = 8874696486929822528L;

	public static DefaultListModel<String> MethodListModel = new DefaultListModel<String>();
	public DefaultListModel<String> TracklistModelCopy = new DefaultListModel<String>();

	private String[] elements = new String[Main.newtrackframe.TracklistModel.size()];

	private JLabel setScale = new JLabel();
	private JLabel setMesCount = new JLabel();
	private JLabel setMethod = new JLabel();

	private JSpinner MeasureCount = new JSpinner(new SpinnerNumberModel(4, 1, 64, 1));

	private static JComboBox<String> scales;
	private JComboBox<String> methods;

	private JList<String> list;

	private JButton confirm = new JButton();
	private JButton add = new JButton();
	private JButton reset = new JButton();
	private JButton back = new JButton();
	private JButton delete = new JButton();
	private JButton showParts = new JButton();
	private RoundButton generate = new RoundButton();

	private JTextField FragmentName = new JTextField();

	public static String[] DrumMethods = {"Simple Drums (slow)", "Simple Drums (middle)",
		"Simple Drums (fast)", "Double Hat Drums (slow)",
		"Double Hat Drums (middle)", "Double Bass Drums (middle)", 
		"Double Bass Drums(fast)", "Open Hat Drums (slow)", "Open Hat Drums (middle)", "Nothing"};

	public static String[] BassMethods = {"Simple Bass Line", "Pause Bass Line", "Bass Line", "Nothing"};

	public static String[] HarmonyMethods = {"Acoustic Arpeggio", "Sound Wall", "Pause Sound Wall",
		"Hard Sound Wall", "Harmony", "Simple Melody", "Nothing"};

	public static String[] RiffMethods = {"Simple Riff", "Simple Power Riff", "Power Riff"};

	public PlayingMethodsFrame() throws IOException
	{
		init();

		this.setSize(700, 430);
		this.getContentPane().setLayout(null);
		this.setTitle("Set playing methods");
		this.add(setMeasureCount());
		this.add(confirmButton());
		this.add(addButton());
		this.add(resetButton());
		this.add(BackButton()); 
		this.add(showButton());
		this.add(deleteButton());
		this.add(RoundButtonGenerate());
		this.add(FragmentName());
		this.add(LabelSetMethod());
		this.add(LabelSetMesCount());
	}

	private JButton confirmButton() {
		confirm.setBounds(370, 250, 150, 30);
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
							TWGenerate.readyTracks.get(i).setMethod(methods.getSelectedItem().toString());

						if(list.getSelectedValue().split(": ")[1].equals("Drums"))
							TWGenerate.readyPercussionTrack.setMethod(methods.getSelectedItem().toString());
					}

					String[] split = methods.getSelectedItem().toString().split(" ");
					int count = split.length;

					if(methods.getSelectedItem().toString().split(" ")[count-1].equals("Riff"))
					{			
						for(int i = 0; i < Main.newtrackframe.TracklistModel.getSize();)
						{
							if(!Main.newtrackframe.TracklistModel.get(i).split(": ")[1].split("; ")[0].equals("Drums") )
							{
								if(!Main.newtrackframe.TracklistModel.get(i).equals(Main.newtrackframe.TracklistModel.get(list.getSelectedIndex())))
									Main.newtrackframe.TracklistModel.remove(i);
								else
									i++;
							}
							else
								i++;
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

	private JButton addButton() {
		add.setBounds(530, 250, 150, 30);
		add.setText("Add part");

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				TWGenerate.parts.add(TWGenerate.newPart);
				JOptionPane.showMessageDialog(null, "Fragment \"" + TWGenerate.newPart.getName() + "\" is added to fragment list");
				NewTrackFrame.song.removeSongPart(0);
				FragmentName.setText("");
			}
		});
		return add;
	}

	private JButton showButton() {
		showParts.setBounds(530, 350, 150, 30);
		showParts.setText("Show part list");

		showParts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				FragmentList frag = new FragmentList();
				frag.setVisible(true);
				setNonVisibleWindow();
			}
		});
		return showParts;
	}
	private void setNonVisibleWindow()
	{
		this.setVisible(false);
		this.dispose();
	}

	private JButton deleteButton() {
		delete.setBounds(530, 290, 150, 30);
		delete.setText("Delete part");

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int DialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?" , "Confirm" , JOptionPane.YES_NO_OPTION);
				if (DialogResult == 0)
				{
					NewTrackFrame.song.removeSongPart(0);
					JOptionPane.showMessageDialog(null, "Fragment \"" + TWGenerate.newPart.getName() + "\" is deleted");
					FragmentName.setText("");
				}
			}
		});
		return delete;
	}

	private JButton resetButton() {
		reset.setBounds(370, 290, 150, 30);
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
		Main.newtrackframe.TracklistModel.removeAllElements();

		for(int i = 0; i < elements.length; i++)
			Main.newtrackframe.TracklistModel.addElement(elements[i]);

		list.setModel(Main.newtrackframe.TracklistModel);

		clearReadyTrackMethods();
	}

	private void clearReadyTrackMethods()
	{
		for(int i = 0; i < TWGenerate.readyTracks.size(); i++)
			TWGenerate.readyTracks.get(i).setMethod("");
	}


	private JButton BackButton() {
		back.setBounds(370, 350, 150, 30);
		back.setText("Back to type");

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				resetWindow();
				closeWindow();
				Main.fragmenttypeframe.setVisible(true);

			}
		});
		return back;
	}

	private void closeWindow()
	{
		this.setVisible(false);
	}

	private RoundButton RoundButtonGenerate()
	{
		generate.setBounds(520, 20, 150, 150);
		ImageIcon cup = new ImageIcon("media//Button.png");
		generate.setIcon(cup);
		generate.setToolTipText("Generate new music fragment");
		generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				//for (int j = 0; j < TWGenerate.readyTracks.size(); j++)
				//	System.out.println(j + ") " + TWGenerate.readyTracks.get(j).getMethod() + " Njaaam");
				
				
				String name = JOptionPane.showInputDialog(null, "Enter a song part name", "Enter name", JOptionPane.QUESTION_MESSAGE);
				FragmentName.setText(name);

				try {

					TWGenerate.createParamSongPart(name);

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

	private JTextField FragmentName(){
		FragmentName.setBounds(530, 210, 150, 30);
		FragmentName.setEditable(false);
		return FragmentName;
	}

	private JComboBox<String> setScale() throws IOException
	{
		scales = new JComboBox<String>();
		scales.setBounds(10, 40, 150, 30);

		DefaultComboBoxModel<String> ñomboModel = new DefaultComboBoxModel<String>();
		scales.setModel(ñomboModel);

		for(int i = 0; i < TWScaleManager.StringScales.size(); i++)
		{
			String[] ScaleName = TWScaleManager.StringScales.get(i).split(" ");

			scales.addItem(ScaleName[0]);
		}
		return scales;
	}

	private JList<String> InstrumentList(){
		list = new JList<String>(Main.newtrackframe.TracklistModel);
		list.setBounds(10, 170, 350, 210);
		Border listPanelBorder = BorderFactory.createTitledBorder("Instruments");
		list.setBorder(listPanelBorder);
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
						fillMethodCombo("Drums");
						
					if(o.toString().split(", ")[1].equals("Bass tuning"))
						fillMethodCombo("Bass tuning");

					if(!o.toString().split(", ")[1].equals("Bass tuning") && !o.toString().split(":")[1].equals(" Drums"))
						fillMethodCombo("Harmony or Riff");
				}
			}
		});
		return list;
	}

	private void fillMethodCombo(String type)
	{
		if(type.equals("Drums"))
		{
			if(Main.fragmenttypeframe.isRiffFragment())
				scales.setEnabled(false);

			for(int i = 0; i < DrumMethods.length; i++)
				methods.addItem(DrumMethods[i]);
		}
		if(type.equals("Bass tuning"))
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
		if(!type.equals("Drums") && !type.equals("Bass tuning"))
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
	

	private JComboBox<String> setPlayingMethod() throws IOException
	{
		methods = new JComboBox<String>();
		methods.setBounds(370, 210, 150, 30);
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

	/**
	 * Get selected scale from list
	 * @return selected item
	 */
	public static String getSelectedScale()
	{
		return scales.getSelectedItem().toString();
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

	private void init() throws IOException
	{
		TWScaleManager.loadScales("data//Scales.twd");

		for(int i = 0; i < Main.newtrackframe.TracklistModel.size(); i++)
		{
			elements[i] = Main.newtrackframe.TracklistModel.get(i);
			TracklistModelCopy.addElement(elements[i]);
		}

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

		if(Main.fragmenttypeframe.isRiffFragment())
			RiffInit();
		if(Main.fragmenttypeframe.isHarmonyFragment())
			HarmonyInit();
	}
}
