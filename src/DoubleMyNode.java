

public class DoubleMyNode {
	private Object data;
	public  DoubleMyNode previousNode,nextNode;
	DoubleMyNode(Object object){
	this( object, null,null);
	}
	DoubleMyNode( Object object, DoubleMyNode pNode, DoubleMyNode nNode ){
	data = object;
	previousNode = pNode;
	nextNode = nNode;
	}
	public void setNextNode( DoubleMyNode node){
	nextNode = node ;
	}
	public void setPreviousNode( DoubleMyNode node){
	previousNode = node ;
	}
	public void setData(Object object){
	data=object;
	}
	public Object getData(){
	return data;
	}
	public DoubleMyNode getNextNode(){
	return nextNode;
	}
	public DoubleMyNode getPreviousNode(){
	return previousNode;
	}

}
