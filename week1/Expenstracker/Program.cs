using System;

namespace Expenstracker
{
    class Program
    {
        static void Main(string[] args)
        {
            Greeting();
        }

        public static void Greeting()
        {
            Console.WriteLine("Welcome to the Expenstracker!");
            Thread.Sleep(2000);
            Console.WriteLine("Select one of the following options:");

            DisplayOptions();
            int userOption = GetUserOption();

            switch (userOption)
            {
                case 1:
                    Console.WriteLine("Add an expense");
                    break;
                case 2:
                    Console.WriteLine("View expenses");
                    break;
                case 3:
                    Console.WriteLine("Edit an expense");
                    break;
                case 4:
                    Console.WriteLine("Delete an expense");
                    break;
                case 5:
                    Console.WriteLine("Goodbye!");
                    break;
                default:
                    Console.WriteLine("Invalid option. Please try again.");
                    Greeting();
                    break;
            }

        }

        public static void DisplayOptions()
        {
            Console.WriteLine("1. Add an expense");
            Console.WriteLine("2. View expenses");
            Console.WriteLine("3. Edit an expense");
            Console.WriteLine("4. Delete an expense");
            Console.WriteLine("5. Exit");
        }

        public static int GetUserOption()
        {
            Console.WriteLine("Enter your choice:");
            string? userInput = Console.ReadLine();

            try
            {
                return int.Parse(userInput);
            }
            catch (FormatException e)
            {
                Console.WriteLine("Invalid input. Please enter a number.");
                return GetUserOption();
            }
        }
    }
}

