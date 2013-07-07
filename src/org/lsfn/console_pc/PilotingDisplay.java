package org.lsfn.console_pc;

import java.nio.FloatBuffer;

import org.lsfn.console_pc.STS.STSup;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;

public class PilotingDisplay extends Thread {
    
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;
    private static final String WINDOW_TITLE = "LSFN";
    
    private boolean[] keyStates; 
    private boolean[] keyStatesChanged;
    private StarshipConnection starshipConnection;
    
    private int vaoId, vboId, vertexCount;
    
    
    private enum DisplayState {
        MENU,
        LOBBY,
        PILOTING
    }
    private DisplayState displayState;
    
    public PilotingDisplay(StarshipConnection starshipConnection) {
        this.starshipConnection = starshipConnection;
        this.displayState = DisplayState.MENU;
        this.keyStates = new boolean[6];
        for(int i = 0; i < this.keyStates.length; i++) {
            this.keyStates[i] = false;
        }
        this.keyStatesChanged = new boolean[6];
        for(int i = 0; i < this.keyStatesChanged.length; i++) {
            this.keyStatesChanged[i] = false;
        }
    }
    
    private void keyboardInput() {
        while(Keyboard.next()) {
            if(Keyboard.getEventKey() == Keyboard.KEY_Q) {
                this.keyStates[0] = Keyboard.getEventKeyState();
                this.keyStatesChanged[0] = true;
            } else if(Keyboard.getEventKey() == Keyboard.KEY_E) {
                this.keyStates[1] = Keyboard.getEventKeyState();
                this.keyStatesChanged[1] = true;
            } else if(Keyboard.getEventKey() == Keyboard.KEY_A) {
                this.keyStates[2] = Keyboard.getEventKeyState();
                this.keyStatesChanged[2] = true;
            } else if(Keyboard.getEventKey() == Keyboard.KEY_D) {
                this.keyStates[3] = Keyboard.getEventKeyState();
                this.keyStatesChanged[3] = true;
            } else if(Keyboard.getEventKey() == Keyboard.KEY_W) {
                this.keyStates[4] = Keyboard.getEventKeyState();
                this.keyStatesChanged[4] = true;
            } else if(Keyboard.getEventKey() == Keyboard.KEY_S) {
                this.keyStates[5] = Keyboard.getEventKeyState();
                this.keyStatesChanged[5] = true;
            }
        }
    }
    
    private void mouseInput() {

    }
    
    private void dispatchStarshipMessages() {
        if(this.starshipConnection.getConnectionStatus() == StarshipConnection.ConnectionStatus.CONNECTED) {
            // Work out if any states have changed
            boolean aKeyStateChanged = false;
            for(int i = 0; i < this.keyStatesChanged.length; i++) {
                aKeyStateChanged |= this.keyStatesChanged[i];
            }
            
            // If some state has changed, prepare the appropriate message
            // more clauses to go in this if statement, it is not redundant
            if(aKeyStateChanged) {
                STSup.Builder stsUp = STSup.newBuilder();
                if(aKeyStateChanged) {
                    STSup.Piloting.Builder stsUpPiloting = STSup.Piloting.newBuilder();
                    if(this.keyStatesChanged[0]) {
                        stsUpPiloting.setTurnAnti(this.keyStatesChanged[0]);
                        this.keyStatesChanged[0] = false;
                    }
                    if(this.keyStatesChanged[1]) {
                        stsUpPiloting.setTurnClock(this.keyStatesChanged[1]);
                        this.keyStatesChanged[1] = false;
                    }
                    if(this.keyStatesChanged[2]) {
                        stsUpPiloting.setThrustLeft(this.keyStatesChanged[2]);
                        this.keyStatesChanged[2] = false;
                    }
                    if(this.keyStatesChanged[3]) {
                        stsUpPiloting.setThrustRight(this.keyStatesChanged[3]);
                        this.keyStatesChanged[3] = false;
                    }
                    if(this.keyStatesChanged[4]) {
                        stsUpPiloting.setThrustForward(this.keyStatesChanged[4]);
                        this.keyStatesChanged[4] = false;
                    }
                    if(this.keyStatesChanged[5]) {
                        stsUpPiloting.setThrustBackward(this.keyStatesChanged[5]);
                        this.keyStatesChanged[5] = false;
                    }
                    stsUp.setPiloting(stsUpPiloting);
                }
                
                this.starshipConnection.sendMessageToStarship(stsUp.build());
            }
        }
    }
    
    private void processStarshipData() {
        // TODO Auto-generated method stub
        
    }
   
    public void setupOpenGL() {
        // Setup an OpenGL context with API version 3.2
        try {
            /*
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
            */
            
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle(WINDOW_TITLE);
            Display.create();
            
            GL11.glViewport(0, 0, WIDTH, HEIGHT);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
        
        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        
        this.exitOnGLError("Error in setupOpenGL");
    }
    
    public void setupQuad() {       
        // OpenGL expects vertices to be defined counter clockwise by default
        float[] vertices = {
                // Left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                // Right top triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };
        // Sending data to OpenGL requires the usage of (flipped) byte buffers
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();
        
        vertexCount = 6;
        
        // Create a new Vertex Array Object in memory and select it (bind)
        // A VAO can have up to 16 attributes (VBO's) assigned to it by default
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        
        // Create a new Vertex Buffer Object in memory and select it (bind)
        // A VBO is a collection of Vectors which in this case resemble the location of each vertex.
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        // Put the VBO in the attributes list at index 0
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        // Deselect (bind to 0) the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);
        
        this.exitOnGLError("Error in setupQuad");
    }
    
    public void loopCycle() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        
        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
        
        // Put everything back to default (deselect)
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        
        this.exitOnGLError("Error in loopCycle");
    }
    
    public void destroyOpenGL() {       
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);
        
        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);
        
        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
        
        Display.destroy();
    }
    
    public void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();
        
        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = "<insert error here>";
            System.err.println("ERROR - " + errorMessage + ": " + errorString);
            
            if (Display.isCreated()) Display.destroy();
            System.exit(-1);
        }
    }
    
    @Override
    public void run() {
        System.out.println("Setting up openGL...");
        setupOpenGL();
        System.out.println("Setting up quad...");
        setupQuad();
        System.out.println("Looping...");
        while(!Display.isCloseRequested()) {
            
            // Update internal variables from input
            //keyboardInput();
            //mouseInput();
            
            // Send commands to Starship
            //dispatchStarshipMessages();
            
            // Receive and display data from Starship
            //processStarshipData();
            
            // Draw all of the things
            loopCycle();
            
            Display.sync(60);
            Display.update();
        }
        System.out.println("Destroying openGL stuff...");
        destroyOpenGL();
        
    }


}
