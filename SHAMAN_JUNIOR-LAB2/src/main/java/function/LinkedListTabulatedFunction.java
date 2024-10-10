package function;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Removable{

    private static final class Node{
        public Node next;
        public  Node prev;
        public double x,y;

        Node(double x,double y){
            this.x = x;
            this.y = y;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;

    private void addNode(double x, double y){
        Node newNode = new Node(x,y);
        if(count == 0){
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        }
        else{
            Node last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }

        count++;
    }

    private Node getNode(int index){

        Node currentNode = head;

        if(index>0){
            for(int i = 0; i < index;i++){
                currentNode = currentNode.next;
            }
        }
        else{
            for(int i = index; i < 0;i++){
                currentNode = currentNode.prev;
            }
        }

        return currentNode;
    }

    LinkedListTabulatedFunction(double[] xValues, double[] yValues){
        for(int i = 0; i < xValues.length ;i++){
            addNode(xValues[i],yValues[i]);
        }
    }

    LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count){
        if(xFrom > xTo){
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }

        double dX = (xTo - xFrom)/(count-1);

        if(xFrom != xTo){
            for(int i = 0; i < count; i++){
                addNode(xFrom + i*dX, source.apply(xFrom + i*dX));
            }
        }
        else{
            for(int i = 0; i < count;i++){
                addNode(xFrom,source.apply(xFrom));
            }
        }


    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    @Override
    public double getX(int index) {
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        Node current = head;
        for(int i = 0; i < count;i++){
            if(current.x == x){
                return i;
            }
            current = current.next;
        }

        return -1;
    }

    @Override
    public int indexOfY(double y) {
        Node current = head;
        for(int i = 0; i < count;i++){
            if(current.y == y){
                return i;
            }
            current = current.next;
        }

        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        Node current = head;

        if(head.x < x){
            return count;
        }

        for(int i = 0;i < count;i++){
            if(x < current.next.x){
                return i-1;
            }
        }

        return 0;
    }

    private Node floorNodeOfx(double x){
        Node current = head;

        if(head.x < x){
            throw new IllegalArgumentException("Less");
        }

        for(int i = 0;i < count;i++){
            if(x < current.next.x){
                break;
            }
            current = current.next;
        }

        return current;
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return head.y;
        }
        Node floor = getNode(floorIndex);

        return interpolate(x,floor.x,floor.next.x,floor.y,floor.next.y);
    }

    protected double interpolate(double x, Node floorIndex) {
        if (count == 1) {
            return head.y;
        }

        return interpolate(x,floorIndex.x,floorIndex.next.x,floorIndex.y,floorIndex.next.y);
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return head.y;
        }

        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) {
            return head.y;
        }

        return interpolate(x, head.prev.prev.x, head.prev.x, head.prev.prev.y, head.prev.y);
    }

    @Override
    public double apply(double x) {
        if(x < leftBound()){
            return extrapolateLeft(x);
        }
        else if(x > rightBound()){
            return extrapolateRight(x);
        }
        else {
            int index = indexOfX(x);
            if (index != -1) {
                return getY(index);
            } else {
                return interpolate(x, floorIndexOfX(x));
            }
        }
    }

    @Override
    public void remove(int index) {
        if(count == 0){
            throw new IllegalArgumentException("Zero length");
        }

        Node currentNode = head;
        if(head.next != currentNode){
            if(index>0){
                for(int i = 0; i < index;i++){
                    currentNode = currentNode.next;
                }
            }
            else{
                for(int i = index; i < 0;i++){
                    currentNode = currentNode.prev;
                }
            }
            if(currentNode == head){
                head = head.next;
            }
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        else{
            head = null;
        }
        count--;
    }
}
