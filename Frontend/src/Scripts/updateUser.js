
document.getElementById("update-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("http://localhost:8080/user/update", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            "userId"  : localStorage.getItem('userId')
        },
        body: JSON.stringify({ name, email, password })
    });

    const messageElement = document.getElementById("message");

    if (response.ok) {
        alert("Update successful");

        messageElement.textContent = "Update successful!";
        messageElement.style.color = "green";
        // Optionally redirect:
        // window.location.href = "login.html";
        window.location.replace("login.html");

    }
     else {
        const errorText = await response.text();
        messageElement.textContent = "Error: " + errorText;
        messageElement.style.color = "red";
    }
});