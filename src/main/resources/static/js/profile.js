const API_BASE = "/api";

document.addEventListener("DOMContentLoaded", loadProfile);

async function loadProfile() {

    const studentId = localStorage.getItem("studentId");

    if (!studentId) {
        window.location.href = "index.html";
        return;
    }

    try {

        const response = await fetch(
            `${API_BASE}/students/${studentId}`
        );

        if (!response.ok) {
            throw new Error("Unable to load profile");
        }

        const student = await response.json();

        document.getElementById("name").value =
            student.name || "";

        document.getElementById("email").value =
            student.email || "";

        document.getElementById("department").value =
            student.department || "";

        document.getElementById("year").value =
            student.year || "";

        document.getElementById("profileName").textContent =
            student.name || "Student";

        document.getElementById("profileEmail").textContent =
            student.email || "";

        document.getElementById("summaryName").textContent =
            student.name || "Student";

        document.getElementById("summaryEmail").textContent =
            student.email || "";

        document.getElementById("summaryDepartment").textContent =
            student.department || "Not specified";

        document.getElementById("summaryYear").textContent =
            student.year
                ? getYearText(student.year)
                : "Not specified";

    } catch (error) {

        console.error(error);

        showMessage(
            "Unable to load profile.",
            "error"
        );
    }
}


function getYearText(year) {

    const years = {
        1: "First Year",
        2: "Second Year",
        3: "Third Year",
        4: "Fourth Year"
    };

    return years[year] || year;
}


document
    .getElementById("profileForm")
    .addEventListener("submit", async function(event) {

        event.preventDefault();

        const studentId =
            localStorage.getItem("studentId");

        if (!studentId) {
            window.location.href = "index.html";
            return;
        }


        const name =
            document.getElementById("name").value.trim();

        const email =
            document.getElementById("email").value.trim();

        const department =
            document.getElementById("department").value.trim();

        const year =
            Number(document.getElementById("year").value);

        const password =
            document.getElementById("password").value.trim();


        const data = {

            name: name,

            email: email,

            department: department,

            year: year

        };


        /*
         * Only send password when user entered
         * a new password.
         */
        if (password !== "") {
            data.password = password;
        }


        try {

            const response = await fetch(
                `${API_BASE}/students/${studentId}`,
                {
                    method: "PUT",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(data)
                }
            );


            const result = await response.json();


            if (!response.ok) {

                throw new Error(
                    result.message ||
                    "Unable to update profile"
                );
            }


            // Update local storage

            localStorage.setItem(
                "studentName",
                result.name
            );

            localStorage.setItem(
                "studentEmail",
                result.email
            );


            document.getElementById("profileName")
                .textContent = result.name;

            document.getElementById("profileEmail")
                .textContent = result.email;

            document.getElementById("summaryName")
                .textContent = result.name;

            document.getElementById("summaryEmail")
                .textContent = result.email;

            document.getElementById("summaryDepartment")
                .textContent =
                    result.department || "Not specified";

            document.getElementById("summaryYear")
                .textContent =
                    result.year
                        ? getYearText(result.year)
                        : "Not specified";


            document.getElementById("password")
                .value = "";


            showMessage(
                "Profile updated successfully! ✓",
                "success"
            );

        } catch (error) {

            console.error(error);

            showMessage(
                error.message ||
                "Something went wrong.",
                "error"
            );
        }

    });


function showMessage(message, type) {

    const element =
        document.getElementById("profileMessage");

    element.textContent = message;

    if (type === "success") {

        element.style.color = "#10b981";

    } else {

        element.style.color = "#ef4444";
    }


    setTimeout(() => {

        element.textContent = "";

    }, 4000);
}