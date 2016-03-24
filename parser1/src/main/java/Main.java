import java.io.BufferedOutputStream;
import java.io.File;
//import java.util.List;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class Main {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		//File folder = new File(FolderName.tc5);
		
		//System.out.println(args[0]);
		File folder = new File(args[0]);
		
		File[] files = folder.listFiles();

		String classString = "";
		String relString = "";
		List<String> classNames = new ArrayList<>();
		for(File f : files)
		{
			if(f.isFile())
			{
				String fileName = f.getName();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);				
				if(ext.equals("java"))
				{
					
					CompilationUnit cu = JavaParser.parse(f);					
					 cu.accept(new ClassVisitor(), classNames);

				}			
			}
		}
		
		MyVisitor.classList = new ArrayList<>(classNames);
		for(File f :files)
		{	
			if(f.isFile())
			{
				String fileName = f.getName();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);				
				if(ext.equals("java"))
				{
					String x = "";
					CompilationUnit cu = JavaParser.parse(f);					
					x = cu.accept(new MyVisitor(), null);
					if(x!=null)
						classString+=x;
				}	
			}
		}
		
		
		for(int i=0;i<files.length;i++)
		{
			File f = files[i];
			if(f.isFile())
			{
				String fileName = f.getName();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);				
				if(ext.equals("java"))
				{
					String x = "";
					CompilationUnit cu = JavaParser.parse(f);					
					x = cu.accept(new RelVisitor(), classString);
					if(x!=null)
						relString+=x;
				}
			}
		}
		String hasRelString = Utils.constructYumlHasRel(RelVisitor.hasRelList);
		String resultYuml = classString + relString + hasRelString;
		byte[] utf8Bytes = resultYuml.getBytes("UTF8");
		//BufferedOutputStream b = new BufferedOutputStream(new FileOutputStream(new File("C:\\Users\\Bala\\Desktop\\202 - SSE\\UML Parser Project\\yUMLOutput.yuml")));
		BufferedOutputStream b = new BufferedOutputStream(new FileOutputStream(new File("yUMLOutput.yuml")));
		b.write(utf8Bytes); b.close();
		//System.out.println(classString);
		//System.out.println(relString);
		System.out.println(resultYuml);
	}

}
