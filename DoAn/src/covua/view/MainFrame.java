package covua.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;




public class MainFrame extends JFrame{
	private CardLayout cardLayout= new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	private Stack<String> backStack = new Stack<>();
	private String currentScreen = "home";
	private ChessGameGUI humanHuman;
	private ChessGameGUI humanAI;
	private ChessGameGUI online;
//	private ChessClient client;
	public MainFrame() {
		setTitle("Game Cờ vua");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     // Các màn hình
         humanHuman = new ChessGameGUI(this, false);
         humanAI = new ChessGameGUI(this, true);
//         online = new ChessGameGUI(this, false;
        
        cardPanel.add(new Home(this),"home");
        cardPanel.add(humanHuman, "human-human");
        cardPanel.add(humanAI, "human-ai");
//        cardPanel.add(online, "online");
        add(cardPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

	}
		//9.1.0. User chọn chế độ chơi để bắt đầu trò chơi.

	public void goTo(String screenName) {
		backStack.push(currentScreen);
	    currentScreen = screenName;
	    cardLayout.show(cardPanel, screenName);

	    // nếu màn hình là ChessGame thì set menu
	    if (screenName.equals("human-human")) {
	        setJMenuBar(humanHuman.createMenuBar());
	    } else if (screenName.equals("human-ai")) {
	        setJMenuBar(humanAI.createMenuBar());
	    } else {
	        setJMenuBar(null); 
	    }
	    pack();
        setLocationRelativeTo(null);

	    revalidate();
	    repaint();
    }
	public void goBack(String screenName) {
        if (!backStack.isEmpty()) {

            while (!currentScreen.equals(screenName)) {
                currentScreen = backStack.pop();
            }

            cardLayout.show(cardPanel, currentScreen);

            if (screenName.equals("home")) {
                setJMenuBar(null);
            }

            // pack lại để thu nhỏ trở về Home
            pack();
            setLocationRelativeTo(null);
        } 
    }
//	public void startOnlineMode() {
//		ChessClient client = new ChessClient(online);
//	    client.connect();                      // connect tại đây
//	    online.setClient(client);         // truyền vào GUI
//	    goTo("online");
//	}
	
	public static void main(String[] args) {
		MainFrame m= new MainFrame();
	}
}
