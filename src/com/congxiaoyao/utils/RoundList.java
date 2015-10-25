package com.congxiaoyao.utils;

import java.util.Iterator;

/**
 * ����һ���д�С���Ƶ�������Ԫ�ؿ�����ArrayListһ������ӵ�RoundList��
 * ����ӵ�Ԫ�ظ����������Ƹ���ʱ����һ������ӵ�Ԫ�ؽ��ᱻ���������Ƴ�
 * ���ڶ���Ԫ�ؽ���Ϊ��һ��Ԫ�أ�����ӵ�Ԫ�ؽ���Ϊ���һ��
 * �Դ����ƣ�����ǰ��ı�����ӵļ���ȥһ�����е�������Բ�ζ���
 * ���Ԫ�ع��̲�������Ԫ��λ�ơ����鿽���Ȳ�������������Ч
 * �򵥵Ĺ����๦�ܲ��ֻ࣬ʵ�������õķ��������ṩ�Ƴ�����Ԫ�صĲ�������Ϊ��ӱ������ɾ������
 * @author congxiaoyao
 *
 * @param <T>
 * @date 2015.8.1		v1.0
 * @date 2015.9.12		v1.1
 */
public class RoundList<T> implements Iterable<T> {
	
	private int tail = 0;		//roundlist��βָ�룬������Ԫ�ص�����
	private int size = 0;		//��ǰroundlist��ʵ�ʴ�ŵĶ���ĸ���
	
	private Object[] objects;	//���ڴ�ŷ��͵�object����
	
	/**
	 * ͨ��һ���������ƴ�С������һ��roundlist
	 * ��roundlist�д洢��Ԫ����������limitSizeʱ�����类�洢��Ԫ�ؽ�������roundlist
	 * @param limitSize ������ɵ�Ԫ�صĸ�����Ҳ�������Ƹ���
	 */
	public RoundList(int limitSize) {
		objects = new Object[limitSize];
	}
	
	/**
	 * ���һ��Ԫ�أ����roundlist�е�Ԫ�ظ����������Ƶ������������类��ӵ�Ԫ�ؼ���
	 * @param t Ҫ����ӵ�Ԫ��
	 */
	public void add(T t) {
		//��βָ�봦���һ��Ԫ��
		objects[tail] = t;
		//������������Ԫ��ʵ�ʴ�ŵĶ���ĸ���
		if(size < objects.length) size++;
		//�ƶ�βָ��
		tail = (tail+1) % objects.length;
	}

	/**
	 * ��Ԫ�س����������ʱ��Ϊ�˻�ȡ�����ӵ�һ��Ԫ�أ�����Ӧ�ô���limitSize-1
	 * ����������Ԫ���򲻸�����
	 * @param index Ԫ����roundlist�е�����
	 * @return ��ȡ�߼���roundlist��Ӧ����������ŵ�Ԫ��
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		return (T) objects[(tail + index) % size];
	}
	
	/**
	 * @return �õ�roundList�е����һ��Ԫ��
	 */
	public T getLast(){
		if(size == 0)
			return null;
		return get(size-1);
	}
	
	/**
	 * @return �õ�roundList�еĵ�һ��Ԫ��
	 */
	public T getFirst(){
		if(size == 0)
			return null;
		return get(0);
	}
	
	/**
	 * �Ƴ�roundlist�����е�Ԫ��
	 */
	public void removeAll(){
		//�Ƴ�ȫ����ʵ������Ҫ�����е�Ԫ����Ϊnull��ֻ��Ҫ�ı�βָ��λ�ü���С�Ϳ���
		tail = 0;
		size = 0;
	}
	
	/**
	 * �ú����ķ���ֵ��ΧΪ[0-limitSize]
	 * ����ӵ�Ԫ��������limitSizeʱ���˷������Ƿ���limitSize
	 * @return roundlist����ʵ�洢��Ԫ�صĸ��� 
	 */
	public int size(){
		return size;
	}
	
	/**
	 * ����һ��roundlist�ĵ�����������roundlistҲ֧��foreach������~
	 */
    public Iterator<T> iterator() {
        return new Itr();
    }
	
    /**
     *iterator��ʵ����
     */
	private class Itr implements Iterator<T>
	{
		private int index = -1;
		
		@Override
		public boolean hasNext() {
			return index < size -1;
		}

		@Override
		public T next() {
			return get(++index);
		}
		
	}
}