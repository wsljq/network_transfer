package com.antfact.twitter.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import org.apache.commons.codec.binary.Base64;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;

/**
 * <p>
 * 功能描述,该部分必须以中文句号结尾。
 * </p>
 * <p>
 * 创建日期 2018/3/20
 *
 * @author kevin
 */
public class GodSerializer {
	public static final String UTF8 = "UTF-8";
	public static final int KB = 1024;

	private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {
		@Override
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
//			kryo.setRegistrationRequired(false);
			
			/**
			 * 不要轻易改变这里的配置！更改之后，序列化的格式就会发生变化， 上线的同时就必须清除 Redis 里的所有缓存，
			 * 否则那些缓存再回来反序列化的时候，就会报错
			 */
			// 支持对象循环引用（否则会栈溢出）
			kryo.setReferences(true); // 默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置

			// 不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
			kryo.setRegistrationRequired(false); // 默认值就是
													// false，添加此行的目的是为了提醒维护者，不要改变这个配置

			// Fix the NPE bug when deserializing Collections.
			((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
					.setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
			
			return kryo;
		}
	};

	public static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}
		Kryo kryo = kryoThreadLocal.get();
		Output output = new Output(KB, KB * KB);
		kryo.writeClassAndObject(output, object);
		return output.toBytes();
	}

	public static String serialize2Str(Object object) {
		if (object == null) {
			return null;
		}
		Kryo kryo = kryoThreadLocal.get();
		Output output = new Output(KB, KB * KB);
		kryo.writeClassAndObject(output, object);
		byte[] b = output.toBytes();

		return new String(new Base64().encode(b));
	}

	public static Object deSerialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		Input input = new Input(bytes);
		Kryo kryo = kryoThreadLocal.get();
		return kryo.readClassAndObject(input);
	}

	public static Object deSerialize4Str(String str) {
		if (str == null) {
			return null;
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(str));
		Input input = new Input(bais);
		Kryo kryo = kryoThreadLocal.get();
		return kryo.readClassAndObject(input);
	}
}
