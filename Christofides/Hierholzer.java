package Christofides;
import java.util.ArrayList;


public class Hierholzer {
	
	//1. Choose any vertex v and push it onto a stack. Initially all edges are unmarked.
	public ArrayList<Vertex> step1(ArrayList<Vertex> vertexs,ArrayList<Edge> edges){
		System.out.println("step1");
		ArrayList<Vertex> tempVertexs = new ArrayList<Vertex>();
		tempVertexs.add(vertexs.remove(0));
		System.out.println("tempVertexs is: ");
		printVertex(tempVertexs);
		return step2(vertexs,tempVertexs,edges);

	}
	
//	2. While the edge stack is nonempty, look at the top vertex, u, on the stack. 
//	If u has an unmarked incident edge, say, to a vertex w, then push w onto the stack and mark the edge uw. 
//	On the other hand, if u has no unmarked incident edge, then pop u off the stack and print it.                
	
	
	public ArrayList<Vertex> step2(ArrayList<Vertex> vertexs,ArrayList<Vertex> tempVertexs,ArrayList<Edge> edges){
		System.out.println("step2");
		ArrayList<Vertex> resultVertexs = new ArrayList<Vertex>();
		while(!edges.isEmpty()){
			Vertex top = tempVertexs.get(tempVertexs.size()-1);
			for(Edge e:edges){
				if(e.start.key.equals(top.key)){
					tempVertexs.add(e.end);
					edges.remove(e);
					break;
				}else if(e.end.key.equals(top.key)){
					tempVertexs.add(e.start);
					edges.remove(e);
					break;
				}else{
					resultVertexs.add(top);
					tempVertexs.remove(top);
				}
			}
			System.out.println("tempVertex is:");
			printVertex(tempVertexs);
			System.out.println("resultVertexs is:");
			printVertex(resultVertexs);
			System.out.println("edges is:");
			printEdge(edges);
			
		}
		for(int i=tempVertexs.size()-1;i>=0;i--){
			resultVertexs.add(tempVertexs.get(i));
		}
		System.out.println("resultVertexs is:");
		printVertex(resultVertexs);
		return resultVertexs;
	}
	
	public void printVertex(ArrayList<Vertex> vertexs){
		for(Vertex v: vertexs){
			System.out.print(v.key+" ");
		}
		System.out.println("\n");
	}
	public static void printEdge(ArrayList<Edge> edges){
		for(Edge e: edges){
			System.out.print(" "+e.start.key+"->"+e.end.key+" ");
		}
		System.out.println("\n");
	}
}