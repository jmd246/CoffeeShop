document.addEventListener("DOMContentLoaded", async function (e) {
    e.preventDefault();
    document.getElementById('greetings').innerText = "Welcome Back " + localStorage.getItem('username');

    const updateAccountTextLine = document.createElement('a');
    updateAccountTextLine.innerHTML = "Update Account info<br>";
    updateAccountTextLine.href = "../Pages/updateUser.html";

    const header = document.createElement('h2');
    header.textContent = "Orders";

    const body = document.body;
    const userId = localStorage.getItem('userId');
    const tableContainer = document.createElement("div");
    tableContainer.id = 'table-container';
    tableContainer.appendChild(updateAccountTextLine);

    if (!userId) {
        window.location.replace("login.html");
        return;
    }

    const response = await fetch("http://localhost:8080/user/orders", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "userId": userId
        }
    });

    if (!response.ok) {
        window.location.replace("login.html");
        return;
    }

    const data = await response.json();
    console.log(data);

    const productLink = document.createElement('a');
    productLink.href = '../Pages/products.html';
    productLink.innerText = 'Browse Our selection of Books and Coffee';

    tableContainer.appendChild(productLink);
    tableContainer.appendChild(header);

    const table = document.createElement('table');
    table.id = "orders-table";
    table.className = "item";

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
        <th>Action</th>
    `;
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');

    data.forEach(order => {
        const totalRef = document.createElement('h2');
        totalRef.className = 'item';
        totalRef.innerHTML = "Total: " + order.total.toFixed(2);
        order.totalRef = totalRef;

        order.orderItems.forEach((orderItem, index) => {
            const row = document.createElement('tr');

            const orderIdCell = document.createElement('td');
            orderIdCell.textContent = index === 0 ? order.orderId : '';
            //orderIdCell.textContent = order.orderId;
            row.appendChild(orderIdCell);

            const statusCell = document.createElement('td');
            statusCell.textContent = order.status;
            row.appendChild(statusCell);

            const orderDateCell = document.createElement('td');
            orderDateCell.textContent = order.orderDate;
            row.appendChild(orderDateCell);

            const productNameCell = document.createElement('td');
            productNameCell.textContent = orderItem.product.name;
            row.appendChild(productNameCell);

            const quantityCell = document.createElement('td');
            const quantityInput = document.createElement('input');
            quantityInput.type = 'number';
            quantityInput.value = orderItem.quantity;
            quantityInput.min = 1;
            quantityInput.dataset.orderId = order.orderId;
            quantityInput.dataset.productId = orderItem.product.id;
            quantityInput.dataset.originalQuantity = orderItem.quantity;
            quantityCell.appendChild(quantityInput);
            row.appendChild(quantityCell);

            const priceCell = document.createElement('td');
            priceCell.textContent = orderItem.product.price.toFixed(2);
            row.appendChild(priceCell);

            const subtotalCell = document.createElement('td');
            subtotalCell.textContent = (orderItem.product.price * orderItem.quantity).toFixed(2);
            row.appendChild(subtotalCell);

            const actionButtonCell = document.createElement('td');
            const updateButton = document.createElement('button');
            updateButton.textContent = 'Update';

            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            

            updateButton.addEventListener('click', async () => {
                const newQuantity = parseInt(quantityInput.value);
                const originalQuantity = parseInt(quantityInput.dataset.originalQuantity);
                const orderId = quantityInput.dataset.orderId;
                const productId = quantityInput.dataset.productId;

                if (newQuantity < 1 || isNaN(newQuantity)) {
                    alert("Please enter a valid quantity.");
                    return;
                }

                //calculate the change in quantity since its +-
                const quantityChange = newQuantity - originalQuantity;

                if (quantityChange === 0) {
                    alert("No changes made.");
                    return;
                }

                try {
                    const patchResponse = await fetch("http://localhost:8080/order/update", {
                        method: "PATCH",
                        headers: {
                            "Content-Type": "application/json",
                            "userId": userId
                        },
                        body: JSON.stringify({
                            orderId,
                            productId,
                            newQuantity,
                            quantityChange
                        })
                    });

                    if (patchResponse.ok) {
                        const updatedOrder = await patchResponse.json();
                        console.log(updatedOrder.orderItems);
                        //find the updateditem in orderitems

                        const updatedItem = updatedOrder.orderItems.find(item => item.product.id == productId);
                    //update subtotal
                        const newSubtotal = updatedItem.product.price * newQuantity;
                        subtotalCell.textContent = newSubtotal.toFixed(2);
                        order.totalRef.innerHTML = "Total: " + updatedOrder.total.toFixed(2);

                        quantityInput.dataset.originalQuantity = newQuantity;
                    } else {
                        alert("Failed to update quantity.");
                    }
                } catch (error) {
                    console.error("Error updating quantity:", error);
                    alert("An error occurred while updating.");
                }
            });
            deleteButton.addEventListener('click', async () => {
                const orderId = quantityInput.dataset.orderId;
                const productId = quantityInput.dataset.productId;
                try {
                    const deleteResponse = await fetch("http://localhost:8080/order/delete", {
                        method: "DELETE",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify({
                            orderId,
                            productId
                        })
                    });

                    if (deleteResponse.ok) {
                        const responseText = await deleteResponse.text();
                        if(!responseText || responseText === null){
                            alert("Order was deleted");
                            row.remove();
                            order.totalRef.remove();
                        }
                        else{
                            const deleteOrder =  JSON.parse(responseText);
                            console.log(deleteOrder);
                            order.totalRef.innerHTML = "Total: " + deleteOrder.total.toFixed(2);
                            row.remove();
                       }
                    }
                } catch (error) {
                    console.error("Error deleting row:", error);
                    alert("An error occurred while deleting.");
                }


            });

            actionButtonCell.appendChild(updateButton);
            row.appendChild(actionButtonCell);
            actionButtonCell.appendChild(deleteButton);
            //row.appendChild(deleteButtonCell);

            tbody.appendChild(row);
        });

        // Append the order total after all items in the order
        tableContainer.appendChild(order.totalRef);
    });

    table.appendChild(tbody);
    tableContainer.appendChild(table);
    body.appendChild(tableContainer);
});