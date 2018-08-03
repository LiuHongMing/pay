package com.github.tiger.test.guava;

import com.google.common.graph.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DFS 深度优先搜索（Depth-First-Search）
 *
 * BFS 广度优先搜索（Breadth-First-Search）
 *
 * @author liuhongming
 */
public class GraphTest {

    @Test()
    public void testDijkstraSolver() {
        MutableValueGraph<String, Integer> graph = DijkstraSolver.buildGraph();
        DijkstraSolver dijkstraSolve = new DijkstraSolver("A", graph);

        dijkstraSolve.dijkstra();
        dijkstraSolve.printResult();
    }

    @Test()
    public void testAdjacentSolver() {
        MutableGraph<String> graph = AdjacentSolver.buildGraph();
        AdjacentSolver adjCelSolve = new AdjacentSolver(graph);

        System.out.println(adjCelSolve.adjacentNodes("A", 1));
        System.out.println(adjCelSolve.adjacentNodes("A", 2));
        System.out.println(adjCelSolve.adjacentNodes("A", 3));
        System.out.println(adjCelSolve.adjacentNodes("A", 4));

    }

    /**
     * 有权最短路径
     * <p>
     * 迪杰斯特拉算法
     */
    public static class DijkstraSolver {

        private final String sourceNode;
        private final MutableValueGraph<String, Integer> graph;

        public DijkstraSolver(String sourceNode, MutableValueGraph<String, Integer> graph) {
            this.sourceNode = sourceNode;
            this.graph = graph;
        }

        private void dijkstra() {
            initPathFromSourceNode(sourceNode);
            Set<String> nodes = graph.nodes();
            if (!nodes.contains(sourceNode)) {
                throw new IllegalArgumentException(sourceNode + " is not in this graph!");
            }

            Set<String> notVisitedNodes = new HashSet<>(graph.nodes());
            String currentVisitNode = sourceNode;
            while (!notVisitedNodes.isEmpty()) {
                String nextVisitNode = findNextNode(currentVisitNode, notVisitedNodes);
                if (nextVisitNode.equals("")) {
                    break;
                }
                notVisitedNodes.remove(currentVisitNode);
                currentVisitNode = nextVisitNode;
            }
        }

        private String findNextNode(String currentVisitNode, Set<String> notVisitedNodes) {
            int shortestPath = Integer.MAX_VALUE;
            String nextVisitNode = "";

            for (String node : graph.nodes()) {
                if (currentVisitNode.equals(node) || !notVisitedNodes.contains(node)) {
                    continue;
                }

                if (graph.successors(currentVisitNode).contains(node)) {
                    Integer edgeValue = graph.edgeValue(sourceNode, currentVisitNode) + graph.edgeValue(currentVisitNode, node);
                    Integer currentPathValue = graph.edgeValue(sourceNode, node);
                    if (edgeValue > 0) {
                        graph.putEdgeValue(sourceNode, node, Math.min(edgeValue, currentPathValue));
                    }
                }

                if (graph.edgeValue(sourceNode, node) < shortestPath) {
                    shortestPath = graph.edgeValue(sourceNode, node);
                    nextVisitNode = node;
                }
            }

            return nextVisitNode;
        }

        private void initPathFromSourceNode(String sourceNode) {
            graph.nodes().stream().filter(
                    node -> !graph.adjacentNodes(sourceNode).contains(node))
                    .forEach(node -> graph.putEdgeValue(sourceNode, node, Integer.MAX_VALUE));
            graph.putEdgeValue(sourceNode, sourceNode, 0);
        }

        private void printResult() {
            for (String node : graph.nodes()) {
                System.out.println(sourceNode + "->" + node + " shortest path is:" + graph.edgeValue(sourceNode, node));
            }
        }


        private static MutableValueGraph<String, Integer> buildGraph() {

            MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
                    .nodeOrder(ElementOrder.<String>natural()).allowsSelfLoops(true).build();

            graph.putEdgeValue("A", "B", 10);
            graph.putEdgeValue("A", "C", 3);
            graph.putEdgeValue("A", "D", 20);
            graph.putEdgeValue("B", "D", 5);
            graph.putEdgeValue("C", "B", 2);
            graph.putEdgeValue("C", "E", 15);
            graph.putEdgeValue("D", "E", 11);

            return graph;
        }
    }

    /**
     * 网络邻区问题
     */
    public static class AdjacentSolver {

        private MutableGraph<String> graph;

        public AdjacentSolver(MutableGraph<String> graph) {
            this.graph = graph;
        }

        public Set<String> adjacentNodes(String sourceNode, int dimension) {
            Set<String> adjacentNodes = new HashSet<>();
            adjacentNodes.add(sourceNode);
            for (int i = 0; i < dimension; i++) {
                Set<String> currentDimensionNodes = new HashSet<>(adjacentNodes);
                adjacentNodes.clear();
                for (String adjacentNode : currentDimensionNodes) {
                    adjacentNodes.addAll(graph.nodes().stream().filter(
                            node -> graph.successors(adjacentNode).contains(node)
                    ).collect(Collectors.toSet()));
                }
            }
            return adjacentNodes;
        }

        public static MutableGraph<String> buildGraph() {
            MutableGraph<String> graph = GraphBuilder.directed().nodeOrder(
                    ElementOrder.<String>natural()).allowsSelfLoops(false).build();

            graph.putEdge("A", "B");
            graph.putEdge("A", "C");
            graph.putEdge("A", "D");
            graph.putEdge("B", "D");
            graph.putEdge("C", "B");
            graph.putEdge("C", "E");
            graph.putEdge("D", "E");

            return graph;
        }
    }

}
