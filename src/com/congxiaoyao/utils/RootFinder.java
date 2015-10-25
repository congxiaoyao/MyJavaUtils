package com.congxiaoyao.utils;

/**
 * 描述：	二分法求解所有形如y=f(x)的一元n次方程
 * 		通过两种方法设置待解方程：
 * 		1、实现类内接口Function中的f(float x)方法并通过setFunction(Function f)方法设置
 * 		2、子类复写RootFinder中的f(float x)方法
 * 		通过调用getRoot(float fromX, float toX, float y)即可获得方程的根
 * 注意：	当前版本必须要保证函数在区间内单调递减且一定有解 否则可能会出现堆栈溢出异常
 * 日期：	15.4.18
 * 版本：	1.01
 * @author 丛逍遥
 *
 */
public class RootFinder {
	
	private Function function;
	private double precision = 0.001;
	
	//通过Function接口来设置待解方程
	public RootFinder(Function function) {
		this.function = function;
	}
	//通过复写f(float x)来设置待解方程
	public RootFinder() {
	}
	
	/**
	 * 保证设置过方程才能调用此方法 获取方程y=f(x)x的值
	 * @param fromX	区间开始
	 * @param toX	区间结束
	 * @param y		目标函数值
	 * @return		方程的解
	 */
	public float getRoot(float fromX, float toX, float y) {
		if(function == null)
		{
			if(Math.abs(f(fromX)-y)<precision)
				return fromX;
			else if(Math.abs(f(toX)-y)<precision)
				return toX;
			else return binarySearch2(fromX,toX,y);
		}else {
			if(Math.abs(function.f(fromX)-y)<precision)
				return fromX;
			else if(Math.abs(function.f(toX)-y)<precision)
				return toX;
			else return binarySearch1(fromX,toX,y);
		}
	}

	/**
	 * binarySearch1跟binarySearch2基本一样 就是其中的变量result的获取方式
	 * 本函数通过Function接口获取reslut
	 * @param from
	 * @param to
	 * @param target
	 * @return
	 */
	private float binarySearch1(float from, float to, float target) {
		
		float maybe = (from+to)/2;
		float result = function.f(maybe);
		
		if(result - target > precision)
			return binarySearch1(maybe, to, target);
		else if(result - target < -precision)
			return binarySearch1(from, maybe, target);
		else return maybe;
	}
	//本函数通过类内f(float x)方法获取reslut变量值
	private float binarySearch2(float from, float to, float target) {
		
		float maybe = (from+to)/2;
		float result = f(maybe);
		
		if(result - target > precision)
			return binarySearch2(maybe, to, target);
		else if(result - target < -precision)
			return binarySearch2(from, maybe, target);
		else return maybe;
	}
	
	//待复写的方程表达式
	public float f(float x){return 0;}
	
	//传入方程表达式
	public void setFunction(Function function){
		this.function = function;
	}
	
	//方程表达式接口
	public interface Function{
		float f(float x);
	}
	
	public void setPrecision(double precision) {
		this.precision = precision;
	}	
}
