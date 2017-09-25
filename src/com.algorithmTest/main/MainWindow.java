package com.algorithmTest.main;

import com.algorithmTest.main.algorithms.ProblemSet;
import com.algorithmTest.main.engine.Engine;
import com.algorithmTest.main.engine.ProblemBuilder;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by Maashes on 9/17/2017.
 */
public class MainWindow extends PApplet {

    private ArrayList<ProblemSet> problems = new ArrayList<>();
    private Engine engine = new Engine(this);

    public static void main(String[] args) {
        PApplet.main(MainWindow.class.getName());
    }

    public void settings()
    {
        size(810,500);
    }

    public void setup()
    {
        ProblemSet aStar = ProblemBuilder.BuildAStarProblem(this);
        engine.SetProblem(aStar);
        engine.RunProblem();
    }

    public void draw()
    {
        engine.Update();
        engine.Display();
    }
}
