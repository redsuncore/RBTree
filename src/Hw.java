import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Hw {
	public static void main(String [] args) throws IOException {
		int index = 0;
		File iDir = new File("./input/"); 
		File sDir = new File("./search/"); 
		File[] iFileList = iDir.listFiles(); 
		File[] sFileList = sDir.listFiles(); 
		
		RBTree rbt = new RBTree();
		
		while(index < iFileList.length)
		{
			File file = iFileList[index]; 
			int inserted, deleted, missed;
			BufferedReader br = new BufferedReader(new FileReader(file));
			if(file.isFile())
			{
				if(file.getName().equals("test.txt"))
				{
					System.out.println("readInput");
					inserted = 0;
					deleted = 0;
					missed = 0;
					while(true)
					{
						int num = Integer.parseInt(br.readLine());
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
					rbt.print(rbt.root, 0);
					index++;
				}
				else
					index++;
			}
			
		}
		index = 0;
		while(index < sFileList.length)
		{
			File file = sFileList[index]; 
			PrintWriter pw = new PrintWriter("output.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			if(file.getName().equals("search.txt"))
			{
				System.out.println("readSearch");
				while(true)
				{
					int num = Integer.parseInt(br.readLine());
					if(num == 0)
						break;
					String data = rbt.search_file(num);
					pw.println(data);
				}
				pw.close();
				index++;
			}
			else index++;
		}
	}
}
