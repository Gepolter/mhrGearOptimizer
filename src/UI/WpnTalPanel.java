package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import UIsupportComponents.AutoCompleteComboBox;
import gearFamily.Armor;
import gearFamily.Weapon;
import skills.Skill;

public class WpnTalPanel extends JPanel{
	
	private FlowLayout wpnTalLayout;
	private JLabel wpnLabel;
	private JLabel talLabel;
	private AutoCompleteComboBox wpnSelection;
	private AutoCompleteComboBox talSelection;
	private Listener wtListener;
	private String[] allWpnNames;
	private Font mhFont;
	private TalFrame TFrame;
	private String[] allSkillNames;
	private Skill[] allSkill;
	
	private String[] armorpools;
	private Dimension boxSize;
	
	public WpnTalPanel(String[] allWpnNames, Font mhFont, String[] allSkillNames, Skill[] allSkill, String[] armorpools) {
		this.allWpnNames = allWpnNames;
		this.mhFont = mhFont;
		this.allSkillNames = allSkillNames;
		this.allSkill = allSkill;
		this.armorpools = armorpools;
		
		wpnTalLayout = new FlowLayout();
		wpnTalLayout.setAlignment(FlowLayout.LEFT);
		wpnTalLayout.setHgap(50);
		this.setLayout(wpnTalLayout);
		
		this.boxSize = new Dimension(150, 15);
				
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createEtchedBorder());
		
		wtListener = new Listener(this);
		
		//Weapon Label
		wpnLabel = new JLabel("Weapon", JLabel.LEFT);
		wpnLabel.setFont(mhFont);
		wpnLabel.setForeground(Color.WHITE);
		this.add(wpnLabel);
		
		//Weapon ComboBox
		JComboBox wpnSelectionBox = new JComboBox(allWpnNames);
		wpnSelectionBox.setEditable(true);
		
		JTextComponent editor = (JTextComponent) wpnSelectionBox.getEditor().getEditorComponent();
		editor.setDocument(new AutoCompleteComboBox(wpnSelectionBox));
		
		wpnSelectionBox.getEditor().getEditorComponent().setBackground(Color.DARK_GRAY);
		wpnSelectionBox.getEditor().getEditorComponent().setForeground(Color.WHITE);
		wpnSelectionBox.getEditor().getEditorComponent().setFont(mhFont);

		wpnSelectionBox.setBackground(Color.DARK_GRAY);
		wpnSelectionBox.setForeground(Color.BLACK);
		wpnSelectionBox.setFont(mhFont);
		
		wpnSelectionBox.addActionListener(wtListener);
		
		this.add(wpnSelectionBox);
		
		//Separator
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setBackground(Color.WHITE);
		sep.setPreferredSize(wpnLabel.getPreferredSize());
		this.add(sep);
		
		JLabel armsLabel = new JLabel("Armorpool");
		armsLabel.setForeground(Color.WHITE);
		armsLabel.setFont(mhFont);
		armsLabel.setAlignmentX(LEFT_ALIGNMENT);
		armsLabel.setAlignmentY(TOP_ALIGNMENT);
		this.add(armsLabel);
		
		
		JComboBox<?> armsCb = new JComboBox<>(armorpools);
		armsCb.setEditable(true);
		JTextComponent editor2 = (JTextComponent) armsCb.getEditor().getEditorComponent();
		editor2.setDocument(new AutoCompleteComboBox(armsCb));
		
		armsCb.getEditor().getEditorComponent().setBackground(Color.DARK_GRAY);
		armsCb.getEditor().getEditorComponent().setForeground(Color.WHITE);
		armsCb.getEditor().getEditorComponent().setFont(mhFont);

		armsCb.setForeground(Color.BLACK);
		armsCb.setBackground(Color.WHITE);
		armsCb.setFont(mhFont);
		armsCb.setMaximumSize(boxSize);
		armsCb.setPreferredSize(boxSize);
		armsCb.setAlignmentX(LEFT_ALIGNMENT);
		armsCb.setAlignmentY(TOP_ALIGNMENT);
		
		armsCb.addActionListener(wtListener);
		
		this.add(armsCb);
		
	}
	//not needed? 
	public void setBtnListener(Listener newBtnListener) {
		wtListener = newBtnListener;
	}	
	
	public void newTalFrame() {
		TFrame = new TalFrame(allSkillNames, allSkill, mhFont);
	}
}
