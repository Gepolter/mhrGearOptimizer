package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;

import gearFamily.*;
import skills.Skill;


public class UiAssembly {

	private UiFrame baseFrame;
	private WpnTalPanel wtPanel;
	private ConfigurationPanel configPanelWl;
	private ConfigurationPanel configPanelTal;
	private BuildDisplayPanel bDispPanel;
	private TalFrame TFrame;
	private BuildOptionsPanel bop;
	
	private Listener bl;
	
	private JScrollPane scrollBarWl;
	private JScrollPane scrollBarDisp;
	private Font mhFont;

	private String[] allWpnNames;
	private String[] allArmorNames;
	private String[] allSkillNames;
	private String[] armorPools;
	
	//TODO Data setup
	private Skill[] allSkill;
	private Armor[] allArmor;
	private Weapon[] allWpn;
	private Deco[] allDeco;
	private Weapon chosenWpn;
	
	public UiAssembly(String[] allWpnNames, Weapon[] allWpn, String[] allArmorNames, Armor[] allArmor, String[] allSkillNames, Skill[] allSkill, Deco[] allDeco, Font mhFont) {
		
		//TODO pref all data including arrays and arraylists for functionality and distribute it among objects
		this.allWpnNames = allWpnNames;
		this.setAllWpn(allWpn);
		this.allArmorNames = allArmorNames;
		this.setAllArmor(allArmor);
		this.allSkillNames = allSkillNames;
		this.setAllSkill(allSkill);
		this.allDeco = allDeco;
		this.armorPools = new String[] {"a","b","c"};
		
		
		//TODO set weapon through combobox in wpnarmor panel
		this.chosenWpn = allWpn[0];
		
		//Use Constructor to create Frame so uiListener is created then create ui parts fill Frame with uiFrame.addComponents
		//Frame
		bl = new Listener(this);
		baseFrame = new UiFrame();
		//baseFrame.setUiListener(bl);
		
		//Components
		wtPanel = new WpnTalPanel(allWpnNames, mhFont, allSkillNames, allSkill, armorPools);
		baseFrame.addComponentHeavyX(wtPanel, 0, 0, 2, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

		String[] selectedTalNames = new String[9];
		bDispPanel = new BuildDisplayPanel(allWpnNames, allArmorNames, selectedTalNames, mhFont);
		baseFrame.addComponentHeavyBoth(bDispPanel, 0, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
		
		//with bPanel width 2 and no scrollpane (assignment to bPanel is enough!! it works)
		
		scrollBarDisp = new JScrollPane(bDispPanel);// ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBarDisp.setWheelScrollingEnabled(true);
		scrollBarDisp.setOpaque(true);
		scrollBarDisp.setPreferredSize(this.bDispPanel.getPreferredSize());
		
		//customise scrollpane
		JScrollPane mainPane = new JScrollPane(bDispPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Dimension dPane = new Dimension(10, Integer.MAX_VALUE);
		mainPane.getVerticalScrollBar().setPreferredSize(dPane);
		mainPane.getVerticalScrollBar().setMaximumSize(dPane);
		
		mainPane.setBorder(null);
		mainPane.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
		mainPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.BLACK;			
				this.thumbDarkShadowColor =Color.GRAY;
			}
		});			
		
		baseFrame.addComponentHeavyBoth(mainPane, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		
		
		//TODO new panel for choice/clear of Wlists, gearpool and confirm
		bop = new BuildOptionsPanel(mhFont, armorPools, this, configPanelWl, configPanelTal, chosenWpn, bDispPanel, baseFrame);
		baseFrame.addComponentHeavyX(bop, 0, 2, 2, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		
		String optionStr = "wl";
		configPanelWl = new ConfigurationPanel(allSkillNames, allSkill, bop, mhFont, optionStr);
		baseFrame.addComponent(configPanelWl, 2, 0, 1, 3, GridBagConstraints.EAST, GridBagConstraints.VERTICAL);
		
		optionStr = "tal";
		configPanelTal = new ConfigurationPanel(allSkillNames, allSkill, bop, mhFont, optionStr);
		baseFrame.addComponent(configPanelTal, 3, 0, 1, 3, GridBagConstraints.EAST, GridBagConstraints.VERTICAL);
		
		bop.setWlConfigPanel(configPanelWl);
		bop.setTalConfigPanel(configPanelTal);
		
		baseFrame.revalidate();
		baseFrame.repaint();

		//this.baseFrame.packFrame();
	}
	
	
	
	public UiFrame getBaseFrame() {
		return this.baseFrame;
	}
	public WpnTalPanel getWtPanel() {
		return this.wtPanel;
	}
	public BuildDisplayPanel getBDispPanel() {
		return this.bDispPanel;
	}



	public Skill[] getAllSkill() {
		return allSkill;
	}



	public void setAllSkill(Skill[] allSkill) {
		this.allSkill = allSkill;
	}



	public Armor[] getAllArmor() {
		return allArmor;
	}



	public void setAllArmor(Armor[] allArmor) {
		this.allArmor = allArmor;
	}



	public Weapon[] getAllWpn() {
		return allWpn;
	}



	public void setAllWpn(Weapon[] allWpn) {
		this.allWpn = allWpn;
	}



	public Deco[] getAllDeco() {
		return allDeco;
	}



	public void setAllDeco(Deco[] allDe1co) {
		this.allDeco = allDe1co;
	}
}
//TODO play around with different RGBs for different shades of black in UI
