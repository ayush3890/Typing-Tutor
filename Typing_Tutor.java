package August_5_game;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Typing_Tutor extends JFrame implements ActionListener, KeyListener {

	private JLabel lbltimer;
	private JLabel lblscore;
	private JLabel lblword;
	private JTextField tfield;
	private JButton startb;
	private JButton stopb;

	private Timer clockTimer = null;
	private Timer wordTimer = null;

	private boolean isRunning = false;
	private int score;
	private int timeremaining = 0;
	private String[] word;

	public Typing_Tutor(String[] args) {
		word = args;
		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);

		Font font = new Font("Comic Sans MS", 2, 100);

		// ****************************************************
		lbltimer = new JLabel("TIMER");
		lbltimer.setFont(font);
		super.add(lbltimer);

		// ****************************************************
		lblscore = new JLabel("SCORE");
		lblscore.setFont(font);
		super.add(lblscore);

		// ****************************************************
		lblword = new JLabel("");
		lblword.setFont(font);
		super.add(lblword);

		// ****************************************************
		tfield = new JTextField("");
		tfield.setFont(font);
		tfield.addKeyListener(this);
		super.add(tfield);

		// ****************************************************
		startb = new JButton("START");
		startb.setFont(font);
		startb.addActionListener(this);
		super.add(startb);

		// ****************************************************
		stopb = new JButton("STOP");
		stopb.setFont(font);
		stopb.addActionListener(this);
		super.add(stopb);
		// ****************************************************

		super.setTitle("TYPING TUTOR");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setVisible(true);

		setupAtStart();
	}

	private void setupAtStart() {
		clockTimer = new Timer(1000, this);
		clockTimer.setInitialDelay(0);

		wordTimer = new Timer(3000, this);
		wordTimer.setInitialDelay(0);

		timeremaining = 50;
		score = 0;
		isRunning = false;

		lbltimer.setText("TIMER :");
		lblscore.setText("SCORE :");
		lblword.setText("");
		tfield.setText("");
		startb.setText("START");
		stopb.setText("STOP");

		tfield.setEnabled(false);
		stopb.setEnabled(false);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		if (e.getSource() == startb) {
			System.out.println("Start Button");
			handelstart();
		} else if (e.getSource() == stopb) {
			System.out.println("Stop Button");
			handlestop();
		} else if (e.getSource() == clockTimer) {
			handleclocktimer();
		} else if (e.getSource() == wordTimer) {
			handlewordtimer();
		}
	}

	private void handelstart() {
		if (isRunning) {
			clockTimer.stop();
			wordTimer.stop();

			isRunning = false;

			startb.setText("START");

			tfield.setEnabled(false);
			stopb.setEnabled(false);

		} else {
			clockTimer.start();
			wordTimer.start();

			isRunning = true;

			startb.setText("PAUSE");

			tfield.setEnabled(true);
			stopb.setEnabled(true);

			tfield.setFocusCycleRoot(true);
			super.nextFocus();
		}
	}

	private void handlestop() {
		clockTimer.stop();
		wordTimer.stop();

		int choice = JOptionPane.showConfirmDialog(this, "REPLAY ??");
		if (choice == JOptionPane.YES_OPTION) {
			setupAtStart();
		} else if (choice == JOptionPane.NO_OPTION) {
			this.dispose();
		} else if (choice == JOptionPane.CANCEL_OPTION) {
			clockTimer.start();
			wordTimer.start();
		}

	}

	private void handleclocktimer() {
		if (timeremaining > 0) {
			timeremaining--;

			lbltimer.setText("TIMER:" + timeremaining);

		} else {
			handlestop();
		}

	}

	private void handlewordtimer() {

		String actual = tfield.getText();
		String expected = lblword.getText();

		if (expected.length() > 0 && expected.equals(actual)) {
			score++;
		}
		lblscore.setText("SCORE:" + score);

		int ridx = (int) (Math.random() * word.length - 2);

		lblword.setText(word[ridx + 1]);
		tfield.setText("");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		String actual = tfield.getText();
		String expected = lblword.getText();

		if (expected.length() > 0 && expected.equals(actual)) {
			score++;
			lblscore.setText("SCORE:" + score);

			int ridx = (int) (Math.random() * word.length);

			lblword.setText(word[ridx]);
			tfield.setText("");
			wordTimer.restart();
		}
	}

}
