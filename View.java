// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;	
import java.util.Observable;
import java.util.Observer;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.*;

class View extends JPanel implements Observer {

	// the view's main user interface
//	private JButton button; 
	
	// the model that this view is showing
	private Model model;
	
	View(Model model_) {
		// create the view UI
//		button = new JButton("?");
//		button.setMaximumSize(new Dimension(100, 50));
//		button.setPreferredSize(new Dimension(100, 50));
		// a GridBagLayout with default constraints centres
		// the widget in the window
		this.setLayout(new GridLayout(3, 0));
                this.setPreferredSize(new Dimension(100, 400));
		this.setMinimumSize(new Dimension(100, 400));
//		this.add(button, new GridBagConstraints());


		
		// set the model 
		model = model_;

                //add color
                ColorPal cp= new ColorPal(model);
                this.add(cp);
		cp.setMaximumSize(new Dimension(100, 600));
		cp.setMinimumSize(new Dimension(100, 600));
		model.addObserver(cp);
		
                //add color demo
                CDemo cdemo=new CDemo();
		cdemo.setBackground(Color.lightGray);
                this.add(cdemo);

                
                //add stroke
                Stroke st= new Stroke(model);
                this.add(st);
		st.setMaximumSize(new Dimension(100, 300));
		st.setMinimumSize(new Dimension(100, 300));
		model.addObserver(st);


		// setup the event to go to the "controller"
		// (this anonymous class is essentially the controller)
/*		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.incrementCounter();
			}
		});*/	
	} 

	// Observer interface 
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("View: update");
                repaint();
//		button.setText(Integer.toString(model.getCounterValue()));		
	}

    class CDemo extends JPanel{
         int p_x,p_y;
         Color[] colors={Color.red,Color.black,Color.black};
         public CDemo(){
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				p_x = e.getX();
				p_y = e.getY();
				testContainment();
				repaint();
			};

		}); //mouse listenner end
         }//endctor

	protected void testContainment(){
                //collide plat
		double d1 = Line2D.ptSegDist(10,40,70,40, p_x, p_y);
		if(d1 < 3){
                   choose(0);
                   model.thickness=3;
                };
		d1 = Line2D.ptSegDist(10,60,70,60, p_x, p_y);
		if(d1 < 6){
                   choose(1);
                   model.thickness=6;
                };
		d1 = Line2D.ptSegDist(10,80,70,80, p_x, p_y);
		if(d1 < 10){
                   choose(2);
                   model.thickness=10;
                };
         }//endtestc
         public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;             // cast to get 2D drawing methods
            g2.setColor(model.color2);
            g2.fillRect(11,11,15,15);
            g2.setColor(model.color1);
            g2.fillRect(4,4,15,15);
            g2.setColor(colors[0]);
	    g2.setStroke(new BasicStroke(3));
            g2.drawLine(10,40,70,40);
            g2.setColor(colors[1]);
	    g2.setStroke(new BasicStroke(6));
            g2.drawLine(10,60,70,60);
            g2.setColor(colors[2]);
	    g2.setStroke(new BasicStroke(10));
            g2.drawLine(10,80,70,80);
        }//endpaint
        
        //set the corresponding color red
        public void choose(int a){
            colors[0]=Color.black;
            colors[1]=Color.black;
            colors[2]=Color.black;
            colors[a]=Color.red;
        }
    }//endmyca
} 
