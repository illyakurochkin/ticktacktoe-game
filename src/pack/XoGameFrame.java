package pack;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class XoGameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private XoGame game;
	private JPanel mainPanel = new JPanel();
	private JPanel infoPanel = new JPanel();

	private MouseAdapter mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent event) {
			if (game.getGameCondition() == GameCondition.CONTINUE) {
				if (event.getX() - (mainPanel.getWidth() - 3 * Constants.BOX_SIZE) / 2 > 0
						&& event.getY() - (mainPanel.getHeight() - 3 * Constants.BOX_SIZE) / 2 > 0) {
					Point point = new Point(
							(event.getX() - (mainPanel.getWidth() - 3 * Constants.BOX_SIZE) / 2) / Constants.BOX_SIZE,
							(event.getY() - (mainPanel.getHeight() - 3 * Constants.BOX_SIZE) / 2) / Constants.BOX_SIZE);
					if (point.x >= 0 && point.x < 3 && point.y >= 0 && point.y < 3
							&& game.getDesk()[point.y][point.x] == null) {
						game.next(point);
						update();
						updateInfo();
					}
				}
			}
		}
	};

	public XoGameFrame(XoGame game) {
		this.game = game;
		Dimension frameSize = new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);

		Dimension infoSize = new Dimension(mainPanel.getWidth(), Constants.INFO_PANEL_HEIGHT);
		infoPanel.setSize(infoSize);
		infoPanel.setPreferredSize(infoSize);
		infoPanel.setFont(Constants.INFO_FONT);
		add(infoPanel, BorderLayout.NORTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel.addMouseListener(mouseListener);

		this.setResizable(false);
		this.setVisible(true);

		update();
		updateInfo();
	}

	public void update() {
		BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int xLocation = (this.mainPanel.getWidth() - 3 * Constants.BOX_SIZE) / 2;
		int yLocation = (this.mainPanel.getHeight() - 3 * Constants.BOX_SIZE) / 2;

		// fill background
		graphics.setColor(Constants.BACKGROUND_COLOR);
		graphics.fillRect(0, 0, mainPanel.getWidth(), mainPanel.getHeight());

		drawGrid(graphics, new Point(xLocation, yLocation));

		Values[][] desk = game.getDesk();

		for (int line = 0; line < 3; line++) {
			for (int box = 0; box < 3; box++) {
				if (desk[line][box] != null) {
					Point location = new Point(xLocation + box * Constants.BOX_SIZE,
							yLocation + line * Constants.BOX_SIZE);
					if (desk[line][box] == Values.O) {
						drawO(graphics, location);
					} else {
						drawX(graphics, location);
					}
				}
			}
		}

		mainPanel.getGraphics().drawImage(bufferedImage, 0, 0, null);
	}

	private void drawGrid(Graphics2D graphics, Point location) {
		// fill shadows
		graphics.setColor(Constants.SHADOW_COLOR);
		for (int i = 1; i <= 2; i++) {
			graphics.fillRoundRect(location.x + Constants.BOX_SIZE * i - Constants.BORDER_SIZE / 2 + Constants.X_SHADOW,
					location.y + Constants.Y_SHADOW, Constants.BORDER_SIZE, Constants.BOX_SIZE * 3,
					Constants.BORDER_SIZE, Constants.BORDER_SIZE);
		}
		for (int i = 1; i <= 2; i++) {
			graphics.fillRoundRect(location.x + Constants.X_SHADOW,
					location.y + Constants.BOX_SIZE * i - Constants.BORDER_SIZE / 2 + Constants.Y_SHADOW,
					Constants.BOX_SIZE * 3, Constants.BORDER_SIZE, Constants.BORDER_SIZE, Constants.BORDER_SIZE);
		}
		// draw grid
		graphics.setColor(Constants.BORDER_COLOR);
		for (int i = 1; i <= 2; i++) {
			graphics.fillRoundRect(location.x + Constants.BOX_SIZE * i - Constants.BORDER_SIZE / 2, location.y,
					Constants.BORDER_SIZE, Constants.BOX_SIZE * 3, Constants.BORDER_SIZE, Constants.BORDER_SIZE);
		}
		for (int i = 1; i <= 2; i++) {
			graphics.fillRoundRect(location.x, location.y + Constants.BOX_SIZE * i - Constants.BORDER_SIZE / 2,
					Constants.BOX_SIZE * 3, Constants.BORDER_SIZE, Constants.BORDER_SIZE, Constants.BORDER_SIZE);
		}
	}

	private void drawO(Graphics2D graphics, Point location) {
		GameCondition condition = game.getGameCondition();
		Color oColor = condition == GameCondition.O_WIN ? Constants.WIN_COLOR
				: condition == GameCondition.X_WIN || condition == GameCondition.NOBODY_WIN ? Constants.LOSE_COLOR
						: Constants.O_COLOR;
		Stroke defaultStroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(Constants.BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		// draw shadow
		graphics.setColor(Constants.SHADOW_COLOR);
		graphics.drawOval(location.x + Constants.BOX_MARGIN + Constants.X_SHADOW,
				location.y + Constants.BOX_MARGIN + Constants.Y_SHADOW, Constants.BOX_SIZE - 2 * Constants.BOX_MARGIN,
				Constants.BOX_SIZE - 2 * Constants.BOX_MARGIN);

		// draw o
		graphics.setColor(oColor);
		graphics.drawOval(location.x + Constants.BOX_MARGIN, location.y + Constants.BOX_MARGIN,
				Constants.BOX_SIZE - 2 * Constants.BOX_MARGIN, Constants.BOX_SIZE - 2 * Constants.BOX_MARGIN);
		graphics.setStroke(defaultStroke);
	}

	public void updateInfo() {
		GameCondition condition = game.getGameCondition();
		String info = "";
		switch (condition) {
		case X_WIN:
			info = Constants.X_WIN_INFO;
			break;
		case O_WIN:
			info = Constants.O_WIN_INFO;
			break;
		case NOBODY_WIN:
			info = Constants.NOBODY_WIN_INFO;
			break;
		default:
			info = game.getCurrentPlayer() == Values.X ? Constants.X_TURN_TO_GO : Constants.O_TURN_TO_GO;
		}

		Graphics2D graphics = (Graphics2D) infoPanel.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// fill background
		graphics.setColor(Constants.BACKGROUND_COLOR);
		graphics.fillRect(0, 0, infoPanel.getWidth(), infoPanel.getHeight());

		int xLocation = (infoPanel.getWidth() - graphics.getFontMetrics(graphics.getFont()).stringWidth(info)) / 2;
		int yLocation = infoPanel.getHeight() * 3 / 4;

		// draw shadow
		graphics.setColor(Constants.SHADOW_COLOR);
		graphics.drawString(info, xLocation + Constants.X_SHADOW, yLocation + Constants.Y_SHADOW);

		// draw info
		graphics.setColor(condition == GameCondition.X_WIN || condition == GameCondition.O_WIN ? Constants.WIN_COLOR
				: Constants.BORDER_COLOR);
		graphics.drawString(info, xLocation, yLocation);

	}

	private void drawX(Graphics2D graphics, Point location) {
		GameCondition condition = game.getGameCondition();
		Color xColor = condition == GameCondition.X_WIN ? Constants.WIN_COLOR
				: condition == GameCondition.O_WIN || condition == GameCondition.NOBODY_WIN ? Constants.LOSE_COLOR
						: Constants.X_COLOR;
		Stroke defaultStroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(Constants.BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		// draw shadow
		graphics.setColor(Constants.SHADOW_COLOR);
		graphics.drawLine(location.x + Constants.BOX_MARGIN + Constants.X_SHADOW,
				location.y + Constants.BOX_MARGIN + Constants.Y_SHADOW,
				location.x + Constants.BOX_SIZE - Constants.BOX_MARGIN + Constants.X_SHADOW,
				location.y + Constants.BOX_SIZE - Constants.BOX_MARGIN + Constants.Y_SHADOW);
		graphics.drawLine(location.x + Constants.BOX_MARGIN + Constants.X_SHADOW,
				location.y + Constants.BOX_SIZE - Constants.BOX_MARGIN + Constants.Y_SHADOW,
				location.x + Constants.BOX_SIZE - Constants.BOX_MARGIN + Constants.X_SHADOW,
				location.y + Constants.BOX_MARGIN + Constants.Y_SHADOW);

		// draw x
		graphics.setColor(xColor);
		graphics.drawLine(location.x + Constants.BOX_MARGIN, location.y + Constants.BOX_MARGIN,
				location.x + Constants.BOX_SIZE - Constants.BOX_MARGIN,
				location.y + Constants.BOX_SIZE - Constants.BOX_MARGIN);
		graphics.drawLine(location.x + Constants.BOX_MARGIN, location.y + Constants.BOX_SIZE - Constants.BOX_MARGIN,
				location.x + Constants.BOX_SIZE - Constants.BOX_MARGIN, location.y + Constants.BOX_MARGIN);
		graphics.setStroke(defaultStroke);
	}

	public static final class Constants {

		public static final Color BACKGROUND_COLOR = new Color(44, 62, 80);
		public static final Color SHADOW_COLOR = BACKGROUND_COLOR.darker();
		public static final Color BORDER_COLOR = new Color(149, 165, 166);
		public static final Color X_COLOR = new Color(52, 152, 219);// new Color(22, 160, 133);
		public static final Color O_COLOR = new Color(247, 202, 24);
		public static final Color WIN_COLOR = new Color(0, 230, 64);
		public static final Color LOSE_COLOR = BORDER_COLOR;

		public static final int BOX_MARGIN = 18;
		public static final int BOX_SIZE = 105;
		public static final int BORDER_SIZE = 6;

		public static final int X_SHADOW = 3;
		public static final int Y_SHADOW = 2;

		public static final String X_WIN_INFO = "X   WIN !";
		public static final String O_WIN_INFO = "O   WIN !";
		public static final String NOBODY_WIN_INFO = "nobody win";
		public static final String X_TURN_TO_GO = "X's turn to go";
		public static final String O_TURN_TO_GO = "O's turn to go";

		public static final Font INFO_FONT = new Font(/* "Corbel" */"Calibri Light", Font.PLAIN, 62);
		// Tw Cen MT Condensed Extra Bold
		public static final int INFO_PANEL_HEIGHT = 150;
		public static final Color INFO_COLOR = BORDER_COLOR;

		public static final int FRAME_WIDTH = 700 + 16;
		public static final int FRAME_HEIGHT = 400 + 39 + INFO_PANEL_HEIGHT;

	}
}
