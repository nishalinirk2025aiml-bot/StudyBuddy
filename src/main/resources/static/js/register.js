const API_BASE = "/api";

document
    .getElementById("registerForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const message =
            document.getElementById("registerMessage");

        const studentData = {

            name: document
                .getElementById("name")
                .value
                .trim(),

            email: document
                .getElementById("email")
                .value
                .trim(),

            password: document
                .getElementById("password")
                .value,

            department: document
                .getElementById("department")
                .value
                .trim(),

            year: Number(
                document.getElementById("year").value
            )
        };

        try {

            const response = await fetch(
                `${API_BASE}/students`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(studentData)
                }
            );

            const data = await response.json();

            if (!response.ok) {

                if (data.message) {
                    throw new Error(data.message);
                }

                throw new Error(
                    "Registration failed."
                );
            }

            message.textContent =
                "Account created successfully!";

            message.style.color = "#10b981";

            document
                .getElementById("registerForm")
                .reset();

            setTimeout(() => {

                window.location.href =
                    "index.html";

            }, 1200);

        } catch (error) {

            console.error(error);

            message.textContent =
                error.message;

            message.style.color =
                "#ef4444";
        }

    });