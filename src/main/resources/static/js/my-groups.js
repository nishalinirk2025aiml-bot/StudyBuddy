const API_BASE = "/api";

const currentStudentId =
    Number(
        localStorage.getItem("studentId") || 1
    );


document.addEventListener(
    "DOMContentLoaded",
    loadMyGroups
);


async function loadMyGroups() {

    const container =
        document.getElementById(
            "myGroupsContainer"
        );

    try {

        /*
         * Currently there is no dedicated
         * GET /api/students/{id}/groups endpoint.
         *
         * So we load all groups and check their
         * memberships individually.
         */

        const groupsResponse =
            await fetch(
                `${API_BASE}/groups`
            );

        if (!groupsResponse.ok) {
            throw new Error(
                "Unable to load groups"
            );
        }

        const groups =
            await groupsResponse.json();

        const myGroups = [];


        for (const group of groups) {

            try {

                const response =
                    await fetch(
                        `${API_BASE}/groups/${group.id}/members`
                    );

                if (!response.ok) {
                    continue;
                }

                const memberships =
                    await response.json();

                const isMember =
                    memberships.some(
                        membership =>
                            membership.student?.id ===
                            currentStudentId
                    );

                if (isMember) {
                    myGroups.push(group);
                }

            } catch (error) {

                console.error(
                    "Membership check failed:",
                    error
                );
            }
        }


        displayMyGroups(myGroups);


    } catch (error) {

        console.error(error);

        container.innerHTML = `

            <div class="empty-message">

                <h3>Unable to load your groups</h3>

                <p>
                    Please make sure the backend is running.
                </p>

            </div>

        `;
    }
}


/* =====================================================
   DISPLAY
   ===================================================== */

function displayMyGroups(groups) {

    const container =
        document.getElementById(
            "myGroupsContainer"
        );

    container.innerHTML = "";


    if (groups.length === 0) {

        container.innerHTML = `

            <div class="empty-message">

                <h3>You haven't joined any groups yet 📚</h3>

                <p>
                    Find a study group and start learning!
                </p>

                <br>

                <a
                    href="groups.html"
                    class="btn-primary">

                    Find Groups

                </a>

            </div>

        `;

        return;
    }


    groups.forEach(group => {

        const card =
            document.createElement("div");

        card.className =
            "my-group-card";


        card.innerHTML = `

            <div class="group-card-icon">
                📚
            </div>

            <h3>
                ${group.groupName}
            </h3>

            <p>
                ${group.description ||
                "Study together!"}
            </p>

            <p>
                📚
                ${group.subject?.name ||
                "Subject"}
            </p>

            <p>
                👤
                ${group.creator?.name ||
                "Creator"}
            </p>

            <button
                class="join-button"
                onclick="openGroup(${group.id})">

                View Group

            </button>

            <br><br>

            <button
                class="leave-button"
                onclick="leaveGroup(${group.id})">

                Leave Group

            </button>

        `;


        container.appendChild(card);
    });
}


/* =====================================================
   OPEN GROUP
   ===================================================== */

function openGroup(groupId) {

    window.location.href =
        `group-details.html?id=${groupId}`;
}


/* =====================================================
   LEAVE GROUP
   ===================================================== */

async function leaveGroup(groupId) {

    const confirmLeave =
        confirm(
            "Are you sure you want to leave this group?"
        );

    if (!confirmLeave) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API_BASE}/groups/${groupId}/leave/${currentStudentId}`,
                {
                    method: "DELETE"
                }
            );


        const data =
            await response.json();


        if (!response.ok) {

            throw new Error(
                data.message ||
                "Unable to leave group."
            );
        }


        alert(
            "You have left the group."
        );


        loadMyGroups();


    } catch (error) {

        console.error(error);

        alert(error.message);
    }
}