const API = "/api";

let allGroups = [];
let allSubjects = [];

document.addEventListener("DOMContentLoaded", () => {
    loadSubjects();
    loadGroups();

    const searchButton = document.getElementById("searchBtn");

    if (searchButton) {
        searchButton.addEventListener("click", searchGroups);
    }
});


// ===============================
// LOAD SUBJECTS
// ===============================
async function loadSubjects() {

    try {
        const response = await fetch(`${API}/subjects`);

        if (!response.ok) {
            throw new Error("Failed to load subjects");
        }

        allSubjects = await response.json();

        const subjectSelect = document.getElementById("subjectFilter");

        if (!subjectSelect) return;

        subjectSelect.innerHTML = `<option value="">All Subjects</option>`;

        allSubjects.forEach(subject => {

            const option = document.createElement("option");

            option.value = subject.id;
            option.textContent = subject.name;

            subjectSelect.appendChild(option);
        });

    } catch (error) {

        console.error("Subject loading error:", error);
    }
}


// ===============================
// LOAD ALL GROUPS
// ===============================
async function loadGroups() {

    try {

        const response = await fetch(`${API}/groups`);

        if (!response.ok) {
            throw new Error("Failed to load groups");
        }

        allGroups = await response.json();

        displayGroups(allGroups);

    } catch (error) {

        console.error("Group loading error:", error);

        const container = document.getElementById("groupsContainer");

        if (container) {
            container.innerHTML = `
                <div class="empty-state">
                    <h3>Unable to load study groups</h3>
                    <p>Please make sure Spring Boot is running.</p>
                </div>
            `;
        }
    }
}


// ===============================
// SEARCH GROUPS
// ===============================
function searchGroups() {

    const searchInput = document.getElementById("searchInput");
    const subjectFilter = document.getElementById("subjectFilter");

    const searchText = searchInput
        ? searchInput.value.trim().toLowerCase()
        : "";

    const selectedSubject = subjectFilter
        ? subjectFilter.value
        : "";

    let filteredGroups = allGroups;

    // Search by group name
    if (searchText) {

        filteredGroups = filteredGroups.filter(group =>
            group.groupName &&
            group.groupName.toLowerCase().includes(searchText)
        );
    }

    // Filter by subject
    if (selectedSubject) {

        filteredGroups = filteredGroups.filter(group =>
            group.subject &&
            String(group.subject.id) === String(selectedSubject)
        );
    }

    displayGroups(filteredGroups);
}


// ===============================
// DISPLAY GROUPS
// ===============================
function displayGroups(groups) {

    const container = document.getElementById("groupsContainer");

    if (!container) {
        console.error("groupsContainer not found");
        return;
    }

    if (!groups || groups.length === 0) {

        container.innerHTML = `
            <div class="empty-state">
                <div style="font-size:40px;">🔍</div>
                <h3>No study groups found</h3>
                <p>Try another search or create a new study group.</p>
            </div>
        `;

        return;
    }

    container.innerHTML = "";

    groups.forEach(group => {

        const subjectName =
            group.subject?.name || "Subject";

        const creatorName =
            group.creator?.name || "Student";

        const card = document.createElement("div");

        card.className = "group-card";

        card.innerHTML = `
            <div class="group-card-header">

                <div class="group-icon">
                    📚
                </div>

                <div>
                    <h3>${escapeHtml(group.groupName)}</h3>

                    <span class="subject-badge">
                        ${escapeHtml(subjectName)}
                    </span>
                </div>

            </div>

            <p class="group-description">
                ${escapeHtml(
                    group.description || "No description available."
                )}
            </p>

            <div class="group-info">

                <span>
                    👥 Max Members:
                    ${group.maxMembers}
                </span>

                <span>
                    👤 Created by:
                    ${escapeHtml(creatorName)}
                </span>

            </div>

            <button
                class="view-group-btn"
                onclick="openGroup(${group.id})">
                View Group →
            </button>
        `;

        container.appendChild(card);
    });
}


// ===============================
// OPEN GROUP DETAILS
// ===============================
function openGroup(groupId) {

    window.location.href =
        `group-details.html?id=${groupId}`;
}


// ===============================
// HTML SECURITY
// ===============================
function escapeHtml(value) {

    const div = document.createElement("div");

    div.textContent = value;

    return div.innerHTML;
}