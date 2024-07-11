//package Prova;
//package solo_java;
import java.util.*;


public class ZSort {

	public record BubbleSort<T extends Comparable<T>>(T[] arr) {
		public void sort() {
			for (int i=0; i< arr.length-1; i++) {
				boolean swap = false;
				for (int j=0; j< arr.length-1-i; j++) {
					if (arr[j].compareTo(arr[j + 1]) > 0) {
						T temp = arr[j];
						arr[j] = arr[j+1];
						arr[j+1] = temp;
						swap = true;
					}
				}
				if (!swap) {break;}
			}
		}
		
	}
	
	public record SelectionSort<T extends Comparable<T>>(T[] arr) {
		public void sort() {
			for (int i=0; i<arr.length-1; i++) {
				int min_idx = i;
				for (int j = i + 1; j < arr.length; j++) {
	                if (arr[j].compareTo(arr[min_idx]) < 0) {
	                    min_idx = j;
	                }
	            }
				if (min_idx != i) {
					T temp = arr[i];
					arr[i] = arr[min_idx];
					arr[min_idx] = temp;
				}
			}
		}
	}

	public record InsertionSort<T extends Comparable<T>>(T[] arr) {
		public void sort() {
			for (int i=1; i<arr.length;i++) {
				int j = i;
				while(j > 0 && arr[j].compareTo(arr[j - 1]) < 0) {
					T temp = arr[j];
					arr[j] = arr[j-1];
					arr[j-1] = temp;
					j--;
				}
			}
		}
	}
	
	public record ShellSort<T extends Comparable<T>>(T[] arr) {
		public void sort() {
			for (int gap = arr.length / 2; gap > 0; gap /= 2) {
				 for (int i = gap; i < arr.length; i++) {
		                int j = i;
		                while (j >= gap && arr[j].compareTo(arr[j - gap]) > 0) {
		                    T temp = arr[j];
		                    arr[j] = arr[j - gap];
		                    arr[j - gap] = temp;
		                    j -= gap;
		                }
		        }
			}
		}
	}
	
	public record CountingSort(int[] arr){
		public void sort() {
			int min = 9999; int max = -999;
			for (int i=0; i<arr.length;i++) {
				if ((int)arr[i] > max) {max = (int)arr[i];}
				if ((int)arr[i] < min) {min = (int)arr[i];}
			}
			int[] countArray = new int[max-min+1];
			for (int value: arr) {countArray[(int)value - min]++;}
			for (int i=1; i<countArray.length;i++) {countArray[i] += countArray[i-1];}
			int[] output = new int[arr.length];
			for (int i=arr.length-1; i>=0;i--) {
				int current = (int) arr[i];
				int pos = countArray[current - min] - 1;
				output[pos] = current;
				countArray[current-min]--;
			}
			System.arraycopy(output, 0, arr, 0, arr.length);
		}
	}
	
	public record BucketSort(int[] arr){
		public void sort() {
			int n = arr.length;
			if (n==0) {return;}
			int max = arr[0];
			for (int i: arr) {
				if (i > max) {max = i;}
			}
			ArrayList<Integer>[] buckets = new ArrayList[n];
			for (int i=0;i<n;i++) {buckets[i] = new ArrayList<>();}
			for (int i: arr) {
				int idx = (int) ((double)i / (max+1)*n);
				buckets[idx].add(i);
			}
			int current = 0;
			for (ArrayList<Integer> bucket: buckets) {
				Collections.sort(bucket);
				for (int i: bucket) {arr[current++] = i;}
				
			}
		}
	}
	
	public record RadixSort(int[] arr){
		public void sort() {
			int max = arr[0];
			for (int i: arr) {
				if (i> max) {max = i;}
			}
			for (int exp = 1; max / exp > 0; exp *= 10) {
	            countingSort(arr, exp);
	        }
		}

		private void countingSort(int[] array, int exp) {
			 int[] output = new int[array.length];
		     int[] count = new int[10];
		     Arrays.fill(count, 0);
		     for (int value : array) {
		            int digit = (value / exp) % 10;
		            count[digit]++;
		     }
		     for (int i = 1; i < 10; i++) {count[i] += count[i-1];}
		     for (int i=array.length-1; i>=0;i--) {
		    	 int digit = (array[i] / exp) % 10;
		         output[count[digit] - 1] = array[i];
		         count[digit]--;
		     }
		     System.arraycopy(output, 0, array, 0, array.length);
		}
		
	}
	
	public record MergeSort(int[] arr){
		public void sort() {
			mergesort(0, arr.length-1);
		}
		public void mergesort(int l, int h) {
			if (l >= h) {return;}
			int m = (l+h)/2;
			mergesort(l, m);
			mergesort(m+1, h);
			merge(l, m, h);
		}
		public void merge(int l, int m, int h) {
			int nl = m-l +1;
			int nr = h-m;
			int[] sx = new int[nl];
			int[] dx = new int[nr];
			for (int i=0; i<nl; i++) {sx[i] = arr[l+i];}
			for (int j=0; j<nr; j++) {dx[j] = arr[m+1+j];}
			int i=0, j=0, k=l;
			while (i<nl && j<nr) {
				if (sx[i] <= dx[j]) {
					arr[k] = sx[i];i++;
				}else {
					arr[k] = dx[j];j++;
				}
				k++;
			}
			while (i<nl) {
				arr[k] = sx[i];
				i++;k++;
			}
			while (j<nr) {
				arr[k] = dx[j];
				j++;k++;
			}
		}
		
	}
	
	public record QuickSort(int[] arr){
		public void sort() {
			quicksort(0, arr.length-1);
		}
		
		private void quicksort(int p, int r) {
			if (p > r) {return;}
			int q = partition(p,r);
			quicksort(p, q-1);
			quicksort(q+1, r);
		}
		
		private int partition(int p, int r) {
			int m = (p+r)/ 2;
			swap(m, r);
			int pivotidx = p;
			for (int i=p; i<r;i++) {
				if (arr[i] <= arr[r]) {
					swap(i, pivotidx++);
				}
			}
			swap(pivotidx,r);
			return pivotidx;
		}
		
		private void swap(int a, int b) {
			if (a==b) {return;}
			int temp = arr[a];
			arr[a] = arr[b];
			arr[b] = temp;
			return;
		}
	}
	
	public static void main(String[] args) {
//		Integer[] integers = { 10, 55, -5, 34, 7, 22, 19 };
		int[] integers = { 10, 8, 5, 4, 7, 2, 9 };
		System.out.println(Arrays.toString(integers));
		new MergeSort(integers).sort();
		System.out.println(Arrays.toString(integers));
	}

}