import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class StaticVisitor extends VoidVisitorAdapter<List<String>>{
	
	public static List<String> getSetList  = new ArrayList<>();
	
	public StaticVisitor(){
		getSetList = new ArrayList<>();
	}
	
	@Override
	public void visit(MethodDeclaration n, List<String> arg) {
		// TODO Auto-generated method stub
//		Void c = null;
		arg.add(n.getName());
	}
	
	@Override
	public void visit(FieldDeclaration n, List<String> arg) {
		// TODO Auto-generated method stub
		
		for(VariableDeclarator v :n.getVariables())
		arg.add(v.getId().toString());
		
	}
}
