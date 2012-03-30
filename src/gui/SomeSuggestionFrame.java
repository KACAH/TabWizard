package gui;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class SomeSuggestionFrame extends JFrame{

	private static final long serialVersionUID = -2305852043390443274L;

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

	    JComponent panel1 = makeTextPanel("Song tracks");
	    tabbedPane.addTab("Tab 1", icon, panel1);

	    JComponent panel2 = makeTextPanel("Panel #2");
	    tabbedPane.addTab("Tab 2", icon, panel2);

	    JComponent panel3 = makeTextPanel("Panel #3");
	    tabbedPane.addTab("Tab 3", icon, panel3);

	    JComponent panel4 = makeTextPanel("Panel #4");
	    tabbedPane.addTab("Tab 4", icon, panel4);
	    
		return tabbedPane;
	}
	
	protected JComponent makeTextPanel(String text) {
	    JPanel panel = new JPanel(false);
	    JLabel filler = new JLabel(text);
	    filler.setHorizontalAlignment(JLabel.CENTER);
	    panel.setLayout(new GridLayout(1, 1));
	    panel.add(filler);
	    return panel;
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
					Main.mainframe.setVisible(true);
				}
			}
		});
	}
}
