document.getElementById("login-form").addEventListener("submit", async function (e) {
    e.preventDefault();
    const tableContainer = document.createElement("div");
    tableContainer.id = 'table-container';
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("http://localhost:8080/user/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({email, password })
    });

    const messageElement = document.getElementById("message");

    if (response.ok) {
        const data = await response.json();
        console.log(response.headers.get("userId"));
        localStorage.setItem("userId",response.headers.get('userId'));
        console.log("localstorage   "   +  localStorage.getItem('userId'));
        messageElement.textContent = "login successful! " + JSON.stringify(data.orders,null,2);
        messageElement.style.color = "green";
        const body =  document.body;
        body.innerHTML  =  "";

        // Create the new content (order table)
        const table = document.createElement('table');
        table.id = "orders-table";
        table.className = "item"

        // Add the table header
        const thead = document.createElement('thead');
        const headerRow = document.createElement('tr');
        headerRow.innerHTML = `
            <th>Order ID</th>
            <th>Status</th>
            <th>Order Date</th>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Subtotal</th>
        `;
        thead.appendChild(headerRow);
        table.appendChild(thead);

        // Add the table body where order items will go
        const tbody = document.createElement('tbody');


     

         // Assuming `data.orders` is the list of orders
         const orders = data.orders;

 
 
         // Loop through the orders and create table rows
         orders.forEach(order => {
             // Loop through each order's items (orderItems)
             order.orderItems.forEach((orderItem, index) => {
                 const row = document.createElement('tr');
 
                 // Add Order ID to the first row, empty for subsequent rows
                 const orderIdCell = document.createElement('td');
                 orderIdCell.textContent = index === 0 ? order.orderId : '';
                 row.appendChild(orderIdCell);
 
                 // Add Order Status
                 const statusCell = document.createElement('td');
                 statusCell.textContent = order.status;
                 row.appendChild(statusCell);
 
                 // Add Order Date
                 const orderDateCell = document.createElement('td');
                 orderDateCell.textContent = order.orderDate;
                 row.appendChild(orderDateCell);
 
                 // Add Product Name
                 const productNameCell = document.createElement('td');
                 productNameCell.textContent = orderItem.product.name;
                 row.appendChild(productNameCell);
 
 
                 // Add Quantity
                 const quantityCell = document.createElement('td');
                 quantityCell.textContent = orderItem.quantity;
                 row.appendChild(quantityCell);
 
                 // Add Price
                 const priceCell = document.createElement('td');
                 priceCell.textContent = orderItem.product.price;
                 row.appendChild(priceCell);
 
                 // Add Subtotal (per product, if needed)
                 const subtotalCell = document.createElement('td');
                 subtotalCell.textContent = orderItem.product.price*orderItem.quantity; // You could also calculate this for each product if needed
                 row.appendChild(subtotalCell);
 
                 // Append the row to the table body
                 tbody.appendChild(row);
               
             });
         });
         table.appendChild(tbody);
         // Add the table to the body
         tableContainer.appendChild(table);

         orders.forEach(order =>{
            const total = document.createElement('h2');
            total.innerHTML = order.total;
            total.className =   'item';
            tableContainer.appendChild(total);
         });
         const updateAccountTextLine = document.createElement('a')
         updateAccountTextLine.innerHTML = "Update Account info";
         updateAccountTextLine.href = "../Pages/updateUser.html"
         tableContainer.appendChild(updateAccountTextLine);
         body.appendChild(tableContainer);
         
 
        // Optionally redirect:
        // window.location.href = "login.html";
    }
     else {
        const errorText = await response.text();
        messageElement.textContent = "Error: " + errorText;
        messageElement.style.color = "red";
    }
});


async function loginUser(email, password) {
    try {
        const response = await fetch("http://localhost:8080/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        // Check if the response is ok (status code 200-299)
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        // Parse the response JSON
        const data = await response.json(); // This is the user data object

        // Handle the data (e.g., save token, redirect, etc.)
        console.log(data); // Example of handling the response
    } catch (error) {
        console.error('Error during login:', error);
    }
}
