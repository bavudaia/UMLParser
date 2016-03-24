import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor  extends VoidVisitorAdapter<List<String>>{
	@Override
	public void visit(ClassOrInterfaceDeclaration n, List<String> arg) {
		// TODO Auto-generated method stub
		if(n!=null)
		if(n.isInterface())
		{
			arg.add(n.getName());
		}
		else
		{
			arg.add(n.getName());
		}
	}
}
