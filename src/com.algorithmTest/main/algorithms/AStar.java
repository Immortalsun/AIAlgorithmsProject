package com.algorithmTest.main.algorithms;

import com.algorithmTest.main.agents.Agent;
import com.algorithmTest.main.agents.EnvironmentalAgent;
import processing.core.PVector;

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
    public ArrayList<PVector> FinalRoute;

    public AStar(){
        aStarGraphArray = new AStarNode[30][20];
        evaluatedNodes = new HashSet<>();
        discoveredSet = new HashSet<>();
        cameFromMap = new HashMap<>();
        fromStartScoreMap = new HashMap<>();
        passThroughScoreMap = new HashMap<>();
    }

    public void SetStartNode(EnvironmentalAgent startNode){
        int nodeX = (int)(startNode.GetLocation().x/20);
        int nodeY = (int)(startNode.GetLocation().y/20);
        if(nodeX < 0)
            nodeX = 0;
        if(nodeY < 0)
            nodeY =0;
        start = aStarGraphArray[nodeX][nodeY];
        start.SetIsStart(true);
    }

    public void SetGoalNode(EnvironmentalAgent endNode){
        int nodeX = (int)(endNode.GetLocation().x/20);
        int nodeY = (int)(endNode.GetLocation().y/20);
        end = aStarGraphArray[nodeX][nodeY];
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

    @Override
    public void SetupAndRun() {

    }

    public void Run(){
        discoveredSet.add(start);
        fromStartScoreMap.put(start, new Score(0));
        passThroughScoreMap.put(start, new Score(DistanceBetween(start, end)));

        //run a star alg
        while(!discoveredSet.isEmpty()){
            AStarNode current = FindLowestPassThroughNode();
            if(current.equals(end))
            {
                ReconstructPath(current);
                return;
            }

            discoveredSet.remove(current);
            evaluatedNodes.add(current);

            for(AStarNode neighbor : current.neightbors){
                if(evaluatedNodes.contains(neighbor))
                    continue;

                if(!discoveredSet.contains(neighbor))
                    discoveredSet.add(neighbor);

                double currentScore = fromStartScoreMap.get(current).GetScore();
                double tentativeFromStartScore = currentScore
                        + DistanceBetween(current, neighbor);

                if(!fromStartScoreMap.containsKey(neighbor)
                        || fromStartScoreMap.get(neighbor).GetScore() < tentativeFromStartScore){
                    cameFromMap.put(neighbor, current);
                    fromStartScoreMap.put(neighbor, new Score(tentativeFromStartScore));
                    passThroughScoreMap.put(neighbor, new Score((tentativeFromStartScore + DistanceBetween(neighbor, end))));
                }
            }
        }
    }

    private AStarNode FindLowestPassThroughNode(){
        double lowestScore = Double.MAX_VALUE;
        AStarNode lowestNode = null;
        for(AStarNode node : discoveredSet){
            if(passThroughScoreMap.get(node).GetScore() < lowestScore){
                lowestScore = passThroughScoreMap.get(node).GetScore();
                lowestNode = node;
            }
        }
        return lowestNode;
    }

    private void ReconstructPath(AStarNode current){
        ArrayList<PVector> totalPath = new ArrayList<>();
        totalPath.add(new PVector(current.GetXLocation(), current.GetYLocation()));
        while(cameFromMap.getOrDefault(current, null) != null){
            current = cameFromMap.get(current);
            totalPath.add(new PVector(current.GetXLocation(), current.GetYLocation()));
        }
        FinalRoute = totalPath;
    }

    private double DistanceBetween(AStarNode nodeA, AStarNode nodeB){
        double dist = Math.sqrt(
                (Math.pow((nodeB.GetXLocation() - nodeA.GetXLocation()),2) + Math.pow((nodeB.GetYLocation() - nodeA.GetYLocation()),2)));
        return dist;
    }

    private class AStarNode implements Comparable<AStarNode>{

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

        @Override
        public int compareTo(AStarNode o) {
            if(xLocation > o.xLocation)
                return 1;

            if(xLocation < o.xLocation)
                return -1;

            if(xLocation == o.xLocation &&
                    yLocation == o.yLocation)
                return 0;

            return 1;
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
