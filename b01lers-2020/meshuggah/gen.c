#include<stdlib.h>
#include<stdio.h>
#include<time.h>

int main(int argc, char *argv[]){
    int i=0;
    int timestamp=atoi(argv[1]);
    int a1=atoi(argv[2]);
    int b1=atoi(argv[3]);
    int c1=atoi(argv[4]);
    for(i=-10000;i<=10000;i++){
    srand(timestamp+i);
    int a=rand();
    int b=rand();
    int c=rand();
    //printf("%d :: %d - %d - %d\n",i,a,b,c);
    if (a== a1 && b == b1 && c == c1){
        //printf("FOUND:\n %d",timestamp+i);
        int j;
        //printf("##########################\n");
        for(j=0;j<100;j++){
            printf("%d\n",rand());
        }
        break;
    }
    }
    return 0;
}