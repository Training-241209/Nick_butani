using System;

namespace MagicBall
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Welcome to the Magic Ball!");
            Console.WriteLine("Ask the Magic Ball a question.");
            string question = Console.ReadLine();
            
            if (!string.IsNullOrEmpty(question))
            {
                Console.WriteLine("The Magic Ball says: " + GetPrediction());
            }
            else
            {
                Console.WriteLine("You must ask a question.");
                Main(args);
            }
        }

        public static string GetPrediction()
        {
            string[] predictions = {
                "The Magic Ball is not sure.",
                "The Magic Ball says no.",
                "The Magic Ball says yes.",
                "The Magic Ball says it depends.",
                "The Magic Ball says it's up to you.",
                "The Magic Ball says it's a mystery.",
                "The Magic Ball says it's a lie.",
                "The Magic Ball says it's a trick."
            };

            Random random = new Random();
            int index = random.Next(predictions.Length);
            return predictions[index];
        }
    }
}
