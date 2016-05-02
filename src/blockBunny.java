/**
 * Created by jared on 2/9/16.
 */
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.opengl.*;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import java.awt.Rectangle;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Random;

import background.*;
public class blockBunny {
    // Set this to true to force the game to exit.
    private static boolean shouldExit;

    // The previous frame's keyboard state.
    private static boolean kbPrevState[] = new boolean[256];

    // The current frame's keyboard state.
    private static boolean kbState[] = new boolean[256];

    // Position of the sprite.
    private static int[] spritePos = new int[] { 140, 335 };

    // Texture for the sprite.
    private static int spriteTex;

    // Size of the sprite.
    private static int[] spriteSize = new int[2];
    private static int[] tileSize = new int[2];
    // for project 
    // [x]40 [y]15   confuse column is x || row is y           ground 
    private static int[][] tiles = new int [][]{{1,1,1,1,1,1,1,1,1,1,1,1,2,3,3},
                                                {0,0,0,0,0,0,0,2,2,0,0,0,2,3,3},
                                                {0,0,0,0,0,0,0,2,3,0,0,0,2,3,3},
                                                {0,0,0,0,0,0,0,2,0,0,0,0,2,3,3},
                                                {0,0,0,0,0,0,0,2,2,0,0,0,2,3,3},
                                                {0,0,0,0,0,0,0,2,3,0,0,0,3,3,3},
                                                {0,0,0,0,0,0,0,0,0,0,0,0,0,2,3},
                                                {0,0,0,0,0,0,0,0,1,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,0,2,0,0,0,0,0,2,3},
                                                {0,0,0,0,0,0,0,2,2,0,0,0,2,2,3},
                                                {0,0,0,0,0,0,0,2,3,0,0,0,0,0,2},
                                                {0,0,0,0,0,0,0,2,0,0,0,0,1,0,2},
                                                {0,0,0,0,0,0,2,0,1,0,0,0,0,0,2},
                                                {0,0,0,0,0,0,2,0,2,0,0,0,2,0,2},
                                                {0,0,0,0,0,0,2,0,3,0,0,0,0,0,2},
                                                {0,0,0,0,2,2,0,0,0,0,0,0,0,1,3},
                                                {0,0,0,0,2,0,0,0,2,0,0,0,1,2,3},
                                                {0,0,0,0,2,2,0,0,0,0,2,0,2,2,3},
                                                {0,0,0,0,0,0,2,0,3,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,2,2,3},
                                                {0,0,0,0,0,0,2,0,2,0,0,0,1,2,3},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,0,2,3},
                                                {0,0,0,0,0,0,2,0,1,0,0,0,2,2,3},
                                                {0,0,0,0,0,0,0,0,0,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,0,0,2,0,0,0,1,2,3},
                                                {0,0,0,0,0,1,0,0,0,0,0,0,2,2,3},
                                                {0,0,0,0,0,1,0,0,3,0,0,0,3,0,2},
                                                {0,0,0,0,0,1,0,0,0,0,0,0,0,0,2},
                                                {0,0,0,0,0,0,3,0,1,0,0,0,2,0,2},
                                                {0,0,0,0,0,0,3,0,2,0,0,0,1,0,2},
                                                {0,0,0,0,2,2,2,0,3,0,0,0,3,0,2},  // 30
                                                {0,0,0,0,0,0,2,0,0,0,0,0,2,1,3},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,1,2,3},
                                                {0,0,0,0,0,0,2,0,1,0,2,0,2,2,3},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,3,0,0,0,0,0,0,2,3},
                                                {0,0,0,0,0,0,3,0,2,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,3,0,0,0,0,0,2,2,3},
                                                {0,0,0,0,0,0,3,0,2,0,0,0,1,2,3},                                               
                                                {0,0,0,0,0,0,3,0,3,0,0,0,0,2,3},   //40 level 1
                                                {0,0,0,0,1,1,1,1,1,1,1,1,2,3,3},
                                                {0,0,0,0,0,0,0,3,2,0,0,0,2,3,3},
                                                {0,0,0,0,0,0,0,3,3,0,0,0,2,3,3},
                                                {0,0,0,0,0,0,0,3,0,0,0,0,2,3,3},
                                                {0,0,0,0,0,0,0,0,2,0,0,0,2,3,3},
                                                {0,0,0,0,0,0,2,0,3,0,0,0,3,3,3},
                                                {0,0,0,0,0,0,0,0,0,0,0,0,0,2,3},
                                                {0,0,0,0,0,1,0,0,1,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,0,2,0,0,0,0,0,2,3},
                                                {0,0,0,0,3,0,0,2,2,0,0,0,2,2,3},
                                                {0,0,0,0,0,0,0,2,3,0,0,0,0,0,2},
                                                {0,0,0,0,0,0,0,2,0,0,0,0,1,0,2},
                                                {0,0,0,0,0,1,2,0,0,0,0,0,0,0,2},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,2,0,2},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,0,0,2},
                                                {0,0,0,0,2,2,0,0,0,0,0,0,0,1,3},
                                                {0,0,0,0,2,0,0,0,2,0,0,0,1,2,3},
                                                {0,0,0,0,2,2,0,0,0,0,2,0,2,2,3},
                                                {0,0,0,0,0,0,2,0,3,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,2,2,3},
                                                {0,0,0,0,0,0,2,0,2,0,0,0,1,2,3},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,0,2,3},
                                                {0,0,0,0,0,0,2,0,1,0,0,0,2,2,3},
                                                {0,0,0,0,0,0,0,0,0,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,0,0,2,0,0,0,1,2,3},
                                                {0,0,0,0,0,1,0,0,0,0,0,0,2,2,3},
                                                {0,0,0,0,0,1,0,0,3,0,0,0,3,0,2},
                                                {0,0,0,0,0,1,0,0,0,0,0,0,0,0,2},
                                                {0,0,0,0,0,0,3,0,1,0,0,0,2,0,2},
                                                {0,0,0,0,0,0,3,0,2,0,0,0,1,0,2},
                                                {0,0,0,0,2,2,2,0,3,0,0,0,3,0,2},  // 30
                                                {0,3,0,0,0,0,2,0,0,0,0,0,2,1,3},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,1,2,3},
                                                {0,2,0,0,0,0,2,0,1,0,2,0,2,2,3},
                                                {0,0,0,0,0,0,2,0,0,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,3,0,0,0,0,0,0,2,3},
                                                {0,2,0,0,0,0,3,0,2,0,0,0,3,2,3},
                                                {0,0,0,0,0,0,3,0,0,0,0,0,2,2,3},
                                                {0,0,0,0,0,0,3,0,2,0,0,0,1,2,3},                                               
                                                {0,2,0,0,0,0,3,0,3,0,0,0,0,2,3},
                                                
                                                   
    };
    public static void main(String[] args) {
        GLProfile gl2Profile;

        try {
            // Make sure we have a recent version of OpenGL
            gl2Profile = GLProfile.get(GLProfile.GL2);
        }
        catch (GLException ex) {
            System.out.println("OpenGL max supported version is too low.");
            System.exit(1);
            return;
        }

        // Create the window and OpenGL context.
        GLWindow window = GLWindow.create(new GLCapabilities(gl2Profile));
        window.setSize(640, 480);
        window.setTitle("Java Framework");
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                kbState[keyEvent.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                kbState[keyEvent.getKeyCode()] = false;
            }
        });

        // Setup OpenGL state.
        window.getContext().makeCurrent();
        GL2 gl = window.getGL().getGL2();
        gl.glViewport(0, 0, 640, 480);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glOrtho(0, 640, 480, 0, 0, 100);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        // load top left corner change color tile
        int red = glTexImageTGAFile(gl, "redTile.tga", spriteSize);
        int green = glTexImageTGAFile(gl, "greenTile.tga", spriteSize);
        int blue = glTexImageTGAFile(gl, "blueTile.tga", spriteSize);
        int selectedTile=red,switchCount=0;
        boolean time=true;
        long timecount=0;
        int solid=2;
        // Load the texture.
        spriteTex = glTexImageTGAFile(gl, "horse1.tga", spriteSize);
        BackGroundDef background = new BackGroundDef(gl,tileSize);
        int camerax=27,cameray=27;
        int gravity = 0;
        int test=0;
        boolean jumping=false, falling=false;
        int wsx =spritePos[0],wsy = spritePos[1];
        // animation sprite
        AnimationDef rightDef = new AnimationDef(gl,spriteSize,"horse");
        AnimationData right = new AnimationData(rightDef);
        
        
        int count = 0;
        double yVelocity = 0.2;
        int curFrameMs;
        int physicsDeltaMs = 10;
        int lastPhysicsFrameMs=0;
        long lastFrameNS;
        long curFrameNS = System.nanoTime();
        System.out.println(" max integer" + Integer.MAX_VALUE);
        System.out.println(curFrameNS/1000000+ " curFrameNS max integer" + (int)((curFrameNS/1000000) % Integer.MAX_VALUE));
        // The game loop
        while (!shouldExit) {
            System.arraycopy(kbState, 0, kbPrevState, 0, kbState.length);
            lastFrameNS = curFrameNS;
          //  lastPhysicsFrameMs = (int)curFrameNS/1000000;
            // Actually, this runs the entire OS message pump.
            window.display();
            if (!window.isVisible()) {
                shouldExit = true;
                break;
            }
            // get input for switching tile color
                if (kbState[KeyEvent.VK_C] & time){
                    switchCount +=1;
                    time = false;
                    timecount = lastFrameNS;
                    System.out.println("testing for pressing ZZZZZZZZZZZZZZZ:"+ switchCount);
                    int check = switchCount % 3;
                    if (check == 0){
                        selectedTile = red;
                        solid = 2;
                    }
                    if (check == 1){
                        selectedTile = green;
                        solid = 3;
                    }
                    if (check == 2){
                        selectedTile = blue; 
                        solid =1;
                    }
                }
                
                int checktimecount = (int) (curFrameNS - timecount)/1000000;
                System.out.println("tesiung for khankhkdshkjakdjkfjkajdkf "+checktimecount+" timecount :" +timecount);
                if ( checktimecount> 200 ){
                    time = true;
                    timecount = curFrameNS;
                }
     //       System.out.println("curframe milisecond"+ curFrameNS/1000000);
  /*          
            if (count ==10){
                count = 0;
                curFrameNS = System.nanoTime();
                int deltaTimeMS = (int) (curFrameNS - lastFrameNS) / 1000000;
                System.out.println("delta time milisecond "+ deltaTimeMS +" frame per second "+deltaTimeMS/10);
                lastFrameNS = curFrameNS;
            }
            count +=1;
   */         
            curFrameNS = System.nanoTime();
            int deltaTimeMS = (int) (curFrameNS - lastFrameNS) / 1000000;
            System.out.println("delta time MS  "+ deltaTimeMS);
            // Physics update
            do {
                // get input for jumping
                if (kbState[KeyEvent.VK_X]){
                    if ( jumping == false&& falling ==false){
                        jumping = true;
                   //     System.out.println("testing gravity and jumping "+gravity + jumping);
                    }
                }
                
                
                // 1. Physics movement
                if (wsx<3800){  // 1900
                wsx +=0.2 * physicsDeltaMs;
                spritePos[0] = wsx - camerax;
                //animated update
                right.update(deltaTimeMS);
                spriteTex = right.getImageFrame();
            }
           
            if (camerax<3800){
                camerax +=0.2 * physicsDeltaMs;
                spritePos[0] = wsx - camerax;
            }
            else {
                camerax=0;
                wsx = 140;
            }
       // jumping        
            if (jumping && yVelocity==0){
                yVelocity = -.7;
                System.out.println("inside the jumping If: "+yVelocity);
            }
            
            wsy += yVelocity * physicsDeltaMs;
            yVelocity += 1.9 * physicsDeltaMs / 1000.0;
            System.out.println("Outside the jumping If: "+yVelocity);

            spritePos[1]= wsy - cameray;
             
            
                // 2. Physics collision detection   1 1 2 3
                    
                    if (tiles [(wsx)/64][(wsy+spriteSize[1]-5)/64]==solid||tiles [(wsx+spriteSize[0])/64][(wsy+spriteSize[1]-5)/64]==solid || tiles [(wsx+spriteSize[0])/64][(wsy+spriteSize[1]-5)/64]==solid||tiles [wsx/64][(wsy+spriteSize[1]-5)/64]==solid){
                        int yTileIndex = (wsy+spriteSize[1]-5)/64;
                        int xTileIndex = (wsx+spriteSize[0])/64;
                        // check for left sprite collision with the background tile
                        if ((wsx+spriteSize[0])> (xTileIndex*64)){
                            // check middle collision
                            if (wsy>(yTileIndex*64)&(wsy+spriteSize[1])<(yTileIndex*64 + 64)){
                                // reset camera and sprite to the begining position
                                camerax=27;
                                cameray=27;
                                wsx=140;wsy=335;
                                spritePos[0]= 140;
                                spritePos[1]= 335;
                            }
                            // check lower right corner collision
                            if ((wsy+spriteSize[1]-20)>(yTileIndex*64)& (wsy+spriteSize[1])<(yTileIndex*64+64)){
                                // reset camera and sprite to the begining position
                                camerax=27;
                                cameray=27;
                                wsx=140;wsy=335;
                                spritePos[0]= 140;
                                spritePos[1]= 335;
                            }
                            
                        }
                        if (yVelocity!=0){
                            wsy -= yVelocity * physicsDeltaMs;
                            spritePos[1] = wsy -cameray;
                        }
                        yVelocity = 0;
                        jumping = false;
                        
                      //  int fix = (wsy+spriteSize[1]-5)/64;
                      //  wsy = fix*64-spriteSize[1];
                     
                      //  spritePos[1] = wsy -cameray;
                    }
                   // check if sprite fall down, restart at the beginning position 
                    if (wsy > 600){
                        camerax=27;
                                cameray=27;
                                wsx=140;wsy=335;
                                spritePos[0]= 140;
                                spritePos[1]= 335;
                    }
                    
          /*              wsy -=3;
                spritePos[1] = wsy -cameray;
                    }
                    else{
                        yVelocity = 1;
                        
                    }
            */    
                // 3. Physics collision resolution
                
                lastPhysicsFrameMs += physicsDeltaMs;
                
          //      System.out.println("lastphysiccsFrame mili "+ lastPhysicsFrameMs+ " compare to "+(int)(curFrameNS/1000000));
            } while (lastPhysicsFrameMs + physicsDeltaMs < deltaTimeMS );
            lastPhysicsFrameMs = 0;
     //     System.out.println("out of the loop ------------------------ ");
            // Game logic.
            if (kbState[KeyEvent.VK_ESCAPE]) {
                shouldExit = true;
            }

            if (kbState[KeyEvent.VK_LEFT]) {
                wsx -=3;
                spritePos[0] = wsx-camerax;
          //      camerax -= 5;
            }

            if (kbState[KeyEvent.VK_RIGHT]) {
                wsx +=3;
                spritePos[0] = wsx -camerax;
           //     camerax +=2;
            }

            if (kbState[KeyEvent.VK_UP]) {
                wsy -=3;
                spritePos[1] = wsy-cameray;
            }

            if (kbState[KeyEvent.VK_DOWN]) {
                wsy +=3;
        //        cameray +=3;
                spritePos[1] = wsy -cameray;
            }
            if (kbState[KeyEvent.VK_SPACE]){
                if ( jumping == false&& falling ==false){
                    gravity = 18;
                   // spritePos[1] -= (int)gravity;
                    jumping = true;
               //     System.out.println("testing gravity and jumping "+gravity + jumping);
                }
            }
            
   /*         
            if (jumping){
                gravity -=1;
                wsy -=gravity+1;
                spritePos[1] =wsy-cameray;
                if (gravity <= 0){
                    jumping = false;
                    falling = true;
         //           System.out.println("testing false  jumping "+gravity + jumping);
                }
            }
            if (falling){
                gravity +=1;
                wsy +=gravity;
                spritePos[1] = wsy -cameray;
                if (gravity >= 18){
                  //  jumping = true;
                    falling = false;
            //        System.out.println("testing true  jumping "+gravity + jumping);
                }
                     
            }
    */        
     /*       
            if (wsx<1900){
                wsx +=6;
                spritePos[0] += wsx - camerax;
            }
           
            if (camerax<1900){
                camerax +=6;
                spritePos[0] = wsx - camerax;
            }
            else {
                camerax=0;
                wsx = 140;
            }
        */     
      //      wsy +=yVelocity;
      //      spritePos[1] += yVelocity;
            
     //       if (cameray<445)
       //         cameray += 1;
            
            gl.glClearColor(0, 0, 0, 1);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
            
            //draw background  11  8
            for (int i= (int)Math.floor(camerax/64) ;i<(int)Math.floor(camerax/64)+11;i++){
             //   System.out.println("teesting in side loop" + (int)Math.floor(camerax/64));
                for (int j=(int)Math.floor(cameray/64) ; j<(int)Math.floor(cameray/64)+8;j++){
                    glDrawSprite(gl, background.bgtiles[tiles[i][j]], i*64-camerax, j*64-cameray, 64,64);
               //     System.out.println("teesting in side loop" + (int)Math.floor(cameray/64));
                }
                    
            }
        //    System.out.println("sprite x "+ spritePos[0]+ " sprite y "+spritePos[1]);
       //     System.out.println(wsx/64+" "+wsy/64 +"worl sprite x "+ wsx+ "world  sprite y "+wsy+" tile x y"+tiles[wsx/64][wsy/64]);
           // glDrawSprite(gl, spriteTex, spritePos[0], spritePos[1], spriteSize[0], spriteSize[1]);
            glDrawSprite(gl, spriteTex, spritePos[0], spritePos[1], spriteSize[0], spriteSize[1]);
            
            glDrawSprite(gl, selectedTile, 30, 30, 55, 55);
            System.out.println("testing for pressing end while loop    ZZZZZZZZZZZZZZZ:"+ switchCount);
            // Present to the player.
        //    window.swapBuffers();
        }
        System.exit(0);
    }

    // Load a file into an OpenGL texture and return that texture.
    public static int glTexImageTGAFile(GL2 gl, String filename, int[] out_size) {
        final int BPP = 4;

        DataInputStream file = null;
        try {
            // Open the file.
            file = new DataInputStream(new FileInputStream(filename));
        } catch (FileNotFoundException ex) {
            System.err.format("File: %s -- Could not open for reading.", filename);
            return 0;
        }

        try {
            // Skip first two bytes of data we don't need.
            file.skipBytes(2);

            // Read in the image type.  For our purposes the image type
            // should be either a 2 or a 3.
            int imageTypeCode = file.readByte();
            if (imageTypeCode != 2 && imageTypeCode != 3) {
                file.close();
                System.err.format("File: %s -- Unsupported TGA type: %d", filename, imageTypeCode);
                return 0;
            }

            // Skip 9 bytes of data we don't need.
            file.skipBytes(9);

            int imageWidth = Short.reverseBytes(file.readShort());
            int imageHeight = Short.reverseBytes(file.readShort());
            int bitCount = file.readByte();
            file.skipBytes(1);

            // Allocate space for the image data and read it in.
            byte[] bytes = new byte[imageWidth * imageHeight * BPP];

            // Read in data.
            if (bitCount == 32) {
                for (int it = 0; it < imageWidth * imageHeight; ++it) {
                    bytes[it * BPP + 0] = file.readByte();
                    bytes[it * BPP + 1] = file.readByte();
                    bytes[it * BPP + 2] = file.readByte();
                    bytes[it * BPP + 3] = file.readByte();
                }
            } else {
                for (int it = 0; it < imageWidth * imageHeight; ++it) {
                    bytes[it * BPP + 0] = file.readByte();
                    bytes[it * BPP + 1] = file.readByte();
                    bytes[it * BPP + 2] = file.readByte();
                    bytes[it * BPP + 3] = -1;
                }
            }

            file.close();

            // Load into OpenGL
            int[] texArray = new int[1];
            gl.glGenTextures(1, texArray, 0);
            int tex = texArray[0];
            gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
            gl.glTexImage2D(
                    GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, imageWidth, imageHeight, 0,
                    GL2.GL_BGRA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(bytes));
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

            out_size[0] = imageWidth;
            out_size[1] = imageHeight;
            return tex;
        }
        catch (IOException ex) {
            System.err.format("File: %s -- Unexpected end of file.", filename);
            return 0;
        }
    }

    public static void glDrawSprite(GL2 gl, int tex, int x, int y, int w, int h) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
        gl.glBegin(GL2.GL_QUADS);
        {
            gl.glColor3ub((byte)-1, (byte)-1, (byte)-1);
            gl.glTexCoord2f(0, 1);
            gl.glVertex2i(x, y);
            gl.glTexCoord2f(1, 1);
            gl.glVertex2i(x + w, y);
            gl.glTexCoord2f(1, 0);
            gl.glVertex2i(x + w, y + h);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2i(x, y + h);
        }
        gl.glEnd();
    }
}
