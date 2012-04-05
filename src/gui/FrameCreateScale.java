package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.JCheckBox;

import music.TWScale;
import music.TWScaleManager;

import datastruct.TWSimpleNote;

public class FrameCreateScale extends JFrame 
{
	private static final long serialVersionUID = 7901835997087229769L;

	private JButton OK = new JButton();
	static JTextField text = new JTextField();

	public FrameCreateScale()
	{
		this.setSize(300, 230);
		this.getContentPane().setLayout(null);

		this.add(c, null);  this.add(C, null);
		this.add(c_, null); this.add(C_, null);
		this.add(d, null);  this.add(D, null);
		this.add(d_, null); this.add(D_, null);    
		this.add(e, null);  this.add(E, null);
		this.add(f, null);  this.add(F, null);
		this.add(f_, null); this.add(F_, null);	      
		this.add(g, null);  this.add(G, null);	      
		this.add(g_, null); this.add(G_, null);
		this.add(a, null);  this.add(A, null);	      
		this.add(a_, null); this.add(A_, null);	      
		this.add(b, null);  this.add(B, null);

		this.add(ScaleName(), null);	      
		this.add(ok(), null); 
		this.setTitle("Create Scale");

		init();

	}

	JLabel c = NoteLabel(16, 10, "C");
	JLabel c_ = NoteLabel(43, 10, "C#");
	JLabel d = NoteLabel(76, 10, "D");
	JLabel d_ = NoteLabel(16, 50, "D#");
	JLabel e = NoteLabel(43, 50, "E");
	JLabel f = NoteLabel(76, 50, "F");
	JLabel f_ = NoteLabel(16, 90, "F#");
	JLabel g = NoteLabel(43, 90, "G");
	JLabel g_ = NoteLabel(76, 90, "G#");
	JLabel a = NoteLabel(16, 130, "A");
	JLabel a_ = NoteLabel(43, 130, "A#");
	JLabel b = NoteLabel(76, 130, "B");

	static JCheckBox C = NoteCheckBox(10, 20);
	static JCheckBox C_ = NoteCheckBox(40, 20);
	static JCheckBox D = NoteCheckBox(70, 20);
	static JCheckBox D_ = NoteCheckBox(10, 60);
	static JCheckBox E = NoteCheckBox(40, 60);
	static JCheckBox F = NoteCheckBox(70, 60);
	static JCheckBox F_ = NoteCheckBox(10, 100);
	static JCheckBox G = NoteCheckBox(40, 100);
	static JCheckBox G_ = NoteCheckBox(70, 100);
	static JCheckBox A = NoteCheckBox(10, 140);
	static JCheckBox A_ = NoteCheckBox(40, 140);
	static JCheckBox B = NoteCheckBox(70, 140);



	private JButton ok() {
		OK.setBounds(140, 60, 120, 100);
		OK.setText("Create Scale");
		OK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				TWScale newScale = new TWScale("");
				newScale = createNewScale();

				if(newScale.getScaleName().equals("Scale already exsists"))
				{
					JOptionPane.showMessageDialog(null, "Scale already exsists");
					return;
				}

				if(newScale.ScaleSize() == 0 || newScale.getScaleName().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Scale is empty or scale doesn't have name");
					return;
				}
				else
				{
					TWScaleManager.writeScaleToFile("data//Scales.twd", newScale);
					JOptionPane.showMessageDialog(null, "Scale is added");
				}
			}
		});

		return OK;
	}

	private JLabel NoteLabel(int x, int y, String text) {
		JLabel newLabel = new JLabel();
		newLabel.setBounds(x, y, 20, 10);
		newLabel.setText(text);
		return newLabel;
	}

	private static JCheckBox NoteCheckBox(int x, int y) {		
		JCheckBox newCheck = new javax.swing.JCheckBox();
		newCheck.setBounds(x, y, 30, 30);
		return newCheck;
	}

	public JTextField ScaleName()
	{
		text.setBounds(140, 20, 120, 30);
		return text;
	}

	public static TWScale createNewScale()
	{
		TWScaleManager.loadScales("data//Scales.twd");
		String name = "";

		name = FrameCreateScale.text.getText();

		TWScale newScale = new TWScale(name);

		if(FrameCreateScale.C.isSelected())
			newScale.addSimpleNote(TWSimpleNote.C);
		if(FrameCreateScale.C_.isSelected())
			newScale.addSimpleNote(TWSimpleNote.C_);
		if(FrameCreateScale.D.isSelected())
			newScale.addSimpleNote(TWSimpleNote.D);
		if(FrameCreateScale.D_.isSelected())
			newScale.addSimpleNote(TWSimpleNote.D_);
		if(FrameCreateScale.E.isSelected())
			newScale.addSimpleNote(TWSimpleNote.E);
		if(FrameCreateScale.F.isSelected())
			newScale.addSimpleNote(TWSimpleNote.F);
		if(FrameCreateScale.F_.isSelected())
			newScale.addSimpleNote(TWSimpleNote.F_);
		if(FrameCreateScale.G.isSelected())
			newScale.addSimpleNote(TWSimpleNote.G);
		if(FrameCreateScale.G_.isSelected())
			newScale.addSimpleNote(TWSimpleNote.G_);
		if(FrameCreateScale.A.isSelected())
			newScale.addSimpleNote(TWSimpleNote.A);
		if(FrameCreateScale.A_.isSelected())
			newScale.addSimpleNote(TWSimpleNote.A_);
		if(FrameCreateScale.B.isSelected())
			newScale.addSimpleNote(TWSimpleNote.B);

		if(TWScaleManager.HasScaleInList(newScale))
		{				
			TWScale fakeScale = new TWScale("Scale already exsists");
			return fakeScale;
		}
		else
			return newScale;
	}

	private void init()
	{
		this.setResizable(false);

		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event) {
				if (event.getID() == WindowEvent.WINDOW_CLOSING) 
				{
					Main.scalemanager.setEnabled(true);
					Main.scalemanager.dispose();
					
					try {
						
						FrameScaleManager scaleManager = new FrameScaleManager();
						scaleManager.setVisible(true);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					text.setText("");
				}
			}
		});
	}

}
