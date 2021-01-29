
import def.execcontext.BooleanCondition;
import def.execcontext.ExecutionContext;

public class IsTestCondition extends BooleanCondition {

	@Override
	public boolean evaluate(final ExecutionContext context) {

		return !context.isVariableEmpty("test_variable");
	}
}
