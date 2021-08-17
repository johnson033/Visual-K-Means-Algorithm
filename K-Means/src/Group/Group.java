package Group;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Group {

    Color groupColor;
    Point groupNode;
    ArrayList<Cell> groupMembers;
    ArrayList<Line2D> lines;
    public Group(Color groupColor, int x, int y){
        this.groupColor = groupColor;
        this.groupNode = new Point(x, y);
        this.groupMembers = new ArrayList<>();
        this.lines = new ArrayList<>();
    }

    public Point groupPoint(){return this.groupNode;}
    public Color groupColor(){return this.groupColor;}
    public ArrayList<Line2D> getLines(){return this.lines;}

    public void addMember(Cell member){this.groupMembers.add(member);}

    public void calcGroupMean(){
        this.lines.clear();
        int xSum = 0;
        int ySum = 0;

        for(Cell c: this.groupMembers){
            xSum += c.getPos().x;
            ySum += c.getPos().y;
        }

        this.groupNode.x = xSum / this.groupMembers.size();
        this.groupNode.y = ySum / this.groupMembers.size();

        for(Cell c: this.groupMembers){
            this.lines.add(new Line2D.Double(c.getPos().x + c.getOffset(), c.getPos().y + c.getOffset(),
                    this.groupNode.x + (c.getOffset() * 2), this.groupNode.y + (c.getOffset() * 2)));
        }

        this.groupMembers.clear();
    }
}
