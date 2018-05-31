package miner.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Miner extends JFrame implements ActionListener {

	private JButton[][] buttons = new JButton[15][15];
	private int[][] counters = new int[15][15];
	private int minesNumber = 10;
	private final int MINE = 10;

	public static final int MIN_WIDTH = 800;
	public static final int MIN_HEIGH = 920;
	private JButton startBtn;
	private JTextField timerField;
	private JTextField infoField;

	public Miner() {
		this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGH));
		this.setTitle("ZDA - MINER - £ukasz Kruk");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timerField = new JTextField("0");
		timerField.setEditable(false);
		timerField.setHorizontalAlignment(JTextField.CENTER);

		startBtn = new JButton(":-)");
		startBtn.addActionListener(e -> {
			for (int x = 0; x < buttons.length; x++) {
				for (int y = 0; y < buttons[x].length; y++) {
					buttons[x][y].setEnabled(true);
					buttons[x][y].setText(" ");
					buttons[x][y].setBackground(null);
					infoField.setText("Powodzenia!");
					startBtn.setText(":-)");
				}
			}
			setUpMines();
		});
		// startBtn.setMinimumSize(new Dimension(30,30));

		JPanel infoPanel = new JPanel();
		GridBagLayout infoPanelLayout = new GridBagLayout();
		infoPanelLayout.columnWidths = new int[] { 200, 50, 200 };
		infoPanelLayout.rowHeights = new int[] { 50 };
		infoPanel.setLayout(infoPanelLayout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weighty = 1;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.insets = new Insets(1, 1, 1, 1);
		gbc.fill = GridBagConstraints.BOTH;
		infoPanel.add(timerField, gbc);
		gbc.gridx = 1;
		infoPanel.add(startBtn, gbc);
		gbc.gridx = 2;
		infoField = new JTextField("Powodzenia!");
		infoField.setEditable(false);
		infoField.setHorizontalAlignment(JTextField.CENTER);
		infoPanel.add(infoField, gbc);

		JPanel mineFieldPanel = new JPanel();
		GridBagLayout mineFieldPanelLayout = new GridBagLayout();
		mineFieldPanel.setLayout(mineFieldPanelLayout);
		int[] fieldSize = new int[15];
		for (int i = 0; i < fieldSize.length; i++) {
			fieldSize[i] = 15;
		}
		mineFieldPanelLayout.columnWidths = fieldSize;
		mineFieldPanelLayout.rowHeights = fieldSize;
		GridBagConstraints fieldGbc = new GridBagConstraints();
		fieldGbc.weighty = 1;
		fieldGbc.weightx = 1;
		fieldGbc.gridwidth = 1;
		fieldGbc.gridheight = 1;
		fieldGbc.insets = new Insets(1, 1, 1, 1);
		fieldGbc.fill = GridBagConstraints.BOTH;

		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j] = new JButton(" ");
				buttons[i][j].setMinimumSize(new Dimension(10, 10));
				buttons[i][j].addActionListener(this);
				fieldGbc.gridx = i;
				fieldGbc.gridy = j;
				mineFieldPanel.add(buttons[i][j], fieldGbc);
				buttons[i][j].addActionListener(e -> {
					for (int x = 0; x < buttons.length; x++) {
						for (int y = 0; y < buttons[x].length; y++) {
							if (e.getSource().equals(buttons[x][y])) {
								if (counters[x][y] == MINE) {
									buttons[x][y].setBackground(Color.WHITE);
									buttons[x][y].setText("X");
									failed();
								} else if (counters[x][y]==0){
									buttons[x][y].setText(counters[x][y] + "");
									buttons[x][y].setEnabled(false);
									ArrayList<Integer> toReveal = new ArrayList<Integer>();
									toReveal.add(x*100+y);
									revealZeros(toReveal);
									checkWin();
									
								} else {
									buttons[x][y].setText(counters[x][y] + "");
									buttons[x][y].setEnabled(false);
									checkWin();
								}
							}
						}
					}

				});
			}
		}

		setUpMines();

		this.getContentPane().add(infoPanel, BorderLayout.NORTH);
		this.getContentPane().add(mineFieldPanel, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);

	}
	
	public void checkWin(){
		boolean won = true;
				for (int x = 0; x < counters.length; x++) {
					for (int y = 0; y < counters[x].length; y++) {
						if(counters[x][y]!=MINE && buttons[x][y].isEnabled()==true){
							won=false;
						}
					}
				}
				if(won==true){
					startBtn.setText("8-D");
					infoField.setText("BRAWO !!!");
				}
	}

	public void failed() {
		for (int x = 0; x < buttons.length; x++) {
			for (int y = 0; y < buttons[x].length; y++) {
				if (buttons[x][y].isEnabled()) {
					if (counters[x][y] != MINE) {
						buttons[x][y].setText(counters[x][y] + "");
						buttons[x][y].setEnabled(false);
					} else {
						buttons[x][y].setText("X");
						buttons[x][y].setBackground(Color.WHITE);
						buttons[x][y].setEnabled(false);
					}

				}
				infoField.setText("Przykro mi!");
				startBtn.setText(":-(");
			}
		}
	}

	public void revealZeros(ArrayList<Integer> toReveal) {
		if (toReveal.size() == 0) {
			return;
		} else {
			int x = toReveal.get(0) / 100;
			int y = toReveal.get(0) % 100;
			toReveal.remove(0);
			
			if(counters[x][y]==0){
				if(x>0 && y>0 && buttons[x-1][y-1].isEnabled()){//lewy_górny_róg
					buttons[x-1][y-1].setText(counters[x-1][y-1]+"");
					buttons[x-1][y-1].setEnabled(false);
					if(counters[x-1][y-1]==0){
						toReveal.add((x-1)*100 +(y-1));
					}
				}
				if(y>0 && buttons[x][y-1].isEnabled()){//górny_róg
					buttons[x][y-1].setText(counters[x][y-1]+"");
					buttons[x][y-1].setEnabled(false);
					if(counters[x][y-1]==0){
						toReveal.add((x)*100 +(y-1));
					}
				}
				if(x<counters.length-1 && y> 0 && buttons[x+1][y-1].isEnabled()){
					buttons[x+1][y-1].setText(counters[x+1][y-1]+"");
					buttons[x+1][y-1].setEnabled(false);
					if(counters[x+1][y-1]==0){
						toReveal.add((x+1)*100 +(y-1));
					}
				}
				
				if(x>0 && buttons[x-1][y].isEnabled()){//lewy_róg
					buttons[x-1][y].setText(counters[x-1][y]+"");
					buttons[x-1][y].setEnabled(false);
					if(counters[x-1][y]==0){
						toReveal.add((x-1)*100 +(y));
					}
				}
				
				if(x<counters.length-1 && buttons[x+1][y].isEnabled()){//prawy_róg
					buttons[x+1][y].setText(counters[x+1][y]+"");
					buttons[x+1][y].setEnabled(false);
					if(counters[x+1][y]==0){
						toReveal.add((x+1)*100 +(y));
					}
				}
				
				if(x>0 && y<counters[x].length - 1 && buttons[x-1][y+1].isEnabled()){//lewy_dolny_róg
					buttons[x-1][y+1].setText(counters[x-1][y+1]+"");
					buttons[x-1][y+1].setEnabled(false);
					if(counters[x-1][y+1]==0){
						toReveal.add((x-1)*100 +(y+1));
					}
				}
				if(y<counters[x].length - 1 && buttons[x][y+1].isEnabled()){//dolny_róg
					buttons[x][y+1].setText(counters[x][y+1]+"");
					buttons[x][y+1].setEnabled(false);
					if(counters[x][y+1]==0){
						toReveal.add((x)*100 +(y+1));
					}
				}
				if(x<counters.length-1 && y<counters[x].length - 1 && buttons[x+1][y+1].isEnabled()){//dolny_prawy
					buttons[x+1][y+1].setText(counters[x+1][y+1]+"");
					buttons[x+1][y+1].setEnabled(false);
					if(counters[x+1][y+1]==0){
						toReveal.add((x+1)*100 +(y+1));
					}
				}
				
			}
			revealZeros(toReveal);
		}
	}

	public void setUpMines() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				list.add(i * 100 + j);
			}
		}

		counters = new int[15][15];
		for (int i = 0; i < minesNumber; i++) {
			int choice = (int) (Math.random() * list.size());
			counters[list.get(choice) / 100][list.get(choice) % 100] = MINE;
			list.remove(choice);
		}
		// liczenie s¹siadów
		for (int x = 0; x < counters.length; x++) {
			for (int y = 0; y < counters[x].length; y++) {
				if (counters[x][y] != MINE) {
					int neighbourCount = 0;
					if (x > 0 && y > 0 && counters[x - 1][y - 1] == MINE) { // lewy
																			// górny
																			// róg
						neighbourCount++;
					}

					if (y > 0 && counters[x][y - 1] == MINE) {// góra
						neighbourCount++;
					}
					if (x < counters.length - 1 && y > 0 && counters[x + 1][y - 1] == MINE) {// lewy
																								// dolny
																								// róg
						neighbourCount++;
					}

					if (x > 0 && counters[x - 1][y] == MINE) {// lewy róg
						neighbourCount++;
					}

					if (x < counters.length - 1 && counters[x + 1][y] == MINE) {// prawy
																				// róg
						neighbourCount++;
					}

					if (y < counters[x].length - 1 && counters[x][y + 1] == MINE) {// dó³
						neighbourCount++;
					}

					if (x < counters.length - 1 && y < counters[x].length - 1 && counters[x + 1][y + 1] == MINE) {// prawy
																													// dolny
																													// róg
						neighbourCount++;
					}

					// if (x < counters.length - 1 && y > 0 && counters[x - 1][y
					// + 1] == MINE) {// prawy górny róg
					// neighbourCount++;
					// }
					counters[x][y] = neighbourCount;
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
