#include<iostream>
using namespace std;

int main()
{
extern int var1 = 20;
extern int var2 = 40;
int sum;
    
sum = var1 + var2;
cout<<"Sum: "<<sum<<endl;
}
