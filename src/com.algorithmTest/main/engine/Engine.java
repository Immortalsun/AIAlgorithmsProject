package com.algorithmTest.main.engine;

import com.algorithmTest.main.agents.Agent;
import com.algorithmTest.main.engine.problemSets.ProblemSet;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by Maashes on 9/17/2017.
 */
public class Engine {

    private ArrayList<Agent> agents;
    private PApplet windowParent;
    private ProblemSet currentProblem;

    public Engine(PApplet parent){
        agents = new ArrayList<>();
        windowParent = parent;
    }

    public void SetProblem(ProblemSet problem){
        currentProblem = problem;
        agents = currentProblem.GetAgents();
    }

    public ProblemSet GetCurrentProblem(){
        return currentProblem;
    }

    public void RunProblem(){
        currentProblem.RunProblemSet();
    }

    public void Update(){
        if(!agents.isEmpty()){
            for(Agent a : agents){
                a.Update();
            }
        }
    }

    public void Display(){
        if(!agents.isEmpty()){
            for(Agent a : agents){
                a.Display();
            }
        }
    }

}
