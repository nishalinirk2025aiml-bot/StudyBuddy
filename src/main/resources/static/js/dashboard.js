const API_BASE = "/api";

document.addEventListener(
    "DOMContentLoaded",
    loadDashboard
);

async function loadDashboard() {

    try {

        const [
            groupsResponse,
            subjectsResponse,
            studentsResponse
        ] = await Promise.all([

            fetch(`${API_BASE}/groups`),

            fetch(`${API_BASE}/subjects`),

            fetch(`${API_BASE}/students`)
        ]);

        const groups =
            await groupsResponse.json();

        const subjects =
            await subjectsResponse.json();

        const students =
            await studentsResponse.json();

        document.getElementById(
            "groupCount"
        ).textContent = groups.length;

        document.getElementById(
            "subjectCount"
        ).textContent = subjects.length;

        document.getElementById(
            "studentCount"
        ).textContent = students.length;

        displayGroups(groups);

    } catch (error) {

        console.error(
            "Dashboard loading error:",
            error
        );
    }
}


function displayGroups(groups) {

    const container =
        document.getElementById(
            "groupContainer"
        );

    container.innerHTML = "";

    if (groups.length === 0) {

        container.innerHTML = `
            <div class="empty-message">
                <h3>No study groups yet 📚</h3>
                <p>Create the first study group!</p>
            </div>
        `;

        return;
    }

    groups.slice(0, 6).forEach(group => {

        const card =
            document.createElement("div");

        card.className = "group-card";

        card.innerHTML = `

            <div class="group-card-icon">
                📚
            </div>

            <span class="group-subject">
                ${group.subject?.name || "Subject"}
            </span>

            <h3>
                ${group.groupName}
            </h3>

            <p>
                ${group.description ||
                "Study together and learn together."}
            </p>

            <div class="group-meta">

                <span>
                    👥 Max ${group.maxMembers}
                </span>

                <span>
                    👤 ${group.creator?.name || "Student"}
                </span>

            </div>

            <button
                class="join-button"
                onclick="openGroup(${group.id})">

                View Group

            </button>
        `;

        container.appendChild(card);
    });
}


function openGroup(groupId) {

    window.location.href =
        `group-details.html?id=${groupId}`;
}