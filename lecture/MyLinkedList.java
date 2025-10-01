import java.util.LinkedList;

public class MyLinkedList {
    public void addMiddle(int item, int position) {
        if (position <= 0 || head == null) {
            addFitst(item);
        }

        Node newNode = newNode(item);
        Node current = head;
        int index = 0;

        while (current != null && index < position -1){
            current = current.next;
            index++;
        }
        
        // if position is our of bounds, add to the end
        if (current == null){
            current.next = newNode;
        }

        // insert the new node
        if (
    }
}