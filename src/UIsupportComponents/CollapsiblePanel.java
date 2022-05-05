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
import javax.swing.Box;
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
    private HeaderPanel headerPanel;
    Listener closeListener;
    ConfigurationPanel configParent;
    //Split Content
    private JPanel cHeader;
    JPanel cMain;
    JPanel cEnd;
    
    //drag Content
    MouseDragAdapter mda;
    
    public CollapsiblePanel(String text, Font panelFont, ConfigurationPanel configParent) {
    	this.closeListener = new Listener(this);
    	
    	//this.customizeComp(this);
    	
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBackground(Color.DARK_GRAY);
        this.setForeground(Color.WHITE);
    	this.setAlignmentY(TOP_ALIGNMENT);
    	this.setAlignmentX(LEFT_ALIGNMENT);
        this.configParent = configParent;
        
    	this.cHeader = new JPanel();
    	cHeader.setAlignmentY(TOP_ALIGNMENT);
    	this.cMain = new JPanel();
    	cMain.setAlignmentY(TOP_ALIGNMENT);
    	this.cEnd = new JPanel();
    	cEnd.setAlignmentY(TOP_ALIGNMENT);
    	
    	
        this.selected = true;
        setHeaderPanel(new HeaderPanel(text, panelFont, this));
        //this.customizeComp(headerPanel);
        this.getHeaderPanel().setAlignmentX(LEFT_ALIGNMENT);
		this.getHeaderPanel().setAlignmentY(TOP_ALIGNMENT);       
        
        Border headerBorder = BorderFactory.createEtchedBorder();
        this.getHeaderPanel().setBorder(headerBorder);
        
        contentPanel = new JPanel();
        this.customizeComp(contentPanel);
        Dimension d = new Dimension(this.configParent.getPreferredSize().width, 500);
        contentPanel.setPreferredSize(d);
        
        
       
        //split content into cHeader, Content, and cEnd
		//Top
        
        cHeader.setBackground(Color.DARK_GRAY);
        cHeader.setForeground(Color.WHITE);
        cHeader.setAlignmentX(LEFT_ALIGNMENT);
        cHeader.setAlignmentY(TOP_ALIGNMENT);
        cHeader.setLayout(new BoxLayout(cHeader, BoxLayout.Y_AXIS));
    	//this.customizeComp(cHeader);
		this.contentPanel.add(cHeader);
        
		//Middle
		this.customizeComp(cMain);
		this.contentPanel.add(cMain);
		
		
		this.mda = new MouseDragAdapter(panelFont, this);
		cMain.addMouseListener(mda);
		cMain.addMouseMotionListener(mda);
		//customise scrollpane
		JScrollPane mainPane = new JScrollPane(cMain, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//mainPane.setAlignmentY(TOP_ALIGNMENT);
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
        
        
        this.add(getHeaderPanel());
        this.add(contentPanel);
       
       /*
        Dimension d = new Dimension(this.configParent.getPreferredSize().width, Integer.MAX_VALUE);
    	this.setPreferredSize(d);
    	this.setMaximumSize(d);    
        */
        contentPanel.setVisible(true);
    }
    
    
    //nested classes for header and content panels
    public class HeaderPanel extends JPanel implements MouseListener {
 
		private JSeparator sep;
    	JTextField nameField;
    	
    	
        //BufferedImage open, closed;
        //final int OFFSET = 30, PAD = 5;
 
        public HeaderPanel(String text, Font panelFont, CollapsiblePanel parent) {
            addMouseListener(this);
                        
            this.setBackground(Color.DARK_GRAY);
    		this.setAlignmentX(LEFT_ALIGNMENT);
    		this.setAlignmentY(TOP_ALIGNMENT);    		
    		
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
    		//Dimension dHeader = new Dimension(configParent.getWidth(), 25);
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
    	return this.getHeaderPanel().getNameField();
    }
    public void callResetPrios() {
    	//how to get adapter to call this?
    	this.configParent.resetPrios(this);
    }
    
    
    public void toggleSelection() {
       
    	selected = !selected;
    	/*
        if (contentPanel.isShowing())
            contentPanel.setVisible(false);
        else
            contentPanel.setVisible(true);
        */
    	
    	this.configParent.deselectCollapsiblePanels();
    	this.selectPanel();
    	
        validate();
 
        getHeaderPanel().repaint();
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
    	//Dimension d = new Dimension(230, Integer.MAX_VALUE);
    	Dimension d = new Dimension(this.configParent.getPreferredSize().width, Integer.MAX_VALUE);
    	//comp.setPreferredSize(d);
    	comp.setMaximumSize(d);    
    	
    }
    
    
	public JPanel getcHeader() {
		return cHeader;
	}
	public void setcHeader(JPanel cHeader) {
		this.cHeader = cHeader;
	}
	public HeaderPanel getHeaderPanel() {
		return headerPanel;
	}
	public void setHeaderPanel(HeaderPanel headerPanel) {
		this.headerPanel = headerPanel;
	}
}
