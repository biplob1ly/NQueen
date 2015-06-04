/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueen;


import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 *
 * @author USER
 */

public class NQueen {

    private static int N;
    private static int numberOfSolutions=0;
    private static LinkedHashMap<Integer,int[]> results=new LinkedHashMap<Integer, int[]>();
    private static LinkedHashMap<Integer,int[]> resultsFCMRV=new LinkedHashMap<Integer, int[]>();
    private static int[][] markKeeper;
    /**
     * @param args the command line arguments
     */
    public static boolean placeNQueens(int[] X, int N, int i){
        if(i==N){
            int[] result=new int[N];
            System.arraycopy(X,0,result,0,N);
            results.put(numberOfSolutions,result);
            numberOfSolutions++;
            return true;
        }
        
        for (int j = 0; j < N; j++) {
            X[i]=j;
            boolean flag=checkConstraints(X,N,i);
            if(!flag)continue;
            boolean succ=placeNQueens(X,N,i+1);
            
            //if (succ)return true; 
        }
        return false;
    }
    
    public static boolean placeNQueensFCMRV(int[] X, int N){
        if(isNoMoreRows(X)){
            int[] result=new int[N];
            System.arraycopy(X,0,result,0,N);
            resultsFCMRV.put(numberOfSolutions,result);
            numberOfSolutions++;
            return true;
        }
        
        int i=selectRowMRV(X);
        for (int j=0; j<N ; j++) {
            if(markKeeper[i][j]>=0){
            X[i]=j;
            
            boolean flag=forwardCheck(X,i,j);
            if(!flag)continue;
            boolean succ=placeNQueensFCMRV(X,N);
            
            //if (succ){return true;}
            //unmark
            X[i]=-1;
            unmark(X,i,j);
            
            }
        }
        return false;
    }
    
    public static boolean isNoMoreRows(int[] X){
        for (int i = 0; i < N; i++) {
            if(X[i]==-1)return false;
        }
        return true;
    }
    
    public static int selectRowMRV(int[] X){
        int counter,min=N+1,row=-1;
        for (int i = 0; i < N; i++) {
            counter=0;
            if(X[i]==-1){
                for (int j = 0; j < N; j++) {
                    if(markKeeper[i][j]==0)counter++;
                }
                if(counter < min){
                    min=counter;
                    row=i;
                }
            }
        }
        //System.out.println("Selected row: "+row);
        return row;
    }
    
    public static boolean checkConstraints(int[] A, int n, int r){
        for (int i = 0; i <= r-1; i++) {
            if(A[i]==A[r] || A[r]-(r-i)==A[i] || A[r]+(r-i)==A[i])
                return false;
        }
        return true;
    }
    
    public static boolean forwardCheck(int[] X,int i, int j){
        int m=i;
        
        for(int k=0; k<N ; k++,m--){
           if(X[k]==-1){
            markKeeper[k][j]-=1;
            if(j+m >= 0 && j+m < N)markKeeper[k][j+m]-=1;
            if(j-m >= 0 && j-m < N)markKeeper[k][j-m]-=1;
           }
        }
        return true;
    }
    
    public static void unmark(int[] X,int i, int j){
        int m=i;
        for(int k=0; k<N ; k++,m--){
            if(X[k]==-1 && k!=i){
           markKeeper[k][j]+=1;
           if(j+m >= 0 && j+m < N)markKeeper[k][j+m]+=1;
           if(j-m >= 0 && j-m < N)markKeeper[k][j-m]+=1;
            }
        }
    }
    
    
    public static void printResult(LinkedHashMap<Integer,int[]> results,int N){
        if(!results.isEmpty()){
            int[] first=results.get(0);
            for (int i = 0; i < N; i++) {
                System.out.print((first[i]+1)+" ");
            }
            System.out.println();
            System.out.println("Total solutions: "+numberOfSolutions);
            for (int i = 0; i < results.size(); i++) {
                int[] result=results.get(i);
                for (int j = 0; j < N; j++) {
                    System.out.print((result[j]+1)+" ");
                }
                System.out.println();
            }
        }
        else System.out.println("No solution found!!");
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input=new Scanner(System.in);
        System.out.print("Board Size : ");
        N=input.nextInt();
        int[] X=new int[N];
        placeNQueens(X,N,0);
        printResult(results,N);
        
        System.out.println();
        numberOfSolutions=0;
        markKeeper=new int[N][N];
        int[] Y=new int[N];
        for(int i=0;i<N;i++)Y[i]=-1;
        placeNQueensFCMRV(Y,N);
        printResult(resultsFCMRV,N);
    }
    
}
