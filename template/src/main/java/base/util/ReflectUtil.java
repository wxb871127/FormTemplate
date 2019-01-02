package base.util;

import java.lang.reflect.Field;

public class ReflectUtil {

	public static final int BOOLEAN = 0;
	public static final int BYTE =  1;
	public static final int SHORT = 2;
	public static final int INT = 3;
	public static final int LONG = 4;
	public static final int FLOAT = 5;
	public static final int DOUBLE = 6;
	public static final int STRING = 7;
	public static final int DATE = 8;
	public static final int OBJECT = 9;
	
	public static int getClassType(Class<?> cls) {
		String type = cls.getName();
		if (type.equals("java.lang.Boolean") || type.equals("boolean")) {
			return BOOLEAN;
		} else if (type.equals("java.lang.Byte") || type.equals("byte")) {
			return BYTE;
		} else if (type.equals("java.lang.Short") || type.equals("short")) {
			return SHORT;
		} else if (type.equals("java.lang.Integer") || type.equals("int")) {
			return INT;
		} else if (type.equals("java.lang.Long") || type.equals("long")) {
			return LONG;
		} else if (type.equals("java.lang.Float") || type.equals("float")) {
			return FLOAT;
		} else if (type.equals("java.lang.Double") || type.equals("double")) {
			return DOUBLE;
		} else if (type.equals("java.lang.String")) {
			return STRING;
		} else if (type.equals("java.util.Date")) {
			return DATE;
		} else {
			return OBJECT;
		}
	}
	
	public static int getFieldType(Field field) {
		return getClassType(field.getType());
	}
	
	public static void setFiled(Object object, Field field, String value) {
		try {
			int type = getFieldType(field);
			switch (type) {
			case BOOLEAN:
				field.setBoolean(object, Boolean.valueOf(value));
				break;
			case BYTE:
				field.setByte(object, Byte.valueOf(value));
				break;
			case SHORT:
				field.setShort(object, Short.valueOf(value));
				break;
			case INT:
				field.setInt(object, Integer.valueOf(value));
				break;
			case LONG:
				field.setLong(object, Long.valueOf(value));
				break;
			case FLOAT:
				field.setFloat(object, Float.valueOf(value));
				break;
			case DOUBLE:
				field.setDouble(object, Double.valueOf(value));
				break;
			case STRING:
				field.set(object, value);
				break;
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
