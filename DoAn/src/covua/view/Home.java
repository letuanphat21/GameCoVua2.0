package covua.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Home extends JPanel{
	public Home(MainFrame frame) {
		setPreferredSize(new Dimension(200, 200));
	    setLayout(new FlowLayout());

	    JLabel label = new JLabel("Home Screen");

	    JButton btnNext2 = new JButton("Human - Human");
	    JButton btnNext3 = new JButton("Human - AI");
//	    JButton btnOnline = new JButton("Online Mode");

	    btnNext2.addActionListener(e -> frame.goTo("human-human"));
	    btnNext3.addActionListener(e -> frame.goTo("human-ai"));
//	    btnOnline.addActionListener(e -> {
//            frame.startOnlineMode();
//        });
	    add(label);
	    add(btnNext2);
	    add(btnNext3);
//	    add(btnOnline);
	}
}
