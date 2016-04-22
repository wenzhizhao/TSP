package Christofides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.HashMap;


public class TSPChristofides {
	public static ArrayList<Vertex> vertexList = new ArrayList<Vertex>();//结点集
	public static ArrayList<Edge> EdgeQueue = new ArrayList<Edge>();//边集
	public static ArrayList<Vertex> newVertex = new ArrayList<Vertex>();//已经 访问过的结点
	
	public static void addEdge(Vertex a, Vertex b, int w) {
		Edge e = new Edge(a, b, w);
		EdgeQueue.add(e);
	}
	
	
	
	
	public static ArrayList<Edge> eulerToPath(ArrayList<Vertex> euler){
		//ArrayList<Vertex> euler = new ArrayList<Vertex>();
		HashMap<Vertex, Integer> pathCount = new HashMap<Vertex, Integer>();
		Vertex start = euler.get(0);
		pathCount.put(start, 1);
		int deleteIndex=euler.size()-1;
		
		while(deleteIndex>0){
			System.out.println(deleteIndex);
			if(!pathCount.containsKey(euler.get(deleteIndex))){
				pathCount.put(euler.get(deleteIndex), 1);
				deleteIndex--;
			}
			else {
				euler.remove(deleteIndex);
				deleteIndex--;
			}
		}
		ArrayList<Edge> result = new ArrayList<Edge>();
		for(int i=0;i<euler.size()-1;i++){
			for(Edge e : minimalSpiningTree.EdgeQueue){
				if((e.start.key==euler.get(i).key &&e.end.key==euler.get(i+1).key)
				||(e.end.key==euler.get(i).key &&e.start.key==euler.get(i+1).key)){
					result.add(e);
				}
			}
		}
		return result;
	}
	
	
	
	
	
	public static ArrayList<Edge> primTree(){
		ArrayList<Edge> mst = new ArrayList<Edge>();
		Vertex start = vertexList.get(0);
		newVertex.add(start);
		//for(int n=0;n<vertexList.size();n++){
		while(newVertex.size()<vertexList.size()){
			Vertex temp = new Vertex(start.key);
			Edge tempedge = new Edge(start,start,Integer.MAX_VALUE);
			for(Vertex v : newVertex){
				for(Edge e : EdgeQueue){
					if(e.start==v && !containVertex(e.end)){
						if(e.key<tempedge.key){
							temp = e.end;
							tempedge = e;
						}
					}
					if(e.end==v && !containVertex(e.start)){
						if(e.key<tempedge.key){
							temp = e.start;
							tempedge = e;
						}
					}
					
				}
			}
			System.out.println(tempedge.start.key+"-"+tempedge.end.key);
			mst.add(tempedge);
			newVertex.add(temp);
		}
		@SuppressWarnings("rawtypes")
		Iterator it = newVertex.iterator();
		while(it.hasNext()){
			Vertex v =(Vertex) it.next();
			System.out.println(v.key);
		}
		return mst;
	}
	
	
	public void printEdge(ArrayList<Edge> edges){
		for(Edge e: edges){
			System.out.print(" "+e.start.key+"->"+e.end.key+" ");
		}
		System.out.println("\n");
	}
	
	public static boolean containVertex(Vertex vte){
		for(Vertex v : newVertex){
			if(v.key.equals(vte.key))
				return true;
		}
		return false;
	}

	
	
	
	//public static minimalSpiningTree mst = new minimalSpiningTree();
	public static void main(String[] args) {
		//primTree();
		//ArrayList<Vertex> reduceVertex = new ArrayList<Vertex>();
		oddDegree od = new oddDegree();
		buildGraph();
		//od.findOddDegree(minimalSpiningTree.primTree());
		ArrayList<Edge> mst= primTree();
		System.out.println("minimalSpiningTree.primTree()");
		for(Edge e : mst){
			System.out.print(" "+e.start.key+"->"+e.end.key);
		}
		System.out.println();
		ArrayList<Edge> reducedGraph = reduceGraph(EdgeQueue,od.findOddDegree(primTree()));
		int[][] matrix = listToMatrix(reducedGraph, od.findOddDegree(reducedGraph));

		System.out.println("\n");
		Hungarian hungarian = new Hungarian();
		ElementMatrix elementMatrix = hungarian.convertMatrix(matrix);
		elementMatrix.print();
		System.out.println("Step 1");
		ElementMatrix elementMatrix1 = hungarian.step1(elementMatrix);
		elementMatrix1.print();
		System.out.println("Step 2");
		ElementMatrix elementMatrix2 = hungarian.step2(elementMatrix1);
		ArrayList<Integer[]> output = elementMatrix2.output();
		ArrayList<Edge> matchResult = arrayListToEdge(output,od.findOddDegree(reducedGraph),matrix);
		System.out.println("print mst:");
		for(Edge e: mst){
			System.out.print(" "+e.start.key+"->"+e.end.key+" ");
		}
		System.out.println("\n");
		mst.addAll(matchResult);

		
		Vertex v1 = new Vertex("a");
		vertexList.add(v1);
		Vertex v2 = new Vertex("b");
		vertexList.add(v2);
		Vertex v3 = new Vertex("c");
		vertexList.add(v3);
		Vertex v4 = new Vertex("d");
		vertexList.add(v4);
		Vertex v5 = new Vertex("e");
		vertexList.add(v5);
		addEdge(v1, v2, 1);
	    addEdge(v1, v3, 1);
		addEdge(v1, v5, 1);
		addEdge(v1, v4, 2);
		addEdge(v2, v3, 2);
		addEdge(v2, v5, 1);
		addEdge(v2, v4, 1);
		addEdge(v3, v4, 1);
		addEdge(v3, v5, 1);
		addEdge(v4, v5, 1);
		
		ArrayList<Vertex> euler = new ArrayList<Vertex>();
		euler.add(v2);
		euler.add(v1);
		euler.add(v4);
		euler.add(v2);
		euler.add(v3);
		euler.add(v5);
		euler.add(v2);
		eulerToPath(euler);
		//find the Euler tour
	
//		ArrayList<Vertex> euler = new ArrayList<Vertex>();
//		HashMap<Vertex, Integer> pathCount = new HashMap<Vertex, Integer>();
//		Vertex start = euler.get(0);
//		pathCount.put(start, 1);
//		int deleteIndex=euler.size()-1;
//		
//		while(deleteIndex>0){
//			if(!pathCount.containsKey(euler.get(deleteIndex))){
//				pathCount.put(euler.get(deleteIndex), 1);
//				deleteIndex--;
//			}
//			else {
//				euler.remove(deleteIndex);
//			}
//		}
//		ArrayList<Edge> result = new ArrayList<Edge>();
//		for(int i=0;i<euler.size()-1;i++){
//			for(Edge e : minimalSpiningTree.EdgeQueue){
//				if((e.start.key==euler.get(i).key &&e.end.key==euler.get(i+1).key)
//				||(e.end.key==euler.get(i).key &&e.start.key==euler.get(i+1).key)){
//					result.add(e);
//				}
//			}
//		}


	}
	public static void buildGraph() {
		Vertex v1 = new Vertex("a");
		vertexList.add(v1);
		Vertex v2 = new Vertex("b");
		vertexList.add(v2);
		Vertex v3 = new Vertex("c");
		vertexList.add(v3);
		Vertex v4 = new Vertex("d");
		vertexList.add(v4);
		Vertex v5 = new Vertex("e");
		vertexList.add(v5);
		addEdge(v1, v2, 1);
	    addEdge(v1, v3, 1);
		addEdge(v1, v5, 1);
		addEdge(v1, v4, 2);
		addEdge(v2, v3, 2);
		addEdge(v2, v5, 1);
		addEdge(v2, v4, 1);
		addEdge(v3, v4, 1);
		addEdge(v3, v5, 1);
		addEdge(v4, v5, 1);
	}
	
	public static ArrayList<Vertex> edgeToVertex(ArrayList<Edge> edge){
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		for(Edge e : edge){
			if(!result.contains(e.end)){
				result.add(e.end);
			}
			if(!result.contains(e.start)){
				result.add(e.start);
			}
		}
		return result;
	}

	
	public static ArrayList<Edge> reduceGraph(ArrayList<Edge> graph,ArrayList<Vertex> vertex){
		ArrayList<Edge> result = new ArrayList<Edge>();
		for(Edge e : graph){
			if(vertex.contains(e.start)&&vertex.contains(e.end)){
				result.add(e);
			}
		}
		for(Edge e : result){
			System.out.println(e.start.key+"-"+e.end.key);
		}
		return result;
	}
	
	public static int[][] listToMatrix(ArrayList<Edge> edges, ArrayList<Vertex> numVertex){
		int[][] matrix = new int[numVertex.size()][numVertex.size()];
		String[] vertexRow = new String[numVertex.size()];
		String[] vertexCol = new String[numVertex.size()];
		for( Edge e : edges){
			int i = numVertex.indexOf(e.start);
			int j = numVertex.indexOf(e.end);
			matrix[i][j] = e.key;
			vertexRow[i] = e.start.key;
			matrix[j][i] = e.key;
			vertexCol[i] = e.start.key;
		}
		for( int i=0; i<numVertex.size(); i++){
			if (matrix[i][i] == 0	)
				matrix[i][i]=9999;
//				matrix[i][i] = Integer.MAX_VALUE;
		}
		for(int[] row : matrix){
			for (int col: row){
				System.out.print(col+"  ");
			}
			System.out.println();
		}
//		System.out.println("\n");
//		System.out.println("start vertex");
//		for(String start :vertexRow ){
//			System.out.print(start+" ");
//		}
//		System.out.println("\n");
//		System.out.println("\nend vertex");
//		for(String start :vertexRow ){
//			System.out.print(start+" ");
//		}
//		System.out.println("\n");
		
		return matrix;
	}
	
	public static ArrayList<Edge> arrayListToEdge(ArrayList<Integer[]> output, ArrayList<Vertex> numVertex, int[][] listToMatrix){
		ArrayList<Edge> matching = new ArrayList<Edge>();
		for(Integer[] match: output){
			if(match[0]<match[1]){
				Edge edge = new Edge(numVertex.get(match[0]),numVertex.get(match[1]),listToMatrix[match[0]][match[1]]);
				matching.add(edge);
			}
		}
		for(Edge e : matching){
			System.out.println(e.start.key+"-"+e.end.key);
		}
		return matching;
	}
}







