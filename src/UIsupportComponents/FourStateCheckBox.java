package UIsupportComponents;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ActionMapUIResource;

import skills.Slot;

public class FourStateCheckBox extends JCheckBox{
	
	
	// the plan here is to reuse unused states of the JCheckBox ButtonModel to display icons and add functionality for the four possible Slot states
	
	public static class State{private State(){}}
	public static final State NOT_SELECTED = new State();
	public static final State LVL1 = new State();
	public static final State LVL2 = new State();
	public static final State LVL3 = new State();

	private FourStateDecorator model;
	
	public FourStateCheckBox(String text, Icon icon, State initial) {
		super(text, icon);
		super.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//grabFocus();
				model.nextState();
			}
		
		});
		//reset keyboard action map
		ActionMap map = new ActionMapUIResource();
		map.put("pressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				//grabFocus();
				model.nextState();
			}
		});
		map.put("released", null);
	    SwingUtilities.replaceUIActionMap(this, map);
	    // set the model to the adapted model
	    model = new FourStateDecorator(getModel());
	    setModel(model);
	    setState(initial);
	    
	    
	//    this.getDisabledIcon().
	}
	
	//constructors
	public FourStateCheckBox(String text) {
	    this(text, NOT_SELECTED);
	  }
	
	public FourStateCheckBox(String text, State initial) {
	    this(text, null, initial);
	  }
	
	public FourStateCheckBox() {
	    this(null);
	  }
	
	//no mouse listeners
	public void addMouseListener(MouseListener l) {
		
	}
	
	public void setState(State state) {
		model.setState(state);
	}
	public State getState() {
		return model.getState();
	}
	
	public String getStateSlotString (State myState) {
		if(this.isSelected() == false && this.isEnabled() == true) {
			return "NOT_SELECTED";
		}else if(this.isSelected() && this.isEnabled()) {
			return "LVL1";
		}else if(this.isSelected() == false && this.isEnabled()== false) {
			return "LVL2";
		}else {
			return "LVL3";
		}
	}
	public Slot getStateSlot() {
		switch (this.getStateSlotString(model.getState())) {
		case "NOT_SELECTED":
			return new Slot(0);
		case "LVL1":
			return new Slot(1);
		case "LVL2":
			return new Slot(2);
		case "LVL3":
			return new Slot(3);
		default:
			//to cause error
			System.out.println(model.getState().toString());
			return new Slot(4);
		}
	}
	 public void setSelected(boolean b) {
		    if (b) {
		     // setState(SELECTED);
		    } else {
		      setState(NOT_SELECTED);
		    }
		  }
	 
	private class FourStateDecorator implements ButtonModel{
		private ButtonModel other;
		
		private FourStateDecorator(ButtonModel other) {
			this.other = other;
		}
		
		public void setState(State state) {
			if (state == NOT_SELECTED) {
				//does other. make a difference here?
				other.setSelected(false);
				other.setEnabled(true);
				
			}else if(state == LVL1){
				other.setSelected(true);
				other.setEnabled(true);
			}else if(state== LVL2) {
				other.setSelected(false);
				other.setEnabled(false);
			}else if(state == LVL3) {
				other.setSelected(true);
				other.setEnabled(false);
			}
			
		}
		
		private State getState() {
			if(this.isSelected() == false && this.isEnabled() == true) {
				return NOT_SELECTED;
			}else if(this.isSelected() && this.isEnabled()) {
				return LVL1;
			}else if(this.isSelected() == false && this.isEnabled()== false) {
				return LVL2;
			}else {
				return LVL3;
			}
		}
		
		private void nextState() {
			State current = getState();
			if(current == NOT_SELECTED) {
				setState(LVL1);
			}else if(current == LVL1) {
				setState(LVL2);
			}else if(current == LVL2) {
				setState(LVL3);
			}else if(current == LVL3) {
				setState(NOT_SELECTED);
			}
		}
		
		/** Filter: No one may change the armed status except us. */
	    public void setArmed(boolean b) {
	    }
	    /** We disable focusing on the component when it is not
	     * enabled. */
	    public void setEnabled(boolean b) {
	      setFocusable(b);
	      other.setEnabled(b);
	    }
		
		public boolean isArmed() { return other.isArmed(); }
	    public boolean isSelected() { return other.isSelected(); }
	    public boolean isEnabled() { return other.isEnabled(); }
	    public boolean isPressed() { return other.isPressed(); }
	    public boolean isRollover() { return other.isRollover(); }
	    public void setSelected(boolean b) { other.setSelected(b); }
	    public void setPressed(boolean b) { other.setPressed(b); }
	    public void setRollover(boolean b) { other.setRollover(b); }
	    public void setMnemonic(int key) { other.setMnemonic(key); }
	    public int getMnemonic() { return other.getMnemonic(); }
	    public void setActionCommand(String s) {
	      other.setActionCommand(s);
	    }
	    
	      public String getActionCommand() {
	          return other.getActionCommand();
	        }
	        public void setGroup(ButtonGroup group) {
	          other.setGroup(group);
	        }
	        public void addActionListener(ActionListener l) {
	          other.addActionListener(l);
	        }
	        public void removeActionListener(ActionListener l) {
	          other.removeActionListener(l);
	        }
	        public void addItemListener(ItemListener l) {
	          other.addItemListener(l);
	        }
	        public void removeItemListener(ItemListener l) {
	          other.removeItemListener(l);
	        }
	        public void addChangeListener(ChangeListener l) {
	          other.addChangeListener(l);
	        }
	        public void removeChangeListener(ChangeListener l) {
	          other.removeChangeListener(l);
	        }
	        public Object[] getSelectedObjects() {
	          return other.getSelectedObjects();
	        }
	
		
	
	}
	
}
