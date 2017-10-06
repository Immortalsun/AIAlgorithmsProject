package com.algorithmTest.main;

import com.algorithmTest.main.engine.problemSets.ProblemSet;
import com.algorithmTest.main.engine.Engine;
import com.algorithmTest.main.engine.ProblemBuilder;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.ArrayList;

/**
 * Created by Maashes on 9/17/2017.
 */
public class MainWindow extends PApplet {

    private Engine engine = new Engine(this);
    private boolean _problemComplete, _problemReady, _dragging;

    private float mouseStartX, mouseStartY;

    public static void main(String[] args) {
        PApplet.main(MainWindow.class.getName());
    }

    public void settings()
    {
        size(800,600);
    }

    public void setup()
    {
        engine.BuildProblems();
        textSize(18);
        fill(102,153, 51);
    }

    public void draw()
    {
        background(205, 208, 214);
        engine.Update();
        engine.Display();

        if(engine.GetCurrentProblem().GetIsReady() &&
                !engine.GetCurrentProblem().GetIsRunning()){
            fill(176,34, 201);
            rect(400, 400,80, 30);
            fill(255,255,255);
            text("RUN",415,420);
        }
        else if(_problemComplete){
            fill(176,54, 241);
            rect(400, 400,80, 30);
            fill(255,255,255);
            text("RESET",415,420);
        }
        drawOnDrag();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        engine.GetCurrentProblem().MouseClicked(mouseX,mouseY,mouseButton == LEFT);
        _problemReady = engine.GetCurrentProblem().GetIsReady();
        if(mouseX >= 400 && mouseX < 480
                && mouseY >= 400 && mouseY < 430
                && _problemReady){
            if(!_problemComplete){
                engine.RunProblem();
                _problemComplete = true;
            }

            else{
                engine.ResetProblem();
                _problemComplete = false;
            }
        }
    }

    @Override
    public void mousePressed() {
        mouseStartX = mouseX;
        mouseStartY = mouseY;
    }

    @Override
    public void mouseDragged() {
        _dragging = true;
    }

    @Override
    public void mouseReleased() {
        float[] selectionRect = new float[4];
        float xCoord = Math.min(mouseX, mouseStartX);
        float yCoord = Math.min(mouseY, mouseStartY);

        selectionRect[0] = xCoord;
        selectionRect[1] = yCoord;
        selectionRect[2] = Math.abs(mouseX-mouseStartX);
        selectionRect[3] = Math.abs(mouseY-mouseStartY);
        mouseStartX = 0;
        mouseStartY = 0;
        _dragging = false;
        engine.GetCurrentProblem().SelectionApplied(selectionRect);
    }

    private void drawOnDrag(){
        if(_dragging){
            noFill();
            stroke(0,0,0);
            rect(mouseStartX, mouseStartY, mouseX-mouseStartX,mouseY-mouseStartY);
        }
    }
}
