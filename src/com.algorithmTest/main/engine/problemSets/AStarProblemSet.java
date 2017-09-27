package com.algorithmTest.main.engine.problemSets;

import com.algorithmTest.main.agents.Agent;
import com.algorithmTest.main.agents.EnvironmentalAgent;
import com.algorithmTest.main.algorithms.AStar;
import processing.core.PVector;

public class AStarProblemSet extends ProblemSet {

    private boolean isStartSet, isGoalSet;

    @Override
    public void MouseClicked(float xCoord, float yCoord, boolean isLeftClick) {
        if(isLeftClick){
            for(Agent e : this.GetAgents()){
                if(IsPointWithinCircle(xCoord, yCoord, e.GetLocation().x, e.GetLocation().y, e.GetHeight()/2)){
                    if(!isStartSet){
                        e.SetShapeColor(28,140, 14);
                        SetStart((EnvironmentalAgent)e);
                    }
                    else if(!isGoalSet){
                        e.SetShapeColor(140,14, 18);
                        SetGoal((EnvironmentalAgent)e);
                    }
                }
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

    private boolean IsPointWithinCircle(float x, float y, float centerX, float centerY, float radius){
        return radius > Math.sqrt(Math.pow((x - centerX),2) + Math.pow((y - centerY),2));
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
