
import java.awt.Rectangle;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Khanh Nguyen
 */
public class Physics {
 
    public static boolean collision(int x1,int y1, int w1,int h1,int x2,int y2, int w2,int h2 ){
        
        Rectangle rec1 = new Rectangle(x1,y1,w1,h1);
        Rectangle rec2 = new Rectangle(x2,y2,w2,h2);
        if (rec1.intersects(rec2)){
            return true;
        }
        
        return false;
    }
    
    
}
