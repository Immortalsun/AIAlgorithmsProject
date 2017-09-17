package com.algorithmTest.main.agents;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by Maashes on 9/17/2017.
 */
public class ActiveAgent extends Agent {

    private PVector startLocation;


    public ActiveAgent(String name, PVector location, float width, float height, PApplet parent) {
        super(name, location, width, height, parent);
    }
}
