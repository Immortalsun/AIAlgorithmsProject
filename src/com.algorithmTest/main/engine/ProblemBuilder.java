package com.algorithmTest.main.engine;

import com.algorithmTest.main.agents.Agent;
import com.algorithmTest.main.agents.EnvironmentalAgent;
import com.algorithmTest.main.algorithms.AStar;
import com.algorithmTest.main.engine.problemSets.AStarProblemSet;
import com.algorithmTest.main.engine.problemSets.ProblemSet;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PConstants.CENTER;
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
        ProblemSet aStarProblem = new AStarProblemSet();
        ArrayList<Agent> aStarAgents = new ArrayList<>();
        AStar astarAlg = new AStar();
        int nodeXIndex = 0;
        int nodeYIndex = 0;
        sketchParent.ellipseMode(CENTER);
        for(int x = 10; x<600; x+=20){
            for(int y = 10; y<400; y+=20){
                EnvironmentalAgent nodeAgent = new EnvironmentalAgent(new PVector(x,y),10,10, sketchParent);
                PShape agentShape = sketchParent.createShape(ELLIPSE, 0,0,nodeAgent.GetWidth(),nodeAgent.GetHeight());
                nodeAgent.SetShape(agentShape,66,134,244);
                aStarAgents.add(nodeAgent);
                astarAlg.AddAgentNode(nodeAgent, nodeXIndex, nodeYIndex);
                nodeYIndex++;
            }
            nodeYIndex = 0;
            nodeXIndex++;
        }
        aStarProblem.SetAgents(aStarAgents);
        astarAlg.BuildNodeNieghbors();
        aStarProblem.SetAlgorithm(astarAlg);

        return aStarProblem;
    }

}
