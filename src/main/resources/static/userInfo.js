$(async function () {
    await infoUser();
    await userTable();
})

const userFetch = {
    findUserByUsername: async () => await fetch(`/api/user`),
}

async function infoUser() {
    let temp = '';
    const info = document.querySelector('#info');
    await userFetch.findUserByUsername()
        .then(res => res.json())
        .then(user => {
            temp += `
            <strong class="text-white">${user.email}</strong>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link disabled text-white">
                            with roles ${user.roles.map(role => " " + role.name.substring(5))}
                        </a>
                    </li>
                </ul>
             </div>
             <form class="form-inline" action="/logout" method="post">
                <a href="#" class="nav-text nav-link text-white-50 active"
                    onclick="this.closest('form').submit();return false;" role="button">Logout</a>
                <input type="submit" name="target" hidden/>
    </form>
            `
        });
    info.innerHTML = temp;
}

async function userTable() {
    let temp = '';
    const table = document.querySelector("#userTable tbody")
    await userFetch.findUserByUsername()
        .then(res => res.json())
        .then(user => {
            temp += `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(e => " " + e.name.substring(5))}</td>
                </tr>
               `;
        })
    table.innerHTML = temp;
}