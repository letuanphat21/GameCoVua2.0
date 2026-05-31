package covua.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

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
		setFocusable(false);
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(70, 55, 45));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		FontMetrics metrics = g.getFontMetrics();

		if (col == 0) {
			String rank = String.valueOf(8 - row);
			g.drawString(rank, 5, metrics.getAscent() + 3);
		}

		if (row == 7) {
			String file = String.valueOf((char) ('a' + col));
			int x = getWidth() - metrics.stringWidth(file) - 5;
			int y = getHeight() - 5;
			g.drawString(file, x, y);
		}
	}

	public void setPieceSymbol(String symbol,Color color) {
		this.setText(symbol);
		this.setForeground(color);
	}
	public void clearPieceSymbol() {
		this.setText("");
	}
}
