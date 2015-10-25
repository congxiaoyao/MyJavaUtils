package com.congxiaoyao.utils;

import java.util.Random;

/**
 * 此类用于产生一个包含一组不重复随机数的数组
 * 为了提高生成效率，这里用空间换时间，将数组中已经存在的随机数记录下来，使得每次查重的时间复杂度降为O(1)
 * 但缺点暴漏无疑，若是为了生成一个范围在int内的随机数组，此类将会申请500MB+的惊人内存，使用时请斟酌
 * 内存消耗大小取决于随机数范围，但足以满足一般需求
 * 
 * @author congxiaoyao
 * @date 2015.8.7
 */

public class RandomArray {
	
	private Random random;		//用于产生随机数的Random类
	
	private int range;			//随机数的取值范围的长度
	private int start;			//随机数取值范围的开始
	
	private int[] allocted;		//为了避免重复，数组用于记录哪一个值已经被获取过了
	
	/**
	 * 取值范围[start,end),产生不重复的随机数数组
	 * @param start 随机数的产生范围的开始
	 * @param end 产生的所有的随机数永远不可能大于等于end
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
	 * 创建一个不重复的随机数数组
	 * @param length 数组的长度
	 * @return 随机数数组 当传入的length大于range或小于等于0时将返回null
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
	 * 判断一个随机数是否已经存在于数组之中
	 * @param number 
	 * @return 存在返回true否则返回false
	 */
	private boolean isExist(int number){
		int src = allocted[number/32];
		int index = number % 32;
		return getBitValue(src, index) == 1;
	}
	
	
	/**
	 * 设置一个整数的某一位为1
	 * @param src 源操作数，也就是被设置某一位为1的整数
	 * @param index 取值范围[0-31]
	 * @return 设置完成后的整数，原来的数没了，可惜
	 */
	private static int setBitTrue(int src , int index){
		return src | (0x01 << index);
	}
	
	/**
	 * 获取一个整数中的某一位的值
	 * @param src 源操作数
	 * @param index 取值范围[0-31]
	 * @return 0或1但是一个int，太浪费，可惜
	 */
	private static int getBitValue(int src , int index){
		return (src >> index) & 0x01;
	}
}