$('.delete').click(function() {
    const id1 = $(this).data('id1');
    const id2 = $(this).data('id2');
    const id3 = $(this).data('id3');
    console.log("ThoiGianDo: " + id3);

    if (id1 && id2 && id3) {
        $('#maTongQuan').val(id1);
        $('#maChiSo').val(id2);
        $('#thoiGianDo').val(id3);
        $('#deleteModal').modal('show');
    } else {
        console.error('ID không hợp lệ');
    }
});


const ctx = document.getElementById('bloodSugarChart').getContext('2d');
let labels = chartData.map(item => new Date(item.thoiGianDo).toLocaleDateString("vi-VN"));
let dataValues = chartData.map(item => item.ketQuaDo);

let maxValue = Math.max(...dataValues) + 25;

let maxY = Math.ceil(maxValue / 25) * 25;
let stepSizeY = maxY / 5;

new Chart(ctx, {
    type: 'line',
    data: {
        labels: labels,
        datasets: [{
            label: tenChiSo,
            data: dataValues,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true,
                max: maxY,
                ticks: {
                    stepSize: stepSizeY
                }
            }
        },
        plugins: {
            legend: {
                display: false
            },
            tooltip: {
                callbacks: {
                    label: function(context) {
                        return `${tenChiSo}: ${context.parsed.y}`;
                    }
                }
            }
        }
    }
});