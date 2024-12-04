flatpickr(".date-picker", {
    locale: "vn",
    dateFormat: "d/m/Y",
    maxDate: "today"
});

// Biến lưu trữ các biểu đồ
let visitsChart, testsChart, vaccinationChart, symptomsChart;

// Hàm hiển thị loading
function showLoading() {
    document.querySelector('.loading').classList.add('active');
}

// Hàm ẩn loading
function hideLoading() {
    document.querySelector('.loading').classList.remove('active');
}

// Hàm áp dụng bộ lọc
function applyFilters() {
    const dateFrom = document.getElementById('dateFrom').value;
    const dateTo = document.getElementById('dateTo').value;
    const hospital = document.getElementById('hospitalFilter').value;
    const totalVisits = document.getElementById('totalVisits');
    const totalTests = document.getElementById('totalTests');
    const totalVaccinations = document.getElementById('totalVaccinations');
    const totalPatients = document.getElementById('totalPatients');

    // Validate dates
    if (dateFrom && dateTo) {
        const fromDate = new Date(dateFrom.split('/').reverse().join('-'));
        const toDate = new Date(dateTo.split('/').reverse().join('-'));

        if (fromDate > toDate) {
            alert('Ngày bắt đầu không thể lớn hơn ngày kết thúc!');
            return;
        }
    }

    showLoading();
    setTimeout(() => {
        updateChartsData(dateFrom, dateTo, hospital);
        totalVisits.value = 1;
        totalTests.value = 1;
        totalVaccinations.value = 1;
        totalPatients.value = 1;
        hideLoading();
    }, 1000);
}

// Hàm đặt lại bộ lọc
function resetFilters() {
    document.getElementById('dateFrom').value = '';
    document.getElementById('dateTo').value = '';
    document.getElementById('hospitalFilter').value = '';

    showLoading();
    setTimeout(() => {
        updateChartsData();
        hideLoading();
    }, 1000);
}

// Hàm cập nhật dữ liệu cho biểu đồ
function updateChartsData(dateFrom = null, dateTo = null, hospital = null) {
    // Cập nhật số liệu tổng quan
    document.getElementById('totalVisits').innerText = Math.floor(Math.random() * 1000);
    document.getElementById('totalTests').innerText = Math.floor(Math.random() * 100);
    document.getElementById('totalVaccinations').innerText = Math.floor(Math.random() * 200);
    document.getElementById('totalPatients').innerText = Math.floor(Math.random() * 50);

    // Cập nhật dữ liệu cho các biểu đồ
    updateVisitsChart();
    updateTestsChart();
    updateVaccinationChart();
    updateSymptomsChart();
}

function initVisitsChart() {
    const labels = ['T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'CN'];
    const ctx = document.getElementById('visitsChart').getContext('2d');
    visitsChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Lượt khám',
                data: visitsData,
                borderColor: '#4e73df',
                backgroundColor: 'rgba(78, 115, 223, 0.1)',
                tension: 0.4,
                fill: true,
                borderWidth: 3,
                pointRadius: 4,
                pointBackgroundColor: '#fff',
                pointBorderColor: '#4e73df',
                pointBorderWidth: 2
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Biểu đồ lượt khám theo thời gian',
                    font: {
                        size: 16,
                        weight: 'bold'
                    },
                    padding: 20
                },
                legend: {
                    position: 'bottom'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        drawBorder: false,
                        color: 'rgba(0, 0, 0, 0.05)'
                    }
                },
                x: {
                    grid: {
                        display: false
                    }
                }
            }
        }
    });
}

function initTestsChart() {
    let labels = Object.keys(soLuongXetNghiemTheoTen);
    let data = Object.values(soLuongXetNghiemTheoTen);
    const ctx = document.getElementById('testsChart').getContext('2d');
    testsChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Số lượng xét nghiệm',
                data: data,
                backgroundColor: [
                    'rgba(54, 185, 204, 0.8)',
                    'rgba(54, 185, 204, 0.6)',
                    'rgba(54, 185, 204, 0.4)',
                    'rgba(54, 185, 204, 0.3)',
                    'rgba(54, 185, 204, 0.2)'
                ],
                borderRadius: 8,
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Thống kê xét nghiệm theo loại',
                    font: {
                        size: 16,
                        weight: 'bold'
                    },
                    padding: 20
                },
                legend: {
                    display: false
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        drawBorder: false,
                        color: 'rgba(0, 0, 0, 0.05)'
                    }
                },
                x: {
                    grid: {
                        display: false
                    }
                }
            }
        }
    });
}

function initVaccinationChart() {

    const ctx = document.getElementById('vaccinationChart').getContext('2d');
    vaccinationChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Đã tiêm', 'Chưa tiêm'],
            datasets: [{
                data: [daTiem, chuaTiem],
                backgroundColor: [
                    'rgba(28, 200, 138, 0.8)',
                    'rgba(246, 194, 62, 0.8)'
                ],
                borderWidth: 0,
                borderRadius: 5
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Thống kê tình trạng tiêm chủng',
                    font: {
                        size: 16,
                        weight: 'bold'
                    },
                    padding: 20
                },
                legend: {
                    position: 'bottom'
                }
            },
            cutout: '60%'
        }
    });
}

function initSymptomsChart() {
    let labels = Object.keys(soLuongBenhTheoTen);
    let data = Object.values(soLuongBenhTheoTen);
    const ctx = document.getElementById('symptomsChart').getContext('2d');
    symptomsChart = new Chart(ctx, {
        type: 'radar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Số lượng bệnh',
                data: data,
                fill: true,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                pointBackgroundColor: '#fff',
                pointBorderColor: '#4e73df',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: '#4e73df',
                pointBorderWidth: 2,
                pointRadius: 4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Biểu đồ số lượng bệnh',
                    font: {
                        size: 16,
                        weight: 'bold'
                    },
                    padding: 20
                },
                legend: {
                    position: 'bottom'
                }
            },
            scales: {
                r: {
                    angleLines: {
                        color: 'rgba(0, 0, 0, 0.1)'
                    },
                    grid: {
                        color: 'rgba(0, 0, 0, 0.05)'
                    },
                    ticks: {
                        backdropColor: 'transparent'
                    }
                }
            }
        }
    });
}

// Các hàm cập nhật dữ liệu cho biểu đồ
function updateVisitsChart() {
    visitsChart.data.datasets[0].data = Array.from({length: 7}, () => Math.floor(Math.random() * 100));
    visitsChart.update();
}

function updateTestsChart() {
    testsChart.data.datasets[0].data = Array.from({length: 5}, () => Math.floor(Math.random() * 20));
    testsChart.update();
}

function updateVaccinationChart() {
    vaccinationChart.data.datasets[0].data = [
        Math.floor(Math.random() * 300),
        Math.floor(Math.random() * 100)
    ];
    vaccinationChart.update();
}

function updateSymptomsChart() {
    symptomsChart.data.datasets[0].data = Array.from({length: 6}, () => Math.floor(Math.random() * 100));
    symptomsChart.update();
}

// Khởi tạo tất cả biểu đồ khi trang tải xong
document.addEventListener('DOMContentLoaded', function() {
    initVisitsChart();
    initTestsChart();
    initVaccinationChart();
    initSymptomsChart();
});