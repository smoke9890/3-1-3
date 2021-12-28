document.addEventListener("DOMContentLoaded", function(event) {
    //HTTP Request Handler
    let api = {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        },

        getUsers: async () => await fetch("/admin/api/users",{method:"GET"}).then(res => res.json()),
        getUser: async id => await fetch(`/admin/api/users/${id}`, {method:"GET"}).then(res => res.json()),
        addUser: async obj => await fetch("/admin/api/users", {method:"POST", headers: api.headers, body: JSON.stringify(obj)}),
        editUser: async obj => await fetch(`/admin/api/users`, {method:"PUT", headers: api.headers, body: JSON.stringify(obj)}),
        deleteUser: async id => await fetch(`/admin/api/users/${id}`, {method:"DELETE"}),
        showUsers: items => {
            let usersTable = document.querySelector("#usersTable")
            items.forEach(item => {
                usersTable.innerHTML += `<tr id="${item.id}">
                <td class="tdId">${item.id}</td>
                <td class="tdName">${item.name}</td>
                <td class="tdLastName">${item.lastName}</td>
                <td class="tdAge">${item.age}</td>
                <td class="tdEmail">${item.email}</td>
                <td class="tdRole">${item.role.toUpperCase()}</td>
                <td>
                <button class="btn btn-info editUser">Edit</button>
                </td>
                <td>
                <button class="btn btn-danger deleteUser">Delete</button>
                </td></tr>`
            })
        }
    };

    api.getUsers().then(res => api.showUsers(res))


    $(document).on('click', 'button.editUser',function (event){ // modal edit
        event.preventDefault();
        let uid = $(this).parents("tr").attr("id");
        $('.modal-title').text("Edit User")
        $('button.updateUser').css('display','inline-block')
        $('button.confirmDelete').css('display','none')
        api.getUser(uid).then(user => {
            $('#modalEditForm #editId').val(uid);
            $('#modalEditForm #name').val(user.name);
            $('#modalEditForm #lastName').val(user.lastName);
            $('#modalEditForm #age').val(user.age);
            $('#modalEditForm #email').val(user.email);
            $('#modalEditForm #password').val(user.password);
            $('.userEditForm').modal();
        })
    })

    $(document).on('click', 'button.deleteUser',function (event){
        event.preventDefault();
        let uid = $(this).parents("tr").attr("id");
        $('.modal-title').text("Delete User")
        $('button.updateUser').css('display','none')
        $('button.confirmDelete').css('display','inline-block')
        api.getUser(uid).then(user => {
            $('#modalEditForm #editId').val(uid);
            $('#modalEditForm #name').val(user.name).attr('readonly', true);
            $('#modalEditForm #lastName').val(user.lastName).attr('readonly', true);
            $('#modalEditForm #age').val(user.age).attr('readonly', true);
            $('#modalEditForm #email').val(user.email).attr('readonly', true);
            $('#modalEditForm #password').val(user.password).attr('readonly', true);
            $('#modalEditForm #editRole').attr('disabled', true);
            $('.userEditForm').modal();
        })
    })

    $('.btn.updateUser').click(async function () { //редактирование пользователя
        let form = document.querySelector("#modalEditForm");
        let formData = new FormData(form);
        let obj = Object.fromEntries(formData);
        obj.roleSetTemp = $("#editRole").val(); console.log(obj)
        if (obj.roleSetTemp.length === 0) {
            alert("Задайте пользователю новые роли!")
        } else {
            api.editUser(obj).then(res => res.json()).then(res => {
                let tr = $('tr#' + obj.id)
                tr.find('.tdName').text(res.name)
                tr.find('.tdLastName').text(res.lastName)
                tr.find('.tdAge').text(res.age)
                tr.find('.tdEmail').text(res.email)
                tr.find('.tdRole').text(res.role.toUpperCase())
            })
            $('.btn.editModalClose').click()
        }
    })

    $('.btn.confirmDelete').click(async function () { //удаление пользователя
        let form = document.querySelector("#modalEditForm");
        let formData = new FormData(form);
        let obj = Object.fromEntries(formData);
        api.deleteUser(obj.id).then(res => {
            let tr = $('tr#' + obj.id)
            tr.remove();
        })
        $('.btn.editModalClose').click()
    })




    document.querySelector("button.showUserForm").addEventListener("click", function (){ //смена вкладки на таблицу пользователей
        document.querySelector(".topButtons.onFocus").classList.remove("onFocus");
        this.classList.add("onFocus");
        document.querySelector(".cardUserList").style.display = "none";
        document.querySelector(".cardAddUserForm").style.display = "flex";
    })


    document.querySelector("button.showUsers").addEventListener("click", function (){ //смена вкладки на форму нового пользователя
        document.querySelector(".topButtons.onFocus").classList.remove("onFocus");
        this.classList.add("onFocus");
        document.querySelector(".cardUserList").style.display = "flex";
        document.querySelector(".cardAddUserForm").style.display = "none";
    })

    $('.btn.addUser').click(async function () { //добавление
        let form = document.querySelector("#addUserForm");
        let formData = new FormData(form);
        let obj = Object.fromEntries(formData);
        obj.roleSetTemp = $(".roleSelect").val();
        if (obj.roleSetTemp.length === 0){
            alert("Дайте вашему новому пользователю роли!")
        } else {
            api.addUser(obj).then(res => res.json()).then(res => {
                api.showUsers([res])
            })
        }
    })






})

