async function createUser() {
    $('#newUserButton').on('click', async () =>{
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
        //
        await userFetch.addNewUser(data);
    await getUsers();
    })
}