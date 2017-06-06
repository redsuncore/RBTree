public class Node {
	public int val;
	private boolean NIL;
	public boolean RB;//true==red, false==black
	public static Node top = new Node();
	public Node left, right, parent;
	
	private Node()
	{
		NIL = true;
		parent = this;
		left = this;
		right = this;
		RB = false;
	}
	public static Node NIL()
	{
		return new Node();
	}
	public Node(int newval) 
	{
		Node lNode = NIL();
		Node rNode = NIL();
		lNode.parent = this;
		rNode.parent = this;
		val = newval;
		left = lNode;
		right = rNode;
		NIL = false;
		parent = top;
		RB = true;
	}
	public boolean isNIL()//NIL¿Ã∏È true;
	{
		if(NIL)
			return true;
		else
			return false;
	}
	public Node grandParent()
	{
		if(this.parent != top)
		{
			return this.parent.parent;
		}
		return top;
	}
	public Node uncle()
	{
		Node grandP = grandParent();
		if(grandP == top)
			return top;
		else if(this.parent == grandP.left)
		{
			return grandP.right;
		}
		else
		{
			return grandP.left;
		}
	}
	public Node Sibling()
	{
		if(this.parent == top)
			return NIL();
		if(this == this.parent.left)
			return this.parent.right;
		else
			return this.parent.left;
	}
}