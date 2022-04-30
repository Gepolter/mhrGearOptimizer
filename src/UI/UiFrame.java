package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class UiFrame extends JFrame{

	
	private Listener uiListener;
	private GridBagLayout baseLayout;
	private final static Insets insets = new Insets(0, 0, 0, 0);


	public UiFrame (){
		
		this.setTitle("Gear Optimizer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		baseLayout = new GridBagLayout();
		this.setLayout(baseLayout);
		
		this.setSize(1100,600);
		this.setVisible(true);
		
		//btnListener test
		/*
		JPanel btnPanel = new JPanel();
		JButton btn = new JButton("+");
		uiListener = new btnListener();
		btn.addActionListener(uiListener);
		this.addComponent(btn, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);
		*/	
	}
	
	public void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 0.0, 0.0, anchor, fill, insets, 0,0);
		this.add(component, gbc);
	}
	
	public void addComponentHeavyY(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 0.0, 1.0, anchor, fill, insets, 0,0);
		this.add(component, gbc);
	}
	
	public void addComponentHeavyX(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 0.0, anchor, fill, insets, 0,0);
		this.add(component, gbc);
	}
	
	public void addComponentHeavyBoth(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0, anchor, fill, insets, 0,0);
		this.add(component, gbc);
	}
	
	public void addSepHor(Container container) {
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		container.add(sep);
	}
	
	public void addSepVert(Container container) {
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		container.add(sep);
	}
	
	public void addPlus(Container container) {
		JButton plusB = new JButton("+");
		container.add(plusB);
		plusB.addActionListener(this.uiListener);
	}
	
	public Listener getUiListener() {
		return this.uiListener;
	}
	public void setUiListener(Listener newBtnListener) {
		uiListener = newBtnListener;
	}
	
	public void packFrame() {
		this.pack();
	}
	/*
	private void createContainers(container ) {
		
		//addcomponents via classes
		
	}
	*/
	
}
