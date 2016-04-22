package BrutalForce;

import java.util.ArrayList;
import java.util.Iterator;

public class Prim {

	public static ArrayList<Vertex> vertexList = new ArrayList<Vertex>();//结点集
	public static ArrayList<Edge> EdgeQueue = new ArrayList<Edge>();//边集
	public static ArrayList<Vertex> newVertex = new ArrayList<Vertex>();//已经 访问过的结点
	public static void main(String[] args) {
		primTree();

	}
	public static void buildGraph() {
		Vertex v1 = new Vertex("a");

		Vertex v2 = new Vertex("b");
		
		Vertex v3 = new Vertex("c");
		
		Vertex v4 = new Vertex("d");
		
		Vertex v5 = new Vertex("e");
		Prim.vertexList.add(v2);
		Prim.vertexList.add(v5);
		Prim.vertexList.add(v3);
		Prim.vertexList.add(v4);
		Prim.vertexList.add(v1);
		addEdge(v1, v2, 1);
		addEdge(v1, v3, 1);
		addEdge(v2, v3, 1);
		addEdge(v2, v5, 1);
		addEdge(v2, v4, 1);
		addEdge(v3, v4, 1);
		addEdge(v3, v5, 1);
		addEdge(v4, v5, 1);
		addEdge(v1, v5, 2);
		addEdge(v1, v4, 2);
	}
	public static void addEdge(Vertex a, Vertex b, int w) {
		Edge e = new Edge(a, b, w);
		Prim.EdgeQueue.add(e);
	}
	public static ArrayList<Edge> primTree(){
		buildGraph();
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
}
class Vertex {
	String key;
	Vertex(String key){
		this.key = key;
	}
}

class Edge{
	Vertex start;
	Vertex end;
	int key;
	Edge(Vertex start,Vertex end,int key){
		this.start = start;
		this.end  = end;
		this.key = key;
		
	}
}

