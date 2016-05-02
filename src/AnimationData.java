

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
public class AnimationData {
    AnimationDef def;
    int curFrame;
    int secsUntilNextFrame;
    
    public AnimationData(AnimationDef animation){
        def = animation;
        curFrame = 1;
        secsUntilNextFrame = 100;
    }
    void update(int deltaTime){
        secsUntilNextFrame = secsUntilNextFrame - deltaTime;
        if (secsUntilNextFrame < 0){
            secsUntilNextFrame = 100 +secsUntilNextFrame;
            curFrame = curFrame+1;
            if(curFrame==5){
                curFrame = 1;
            }
        }  
        
    }
    void draw(int x, int y){
        
    }
    
    // get int of image to draw
    public int getImageFrame(){
        if (curFrame==1)
            return def.frames[0].image;
        else if (curFrame==2)
            return def.frames[1].image;
        else if (curFrame==3)
            return def.frames[2].image;
        else
            return def.frames[3].image;
    }
    
    
}
