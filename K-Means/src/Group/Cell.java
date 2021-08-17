package Group;

import java.awt.*;

public class Cell {

    private Group group;
    private final Point pos;
    private final int offset;

    public Cell(Group group, int x, int y, int offset){
        this.group = null;
        this.pos = new Point(x,y);
        this.offset = offset;
    }

    public Color getColor(){
        return this.group == null? new Color(200,200,255): this.group.groupColor;
    }
    public Point getPos(){return this.pos;}

    public int getOffset(){return this.offset;}
    public void setGroup(Group[] groups){
       int index = -1;
       double bestDistance = Integer.MAX_VALUE;
       for(int i = 0 ; i < groups.length; i++){
           Point groupPos = groups[i].groupNode;
           double distance = Math.sqrt( Math.pow(this.pos.x - groupPos.x, 2) + Math.pow(this.pos.y - groupPos.y, 2));
           if(distance < bestDistance){
               index = i;
               bestDistance = distance;
           }
       }
       this.group = groups[index];
       groups[index].addMember(this);
    }
}
