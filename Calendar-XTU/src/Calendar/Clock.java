package Calendar;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Calendar;

/**
 * 
 *  该类用于实现一个转盘表
 *  基于JFrame样式
 *  采用无布局格式
 *  利用repaint()函数使用方法
 *  利用多线程实现图像刷新
 *  使用g2d抗锯齿功能
 *  
 */
public class Clock extends JPanel {

	private static final long serialVersionUID = 4L;
	
	private int h;
    private int m;
    private int s;

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
    
    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public Clock() {
        setTime();
    }

    // 获取时间
    public void setTime() {
    	
        Calendar currentTime = Calendar.getInstance();
        h = currentTime.get(Calendar.HOUR_OF_DAY);
        m = currentTime.get(Calendar.MINUTE);
        s = currentTime.get(Calendar.SECOND);

    }

    // 画钟函数
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // 开启反锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 圆心坐标和半径
        int x = 150;
        int y = 150;
        int r = (int) (x * 0.6);
        // 开始画图

        // 画外圈圆
        g2d.setColor(Color.LIGHT_GRAY);
        // Ellipse2D rect1 = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);
        // g2d.fill(rect1);
        g2d.drawOval(x - r, y - r, r * 2, r * 2);

        // 画数字
//        g2d.drawString("Ⅲ", x + r, y);
//        g2d.drawString("Ⅵ", x, y - r);
//        g2d.drawString("Ⅸ", x - r, y);
//        g2d.drawString("Ⅻ", x, y + r);

        // 画圆心点
        Ellipse2D rect2 = new Ellipse2D.Double(x - 0.2, y - 0.2, 0.2 * 2, 0.2 * 2);
        g2d.fill(rect2);
        // 画时钟的间隔
        for (int i = 0; i < 12; i++) {
            if(i % 3 == 0) {
                g2d.fillOval((int)(x + (int)(0.9 *r*Math.sin(Math.PI*i/6.0)))-3,(int)(y - 0.9 *r*Math.cos(Math.PI*i/6.0))-3,6,6);
            }
            else {
                g2d.fillOval((int)(x + (int)(0.9 *r*Math.sin(Math.PI*i/6.0))-2.5),(int)(y - 0.9 *r*Math.cos(Math.PI*i/6.0)-2.5),5,5);
            }

        }


        // 画秒针
        int slen = (int) (0.80 * r);
        int sx = (int) (x + slen * Math.sin(s * (2 * Math.PI / 60)));
        int sy = (int) (y - slen * Math.cos(s * (2 * Math.PI / 60)));
        BasicStroke bs1 = new BasicStroke(1); // 笔画的轮廓（画笔宽度/线宽为1px）
        g2d.setStroke(bs1);
        Line2D s1 = new Line2D.Double(x, y, sx, sy);
        g2d.draw(s1);
        // 画分针
        int mlen = (int) (0.65 * r);
        int mx = (int) (x + mlen * Math.sin((m + s / 60.0) * (2 * Math.PI / 60)));
        int my = (int) (y - mlen * Math.cos((m + s / 60.0) * (2 * Math.PI / 60)));
        BasicStroke bs2 = new BasicStroke((float) 2.5); // 笔画的轮廓（画笔宽度/线宽为2.5px）
        g2d.setStroke(bs2);
        Line2D s2 = new Line2D.Double(x, y, mx, my);
        g2d.draw(s2);
        // 画时针
        int hlen = (int) (0.55 * r);
        int hx = (int) (x + hlen * Math.sin((h % 12 + m / 60.0 + s / 3600.0) * (2 * Math.PI / 12)));
        int hy = (int) (y - hlen * Math.cos((h % 12 + m / 60.0 + s / 3600.0) * (2 * Math.PI / 12)));
        BasicStroke bs3 = new BasicStroke(4); // 笔画的轮廓（画笔宽度/线宽为4px）
        g2d.setStroke(bs3);
        Line2D s3 = new Line2D.Double(x, y, hx, hy);
        g2d.draw(s3);

    }
    
    public void showd() {
        new Thread() {
            public void run() {
                while (true) {
                   
                    setTime();
                    repaint();
                    /*
		                     *  线程被打断时，
		                     *  打印异常信息出现的位置和原因
                     *  
                     */
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
