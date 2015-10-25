package com.congxiaoyao.utils;

/**
 * ������	���ַ������������y=f(x)��һԪn�η���
 * 		ͨ�����ַ������ô��ⷽ�̣�
 * 		1��ʵ�����ڽӿ�Function�е�f(float x)������ͨ��setFunction(Function f)��������
 * 		2�����ิдRootFinder�е�f(float x)����
 * 		ͨ������getRoot(float fromX, float toX, float y)���ɻ�÷��̵ĸ�
 * ע�⣺	��ǰ�汾����Ҫ��֤�����������ڵ����ݼ���һ���н� ������ܻ���ֶ�ջ����쳣
 * ���ڣ�	15.4.18
 * �汾��	1.01
 * @author ����ң
 *
 */
public class RootFinder {
	
	private Function function;
	private double precision = 0.001;
	
	//ͨ��Function�ӿ������ô��ⷽ��
	public RootFinder(Function function) {
		this.function = function;
	}
	//ͨ����дf(float x)�����ô��ⷽ��
	public RootFinder() {
	}
	
	/**
	 * ��֤���ù����̲��ܵ��ô˷��� ��ȡ����y=f(x)x��ֵ
	 * @param fromX	���俪ʼ
	 * @param toX	�������
	 * @param y		Ŀ�꺯��ֵ
	 * @return		���̵Ľ�
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
	 * binarySearch1��binarySearch2����һ�� �������еı���result�Ļ�ȡ��ʽ
	 * ������ͨ��Function�ӿڻ�ȡreslut
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
	//������ͨ������f(float x)������ȡreslut����ֵ
	private float binarySearch2(float from, float to, float target) {
		
		float maybe = (from+to)/2;
		float result = f(maybe);
		
		if(result - target > precision)
			return binarySearch2(maybe, to, target);
		else if(result - target < -precision)
			return binarySearch2(from, maybe, target);
		else return maybe;
	}
	
	//����д�ķ��̱��ʽ
	public float f(float x){return 0;}
	
	//���뷽�̱��ʽ
	public void setFunction(Function function){
		this.function = function;
	}
	
	//���̱��ʽ�ӿ�
	public interface Function{
		float f(float x);
	}
	
	public void setPrecision(double precision) {
		this.precision = precision;
	}	
}
