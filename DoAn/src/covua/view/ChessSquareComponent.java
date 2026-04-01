package covua.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ChessSquareComponent extends JButton {
	private int row;
	private int col;

	public ChessSquareComponent(int row, int col) {
		this.row = row;
		this.col = col;
		initButton();
	}

	private void initButton() {
		// thiết lập size button
		setPreferredSize(new Dimension(90, 90));

		// set background

		if ((row + col) % 2 == 0) {
			setBackground(Color.LIGHT_GRAY);

		} else {
			setBackground(new Color(205, 133, 63));
		}
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
		setFont(new Font("Serif",Font.BOLD,36));
	}
	public void setPieceSymbol(String symbol,Color color) {
		this.setText(symbol);
		this.setForeground(color);
	}
	public void clearPieceSymbol() {
		this.setText("");
	}
}
