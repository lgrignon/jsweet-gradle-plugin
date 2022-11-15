/* Generated from Java with JSweet 3.2.0-SNAPSHOT - http://www.jsweet.org */
class IsTestCondition extends BooleanCondition {
    /**
     *
     * @param {*} context
     * @return {boolean}
     */
    evaluate(context) {
        return !context.isVariableEmpty("test_variable");
    }
}
IsTestCondition["__class"] = "IsTestCondition";
