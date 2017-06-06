import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hw {
	public static void main(String [] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		RBTree rbt = new RBTree();
		while(true)
		{
			String line = br.readLine();
			int num = Integer.parseInt(line);
			System.out.println(num);
			if(num>0)
				rbt.insert(rbt.root, new Node(num));
			else if(num<0)
			{
				if(rbt.search(rbt.root, -num)!=null)
				{
					rbt.delete(rbt.search(rbt.root, -num));
				}
			}
			else
				break;
			if(line == null) break;
		}
		rbt.print(rbt.root, 0);
		System.out.println("-----------------------");
		rbt.inorder_iter();
	}
}
