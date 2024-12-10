using System;

namespace HellloC_;

class Program
{
    static void Main(string[] args)
    {

        int age = 25;
        string name = "John";
        Console.WriteLine("Hello, World!");
        Console.WriteLine($"My name is {name} and I am {age} years old.");
      
      

        for (int i = 0; i < 10; i++)
        {
            Console.WriteLine(i);
        }

        while (age < 30)
        {
            Console.WriteLine(age);
            age++;
        }

        if (age == 30)
        {
            Console.WriteLine("I am 30 years old.");
        }
        else
        {
            Console.WriteLine("I am not 30 years old.");
        }

        double pi = 3.14;
        int intPi = (int)pi;
        Console.WriteLine($"The value of pi as an integer is {intPi}.");
    
    }
}
