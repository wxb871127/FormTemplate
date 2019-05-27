package base.util;

import android.text.TextUtils;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.ql.util.express.Operator;

import java.util.Map;

public class ExpressionUtil {
    public static ExpressionUtil expressionUtil = null;
    public static ExpressRunner runner;

    private ExpressionUtil(){

    }

    public static class Contains extends Operator {
        public Boolean executeInner(Object[] list) throws Exception {
            String opdata1 = list[0].toString();
            String opdata2 = list[1].toString();
            return opdata1.contains(opdata2);
        }
    }

    public static class Equals extends Operator{
        public Boolean executeInner(Object[] list) throws Exception{
            String opdata1 = list[0].toString();
            String opdata2 = list[1].toString();
            return opdata1.equals(opdata2);
        }
    }

    public static ExpressionUtil getExpressionUtil(){
        if(expressionUtil == null){
            synchronized (ExpressionUtil.class) {
                if (expressionUtil == null) {
                    expressionUtil = new ExpressionUtil();
                    try {
                        initExpressRunner();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return expressionUtil;
    }

    private static void initExpressRunner() throws Exception{
        runner = new ExpressRunner(true,false);
        runner.addFunctionOfClassMethod("abs", Math.class.getName(), "abs",
                new String[] { "double" }, null);
        runner.addFunctionOfClassMethod("pow", Math.class.getName(), "pow",
                new String[] { "double", "double" }, null);
        runner.addOperator("contains", new Contains());
        runner.addOperator("equals", new Equals());
    }

    public Object executeExpression(String expression, Map<String, Object> map){
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.putAll(map);
        Object object = null;
        try {
            object = runner.execute(expression, context, null, true, true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }

    public boolean logicExpression(String expression, Map<String, Object> map, boolean defaultValue) throws Exception{
        if (TextUtils.isEmpty(expression)) {
            return defaultValue;
        }
        IExpressContext<String, Object> context = new DefaultContext<>();
        ((DefaultContext<String, Object>) context).putAll(map);
        Boolean result = (Boolean)runner.execute(expression, context, null, true, false);
        return result;
    }

}
