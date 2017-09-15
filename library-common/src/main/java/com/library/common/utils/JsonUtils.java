package com.library.common.utils;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * json转换工具类
 * 
 * @author Wesly186
 *
 */
public class JsonUtils {

    private static final Gson GSON = new GsonBuilder()
	    .registerTypeAdapter(Date.class, new JsonDeserializer<java.util.Date>() {

		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		    return new Date(json.getAsJsonPrimitive().getAsLong());
		}

	    }).registerTypeAdapter(Date.class, new JsonSerializer<Date>() {

		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		    return new JsonPrimitive(src.getTime());
		}

	    }).create();

    /**
     * 将对象转换成json字符串
     * 
     * @param data
     * @return
     */
    public static String toJson(Object data) {
	String retvl = null;
	try {
	    retvl = GSON.toJson(data);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return retvl;
    }

    public static <T> T fromJson(String jsonData, Class<T> clazz) {

	T t = null;
	try {
	    t = GSON.fromJson(jsonData, clazz);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return t;
    }

    public static <T> T fromJson(String jsonData, Type type) {

	T t = null;
	try {
	    t = GSON.fromJson(jsonData, type);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return t;
    }
}
