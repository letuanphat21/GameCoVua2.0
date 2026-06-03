package covua.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Home extends JPanel {
	private BufferedImage backgroundImage;

	public Home(MainFrame frame) {
		setPreferredSize(new Dimension(900, 620));
		setLayout(new GridBagLayout());
		loadBackgroundImage();

		JPanel contentPanel = new JPanel(new GridBagLayout());
		contentPanel.setOpaque(false);
		contentPanel.setBorder(new EmptyBorder(36, 52, 36, 52));

		JLabel title = new JLabel("Game Co Vua 2.0", SwingConstants.CENTER);
		title.setForeground(new Color(255, 244, 218));
		title.setFont(new Font(Font.SERIF, Font.BOLD, 46));

		JLabel subtitle = new JLabel("Choose your match mode", SwingConstants.CENTER);
		subtitle.setForeground(new Color(226, 214, 190));
		subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));

		JButton humanButton = createModeButton("Human - Human");
		JButton aiButton = createModeButton("Human - AI");

		humanButton.addActionListener(e -> frame.goTo("human-human"));
		aiButton.addActionListener(e -> frame.goTo("human-ai"));

		GridBagConstraints contentConstraints = new GridBagConstraints();
		contentConstraints.gridx = 0;
		contentConstraints.fill = GridBagConstraints.HORIZONTAL;
		contentConstraints.insets = new Insets(0, 0, 10, 0);
		contentPanel.add(title, contentConstraints);

		contentConstraints.gridy = 1;
		contentConstraints.insets = new Insets(0, 0, 34, 0);
		contentPanel.add(subtitle, contentConstraints);

		contentConstraints.gridy = 2;
		contentConstraints.insets = new Insets(0, 0, 14, 0);
		contentPanel.add(humanButton, contentConstraints);

		contentConstraints.gridy = 3;
		contentConstraints.insets = new Insets(0, 0, 0, 0);
		contentPanel.add(aiButton, contentConstraints);

		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 0;
		panelConstraints.anchor = GridBagConstraints.CENTER;
		add(contentPanel, panelConstraints);
	}

	private void loadBackgroundImage() {
		try {
			File imageFile = new File("background.png");
			if (imageFile.exists()) {
				backgroundImage = ImageIO.read(imageFile);
			} else {
				backgroundImage = ImageIO.read(getClass().getResource("/background.png"));
			}
		} catch (IOException | IllegalArgumentException e) {
			backgroundImage = null;
		}
	}

	private JButton createModeButton(String text) {
		StyledButton button = new StyledButton(text);
		button.setPreferredSize(new Dimension(280, 54));
		button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setForeground(new Color(255, 246, 225));
		return button;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		if (backgroundImage != null) {
			drawCoverImage(g2);
		} else {
			g2.setColor(new Color(24, 22, 20));
			g2.fillRect(0, 0, getWidth(), getHeight());
		}

		g2.setColor(new Color(0, 0, 0, 92));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(new Color(8, 10, 13, 128));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
	}

	private void drawCoverImage(Graphics2D g2) {
		double panelRatio = (double) getWidth() / getHeight();
		double imageRatio = (double) backgroundImage.getWidth() / backgroundImage.getHeight();

		int drawWidth;
		int drawHeight;
		if (imageRatio > panelRatio) {
			drawHeight = getHeight();
			drawWidth = (int) Math.round(drawHeight * imageRatio);
		} else {
			drawWidth = getWidth();
			drawHeight = (int) Math.round(drawWidth / imageRatio);
		}

		int x = (getWidth() - drawWidth) / 2;
		int y = (getHeight() - drawHeight) / 2;
		g2.drawImage(backgroundImage.getScaledInstance(drawWidth, drawHeight, Image.SCALE_SMOOTH), x, y, null);
	}

	private static class StyledButton extends JButton {
		private boolean hovered;

		StyledButton(String text) {
			super(text);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					hovered = true;
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					hovered = false;
					repaint();
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			int arc = 16;
			int width = getWidth() - 1;
			int height = getHeight() - 1;
			RoundRectangle2D shape = new RoundRectangle2D.Double(0, 0, width, height, arc, arc);

			g2.setColor(hovered ? new Color(177, 124, 39, 232) : new Color(92, 64, 31, 218));
			g2.fill(shape);
			g2.setStroke(new BasicStroke(1.5f));
			g2.setColor(hovered ? new Color(255, 220, 142) : new Color(204, 166, 91));
			g2.draw(shape);

			FontMetrics metrics = g2.getFontMetrics(getFont());
			int textX = (getWidth() - metrics.stringWidth(getText())) / 2;
			int textY = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
			g2.setFont(getFont());
			g2.setColor(getForeground());
			g2.drawString(getText(), textX, textY);
			g2.dispose();
		}
	}
}
