async function createUser() {
    $('#newUserButton').click(async () =>  {
    const addUserForm = document.getElementById('formNewUser')
    const username = document.getElementById('newUserUsername')
    const firstName = document.getElementById('newUserFirstName')
    const lastName = document.getElementById('newUserLastName')
    const age = document.getElementById('newUserAge')
    const email = document.getElementById('newUserEmail')
    const password = document.getElementById('newUserPassword')
        let checkedRoles = () => {
            let array = []
            let options = document.getElementById('newUserRoles').options
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    array.push(roleList[i])
                }
            }
            return array;
        }
        let data = {
            username: username.value,
            password: password.value,
            firstName: firstName.value,
            lastName: lastName.value,
            age: age.value,
            email: email.value,
            roles: checkedRoles()
        }
        //вставляем в ранне объявленную константу полученные из формы значения

        await userFetch.addNewUser(data);

    });
}

//
// const addUserForm = document.getElementById('formNewUser')
// const username = document.getElementById('newUserUsername')
// const firstName = document.getElementById('newUserFirstName')
// const lastName = document.getElementById('newUserLastName')
// const age = document.getElementById('newUserAge')
// const email = document.getElementById('newUserEmail')
// const password = document.getElementById('newUserPassword')
// const role = document.getElementById('newUserRoles');
// let nsubmit = document.getElementById('newUserButton');
//
// nsubmit.addEventListener('click', e => {
//     let url = '/api/admin';
//     let requestBody = JSON.stringify({
//         id: null,
//         username: username.value,
//         password: password.value,
//         age: age.value,
//         firstName: firstName.value,
//         lastName: lastName.value,
//         email: email.value,
//         roles: selectedRoles(role.childNodes)
//     });
//
//     fetch(url, {
//         method: "POST",
//         headers: { 'Content-Type': 'application/json;charset=utf-8' },
//         body: requestBody
//     })
//         .then(response => {
//             getUsers();
//             addUserForm.reset();
//             document.querySelector('#nav-list-tab').click();
//         });
// });
//
// /***
//  * Получить список выбранных ролей
//  * @param options
//  * @return roles список
//  */
// function selectedRoles(options) {
//     let roles = [];
//     options.forEach(o => {
//         if (o.selected) roles.push(allRoles[o.value - 1]);
//     });
//     return roles;
// }