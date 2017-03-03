// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import javax.swing.JSlider;
import javax.swing.event.*;

class Viewd extends JPanel implements Observer, Runnable {

	// the model that this view is showing
	private Model model;

        JSlider jsl;

	Viewd(Model model_) {
		// create UI
		setBackground(Color.WHITE);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		// set the model
		model = model_;
		
		// setup the event to go to the "controller"
		// (this anonymous class is essentially the controller)		
		addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		//			model.incrementCounter();
				}
		});

                JButton play= new JButton("Play");
                play.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e){
                              if(model.index==model.recnum){
                                 model.index=0;   
                                 model.sldindex=0; 
                              }//endif                  
                              model.playing=true;
                   }
               });//end addact
               this.add(play); 

                jsl= new JSlider(0,model.recnum,model.sldindex);
                jsl.setMajorTickSpacing(1);
                jsl.setPaintTicks(true);
                jsl.addChangeListener(new ChangeListener(){
                    public void stateChanged(ChangeEvent e) {
		          if((JSlider) e.getSource() == jsl && !model.oncav){
	                      model.index = jsl.getValue();
                              model.sldindex=model.index;
                              model.incrementCounter();
                          }//endif
	            }
                });
                this.add(jsl);

                JButton start= new JButton("Start");
                start.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e){
                      model.index=0;
                      model.sldindex=0;
                      model.incrementCounter();
                   }
               });//end addact
               this.add(start);                

                JButton end= new JButton("End");
                end.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e){
                      model.index=model.recnum;
                      model.sldindex=model.index;
                      model.incrementCounter();
                   }
               });//end addact
               this.add(end);   


	}



	// Observer interface 
	@Override
	public void update(Observable o, Object arg) {
                jsl.setMaximum(model.recnum);
                jsl.setValue(model.sldindex);
//		System.out.println("Viewd: updateView");
		// just displays an 'X' for each counter value
		
	}

        @Override
        public void run(){
          while(true){
            while(model.playing){
               if(model.index==model.recnum)break;
               ++model.index;
               ++model.sldindex;
               model.incrementCounter();
               try{
                 Thread.sleep(500);
               }catch(InterruptedException ex){
               }
            } //endwhile
             try{
                 Thread.sleep(50);
             }catch(InterruptedException ex){
             }
             if(model.index==model.recnum)model.playing=false;
          }//endwhileout
        };

}
