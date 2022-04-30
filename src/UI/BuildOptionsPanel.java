package UI;

import java.util.ArrayList;

import javax.swing.MutableComboBoxModel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import UIsupportComponents.AutoCompleteComboBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import gearFamily.Armor;
import gearFamily.Deco;
import gearFamily.Talisman;
import gearFamily.Weapon;
import sorting.Wishlist;


public class BuildOptionsPanel extends JPanel{

	private UiAssembly uiA;
	
	//Design
	private FlowLayout fL;
	private Font optionsFont;
	private Dimension boxSize;
	
	
	//armorpool
	private JLabel armsLabel;
	private JComboBox armsCb;

	
	//WlChoice
	private JLabel wlLabel;
	private JComboBox<String> wlCb;
	private JTextComponent editor2;
	
	//buil confirm
	private JButton buildBtn;
	
	//Listener
	private Listener optionsListener;
	
	//Data for display of components
	private String[] armorpools;
	private String[] wListNames;
	private ArrayList<String> wArrayList;
	private ArrayList<Wishlist> wlArray;
	
	//Data for build
	private Armor[] armorPool;
	private Deco[] allDecos;
	private Talisman[] createdTalismans;
	private Weapon chosenWpn;
	private Wishlist buildWl;
	private ConfigurationPanel wlConfigPanel;
	private ConfigurationPanel talConfigPanel;
	private BuildDisplayPanel displayPanel;
	
	public BuildOptionsPanel(Font mhFont, String[] armorpools, UiAssembly uiA, ConfigurationPanel wlConfigPanel, ConfigurationPanel talConfigPanel, Weapon chosenWpn, BuildDisplayPanel displayPanel) {
		this.setUiA(uiA);
		this.wlConfigPanel = wlConfigPanel;
		this.talConfigPanel = talConfigPanel;
		this.chosenWpn = chosenWpn;
		this.armorpools = armorpools;
		this.setDisplayPanel(displayPanel);
		this.optionsFont = mhFont;
		this.boxSize = new Dimension(150, 15);
		this.wlArray = new ArrayList<Wishlist>();
		
		
		this.fL = new FlowLayout(FlowLayout.CENTER, 10, 0);
		this.setLayout(fL);
		this.setBackground(Color.DARK_GRAY);
		
		Dimension size = new Dimension(720,30);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
	
		this.optionsListener = new Listener(this);
		
		this.wlLabel = new JLabel("Wishlist choice");
		wlLabel.setForeground(Color.WHITE);
		wlLabel.setFont(optionsFont);
		wlLabel.setAlignmentX(LEFT_ALIGNMENT);
		wlLabel.setAlignmentY(CENTER_ALIGNMENT);
		this.add(wlLabel);
		this.setBorder(BorderFactory.createEtchedBorder());
		
		//Placeholder
		this.wListNames = new String[] {"a","b","c"};
		this.wArrayList = new ArrayList<String>();
		
		
		
		wlCb = new JComboBox<String>(wListNames);
//		wlCb.setModel(new MutableComboBoxModel(wArrayList.toArray()));
		MutableComboBoxModel mcb = new DefaultComboBoxModel(wListNames);
		wlCb.setModel(mcb);
		wlCb.setEditable(true);
		this.editor2 = (JTextComponent) wlCb.getEditor().getEditorComponent();
		this.editor2.setDocument(new AutoCompleteComboBox(wlCb));
		
		wlCb.getEditor().getEditorComponent().setBackground(Color.DARK_GRAY);
		wlCb.getEditor().getEditorComponent().setForeground(Color.WHITE);
		wlCb.getEditor().getEditorComponent().setFont(optionsFont);

		wlCb.setForeground(Color.BLACK);
		wlCb.setBackground(Color.WHITE);
		wlCb.setFont(optionsFont);
		wlCb.setMaximumSize(boxSize);
		wlCb.setPreferredSize(boxSize);
		wlCb.setAlignmentX(LEFT_ALIGNMENT);
		wlCb.setAlignmentY(CENTER_ALIGNMENT);
		
		wlCb.addActionListener(optionsListener);
		
		this.add(wlCb);
		
		buildBtn = new JButton ("Calculate Build");		
		buildBtn.setBackground(Color.DARK_GRAY);
		buildBtn.setForeground(Color.WHITE);
		buildBtn.setFont(optionsFont);
		buildBtn.setAlignmentX(LEFT_ALIGNMENT);
		buildBtn.setAlignmentY(CENTER_ALIGNMENT);
		this.add(buildBtn);
		buildBtn.addActionListener(optionsListener);
		
	}
	//methods
	public void addWlToCb(ArrayList<Wishlist> wishlists) {
		this.wlArray = wishlists;
		//this.wLists[1] = newWishlist.getWlName();
		
		this.wlCb.removeAllItems();
		for(Wishlist i : wlArray) {
			this.wlCb.addItem(i.getWlName());
		}
		this.wlCb.getEditor().setItem(wlArray.get(0).getWlName());
		
		//this.wlCb.revalidate();
		//this.wlCb.repaint();
		
		
	}
	public void clearWlCb() {
		this.wlArray.clear();
		
	}
	public Armor[] getArmorPool() {
		return armorPool;
	}
	public void setArmorPool(Armor[] armorPool) {
		this.armorPool = armorPool;
	}
	public Deco[] getAllDecos() {
		return allDecos;
	}
	public void setAllDecos(Deco[] allDecos) {
		this.allDecos = allDecos;
	}
	public Talisman[] getCreatedTalismans() {
		return createdTalismans;
	}
	public void setCreatedTalismans(Talisman[] createdTalismans) {
		this.createdTalismans = createdTalismans;
	}
	public Weapon getChosenWpn() {
		return chosenWpn;
	}
	public void setChosenWpn(Weapon chosenWpn) {
		this.chosenWpn = chosenWpn;
	}
	public Wishlist getBuildWl() {
		return buildWl;
	}
	public void setBuildWl(Wishlist buildWl) {
		this.buildWl = buildWl;
	}
	public UiAssembly getUiA() {
		return uiA;
	}
	public void setUiA(UiAssembly uiA) {
		this.uiA = uiA;
	}
	public ArrayList<Wishlist> getWlList() {
		return this.wlConfigPanel.getOverviewWishlists();
	}
	
	public Talisman[] getTalList() {
		Talisman[] talArray = new Talisman[this.talConfigPanel.getOverviewTalismans().size()];
		
		for(int i = 0; i < talArray.length; i++) {
			talArray[i] = this.talConfigPanel.getOverviewTalismans().get(i);
		}
		
		return talArray;
	}
	public JComboBox<String> getWlBox() {
		return this.wlCb;
	}
	
	public void setWlConfigPanel(ConfigurationPanel wlPanel) {
		this.wlConfigPanel = wlPanel;
	}
	public void setTalConfigPanel(ConfigurationPanel talPanel) {
		this.talConfigPanel = talPanel;
	}
	public BuildDisplayPanel getDisplayPanel() {
		return displayPanel;
	}
	public void setDisplayPanel(BuildDisplayPanel displayPanel) {
		this.displayPanel = displayPanel;
	}
}
