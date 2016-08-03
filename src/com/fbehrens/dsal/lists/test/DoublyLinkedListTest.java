package com.fbehrens.dsal.lists.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.fbehrens.dsal.lists.DoublyLinkedList;

public class DoublyLinkedListTest {

	@Test
	public void stringRep(){
		DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
		assertEquals("[]", list.toString());
		for(int i = 0; i < 5; i++){
			list.append(i);
		}
		assertEquals("[0,1,2,3,4]", list.toString());
	}
	
	@Test
	public void equality() {
		DoublyLinkedList<Integer> one = new DoublyLinkedList<>(), two = new DoublyLinkedList<>();
		assertNotEquals(one, "A random object");
		for(int i = 0; i < 5; i++){
			one.append(i);
			two.append(i);
		}
		assertEquals(one, two);
		two.delete(2);
		assertNotEquals(one, two);
		one.delete(2);
		assertEquals(one, two);
	}
	
	@Test
	public void insertion(){
		// test whether appending to an empty list is the same as prepending in reverse order
		DoublyLinkedList<Character> charsOne = new DoublyLinkedList<>();
		for(char c = 'a'; c <= 'z'; c++){
			charsOne.append(c);
		}
		DoublyLinkedList<Character> charsTwo = new DoublyLinkedList<>();
		for(char c = 'z'; c >= 'a'; c--){
			charsTwo.prepend(c);
		}
		assertEquals(charsOne, charsTwo);
		
		charsOne.append('1');
		assertNotEquals(charsOne, charsTwo);
		charsTwo.append('1');
		assertEquals(charsOne, charsTwo);
	}
	
	@Test
	public void retrieval(){
		// insert the numbers 0 to 1023 and then check whether they are stored in that oreder
		DoublyLinkedList<Integer> ints = new DoublyLinkedList<>();
		for(int i = 0; i < 1024; i++){
			ints.append(i);
		}
		int i = 0;
		for(int r : ints){
			if(r != i){
				fail("Numbers could not be retrieved in the same order as they were inserted!");
			}
			i++;
		}
		if(i != 1024){
			fail("The number of retrieved elements was not correct!");
		}
	}
	
	@Test
	public void deletion(){
		DoublyLinkedList<String> strings = new DoublyLinkedList<>();
		strings.delete("this should do nothing");
		for(char c = 'a'; c <= 'z'; c++){
			strings.append(c + "");
		}
		for(String c : new String[]{"f","l","o","r","i","a","n"}){
			strings.delete(c);
		}
		DoublyLinkedList<String> stringsTwo = new DoublyLinkedList<>();
		for(char c = 'a'; c <= 'z'; c++){
			if(c != 'f' && c != 'l' && c != 'o' && c != 'r' && c != 'i' && c != 'a' && c != 'n'){
				stringsTwo.append(c + "");
			}
		}
		assertEquals(stringsTwo, strings);
	}
	
	@Test
	public void contains(){
		DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
		for(int i = 0; i < 1024; i++){
			list.prepend(i);
		}
		for(int i = 0; i < 1024; i++){
			assertEquals(true, list.contains(i));
		}
		assertEquals(false, list.contains(0xE5E1));
		assertEquals(false, list.contains(-1));
	}
	
	@Test
	public void accessListEnds(){
		DoublyLinkedList<String> strings = new DoublyLinkedList<>();
		assertNull(strings.firstElement());
		assertNull(strings.lastElement());
		assertNull(strings.pop());
		assertNull(strings.dequeue());
		strings.append("Anfang");
		assertEquals("Anfang", strings.firstElement());
		assertEquals("Anfang", strings.lastElement());
		assertEquals("Anfang", strings.pop());
		assertEquals(false, strings.contains("Anfang"));
		strings.append("Anfang");
		assertEquals("Anfang", strings.dequeue());
		assertNull(strings.dequeue());
		strings.append("Anfang");
		strings.append("Ende");
		assertEquals("Anfang", strings.firstElement());
		assertEquals("Ende", strings.lastElement());
		assertEquals("Anfang", strings.pop());
		strings.prepend("Anfang");
		assertEquals("Ende", strings.dequeue());
		strings.pop();
		strings.pop();
		strings.dequeue();
		DoublyLinkedList<Integer> ints = new DoublyLinkedList<>();
		for(int i = 0; i < 1024; i++){
			ints.prepend(i);
		}
		for(int i = 1023; i >= 0; i--){
			if(ints.pop() != i){
				fail("Pop was not in the right order");
			}
		}
		for(int i = 0; i < 655; i++){
			ints.prepend(i);
		}
		for(int i = 0; i < 655; i++){
			if(ints.dequeue() != i){
				fail("Dequeue was not in the right order!");
			}
		}
	}
	
	@Test
	public void size(){
		DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
		assertEquals(0, list.size());
		for(int i = 1; i < 1024; i++){
			list.append(i);
			assertEquals(i, list.size());
		}
		int i = 0;
		while(!list.isEmpty()){
			if(i % 4 == 0){
				list.pop();
			} else if (i % 4 == 1){
				list.dequeue();
			} else if (i % 4 == 2){
				list.delete(list.firstElement());
			} else if (i % 4 == 3){
				list.delete(list.lastElement());
			}
			i++;
		}
		assertEquals(1023, i);
		assertEquals(0, list.size());
		list.delete(5);
		list.delete(6);
		assertEquals(0, list.size());
		list.prepend(1);
		assertEquals(1, list.size());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void accessBadIndex(){
		DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
		list.get(0);
	}
	
	@Test
	public void randomAccess(){
		DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
		for(int i = 0; i < 1024; i++){
			list.append(i);
		}
		for(int i = 1023; i >= 0; i--){
			assertEquals((int) i, (int) list.get(i));
		}
	}
	

}
