let roleList = [
    {id: 2, role: "ROLE_USER"},
    {id: 1, role: "ROLE_ADMIN"}
]

$(async function () {
    // await infoUser();
    await getUsers();
    await getNewUserForm();
    await getDefaultModal();
    await createUser();
})
//навбар
// async function infoUser() {
//     let temp = '';
//     const info = document.querySelector('#info');
//     await userFetch.findUserByUsername()
//         .then(res => res.json())
//         .then(user => {
//             temp += `
//             <strong class="text-white">${user.email}</strong>
//             <div class="collapse navbar-collapse" id="navbarText">
//                 <ul class="navbar-nav mr-auto">
//                     <li class="nav-item">
//                         <a class="nav-link disabled text-white">
//                             with roles ${user.roles.map(role => " " + role.name.substring(5))}
//                         </a>
//                     </li>
//                 </ul>
//              </div>
//              <form class="form-inline" action="/logout" method="post">
//                 <a href="#" class="nav-text nav-link text-white-50 active"
//                     onclick="this.closest('form').submit();return false;" role="button">Logout</a>
//                 <input type="submit" name="target" hidden/>
//              </form>`
//         });
//     info.innerHTML = temp;
// }

const userFetch = {
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    findAllUsers: async () => await fetch('api/admin'),
    findUserByUsername: async () => await fetch('api/user'),
    findOneUser: async (id) => await fetch(`api/admin/${id}`),
    addNewUser: async (user) => await fetch('api/admin', {
        method: 'POST',
        headers: userFetch.headers,
        body: JSON.stringify(user)
    }),
    updateUser: async (user) => await fetch('api/admin/', {
        method: 'PUT',
        headers: userFetch.headers,
        body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch(`api/admin/${id}`, {
        method: 'DELETE',
        headers: userFetch.headers})
}

async function getUsers() {
    const table = document.querySelector('#tb tbody');
    await userFetch.findAllUsers()
        .then(res => res.json())
        .then(users => {
            let temp = "";
            users.forEach(user => {
                temp += `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.authorities.map(e => " " + e.authority.substring(5))}</td>
                    <td>
                        <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info"
                            className data-toggle="modal" data-target="#editModal">Edit</button>
                    </td>
                    <td>
                        <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger"
                            className data-toggle="modal" data-target="#deleteModal">Delete</button>
                    </td>
                </tr>
               `;
            })
            table.innerHTML = temp;
        })

    $("#tb").find('button').on('click', (event) => {
        let defaultModal = $('#defaultModal');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}

async function getNewUserForm() {
    let button = $('#newUserButton');
    let form = $('#formNewUser')
    button.on('click', () => {
        form.show()
    })
}

async function getDefaultModal() {
    $('#defaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}