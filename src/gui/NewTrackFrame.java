package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import music.ReadyTrackFragmentForWrite;
import music.TWGenerate;
import music.TWHarmonyGenerator;

import datastruct.TWColor;
import datastruct.TWDataException;
import datastruct.TWInstruments;
import datastruct.TWSong;
import datastruct.TWStringsTunning;
import datastruct.TWTrackHeader;

public class NewTrackFrame extends JFrame {

	private static final long serialVersionUID = -3224588457961360603L;

	public static TWSong song;

	public DefaultListModel<String> TracklistModel = new DefaultListModel<String>();

	private ArrayList<String> Instruments = new ArrayList<String>();

	private JButton addNewSimpleTrack = new JButton();
	private JButton addNewPercussionTrack = new JButton();
	private JButton Delete = new JButton();
	private JButton nextStep = new JButton();

	private JTextField SimpleTrackName = new JTextField();
	private JTextField PercTrackName = new JTextField();
	private JTextField SongName = new JTextField();

	private JSpinner SimpleTrackVolume = new JSpinner(new SpinnerNumberModel(13, 0, 16, 1));
	private JSpinner PercTrackVolume = new JSpinner(new SpinnerNumberModel(13, 0, 16, 1));
	private JSpinner Tempo = new JSpinner(new SpinnerNumberModel(150, 1, 300, 1));

	private JSlider Balance = new JSlider();

	private JComboBox<String> Tuning;
	private JComboBox<String> instruments;

	private JList<String> TrackList;

	private JLabel LabelTempo = new JLabel();
	private JLabel LabelSongName = new JLabel();
	private JLabel LabelSimpleTrack = new JLabel();
	private JLabel LabelPercTrack = new JLabel();


	public NewTrackFrame() throws IOException
	{
		this.setSize(700, 430);
		this.getContentPane().setLayout(null);
		this.setTitle("Add tracks");

		init();

		this.add(AddSimpleTrackButton());
		this.add(AddPercussionTrackButton());
		this.add(DeleteFromList());
		this.add(nextStep());
		this.add(newSimpleTrackName());
		this.add(newPercussionTrackName());
		this.add(setSongName());
		this.add(newSimpleTrackVolume());
		this.add(newPercussionTrackVolume());
		this.add(newSongTempo());
		this.add(newTrackBalance());
		this.add(newTrackTuning());
		this.add(newTrackInstruments());
		this.add(newTrackList());
		this.add(Tempo());
		this.add(SimpleTrack());
		this.add(PercussionTrack());
		this.add(SongName());
	}

	private JButton AddSimpleTrackButton() 
	{
		addNewSimpleTrack.setBounds(570, 70, 100, 30);
		addNewSimpleTrack.setText("Add Track");
		addNewSimpleTrack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(SimpleTrackName.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Name is empty");
				else
				{
					String name = SimpleTrackName.getText();
					int volume = (Integer) SimpleTrackVolume.getValue()*8-1; //Max volume = 127
					int balance = Balance.getValue()*8+64; // 64 - balance center
					String tuning = (String) Tuning.getSelectedItem();
					
					String instr = (String) instruments.getSelectedItem();
					int instrument = instruments.getSelectedIndex()+1;

					String ListElem = TracklistModel.size()+1 + ") " + name + ", " + tuning + " tuning,  instrument: " + instr;

					if(TracklistModel.size() != 0)
					{
						if(TracklistModel.size() >= 10)
						{
							JOptionPane.showMessageDialog(null, "The maximum track count is 10");
							return;
						}	
						for(int i = 0; i < TracklistModel.size(); i++)
						{
							// Get track name splitting string in list
							String TrackName = TracklistModel.get(i).split("\\) ")[1].split(",")[0];

							if(SimpleTrackName.getText().equals(TrackName))
							{
								JOptionPane.showMessageDialog(null, "Please, enter the unique name");
								return;
							}
						}
						addElemToList(ListElem);
						addTrackHeader(name, volume, balance, tuning, instrument);
					}
					else
					{
						addElemToList(ListElem);
						addTrackHeader(name, volume, balance, tuning, instrument);
					}
				}
			}
		});
		return addNewSimpleTrack;
	}

	private void addTrackHeader(String name, int volume, int balance, String tuning, int instrument)
	{
		if(tuning.equals("Standart"))
		{
			Random rnd = new Random();
			
			int rndR = rnd.nextInt(255);
			int rndG = rnd.nextInt(255);
			int rndB = rnd.nextInt(255);
			
			TWTrackHeader header = new TWTrackHeader(name, (short)volume, (short)balance, new TWColor(rndR,rndG,rndB), new TWStringsTunning(TWStringsTunning.STANDARD), (short)instrument);
			ReadyTrackFragmentForWrite newTr = new ReadyTrackFragmentForWrite();
			newTr.setTrackHeader(header);
			TWGenerate.readyTracks.add(newTr);
		}
		if(tuning.equals("Bass"))
		{
			TWTrackHeader header = new TWTrackHeader(name, (short)volume, (short)balance, new TWColor(12,123,32), new TWStringsTunning(TWStringsTunning.BASS), (short)instrument);
			ReadyTrackFragmentForWrite newTr = new ReadyTrackFragmentForWrite();
			newTr.setTrackHeader(header);
			TWGenerate.readyTracks.add(newTr);
		}
	}

	private void addElemToList(String elem)
	{
		TracklistModel.addElement(elem);
		int index = TracklistModel.size() - 1;
		TrackList.setSelectedIndex(index);
		TrackList.ensureIndexIsVisible(index);

		SimpleTrackName.setText("");
	}

	private JButton AddPercussionTrackButton() 
	{
		addNewPercussionTrack.setBounds(180, 125, 100, 30);
		addNewPercussionTrack.setText("Add Track");

		addNewPercussionTrack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(PercTrackName.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Name is empty");
				else
				{
					String name = PercTrackName.getText();
					String PercTrackElem = TracklistModel.size()+1 + ") " + name + ", " + "instrument: Drums";

					if(TracklistModel.size() != 0)
					{
						if(TracklistModel.size() >= 10)
						{
							JOptionPane.showMessageDialog(null, "The maximum track count is 10");
							return;
						}
						
						for(int i = 0; i < TracklistModel.size(); i++)
						{
							String TrackName = TracklistModel.get(i).split("\\) ")[1].split(",")[0];

							if(PercTrackName.getText().equals(TrackName))
							{
								JOptionPane.showMessageDialog(null, "Please, enter the unique name");
								return;
							}
						}
						
						addElemToList(PercTrackElem);			
						PercTrackName.setText("");
						PercTrackName.setEnabled(false);
						addNewPercussionTrack.setEnabled(false);

						TWTrackHeader header = new TWTrackHeader(name, TWInstruments.DRUMS);
						TWGenerate.readyPercussionTrack.setTrackHeader(header);
					}
					else
					{
						addElemToList(PercTrackElem);				
						PercTrackName.setEnabled(false);
						addNewPercussionTrack.setEnabled(false);

						TWTrackHeader header = new TWTrackHeader(name, TWInstruments.DRUMS);
						TWGenerate.readyPercussionTrack.setTrackHeader(header);
					}
				}
			}
		});
		return addNewPercussionTrack;
	}

	private JButton DeleteFromList()
	{
		Delete.setBounds(380, 170, 170, 30);
		Delete.setText("Delete selected track");
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TracklistModel.size() == 0)
					JOptionPane.showMessageDialog(null, "List is empty");
				else
				{
					String[] elem = TrackList.getSelectedValue().split(":");
					if(elem[1].equals(" Drums"))
					{
						PercTrackName.setEnabled(true);
						addNewPercussionTrack.setEnabled(true);

						TWGenerate.PercTrack = null;
					}
					else
						TWGenerate.readyTracks.remove(TrackList.getSelectedIndex());

					TracklistModel.remove(TrackList.getSelectedIndex());
				}
			}
		});
		return Delete;
	}

	private JButton nextStep() {
		nextStep.setBounds(570, 350, 100, 30);
		nextStep.setText("Next Step");

		nextStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(TracklistModel.size() != 0)
				{	
					if(!hasBassTrack() || !hasDrumTrack())
						return;
					else
					{
						Main.newtrackframe.setVisible(false);
						Main.fragmenttypeframe.setVisible(true);

						PercTrackName.setEnabled(true);
						addNewPercussionTrack.setEnabled(true);

						try {
							TWGenerate.Harmony = TWHarmonyGenerator.generateSimpleHarmony();

							song = new TWSong(getTempo());

							
							for(int i = 0; i < TWGenerate.readyTracks.size(); i++)
								song.addTrack(TWGenerate.readyTracks.get(i).getHeader());

							for(int i = 0; i < TracklistModel.size(); i++)
							{
								if(TracklistModel.get(i).split(":")[1].equals(" Drums"))
									song.addTrack(TWGenerate.readyPercussionTrack.getHeader());
							}
						} catch (TWDataException e1) {
							e1.printStackTrace();
						}
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please, add tracks");
			}
		});
		return nextStep;
	}

	private boolean hasBassTrack()
	{
		for(int i = 0; i < TracklistModel.size(); i++)
		{
			if(TracklistModel.get(i).split(", ")[1].split(" tuning")[0].equals("Bass"))
				return true;
		}
		JOptionPane.showMessageDialog(null, "Please add a bass track");
		return false;
	}
	
	private boolean hasDrumTrack()
	{
		for(int i = 0; i < TracklistModel.size(); i++)
		{
			if(TracklistModel.get(i).split(": ")[1].equals("Drums"))
				return true;
		}
		JOptionPane.showMessageDialog(null, "Please add a drum track");
		return false;
	}

	private JTextField newSimpleTrackName(){
		SimpleTrackName.setBounds(10, 70, 100, 30);
		return SimpleTrackName;
	}

	private JTextField newPercussionTrackName(){
		PercTrackName.setBounds(10, 125, 100, 30);
		return PercTrackName;
	}

	private JTextField setSongName(){
		SongName.setBounds(275, 10, 100, 30);
		return SongName;
	}

	private JSpinner newSimpleTrackVolume() {
		SimpleTrackVolume.setBounds(120, 70, 50, 30);
		JFormattedTextField tf = ((JSpinner.DefaultEditor)SimpleTrackVolume.getEditor()).getTextField();
		tf.setEditable(false);
		return SimpleTrackVolume;
	}

	private JSpinner newPercussionTrackVolume() {
		PercTrackVolume.setBounds(120, 125, 50, 30);
		JFormattedTextField tf = ((JSpinner.DefaultEditor)PercTrackVolume.getEditor()).getTextField();
		tf.setEditable(false);
		return PercTrackVolume;
	}

	private JSpinner newSongTempo() {
		Tempo.setBounds(90, 10, 50, 30);
		JFormattedTextField tf = ((JSpinner.DefaultEditor)Tempo.getEditor()).getTextField();
		tf.setEditable(false);

		return Tempo;
	}

	private JSlider newTrackBalance(){	
		Balance.setBounds(200, 70, 100, 35);
		Balance.setMinimum(-8);
		Balance.setMaximum(8);
		Balance.setValue(0);
		Balance.setPaintTicks(true);
		Balance.setMajorTickSpacing(4);
		Balance.setMinorTickSpacing(1);
		Balance.setPaintLabels(true);

		Font font = new Font("Serif", Font.PLAIN, 12);
		Balance.setFont(font);
		Balance.setToolTipText("Balance");
		return Balance;
	}

	private JComboBox<String> newTrackTuning() {
		String[] items = {"Standart", "Bass"};
		Tuning = new JComboBox<String>(items);
		Tuning.setBounds(320, 70, 100, 30);
		return Tuning;
	}

	private JComboBox<String> newTrackInstruments() throws IOException
	{
		instruments = new JComboBox<String>();
		instruments.setBounds(420, 70, 150, 30);

		DefaultComboBoxModel<String> ñomboModel = new DefaultComboBoxModel<String>();
		instruments.setModel(ñomboModel);

		for(int i = 0; i < Instruments.size(); i++)
			instruments.addItem(Instruments.get(i));		   

		return instruments;
	}

	private JList<String> newTrackList(){
		TrackList = new JList<String>(TracklistModel);
		TrackList.setBounds(10, 170, 350, 200);
		Border listPanelBorder = BorderFactory.createTitledBorder("Added tracks");
		TrackList.setBorder(listPanelBorder);
		TrackList.setLayoutOrientation(JList.VERTICAL);
		return TrackList;
	}

	private void loadInstruments(String fileName) 
	{
		Instruments.clear();

		try {
			FileReader	read = new FileReader(fileName);
			BufferedReader stream = new BufferedReader(read);
			String line;

			while((line = stream.readLine()) != null) 
			{
				Instruments.add(line);
			}
			stream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JLabel Tempo(){
		LabelTempo.setBounds(10, 20, 80, 15);
		LabelTempo.setText("Enter tempo:");
		return LabelTempo;
	}
	
	private JLabel SongName(){
		LabelSongName.setBounds(170, 20, 110, 15);
		LabelSongName.setText("Enter song name:");
		return LabelSongName;
	}

	private JLabel SimpleTrack(){
		LabelSimpleTrack.setBounds(10, 53, 150, 15);
		LabelSimpleTrack.setText("Set and add simple track:");
		return LabelSimpleTrack;
	}
	private JLabel PercussionTrack(){
		LabelPercTrack.setBounds(10, 110, 170, 15);
		LabelPercTrack.setText("Set and add percussion track:");
		return LabelPercTrack;
	}

	/**
	 * Get song tempo
	 * @return song tempo from Spinner
	 */
	public int getTempo(){
		return (Integer) Tempo.getValue();
	}

	/**
	 * Get track name
	 * @return track name from text field
	 */
	public String getSimpleTrackName(){
		return SimpleTrackName.getText();
	}
	
	/**
	 * Get song name
	 * @return song name from text field 
	 */
	public String getSongName(){
		return SongName.getText();
	}

	private void init()
	{
		loadInstruments("data//Instruments.twd");

		this.setResizable(false);

		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event) {
				if (event.getID() == WindowEvent.WINDOW_CLOSING) 
				{
					Main.mainframe.setVisible(true);
					TracklistModel.clear();
					TWGenerate.readyTracks.clear();
					PercTrackName.setEnabled(true);
					addNewPercussionTrack.setEnabled(true);
				}
			}
		});
	}
}
