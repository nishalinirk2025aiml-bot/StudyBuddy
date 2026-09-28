const API_BASE = "/api";

document.addEventListener(
    "DOMContentLoaded",
    initializeCreateGroup
);


async function initializeCreateGroup() {

    loadSubjects();

    const studentId =
        localStorage.getItem("studentId");

    if (studentId) {

        document.getElementById(
            "creatorId"
        ).value = studentId;
    }
}


/* =====================================================
   LOAD SUBJECTS
   ===================================================== */

async function loadSubjects() {

    try {

        const response =
            await fetch(`${API_BASE}/subjects`);

        if (!response.ok) {
            throw new Error(
                "Unable to load subjects"
            );
        }

        const subjects =
            await response.json();

        const select =
            document.getElementById(
                "subjectId"
            );

        subjects.forEach(subject => {

            const option =
                document.createElement("option");

            option.value =
                subject.id;

            option.textContent =
                subject.name;

            select.appendChild(option);
        });

    } catch (error) {

        console.error(error);

        document.getElementById(
            "groupMessage"
        ).textContent =
            "Unable to load subjects.";
    }
}


/* =====================================================
   CREATE GROUP
   ===================================================== */

document
    .getElementById("createGroupForm")
    .addEventListener(
        "submit",
        createGroup
    );


async function createGroup(event) {

    event.preventDefault();

    const message =
        document.getElementById(
            "groupMessage"
        );

    const groupData = {

        groupName:
            document
                .getElementById("groupName")
                .value
                .trim(),

        subjectId:
            Number(
                document
                    .getElementById("subjectId")
                    .value
            ),

        creatorId:
            Number(
                document
                    .getElementById("creatorId")
                    .value
            ),

        maxMembers:
            Number(
                document
                    .getElementById("maxMembers")
                    .value
            ),

        description:
            document
                .getElementById("description")
                .value
                .trim()
    };


    if (!groupData.creatorId) {

        message.textContent =
            "Please login before creating a group.";

        message.style.color =
            "#ef4444";

        return;
    }


    try {

        const response =
            await fetch(
                `${API_BASE}/groups`,
                {

                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(
                            groupData
                        )
                }
            );


        const data =
            await response.json();


        if (!response.ok) {

            throw new Error(
                data.message ||
                "Unable to create group."
            );
        }


        message.textContent =
            "Study group created successfully! 🎉";

        message.style.color =
            "#10b981";


        document
            .getElementById(
                "createGroupForm"
            )
            .reset();


        setTimeout(() => {

            window.location.href =
                `group-details.html?id=${data.id}`;

        }, 1000);


    } catch (error) {

        console.error(error);

        message.textContent =
            error.message;

        message.style.color =
            "#ef4444";
    }
}