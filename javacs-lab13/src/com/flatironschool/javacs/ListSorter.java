/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        List<T> aux = new ArrayList<T>();
        List<T> res = new ArrayList<T>();
        for (T elt : list) {
        	aux.add(elt);
        }
        mergeSort(list, aux, comparator, 0, list.size() - 1);
        res.addAll(list);
        return res;
	}

	public void mergeSort(List<T> list, List<T> aux, Comparator<T> comparator, int low, int high) {
		if (low >= high) {
			return;
		}
		int mid = (low + high) / 2;
		mergeSort(list, aux, comparator, low, mid);
		mergeSort(list, aux, comparator, mid + 1, high);
		merge(list, aux, comparator, low, mid, high);
	}

	public void merge(List<T> list, List<T> aux, Comparator<T> comparator, int low, int mid, int high) {
		for (int i = 0; i < list.size(); i++) {
			aux.set(i, list.get(i));
		}
		int i = low, j = mid + 1;
		for (int k = low; k <= high; k++) {
			if (i > mid) list.set(k, aux.get(j++));
			else if (j > high) list.set(k, aux.get(i++));
			else if (comparator.compare(aux.get(i), aux.get(j)) < 0) list.set(k, aux.get(i++));
			else list.set(k, aux.get(j++));
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        PriorityQueue<T> heap = new PriorityQueue<T>(comparator);
        for (T t : list) {
        	heap.offer(t);
        }
        int index = 0;
        while (!heap.isEmpty()) {
        	list.set(index, heap.poll());
        	index++;
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        PriorityQueue<T> heap = new PriorityQueue(k, comparator);
        List<T> topKEl = new ArrayList<T>();
        for (T t : list) {
        	if (heap.size() < k) {
        		heap.offer(t);
        	} else {
        		if (comparator.compare(t, heap.peek()) > 0) {
        			heap.poll();
        			heap.offer(t);
        		}
        	}
        }

        while (!heap.isEmpty()) {
        	topKEl.add(heap.poll());
        }

        return topKEl;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
