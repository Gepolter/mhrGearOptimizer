package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextField;

import UI.TalFrame.TResult;
import UI.TalFrame.TSelectionSet;
import UI.ConfigurationPanel.OverviewSet;
import UI.ConfigurationPanel.SelectionObjSet2;
import UIsupportComponents.CollapsiblePanel;
import interfaces.IMethods;
import skills.Skill;
import sorting.Build;

public class Listener implements ActionListener {
	
	private UiAssembly ui;
	private WpnTalPanel wtPanel;
	private ConfigurationPanel configPanel;
	private SelectionObjSet2 sos2Panel;
	private JComboBox cb;
	private JButton btn;
	private JComboBox<?> cbgeneric;
	private TSelectionSet tss;
	private TalFrame tFrame;
	private JTextField tField;
	private TResult tRes;
	private BuildOptionsPanel bOP;
	private OverviewSet ovSet;
	private CollapsiblePanel cPanel;
	
	//the problem here is passing relevant data to and from a certain ui part to the central listener. next time better put listeners as inner classes into ui parts?
	public Listener(UiAssembly ui) {
		this.ui = ui;
		//TODO confirm WL
	}
	
	public Listener(WpnTalPanel wtPanel) {
		this.wtPanel = wtPanel;
	}
	
	
	public Listener(TalFrame tFrame) {
		this.tFrame = tFrame;
	}
	
	public Listener(TSelectionSet tss) {
		this.tss =tss;
	}
	
	public Listener(TResult tRes) {
		this.tRes = tRes;
	}
	
	public Listener(BuildOptionsPanel bOP) {
		this.bOP = bOP;
	}
///////////////77
	public Listener(ConfigurationPanel configPanel) {
		this.configPanel = configPanel;
	}
	
	public Listener(SelectionObjSet2 sos2Panel) {
		this.sos2Panel = sos2Panel;
	}

	public Listener(OverviewSet ovSet) {
		this.ovSet = ovSet;
	}

	public Listener(CollapsiblePanel cPanel) {
		this.cPanel = cPanel;
	}

	////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() instanceof JButton) {
				
			JButton btn = (JButton)(e.getSource());
			Component source = (Component)e.getSource();
			
			switch(btn.getText()) {
				//list all Buttons
				case "confirm":
					System.out.println("confirm");
					
					if(source.getParent() != null && source.getParent().getParent() instanceof SelectionObjSet2) {
						
						System.out.println(source.getClass().getName());
						System.out.println(source.getParent().getClass().getName());
						System.out.println(source.getParent().getParent().getClass().getName());	
						// hier bis SelectionObjSet2
						
						System.out.println(source.getParent().getParent().getParent().getClass().getName());
						System.out.println(source.getParent().getParent().getParent().getParent().getClass().getName());
						System.out.println(source.getParent().getParent().getParent().getParent().getParent().getClass().getName());
						System.out.println(source.getParent().getParent().getParent().getParent().getParent().getParent().getClass().getName());
						System.out.println(source.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getClass().getName());
						// hier bis JFrame
						
						
						//add new ovSet and save array of data
						//for loop through wlStack to find selected CollapsiblePanel
						for(int i = 0; i < this.sos2Panel.getConfigPanel().getCollapsiblePanelList().size(); i++) {
							if(this.sos2Panel.getConfigPanel().getCollapsiblePanelList().get(i).getSelected() == true) {
								CollapsiblePanel activePanel = this.sos2Panel.getConfigPanel().getCollapsiblePanelList().get(i);
								this.sos2Panel.getConfigPanel().newOverviewSet(sos2Panel.getCbString(), sos2Panel.getSlValue(), activePanel);	
							}
						}
						this.sos2Panel.getConfigPanel().revalidate();
						this.sos2Panel.getConfigPanel().repaint();
						


						
					}
					
					if(source.getParent() != null && source.getParent().getParent() instanceof TalFrame) {
						//functionality: add string from textfield to memory for creation of result set
						
						String tName = new String();
						tName = this.tField.getText();
					}
					if(source.getParent() != null && source.getParent().getParent() instanceof TSelectionSet) {
						//functionality: add next selection set
					}
					
					
					break;
					
				case "clear" :
					System.out.println("clear");
					
					if(source.getParent() != null && source.getParent().getParent() instanceof TalFrame) {
						//functionality
					}
					if(source.getParent() != null && source.getParent().getParent() instanceof TSelectionSet) {
						//functionality
					}
					if(source.getParent() != null && source.getParent() instanceof OverviewSet) {
						//functionality
						//remove JPanel
						//this.configPanel.removeOverviewSet((OverviewSet)source.getParent());
						//OverviewSet removeSet = (OverviewSet)source.getParent();
						this.ovSet.getOwner().removeOverviewSet(this.ovSet);
						//remove from arraylist
						
					}
					
					break;
					
				case "add Talisman":
					System.out.println("TODO tal frame");
					//System.out.println((WpnTalPanel)source.getParent());
					this.wtPanel.newTalFrame();
					break;
					
				case "create Talisman":
					System.out.println("create Talisman");

					if(source.getParent() != null && source.getParent().getParent() instanceof TalFrame) {
						//new result set
						//add 1 to result counter
					}
					break;
					
				case "delete":
					System.out.println("delete");
					
					if(source.getParent() != null && source.getParent().getParent() instanceof TResult) {
						//delete result set
						//subtract 1 from result counter
					}
					break;
				
				case "x":
					System.out.println("x");
					//remove panel, remove panel from stack
					if(source.getParent() != null && source.getParent().getParent() instanceof CollapsiblePanel) {
						//this.cPanel.
					}
					
					if(source.getParent() != null && source.getParent() instanceof OverviewSet) {
						//functionality
						//remove JPanel
						this.ovSet.getOwner().removeOverviewSet(this.ovSet);
						//remove from arraylist
						
					}
					
					break;
					
				case "Update Wishlists":
					System.out.println("addWL");
					
					if(source.getParent() != null && source.getParent() instanceof ConfigurationPanel) {
						//update wishlist objects defined by wl window
						
						//loop throug wl stack. If textfield isn't empty, create wl Obj. 
						//these Objects get overwritten each time update wl btn is pressed
						//this.configPanel.getBop().
						//this.configPanel.getBop().clearWlCb();
						
						this.configPanel.getBop().addWlToCb(this.configPanel.getOverviewWishlists());
						
						boolean emptyWlExists = false;
						for(int i = 0; i < this.configPanel.getCollapsiblePanelList().size(); i++) {

							int z = this.configPanel.getCollapsiblePanelList().size();
							if(this.configPanel.getCollapsiblePanelList().get(i).getNameField().getText().isEmpty()) {
								emptyWlExists = true;
							}else {
								//? messagebox "name all given wishlists"
							}
							
						}
						if (emptyWlExists == false) {
							this.configPanel.newWlPanel();							
						}
					}
					break;
					
				case "Update Talismans":
					System.out.println("addTal");
					
					if(source.getParent() != null && source.getParent() instanceof ConfigurationPanel) {
						
						
						boolean emptyTalExists = false;
						for(int i = 0; i < this.configPanel.getCollapsiblePanelList().size(); i++) {

							System.out.println(this.configPanel.getCollapsiblePanelList().get(i).getNameField().getText());
							int z = this.configPanel.getCollapsiblePanelList().size();
							if(this.configPanel.getCollapsiblePanelList().get(i).getNameField().getText().isEmpty()) {
								emptyTalExists = true;
							}else {
								//? messagebox "name all talismans"
							}	
						}
						if (emptyTalExists == false) {
							this.configPanel.newTalPanel();							
						}
					}
					break;
				
				////////////////////////////////////////////////////////////////////////////////////////////
				case "Calculate Build":
					System.out.println("calculate Build");
					//get wishlistData
					Build newBuild = null;
					for(int i = 0;i < this.bOP.getWlList().size();i++) {
						if(this.bOP.getWlList().get(i).getWlName().equals((String) this.bOP.getWlBox().getSelectedItem()) == true) {
							newBuild = IMethods.buildAlgorithm(this.bOP.getUiA().getAllArmor(), this.bOP.getUiA().getAllDeco(), this.bOP.getTalList(), this.bOP.getChosenWpn(), this.bOP.getWlList().get(i));	
						}
					}
					
					//display results in buildPanel
					this.bOP.getDisplayPanel().displayBuild(newBuild);
					//this.bOP.getParentFrame().pack();
					break;
				////////////////////////////////////////////////////////////////////////////////////////////	
				default:
					System.out.println("unknown source");
			}		
		}
		
		if(e.getSource()instanceof JComboBox) {
			JComboBox<?> cb = (JComboBox<?>)(e.getSource());
			
			Component source = (Component)e.getSource();
			
			if(source.getParent() != null && source.getParent() instanceof SelectionObjSet2) {
				
				for(int i = 0;i < this.sos2Panel.getAllSkill().length; i++) {
					//compare all Skills to Combobox 
					if(this.sos2Panel.getAllSkill()[i].getSkillName() == cb.getSelectedItem()) {
						this.sos2Panel.resetSlider(this.sos2Panel.getslider(), this.sos2Panel.getAllSkill()[i].getMaxLvl());
					}
				}
				
			}
			
			if(source.getParent() != null && source.getParent() instanceof BuildOptionsPanel) {
				//if statements for different comboboxes
				//if(cb == this.bOP.get)
				for(int i = 0; i < this.bOP.getWlList().size(); i++) {
					if (this.bOP.getWlList().get(i).getWlName() == cb.getSelectedItem()) {
						this.bOP.setBuildWl(this.bOP.getWlList().get(i));
					}
				}
				
				this.bOP.setBuildWl(null);
			}
			
			
			if(source.getParent() != null && source.getParent() instanceof WpnTalPanel) {
				//TODO select wpn from array for build
				
				System.out.println(cb.getSelectedItem());
			}
			
		}	
		
		
	}
}
