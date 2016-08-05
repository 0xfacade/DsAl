package com.fbehrens.dsal.lists.competition;

import java.util.Random;

import com.fbehrens.dsal.RuntimeComparator;
import com.fbehrens.dsal.RuntimeComparator.Competitor;
import com.fbehrens.dsal.lists.AbstractList;
import com.fbehrens.dsal.lists.DoublyLinkedList;
import com.fbehrens.dsal.lists.SimpleLinkedList;

public class ListAsStack implements RuntimeComparator.CompetitorGroup {
	
	private static long seed = System.currentTimeMillis();
	private static void useListAsStack(AbstractList<Integer> list){
		Random r = new Random(seed);
		int opCount = 0;
		while(opCount < 100000000){
			if(!list.isEmpty() && r.nextInt(5) % 5 == 0){
				int remove = r.nextInt(list.size());
				for(int i = 0; i < remove; i++){
					list.pop();
				}
			} else {
				list.prepend(r.nextInt());
			}
			opCount++;
		}
	}
	
	private static class SimpleLinkedListCompetitor implements RuntimeComparator.Competitor {

		@Override
		public void run() {
			AbstractList<Integer> list = new SimpleLinkedList<>();
			useListAsStack(list);
		}
		
	}
	
	private static class DoublyLinkedListCompetitor implements RuntimeComparator.Competitor {

		@Override
		public void run() {
			AbstractList<Integer> list = new DoublyLinkedList<>();
			useListAsStack(list);
		}
		
	}
	
	@Override
	public Competitor[] getCompetitors() {
		return new Competitor[]{new SimpleLinkedListCompetitor(), new DoublyLinkedListCompetitor()};
	}

}
