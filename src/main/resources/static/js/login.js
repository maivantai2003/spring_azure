window.onload = function () {
    var input = document.getElementById('usernameForm');
    input.addEventListener('input', function (e) {
        // Loại bỏ tất cả các ký tự không phải là số từ giá trị nhập
        this.value = this.value.replace(/\D/g, '');
    });
};

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector('form');
    const password = document.getElementById('passwordForm');
    const rePassword = document.getElementById('rePasswordForm');

    form.addEventListener('submit', function (event) {
        if (password.value !== rePassword.value) {
            event.preventDefault();
            alert("Mật khẩu và mật khẩu nhập lại không khớp. Vui lòng kiểm tra lại.");
            rePassword.focus();
        }
    });
});