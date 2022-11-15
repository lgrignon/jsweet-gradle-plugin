/* Generated from Java with JSweet 3.2.0-SNAPSHOT - http://www.jsweet.org */
class IsTestCondition extends BooleanCondition {
    /**
     * 
     * @param {*} context
     * @return {boolean}
     */
    public evaluate(context: ExecutionContext): boolean {
        return !context.isVariableEmpty("test_variable");
    }
}
IsTestCondition["__class"] = "IsTestCondition";



