const API_BASE = "/api";

let allGroups = [];

document.addEventListener(
    "DOMContentLoaded",
    initializeGroupsPage
);


async function initializeGroupsPage() {

    await loadSubjects();

    await loadGroups();
}


/* =====================================================
   LOAD GROUPS
   ===================================================== */

async function loadGroups() {

    try {

        const response =
            await fetch(`${API_BASE}/groups`);

        if (!response.ok) {
            throw new Error(
                "Unable to load groups"
            );
        }

        allGroups =
            await response.json();

        displayGroups(allGroups);

    } catch (error) {

        console.error(error);

        document.getElementById(
            "groupsContainer"
        ).innerHTML = `

            <div class="empty-message">

                <h3>Unable to load groups</h3>

                <p>
                    Please make sure Spring Boot
                    is running.
                </p>

            </div>
        `;
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
            return;
        }

        const subjects =
            await response.json();

        const select =
            document.getElementById(
                "subjectFilter"
            );

        subjects.forEach(subject => {

            const option =
                document.createElement("option");

            option.value = subject.id;

            option.textContent =
                subject.name;

            select.appendChild(option);
        });

    } catch (error) {

        console.error(
            "Subject loading error:",
            error
        );
    }
}


/* =====================================================
   DISPLAY GROUPS
   ===================================================== */

function displayGroups(groups) {

    const container =
        document.getElementById(
            "groupsContainer"
        );

    container.innerHTML = "";

    if (groups.length === 0) {

        container.innerHTML = `

            <div class="empty-message">

                <h3>No groups found 🔍</h3>

                <p>
                    Try another search or create
                    a new study group.
                </p>

            </div>

        `;

        return;
    }

    groups.forEach(group => {

        const card =
            document.createElement("div");

        card.className = "group-card";

        card.innerHTML = `

            <div class="group-card-icon">
                📚
            </div>

            <span class="group-subject">

                ${group.subject?.name ||
                "Subject"}

            </span>

            <h3>
                ${group.groupName}
            </h3>

            <p>
                ${group.description ||
                "Let's study together!"}
            </p>

            <div class="group-meta">

                <span>
                    👤
                    ${group.creator?.name ||
                    "Student"}
                </span>

                <span>
                    👥
                    ${group.maxMembers} members
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


/* =====================================================
   SEARCH GROUPS
   ===================================================== */

function searchGroups() {

    const searchText =
        document
            .getElementById("searchInput")
            .value
            .toLowerCase()
            .trim();

    const subjectId =
        document
            .getElementById("subjectFilter")
            .value;

    const filtered =
        allGroups.filter(group => {

            const matchesName =
                group.groupName
                    .toLowerCase()
                    .includes(searchText);

            const matchesSubject =
                !subjectId ||
                String(group.subject?.id) ===
                String(subjectId);

            return (
                matchesName &&
                matchesSubject
            );
        });

    displayGroups(filtered);
}


function openGroup(groupId) {

    window.location.href =
        `group-details.html?id=${groupId}`;
}


/* Search while typing */

document.addEventListener(
    "DOMContentLoaded",
    () => {

        const input =
            document.getElementById(
                "searchInput"
            );

        if (input) {

            input.addEventListener(
                "input",
                searchGroups
            );
        }

        const subjectFilter =
            document.getElementById(
                "subjectFilter"
            );

        if (subjectFilter) {

            subjectFilter.addEventListener(
                "change",
                searchGroups
            );
        }
    }
);