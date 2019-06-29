package com.dn.spring.aop.pointcut;

import java.lang.reflect.Method;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

public class AspectJExpressionPointcut implements Pointcut {

	private static PointcutParser pp = PointcutParser
			.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

	private String expression;

	private PointcutExpression pe;

	public AspectJExpressionPointcut(String expression) {
		super();
		this.expression = expression;
		pe = pp.parsePointcutExpression(expression);
	}

	/**
	 * 用PointcutExpression匹配类，不可靠。没关系，通过匹配方法来正确匹配
	 */
	@Override
	public boolean matchsClass(Class<?> targetClass) {
		return pe.couldMatchJoinPointsInType(targetClass);
	}

	/**
	 * 用PointcutExpression匹配方法，可靠
	 */
	@Override
	public boolean matchsMethod(Method method, Class<?> targetClass) {
		ShadowMatch sm = pe.matchesMethodExecution(method);
		return sm.alwaysMatches();
	}

	public String getExpression() {
		return expression;
	}

}
