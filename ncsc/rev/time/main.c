#include<stdio.h>
#include<stdlib.h> 
#include<string.h>
int main(){
	int i;
	for(i=1579409148-58;i<=1579409148+58;i++){
			srand(i);
			int j;
			printf("%d  :",i);
			char s[]="securinets";
			for(j=0;j<strlen(s);j++){
				printf("%d,",s[j]^(rand()%255));
			}
			printf("\n");
	}
	return 0;
}