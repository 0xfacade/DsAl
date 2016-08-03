package com.fbehrens.dsal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fbehrens.dsal.lists.AbstractList;
import com.fbehrens.dsal.lists.SimpleLinkedList;
import com.fbehrens.dsal.lists.competition.ListAppendingAndPrepending;
import com.fbehrens.dsal.lists.competition.ListAsStack;

public class RuntimeComparator {
	
	public interface Competitor {
		default String getName() {
			return this.getClass().getSimpleName();
		}
		default void prepare(){}
		void run();
		default void takeDown(){}
	}
	
	public interface CompetitorGroup {
		default String getName() {
			return this.getClass().getSimpleName();
		}
		Competitor[] getCompetitors();
	}
	
	private static class Result {
		public long timing;
		public Competitor competitor;
	}
	
	public static void main(String[] args){
		AbstractList<CompetitorGroup> groups = new SimpleLinkedList<>();
		// List competitions
		groups.append(new ListAppendingAndPrepending());
		groups.append(new ListAsStack());
		for(CompetitorGroup group : groups){
			compare(group);
		}
	}
	
	private static void compare(CompetitorGroup group){
		System.out.println("Comparing: " + group.getName());
		List<Result> results = new ArrayList<>(group.getCompetitors().length);
		for(Competitor competitor : group.getCompetitors()){
			competitor.prepare();
			long start = System.nanoTime();
			competitor.run();
			long end = System.nanoTime();
			competitor.takeDown();
			Result result = new Result();
			result.competitor = competitor;
			result.timing = end - start;
			results.add(result);
		}
		
		results.sort(new Comparator<Result>() {
			@Override
			public int compare(Result r1, Result r2) {
				return Long.compare(r1.timing, r2.timing);
			}
		});
		
		System.out.println("-----------------------------------------------------------");
		for(Result result : results){
			String formattedTime = String.format("%f s", result.timing / Math.pow(10, 9));
			System.out.println(result.competitor.getName() + ": " + formattedTime);
		}
		System.out.println();
	}
}
