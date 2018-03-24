package tool;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

import tool.function.FunctionUtils;

public class JsonUtils {
	public static JsonNumber buildJsonNumber(int number) {
		String key = "number";
		return Json.createObjectBuilder().add(key, number).build().getJsonNumber(key);
	}

	public static JsonString buildJsonString(String string) {
		String key = "number";
		return Json.createObjectBuilder().add(key, string).build().getJsonString(key);
	}

	public static JsonArray arrayToJsonArray(int... array) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		FunctionUtils.forEachInt(array, jsonArrayBuilder::add);
		return jsonArrayBuilder.build();
	}

	public static JsonArray arrayToJsonArray(String... array) {
		return arrayToJsonArray(array, JsonUtils::buildJsonString);
	}

	public static JsonArray arrayToJsonArray(JsonValue... array) {
		return arrayToJsonArray(array, FunctionUtils::returnSelf);
	}

	public static <T> JsonArray arrayToJsonArray(T[] array, Function<T, JsonValue> mapper) {
		return arrayToJsonArray(FunctionUtils.stream(array), mapper);
	}

	public static <T> JsonArray arrayToJsonArray(Collection<T> collection, Function<T, JsonValue> mapper) {
		return arrayToJsonArray(collection.stream(), mapper);
	}

	public static <T> JsonArray arrayToJsonArray(Stream<T> stream, Function<T, JsonValue> mapper) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		stream.map(mapper).forEach(jsonArrayBuilder::add);
		return jsonArrayBuilder.build();
	}

	public static JsonObject replace(JsonObject source, String replacedKey, UnaryOperator<JsonValue> replacedValue) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		source.forEach(builder::add);
		builder.add(replacedKey, replacedValue.apply(source.get(replacedKey)));
		return builder.build();
	}

	public static int[] getIntArray(JsonArray array) {
		return array.getValuesAs(JsonNumber.class).stream().mapToInt(JsonNumber::intValue).toArray();
	}

	public static int[] getIntArray(JsonObject json, String key) {
		return getIntArray(json.getJsonArray(key));
	}

	public static double[] getDoubleArray(JsonArray array) {
		return array.getValuesAs(JsonNumber.class).stream().mapToDouble(JsonNumber::doubleValue).toArray();
	}

	public static double[] getDoubleArray(JsonObject json, String key) {
		return getDoubleArray(json.getJsonArray(key));
	}

	public static String[] getStringArray(JsonArray array) {
		return array.getValuesAs(JsonString.class).stream().map(JsonString::getString).toArray(String[]::new);
	}

	public static String[] getStringArray(JsonObject json, String key) {
		return getStringArray(json.getJsonArray(key));
	}
}
