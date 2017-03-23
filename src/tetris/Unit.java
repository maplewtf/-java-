package tetris;

import java.awt.Point;
/*
 * ����λ�ú���״,�ͱ任������
 */
public class Unit {
	private Point location; //λ��
	private int[] key;      //��״����
	
	public Unit(Point l) {
		location = l;
		key = Shape.getRandomKey();
	}
	
	public Unit(Point l,int[] k) {
		location = l;
		key = k;
	}
	
	//��ȡ��ǰ��״
	public Point[] getPaintShape() {
		return Shape.getKeyShape(key);
	}
	
	//��ȡ��ǰ��״������
	public Point[] getPaintLocation() {
		Point[] s = Shape.getKeyShape(key);
		Point[] p = new Point[s.length];
		
		
		for(int i = 0; i < s.length; i++) {
			p[i] = new Point(s[i].x + location.x,s[i].y + location.y);
		}
		return p;
	}
	
	//����ָ��ı���״
	public Unit getChangeUnit(boolean isShapeChanged,int x,int y) {
		Point l = new Point(location.x + x, location.y + y);
		int[] k;
		if(isShapeChanged) 
			k = Shape.getNextKey(key);
		else
			k = new int[]{this.key[0],this.key[1]};
		return new Unit(l,k);
	}
	
	
}
