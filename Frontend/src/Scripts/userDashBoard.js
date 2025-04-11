document.addEventListener("DOMContentLoaded",async function(e) {
    const updateAccountTextLine = document.createElement('a')
    updateAccountTextLine.innerHTML = "Update Account info<br>";
    updateAccountTextLine.href = "../Pages/updateUser.html"
    const header = document.createElement('h2');
    header.textContent = "Orders";
    const body = document.body;
    const userId = localStorage.getItem('userId');
    const tableContainer = document.createElement("div");
    tableContainer.id = 'table-container';
    tableContainer.appendChild(updateAccountTextLine);

    if(userId){
        const response = await fetch("http://localhost:8080/user/orders",{
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "userId"  : userId
            }
        });
        if(response.ok){
           const data = await response.json();
           console.log(data);


           body.appendChild(tableContainer);
           const productLink = document.createElement('a');
           productLink.href = '../Pages/products.html';
           productLink.innerText = 'Browse Our selection of Books and Coffee';
           tableContainer.appendChild(productLink);
           tableContainer.appendChild(header);

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
   
   
        
   
           
   
    
    
            // Loop through the orders and create table rows
            data.forEach(order => {
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
                    priceCell.textContent = orderItem.product.price.toFixed(2);
                    row.appendChild(priceCell);
    
                    // Add Subtotal (per product, if needed)
                    const subtotalCell = document.createElement('td');
                    subtotalCell.textContent = (orderItem.product.price*orderItem.quantity).toFixed(2); // You could also calculate this for each product if needed
                    row.appendChild(subtotalCell);
    
                    // Append the row to the table body
                    tbody.appendChild(row);
                  
                });
            });
            table.appendChild(tbody);
            // Add the table to the body
            tableContainer.appendChild(table);
   
            data.forEach(order =>{
               const total = document.createElement('h2');
               total.innerHTML = "Total: " +order.total.toFixed(2);
               total.className =   'item';
               tableContainer.appendChild(total);
            });
            document.body.appendChild(tableContainer);
        }
    }
});