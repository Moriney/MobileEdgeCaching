package mobileEdgeLayer;

public class Edge implements IEdge {

	
    private static Edge singletonInstance =new Edge();
	
	//Get the singleton instance
	public static Edge getInstance(){
	     return singletonInstance;
	}

	public Edge() {

		
			
	}
	
}
