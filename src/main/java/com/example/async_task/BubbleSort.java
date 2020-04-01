package com.example.async_task;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int [] arr = {3,2,5,8,6,4,9};
//        System.out.printf("1---" + Arrays.toString(arr));
//        bubbleSort(arr);
//        System.out.printf("2---" + Arrays.toString(arr));

        System.out.printf("3---" + binarySearch(arr,9));
    }

    //二分查找
    private static int binarySearch(int[] arr,int key){
        int min = 0;
        int max = arr.length - 1;

        while(max > min){
            int mid = (min + max) / 2 ;
            if (key == arr[mid]){
                return mid;
            }else if(key > arr[mid]){
                min = mid + 1;
            }else {
                max = mid - 1;
            }
        }

        return -1;
    }

    //冒泡
    private static void bubbleSort(int[] arr){
       for (int i=0;i<arr.length - 1;i++){
           for (int j=0;j<arr.length - 1 - i;j++){
               if (arr[j] > arr[j+1]){
                   int temp = arr[j];
                   arr[j] = arr[j+1];
                   arr[j+1] = temp;
               }
           }
       }
    }
}
