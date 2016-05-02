
import com.jogamp.opengl.GL2;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Khanh Nguyen
 */
public class AnimationDef {
    
    public String name;
    public FrameDef[] frames = new FrameDef[4];
    public AnimationDef(GL2 gl, int[] size, String name){
        
        // load all animated frame
        frames[0] = new FrameDef(gl,100,size,name+"1.tga");
        frames[1] = new FrameDef(gl,100,size,name+"2.tga");
        frames[2] = new FrameDef (gl,100,size,name+"3.tga");
        frames[3] = new FrameDef (gl,100,size,name+"4.tga");  
      
    }
    
    

}
