package com.maapuu.mereca.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * json管理
 * 
 * @author Administrator
 * 
 */
public class JsonUtils {
	private static Gson gson = new Gson();

	public static <T> T object(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
	public static <T> String toJson(Class<T> param) {
		return gson.toJson(param);
	}
	
	/**
	 * 将JsonString 转成list对象
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T jsonObject(String json, TypeToken<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(json, type.getType());
	}
	
	/**
	 * json字符串转成对象
	 * 
	 * @param str
	 * @param type
	 * @return json字符串转换成javabean Person newPerson = JsonUtil.fromJson(jsonStr,
	 *         Person.class);
	 */
	public static <T> T fromJson(String str, Type type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

	/**
	 * 对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * 将JAVA对象转换成JSON字符串
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String simpleObjectToJsonStr(Object obj, List<Class> claList)
			throws IllegalArgumentException, IllegalAccessException {
		if (obj == null) {
			return "null";
		}
		String jsonStr = "{";
		Class<?> cla = obj.getClass();
		Field fields[] = cla.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getType() == long.class) {
				jsonStr += "\"" + field.getName() + "\":" + field.getLong(obj)
						+ ",";
			} else if (field.getType() == double.class) {
				jsonStr += "\"" + field.getName() + "\":"
						+ field.getDouble(obj) + ",";
			} else if (field.getType() == float.class) {
				jsonStr += "\"" + field.getName() + "\":" + field.getFloat(obj)
						+ ",";
			} else if (field.getType() == int.class) {
				jsonStr += "\"" + field.getName() + "\":" + field.getInt(obj)
						+ ",";
			} else if (field.getType() == boolean.class) {
				jsonStr += "\"" + field.getName() + "\":"
						+ field.getBoolean(obj) + ",";
			} else if (field.getType() == Integer.class
					|| field.getType() == Boolean.class
					|| field.getType() == Double.class
					|| field.getType() == Float.class
					|| field.getType() == Long.class) {
				jsonStr += "\"" + field.getName() + "\":" + field.get(obj)
						+ ",";
			} else if (field.getType() == String.class) {
				jsonStr += "\"" + field.getName() + "\":\"" + field.get(obj)
						+ "\",";
			} else if (field.getType() == List.class) {
				String value = simpleListToJsonStr((List<?>) field.get(obj),
						claList);
				jsonStr += "\"" + field.getName() + "\":" + value + ",";
			} else {
				if (claList != null && claList.size() != 0
						&& claList.contains(field.getType())) {
					String value = simpleObjectToJsonStr(field.get(obj),
							claList);
					jsonStr += "\"" + field.getName() + "\":" + value + ",";
				} else {
					jsonStr += "\"" + field.getName() + "\":null,";
				}
			}
		}
		jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		jsonStr += "}";
		return jsonStr;
	}

	/**
	 * 将JAVA的LIST转换成JSON字符串
	 * 
	 * @param list
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static String simpleListToJsonStr(List<?> list, List<Class> claList)
			throws IllegalArgumentException, IllegalAccessException {
		if (list == null || list.size() == 0) {
			return "[]";
		}
		String jsonStr = "[";
		for (Object object : list) {
			jsonStr += simpleObjectToJsonStr(object, claList) + ",";
		}
		jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		jsonStr += "]";
		return jsonStr;
	}

	/**
	 * 将JAVA的MAP转换成JSON字符串，
	 * 只转换第一层数据
	 * @param map
	 * @return
	 */
	public static String simpleMapToJsonStr(Map<?,?> map){
		if(map==null||map.isEmpty()){
			return "null";
		}
		String jsonStr = "{";
		Set<?> keySet = map.keySet();
		for (Object key : keySet) {
			jsonStr += "\""+key+"\":\""+map.get(key)+"\",";
		}
		jsonStr = jsonStr.substring(0,jsonStr.length()-1);
		jsonStr += "}";
		return jsonStr;
	}

	/**
	 *
	 * 函数名称: parseData
	 * 函数描述: 将json字符串转换为map
	 * @param data
	 * @return
	 */
	public static Map<String,String> jsonStrToMap(String data){
		GsonBuilder gb = new GsonBuilder();
		Gson g = gb.create();
		Map<String, String> map = g.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
		return map;
	}

	/**
	 * 将map转化为json字符串
	 *
	 * @param map
	 * @param keyName
	 * @param valueName
	 * @return
	 */
	public static String MapToJson(Map<? extends Object, ? extends Object> map,
								   String keyName, String valueName) {
		String string = "";
		if (map != null && keyName != null && valueName != null) {
			List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
			for (Map.Entry<? extends Object, ? extends Object> m : map.entrySet()) {
				Map<Object, Object> newMap = new HashMap<Object, Object>();
				newMap.put(keyName, m.getKey());
				newMap.put(valueName, m.getValue());
				list.add(newMap);
			}
			Gson gson = new Gson();
			string = gson.toJson(list);
		}
		return string;
	}
}
