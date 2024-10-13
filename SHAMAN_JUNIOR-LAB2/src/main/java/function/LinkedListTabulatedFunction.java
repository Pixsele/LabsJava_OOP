package function;

import function.api.Insertable;
import function.api.MathFunction;
import function.api.Removable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Iterable;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Removable, Insertable, Iterable<Point>{

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

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues){
        if(xValues.length < 2 || yValues.length < 2){
            throw new IllegalArgumentException("The size must be >2");
        }

        for(int i = 0; i < xValues.length ;i++){
            addNode(xValues[i],yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count){
        if(count < 2){
            throw new IllegalArgumentException("The number of elements must be >2");
        }

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
        if(head==null){
            throw new IllegalStateException("List is empty");
        }
        return head.x;
    }

    @Override
    public double rightBound() {
        if(head==null){
            throw new IllegalStateException("List is empty");
        }
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

    protected Node floorNodeOfx(double x){
        Node current = head;

        if(head.x < x){
            throw new IllegalArgumentException("Less than left bound");
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
        Node floor = getNode(floorIndex);

        //ЗАМЕНИТЬ ОШИБКУ TODO

        if(x < floor.x || x > floor.next.x){
            throw new IllegalArgumentException("x is out of bounds");
        }

        return interpolate(x,floor.x,floor.next.x,floor.y,floor.next.y);
    }

    private double interpolate(double x, Node floorIndex) {

        //ЗАМЕНИТЬ ОШИБКУ TODO

        if(x < floorIndex.x || x > floorIndex.next.x){
            throw new IllegalArgumentException("x is out of bounds");
        }


        return interpolate(x,floorIndex.x,floorIndex.next.x,floorIndex.y,floorIndex.next.y);
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    protected double extrapolateRight(double x) {
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

    @Override
    public void insert(double x, double y) {
        if (count == 0){
            addNode(x,y);
        }
        if (x < head.x) {
            Node newNode = new Node(x, y);
            newNode.next = head;
            newNode.prev = head.prev;
            head.prev.next = newNode;
            head.prev = newNode;
            head = newNode;
            count++;
            return;
        }
        Node cur;
        if (x > head.prev.x) {
            cur = head.prev;
        } else {
            cur = head;
            while (cur.next != head && cur.next.x < x) {
                cur = cur.next;
            }
        }
        if (cur.x == x) {
            cur.y = y;
            count++;
            return;
        }
        if (cur.next != head && cur.next.x == x) {
            cur.next.y = y;
            count++;
            return;
        }
        Node newNode = new Node(x, y);
        newNode.next = cur.next;
        newNode.prev = cur;
        cur.next.prev = newNode;
        cur.next = newNode;
        count++;

    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {

            Node node = head;

            @Override
            public boolean hasNext() {
                if(node != null && node.next != head){
                    return true;
                }
                return false;
            }

            @Override
            public Point next() {
                if(node == null){
                    throw new NoSuchElementException("Empty list");
                }

                Point point = new Point(node.x,node.y);

                if(hasNext()){
                    node = node.next;
                }
                else{
                    node =null;
                }

                return point;
            }
        };
    }
}
