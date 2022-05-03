package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;


import UIsupportComponents.AutoCompleteComboBox;
import UIsupportComponents.CollapsiblePanel;
import UIsupportComponents.FourStateCheckBox;
import UIsupportComponents.MouseDragAdapter;
import gearFamily.Talisman;
import skills.Skill;
import skills.Slot;
import sorting.Wishlist;

public class ConfigurationPanel extends JPanel{

	private BoxLayout bLayout;
	private Wishlist panelWishlist;
	private String[] allSkillNames;
	private Skill[] allSkill;
	private Font panelFont;
	private AutoCompleteComboBox skillSelectionBox;
	private Listener wlListener;
	
	/*
	//setup for passing wl Objects to external methods
	//parameters
	private Skill[] wlSkills;
	private int[] prio;
	private int[] wantedLvls;
	private Slot[] talSlots;
	*/
	private ArrayList<ArrayList<OverviewSet>> overviewSetList;
	private ArrayList<CollapsiblePanel> collapsiblePanelList;
	private ArrayList<TalSlotLvlPanel> SlotLvlPanelList;
	private ArrayList<Wishlist> wlObjects;
	private ArrayList<Talisman> talObjects;
	
	//switch mechanic wl-tal
	private String optionStr;
	
	//components
	private CollapsiblePanel activeConfigPanel;
	private JPanel headerPanel;
	private JPanel overviewPanel;
	private JPanel selectionPanel;
	private JButton wlConfirmBtn;
	private JPanel btnPanel;
	
	//drag mechanic
	private MouseDragAdapter mda;
	
	//passing Obj to:
	private BuildOptionsPanel bop;
	
	private int wlWidth = 240;
	private int talWidth = 220;
	
	//one constructor for tal and wl panels. Differences filtered with if stmnts
	public ConfigurationPanel (String[] allSkillNames, Skill[] allSkill, BuildOptionsPanel bop, Font panelFont, String optionStr) {
		this.setOptionStr(optionStr);
		Border blackline = BorderFactory.createEtchedBorder(Color.GREEN, Color.BLUE);
		this.setBorder(blackline);
		
		if(optionStr == "wl") {
			//set Variables
			//selectionStack arraylists have to go together with one entry of collapsiblePanelList - always!!
			//in order to keep size and index correlation between the two arrays
			this.overviewSetList = new ArrayList<ArrayList<OverviewSet>>();
			this.collapsiblePanelList = new ArrayList<CollapsiblePanel>();
			this.wlObjects = new ArrayList<Wishlist>();
			
			this.allSkillNames = allSkillNames;
			this.panelFont = panelFont;
			this.allSkill = allSkill;
			this.setBop(bop);
			Font headerFont = panelFont.deriveFont(panelFont.getSize()*1.2F);

			
			//Layout
			this.bLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
			this.setLayout(bLayout);
			//int width = 240;
			Dimension d = new Dimension(wlWidth, Integer.MAX_VALUE);
			
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setBackground(Color.DARK_GRAY);
			
			//.createLineBorder(Color.BLACK);
			
			//listener
			wlListener = new Listener(this);
			
			//headerPanel
			this.headerPanel = new JPanel();
			headerPanel.setBackground(Color.DARK_GRAY);
			headerPanel.setAlignmentX(LEFT_ALIGNMENT);
			headerPanel.setAlignmentY(TOP_ALIGNMENT);	
			headerPanel.setPreferredSize(new Dimension(wlWidth, 40));
			headerPanel.setMinimumSize(new Dimension(wlWidth, 40));		
			headerPanel.setMaximumSize(new Dimension(wlWidth, 40));
			headerPanel.setBorder(blackline);
			
			JLabel header = new JLabel("Wishlist Creation", JLabel.LEFT);
			header.setForeground(Color.WHITE);
			header.setFont(headerFont);
			header.setAlignmentX(LEFT_ALIGNMENT);
			header.setAlignmentY(TOP_ALIGNMENT);
			this.headerPanel.add(header);	
					
			this.add(headerPanel);
			
			//overview panel contains separate collapsible obj overviews
			this.overviewPanel = new JPanel();
			BoxLayout overviewLayout = new BoxLayout(this.overviewPanel, BoxLayout.Y_AXIS);
			this.overviewPanel.setLayout(overviewLayout);
			this.overviewPanel.setBackground(Color.DARK_GRAY);
			this.overviewPanel.setAlignmentX(LEFT_ALIGNMENT);
			this.overviewPanel.setAlignmentY(TOP_ALIGNMENT);
			this.overviewPanel.setMaximumSize(new Dimension(wlWidth, Integer.MAX_VALUE));
			this.overviewPanel.setBorder(blackline);
			
			this.add(overviewPanel);
		
			this.newWlPanel();

			//selection
			this.selectionPanel = new SelectionObjSet2(this, allSkill);
			this.selectionPanel.setAlignmentX(LEFT_ALIGNMENT);
			this.selectionPanel.setAlignmentY(TOP_ALIGNMENT);
			//this.selectionPanel.setPreferredSize(new Dimension(width, 100));
			//this.selectionPanel.setMaximumSize(new Dimension(width, 100));
			this.add(this.selectionPanel);
			
			
			JButton wlConfirmBtn = new JButton("Update Wishlists");
			wlConfirmBtn.setFont(panelFont);
			wlConfirmBtn.setBackground(Color.DARK_GRAY);
			wlConfirmBtn.setForeground(Color.WHITE);
			wlConfirmBtn.setAlignmentX(LEFT_ALIGNMENT);
			wlConfirmBtn.setAlignmentY(TOP_ALIGNMENT);
			wlConfirmBtn.setPreferredSize(new Dimension(wlWidth, 20));			
			wlConfirmBtn.setMaximumSize(new Dimension(wlWidth, 20));
			wlConfirmBtn.addActionListener(wlListener);
			Border eBorder = BorderFactory.createEtchedBorder();
			wlConfirmBtn.setBorder(eBorder);
			
			this.add(wlConfirmBtn);
			
		}else if(optionStr == "tal") {
			//set Variables
			//selectionStack arraylists have to go together with one entry of collapsiblePanelList - always!!
			//in order to keep size and index correlation between the two arrays
			this.overviewSetList = new ArrayList<ArrayList<OverviewSet>>();
			this.collapsiblePanelList = new ArrayList<CollapsiblePanel>();
			this.SlotLvlPanelList = new ArrayList<TalSlotLvlPanel>();
			this.talObjects = new ArrayList<Talisman>();
			
			this.allSkillNames = allSkillNames;
			this.panelFont = panelFont;
			this.allSkill = allSkill;
			this.setBop(bop);
			Font headerFont = panelFont.deriveFont(panelFont.getSize()*1.2F);

			
			//Layout
			this.bLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
			this.setLayout(bLayout);
			
			Dimension d = new Dimension(talWidth, Integer.MAX_VALUE);
			//this.setPreferredSize(d);
			//this.setMinimumSize(d);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setBackground(Color.DARK_GRAY);
			
			//.createLineBorder(Color.BLACK);
			
			//listener
			wlListener = new Listener(this);
			
			//headerPanel
			this.headerPanel = new JPanel();
			headerPanel.setBackground(Color.DARK_GRAY);
			headerPanel.setAlignmentX(LEFT_ALIGNMENT);
			headerPanel.setAlignmentY(TOP_ALIGNMENT);
			headerPanel.setPreferredSize(new Dimension(talWidth, 40));
			headerPanel.setMinimumSize(new Dimension(talWidth, 40));
			headerPanel.setMaximumSize(new Dimension(talWidth, 40));
			headerPanel.setBorder(blackline);
			
			JLabel header = new JLabel("Talisman Creation", JLabel.LEFT);
			header.setForeground(Color.WHITE);
			header.setFont(headerFont);
			header.setAlignmentX(LEFT_ALIGNMENT);
			header.setAlignmentY(TOP_ALIGNMENT);
			this.headerPanel.add(header);	
					
			this.add(headerPanel);
			
			//overview panel contains separate collapsible obj overviews
			this.overviewPanel = new JPanel();
			BoxLayout overviewLayout = new BoxLayout(this.overviewPanel, BoxLayout.Y_AXIS);
			this.overviewPanel.setLayout(overviewLayout);
			this.overviewPanel.setBackground(Color.DARK_GRAY);
			this.overviewPanel.setAlignmentX(LEFT_ALIGNMENT);
			this.overviewPanel.setAlignmentY(TOP_ALIGNMENT);
			this.overviewPanel.setMaximumSize(new Dimension(talWidth, Integer.MAX_VALUE));
			this.overviewPanel.setBorder(blackline);
			
			this.add(overviewPanel);

			this.newTalPanel();
			
			//btn for more wlists
			

			//selection
			this.selectionPanel = new SelectionObjSet2(this, allSkill);
			this.selectionPanel.setAlignmentX(LEFT_ALIGNMENT);
			this.selectionPanel.setAlignmentY(TOP_ALIGNMENT);
			this.add(this.selectionPanel);
			
			
			JButton wlConfirmBtn = new JButton("Update Talismans");
			wlConfirmBtn.setFont(panelFont);
			wlConfirmBtn.setBackground(Color.DARK_GRAY);
			wlConfirmBtn.setForeground(Color.WHITE);
			wlConfirmBtn.setAlignmentX(LEFT_ALIGNMENT);
			wlConfirmBtn.setAlignmentY(TOP_ALIGNMENT);
			wlConfirmBtn.setMaximumSize(new Dimension(talWidth, 20));
			wlConfirmBtn.addActionListener(wlListener);
			Border eBorder = BorderFactory.createEtchedBorder();
			wlConfirmBtn.setBorder(eBorder);
			
			this.add(wlConfirmBtn);
			
			//this.repaint();
			
		}else {
			System.out.println("optionStr must be either tal or wl");
		}
		
		
		
	}
	//methods
	public ArrayList<Wishlist> getOverviewWishlists() {
		//cycle through all wishlists
		for(int i = 0;i < this.overviewSetList.size(); i++) {
			//prepare parameters for wl creation for currently selected wl
			Skill[] skills = new Skill[this.overviewSetList.get(i).size()];
			int[] prios = new int[this.overviewSetList.get(i).size()];
			int[] wLvls = new int[this.overviewSetList.get(i).size()];
			//cycle through overviewSets of wl
			for(int j = 0; j < this.overviewSetList.get(i).size(); j++) {
				prios[j] = this.overviewSetList.get(i).get(j).getPrio();
				wLvls[j] = this.overviewSetList.get(i).get(j).getWantedLvl();
				
				//find fitting skill to skillname
				for (int k = 0;k < this.allSkill.length; k++){
					if(this.overviewSetList.get(i).get(j).getSkillName() == ((Skill)allSkill[k]).getSkillName()) {
						skills[j] = this.allSkill[k];
					}
				}
			}
			//use parameters to add wl obj to selection
			if(i >= this.wlObjects.size()) {
				this.wlObjects.add(new Wishlist(skills, prios, wLvls, this.collapsiblePanelList.get(i).getNameField().getText()));
			}else {
				this.wlObjects.set(i, new Wishlist(skills, prios, wLvls, this.collapsiblePanelList.get(i).getNameField().getText())) ;				
			}
		}
		return this.wlObjects;	
	}
	
	//TAl NO WANTED LVLS but lvls -> Change constructor 
	public ArrayList<Talisman> getOverviewTalismans(){
		//cycle through all tals
		for(int i = 0;i < this.overviewSetList.size(); i++) {
			//needed parameters for each tal creation
			ArrayList<Skill> skillList = new ArrayList<Skill>();
			
			//slots have to be read from state of the btns
			Slot[] gearSlots = new Slot[3];
			gearSlots[0] = this.SlotLvlPanelList.get(i).getSlotBoxList().get(0).getStateSlot();
			gearSlots[1] = this.SlotLvlPanelList.get(i).getSlotBoxList().get(1).getStateSlot();
			gearSlots[2] = this.SlotLvlPanelList.get(i).getSlotBoxList().get(2).getStateSlot();	
			
			//cycle through overviewSets of selected tal
			for(int j = 0;j < this.overviewSetList.get(i).size();j++) {
				//find fitting skill to skillname
				for (int k = 0;k < this.allSkill.length; k++){
					if(this.overviewSetList.get(i).get(j).getSkillName() == ((Skill)allSkill[k]).getSkillName()) {					
						for(int s = 0; s < this.overviewSetList.get(i).get(j).getWantedLvl(); s++) {
							skillList.add(this.allSkill[k]);							
						}
					}
				}
			}
			//casting doesnt work on objects within array, so the [] is filled manually by AList
			Skill[] skillsA = new Skill[skillList.size()];
			for(int y = 0; y < skillsA.length; y++) {
				skillsA[y] = skillList.get(y);
			}
			
			//use parameters to add tal obj to selection
			if(this.talObjects == null) {
				this.talObjects.add(new Talisman(this.collapsiblePanelList.get(i).getNameField().getText(), skillsA, gearSlots));				
			}
			if(i >= this.talObjects.size()) {
				this.talObjects.add(new Talisman(this.collapsiblePanelList.get(i).getNameField().getText(), skillsA, gearSlots));
			}else {
				this.talObjects.set(i, new Talisman(this.collapsiblePanelList.get(i).getNameField().getText(), skillsA, gearSlots)) ;				
			}
		}
		
		return this.talObjects;
	}
	
	// WLStack stuff
	public void newWlPanel() {
		this.deselectCollapsiblePanels();
		
		CollapsiblePanel i = new CollapsiblePanel("WL " + (this.collapsiblePanelList.size() + 1), this.panelFont, this);
		i.addcHeader(new OverviewSet(this.getOptionStr()));	
		i.getcHeader().setPreferredSize(new Dimension(wlWidth, 45));
		collapsiblePanelList.add(i);
		//add corresponding overview list to each configPanel
		this.overviewSetList.add(new ArrayList<OverviewSet>());
		this.overviewPanel.add(i);
	}
	
	public void newTalPanel() {
		this.deselectCollapsiblePanels();
		
		CollapsiblePanel i = new CollapsiblePanel("Tal " + (this.collapsiblePanelList.size() + 1), this.panelFont, this);
		TalSlotLvlPanel btnPanel = new TalSlotLvlPanel();
		i.addcHeader(btnPanel);
		//list for every collapsible panel to store buttons
		this.SlotLvlPanelList.add(btnPanel);
				
		i.addcHeader(new OverviewSet(this.getOptionStr()));		
		collapsiblePanelList.add(i);
		
		//TODO resize width
		i.getcHeader().setPreferredSize(new Dimension(wlWidth, 100));
		//add corresponding overview stack to each configPanel
		this.overviewSetList.add(new ArrayList<OverviewSet>());
		this.overviewPanel.add(i);
		
		
	}
	public void removeCollapsiblePanel(CollapsiblePanel cPanel) {
		//TODO two removal methods for either optionStr
		//careful with corresponding arraylist entries of each type of collapsible
	}
	public ArrayList<CollapsiblePanel> getCollapsiblePanelList() {
		return this.collapsiblePanelList;
	}
	public ArrayList<TalSlotLvlPanel> getTalSlotLvlPanelList(){
		return this.SlotLvlPanelList;
	}
	
	public CollapsiblePanel getActiveConfigPanel() {
		for(int i = 0; i < this.collapsiblePanelList.size(); i++) {
			if(collapsiblePanelList.get(i).getSelected() == true) {
				this.activeConfigPanel = collapsiblePanelList.get(i);
			}
		}
		return this.activeConfigPanel;
	}
	public void deselectCollapsiblePanels() {
		for(int i = 0; i < this.collapsiblePanelList.size(); i++) {
			this.collapsiblePanelList.get(i).deselectPanel();
		}
	}
	
	// WL internal Overview Sets
	public ArrayList<OverviewSet> getCorrespondingOvSet (CollapsiblePanel correspondingconfigPanel) {
		ArrayList<OverviewSet> returnedOvSet = null;
		for(int i = 0;i < this.collapsiblePanelList.size(); i++) {
			if(this.collapsiblePanelList.get(i) == correspondingconfigPanel) {
				returnedOvSet = this.overviewSetList.get(i);
			}
		}
		return returnedOvSet;
	}
	public void newOverviewSet(String skillName, int wantedLvl, CollapsiblePanel wlOverview) {
		OverviewSet i = new OverviewSet(this.getCorrespondingOvSet(wlOverview).size() + 1, skillName, wantedLvl, this, this.getOptionStr());
		this.getCorrespondingOvSet(wlOverview).add(i);
		
		wlOverview.addMainContent(i);
	}
	
	public void removeOverviewSet(OverviewSet ovSet) {
		CollapsiblePanel wlOverview = this.getActiveConfigPanel();
		wlOverview.removeMainContent(ovSet);
		this.getCorrespondingOvSet(wlOverview).remove(ovSet);
		this.resetPrios(wlOverview);
	}
	
	public void resetPrios(CollapsiblePanel wlOverview) {
		for(int i = 0; i < this.getCorrespondingOvSet(wlOverview).size(); i++) {
			//only sees missing NOT switched ovSets
			/*
			this.getCorrespondingOvSet(wlOverview).get(i).revalidate();
			this.getCorrespondingOvSet(wlOverview).get(i).repaint();;
			this.getCorrespondingOvSet(wlOverview).get(i).setPrio(i + 1);
			*/
			
			if(wlOverview.getCMain().getComponent(i) instanceof OverviewSet) {
				((OverviewSet) wlOverview.getCMain().getComponent(i)).setPrio(i + 1);
				
			}
			
		
		}
		this.revalidate();
		this.repaint();
	}
	
	public BuildOptionsPanel getBop() {
		return bop;
	}

	public void setBop(BuildOptionsPanel bop) {
		this.bop = bop;
	}

	public void customizeComp(JComponent comp) {
    	comp.setBackground(Color.DARK_GRAY);
    	comp.setForeground(Color.WHITE);
    	comp.setAlignmentX(LEFT_ALIGNMENT);
    	comp.setAlignmentY(TOP_ALIGNMENT);
    	
    	
    	Dimension d = new Dimension(230, Integer.MAX_VALUE);
    	comp.setMaximumSize(d);    
    	
    	comp.revalidate();
    }
	//inner class to display chosen skills in a more compact format
	
	public String getOptionStr() {
		return optionStr;
	}
	public void setOptionStr(String optionStr) {
		this.optionStr = optionStr;
	}
	//minimal subclass to return btn states easier than with arrray calls to components at int
	public class TalSlotLvlPanel extends JPanel{
		private FourStateCheckBox slot1Box;
		private FourStateCheckBox slot2Box;
		private FourStateCheckBox slot3Box;
		private ArrayList<FourStateCheckBox> slotBoxList;
		
		public TalSlotLvlPanel() {
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.customizeComp(this);
			
			
			
			this.slot1Box = new FourStateCheckBox("Slot 1");
			this.customizeComp(slot1Box);
			this.setDecoIcons(slot1Box);
			this.slot2Box = new FourStateCheckBox("Slot 2");
			this.customizeComp(slot2Box);
			this.setDecoIcons(slot2Box);
			this.slot3Box = new FourStateCheckBox("Slot 3");
			this.customizeComp(slot3Box);
			this.setDecoIcons(slot3Box);
			
			this.add(slot1Box);
			this.add(slot2Box);
			this.add(slot3Box);
			
			slotBoxList = new ArrayList<FourStateCheckBox>();
			this.slotBoxList.add(slot1Box);
			this.slotBoxList.add(slot2Box);
			this.slotBoxList.add(slot3Box);
			
			
			//TODO track state of buttons somewhere
			this.setPreferredSize(new Dimension(talWidth, Integer.MAX_VALUE));			
			this.setMaximumSize(new Dimension(talWidth, Integer.MAX_VALUE));
			
			Border blackline = BorderFactory.createEtchedBorder();	
			
			this.setBorder(blackline);
			
		}
		
		public void setDecoIcons(FourStateCheckBox box) {
			
			
			
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("UI/mhw-decorations-wiki.png");
			Image image2 = null;
			try {
				image2 = ImageIO.read(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			box.setIcon(new ImageIcon(image2));
			ImageIcon goodSizeIcon = new ImageIcon(image2);

			int width = goodSizeIcon.getIconWidth();
			int heigth = goodSizeIcon.getIconHeight();
			
			
			is = this.getClass().getClassLoader().getResourceAsStream("UI/gem_level_1.png");
			Image image1 = null;
			try {
				image1 = ImageIO.read(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			;
			box.setSelectedIcon(new ImageIcon(image1.getScaledInstance(width, heigth, Image.SCALE_SMOOTH)));			
			
			is = this.getClass().getClassLoader().getResourceAsStream("UI/gem_level_2.png");
			Image image3 = null;
			try {
				image3 = ImageIO.read(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			box.setDisabledIcon(new ImageIcon(image3.getScaledInstance(width, heigth, Image.SCALE_SMOOTH)));		
			  
			is = this.getClass().getClassLoader().getResourceAsStream("UI/gem_level_3.png");
			Image image4 = null;
			try {
				image4 = ImageIO.read(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			box.setDisabledSelectedIcon(new ImageIcon(image4.getScaledInstance(width, heigth, Image.SCALE_SMOOTH)));
			
		}
		
		public ArrayList<FourStateCheckBox> getSlotBoxList(){
			return this.slotBoxList;
		}
		
		public void customizeComp(JComponent comp) {
	    	comp.setBackground(Color.DARK_GRAY);
	    	comp.setForeground(Color.WHITE);
	    	comp.setAlignmentX(LEFT_ALIGNMENT);
	    	comp.setAlignmentY(TOP_ALIGNMENT);
	    	
	    	//comp.setLayout(new BoxLayout(comp, BoxLayout.Y_AXIS));
	    	
	    	//Dimension d = new Dimension(230, Integer.MAX_VALUE);
	    	//comp.setMaximumSize(d);    
	    	
	    	comp.revalidate();
	    }
	}

	//OVERALL WIDTH: 230
	public class OverviewSet extends JPanel{
		private FlowLayout overviewLaoyut;
		private Listener ovListener;
		private ConfigurationPanel configPanel;
		private Integer prio;
		private JLabel prioColumn;
		private String skillName;
		private int wantedLvl;
		
		private ConfigurationPanel owner;
		private String optionStr;
		
		public OverviewSet(int prio, String skillName, int wantedLvl, ConfigurationPanel owner, String optionStr) {	
			this.optionStr = optionStr;
			this.owner = owner;
			this.prio = prio;
			this.skillName = skillName;
			this.wantedLvl = wantedLvl;
			this.overviewLaoyut = new FlowLayout(FlowLayout.LEADING);
			this.setLayout(overviewLaoyut);
			
			this.setAlignmentX(CENTER_ALIGNMENT);
			this.setAlignmentY(TOP_ALIGNMENT);
			
			ovListener = new Listener(this);
			
			this.setBackground(Color.DARK_GRAY);
			
			//Border instead of Separator
			Border whiteLine = BorderFactory.createLineBorder(Color.WHITE, 2, true);
			this.setBorder(whiteLine);
			
			if(optionStr =="wl") {
				
				prioColumn = new JLabel(((Integer)prio).toString(), JLabel.CENTER);
				prioColumn.setForeground(Color.WHITE);
				prioColumn.setFont(panelFont);
				prioColumn.setAlignmentX(CENTER_ALIGNMENT);
				prioColumn.setAlignmentY(CENTER_ALIGNMENT);
				Dimension prioD = new Dimension(20,30);
				prioColumn.setPreferredSize(prioD);
				prioColumn.setMaximumSize(prioD);
				prioColumn.setMinimumSize(prioD);

				this.add(prioColumn);
			}
			
			JLabel skillColumn = new JLabel(skillName, JLabel.LEFT);
			skillColumn.setForeground(Color.WHITE);
			skillColumn.setFont(panelFont);
			skillColumn.setAlignmentX(CENTER_ALIGNMENT);
			skillColumn.setAlignmentY(CENTER_ALIGNMENT);
			skillColumn.setPreferredSize(new Dimension(100, 30));
			skillColumn.setMinimumSize(new Dimension(100, 30));
			skillColumn.setMaximumSize(new Dimension(100, 30));
			
			this.add(skillColumn);	
			
			
			JLabel lvlColumn = new JLabel(((Integer)wantedLvl).toString(), JLabel.CENTER);
			lvlColumn.setForeground(Color.WHITE);
			lvlColumn.setFont(panelFont);
			lvlColumn.setAlignmentX(CENTER_ALIGNMENT);
			lvlColumn.setAlignmentY(CENTER_ALIGNMENT);
			Dimension prioD = new Dimension(20,30);
			lvlColumn.setPreferredSize(prioD);
			lvlColumn.setMinimumSize(prioD);
			lvlColumn.setMaximumSize(prioD);
			
			
			this.add(lvlColumn);	
			
			//JButton clear
			JButton clear = new JButton();
			//clear.setFont(panelFont.deriveFont((float)20));
			clear.setText("x");
			clear.setAlignmentX(RIGHT_ALIGNMENT);
			clear.setAlignmentY(TOP_ALIGNMENT);
			clear.setFont(panelFont);
			clear.setForeground(Color.WHITE);
			clear.setBackground(Color.DARK_GRAY);
			
			Dimension btnDim = new Dimension(40,25);
			clear.setPreferredSize(btnDim);
			clear.setMinimumSize(btnDim);		
			clear.setMaximumSize(btnDim);
			clear.addActionListener(ovListener);
			this.add(clear);
			
			//space for scrollbar
			//this.add(Box.createHorizontalStrut(15));
			
			this.revalidate();
			
			if (this.optionStr =="wl") {
				//what is this doing...?
				//this.add(Box.createHorizontalStrut(55));				
				int width = wlWidth-35;
				this.setPreferredSize(new Dimension(width, 40));
				this.setMinimumSize(new Dimension(width, 40));			
				this.setMaximumSize(new Dimension(width, 40));
			}else if(this.optionStr == "tal") {
				this.setPreferredSize(new Dimension(185, 40));
				this.setMinimumSize(new Dimension(185, 40));			
				this.setMaximumSize(new Dimension(185, 40));
			}			
		}
		//header constructor
		public OverviewSet(String optionStr) {	
			this.optionStr = optionStr;
			this.overviewLaoyut = new FlowLayout(FlowLayout.LEADING);
			this.setLayout(overviewLaoyut);  
			this.setBackground(Color.DARK_GRAY);
			
			Border etchedB = BorderFactory.createEtchedBorder();
			this.setBorder(etchedB);
			
			this.setAlignmentX(LEFT_ALIGNMENT);
			this.setAlignmentY(TOP_ALIGNMENT);
			
			if (this.optionStr =="wl") {
				JLabel prioColumn = new JLabel("Prio", JLabel.LEFT);
				prioColumn.setForeground(Color.WHITE);
				prioColumn.setFont(panelFont);
				prioColumn.setAlignmentX(LEFT_ALIGNMENT);
				prioColumn.setAlignmentY(TOP_ALIGNMENT);
				prioColumn.setPreferredSize(new Dimension(25, 30));
				//prioColumn.setPreferredSize(this.getSize());
				//prioColumn.setMaximumSize(new Dimension(270, Integer.MAX_VALUE));

				this.add(prioColumn);			
			}
			
			JLabel skillColumn = new JLabel("Skill", JLabel.LEFT);
			skillColumn.setForeground(Color.WHITE);
			skillColumn.setFont(panelFont);
			skillColumn.setAlignmentX(LEFT_ALIGNMENT);
			skillColumn.setAlignmentY(TOP_ALIGNMENT);
			skillColumn.setPreferredSize(new Dimension(100, 30));
			this.add(skillColumn);	
			
			
			JLabel lvlColumn = new JLabel("Lvl", JLabel.LEFT);
			lvlColumn.setForeground(Color.WHITE);
			lvlColumn.setFont(panelFont);
			lvlColumn.setAlignmentX(LEFT_ALIGNMENT);
			lvlColumn.setAlignmentY(TOP_ALIGNMENT);
			lvlColumn.setPreferredSize(new Dimension(25, 30));
			this.add(lvlColumn);
			
			if (this.optionStr =="wl") {
				//this.add(Box.createHorizontalStrut(55));				
				Dimension wlD = new Dimension(185,40);
				this.setPreferredSize(wlD);
				this.setMinimumSize(wlD);			
				this.setMaximumSize(wlD);
			}else if(this.optionStr == "tal") {
				this.setPreferredSize(new Dimension(185, 40));
				this.setMinimumSize(new Dimension(185, 40));			
				this.setMaximumSize(new Dimension(185, 40));
			}
			
		}
		//overview methods
		
		public void setPrio(int newPrio) {
			if(this.optionStr =="wl") {				
			this.prio = newPrio;
			this.prioColumn.setText(prio.toString());
			}
		}
		public int getPrio() {
			return this.prio;
		}
		public int getWantedLvl() {
			return this.wantedLvl;
		}
		public String getSkillName() {
			return this.skillName;
		}
		public ConfigurationPanel getOwner() {
			return this.owner;
		}
		
	}
	
	//reused old inner class even though only one instance is supposed to be generated now
	public class SelectionObjSet2 extends JPanel {
		//Variables
		private BoxLayout innerBLayout;
		private Listener SOSListener;
		private ConfigurationPanel configPanel;
		private JPanel btnPanel;
		private JSlider sl;
		private ArrayList<ArrayList<Object>> entryArray;
		private JComboBox<?> skillSelectionBox;
		private Skill[] allSkill;
		
		public SelectionObjSet2(ConfigurationPanel configPanel, Skill[] allSkill) {
			//entry Array
			entryArray = new ArrayList<ArrayList<Object>>();
			
			//Variable
			this.configPanel = configPanel;
			this.allSkill = allSkill;
			
			//Listener
			SOSListener = new Listener(this);
			
			//Layout
			innerBLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
			this.setLayout(innerBLayout);
			this.setBackground(Color.DARK_GRAY);
			
			Dimension parentDim = new Dimension((int)configPanel.getPreferredSize().getWidth(), 120);
			this.setPreferredSize(parentDim);
			this.setMaximumSize(parentDim);
			
			JLabel skillLabel = new JLabel("Skill selection");
			skillLabel.setForeground(Color.WHITE);
			skillLabel.setFont(panelFont);
			skillLabel.setAlignmentX(LEFT_ALIGNMENT);
			skillLabel.setAlignmentY(TOP_ALIGNMENT);
			this.add(skillLabel);
			
			
			//JComboBox
			skillSelectionBox = new JComboBox<>(allSkillNames);
			skillSelectionBox.setEditable(true);
			JTextComponent editor = (JTextComponent) skillSelectionBox.getEditor().getEditorComponent();
			editor.setDocument(new AutoCompleteComboBox(skillSelectionBox));
			
			skillSelectionBox.getEditor().getEditorComponent().setBackground(Color.DARK_GRAY);
			skillSelectionBox.getEditor().getEditorComponent().setForeground(Color.WHITE);
			skillSelectionBox.getEditor().getEditorComponent().setFont(panelFont);
	
			skillSelectionBox.setForeground(Color.BLACK);
			skillSelectionBox.setBackground(Color.WHITE);
			skillSelectionBox.setFont(panelFont);
			Dimension dropDownBoxSize = new Dimension((int) this.configPanel.getPreferredSize().getWidth(), 15);
			//THIS does it
			editor.setPreferredSize(dropDownBoxSize);
			editor.setMaximumSize(dropDownBoxSize);
			
			
			//skillSelectionBox.setPreferredSize(dropDownBoxSize);
			//skillSelectionBox.setMaximumSize(dropDownBoxSize);
			
			skillSelectionBox.setAlignmentX(LEFT_ALIGNMENT);
			skillSelectionBox.setAlignmentY(TOP_ALIGNMENT);
			
			skillSelectionBox.addActionListener(SOSListener);
			
			this.add(skillSelectionBox);
			
			//Slider
			JSlider sl = new JSlider(1,3,3);
			sl.setAlignmentX(LEFT_ALIGNMENT);
			sl.setAlignmentY(TOP_ALIGNMENT);
			sl.setForeground(Color.WHITE);
			sl.setBackground(Color.DARK_GRAY);
			sl.setFont(panelFont);
			sl.setPaintTrack(true);
			sl.setPaintTicks(true);
			sl.setPaintLabels(true);
			sl.setMajorTickSpacing(1);
			
			sl.setMaximumSize(new Dimension (200, 20));
			this.sl = sl;
			
			this.add(sl);
			
			JPanel btnPanel = new JPanel();
			//BorderLayout borderL = new BorderLayout();
			//btnPanel.setLayout(borderL);
			btnPanel.setAlignmentX(LEFT_ALIGNMENT);
			btnPanel.setAlignmentY(TOP_ALIGNMENT);
			btnPanel.setForeground(Color.WHITE);
			btnPanel.setBackground(Color.DARK_GRAY);
			this.btnPanel = btnPanel;
			
			this.add(btnPanel);
			
			//JButton confirm
			JButton confirm = new JButton("confirm");
			confirm.setAlignmentX(CENTER_ALIGNMENT);
			confirm.setAlignmentY(TOP_ALIGNMENT);
			confirm.setFont(panelFont);
			confirm.setForeground(Color.WHITE);
			confirm.setBackground(Color.DARK_GRAY);
			//confirm.setMaximumSize(this.getPreferredSize());
			confirm.addActionListener(SOSListener);
			btnPanel.add(confirm);
			
			//this.setMaximumSize(new Dimension(275, 120));
		}
		
		//methods selection panel
		public ConfigurationPanel getConfigPanel() {
			return this.configPanel;
		}
		public int getSlValue() {
			return this.sl.getValue();
		}
		public String getCbString() {
			return (String) this.skillSelectionBox.getSelectedItem();
		}
		public void addToStack() {
		
		}
		public JSlider getslider() {
			return this.sl;
		}
		public void resetSlider(JSlider sl, int max) {
			if(this.configPanel.getOptionStr().equals("wl")){
				sl.setMaximum(max);
				sl.setValue(max);				
			}else if(this.configPanel.getOptionStr().equals("tal")) {
				sl.setMaximum(max);
				sl.setValue(1);
			}
			//redraw?
		}
		public Skill[] getAllSkill() {
			return allSkill;
		}
	}
	
}
