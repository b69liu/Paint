// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.JScrollPane;

class View2 extends JPanel implements Observer {

	// the model that this view is showing
	private Model model;
        private int pos_x,pos_y;
        public Mycanvas drawp;

	View2(Model model_) {
		// create UI
		setBackground(Color.GRAY);
		//setLayout(new FlowLayout(FlowLayout.LEFT));
                this.setLayout(null);
		// set the model
		model = model_;
		
		// setup the event to go to the "controller"
		// (this anonymous class is essentially the controller)		
		addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					model.incrementCounter();
				}
		});
                
                //create the drawing panel
                drawp = new Mycanvas();
                if(model.freesize){
                drawp.setBounds(0, 0, getWidth(),getHeight());
                //drawp.setPreferredSize(new Dimension(getWidth(),getHeight()));
                }else{
                //drawp.setPreferredSize(new Dimension(400,500));
                drawp.setBounds(0, 0, 400,500);
                }
		drawp.setBackground(Color.white);
                drawp.setFocusable(true);
                this.add(drawp);




                //mouse listener
                drawp.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){    //press
                             model.oncav=true;
                             pos_x=e.getX()*10000/drawp.getWidth();
                             pos_y=e.getY()*10000/drawp.getHeight();
                             if(model.index!=model.recnum) model.recnum=model.index;
                             if(e.getButton() == 1){
                                  model.addrec(pos_x,pos_y,model.curtool,model.color1,model.thickness);
                             }else if(e.getButton() == 3){
                                  model.addrec(pos_x,pos_y,model.curtool,model.color2,model.thickness);
                             }//endif
                             ++model.index;
                             ++model.sldindex;
			      	model.incrementCounter();
			};



			public void mouseReleased(MouseEvent e){   //release
                             model.oncav=false;
                             if(model.recnum>0&&model.record[model.index-1].process){
                                 model.record[model.index-1].addpoint(pos_x,pos_y);
                             }//endif
                             model.record[model.index-1].process=false;

			      	repaint();
 //System.out.println("index:"+model.index+"  length:"+model.record[model.index-1].length); //for test
			};



		}); //mouse listener end

                drawp.addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e){   //drag
                             pos_x=e.getX()*10000/drawp.getWidth();
                             pos_y=e.getY()*10000/drawp.getHeight();
                             if(model.recnum>0&&model.record[model.index-1].process){
                                 model.record[model.index-1].addpoint(pos_x,pos_y);
                             }//endif
			      	repaint();
			};


                });//mouse motion listener end 




	}//endctor


                //adjust size of drawp
                public void chsize(){
                    if(model.freesize){
                           drawp.setBounds(0, 0, model.scp.getWidth()-15,model.scp.getHeight()-18);

                    }else{

                           drawp.setBounds(0, 0, 400,500);
                    }//endif
                }



	// Observer interface 
	@Override
	public void update(Observable o, Object arg) {

                //change the panel size
                 chsize();
                 repaint();
/*                if(model.freesize){
		System.out.println("FULL SCREEN");
                         drawp.setPreferredSize(new Dimension(getWidth(),getHeight()));
                }else{
		System.out.println("FIXED SCREEN");
                         drawp.setPreferredSize(new Dimension(400,500));
                }*/
		System.out.println("View2: updateView");
		// just displays an 'X' for each counter value
				
	}

    class Mycanvas extends JPanel{
         public Mycanvas(){}
         public void paintComponent(Graphics g) {
           super.paintComponent(g);
           Graphics2D g2 = (Graphics2D) g;             // cast to get 2D drawing methods



           for(int i=0;i< model.index/*!model.record[i].process*/;++i){
	       g2.setStroke(new BasicStroke(model.record[i].wid));
               g2.setColor(model.record[i].linecolor); 
               for(int j=0;j<model.record[i].getlen()-1&&model.record[i].getlen()>1;++j){
                   g2.drawLine(model.record[i].points[j].x*getWidth()/10000, model.record[i].points[j].y*getHeight()/10000,
                               model.record[i].points[j+1].x*getWidth()/10000, model.record[i].points[j+1].y*getHeight()/10000);
               }//endforj
           }//endfori


       }//endpaint
    }//endmyca
}
