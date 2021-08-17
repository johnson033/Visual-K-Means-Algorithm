package GUI;

import Group.Cell;
import Group.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

public class MainPanel extends JPanel {

    int quadSize, gridWidth, gridHeight, gridX, gridY;
    int cellSize, cellCount, numGroups;
    int cellMaxX, cellMaxY, cellMinX, cellMinY;

    private Cell[] cells;
    private Group[] groups;
    private final Random random = new Random();

    public MainPanel(int width, int height){
        this.setBounds(0,0,width,height);
        this.setLayout(null);
        this.setBackground(new Color(32,32,32));

        this.quadSize = (int) ((height * .9) / 2);
        this.gridHeight = this.quadSize *2;
        this.gridWidth = this.quadSize *2;
        this.gridX = (width / 2)- this.quadSize;
        this.gridY = ((height - (this.quadSize * 2)) / 2);

        this.cellSize = 10;
        this.cellCount = 100;
        this.numGroups = 3;

        this.cellMaxX = width; //(this.gridX + ( this.quadSize * 2)) - this.cellSize;
        this.cellMinX = 0; //(this.gridX);
        this.cellMaxY = height;//(this.gridY + (this.quadSize * 2)) - this.cellSize;
        this.cellMinY = 0;//(this.gridY);

        JButton testButton = new JButton("Test");
        testButton.setBounds(0,0,100,50);
        testButton.addActionListener( e -> {
            testGroups();
        });

        this.add(testButton);
        initGroups();
        initCells();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //paintGrid(g);
        paintCells(g);
        paintGroupNodes(g);
    }

    private void paintGrid(Graphics g){
        g.setColor(new Color(68, 68, 68));
        g.drawRect(this.gridX, this.gridY, this.quadSize, this.quadSize);
        g.drawRect(this.gridX + this.quadSize, this.gridY, this.quadSize, this.quadSize);
        g.drawRect(this.gridX, this.gridY + this.quadSize, this.quadSize, this.quadSize);
        g.drawRect(this.gridX + this.quadSize, this.gridY + this.quadSize, this.quadSize, this.quadSize);
    }

    private void paintCells(Graphics g){
        for(Cell c: this.cells){
            g.setColor(c.getColor().darker());
            g.fillOval(c.getPos().x, c.getPos().y, this.cellSize, this.cellSize);
        }
    }

    private void paintGroupNodes(Graphics g){
        for(Group group: this.groups){
            ArrayList<Line2D> lines = group.getLines();
            for(Line2D l: lines){
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(group.groupColor().darker());
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine((int) l.getX1(), (int)l.getY1(), (int)l.getX2(),(int) l.getY2());
            }
            g.setColor(group.groupColor());
            g.fillOval(group.groupPoint().x, group.groupPoint().y, this.cellSize * 2, this.cellSize * 2);
        }

    }

    private void initGroups(){
        this.groups = new Group[this.numGroups];
        for(int i = 0; i < this.numGroups; i++){
            this.groups[i] = new Group(new Color(  random.nextInt(255), random.nextInt(255), random.nextInt(255)),
                                                random.nextInt(this.cellMaxX - this.cellMinX) + this.cellMinX,
                                                random.nextInt(this.cellMaxY - this.cellMinY) + this.cellMinY);
        }
    }

    private void initCells(){
        this.cells = new Cell[this.cellCount];
        for(int i = 0; i < this.cellCount; i++){
            this.cells[i] = new Cell(null,random.nextInt(this.cellMaxX - this.cellMinX) + this.cellMinX,
                                                random.nextInt(this.cellMaxY - this.cellMinY) + this.cellMinY, this.cellSize / 2);
        }
    }

    private void testGroups(){
        for(Cell c: this.cells){
            c.setGroup(this.groups);
        }

        for(Group group: this.groups){
            group.calcGroupMean();
        }

        this.repaint();


    }
}
