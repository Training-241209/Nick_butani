const form = document.querySelector('form');
const input = document.querySelector('input');
const list = document.querySelector('ul');

form.addEventListener('submit', function(e) {
    e.preventDefault();

    const todoText = input.value;

    const todoItem = document.createElement('li');
    const checkbox = document.createElement('input');
    checkbox.type = 'checkbox';
    checkbox.style.marginRight = '10px';

    const textSpan = document.createElement('span');
    textSpan.textContent = todoText;

    checkbox.addEventListener('change', function() {
        if (checkbox.checked) {
            todoItem.style.textDecoration = 'line-through';
        } else {
            todoItem.style.textDecoration = 'none';
        }
    });

    const trashIcon = document.createElement('i');
    trashIcon.classList.add('fas', 'fa-trash-alt');
    trashIcon.style.cursor = 'pointer';
    trashIcon.style.marginLeft = '10px';

    trashIcon.addEventListener('click', function() {
        list.removeChild(todoItem);
    });

    const editIcon = document.createElement('i');
    editIcon.classList.add('fas', 'fa-edit');
    editIcon.style.cursor = 'pointer';
    editIcon.style.marginLeft = '10px';

    editIcon.addEventListener('click', function() {
        const inputField = document.createElement('input');
        inputField.type = 'text';
        inputField.value = textSpan.textContent;
        textSpan.textContent = '';  

        
        textSpan.appendChild(inputField);
        inputField.focus();

       
        inputField.addEventListener('blur', function() {
            const newText = inputField.value.trim();
            if (newText !== '') {
                textSpan.textContent = newText;
            } else {
                textSpan.textContent = 'Unnamed Task';
            }
        });
    });

    todoItem.appendChild(checkbox);
    todoItem.appendChild(textSpan);
    todoItem.appendChild(editIcon);
    todoItem.appendChild(trashIcon);

    list.appendChild(todoItem);

    input.value = '';
});
