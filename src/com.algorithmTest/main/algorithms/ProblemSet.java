package com.algorithmTest.main.algorithms;

import com.algorithmTest.main.agents.Agent;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Describes a problem for the applioation to run.
 * Contains a collection of agents, both active and environmental
 * and a specifies behaviors for each
 * Created by Maashes on 9/17/2017.
 */
public class ProblemSet {

    private ArrayList<Agent> agents;
    private boolean isRunning;
    private String description;
    private Algorithm algorithm;

    public ProblemSet(ArrayList<Agent> actors, String description){
        this.description = description;
        agents = actors;
    }

    public ProblemSet(){
        agents = new ArrayList<>();
        this.description = "";
    }

    public String GetDescription(){
        return description;
    }


    public ArrayList<Agent> GetAgents(){
        return agents;
    }

    public boolean GetIsRunning(){
        return isRunning;
    }

    public void SetAgents(ArrayList<Agent> actors){
        agents.addAll(actors);
    }

    public void SetAlgorithm(Algorithm alg){
        algorithm = alg;
    }

    public void SetDescription(String description){
        this.description = description;
    }

    public void RunProblemSet(){
        isRunning = true;
    }

    public void StopProblemSet(){
        isRunning = false;
    }
}
