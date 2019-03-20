package com.company;

import java.util.*;

public class Main {

    private static LinkedList[] Graph_list_index;
    private static Map<Integer, Integer> parentNodes;
    private static int[] result = new int[10000];
    private static List<Integer> shortestPath = new ArrayList<>();

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("enter size of array : ");

        int size_array = input.nextInt();
        int[] initial_array = new int[size_array];
        int number_of_nodes_GRAPH = factorialUsingForLoop(size_array);
        LinkedList<Long>[] Graph_list = new LinkedList[number_of_nodes_GRAPH];
        Graph_list_index = new LinkedList[number_of_nodes_GRAPH];
        parentNodes = new HashMap<>();

        for (int i = 0; i < number_of_nodes_GRAPH; i++) {
            Graph_list[i] = new LinkedList();
            Graph_list_index[i] = new LinkedList();
        }


        System.out.println("enter values of array : ");

        for (int i = 0; i < size_array; i++) {
            System.out.print("value " + i + " : ");
            initial_array[i] = input.nextInt();
        }
        System.out.println();

        System.out.print("your array : ");

        for (int i = 0; i < size_array; i++) {
            System.out.print(initial_array[i] + " ");
        }

        System.out.println();

        for (int i = 0; i < number_of_nodes_GRAPH; i++) {
            if (i == 0)
                for (int j = 0; j < size_array; j++)
                    Graph_list[i].add((long) initial_array[j]);
            else
                for (int j = 0; j < size_array; j++)
                    Graph_list[i].add((long) 111);
        }


        //filling Graph_list
        for (int i = 0, d = 0; d < number_of_nodes_GRAPH; d++) {

            for (int j = 0, t = 0; j < size_array - 1; j++) {

                t = j;
                for (int h = 0; h < number_of_nodes_GRAPH; h++) {
                    if (Graph_list[d].get(j).equals(Graph_list[h].get(j + 1)) && Graph_list[d].get(j + 1).equals(Graph_list[h].get(j))
                            && Graph_list[h].get(j + 1) != 111 && Graph_list[h].get(j) != 111) {
                        j++;
                        Graph_list_index[d].add(h);
                        break;
                    }
                }
                if (t != j) {
                    j--;
                    continue;
                }

                Graph_list[i + 1].clear();
                Graph_list_index[d].add(i + 1);

                for (int e = 0; e < size_array; e++) {
                    if (j == e) {
                        Graph_list[i + 1].add(Graph_list[d].get(e + 1));
                        Graph_list[i + 1].add(Graph_list[d].get(e));
                        e++;
                    } else
                        Graph_list[i + 1].add(Graph_list[d].get(e));

                }
                i++;
            }


        }

        //finding sorted array and end node for BFS algoritm
        int[] array_find_end_node = new int[number_of_nodes_GRAPH];
        int index_of_array_end_node = 0;
        for (int i = 0; i < number_of_nodes_GRAPH; i++) {
            for (int j = 0; j < size_array; j++) {
                for (int t = 0; t < size_array; t++) {
                    if (t + j + 1 == size_array)
                        break;
                    else if (Graph_list[i].get(j) < Graph_list[i].get(t + j + 1)) {
                        array_find_end_node[i]++;
                    }
                }
            }
        }

        //finding index_of_array_end_node in Graph_list
        for (int i = 0, e = 0; i < number_of_nodes_GRAPH; i++) {
            for (int j = 0; j < number_of_nodes_GRAPH; j++) {
                if (array_find_end_node[i] > array_find_end_node[j]) {
                    e++;
                    if (e == number_of_nodes_GRAPH - 1) {
                        index_of_array_end_node = i;
                    }
                }
            }
            e = 0;
        }

        //finding The best way to get to the final node with the help of BFS algoritm
        BFS(0, index_of_array_end_node);




        // P R I N T S

        //******************************************************************

        System.out.println("\nnodes of graph : ");

        for (int i = 0; i < number_of_nodes_GRAPH; i++) {
            System.out.print("node " + i + ": ");
            for (int j = 0; j < size_array; j++) {
                System.out.print(Graph_list[i].get(j) + " ");
            }
            System.out.println();
        }

        //******************************************************************

        System.out.print("\nsorted array : ");

        for (int i = 0; i < size_array; i++) {
            System.out.print(Graph_list[index_of_array_end_node].get(i) + " ");
        }
        System.out.println();

        //******************************************************************


        System.out.println("\nbest way (BFS) : ");

        //first node
        for (int i = 0; i < size_array; i++) {
            System.out.print(Graph_list[0].get(i) + " ");
        }
        System.out.print(" ==> ");

        //best way
        for (int i1 : result) {
            if (i1 != 0) {
                for (int j = 0; j < size_array; j++) {
                    System.out.print(Graph_list[i1].get(j) + " ");
                }
                System.out.print(" ==> ");
            }
        }
        System.out.println();

        //******************************************************************


    }


    private static int factorialUsingForLoop(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return (int) fact;
    }


    static void BFS(int first_node, int end_node) {

        boolean[] visited = new boolean[500];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[first_node] = true;
        queue.add(first_node);
        while (queue.size() != 0) {

            first_node = queue.poll();
            if (first_node == end_node) {

                Integer node = end_node;
                while (node != null) {
                    shortestPath.add(node);
                    node = parentNodes.get(node);
                }
                Collections.reverse(shortestPath);
                for (int i = 0; i < shortestPath.size(); i++) {
                    result[i] = shortestPath.get(i);
                }
                break;
            } else {

                for (int n : (Iterable<Integer>) Graph_list_index[first_node]) {
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                        parentNodes.put(n, first_node);
                    }
                }
            }
        }
    }


}