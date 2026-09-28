const API_BASE = "/api";

const urlParams =
    new URLSearchParams(
        window.location.search
    );

const groupId =
    urlParams.get("id");

const currentStudentId =
    Number(
        localStorage.getItem("studentId") || 1
    );


document.addEventListener(
    "DOMContentLoaded",
    initializeGroupDetails
);


async function initializeGroupDetails() {

    if (!groupId) {

        document.getElementById(
            "groupDetails"
        ).innerHTML = `

            <div class="empty-message">

                <h3>Group not found</h3>

                <p>
                    No group ID was provided.
                </p>

            </div>

        `;

        return;
    }

    await loadGroup();

    await loadMembers();

    setupJoinButton();
}


/* =====================================================
   LOAD GROUP
   ===================================================== */

async function loadGroup() {

    try {

        const response =
            await fetch(
                `${API_BASE}/groups/${groupId}`
            );

        if (!response.ok) {

            throw new Error(
                "Group not found"
            );
        }

        const group =
            await response.json();

        displayGroup(group);

    } catch (error) {

        console.error(error);

        document.getElementById(
            "groupDetails"
        ).innerHTML = `

            <div class="empty-message">

                <h3>Unable to load group</h3>

                <p>
                    ${error.message}
                </p>

            </div>

        `;
    }
}


/* =====================================================
   DISPLAY GROUP
   ===================================================== */

function displayGroup(group) {

    const container =
        document.getElementById(
            "groupDetails"
        );

    container.innerHTML = `

        <div class="group-details-card">

            <div class="group-details-header">

                <div class="group-large-icon">
                    📚
                </div>

                <div>

                    <h2>
                        ${group.groupName}
                    </h2>

                    <p>
                        ${group.description ||
                        "No description available."}
                    </p>

                </div>

            </div>


            <div class="group-info-grid">

                <div class="group-info">

                    <span>Subject</span>

                    <strong>
                        ${group.subject?.name ||
                        "Unknown"}
                    </strong>

                </div>


                <div class="group-info">

                    <span>Creator</span>

                    <strong>
                        ${group.creator?.name ||
                        "Unknown"}
                    </strong>

                </div>


                <div class="group-info">

                    <span>Maximum Members</span>

                    <strong>
                        ${group.maxMembers}
                    </strong>

                </div>

            </div>

        </div>

    `;
}


/* =====================================================
   LOAD MEMBERS
   ===================================================== */

async function loadMembers() {

    try {

        const response =
            await fetch(
                `${API_BASE}/groups/${groupId}/members`
            );

        if (!response.ok) {
            throw new Error(
                "Unable to load members"
            );
        }

        const memberships =
            await response.json();

        displayMembers(memberships);

    } catch (error) {

        console.error(error);

        document.getElementById(
            "membersContainer"
        ).innerHTML = `

            <div class="empty-message">
                Unable to load members.
            </div>

        `;
    }
}


/* =====================================================
   DISPLAY MEMBERS
   ===================================================== */

function displayMembers(memberships) {

    const container =
        document.getElementById(
            "membersContainer"
        );

    container.innerHTML = "";

    if (memberships.length === 0) {

        container.innerHTML = `

            <div class="empty-message">

                <h3>No members yet 👥</h3>

                <p>
                    Be the first person to join!
                </p>

            </div>

        `;

        return;
    }


    memberships.forEach(
        membership => {

            const student =
                membership.student;

            const card =
                document.createElement(
                    "div"
                );

            card.className =
                "member-card";


            card.innerHTML = `

                <div class="member-info">

                    <div class="member-avatar">
                        👤
                    </div>

                    <div>

                        <h4>
                            ${student.name}
                        </h4>

                        <p>
                            ${student.email}
                        </p>

                    </div>

                </div>

            `;


            container.appendChild(card);
        }
    );
}


/* =====================================================
   JOIN BUTTON
   ===================================================== */

function setupJoinButton() {

    const button =
        document.getElementById(
            "joinButton"
        );

    button.addEventListener(
        "click",
        joinGroup
    );
}


/* =====================================================
   JOIN GROUP
   ===================================================== */

async function joinGroup() {

    try {

        const response =
            await fetch(
                `${API_BASE}/groups/${groupId}/join/${currentStudentId}`,
                {
                    method: "POST"
                }
            );

        const data =
            await response.json();

        if (!response.ok) {

            throw new Error(
                data.message ||
                "Unable to join group."
            );
        }


        alert(
            "Successfully joined the group! 🎉"
        );

        await loadMembers();

    } catch (error) {

        console.error(error);

        alert(error.message);
    }
}