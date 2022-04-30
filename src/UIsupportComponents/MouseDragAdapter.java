package UIsupportComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import UI.ConfigurationPanel;

//if an instance of this class is assigned to a container, its contents become draggable
public class MouseDragAdapter extends MouseAdapter {
	private final JWindow mouseWindow = new JWindow();
	private Component draggingComponent;
	private Component gap; //to make gaps when hovering between two panels
	
	//objects to calculate place of components
	//used to determine upper and lower halfs of components
	private final static Rectangle R1 = new Rectangle();
	private final static Rectangle R2 = new Rectangle();
	
	private static Rectangle prevRect;
	private int index = -1; //starting index value is -1 so as default methods return without moving stuff
	private Point startPt;
	private Point dragOffset;
	private final int gestureMotionThreshold = DragSource.getDragThreshold(); //min amount of movement to start dragging
	
	//for updating prio???
	CollapsiblePanel owner;
	
	public MouseDragAdapter(Font mhFont, CollapsiblePanel owner) {
		super();
		mouseWindow.setBackground(Color.DARK_GRAY);
		mouseWindow.setForeground(Color.white);
		mouseWindow.setFont(mhFont);

		this.owner = owner;
	
	}

	@Override public void mousePressed(MouseEvent e) {
		JComponent parent = (JComponent) e.getComponent();
		if (parent.getComponentCount() <= 1) {
			startPt = null;
			return;
		}
		startPt = e.getPoint();
	}
	
	//Methods for dragging stuff:
	
	private void startDragging(JComponent parent, Point pt) {
		//get dragging panel
		Component c = parent.getComponentAt(pt);
		index = parent.getComponentZOrder(c); //this returns the place of the component in parent
		if(Objects.equals(parent, c) || index < 0) { //if clicked component is actually the parent do nothing, dont know if index < 0 is possible outside of setting it to this value
			return;
		}
		draggingComponent = c;
		Dimension d = draggingComponent.getSize();
		
		Point dragPos = draggingComponent.getLocation();
		dragOffset = new Point(pt.x - dragPos.x, pt.y - dragPos.y);
		
		//make filler to be shifted under dragged component of same size
		gap = Box.createRigidArea(d);
		swapComponentLocation(parent, c, gap, index);
		
		//make cursor window
		mouseWindow.add(draggingComponent);
		mouseWindow.setSize(draggingComponent.getSize());
		//mouseWindow.pack();
		
		updateWindowLocation(pt, parent);
		mouseWindow.setVisible(true);
	}
	
	private void updateWindowLocation(Point pt, JComponent parent) {
		Point p = new Point(pt.x - dragOffset.x, pt.y -dragOffset.y);
		SwingUtilities.convertPointToScreen(p, parent);
		mouseWindow.setLocation(p);
	}
	
	private static void swapComponentLocation(Container parent, Component removedC, Component addedC, int i) {
		parent.remove(removedC);
		parent.add(addedC, i);
		parent.setSize(parent.getMaximumSize());
		parent.revalidate();
		parent.repaint();
	}
	
	//to switch gaps from one index to another, or swap the gap and the dragging panel
	//this method can be used inside a loop over all contents of the drag panel
	//it returns the index of a component AND sets the prevRect to be the half of the component, the passed pt is in
	private static int getTargetIndex(Rectangle r , Point pt, int i) {
		//the rectangle that is passed to this method has position and size of component with index i
		
		//upper or lower half of component?
		int ht2 = (int) (.5 + r.height *.5);
		R1.setBounds(r.x, r.y,       r.width, ht2);
		R2.setBounds(r.x, r.y + ht2, r.width, ht2);
		if (R1.contains(pt)) {
			prevRect = R1;
			//if i <= 1 return 0, else return i
			return i - 1 > 0 ? i : 0;
		} else if (R2.contains(pt)) {
			prevRect = R2;
			return i;			
		}
		return -1;
	}
	
	@Override public void mouseDragged(MouseEvent e) {
		Point pt = e.getPoint();
		JComponent parent = (JComponent) e.getComponent();
		
		//check, if distance surpasses MotionThreshold
		//startPt was set when mouse was pressed
		//Pytagoras for hypothenuse 
		double a = Math.pow(pt.x - startPt.x, 2);
		double b = Math.pow(pt.y - startPt.y, 2);
		if(draggingComponent == null && Math.sqrt(a + b) > gestureMotionThreshold) {
			startDragging(parent, pt);
			return;
		}
		if(!mouseWindow.isVisible() || draggingComponent == null) {
			return;
		}
		
		//update mouseWindows location
		updateWindowLocation(pt, parent);
		if (prevRect != null && prevRect.contains(pt)) {
			return;
		}
		
		//change gap location
		for (int i = 0; i < parent.getComponentCount(); i++) {
			Component c = parent.getComponent(i);
			Rectangle r = c.getBounds();
			if (Objects.equals(c, gap) && r.contains(pt)) {
				return;
			}
			int targetIndex = getTargetIndex(r, pt, i);
			if (targetIndex >= 0) {
				swapComponentLocation(parent, gap, gap, targetIndex);
				return;
			}
		}
		//if loop doesnt return, delete gap
		parent.remove(gap);
		parent.revalidate();
	}
	
	@Override public void mouseReleased(MouseEvent e) {
		startPt = null;
		if(!mouseWindow.isVisible() || draggingComponent == null) {
			return;
		}
		Point pt = e.getPoint();
		JComponent parent = (JComponent) e.getComponent();
		
		//close mouseWindow
		Component cmp = draggingComponent; //temp storage for panel
		draggingComponent = null; //bc this is reset
		prevRect = null;
		startPt = null;
		dragOffset = null;
		mouseWindow.setVisible(false);
		
		//swap dragged panel and gap
		for (int i = 0; i < parent.getComponentCount(); i++) {
			Component c = parent.getComponent(i);
			if(Objects.equals(c, gap)) {
				swapComponentLocation(parent, gap, cmp, i);
				this.owner.callResetPrios();

				return;
			}
			int target = getTargetIndex(c.getBounds(), pt, i);
			if (target >= 0) {
				//why change with gap? this should concern components other than gap
				swapComponentLocation(parent, gap, cmp, target);
				this.owner.callResetPrios();

				return;
			}
		}
		if (parent.getParent().getBounds().contains(pt)) {
			//if mouse position is outside of panel, put component at end
			swapComponentLocation(parent, gap, cmp, parent.getComponentCount());
			
		}else {
			//else make it original components ZOrder (inverted index)
			swapComponentLocation(parent, gap, cmp, index);
		}
		//TODO reset the counter for overviewSets
		this.owner.callResetPrios();
	}
}

/* 
 * Questions: 
 * what makes this class tick? Must be design of MouseAdapter
 * probably every time a mouse event happens inside of containers to which an object
 * of this class is assigned to, the overridden methods of this class are checked / executed 
 * 
*/