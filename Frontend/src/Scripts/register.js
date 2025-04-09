document.getElementById("register-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("http://localhost:8080/user/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ name, email, password })
    });

    const messageElement = document.getElementById("message");
    alert("registration successful");

    if (response.ok) {
        messageElement.textContent = "Registration successful!";
        messageElement.style.color = "green";
        // Optionally redirect:
        // window.location.href = "login.html";
    }
     else {
        const errorText = await response.text();
        messageElement.textContent = "Error: " + errorText;
        messageElement.style.color = "red";
    }
});