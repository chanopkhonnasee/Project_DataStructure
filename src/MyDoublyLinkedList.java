

public class MyDoublyLinkedList  {
	DoubleMyNode head;
	DoubleMyNode tail;
	DoubleMyNode cur;
	Object data =null;
	int size=0;
	public MyDoublyLinkedList(){
		head = new DoubleMyNode(data);
		tail = new DoubleMyNode(data);
		cur = new DoubleMyNode(data);
		head.nextNode = tail;
		head.previousNode = null;
		tail.nextNode = null;
		tail.previousNode = head;
	}

	public void insert(Object e) throws Exception {
		if(size==0){
			cur = new DoubleMyNode( e, head, tail);
			head.nextNode = cur;
			tail.previousNode = cur;
			size++;
		}
		else {
			DoubleMyNode node = new DoubleMyNode( e,cur,cur.nextNode);
			DoubleMyNode nextNode = cur.getNextNode();
			cur.setNextNode(node);
			node.setPreviousNode(cur);
			node.setNextNode(nextNode);
			nextNode.setPreviousNode(node);
			cur = node;
			node=null;
			nextNode=null;
			size++;
		}

	}


	public Object delete() throws Exception {
		Object tmp = cur.getData();
		cur.previousNode.nextNode=cur.nextNode;
		cur.nextNode.previousNode=cur.previousNode;
		cur=cur.previousNode;
		size--;
		return tmp;
	}


	public Object retrieve() throws Exception {
		return cur.getData();
	}


	public boolean isEmpty() {
		if(head.getNextNode()==tail&&tail.getPreviousNode()==head){
			return true;
		}
		return false;
	}



	public void update(Object e) throws Exception {
		cur.setData(e);

	}


	public void findFirst() throws Exception {
		cur = head.nextNode;

	}


	public void findNext() throws Exception {
		cur = cur.nextNode;
	}


	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean findKey(Object tKey) {
		DoubleMyNode tmp = head.nextNode;
		while(!tmp.getData().equals(tKey)){
			tmp=tmp.nextNode;
			if(tmp.equals(tail)){
				return false;
			}
		}
		return true;
	}
	public int size(){
		return size;
	}

	public void findCur(int cnt) throws Exception {
		cur = head;
		for(int j = 1;j<cnt;j++){
			cur = cur.getNextNode();
		}
	}

}
