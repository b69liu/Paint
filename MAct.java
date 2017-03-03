//this class is to record the mouse action
//one action is defined by press->drag->release
import java.awt.Color;



class MAct{
    public class Pos{
        int x,y;
        Pos(int x,int y){
          this.x=x;
          this.y=y;
        }
    }
     
    int length,wid;
    Pos points[]=new Pos[9999];
    char tool;
    Color linecolor;
    boolean process;  // if the current mouse action finish, true untill PressReleased
    MAct(char tool, Color linecolor,int wid){             //call when press
      length=0;
      process=true;
      this.wid=wid;
      this.tool=tool;
      this.linecolor=linecolor;
    }

    public void addpoint(int px,int py){   //add points to the array when drag
      if(length<10000){
          points[length]=new Pos(px,py);
          ++length;
      }
    }

    public int getlen(){
      return length;
    };
    
}
