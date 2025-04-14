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

            const updateButtonCell = document.createElement('td');
            const updateButton = document.createElement('button');
            updateButton.textContent = 'Update';

            updateButton.addEventListener('click', async () => {
                const newQuantity = parseInt(quantityInput.value);
                const originalQuantity = parseInt(quantityInput.dataset.originalQuantity);
                const orderId = quantityInput.dataset.orderId;
                const productId = quantityInput.dataset.productId;

                if (newQuantity < 1 || isNaN(newQuantity)) {
                    alert("Please enter a valid quantity.");
                    return;
                }

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
                        const updatedItem = updatedOrder.orderItems.find(item => item.product.id == productId);

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

            updateButtonCell.appendChild(updateButton);
            row.appendChild(updateButtonCell);

            tbody.appendChild(row);
        });

        // Append the order total after all items in the order
        tableContainer.appendChild(order.totalRef);
    });

    table.appendChild(tbody);
    tableContainer.appendChild(table);
    body.appendChild(tableContainer);
});