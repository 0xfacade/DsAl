package com.fbehrens.dsal.lists.competition;

import java.util.Random;

import com.fbehrens.dsal.RuntimeComparator;
import com.fbehrens.dsal.RuntimeComparator.Competitor;
import com.fbehrens.dsal.lists.AbstractList;
import com.fbehrens.dsal.lists.DoublyLinkedList;
import com.fbehrens.dsal.lists.SimpleLinkedList;

public class ListAppendingAndPrepending implements RuntimeComparator.CompetitorGroup {

	// we use the same seed both times to make sure we insert the same elements
	// and use the same order of prepend / append
	private static long seed = System.currentTimeMillis();
	private static void fillListRandomly(AbstractList<Integer> list){
		Random random = new Random(seed);
		for(int i = 0; i < 50000; i++){
			int r = random.nextInt();
			if(r % 2 == 0){
				list.append(r);
			} else {
				list.prepend(r);
			}
		}
	}
	
	private static class SimpleLinkedListCompetitor implements RuntimeComparator.Competitor {

		@Override
		public void run() {
			AbstractList<Integer> list = new SimpleLinkedList<>();
			fillListRandomly(list);
		}
		
	}
	
	public static class DoublyLinkedListCompetitor implements RuntimeComparator.Competitor {

		@Override
		public void run() {
			AbstractList<Integer> list = new DoublyLinkedList<>();
			fillListRandomly(list);
		}
		
	}

	@Override
	public Competitor[] getCompetitors() {
		return new Competitor[]{ new SimpleLinkedListCompetitor(), new DoublyLinkedListCompetitor() };
	}

}
