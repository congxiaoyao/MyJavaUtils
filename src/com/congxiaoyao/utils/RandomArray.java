package com.congxiaoyao.utils;

import java.util.Random;

/**
 * �������ڲ���һ������һ�鲻�ظ������������
 * Ϊ���������Ч�ʣ������ÿռ任ʱ�䣬���������Ѿ����ڵ��������¼������ʹ��ÿ�β��ص�ʱ�临�ӶȽ�ΪO(1)
 * ��ȱ�㱩©���ɣ�����Ϊ������һ����Χ��int�ڵ�������飬���ཫ������500MB+�ľ����ڴ棬ʹ��ʱ������
 * �ڴ����Ĵ�Сȡ�����������Χ������������һ������
 * 
 * @author congxiaoyao
 * @date 2015.8.7
 */

public class RandomArray {
	
	private Random random;		//���ڲ����������Random��
	
	private int range;			//�������ȡֵ��Χ�ĳ���
	private int start;			//�����ȡֵ��Χ�Ŀ�ʼ
	
	private int[] allocted;		//Ϊ�˱����ظ����������ڼ�¼��һ��ֵ�Ѿ�����ȡ����
	
	/**
	 * ȡֵ��Χ[start,end),�������ظ������������
	 * @param start ������Ĳ�����Χ�Ŀ�ʼ
	 * @param end ���������е��������Զ�����ܴ��ڵ���end
	 */
	public RandomArray(int start , int end) {
		this.range = end - start;
		this.start = start;
		this.random = new Random();
		this.allocted = new int[(range/32)+1];
	}
	
	public RandomArray(int range){
		this(0, range);
	}
	
	/**
	 * ����һ�����ظ������������
	 * @param length ����ĳ���
	 * @return ��������� �������length����range��С�ڵ���0ʱ������null
	 */
	public int[] getRandomArray(int length){
		if(length > range || length <= 0) return null;
		
		int[] randomArray = new int[length];
		int number = 0;
		for(int i=0;i<length;i++){
			while(isExist(number = random.nextInt(range)));
			randomArray[i] = start + number;
			allocted[number/32] = setBitTrue(allocted[number/32], number%32);
		}
		
		return randomArray;
	}
	
	/**
	 * �ж�һ��������Ƿ��Ѿ�����������֮��
	 * @param number 
	 * @return ���ڷ���true���򷵻�false
	 */
	private boolean isExist(int number){
		int src = allocted[number/32];
		int index = number % 32;
		return getBitValue(src, index) == 1;
	}
	
	
	/**
	 * ����һ��������ĳһλΪ1
	 * @param src Դ��������Ҳ���Ǳ�����ĳһλΪ1������
	 * @param index ȡֵ��Χ[0-31]
	 * @return ������ɺ��������ԭ������û�ˣ���ϧ
	 */
	private static int setBitTrue(int src , int index){
		return src | (0x01 << index);
	}
	
	/**
	 * ��ȡһ�������е�ĳһλ��ֵ
	 * @param src Դ������
	 * @param index ȡֵ��Χ[0-31]
	 * @return 0��1����һ��int��̫�˷ѣ���ϧ
	 */
	private static int getBitValue(int src , int index){
		return (src >> index) & 0x01;
	}
}