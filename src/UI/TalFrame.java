package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import UIsupportComponents.AutoCompleteComboBox;
import gearFamily.Talisman;
import skills.Skill;

class TalFrame extends JFrame{

	private Listener talListener;
	private String[] allSkillNames;
	private Skill[] allSkill;
	private Font mhFont;
	private GridBagLayout baseLayout;
	private final static Insets insets = new Insets(0, 0, 0, 0);
	private JPanel headerPanel;
	private JPanel selectionPanel;
	private JPanel resultsPanel;
	private TSelectionSet tss;
	private FlowLayout headerLayout;
	private int counter;
	private JScrollPane sbRes;
	private JScrollPane sbChoice;
	
	private String[] tNameMemory;
	private Skill[] tSkillMemory;
	private Integer[] tLvlMemory;
	
	private Talisman[] tMemory;
	
	public TalFrame(String[] allSkillNames, Skill[] allSkill, Font mhFont) {
		//Variable
		this.allSkillNames = allSkillNames;
		this.allSkill = allSkill;
		this.mhFont = mhFont;
		this.counter = 1;
		
		talListener = new Listener(this);
		
		//first step! if reopened after prior use, reinstate tals from memory
		//when tFrame is closed, pass tMemory to 
		
		this.setName("Tal Frame");
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		baseLayout = new GridBagLayout();
		this.setLayout(baseLayout);
		this.setSize(500,600);
		this.setVisible(true);
		
		//header
		headerPanel = new JPanel();
		//TODO functionality
		headerLayout = new FlowLayout();
		headerLayout.setAlignment(FlowLayout.LEFT);
		headerLayout.setHgap(50);
		headerPanel.setLayout(headerLayout);
		
		headerPanel.setBackground(Color.DARK_GRAY);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(mhFont);
		nameLabel.setBackground(Color.DARK_GRAY);
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		headerPanel.add(nameLabel);
		
		JTextField tField = new JTextField();
		tField.setFont(mhFont);
		tField.setBackground(Color.DARK_GRAY);
		tField.setForeground(Color.WHITE);
		tField.setCaretColor(Color.WHITE);
		tField.setAlignmentX(CENTER_ALIGNMENT);
		tField.setPreferredSize(new Dimension(120,25));
		tField.addActionListener(talListener);
		headerPanel.add(tField);
		
		//memory for JTextField
		//counter for numer of tals assigned to create tal btn? !!!number of tResultSets
		
		JButton confirmBtn = new JButton("confirm");
		confirmBtn.setFont(mhFont);
		confirmBtn.setBackground(Color.DARK_GRAY);
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.setAlignmentX(CENTER_ALIGNMENT);
		confirmBtn.setPreferredSize(new Dimension(100,25));
		confirmBtn.addActionListener(talListener);
		headerPanel.add(confirmBtn);
		
		this.addComponent(headerPanel, 0, 0, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this);
		
		//Selection of tals
		selectionPanel = new JPanel();
		//TODO functionality: create 2 arrays (skills,lvl) for creation of result objects. add stuff when create tal is pressed
		
		tss = new TSelectionSet(this);
		selectionPanel.add(tss);
		tss.removeFirstBtn();
		this.addComponentHeavyX(selectionPanel, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this);
		
		//scrollbar for choice
		sbChoice = new JScrollPane(selectionPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sbChoice.setWheelScrollingEnabled(true);
		this.addComponentHeavyBoth(sbChoice, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, this);
		
		
				// result panel - auto update of table
		resultsPanel = new JPanel();
		GridBagLayout tableGbl = new GridBagLayout();
		int[] widhts = {80,80,80};
		//tableGbl.columnWidths = widhts;
		
		resultsPanel.setLayout(tableGbl);
		
		//create Tal
		JButton createT = new JButton("create Talisman");
		createT.setAlignmentY(TOP_ALIGNMENT);
		createT.addActionListener(talListener);
		this.addComponent(createT,  0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this.resultsPanel);
		
		JPanel headerRow = new JPanel();
		FlowLayout fL = new FlowLayout();
		fL.setHgap(80);
		headerRow.setLayout(fL);
		
		//columnH1
		JLabel columnH1 = new JLabel("Name");
		columnH1.setForeground(Color.WHITE);
		columnH1.setAlignmentX(CENTER_ALIGNMENT);
		columnH1.setFont(mhFont);
		headerRow.add(columnH1);
		
		//columnH2
		JLabel columnH2 = new JLabel("Skill");
		columnH2.setForeground(Color.WHITE);
		columnH2.setAlignmentX(CENTER_ALIGNMENT);
		columnH2.setFont(mhFont);
		headerRow.add(columnH2);
		
		//columnH3
		JLabel columnH3 = new JLabel("Lvl");
		columnH3.setForeground(Color.WHITE);
		columnH3.setAlignmentX(CENTER_ALIGNMENT);
		columnH3.setFont(mhFont);
		headerRow.add(columnH3);
		
		this.addComponentHeavyX(headerRow,  0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this.resultsPanel);

		//Filled by objects of inner class 
		String tName = "testName";
		Integer[] tLvl = {1,2,3,4,5};
		
	    TResult testResult = new TResult(this, tName, allSkill, tLvl);
		this.addComponent(testResult, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this.resultsPanel);

		this.addComponentHeavyY(resultsPanel, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this);
		
		//scrollbar for results
		sbRes = new JScrollPane(resultsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sbRes.setWheelScrollingEnabled(true);
		this.addComponentHeavyBoth(sbRes, 1, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, this);
	}
	
	//inner class for selection of tSkills
	public class TSelectionSet extends JPanel{
		//Variables
		private BoxLayout innerLayout;
		private Listener tssListener;
		private TalFrame tFrame;
		private JSlider sl;
		private JPanel btnPanel;
		
		public TSelectionSet(TalFrame tFrame) {
			//Variables
			this.tFrame = tFrame;
			//Listener
			tssListener = new Listener(this);
			//Layout
			innerLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
			this.setLayout(innerLayout);
			this.setBackground(Color.DARK_GRAY);
			//JSeparator
			JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
			sep.setBackground(Color.WHITE);
			sep.setForeground(Color.WHITE);
			sep.setPreferredSize(this.getPreferredSize());
			sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
			sep.setAlignmentX(CENTER_ALIGNMENT);
			sep.setAlignmentY(TOP_ALIGNMENT);
			this.add(sep);
			
			//JComboBox
			JComboBox<?> skillSelectionBox = new JComboBox<>(allSkillNames);
			skillSelectionBox.setEditable(true);
			JTextComponent editor = (JTextComponent) skillSelectionBox.getEditor().getEditorComponent();
			editor.setDocument(new AutoCompleteComboBox(skillSelectionBox));
			
			skillSelectionBox.getEditor().getEditorComponent().setBackground(Color.DARK_GRAY);
			skillSelectionBox.getEditor().getEditorComponent().setForeground(Color.WHITE);
			skillSelectionBox.getEditor().getEditorComponent().setFont(mhFont);

			skillSelectionBox.setForeground(Color.BLACK);
			skillSelectionBox.setBackground(Color.WHITE);
			skillSelectionBox.setFont(mhFont);
			skillSelectionBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
			skillSelectionBox.setPreferredSize(this.getPreferredSize());
			skillSelectionBox.setAlignmentX(CENTER_ALIGNMENT);
			skillSelectionBox.setAlignmentY(TOP_ALIGNMENT);
			
			skillSelectionBox.addActionListener(tssListener);
			
			this.add(skillSelectionBox);
			
			//JSlider
			JSlider sl = new JSlider(1,10,5);
			sl.setAlignmentX(CENTER_ALIGNMENT);
			sl.setAlignmentY(TOP_ALIGNMENT);
			sl.setForeground(Color.WHITE);
			sl.setBackground(Color.DARK_GRAY);
			sl.setFont(mhFont);
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
			btnPanel.setAlignmentX(CENTER_ALIGNMENT);
			btnPanel.setAlignmentY(TOP_ALIGNMENT);
			btnPanel.setForeground(Color.WHITE);
			btnPanel.setBackground(Color.DARK_GRAY);
			this.btnPanel = btnPanel;
			
			this.add(btnPanel);
			
			//JButton confirm
			JButton confirm = new JButton("confirm");
			confirm.setAlignmentX(CENTER_ALIGNMENT);
			confirm.setAlignmentY(TOP_ALIGNMENT);
			confirm.setFont(mhFont);
			confirm.setForeground(Color.WHITE);
			confirm.setBackground(Color.DARK_GRAY);
			confirm.setMaximumSize(this.getPreferredSize());
			confirm.addActionListener(tssListener);
			
			btnPanel.add(confirm);
			
			//JButton clear
			JButton clear = new JButton("clear");
			clear.setAlignmentX(CENTER_ALIGNMENT);
			clear.setAlignmentY(TOP_ALIGNMENT);
			clear.setFont(mhFont);
			clear.setForeground(Color.WHITE);
			clear.setBackground(Color.DARK_GRAY);
			clear.setMaximumSize(this.getPreferredSize());
			clear.addActionListener(tssListener);
			
			btnPanel.add(clear);	
			
		}
		public void removeFirstBtn() {
			this.btnPanel.remove(btnPanel.getComponent(1));
		}
	}
	
	public class TResult extends JPanel{
		//variables
		private TalFrame tFrame;
		private GridBagLayout tResLayout;
		private Listener tResListener;
		private JPanel btnPanel;
		private JButton deleteB;
		
		//result memory variables
		private String tName; 
		private Skill[] tSkills;
		private Integer[] tLvls;
		
		public TResult(TalFrame tFrame,String tName, Skill[] tSkills, Integer[] tLvls) {
			//Variables
			this.tFrame = tFrame;
			this.tName = tName;
			this.tSkills = tSkills;
			this.tLvls = tLvls;
			
			//Listener
			tResListener = new Listener(this);
			//Layout
			tResLayout = new GridBagLayout();
			this.setLayout(tResLayout);
			//1st Column
			JLabel tNameL = new JLabel(tName);
			tFrame.addComponent(tNameL, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this);
			
			//2nd Column
			for(int i = 0; i < tSkills.length; i++) {
				JLabel sLabel = new JLabel(tSkills[i].getSkillName());
				tFrame.addComponent(sLabel, 1, i, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this);
			}
			//3rd Column
			for(int i = 0; i < tLvls.length; i++) {
				JLabel lvlLabel = new JLabel(tLvls[i].toString());
				tFrame.addComponent(lvlLabel, 2, i, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, this);
			}
			//delete btn
			deleteB = new JButton("delete");
			deleteB.setFont(mhFont);
			deleteB.setBackground(Color.WHITE);
			deleteB.setForeground(Color.LIGHT_GRAY);
			deleteB.addActionListener(tResListener);
			
						
		}
	
		//methods for adding elements of results for one tal per stored arraylist reference of TSelectionSet 
	}
	
	
	public void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill, Container parent) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 0.0, 0.0, anchor, fill, insets, 0,0);
		gbc.ipadx = 50;
		
		component.setFont(mhFont);
		component.setBackground(Color.DARK_GRAY);
		component.setForeground(Color.WHITE);
		parent.add(component, gbc);
	}
	
	public void addComponentHeavyY(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill, Container parent) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 0.0, 1.0, anchor, fill, insets, 0,0);
		component.setFont(mhFont);
		component.setBackground(Color.DARK_GRAY);
		component.setForeground(Color.WHITE);
		parent.add(component, gbc);
	}
	
	public void addComponentHeavyX(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill, Container parent) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 0.0, anchor, fill, insets, 0,0);
		
		component.setFont(mhFont);
		component.setBackground(Color.DARK_GRAY);
		component.setForeground(Color.WHITE);
		parent.add(component, gbc);
	}
	
	public void addComponentHeavyBoth(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill, Container parent) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0, anchor, fill, insets, 0,0);
		
		component.setFont(mhFont);
		component.setBackground(Color.DARK_GRAY);
		component.setForeground(Color.WHITE);
		parent.add(component, gbc);
	}
	
	public void addSepHor(Container container) {
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		sep.setBackground(Color.WHITE);
		sep.setPreferredSize(this.getPreferredSize());
		sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
		sep.setAlignmentY(TOP_ALIGNMENT);
		container.add(sep);
	}
	
	

}
