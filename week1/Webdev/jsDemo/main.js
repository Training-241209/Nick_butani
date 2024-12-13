document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM is ready');
    let button = document.querySelector('button');
    let h1 = document.getElementById('h1');

    button.addEventListener('click', function() {
        h1.style.color = 'red';
    });
});