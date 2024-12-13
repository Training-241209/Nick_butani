let cart = JSON.parse(localStorage.getItem('cart')) || [];
function updateCartCount() {
  document.getElementById('cart-count').textContent = cart.length;
  localStorage.setItem('cart', JSON.stringify(cart));
}

function addToCart(productId) {
  const product = document.querySelector(`.product[data-id="${productId}"]`);
  const name = product.querySelector('h3').textContent;
  const price = product.querySelector('p').textContent;

  const productObj = {
    id: productId,
    name: name,
    price: price,
  };

  if (!cart.some(item => item.id === productId)) {
    cart.push(productObj);
    alert(`${name} added to cart!`);
  } else {
    alert(`${name} is already in your cart.`);
  }

  updateCartCount();
}
function removeFromCart(productId) {
  cart = cart.filter(item => item.id !== productId);
  alert(`Product removed from cart!`);
  updateCartCount();
  displayCartItems();  // Update cart display
}

function displayCartItems() {
  const cartList = document.getElementById('cart-list');
  cartList.innerHTML = '';

  if (cart.length === 0) {
    cartList.innerHTML = '<p>Your cart is empty.</p>';
    return;
  }

  cart.forEach(item => {
    const cartItem = document.createElement('div');
    cartItem.classList.add('cart-item');
    cartItem.innerHTML = `
      <h3>${item.name}</h3>
      <p>${item.price}</p>
      <button class="remove-from-cart" data-id="${item.id}">Remove</button>
    `;
    cartList.appendChild(cartItem);
  });

  // Attach event listeners to remove buttons
  document.querySelectorAll('.remove-from-cart').forEach(button => {
    button.addEventListener('click', (e) => {
      const productId = e.target.dataset.id;
      removeFromCart(productId);
    });
  });
}

updateCartCount();

if (window.location.pathname.includes('cart.html')) {
  displayCartItems();
}

document.querySelectorAll('.add-to-cart').forEach(button => {
  button.addEventListener('click', () => {
    const productId = button.closest('.product').dataset.id;
    addToCart(productId);
  });
});
