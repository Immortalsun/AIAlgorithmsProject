package com.algorithmTest.main.algorithms;

import com.algorithmTest.main.agents.Agent;
import com.algorithmTest.main.agents.EnvironmentalAgent;

import java.util.*;

/**
 * Created by Maashes on 9/18/2017.
 */
public class AStar extends Algorithm{

    private AStarNode start, end;
    private AStarNode[][] aStarGraphArray;
    private Set<AStarNode> evaluatedNodes;
    private Set<AStarNode> discoveredSet;
    private Map<AStarNode, AStarNode> cameFromMap;
    private Map<AStarNode, Score> fromStartScoreMap;
    private Map<AStarNode, Score> passThroughScoreMap;

    public AStar(){
        aStarGraphArray = new AStarNode[39][24];
        evaluatedNodes = new HashSet<>();
        discoveredSet = new HashSet<>();
        cameFromMap = new TreeMap<>();
        fromStartScoreMap = new TreeMap<>();
        passThroughScoreMap = new TreeMap<>();
    }

    public void SetStartAndGoalNodes(EnvironmentalAgent startNode, EnvironmentalAgent endNode){
        start = new AStarNode(startNode);
        start.SetIsStart(true);
        end = new AStarNode(endNode);
        end.SetIsEnd(true);
    }

    public void BuildNodeNieghbors(){
        boolean northEnabled, southEnabled, westEnabled, eastEnabled;
        northEnabled = southEnabled = westEnabled = eastEnabled = false;
        for(int x = 0; x<aStarGraphArray.length; x++){
            AStarNode[] innerArray = aStarGraphArray[x];
            for(int y=0; y<innerArray.length; y++){
                AStarNode current = innerArray[y];

                westEnabled = x-1 >= 0;
                eastEnabled = x+1 < aStarGraphArray.length;
                northEnabled = y-1 >= 0;
                southEnabled = y+1 < innerArray.length;
                if(northEnabled) {
                    current.AddNeightbor(aStarGraphArray[x][y-1]);
                    if(eastEnabled){
                        current.AddNeightbor(aStarGraphArray[x+1][y-1]);
                    }
                    if(westEnabled) {
                        current.AddNeightbor(aStarGraphArray[x-1][y-1]);
                    }
                }

                if(southEnabled){
                    current.AddNeightbor(aStarGraphArray[x][y+1]);
                    if(eastEnabled) {
                        current.AddNeightbor(aStarGraphArray[x+1][y+1]);
                    }
                    if(westEnabled){
                        current.AddNeightbor(aStarGraphArray[x-1][y+1]);
                    }
                }

                if(eastEnabled){
                    current.AddNeightbor(aStarGraphArray[x+1][y]);
                }
                if(westEnabled){
                    current.AddNeightbor(aStarGraphArray[x-1][y]);
                }
                northEnabled = southEnabled = westEnabled = eastEnabled = false;
            }
        }
    }

    public void AddAgentNode(EnvironmentalAgent agent, int xIndex, int yIndex){
        aStarGraphArray[xIndex][yIndex] = new AStarNode(agent);
    }

    public void Run(){
        fromStartScoreMap.put(start, new Score(0));
        passThroughScoreMap.put(start, EstimateCost(start, end));

        //run a star alg

    }

    private Score EstimateCost(AStarNode nodeA, AStarNode nodeB){
        double dist = Math.sqrt(
                (Math.pow((nodeB.GetXLocation() - nodeA.GetXLocation()),2) + Math.pow((nodeB.GetYLocation() - nodeA.GetYLocation()),2)));

        return new Score(dist);
    }

    private class AStarNode{

        private ArrayList<AStarNode> neightbors;
        private boolean isStart, isEnd, isAvailable, isVisited;
        private float xLocation, yLocation;

        public AStarNode(EnvironmentalAgent agent){
            neightbors = new ArrayList<>();
            xLocation = agent.GetLocation().x;
            yLocation = agent.GetLocation().y;
            isAvailable = true;
        }

        public boolean GetIsStart(){
            return isStart;
        }

        public boolean GetIsEnd(){
            return isEnd;
        }

        public boolean GetIsAvailable(){
            return isAvailable;
        }

        public boolean GetIsVisited(){
            return isVisited;
        }

        public ArrayList<AStarNode> GetNeightbors() {
            return neightbors;
        }

        public void AddNeightbor(AStarNode neighbor){
            neightbors.add(neighbor);
        }

        public float GetXLocation(){
            return xLocation;
        }

        public float GetYLocation(){
            return yLocation;
        }

        public void SetIsStart(boolean start){
            isStart = start;
        }

        public void SetIsEnd(boolean end){
            isEnd = end;
        }

        public void SetIsAvailable(boolean avaialable){
            isAvailable = avaialable;
        }

        public void SetIsVisited(boolean visited){
            isVisited = visited;
        }

    }

    private class Score{
        private double score;

        public Score(double s){
            score = s;
        }

        public double GetScore(){
            return score;
        }
    }

}
