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
        aStarProblem.Initialize(sketchParent);
        return aStarProblem;
    }

}
