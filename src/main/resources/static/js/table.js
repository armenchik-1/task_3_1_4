$(document).ready(getAllUsers());

// Для отображения списка пользователей в таблице
function getAllUsers() {
    $("#table").empty();
    $.ajax({
        type: 'GET',
        url: '/api/admin/users',
        timeout: 3000,
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                $("#table").append($('<tr>').append(
                    $('<td>').append($('<span>')).text(user.id),
                    $('<td>').append($('<span>')).text(user.username),
                    $('<td>').append($('<span>')).text(user.lastName),
                    $('<td>').append($('<span>')).text(user.age),
                    $('<td>').append($('<span>')).text(user.email),
                    $('<td>').append($('<span>')).text(user.roles),
                    $('<td>').append($('<button>').text("Edit").attr({
                        "type": "button",
                        "class": "btn btn-primary edit",
                        "data-toggle": "modal",
                        "data-target": "#myModal",

                    })
                        .data("user", user)),
                    $('<td>').append($('<button>').text("Delete").attr({
                        "type": "button",
                        "class": "btn btn-danger delete",
                        "data-toggle": "modal",
                        "data-target": "#myModalDelete",
                    })
                        .data("user", user))
                    )
                );
            });
        }
    });
}

//Для редактирования пользователя через модальное окно
$(document).on("click", ".edit", function () {
    let user = $(this).data('user');

    $('#firstNameInput').val(user.username);
    $('#lastNameInput').val(user.lastName);
    $('#emailInput').val(user.email);
    $('#idInput').val(user.id);
    $('#ageInput').val(user.age);
    $('#roleInput').val(user.roles);

});

$(document).on("click", ".editUser", function () {
    let formData = $(".formEditUser").serializeArray();
    $.ajax({
        type: 'PUT',
        url: '/api/admin/update',
        data: formData,
        timeout: 100,
        success: function () {
            getAllUsers();
        }
    });
});

//Для удаления пользователя через модальное окно
$(document).on("click", ".delete", function () {
    let user = $(this).data('user');

    $('#firstName').val(user.username);
    $('#lastName').val(user.lastName);
    $('#email').val(user.email);
    $('#id').val(user.id);
    $('#age').val(user.age);

    $(document).on("click", ".deleteUser", function () {
        $.ajax({
            type: 'DELETE',
            url: '/api/admin/remove',
            data: {id: $('#id').val()},
            timeout: 100,
            success: function () {
                getAllUsers();
            }
        });
    });
})

// Для добавления пользователя
$('.addUser').click(function () {
    $('#usersTable').trigger('click');
    let formData = $(".formAddUser").serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/admin/addUser',
        data: formData,
        timeout: 100,
        success: function () {

            $('.formAddUser')[0].reset();
            getAllUsers()
        }
    })
});

//Для отображения информаций о пользователе на его странице и сокрытия меню в зависемости от роли
$(document).ready(getUser());
function getUser() {
    $("#userTable").empty();
    $.ajax({
        type: 'GET',
        url: '/api/user/getUser',
        timeout: 3000,
        error: function() {
            $('#blockMenuforUser').hide();
        },
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                if(user.roles == "USER") {
                    $('#menuUser').trigger('click');
                    $('#main2').trigger('click');
                    $('#blockMenuforAdmin').hide();
                }
                $("#userTable").append($('<tr>').append(
                    $('<td>').append($('<span>')).text(user.id),
                    $('<td>').append($('<span>')).text(user.username),
                    $('<td>').append($('<span>')).text(user.lastName),
                    $('<td>').append($('<span>')).text(user.age),
                    $('<td>').append($('<span>')).text(user.email),
                    $('<td>').append($('<span>')).text(user.roles),
                    )
                );
            });
        }
    });
}


