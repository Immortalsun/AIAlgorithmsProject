package com.algorithmTest.main.agents;

import com.algorithmTest.main.behaviors.Behavior;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

/**
 * Created by Maashes on 9/17/2017.
 */
public class Agent {

    private PVector location, velocity;
    private float width, height;
    private PShape agentShape;
    private String name;
    private Behavior behavior;
    private PApplet parent;

    public Agent(String name, PVector location, float width, float height, PApplet parent){
        this.name = name;
        this.location = location;
        this.velocity = new PVector(0,0);
        this.width = width;
        this.height= height;
        this.parent = parent;
    }

    public PVector GetLocation(){
        return location;
    }

    public PVector GetVelocity()
    {
        return velocity;
    }

    public float GetWidth(){
        return width;
    }

    public float GetHeight(){
        return height;
    }

    public String GetName(){
        return name;
    }

    public Behavior GetBehavior(){
        return behavior;
    }

    public void SetLocation(PVector loc){
        location = loc;
    }

    public void SetVelocity(PVector vel){
        velocity = vel;
    }

    public void SetWidth(float w){
        width = w;
    }

    public void SetHeight(float h){
        height = h;
    }

    public void SetShape(PShape shape, float rColor, float gColor, float bColor){
        agentShape = shape;
        agentShape.setFill(true);
        agentShape.setFill(parent.color(rColor,gColor,bColor));
    }

    public void SetBehavior(Behavior behave){
        behavior = behave;
    }

    public void Update(){
        location.add(velocity);
    }

    public void Display(){
        if(agentShape != null){
            parent.shape(agentShape, location.x, location.y);
        }
    }

}
