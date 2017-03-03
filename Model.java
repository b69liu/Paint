import java.util.Observable;
import java.awt.Color;
import javax.swing.JScrollPane;
// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

public class Model extends Observable {	
	// the data in the model, just a counter
	private int counter;
        int recnum;	//the number of records
        int index;      //the pointer to current action
        Color color1 = Color.BLACK;
        Color color2 = Color.WHITE; 
	MAct record[]=new MAct[999];   //record draws
        char curtool;
        int  thickness;
        int sldindex;
        boolean freesize;//true when full screen
        boolean playing; //true when play animation
        boolean oncav;   //this is to determine if click on slider
        JScrollPane scp;

	Model() {
		setChanged();
                recnum=0;
                index=0;
                curtool='s';
                thickness=3;
                sldindex=0;
                freesize=false;
                playing=false;
                oncav=false;
	}

        public void addrec(int px,int py,char t, Color c,int wid){   //call when listen pressing
               if(recnum>=0&&recnum<1000){
                  record[recnum]=new MAct(t,c,wid);
                  record[recnum].addpoint(px,py);
                  ++recnum;
               }//endofif


        }
	
	public int getCounterValue() {
		return counter;
	}
	
	public void incrementCounter() {
//		if (counter < 5) {
			++counter;
//			System.out.println("Model: increment counter to " + counter);
			setChanged();
			notifyObservers();
//		}
	} 	
}





