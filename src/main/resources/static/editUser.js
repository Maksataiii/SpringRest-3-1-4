async function editUser(modal, id) {
    let oneUser = await userFetch.findOneUser(id);
    let user = oneUser.json();

    modal.find('.modal-title').html('Edit user');

    let editButton = `<button  class="btn btn-info" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group text-center" id="editUser">
               <div class="form-group">
                    <label for="userId" class="col-form-label">ID</label>
                    <input type="text" class="form-control username" id="userId" value="${user.id}" readonly>
               </div>
                   
               <div class="form-group">
                    <label for="username" class="col-form-label">Username</label>
                    <input type="text" class="form-control username" id="username" value="${user.username}">
               </div>
                <div class="form-group">
                    <label for="password" class="com-form-label">Password</label>
                    <input type="password" class="form-control" id="password" value="" placeholder="Enter new or repeat your password">
                </div>
                <div class="form-group">
                    <label for="lastname" class="com-form-label">First Name</label>
                    <input type="text" class="form-control" id="firstName" value="${user.firstName}">
                </div>
                <div class="form-group">
                    <label for="lastName" class="com-form-label">Last Name</label>
                    <input type="text" class="form-control" id="lastName" value="${user.lastName}">
                </div>
                <div class="form-group">
                    <label for="age" class="com-form-label">Age</label>
                    <input type="text" class="form-control" id="age" value="${user.age}">
                </div>
                <div class="form-group">
                    <label for="email" class="com-form-label">Email</label>
                    <input type="text" class="form-control" id="email" value="${user.email}">
                </div>
                
                
                <div class="form-group">
                     <label for="rolesEdit" class="com-form-label">Role:</label>
                        <select multiple id="rolesEdit" class="form-control select" size="2">
                             <option value="ROLE_USER">USER</option>
                             <option value="ROLE_ADMIN">ADMIN</option>
                        </select>
                </div>
                
                
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#editButton").on('click', async () => {
        let checkedRoles = () => {
            let array = []
            let options = document.querySelector('#rolesEdit').options
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    array.push(roleList[i])
                }
            }
            return array;
        }
        let id = modal.find("#userId").val().trim();
        let username = modal.find("#username").val().trim();
        let password = modal.find("#password").val().trim();
        let firstname = modal.find("#firstName").val().trim();
        let lastname = modal.find("#lastName").val().trim();
        let age = modal.find("#age").val().trim();
        let email = modal.find("#email").val().trim();
        let data = {
            id: id,
            username: username,
            password: password,
            firstName: firstname,
            lastName: lastname,
            age: age,
            email: email,
            roles: checkedRoles()

        }
        const response = await userFetch.updateUser(data, id);

        if (response.ok) {
            await getUsers();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}