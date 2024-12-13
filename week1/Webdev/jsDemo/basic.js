//Varialbles
var x = 10;
let y = 20;
const z = 30;

//Data types
let a = 10; // Number
let b = 'Hello'; // String
let c = true; // Boolean
let d = null; // Null

//Arrays
let fruits = ['apple', 'orange', 'banana'];

//Objects
let person = {
    name: 'John',
    age: 30
};

//Functions
function add(a, b) {
    return a + b;
}

//Function expression
let multiply = function(a, b) {
    return a * b;
};

//Arrow function
let subtract = (a, b) => {
    return a - b;
};

//DOM manipulation
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM is ready');
    let button = document.querySelector('button');
    let h1 = document.getElementById('h1');
    button.addEventListener('click', function() {
        h1.style.color = 'red';
    });
});

//Event listener
let button = document.querySelector('button');
button.addEventListener('click', function() {
    console.log('Button clicked');
});

//Conditional statements
let age = 20;
if (age >= 18) {
    console.log('You are an adult');
}

//Loops
let numbers = [1, 2, 3, 4, 5];
for (let i = 0; i < numbers.length; i++) {
    console.log(numbers[i]);
}

//While loop
let i = 0;
while (i < 5) {
    console.log(i);
    i++;
}

//Do-while loop
let j = 0;
do {
    console.log(j);
    j++;
}while (j < 5);

//Switch statement
let fruit = 'apple';
switch (fruit) {
    case 'apple':
        console.log('Apple is red');
        break;
    case 'banana':
        console.log('Banana is yellow');
        break;
    default:
        console.log('Unknown fruit');
}

//Classes
class Car {
    constructor(make, model) {
        this.make = make;
        this.model = model;
    }

    display() {
        console.log(`Car: ${this.make} ${this.model}`);
    }
}


//Create an instance of the Car class
let car = new Car('Toyota', 'Corolla');
car.display();

//Inheritance
class ElectricCar extends Car {
    constructor(make, model, batteryCapacity) {
        super(make, model);
        this.batteryCapacity = batteryCapacity;
    }
    display() {
        console.log(`Electric Car: ${this.make} ${this.model} with ${this.batteryCapacity} kWh battery`);
    }
}

//Create an instance of the ElectricCar class
let electricCar = new ElectricCar('Tesla', 'Model S', 100);

//Call the display method
electricCar.display();

//Promises
let promise = new Promise(function(resolve, reject) {
    setTimeout(function() {
        resolve('Promise resolved');
    }, 2000);
});
promise.then(function(result) {
    console.log(result);
});

//Fetch API
fetch('https://api.example.com/data')
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error(error));

//Async/await
async function fetchData() {
    try {
        let response = await fetch('https://api.example.com/data');
        let data = await response.json();
        console.log(data);
    } catch(error) {
        console.error(error);
    }
}
fetchData();


//Local storage
localStorage.setItem('name', 'John');
localStorage.getItem('name');
localStorage.removeItem('name');
localStorage.clear();

//Session storage
sessionStorage.setItem('name', 'John');
sessionStorage.getItem('name');
sessionStorage.removeItem('name');
sessionStorage.clear();


//Cookies
document.cookie = 'name=John';
document.cookie = 'age=30';
console.log(document.cookie);
document.cookie = 'name=Jane';
console.log(document.cookie);

//Regular expressions
let pattern = /hello/;
let str = 'hello world';
console.log(pattern.test(str));

//Error handling
try {
    throw new Error('Something went wrong');
}
catch(error) {
    console.error(error);
}




