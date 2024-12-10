using System;
using System.Diagnostics;

namespace SimpleStopwatch
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Welcome to the Stopwatch App!");
            Console.WriteLine("Commands: 'start' to begin, 'stop' to end, 'exit' to quit the app.");
            
            Stopwatch stopwatch = new Stopwatch();
            bool running = true;

            while (running)
            {
                Console.Write("Enter a command: ");
                string? command = Console.ReadLine()?.ToLower();

                switch (command)
                {
                    case "start":
                        if (stopwatch.IsRunning)
                        {
                            Console.WriteLine("Stopwatch is already running.");
                        }
                        else
                        {
                            stopwatch.Start();
                            Console.WriteLine("Stopwatch started!");
                        }
                        break;

                    case "stop":
                        if (stopwatch.IsRunning)
                        {
                            stopwatch.Stop();
                            Console.WriteLine($"Stopwatch stopped. Elapsed time: {stopwatch.Elapsed}");
                        }
                        else
                        {
                            Console.WriteLine("Stopwatch is not running.");
                        }
                        break;

                    case "exit":
                        running = false;
                        Console.WriteLine("Exiting... Goodbye!");
                        break;

                    default:
                        Console.WriteLine("Invalid command. Use 'start', 'stop', or 'exit'.");
                        break;
                }
            }
        }
    }
}
