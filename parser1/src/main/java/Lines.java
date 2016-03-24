
public interface Lines {
	
	String EXTENDS_ARROW = "-^";
	String IMPLEMENTS_ARROW = "-.-^";
	String USES_ARROW = "uses-.->";
	
	String HAS_ARROW_UNI = "--1";
	String HAS_MANY_ARROW_UNI = "--*";
	/* bidirectional has arrows*/
	
	String HAS_ARROW_BI = "1--1";
	String HAS_ARROW_ONE_TO_MANY_BI = "1--*";
	String HAS_ARROW_MANY_TO_ONE_BI = "*--1";
	String HAS_ARROW_MANY_TO_MANY_BI = "*--*";
	
}