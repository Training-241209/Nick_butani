/* 

FizzBuzz: 
Print "Fizzbuzz" If i/3 and i/5
Print "Fizz" if i/3
Print "Buzz" if i/5
Print i otherwise

*/

/*

public - it is an access modifier. It is used to specify the access level of a class, method, or
private - it is an access modifier. It is used to specify that the member is accessible only within its own class.
protected - it is an access modifier. It is used to specify that the member is accessible only within its own class and by derived class instances.
internal - it is an access modifier. It is used to specify that the member is accessible only within its own assembly.

static keyword - it is used to declare a static member. It is used to declare a member that belongs to the type itself rather than to a specific object of that type.
public static void Main(string[] args)



*/

using System;

namespace FizzBuzz
{
    class Program
    {
        static void Main(string[] args)
        {
            int number = 15;
            FizzBuzz(number);
        }

        public static void FizzBuzz(int number){
            int count = 1;
            while(count <= number){
                if(count % 3 == 0 && count % 5 == 0){
                    Console.WriteLine("FizzBuzz");
                }
                else if(count % 3 == 0){
                    Console.WriteLine("Fizz");
                }
                else if(count % 5 == 0){
                    Console.WriteLine("Buzz");
                }
                else{
                    Console.WriteLine(count);
                }
                count++;
            }
        }
    }
    
}


