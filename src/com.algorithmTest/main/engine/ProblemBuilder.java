package com.algorithmTest.main.engine;

import com.algorithmTest.main.agents.Agent;
import com.algorithmTest.main.agents.EnvironmentalAgent;
import com.algorithmTest.main.algorithms.ProblemSet;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PConstants.ELLIPSE;

/**
 * Builds problem sets for use in the application
 * Created by Maashes on 9/17/2017.
 */
public class ProblemBuilder {

    public static ArrayList<ProblemSet> BuildAllProblems(PApplet sketchParent){
        ArrayList<ProblemSet> problems = new ArrayList<>();
        problems.add(BuildAStarProblem(sketchParent));

        return problems;
    }

    public static ProblemSet BuildAStarProblem(PApplet sketchParent){
        ProblemSet aStarProblem = new ProblemSet();
        ArrayList<Agent> aStarAgents = new ArrayList<>();
        for(int x = 10; x<400; x+=10){
            for(int y = 10; y<250; y+=10){
                EnvironmentalAgent nodeAgent = new EnvironmentalAgent(new PVector(x,y),10,10, sketchParent);
                nodeAgent.SetShape(sketchParent.createShape(ELLIPSE, x,y, 8,8),66,134,244);
                aStarAgents.add(nodeAgent);
            }
        }
        aStarProblem.SetAgents(aStarAgents);

        return aStarProblem;
    }

}
