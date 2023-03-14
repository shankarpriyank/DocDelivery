#include <iostream>
using namespace std;

struct Node{
    int data;
    struct Node *next;
};


void Traversal(struct Node* top)
{
    
    struct Node * ptr=top;
    while(ptr!=NULL)
    {
        cout<<"ELEMENT:"<<ptr->data<<endl;
        ptr=ptr->next;
    }
} 
int IsEmpty(struct Node *top)
{
    if(top==NULL) {
        return 1;
    }
    else
    {
        return 0;
    }
}


int IsFull(struct Node *top)
{
    struct Node *n = (struct Node *)malloc(sizeof(struct Node));
    if(n==NULL) {
        return 1;
    }
    else
    {
        return 0;
    }
}


struct Node *Push(struct Node *top,int val){
 if(IsFull(top)==1)
 {
     cout<<"stack is already full Babe!Better watch out"<<endl;
     return top;
 }
 else
 {
     struct Node *n = (struct Node *)malloc(sizeof(struct Node));
     n->data=val;
     n->next=top;
     top=n;
     return top;
 }
}

 
 int Pop(struct Node **top)
 {
     if(IsEmpty(*top)==1){
         cout<<"Empty like Shreyash's Heart !!"<<endl;
         return -1;
     }
     else
     {
         struct Node *sb=*top;
          int x=sb->data;
         *top=(*top)->next;
         free(sb);
         return x;
        
     }
 }
 

 int main()
 {
     struct Node *top=NULL;
     
     
cout<<"before pushing"<<endl;
     Traversal(top);
     cout<<"after pushing"<<endl;

     top=Push(top,33);
     top=Push(top,35);
     top=Push(top,36);
     top=Push(top,37);
     top=Push(top,38);
     Traversal(top);
     cout<<"after popping"<<endl;
     int d=Pop(&top);
     int e=Pop(&top);
     int f=Pop(&top);
     int g=Pop(&top);
     int h=Pop(&top);
     int i=Pop(&top);
     
     Traversal(top);

     return 0;


 }