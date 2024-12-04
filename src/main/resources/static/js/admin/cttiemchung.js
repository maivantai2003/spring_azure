$('.delete').click(function() {
    const id1 = $(this).data('id1');
    const id2 = $(this).data('id2');

    if (id1 && id2) {
        $('#maTiemChung').val(id1);
        $('#maNguoiDung').val(id2);
        $('#deleteModal').modal('show');
    } else {
        console.error('ID không hợp lệ');
    }
});