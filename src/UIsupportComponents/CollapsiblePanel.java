package UIsupportComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;

import UI.Listener;
import UI.ConfigurationPanel;



public class CollapsiblePanel extends JPanel {

	private boolean selected;
    JPanel contentPanel;
    HeaderPanel headerPanel;
    Listener closeListener;
    ConfigurationPanel parent;
    //Split Content
    JPanel cHeader;
    JPanel cMain;
    JPanel cEnd;
    
    //drag Content
    MouseDragAdapter mda;
    
    public CollapsiblePanel(String text, Font panelFont, ConfigurationPanel parent) {
    	this.closeListener = new Listener(this);
    	
    	//this.customizeComp(this);
    	
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBackground(Color.DARK_GRAY);
        this.setForeground(Color.WHITE);
    	
        this.parent = parent;
        
    	this.cHeader = new JPanel();
    	this.cMain = new JPanel();
    	this.cEnd = new JPanel();
    	
        this.selected = true;
        headerPanel = new HeaderPanel(text, panelFont, this);
        //this.customizeComp(headerPanel);
        this.headerPanel.setAlignmentX(LEFT_ALIGNMENT);
		this.headerPanel.setAlignmentY(TOP_ALIGNMENT);       
        
        Border headerBorder = BorderFactory.createEtchedBorder();
        this.headerPanel.setBorder(headerBorder);
        
        contentPanel = new JPanel();
        this.customizeComp(contentPanel);
       
       
        //split content into cHeader, Content, and cEnd
		//Top
        
		this.customizeComp(cHeader);
		this.contentPanel.add(cHeader);
        
		//Middle
		this.customizeComp(cMain);
		this.contentPanel.add(cMain);
		
		
		this.mda = new MouseDragAdapter(panelFont, this);
		cMain.addMouseListener(mda);
		cMain.addMouseMotionListener(mda);
		//customise scrollpane
		JScrollPane mainPane = new JScrollPane(cMain, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Dimension dPane = new Dimension(10, Integer.MAX_VALUE);
		mainPane.getVerticalScrollBar().setPreferredSize(dPane);
		mainPane.getVerticalScrollBar().setMaximumSize(dPane);

		mainPane.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
		mainPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.BLACK;			
				this.thumbDarkShadowColor =Color.GRAY;
			}
		});
		
			
		
		this.contentPanel.add(mainPane);
		
		//Bottom
        this.customizeComp(cEnd);
		this.contentPanel.add(cEnd);
        
        
        this.add(headerPanel);
        this.add(contentPanel);
        contentPanel.setVisible(true);
    }
    
    
    //nested classes for header and content panels
    private class HeaderPanel extends JPanel implements MouseListener {
 
		private JSeparator sep;
    	JTextField nameField;
    	
    	
        //BufferedImage open, closed;
        //final int OFFSET = 30, PAD = 5;
 
        public HeaderPanel(String text, Font panelFont, CollapsiblePanel parent) {
            addMouseListener(this);
                        
            this.setBackground(Color.DARK_GRAY);
    		this.setAlignmentX(LEFT_ALIGNMENT);
    		this.setAlignmentY(TOP_ALIGNMENT);
    		Dimension d = new Dimension(265, 40);
    		this.setPreferredSize(d);
    		this.setMinimumSize(d);
    		this.setMaximumSize(d);
    		
    		
    		JLabel header = new JLabel(text, JLabel.LEFT);
    		header.setForeground(Color.WHITE);
    		header.setFont(panelFont);
    		header.setAlignmentX(LEFT_ALIGNMENT);
    		header.setAlignmentY(TOP_ALIGNMENT);
    		this.add(header);	
    		
    		nameField = new JTextField();
    		nameField.setFont(panelFont);
    		nameField.setBackground(Color.DARK_GRAY);
    		nameField.setForeground(Color.WHITE);
    		nameField.setCaretColor(Color.WHITE);
    		nameField.setAlignmentX(LEFT_ALIGNMENT);
    		nameField.setAlignmentY(TOP_ALIGNMENT);
    		Dimension dHeader = new Dimension(120, 25);
    		nameField.setPreferredSize(dHeader);
    		nameField.setMinimumSize(dHeader);
    		nameField.setMaximumSize(dHeader);
    		//nameField.addActionListener(wlListener);
    		this.add(nameField);
    		
    		
    		JButton close = new JButton("x");
    		close.addActionListener(parent.getCloseListener());
    		close.setFont(panelFont);
    		close.setBackground(Color.DARK_GRAY);
    		close.setForeground(Color.WHITE);
    		close.setAlignmentX(LEFT_ALIGNMENT);
    		close.setAlignmentY(TOP_ALIGNMENT);
    		
    		this.add(close);
    		
            //int w = getWidth();
            //int h = getHeight();
 
           
 
        }
 
        /*
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int h = getHeight();
            /*if (selected)
                g2.drawImage(open, PAD, 0, h, h, this);
            else
                g2.drawImage(closed, PAD, 0, h, h, this);
                         // Uncomment once you have your own images
            g2.setFont(font);
            FontRenderContext frc = g2.getFontRenderContext();
            LineMetrics lm = font.getLineMetrics(text_, frc);
            float height = lm.getAscent() + lm.getDescent();
            float x = OFFSET;
            float y = (h + height) / 2 - lm.getDescent();
            g2.drawString(text_, x, y);
        }
    	*/
 
        public void mouseClicked(MouseEvent e) {
            toggleSelection();
        }
 
        public void mouseEntered(MouseEvent e) {
        }
 
        public void mouseExited(MouseEvent e) {
        }
 
        public void mousePressed(MouseEvent e) {
        }
 
        public void mouseReleased(MouseEvent e) {
        }
 
        public JTextField getNameField() {
        	return this.nameField;
        }
    }
    public JPanel getCMain() {
    	return this.cMain;
    }
    public void addMainContent(JPanel newContent) {
    	this.cMain.add(newContent);
    }
    public void removeMainContent(JPanel oldContent) {
    	this.cMain.remove(oldContent);
    }
    public void addcHeader(JPanel newContent) {
    	this.cHeader.add(newContent);
    }
    public void addcEnd(JPanel newContent) {
    	this.cEnd.add(newContent);
    }
    public Listener getCloseListener() {
    	return this.closeListener;
    }
    public JTextField getNameField() {
    	return this.headerPanel.getNameField();
    }
    public void callResetPrios() {
    	//how to get adapter to call this?
    	this.parent.resetPrios(this);
    }
    
    
    public void toggleSelection() {
       
    	selected = !selected;
    	/*
        if (contentPanel.isShowing())
            contentPanel.setVisible(false);
        else
            contentPanel.setVisible(true);
        */
    	
    	this.parent.deselectCollapsiblePanels();
    	this.selectPanel();
    	
        validate();
 
        headerPanel.repaint();
    }
    public void deselectPanel() {
    	selected = false;
    	contentPanel.setVisible(selected);
    }
    public void selectPanel() {
    	//deselect all other panels first
    	//this.parent.deselectWlPanels();
    	selected = true;
    	contentPanel.setVisible(selected);
    }
    public boolean getSelected() {
    	return this.selected;
    }
    
    public void customizeComp(JPanel comp) {
    	comp.setBackground(Color.DARK_GRAY);
    	comp.setForeground(Color.WHITE);
    	comp.setAlignmentX(LEFT_ALIGNMENT);
    	comp.setAlignmentY(TOP_ALIGNMENT);
    	
    	comp.setLayout(new BoxLayout(comp, BoxLayout.Y_AXIS));
    	
    	Dimension d = new Dimension(230, Integer.MAX_VALUE);
    	comp.setMaximumSize(d);    
    	
    	comp.revalidate();
    }
}
