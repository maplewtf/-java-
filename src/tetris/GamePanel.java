package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel{

	private JLabel[][] box = null;
	private Unit movingUnit;//��ǰ���ƶ��ķ���
	private LinkedList<Unit> nextUnit;  //�·���
	public GamePanel(int x,int y) {
		/*
		 * ����Ϸ���
		 */
		super();
		this.setLayout(new GridLayout(x,y));
		box = new JLabel[x][y];
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				box[i][j] = new JLabel();
				box[i][j].setBackground(Color.yellow);
				box[i][j].setPreferredSize(new Dimension(30,30));
				box[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.black));
				this.add(box[i][j]);
			}
		}
		
		nextUnit = new LinkedList<Unit>();
	}
	
	//��������Ϸ
	public void resetGame() {
		for(int i=0;i<box.length;i++)
			 for(int j=0;j<box[0].length;j++)
					 box[i][j].setOpaque(false);
		movingUnit = null;
		nextUnit.clear();
	}
	
	//��������
	public Unit createNextUnit() {
		if(nextUnit.isEmpty()) {
			nextUnit.add(new Unit(new Point(0,box[0].length/2-1)));
		}
		nextUnit.add(new Unit(new Point(0,box[0].length/2-1)));
		return nextUnit.getLast();
	}
	
	//��÷���
	public boolean getNextUnit() {
		return checkPaint(nextUnit.poll());
	}
	
	//�ƶ���任���飬�������䵽�ײ�������false�����򷵻�true
	public synchronized boolean moveUnit(int k) {
		if(movingUnit == null) return true;
		//0�任������������
		switch(k) {
		case 0://�任��״ʱ�����жϣ����ⳤ��û���任
			if(!checkPaint(movingUnit.getChangeUnit(true, 0, 0)))
				if(!checkPaint(movingUnit.getChangeUnit(true, 0, -1)))
					if(!checkPaint(movingUnit.getChangeUnit(true, 0, -2)))
						if(!checkPaint(movingUnit.getChangeUnit(true, 0, -3)));
			break;
		case 1:
			checkPaint(movingUnit.getChangeUnit(false, 0, -1));
			break;
		case 2:
			checkPaint(movingUnit.getChangeUnit(false, 0, 1));
			break;
		case 3:
			return checkPaint(movingUnit.getChangeUnit(false, 1, 0));
		}
		return true;
			
	}
	
	//�������Ƿ��ܻ���
	public synchronized boolean checkPaint(Unit unit) {
		List<Point> in = new ArrayList<Point>();
		for(Point n:unit.getPaintLocation())
			in.add(n);
		if(movingUnit != null) { //�Ѿ������ĵ㲻���ټ����
			for(Point n:movingUnit.getPaintLocation())
				in.remove(n);
		}
		for(Point u:in) {
			if(u.x < 0 || u.x >= box.length || u.y < 0 || u.y >= box[0].length || box[u.x][u.y].isOpaque())
				return false;
		}
		if(movingUnit!=null)
			for(Point u:movingUnit.getPaintLocation())
				box[u.x][u.y].setOpaque(false);
		movingUnit = unit;
		for(Point u:movingUnit.getPaintLocation())
			box[u.x][u.y].setOpaque(true);
		updateUI();
		return true;
		
	}
	
	//��ȥ�У���������
	public int removeRow() {
		movingUnit = null;
		int count = 0;
		for(int i = box.length - 1; i >= 0; i--) {
			//�ж���û���ж�����
			int j;
			for(j = 0; j < box[0].length; j++) {
				if(!box[i][j].isOpaque()) {
					//System.out.println(box[i][j].isOpaque());
					break;
				}
			}
			//System.out.println("removeRow");
			if(j == box[0].length) {
				count++;
				for(int k = i; k > 0; k--) {
					for(int h = 0; h < box[0].length; h++) {
						box[k][h].setOpaque(box[k-1][h].isOpaque());
					}
				}
				/*for(int h = 0; h < box[0].length; h++) {
					box[0][h].setOpaque(false);
				}*/
				i++;
			}
		}//System.out.println("removeRow");
		return count;
	}
	
	//������Ϸ
	public void endGame() {
		movingUnit = null;
	}
	
}
