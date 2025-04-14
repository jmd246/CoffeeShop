document.getElementById("books").addEventListener("click", async function (e) {
    e.preventDefault();
    console.log("books");
    const response = await fetch("http://localhost:8080/books/all",{
        method : "GET",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(),
    });
    if(response.ok){
        const data = await response.json();
        const bookList = document.getElementById('bookList');
        bookList.innerHTML = '';
        const bookHeader = document.createElement('h2');
        bookHeader.textContent = 'book'; 
        bookList.appendChild(bookHeader);
        const container = document.createElement('div');
        container.className = 'item';
        const list = document.createElement("ul");
        data.forEach(element => {
            if(element.available){
            const item = document.createElement('li');
            const description = document.createElement('div');
            const itemName =  document.createElement('p');
            const itemPrice =  document.createElement('p');
            const author = document.createElement('p');
            itemName.innerHTML = "Name: " + element.name;
            itemPrice.innerHTML = "Price: " + element.price;
            author.innerHTML = "Author: " + element.author;
            description.appendChild(itemName);
            description.appendChild(itemPrice);
            description.appendChild(author);
            // Quantity input
        const quantityLabel = document.createElement('label');
        quantityLabel.innerText = "Quantity: ";
        const quantityInput = document.createElement('input');
        quantityInput.type = 'number';
        quantityInput.min = '1';
        quantityInput.value = '1';
        quantityInput.className = 'quantityInput'; 
        quantityLabel.appendChild(quantityInput);
        description.appendChild(quantityLabel);
        description.appendChild(quantityInput);

           
            const buyNow = document.createElement('button');
            buyNow.innerText = "Buy Now";
            buyNow.className = 'addToCart';
            buyNow.addEventListener("click" ,async function(e){
                e.preventDefault();
                const quantity = parseInt(quantityInput.value);
                const response = await fetch(`http://localhost:8080/order/${element.id}`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "userId"  : localStorage.getItem('userId')
                    },
                    body: JSON.stringify({
                        quantity : quantity
                    })
                });
            
                if (response.ok) {
                    const result = await response.json();
                    console.log("Added to cart:", result);
                    window.location.replace("../Pages/userDashBoard.html");

                } else {
                    console.error("Failed to add to cart");
                }
            });
            description.appendChild(buyNow);
            item.appendChild(description);
            list.appendChild(item);
            console.log(element);
            }
        });
        container.appendChild(list);
        bookList.appendChild(container);
    }
});
document.getElementById("coffee").addEventListener("click", async function (e) {
    e.preventDefault();
    console.log("cofeeeeee");
    const response = await fetch("http://localhost:8080/coffee/all",{
        method : "GET",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(),
    });
    if(response.ok){
        const data = await response.json();
        const coffeeList = document.getElementById('coffeeList');
        coffeeList.innerHTML = '';
        const coffeeHeader = document.createElement('h2');
        coffeeHeader.textContent = 'Coffee'; 
        coffeeList.appendChild(coffeeHeader);
        const container = document.createElement('div');
        container.className = 'item';
        const list = document.createElement("ul");
        data.forEach(element => {
            if(element.available){
            const item = document.createElement('li');
            const description = document.createElement('div');
            const itemName =  document.createElement('p');
            const cold = document.createElement('p');
            const itemPrice =  document.createElement('p');
            itemName.innerHTML = "Name: " + element.name;
            itemPrice.innerHTML = "Price: " + element.price;
            cold.innerHTML = element.cold ? "iced" : "hot";
            description.appendChild(itemName);
            description.appendChild(itemPrice);
            description.appendChild(cold);
            // Quantity input
        const quantityLabel = document.createElement('label');
        quantityLabel.innerText = "Quantity: ";
        const quantityInput = document.createElement('input');
        quantityInput.type = 'number';
        quantityInput.min = '1';
        quantityInput.value = '1';
        quantityInput.className = 'quantityInput'; 
        quantityLabel.appendChild(quantityInput);
        description.appendChild(quantityLabel);
        description.appendChild(quantityInput);

           
            const buyNow = document.createElement('button');
            buyNow.innerText = "Buy Now";
            buyNow.className = 'addToCart';
            buyNow.addEventListener("click" ,async function(e){
                e.preventDefault();
                const quantity = parseInt(quantityInput.value);
                const response = await fetch(`http://localhost:8080/order/${element.id}`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "userId"  : localStorage.getItem('userId')
                    },
                    body: JSON.stringify({
                        quantity : quantity
                    })
                });
            
                if (response.ok) {
                    const result = await response.json();
                    console.log("Added to cart:", result);
                    window.location.replace("../Pages/userDashBoard.html");

                } else {
                    const errText = await response.text();
                    alert(errText);
                    console.error("Failed to add to cart");
                }
            });
            description.appendChild(buyNow);
            item.appendChild(description);
            list.appendChild(item);
            console.log(element);
            }
        });
        container.appendChild(list);
        coffeeList.appendChild(container);
    }
});


