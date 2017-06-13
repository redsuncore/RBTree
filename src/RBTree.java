

public class RBTree {
	public Node root;
	public RBTree()
	{
		root = Node.top;
	}
	public void print(Node tree, int level) {
		if(tree.isNIL())
		{
			System.out.println("Null");
			return;
		}
		if (!tree.right.isNIL())
			print(tree.right, level + 1);
		else
		{
			for(int i = 0; i < level+1; i++)
				System.out.print("	");
			System.out.println("nil");
		}
		for(int i = 0; i < level; i++)
			System.out.print("	");
		if(tree.RB)
			System.out.println(tree.val+" R");
		else
			System.out.println(tree.val+" B");
		if (!tree.left.isNIL())
			print(tree.left, level + 1);
		else
		{
			for(int i = 0; i < level+1; i++)
				System.out.print("	");
			System.out.println("nil");
		}
	}
	public void insert(Node tree, Node n)
	{
		insert_0(tree, n);
		insert_1(n);
		root=findRoot(n);
	}
	public void insert_0(Node tree, Node n) 
	{
		if (root == Node.top) 
		{
			root = n;
		}
		else if (n.val <= tree.val) 
		{
			if (tree.left.isNIL())
			{
				tree.left = n;
				n.parent = tree;
			}
			else
				insert_0(tree.left,n);
		}
		else 
		{
			if (tree.right.isNIL())
			{
				tree.right = n;
				n.parent = tree;
			}
			else
				insert_0(tree.right,n);
		}
	}
	
	public void insert_1(Node n)
	{
		if(n.parent.isNIL())
			n.RB = false;
		else
			insert_2(n);
	}
	public void insert_2(Node n)
	{
		if(n.parent.RB == false)
			return;
		else
			insert_3(n);
	}
	public void insert_3(Node n)
	{
		if(!n.uncle().isNIL() && n.uncle().RB == true)
		{
			n.parent.RB = false;
			n.uncle().RB = false;
			n.grandParent().RB =true;
			
			insert_1(n.grandParent());
		}
		else
			insert_4(n);
	}
	public void insert_4(Node n)
	{
		if( n== n.parent.right && n.parent == n.grandParent().left)
		{
			rotateLeft(n.parent);
			n=n.left;
		}
		else if((n==n.parent.left)&&(n.parent == n.grandParent().right))
		{
			rotateRight(n.parent);
			n=n.right;
		}
		insert_5(n);
	}
	public void insert_5(Node n)
	{
		n.parent.RB = false;
		n.grandParent().RB = true;
		if(n == n.parent.left)
			rotateRight(n.grandParent());
		else
			rotateLeft(n.grandParent());
	}
	private void transPlant(Node x, Node y)
	{
		if(x.parent == Node.top)
		{
			root = y;
		}
		else if(x.parent.left == x)
		{
			x.parent.left = y;
		}
		else
		{
			x.parent.right = y;
		}
		
		y.parent = x.parent;
		y.RB = x.RB;
	}
	public Node search (Node tree, int val) {
		if (tree.isNIL())
			return null;
		else if (val == tree.val)
			return tree;
		else if (val < tree.val)      
			return search(tree.left,val);
		else
			return search(tree.right,val);
	}
	public Node minimum(Node n)
	{
		Node find = n;
		while(!find.left.isNIL())
		{
			find = find.left;
		}
		return find;
	}
	public void delete(Node n)
	{
		Node org, child;
		org = n;
		if(n == null)
			return;
		boolean originColor = n.RB;
		if(n.left.isNIL())
		{
			child=n.right;
			transPlant(n,n.right);
		}
		else if(n.right.isNIL())
		{
			child = n.left;
			transPlant(n,n.left);
		}
		else 
		{
			org = minimum(n.right);
			originColor = org.RB;
			child = org.right;
			if(org.parent == n)
				child.parent = org;
			else
			{
				transPlant(org, org.right);
				org.right = n.right;
				org.right.parent = org;
			}
			transPlant(n, org);
			org.left = n.left;
			org.left.parent = org;
			org.RB = n.RB;
		}
		if(n.parent == Node.top&&n.left.isNIL()&&n.right.isNIL())
		{
			root = Node.top;
		}
		if(!originColor)
			delete_Fixup(child);
	}
	public void delete_Fixup(Node start)
	{
		Node n = start;
		while(n != root && !n.RB)
		{
			Node s = n.Sibling();
			if(s.RB)
			{
				s.RB = false;
				n.parent.RB = true;
				if(n == n.parent.left)
				{
					rotateLeft(n.parent);
					s = n.parent.right;
				}
				else
				{
					rotateRight(n.parent);
					s = n.parent.left;
				}
			}
			if(!s.left.RB&&!s.right.RB)
			{
				s.RB = true;
				n = n.parent;
			}
			else 
			{
				if(n == n.parent.left)
				{
					if(!s.right.RB)
					{
						s.left.RB = false;
						s.RB = true;
						rotateRight(s);
						s = n.parent.right;
					}
				}
				else
				{
					if(!s.left.RB)
					{
						s.right.RB = false;
						s.RB = true;
						rotateLeft(s);
						s = n.parent.left;
					}
				}
				s.RB = n.parent.RB;
				n.parent.RB = false;
				if(n == n.parent.left)
				{
					s.right.RB = false;
					rotateLeft(n.parent);
				}
				else
				{
					s.left.RB = false;
					rotateRight(n.parent);
				}
				n = root;
			}
		}
		n.RB = false;
	}
	
	public void rotateLeft(Node node) {//왼쪽 회전
		Node child = node.right;
		Node parent = node.parent;

		node.right = child.left;
		
	    child.left.parent=node;
	    child.parent=parent;
	    if(parent == Node.top)
	    	root = child;
	    else
	    {
	        if (parent.left == node)
	            parent.left=child;
	        else
	            parent.right=child;
	    }
	    child.left=node;
	    node.parent=child;
	}
	  
	public void rotateRight(Node node) { // 오른쪽 회전
	    Node child = node.left;
	    Node parent = node.parent;
	    
	    node.left = child.right;
		
	    child.right.parent=node;
	    child.parent=parent;
		if(parent == Node.top)
	    	root = child;
	    else
	    {
	        if (parent.right == node)
	            parent.right=child;
	        else
	            parent.left=child;
	    }
	    child.right=node;
	    node.parent=child;
	  
	}
	public Node findRoot(Node n)
	{
		while(n.parent != Node.top)
		{
			n=n.parent;
		}
		return n;
	}
	public void inorder_iter()
	{
		int total, nb, bh, curbh, bs, rs;
		if(root == null)
			return;
		else
		{
			NodeStack stack = new NodeStack(root);
			Node curNode = root;
			boolean term = true;
			total = 0;
			nb = 0;
			curbh = 1;
			bh = 1;
			bs = 1;
			rs = 0;
			while(!stack.isEmpty())
			{
				if(!curNode.left.isNIL() && term)
				{
					curNode = curNode.left;
					stack.push(curNode);
					if(!curNode.RB)
					{
						curbh++;
						bs++;
					}
					else
						rs++;
				}
				else
				{
					if(bh < curbh)
						bh = curbh;
					
					curNode = stack.pop();
					total++;
					if(!curNode.RB)
					{
						nb++;
						curbh--;
					}
					term = false;
					//System.out.printf("%d\n",curNode.val);
					if(!curNode.right.isNIL())
					{
						curNode = curNode.right;
						stack.push(curNode);
						if(!curNode.RB)
						{
							curbh++;
							bs++;
						}
						else
							rs++;
						term = true;
					}
				}
			}
		}
		
		System.out.println("total = "+total);
		System.out.println("nb = "+nb);
		System.out.println("bh = "+bh);
		System.out.println(bs + " B");
		System.out.println(rs + " R");
	}
	
	class NodeStack
	{
		Item top;
		class Item
		{
			Node node;
			Item nextItem;
			public Item(Node item)
			{
				node=item;
				nextItem = null;
			}
		}
		public NodeStack(Node root)
		{
			top=new Item(root);
		}
		public Node pop()
		{
			Node ret = top.node;
			top = top.nextItem;
			return ret;
		}
		public void push(Node item)
		{
			if(item  == null)
				return;
			if(top == null)
				top = new Item(item);
			else
			{
				Item nItem = new Item(item);
				nItem.nextItem = top;
				top = nItem;
			}
		}
		public boolean isEmpty()
		{
			if(top == null)
				return true;//empty
			else
				return false;
		}
	}
}
