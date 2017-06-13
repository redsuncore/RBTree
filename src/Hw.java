import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Hw {
	public static void main(String [] args) throws IOException {
		int index = 0;
		File dir = new File("./input/"); 
		File[] fileList = dir.listFiles(); 
		while(index < fileList.length)
		{
			File file = fileList[index]; 
			int inserted, deleted, missed;
			BufferedReader br = new BufferedReader(new FileReader(file));
			RBTree rbt = new RBTree();
			if(file.isFile())
				System.out.println("filename = "+file.getName());
			inserted = 0;
			deleted = 0;
			missed = 0;
			while(true)
			{
				if(index == 0)
				{
					rbt.print(rbt.root, 0);
					
				}
				int num = Integer.parseInt(br.readLine());
				if(index == 0)
				{
					System.out.println(num);
					System.out.println("-----------------------");
					
				}
				if(num == 0)
					break;
				if(num>0)
				{
					rbt.insert(rbt.root, new Node(num));
					inserted++;
				}
				else if(num<0)
				{
					if(rbt.search(rbt.root, -num)!=null)
					{
						rbt.delete(rbt.search(rbt.root, -num));
						deleted++;
					}
					else
					{
						missed++;
					}
				}
				else
					break;
				if(num == -1) break;
			}
			if(index == 0)
				rbt.print(rbt.root, 0);
			rbt.inorder_iter();
			System.out.println("inserted = "+inserted);
			System.out.println("deleted = "+deleted);
			System.out.println("missed = "+missed);
			System.out.println("-----------------------");
			index++;
		}
	}
}
