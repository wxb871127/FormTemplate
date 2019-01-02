package base.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

import util.StringUtil;

public class CommonUtil {
    /*
        采用后缀表达式 计算表达式是否成立
     */
    public static boolean compute(String expression, Map<String, Object> valueMap, boolean defaultValue) {
        if (TextUtils.isEmpty(expression)) {
            return defaultValue;
        }

        try {
            return compute(toSuffixExpression(toInfixExpression(expression, valueMap)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    private static class Item {

        private static final int OPERATOR = 0;//运算符
        private static final int OPERATOR_SINGLE = 1;//单目运算符
        private static final int VALUE = 2;
        private static final int BRACKET = 3;//括号

        private static final Map<String, Integer> OPERATOR_MAP = new HashMap<String, Integer>();

        static {
            OPERATOR_MAP.put("!", 2);
            OPERATOR_MAP.put(">", 6);
            OPERATOR_MAP.put(">=", 6);
            OPERATOR_MAP.put("<", 6);
            OPERATOR_MAP.put("<=", 6);
            OPERATOR_MAP.put("==", 7);
            OPERATOR_MAP.put("!=", 7);
            OPERATOR_MAP.put("contains", 7);
            OPERATOR_MAP.put("&&", 11);
            OPERATOR_MAP.put("||", 12);
        }

        private int type;

        public String operator;

        public float value;
        public boolean isShow;
        public String valueString;

        private boolean isBuild;

        public Item(String string, Map<String, Object> valueMap) throws Exception {
            if (string.equals("(") || string.equals(")")) {
                type = BRACKET;
                operator = string;
            } else if (string.equals(">")
                    || string.equals(">=")
                    || string.equals("<")
                    || string.equals("<=")
                    || string.equals("==")
                    || string.equals("!=")
                    || string.equals("&&")
                    || string.equals("||")
                    || string.equals("contains")) {
                type = OPERATOR;
                operator = string;
            } else if (string.equals("!")) {
                type = OPERATOR_SINGLE;
                operator = string;
            } else {
                type = VALUE;
                valueString = string;
                if (StringUtil.isBoolean(string)) {
                    isShow = Boolean.parseBoolean(string);
                } else if (StringUtil.isFloat(string)) {
                    value = Float.parseFloat(string);
                } else {
                    buildValue(valueMap);
                }
            }
        }

        private void buildValue(Map<String, Object> valueMap) {
            if (!isBuild) {
                isBuild = true;

                valueString = valueMap.get(valueString).toString();
                if (TextUtils.isEmpty(valueString)) {
                    value = Float.NaN;
                } else {
                    if (StringUtil.isFloat(valueString)) {
                        value = Float.parseFloat(valueString);
                    }
                }
            }
        }

        public Item(Boolean isShow) {
            type = VALUE;
            this.isShow = isShow;
        }

        public boolean isValue() {
            return type == VALUE;
        }

        public boolean isOperator() {
            return type == OPERATOR || type == OPERATOR_SINGLE;
        }

        public boolean isSingleOperator() {
            return type == OPERATOR_SINGLE;
        }

        public boolean isLeftBracket() {
            return operator.equals("(");
        }

        public boolean isRightBracket() {
            return operator.equals(")");
        }

        public boolean compareOperator(Item item) {
            return OPERATOR_MAP.get(operator) < OPERATOR_MAP.get(item.operator);
        }

    }

    //中缀表达式
    private static List<Item> toInfixExpression(String expression, Map<String, Object> valueMap) throws Exception {
        expression = expression.replaceAll(Pattern.quote("("), " ( ");
        expression = expression.replaceAll(Pattern.quote(")"), " ) ");
        expression = expression.replaceAll(Pattern.quote("!="), "_notequal");
        expression = expression.replaceAll(Pattern.quote("!"), " ! ");
        expression = expression.replaceAll(Pattern.quote("_notequal"), "!=");

        List<Item> items = new ArrayList<Item>();
        String[] strings = expression.split("\\s+");//多个空格切割的

        for (String string : strings) {
            if (!TextUtils.isEmpty(string)) {
                Item item = new Item(string, valueMap);
                items.add(item);
            }
        }

        for (int i = 1; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.isOperator()
                    && (item.operator.equals(">")
                    || item.operator.equals(">=")
                    || item.operator.equals("<")
                    || item.operator.equals("<=")
                    || item.operator.equals("==")
                    || item.operator.equals("!=")
                    || item.operator.equals("contains"))) {
                Item lastItem = items.get(i - 1);
                lastItem.buildValue(valueMap);
            }
        }

        return items;
    }

    //后缀表达式 关键算法：栈s1最终用来存放表达式的最小计算单元（左值、右值、运算符也就是后缀表达式）
    //通过优先级排序实现
    private static Stack<Item> toSuffixExpression(List<Item> items) {
        Stack<Item> s1 = new Stack<Item>();
        Stack<Item> s2 = new Stack<Item>();

        for (Item item : items) {
            if (item.isValue()) {
                s2.push(item);
            } else if (item.isOperator()) {
                boolean itemPushed = false;
                while (!itemPushed) {
                    if (s1.isEmpty() || "(".equals(s1.peek().operator)) {//出栈： peek方法不删除栈顶，pop是删除栈顶
                        s1.push(item);
                        itemPushed = true;
                    } else if (item.compareOperator(s1.peek())) {//优先级高的入栈s1
                        s1.push(item);
                        itemPushed = true;
                    } else {
                        s2.push(s1.pop());
                    }
                }
            } else {
                if (item.isLeftBracket()) {
                    s1.push(item);
                } else if (item.isRightBracket()) {
                    while (!s1.peek().isLeftBracket()) {
                        s2.push(s1.pop());
                    }
                    s1.pop();
                }
            }
        }
        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }

        return reverse(s2);
    }

    private static Stack<Item> reverse(Stack<Item> s) {
        Stack<Item> result = new Stack<Item>();
        while (!s.isEmpty()) {
            result.push(s.pop());
        }
        return result;
    }

    //逐个计算后缀表达式，计算出结果
    private static boolean compute(Stack<Item> s) {
        Stack<Item> result = new Stack<Item>();
        while (!s.isEmpty()) {
            Item item = s.pop();
            if (item.isValue()) {
                result.push(item);
            } else if (item.isOperator()) {
                String operator = item.operator;
                Item right = result.pop();
                boolean isShow = true;
                if (item.isSingleOperator()) {
                    if (operator.equals("!")) {
                        isShow = !right.isShow;
                    }
                } else {
                    Item left = result.pop();

                    if (operator.equals("==")) {
                        isShow = left.value == right.value;
                    } else if (operator.equals("!=")) {
                        isShow = left.value != right.value;
                    } else if (operator.equals(">=")) {
                        isShow = left.value >= right.value;
                    } else if (operator.equals("<=")) {
                        isShow = left.value <= right.value;
                    } else if (operator.equals("<")) {
                        isShow = left.value < right.value;
                    } else if (operator.equals(">")) {
                        isShow = left.value > right.value;
                    } else if (operator.equals("&&")) {
                        isShow = left.isShow && right.isShow;
                    } else if (operator.equals("||")) {
                        isShow = left.isShow || right.isShow;
                    } else if (operator.equals("contains")) {
                        if (TextUtils.isEmpty(left.valueString)) {
                            isShow = false;
                        } else {
                            isShow = Arrays.asList(left.valueString.split(";"))
                                    .contains(right.valueString);
                        }
                    }
                }
                Item itemResult = new Item(isShow);
                result.push(itemResult);
            }
        }
        return result.pop().isShow;
    }
}
