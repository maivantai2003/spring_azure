// Xử lý nút xóa một item
$('.delete').click(function() {
    const id = $(this).data('id');
    if (id) {
        $('#maXetNghiem').val(id);
        $('#deleteModal').modal('show');
    } else {
        console.error('ID không hợp lệ');
    }
});

// Xử lý checkbox "Chọn tất cả" sử dụng vanilla JavaScript
document.getElementById('selectAll').addEventListener('change', function() {
    const checkboxes = document.querySelectorAll('input[name="selectedIds"]');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = this.checked;
    });
});

// Xử lý nút xóa nhiều items
$('.delete-all').click(function() {
    const selectedIds = [];
    $('input[name="selectedIds"]:checked').each(function() {
        selectedIds.push($(this).val());
    });

    if (selectedIds.length === 0) {
        alert('Vui lòng chọn ít nhất một mục để xóa.');
        return false;
    }

    $('#selectedIds').val(selectedIds.join(','));
    $('#deleteAllModal').modal('show');
});

// Xử lý tìm kiếm
$("#searchButton").click(function() {
    performSearch();
});

$("#searchInput").keypress(function(event) {
    if (event.which === 13) {
        event.preventDefault();
        performSearch();
    }
});

function performSearch() {
    var query = $("#searchInput").val();
    var maChiTietKhamBenh = $("#maKhamBenh").val();
    window.location.href = "/admin/khambenh/chitiet/xetnghiem?maChiTietKhamBenh=" + maChiTietKhamBenh + "&query=" + query;
}