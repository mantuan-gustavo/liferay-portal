/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.spring.aop;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.transaction.TransactionsUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopCacheManager {

	public ServiceBeanAopCacheManager(
		ChainableMethodAdvice chainableMethodAdvice) {

		List<ChainableMethodAdvice> fullChainableMethodAdvices =
			new ArrayList<>();

		while (chainableMethodAdvice != null) {
			chainableMethodAdvice.setServiceBeanAopCacheManager(this);

			if (chainableMethodAdvice instanceof
					AnnotationChainableMethodAdvice) {

				AnnotationChainableMethodAdvice<?>
					annotationChainableMethodAdvice =
						(AnnotationChainableMethodAdvice<?>)
							chainableMethodAdvice;

				Class<? extends Annotation> annotationClass =
					annotationChainableMethodAdvice.getAnnotationClass();

				_registerAnnotationChainableMethodAdvice(
					annotationClass, annotationChainableMethodAdvice);
			}

			fullChainableMethodAdvices.add(chainableMethodAdvice);

			chainableMethodAdvice =
				(ChainableMethodAdvice)
					chainableMethodAdvice.nextMethodInterceptor;
		}

		_fullChainableMethodAdvices = fullChainableMethodAdvices.toArray(
			new ChainableMethodAdvice[fullChainableMethodAdvices.size()]);
	}

	public <T> T findAnnotation(
		Class<?> targetClass, Method method,
		Class<? extends Annotation> annotationType, T defaultValue) {

		T annotation = _findAnnotation(
			_methodAnnotations, method, annotationType, defaultValue);

		if (annotation == null) {
			annotation = defaultValue;

			List<Annotation> annotations = AnnotationLocator.locate(
				method, targetClass);

			Iterator<Annotation> iterator = annotations.iterator();

			while (iterator.hasNext()) {
				Annotation curAnnotation = iterator.next();

				Class<? extends Annotation> curAnnotationType =
					curAnnotation.annotationType();

				if (!_annotationChainableMethodAdvices.containsKey(
						curAnnotationType)) {

					iterator.remove();
				}
				else if (annotationType == curAnnotationType) {
					annotation = (T)curAnnotation;
				}
			}

			if (annotations.isEmpty()) {
				_methodAnnotations.put(method, _nullAnnotations);
			}
			else {
				_methodAnnotations.put(
					method,
					annotations.toArray(new Annotation[annotations.size()]));
			}
		}

		return annotation;
	}

	public AopMethod getAopMethod(Object target, Method method) {
		if (!TransactionsUtil.isEnabled()) {
			return new AopMethod(target, method, _emptyChainableMethodAdvices);
		}

		return _aopMethods.computeIfAbsent(
			new CacheKey(target, method), this::_createAopMethod);
	}

	public void reset() {
		_aopMethods.clear();
	}

	private static <T> T _findAnnotation(
		Map<Method, Annotation[]> methodAnnotations, Method method,
		Class<? extends Annotation> annotationType, T defaultValue) {

		Annotation[] annotations = methodAnnotations.get(method);

		if (annotations == _nullAnnotations) {
			return defaultValue;
		}

		if (annotations == null) {
			return null;
		}

		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == annotationType) {
				return (T)annotation;
			}
		}

		return defaultValue;
	}

	private AopMethod _createAopMethod(CacheKey cacheKey) {
		Object target = cacheKey._target;

		Class<?> targetClass = target.getClass();

		Method method = cacheKey._method;

		List<ChainableMethodAdvice> filteredChainableMethodAdvices =
			new ArrayList<>();

		for (ChainableMethodAdvice chainableMethodAdvice :
				_fullChainableMethodAdvices) {

			if (chainableMethodAdvice.isEnabled(targetClass, method)) {
				filteredChainableMethodAdvices.add(chainableMethodAdvice);
			}
		}

		ChainableMethodAdvice[] chainableMethodAdvices =
			_emptyChainableMethodAdvices;

		if (!filteredChainableMethodAdvices.isEmpty()) {
			chainableMethodAdvices = filteredChainableMethodAdvices.toArray(
				new ChainableMethodAdvice
					[filteredChainableMethodAdvices.size()]);
		}

		return new AopMethod(target, method, chainableMethodAdvices);
	}

	private void _registerAnnotationChainableMethodAdvice(
		Class<? extends Annotation> annotationClass,
		AnnotationChainableMethodAdvice<?> annotationChainableMethodAdvice) {

		AnnotationChainableMethodAdvice<?>[] annotationChainableMethodAdvices =
			_annotationChainableMethodAdvices.get(annotationClass);

		if (annotationChainableMethodAdvices == null) {
			annotationChainableMethodAdvices =
				new AnnotationChainableMethodAdvice<?>[1];

			annotationChainableMethodAdvices[0] =
				annotationChainableMethodAdvice;
		}
		else {
			annotationChainableMethodAdvices = ArrayUtil.append(
				annotationChainableMethodAdvices,
				annotationChainableMethodAdvice);
		}

		_annotationChainableMethodAdvices.put(
			annotationClass, annotationChainableMethodAdvices);
	}

	private static final ChainableMethodAdvice[] _emptyChainableMethodAdvices =
		new ChainableMethodAdvice[0];
	private static final Annotation[] _nullAnnotations = new Annotation[0];

	private final Map
		<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			_annotationChainableMethodAdvices = new HashMap<>();
	private final Map<CacheKey, AopMethod> _aopMethods =
		new ConcurrentHashMap<>();
	private final ChainableMethodAdvice[] _fullChainableMethodAdvices;
	private final Map<Method, Annotation[]> _methodAnnotations =
		new ConcurrentHashMap<>();

	private static class CacheKey {

		@Override
		public boolean equals(Object obj) {
			CacheKey cacheKey = (CacheKey)obj;

			if (Objects.equals(_target, cacheKey._target) &&
				Objects.equals(_method, cacheKey._method)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _target);

			return HashUtil.hash(hash, _method);
		}

		private CacheKey(Object target, Method method) {
			_target = target;
			_method = method;
		}

		private final Method _method;
		private final Object _target;

	}

}