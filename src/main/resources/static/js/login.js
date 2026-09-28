const API_BASE = "/api";

document
    .getElementById("loginForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value;

        const message = document.getElementById("loginMessage");

        try {

            const response = await fetch(`${API_BASE}/students`);

            if (!response.ok) {
                throw new Error("Unable to connect to server");
            }

            const students = await response.json();

            const student = students.find(
                s => s.email === email
            );

            if (!student) {
                message.textContent = "Account not found.";
                message.style.color = "#ef4444";
                return;
            }

            /*
             * Password is not returned by the backend because
             * Student.password uses @JsonIgnore.
             *
             * For now this is a simple project login.
             * Proper authentication/JWT can be added later.
             */

            localStorage.setItem(
                "studentId",
                student.id
            );

            localStorage.setItem(
                "studentName",
                student.name
            );

            localStorage.setItem(
                "studentEmail",
                student.email
            );

            message.textContent = "Login successful!";
            message.style.color = "#10b981";

            setTimeout(() => {
                window.location.href = "dashboard.html";
            }, 700);

        } catch (error) {

            console.error(error);

            message.textContent =
                "Unable to connect to StudyBuddy server.";

            message.style.color = "#ef4444";
        }

    });