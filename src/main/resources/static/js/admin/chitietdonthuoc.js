$('.delete').click(function() {
    const id1 = $(this).data('id1');
    const id2 = $(this).data('id2');

    if (id1 && id2) {
        $('#maDonThuoc').val(id1);
        $('#maChiTietKhamBenh').val(id2);
        $('#deleteModal').modal('show');
    } else {
        console.error('ID không hợp lệ');
    }
});

document.getElementById('selectAll').addEventListener('change', function() {
    const checkboxes = document.querySelectorAll('input[name="selectedIds"]');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = this.checked;
    });
});

$('.delete-all').click(function() {
    const selectedIds = [];
    $('input[name="selectedIds"]:checked').each(function() {
        selectedIds.push($(this).val());
    });
    $('#selectedIds').val(selectedIds.join(','));
    if (selectedIds.length === 0) {
        alert('Vui lòng chọn ít nhất một mục để xóa.');
        return false;
    }
    $('#deleteAllModal').modal('show');
});