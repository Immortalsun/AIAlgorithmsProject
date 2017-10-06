package com.algorithmTest.main.engine.problemSets;

import com.algorithmTest.main.agents.Agent;
import com.algorithmTest.main.agents.EnvironmentalAgent;
import com.algorithmTest.main.algorithms.AStar;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.ELLIPSE;

public class AStarProblemSet extends ProblemSet {

    private boolean isStartSet, isGoalSet;

    @Override
    public void Initialize(PApplet sketchParent) {
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
        this.SetAgents(aStarAgents);
        astarAlg.BuildNodeNieghbors();
        this.SetAlgorithm(astarAlg);
    }

    @Override
    public void Reset(PApplet sketchParent) {
        super.Reset(sketchParent);
        this.isGoalSet = false;
        this.isStartSet = false;
        this.Initialize(sketchParent);
    }

    @Override
    public void MouseClicked(float xCoord, float yCoord, boolean isLeftClick) {
        Agent clickedAgent = null;
        for(Agent e : this.GetAgents()){
            if(IsPointWithinCircle(xCoord, yCoord, e.GetLocation().x, e.GetLocation().y, e.GetHeight()/2)){
                clickedAgent = e;
            }
        }

        if(clickedAgent == null)
            return;

        if(isLeftClick){
            if(!isStartSet){
                clickedAgent.SetShapeColor(28,140, 14);
                SetStart((EnvironmentalAgent)clickedAgent);
            }
            else if(!isGoalSet){
                clickedAgent.SetShapeColor(140,14, 18);
                SetGoal((EnvironmentalAgent)clickedAgent);
            }
        }
        else{
            clickedAgent.SetShapeColor(145,89,5);
            SetNodeUnavailable((EnvironmentalAgent)clickedAgent);
        }
    }

    @Override
    public void SelectionApplied(float[] selectionRect){
        for(Agent e : this.GetAgents()){
            if(IsPointWithinRectangle(e.GetLocation().x, e.GetLocation().y, selectionRect)){
                e.SetShapeColor(145,89,5);
                SetNodeUnavailable((EnvironmentalAgent)e);
            }
        }
    }

    private void SetStart(EnvironmentalAgent node){
        AStar alg = (AStar)this.GetAlgorithm();
        alg.SetStartNode(node);
        isStartSet = true;
    }

    private void SetGoal(EnvironmentalAgent node){
        AStar alg = (AStar)this.GetAlgorithm();
        alg.SetGoalNode(node);
        isGoalSet = true;
        SetIsReady(true);
    }

    private void SetNodeUnavailable(EnvironmentalAgent node){
        AStar alg = (AStar)this.GetAlgorithm();
        alg.SetNodeUnavailable(node);
    }

    private boolean IsPointWithinCircle(float x, float y, float centerX, float centerY, float radius){
        return radius > Math.sqrt(Math.pow((x - centerX),2) + Math.pow((y - centerY),2));
    }

    private boolean IsPointWithinRectangle(float x, float y, float[] rectangleArray){
        float rectLeft = rectangleArray[0];
        float rectTop = rectangleArray[1];
        float rectRight = rectLeft + rectangleArray[2];
        float rectBottom = rectTop + rectangleArray[3];

        return ((rectLeft <= x) && (x <= rectRight)) && ((rectTop <= y) && (y <= rectBottom));
    }

    @Override
    public void RunProblemSet() {
        super.RunProblemSet();
        AStar alg = (AStar)this.GetAlgorithm();
        alg.Run();
        for(PVector p : alg.FinalRoute){
            EnvironmentalAgent routeAgent = GetAgentAtCoordinates(p);
            if(routeAgent != null)
                routeAgent.SetShapeColor(226,242,0);
        }
    }

    private EnvironmentalAgent GetAgentAtCoordinates(PVector p){
        for(Agent agent : this.GetAgents()){
            if(agent.GetLocation().x == p.x && agent.GetLocation().y == p.y){
                return (EnvironmentalAgent)agent;
            }
        }
        return null;
    }

    public boolean GetStartSet(){
        return isStartSet;
    }

    public boolean GetGoalSet(){
        return isGoalSet;
    }
}
