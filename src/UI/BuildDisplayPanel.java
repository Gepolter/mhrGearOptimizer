package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;
import gearFamily.Armor;
import gearFamily.Weapon;
import skills.Skill;
import sorting.Build;

public class BuildDisplayPanel extends JPanel{
	
	private int xAxis;
	private int yAxis;
	
	private ArrayList<ArrayList<JPanel>> grid;
	private GridBagLayout gbLayout;
	private final static Insets insets = new Insets(2, 2, 0, 0);
	//gaps not used. Gridbad padX, padY used instead
	private int vGap;
	private int hGap;
	
	private Font buildPanelFont;
	private Dimension defaultSize;
	private Dimension firstColSize;
	private Dimension secondColSize;
	
	private Build displayBuild;
	
	public BuildDisplayPanel(String[] allWpnNames, String[] allArmorNames, String[]selectedTalNames, Font mhFont) {
		
		this.buildPanelFont = mhFont;

		this.setBorder(BorderFactory.createEtchedBorder());
		//configure Layout
		xAxis = 9;
		//prev 25
		yAxis = 25;
		
		defaultSize = new Dimension(70, 20);
		firstColSize = new Dimension(100,20);
		secondColSize = new Dimension(20,20);
		
		grid = new ArrayList<ArrayList<JPanel>>();
		gbLayout = new GridBagLayout();
		
		this.setLayout(gbLayout);
		this.setBackground(Color.DARK_GRAY);
		//Dimension size = new Dimension(700,500);
		//this.setPreferredSize(size);
		//this.setMaximumSize(size);
		
		
		//the grid is filled left to right, so the lines are accessed first (y) and filled with row elements of said line (x)
		for(int i = 0; i < yAxis; i++) {
			this.grid.add(new ArrayList<JPanel>());
			for(int j = 0; j < xAxis; j++) {
				//create JPanel and configure default size, etc.  //also create and save reference in arraylist
				this.grid.get(i).add(new JPanel());
				this.grid.get(i).get(j).setLayout(new BorderLayout());
				
				this.grid.get(i).get(j).setBackground(Color.BLACK);
				
 
				//needs both size methods to work
				//first column size
				if(j == 0) {
					//this.grid.get(i).get(j).setPreferredSize(firstColSize);
					//this.grid.get(i).get(j).setMinimumSize(firstColSize);
				}else if(j == 1) {
					
					//this.grid.get(i).get(j).setPreferredSize(secondColSize);
					//this.grid.get(i).get(j).setMinimumSize(secondColSize);
				}else {
					this.grid.get(i).get(j).setPreferredSize(defaultSize);
					this.grid.get(i).get(j).setMinimumSize(defaultSize);					
				}
				
				//add panel to frame
				this.addComponent(this.grid.get(i).get(j), j, i, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
			}
		}
		this.changeColumnWidth(0, firstColSize);
		this.changeColumnWidth(1, secondColSize);
		
		
		//2 header lines
		this.grid.get(0).get(0).setBackground(Color.DARK_GRAY);
		this.grid.get(0).get(1).setBackground(Color.DARK_GRAY);
		
		this.addLabelToGrid(0, 1, "Active Skill");
		this.addLabelToGrid(1, 1, "Lvl");
		
		//greartype names and img
		this.addLabelToGrid(2, 0, "Wpn Img");
		this.addLabelToGrid(2, 1, "Weapon");
		
		String headPath = "UI/mhw-helm-headgear-wiki.png";
		this.addImageToGrid(3, 0, headPath);
		this.addLabelToGrid(3, 1, "Head");
		
		String chestPath = "UI/mhw-torso-chest-plate-wiki.png";
		this.addImageToGrid(4, 0, chestPath);
		this.addLabelToGrid(4, 1, "Chest");
		
		String armsPath = "UI/mhw-arm-gauntlets-wiki.png";
		this.addImageToGrid(5, 0, armsPath);
		this.addLabelToGrid(5, 1, "Arms");
		
		String waistPath = "UI/mhw-waist-belt-wiki.png";
		this.addImageToGrid(6, 0, waistPath);
		this.addLabelToGrid(6, 1, "Waist");
		
		String legsPath = "UI/mhw-feet-boots-greaves.png";
		this.addImageToGrid(7, 0, legsPath);
		this.addLabelToGrid(7, 1, "Legs");
		
		String talPath = "UI/mhw-charms-wiki.png";
		this.addImageToGrid(8, 0, talPath);
		this.addLabelToGrid(8, 1, "Talisman");
		
	}
	
	
	private void addImageToGrid(int x, int y, String imgPath) {
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(imgPath);
		Image image1 = null;
		try {
			image1 = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		JLabel contentLabel = new JLabel(new ImageIcon(image1.getScaledInstance(20, 20, Image.SCALE_SMOOTH)), JLabel.LEFT);
		contentLabel.setVerticalAlignment(SwingConstants.CENTER);
		contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//resize label column if new content exceeds current size		
		this.grid.get(y).get(x).add(contentLabel);
		
		if(contentLabel.getPreferredSize().getWidth() > this.grid.get(y).get(x).getSize().getWidth()) {
			//contentLabel.setPreferredSize(contentLabel.getPreferredSize());
			contentLabel.setMinimumSize(contentLabel.getPreferredSize());
			
			//this.grid.get(y).get(x).setPreferredSize(contentLabel.getMinimumSize());			
			this.grid.get(y).get(x).setMinimumSize(contentLabel.getMinimumSize());
		}else {
			//contentLabel.setPreferredSize(this.grid.get(y).get(x).getMinimumSize());			
			contentLabel.setMinimumSize(this.grid.get(y).get(x).getMinimumSize());
		}
		
		
		this.grid.get(y).get(x).repaint();
		
	}


	//add methods
	public void addLabelToGrid(int x, int y, String content) {
		JLabel contentLabel = new JLabel(content);
		contentLabel.setFont(buildPanelFont);
		
		contentLabel.setForeground(Color.WHITE);
		contentLabel.setVerticalAlignment(SwingConstants.CENTER);
		contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		if(containsNumeric(content)) {
			this.grid.get(y).get(x).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY), BorderFactory.createLoweredBevelBorder()));
		}
		
		//contentLabel.setAlignmentY(TOP_ALIGNMENT);
		
		//resize label column if new content exceeds current size		
		
		//this.grid.get(y).get(x).setAlignmentY(TOP_ALIGNMENT);
		
		//check for size and 0, bc the preferredSize of Labels seems to initialize when beeing drawn. seems weird though.
		//add buffer 
		int buffer = 10;
		if(contentLabel.getPreferredSize().getWidth() + 10 > this.grid.get(y).get(x).getSize().getWidth() && this.grid.get(y).get(x).getSize().getWidth() != 0) {
			
			Dimension bufferedDim = new Dimension((int) contentLabel.getPreferredSize().getWidth() + 10, 20/*(int)contentLabel.getPreferredSize().getHeight() + 10*/);
			contentLabel.setMinimumSize(bufferedDim);
			
			//this.grid.get(y).get(x).setPreferredSize(contentLabel.getMinimumSize());			
			//this.grid.get(y).get(x).setMinimumSize(contentLabel.getMinimumSize());
			this.changeColumnWidth(x, bufferedDim);
			
			
		}else {
			contentLabel.setMinimumSize(this.grid.get(y).get(x).getMinimumSize());
			
		}
		
		this.grid.get(y).get(x).add(contentLabel, BorderLayout.CENTER);
		
		this.grid.get(y).get(x).repaint();
		
		
	}
	
	public void displayBuild(Build myBuild) {
		
		this.clearGrid();
		//loop through total buildskills to fill first two columns (names total lvls)
		//TODO display in prio order
		for(int i = 0;i < myBuild.totalSkillLevels().size();i++) {
			this.addLabelToGrid(0, i + 2, myBuild.totalSkillLevels().get(i).get(0).toString());
			this.addLabelToGrid(1, i + 2, myBuild.totalSkillLevels().get(i).get(2).toString());
		}
		
		//call build method that gives array(skill arrayNat/Deco(Skillname, lvl)) for certain geartypes
		//put results in fitting column - repeat for every geartype
		for(int i = 0; i < myBuild.totalSkillLevels().size();i++) {
			//check nat skills on all types for line i
			
			for(int n = 0; n < 7; n++) {
				
				for(int j = 0; j < myBuild.buildGearDecoInfo(n).get(0).size(); j++) {
					//if name fits skill then add label to type column in line i+2
					
					ArrayList<ArrayList<ArrayList<Object>>> a = myBuild.buildGearDecoInfo(n);
					
					
					if(((JLabel) this.grid.get(i + 2).get(0).getComponent(0)).getText() == myBuild.buildGearDecoInfo(n).get(0).get(j).get(0)) {
						this.addLabelToGrid(n + 2, i + 2, myBuild.buildGearDecoInfo(n).get(0).get(j).get(1).toString());
					}	
				}
				//check deco skills on all types for line i
				for(int j = 0; j < myBuild.buildGearDecoInfo(n).get(1).size(); j++) {
					if(((JLabel) this.grid.get(i + 2).get(0).getComponent(0)).getText() == myBuild.buildGearDecoInfo(n).get(1).get(j).get(0)) {
						this.addLabelToGrid(n + 2, i + 2, " (" + myBuild.buildGearDecoInfo(n).get(1).get(j).get(1).toString() + ")");
					}
				}
			}	
		}
		//TODO give Headers the Name of the selected Gear parts
		this.grid.get(1).get(2).remove(0);
		this.addLabelToGrid(2, 1,myBuild.getbuildWpn().getGearName());
		
		this.grid.get(1).get(3).remove(0);
		this.addLabelToGrid(3, 1,myBuild.getBuildArmor().get(0).getGearName());
		
		this.grid.get(1).get(4).remove(0);
		this.addLabelToGrid(4, 1,myBuild.getBuildArmor().get(1).getGearName());
		
		this.grid.get(1).get(5).remove(0);
		this.addLabelToGrid(5, 1,myBuild.getBuildArmor().get(2).getGearName());
		
		this.grid.get(1).get(6).remove(0);
		this.addLabelToGrid(6, 1,myBuild.getBuildArmor().get(3).getGearName());
		
		this.grid.get(1).get(7).remove(0);
		this.addLabelToGrid(7, 1,myBuild.getBuildArmor().get(4).getGearName());
		
		this.grid.get(1).get(8).remove(0);
		this.addLabelToGrid(8, 1,myBuild.getBuildTalisman().getGearName());

		
		this.revalidate();
		this.repaint();
	}
	
	public void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0, anchor, fill, insets, 1, 1);
		this.add(component, gbc);
	}

	public void clearGrid() {
		for(int i = 2; i < this.grid.size(); i++) {
			for(int j = 0; j < this.grid.get(i).size(); j++) {
				this.grid.get(i).get(j).setBorder(null);
				if(this.grid.get(i).get(j).getComponentCount() != 0) {
					for(Component a : this.grid.get(i).get(j).getComponents()) {
						this.grid.get(i).get(j).remove(a);
					}	
				}
			}
		}
	}
	public boolean containsNumeric(String str) {
		if(str.matches(".*\\d.*")) {
			return true;
		}
		return false;
	}
	
	public boolean isNumeric (String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}	
	}
	
	public void changeColumnWidth(int x, Dimension d) {
		for(int i = 0; i < this.yAxis; i++) {
			this.grid.get(i).get(x).setPreferredSize(d);
			this.grid.get(i).get(x).setMinimumSize(d);

		}
	}
}
