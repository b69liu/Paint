// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import java.awt.Dimension;
import javax.swing.BorderFactory;

class ColorPal extends JPanel implements Observer {
        static final long serialVersionUID=-2888888888888888888L;
	// the model that this view is showing
	private Model model;
        Color newcolor=null;
        public void adde(JButton b, Color c, Model m){
                //add Listener to buttons
                b.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){   //drag
                             if(e.getButton() == 1){
                                  model.color1=c;
                             }else if(e.getButton() == 3){
                                  model.color2=c;
                             }//endif
		             m.incrementCounter();
			};
                });//mouse motion listener end 
        }//end adde

	ColorPal(Model model_) {
		// create UI
		setBackground(Color.lightGray);
		setLayout(new GridLayout(3,2,getWidth()/10,getHeight()/10));
                setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

		// set the model
		model = model_;
		
		// setup the event to go to the "controller"
		// (this anonymous class is essentially the controller)		
		addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					model.incrementCounter();
				}
		});

                //add color button
                JButton cbutton1 = new JButton();
                cbutton1.setPreferredSize(new Dimension(15, 15));
                cbutton1.setBackground(Color.black);
                adde(cbutton1,Color.black,model);
                JButton cbutton2 = new JButton();
                cbutton2.setPreferredSize(new Dimension(15, 15));
                cbutton2.setBackground(Color.white);
                adde(cbutton2,Color.white,model);
                JButton cbutton3 = new JButton();
                cbutton3.setPreferredSize(new Dimension(15, 15));
                cbutton3.setBackground(Color.green);
                adde(cbutton3,Color.green,model);
                JButton cbutton4 = new JButton();
                cbutton4.setPreferredSize(new Dimension(15, 15));
                cbutton4.setBackground(Color.gray);
                adde(cbutton4,Color.gray,model);
                JButton cbutton5 = new JButton();
                cbutton5.setPreferredSize(new Dimension(15, 15));
                cbutton5.setBackground(Color.yellow);
                adde(cbutton5,Color.yellow,model);
                JButton cbutton6 = new JButton();
                cbutton6.setPreferredSize(new Dimension(15, 15));
                cbutton6.setBackground(Color.red);
                adde(cbutton6,Color.red,model);
                this.add(cbutton1);
                this.add(cbutton2);
                this.add(cbutton3);
                this.add(cbutton4);
                this.add(cbutton5);
                this.add(cbutton6);

         //add color chooser for color1
         JButton chosecolor= new JButton();
         chosecolor.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e){
                 newcolor=JColorChooser.showDialog(null,"ColorChooserL",newcolor);
                 if(newcolor != null){
                         model.color1=newcolor;
		         model.incrementCounter();
                 }//endif
             }
         });//end addact
         this.add(chosecolor);

         //add color chooser for color1
         JButton chosecolor2= new JButton();
         chosecolor2.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e){
                 newcolor=JColorChooser.showDialog(null,"ColorChooserR",newcolor);
                 if(newcolor != null){
                         model.color2=newcolor;
		         model.incrementCounter();
                 }//endif
             }
         });//end addact
         this.add(chosecolor2);


	}

	// Observer interface 
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("ColorPal: updateView");
	
	}
}
