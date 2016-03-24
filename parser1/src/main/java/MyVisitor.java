import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class MyVisitor extends GenericVisitorAdapter<String,Object> {
    public static List<String> classList = new ArrayList<>();
	@Override
	public String visit(final CompilationUnit n, final Object arg) {

		List<TypeDeclaration> typeDeclarations = n.getTypes();
		String classString = "";
		if (typeDeclarations != null) {
			classString += "[";
			for (final TypeDeclaration typeDeclaration :typeDeclarations) 
			{
				String x = typeDeclaration.accept(this, arg);				
				x = Utils.getTypeForUMLString(x);
				if(x!=null)
					classString += x;
				classString+=("]\n");
			}
		}
		
		return classString;
	}

	@Override
	public String visit(final ClassOrInterfaceDeclaration n, final Object arg) {
		String res = n.getName();
		res = 
				(n.isInterface()?Constants.interfaceBegin+res: res);
				//(n.isInterface()?"<<"+res+">>": res);
		
		/* create method list for each class */
		StaticVisitor m = new StaticVisitor();
		List<String> getsetList = new ArrayList<>();
		List<String> fieldList = new ArrayList<>();

		for(BodyDeclaration method : n.getMembers())
		{
			if(method instanceof MethodDeclaration)
			method.accept(m, getsetList);
			
		}
		
		for(BodyDeclaration field : n.getMembers()){
			if(field instanceof FieldDeclaration)
			field.accept(m, fieldList);
		}
		
		List<BodyDeclaration> members = n.getMembers();
		List<MethodDeclaration> methods = new LinkedList<MethodDeclaration>();
		List<FieldDeclaration> fields = new LinkedList<FieldDeclaration>();
		List<ConstructorDeclaration> constructors = new LinkedList<ConstructorDeclaration>();
		if (members != null) {
			for (final BodyDeclaration member : members) {	
				if(member instanceof FieldDeclaration)
				{
					fields.add((FieldDeclaration)member);
				}
				else if(member instanceof MethodDeclaration)
				{
					methods.add((MethodDeclaration)member);
				}		
				else if(member instanceof ConstructorDeclaration)
				{
					constructors.add((ConstructorDeclaration)member);
				}
			}
		}
		//if(!n.isInterface())
			res = res +"|";

		if(!fields.isEmpty())
		{
			for(FieldDeclaration field : fields)
			{
				int modifier = field.getModifiers();			
				String x = "";

				if(modifier==1 || modifier == 2)
				{

					x +=  field.accept(this, getsetList);
					if(x!=null)
					{	res = res + x; } 
				}
			}
		}
		//if(!n.isInterface())
			res = res + "|";
		if(!constructors.isEmpty())
		{
			for(ConstructorDeclaration constructor : constructors)
			{
				String x = constructor.accept(this, arg);
				if(x != null)
				{	res = res+x; }
			}
		}
		if(!methods.isEmpty())
		{			
			for(MethodDeclaration method : methods)
			{
				int methodModifier = method.getModifiers();
				// methodModifier = 1 => public
				// methodModifier = 9 => public static
				// methodModifier = 1025 => public abstract
				if(methodModifier == 1 || methodModifier == 9 || methodModifier == 1025){
					String methodName = method.getName();

						boolean getterFlag = Utils.isGetter(methodName, fieldList);
						boolean setterFlag =  Utils.isSetter(methodName, fieldList);
						if(!getterFlag && !setterFlag)
						{
						String x = method.accept(this, arg);
						if(x != null)
						{	res = res+x; }
						}
				}
			}
		}
		return res;
	}

	@Override
	public String visit(final FieldDeclaration n, final Object arg) {
		String res = "";
		
		int fieldModifier = n.getModifiers();
		@SuppressWarnings({  "unchecked" })
		List<String> getSetList = (List<String>)arg;
		
		List<VariableDeclarator> variables = n.getVariables();
		Type type= n.getType();
		String typeName = type.toString();
		if(typeName!=null && typeName.contains("[]"))
		{
			typeName = typeName.replace("[]","\uFF3B\uFF3D");
		}
		
		typeName = Utils.getTypeForUMLString(typeName);
		Set<String> typeParams = new HashSet<String>();
		if(Utils.hasTypeParams(typeName)){
			typeParams.addAll(Utils.getTypeParamsFromType(typeName));
			typeParams.add(typeName.substring(0, typeName.indexOf("\uFF1C")));
		}
		else
		{
			typeParams.add(typeName);
		}
		
		boolean show = true;
		
		for(String typeParam : typeParams){
			for(String className : classList)
			{
				if(className.equals(typeParam))
				{
					show =false;
					break;
				}
			}
			if(!show)
			{
				break;
			}
		}
		
		if(variables!=null && show)
		{
			for(VariableDeclarator variable : variables)
			{
				
				if(variable != null && variable.getId()!=null && typeName != null)
				
					{
						boolean getset = Utils.hasGetterSetter(variable.getId().toString(),getSetList);
						fieldModifier = getset?1:fieldModifier;						
						res = res+ Utils.getModifierString(fieldModifier)+ variable.getId().toString()+":"+typeName+";";
					}
			}
		}
		//n.getChildrenNodes().

		//super.visit(n, arg);	
		return res;
	}
	
	@Override
	public String visit(final MethodDeclaration n, final Object arg) {
		String res="";
		int methodModifier = n.getModifiers();
		res= res+ Utils.getModifierString(methodModifier)+ "";
		String methodName = n.getName()+"";
		if(methodName!=null)
		{res = res + methodName;}
		List<Parameter> parameters =  n.getParameters();
		int paramCount = 0;
		res = res + "(";
		if(parameters!= null && !parameters.isEmpty()){
			for(Parameter parameter : parameters){
				String x =  parameter.accept(this, arg);
				if(x!=null)
					
				{ 
					res = res + x; paramCount++;}
				if(paramCount != parameters.size())
				{ res = res + ",";}
			}
		}
		res = res + ")";
		res = res + ":";
		res = res + n.getType().toString();
		res = res + ";";
		return res;
	}
	public String visit(final ConstructorDeclaration n, final Object arg ){
		String res="";
		int constructorModifier = n.getModifiers();
		res= res+ Utils.getModifierString(constructorModifier)+ "";
		String methodName = n.getName()+" ";
		if(methodName!=null)
		{res = res + methodName;}
		List<Parameter> parameters =  n.getParameters();
		int paramCount = 0;
		res = res +"(";
		if(parameters!= null && !parameters.isEmpty()){
			for(Parameter parameter : parameters){
				String x =  parameter.accept(this, arg);
				if(x!=null)	
				{					res = res + x; paramCount++;}
				if(paramCount != parameters.size())
				{ res = res + ",";}
			}
		}
		res = res+")";
		res = res + ";";
		return res;
	}

	@Override
	public String visit(final Parameter n, final Object arg){
		String res = "";
		if(n!=null && n.getId()!=null && n.getType()!=null)
		{		
			String varName = n.getId().getName();
			String varType = n.getType().toString();
			/*support for yUML array sq brackets */
			if(varType.contains("[]")){
				varType = varType.replace("[]", "\uFF3B\uFF3D");
			}
			if(varName!= null && varType!=null)
			{
				res = res + varName + ":" + varType;
			}
		}
		return res;
	}	 
}