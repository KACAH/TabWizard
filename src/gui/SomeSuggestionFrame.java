package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;

import music.TWGenerate;
import music.TWScaleManager;
import datastruct.TWDataException;


public class SomeSuggestionFrame extends JFrame{

	private static final long serialVersionUID = -2305852043390443274L;
	
	private JComponent panelTempo;
	private JComponent panelScale;
	private JComponent panelGenerate;
	private JSpinner Tempo;
	private JComboBox<String> scales;

	public SomeSuggestionFrame()
	{
		this.setSize(600, 400);

		init();
	    this.add(createPane());
	}
	
	private JTabbedPane createPane()
	{
	    JTabbedPane tabbedPane = new JTabbedPane();
	    ImageIcon icon = new ImageIcon("icon.png");

	    panelTempo = makeTextPanel("Song tempo");
	    setTempoPanel();
		tabbedPane.addTab("Tempo", icon, panelTempo);

		panelScale = makeTextPanel("Set Scale");
	    setScalePanel();
	    tabbedPane.addTab("Scale", icon, panelScale);

	    JComponent panel3 = makeTextPanel("Panel #3");
	    tabbedPane.addTab("Tab 3", icon, panel3);

	    panelGenerate = makeTextPanel("Generate");
	    setGeneratePanel();
	    tabbedPane.addTab("Generate song", icon, panelGenerate);
	    
		return tabbedPane;
	}
	
	private void setTempoPanel()
	{
		Tempo = new JSpinner(new SpinnerNumberModel(150, 1, 300, 1));
		JFormattedTextField tf = ((JSpinner.DefaultEditor)Tempo.getEditor()).getTextField();
		tf.setEditable(false);
		Tempo.setFont( new Font("Dialog", Font.PLAIN, 35));
		
		panelTempo.add(Tempo);
	}
	
	private void setScalePanel()
	{
		scales  = new JComboBox<String>();
		DefaultComboBoxModel<String> ñomboModel = new DefaultComboBoxModel<String>();
		scales.setModel(ñomboModel);
		scales.setFont( new Font("Dialog", Font.PLAIN, 25));
		
		for(int i = 0; i < TWScaleManager.StringScales.size(); i++)
		{
			String[] ScaleName = TWScaleManager.StringScales.get(i).split(" ");
			scales.addItem(ScaleName[0]);
		}
		panelScale.add(scales);
	}
	
	private void setGeneratePanel()
	{
		RoundButton generate = new RoundButton();
		ImageIcon cup = new ImageIcon("media//Button.png");
		generate.setIcon(cup);
		generate.setToolTipText("Generate new music fragment");
		
		generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					TWGenerate.Generate((Integer)Tempo.getValue(), scales.getSelectedItem().toString());
					
				} catch (TWDataException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		});
		
		panelGenerate.add(generate);
	}
	
	protected JComponent makeTextPanel(String text) {
	    JPanel panel = new JPanel(false);
	    JLabel filler = new JLabel(text);
	    filler.setFont(new Font("Dialog", Font.PLAIN, 35));
	    filler.setHorizontalAlignment(JLabel.CENTER);
	    panel.setLayout(new GridLayout(2, 0));
	    panel.add(filler);
	    return panel;
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
