package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import music.TWScaleManager;


public class FrameScaleManager extends JFrame
{
	private static final long serialVersionUID = -1818704328534259047L;


	private JButton newScale = new JButton();
	private JButton deleteScale = new JButton();
	private JButton showScale = new JButton();
	private JTextField NotesGer = new JTextField();
	private JTextField NotesIt = new JTextField();
	private JComboBox<String> scales;

	public FrameScaleManager() throws IOException
	{		
		init();
		this.setSize(600, 400); 
		this.getContentPane().setLayout(null);
		this.add(NewScale(), null);
		this.setTitle("Scale Manager");
		this.add(setScale());
		this.add(ScaleNotesGer());
		this.add(ScaleNotesIt());
		this.add(showSc());
		this.add(delScale());

	}


	private JButton NewScale() 
	{
		newScale.setBounds(300, 20, 130, 30);
		newScale.setText("New Scale");

		newScale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Main.createscale.setVisible(true);
				Main.scalemanager.setEnabled(false);
			}
		});

		return newScale;
	}

	private JButton delScale() 
	{
		deleteScale.setBounds(300, 60, 130, 30);
		deleteScale.setText("Delete Scale");

		deleteScale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				int temp = JOptionPane.showConfirmDialog(null, "Are you sure?" , "Confirm" , JOptionPane.YES_NO_OPTION);
				if (temp == 0)
				{
					try {
						TWScaleManager.deleteScaleFromList("data//Scales.twd", scales.getSelectedItem().toString());
						refreshScales();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return deleteScale;
	}

	private JButton showSc() 
	{
		showScale.setBounds(10, 60, 150, 30);
		showScale.setText("Show Scale");

		final String[] NoteGer = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#",
				"A", "A#", "B"};
		final String[] NoteIt = {"Do", "Do#", "Re", "Re#", "Mi", "Fa", "Fa#", "Sol", "Sol#",
				"La", "La#", "Si"};

		showScale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Iterator<String> itr1 = TWScaleManager.StringScales.iterator(); 
				while(itr1.hasNext()) 
				{
					String curScale = itr1.next();

					if( curScale.split(" ")[0].equals(scales.getSelectedItem()))
					{
						NotesGer.setText(curScale.split(" [0-9]* ")[1]);

						String[] notes = curScale.split(" [0-9]* ")[1].split(" ");
						String ItNotes = "";
						for(int i = 0; i < NoteGer.length; i++)
						{
							for(int j = 0; j < notes.length; j++)
							{
								if(NoteGer[i].equals(notes[j]))
									ItNotes = ItNotes + NoteIt[i] + " ";
							}
						}
						NotesIt.setText(ItNotes);
					}
				}
			}
		});
		return showScale;
	}

	private JComboBox<String> setScale() throws IOException
	{
		scales = new JComboBox<String>();
		scales.setBounds(10, 20, 150, 30);

		DefaultComboBoxModel<String> ñomboModel = new DefaultComboBoxModel<String>();
		scales.setModel(ñomboModel);

		refreshScales();
		return scales;
	}

	private void refreshScales()
	{
		scales.removeAllItems();
		for(int i = 0; i < TWScaleManager.StringScales.size(); i++)
		{
			String[] ScaleName = TWScaleManager.StringScales.get(i).split(" ");
			scales.addItem(ScaleName[0]);
		}
	}


	private JTextField ScaleNotesGer()
	{
		NotesGer.setBounds(170, 20, 120, 30);
		NotesGer.setEditable(false);
		return NotesGer;
	}

	private JTextField ScaleNotesIt()
	{
		NotesIt.setBounds(170, 60, 120, 30);
		NotesIt.setEditable(false);
		return NotesIt;
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

				}
			}
		});
	}
}
