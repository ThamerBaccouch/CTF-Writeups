#include<bits/stdc++.h>

#define vi vector<int>
#define ii pair<long long,long long>
#define vii vector<pair<int,int>>
#define ll long long
#define si set<int>
#define msi multiset<int>
#define fastio std::ios::sync_with_stdio(false); cin.tie(NULL);
#define lim 23300005
 
#define pi 3.14159265358979323846
using namespace std;
ll num_bombs[505][505];
ll sum[505][505];
int tab[505][505];
int n=500;
ll ans=0;
ll number=0;
ii sum_right(int i,int j){
	ll s=0;
	ll bombs=0;
	for (int k=j;k<n;k++){
		s+=tab[i][k];
		if (tab[i][k] == -1){
			bombs++;
		}
	}
	return {s,bombs};
}

ii sum_down(int i,int j){
	ll s=0;
	ll bombs=0;
	for (int k=i;k<n;k++){
		s+=tab[k][j];
		if (tab[k][j] == -1){
			bombs++;
		}
	}
	return {s,bombs};
}

int sum_all(int i,int j){
		ll s=0;
		ii p;
		ll bombs=0;
		p= sum_right(i,j);
		s+=p.first;
		bombs+=p.second;

		p=sum_down(i,j);
		s+=p.first;
		bombs+=p.second;

		s-=tab[i][j];

		if (tab[i][j] == -1)
			bombs--;

		
		num_bombs[i][j] = bombs + num_bombs[i+1][j+1];
		sum[i][j] = s + sum[i+1][j+1];
		return 0;

}



int main(){
	//fastio;
	string line;
	cout << "[*] OPENING FILE ..\n";
  	ifstream myfile ("input.txt");
  	int cnt=0;
  	cout << "[*] READING INPUT..\n";
  	int i=0,j=0;
  	if (myfile.is_open())
  	{
    	while ( getline (myfile,line) )
    	{
    		cnt++;
    		istringstream iss(line);
    		for(string s;iss >>s;){
    			tab[i][j]=stoi(s);
    			j++;
    		}
    		j=0;
    		i++;
    	}
    	myfile.close();
  	}
  	cout << "[*] PREPARING SUM TABLE..\n";
  	for (int i=n-1;i>=0;i--){
  		for(int j=n-1;j>=0;j--){
  			sum_all(i,j);
  		}
  	}
  	cout << "[+] DONE..\n";
  	cout << "[*] CALCULATING ANSWER..\n";
  	for (int head_x=0;head_x<n;head_x++){
  		cout << "line :"<<head_x<<"\n";
  		for(int head_y=0;head_y<n;head_y++){
  			for(int i=head_x;i<n;i++){
  				for(int j=head_y;j<n;j++){

  					ll s = sum[head_x][head_y] - sum[head_x][j+1] - sum[i+1][head_y] + sum[i+1][j+1];
  					if (s % 13 ==0){
  						ll bombs= num_bombs[head_x][head_y] - num_bombs[head_x][j+1] - num_bombs[i+1][head_y] + num_bombs[i+1][j+1];
  						if (bombs % 2 == 1)
  							s*=-1;
  						ans+=s;
  						number++;
  					
  					}
  				}
  			}
  		}
  	}
  	cout << "FLAG : flag{"<< ans <<"}\n";
  	return 0;
}