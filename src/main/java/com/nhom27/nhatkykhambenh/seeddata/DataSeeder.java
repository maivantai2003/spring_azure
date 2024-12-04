package com.nhom27.nhatkykhambenh.seeddata;

import com.nhom27.nhatkykhambenh.enums.MoiQuanHe;
import com.nhom27.nhatkykhambenh.model.*;
import com.nhom27.nhatkykhambenh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class
DataSeeder implements CommandLineRunner {

    @Autowired
    private IGiaDinhRepo giaDinhRepo;

    @Autowired
    private INguoiDungRepo nguoiDungRepo;

    @Autowired
    private ITaiKhoanRepo taiKhoanRepo;

    @Autowired
    private ITongQuanRepo tongQuanRepo;

    @Autowired
    private IChiSoRepo chiSoRepo;

    @Autowired
    private IChiTietChiSoRepo chiTietChiSoRepo;

    @Autowired
    private ITiemChungRepo tiemChungRepo;

    @Autowired
    private IChiTietTiemChungRepo chiTietTiemChungRepo;

    @Autowired
    private IKhamBenhRepo khamBenhRepo;

    @Autowired
    private IChiTietKhamBenhRepo chiTietKhamBenhRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IXetNghiemRepo xetNghiemRepo;

    @Autowired
    private IDonThuocRepo donThuocRepo;

    @Autowired
    private IChiTietDonThuocRepo chiTietDonThuocRepo;

    @Autowired
    private IRoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        // Seed data cho GiaDinh
        if(giaDinhRepo.count() == 0) {
            List<GiaDinh> giaDinhList = List.of(
                new GiaDinh(null, 3, true, null, new HashSet<>())
            );

            giaDinhRepo.saveAll(giaDinhList);
            System.out.println("Save GiaDinh to Database size = " + giaDinhRepo.count());
        }

        // Seed data cho NguoiDung
        if (nguoiDungRepo.count() == 0) {
            List<NguoiDung> nguoiDungList = List.of(
                new NguoiDung(null, null, "admin", "ADMIN-CCCD", LocalDate.of(2003, 1, 1), "Nam", "123 Address ADMIN", "Đại diện phía Bệnh viện", "benhvien@gmail.com", MoiQuanHe.TOI, true, null, null, null, new HashSet<>(), null, null),
                new NguoiDung(null, null, "0912345678", "123456789", LocalDate.of(2003, 6, 15), "Nam", "123 Address St", "Nguyen Van A", "nguyenvana@gmail.com", MoiQuanHe.TOI, true, giaDinhRepo.findAll().get(0), null, null, new HashSet<>(), null, null),
                new NguoiDung(null, null, "0912345679", "123456780", LocalDate.of(1972, 4, 29), "Nữ", "456 Address St", "Le Thi B", "lethib@gmail.com", MoiQuanHe.ME, true, giaDinhRepo.findAll().get(0), null, null, new HashSet<>(), null, null),
                new NguoiDung(null, null, "0912345680", "123456781", LocalDate.of(1963, 9, 12), "Nam", "789 Address St", "Tran Van C", "tranvanc@gmail.com", MoiQuanHe.CHA, true, giaDinhRepo.findAll().get(0), null, null, new HashSet<>(), null, null)
            );

            nguoiDungRepo.saveAll(nguoiDungList);
            System.out.println("Save NguoiDung to Database size = " + nguoiDungRepo.count());
        }

        // Seed Role
        if(roleRepo.count() == 0) {
            List<Role> roleList = List.of(
                new Role(null, "USER", new ArrayList<>()),
                new Role(null, "ADMIN", new ArrayList<>())
            );

            roleRepo.saveAll(roleList);
            System.out.println("Save Role to Database size = " + roleRepo.count());
        }

        // Seed data cho TaiKhoan
        if (taiKhoanRepo.count() == 0) {
            Role adminRole = roleRepo.findByName("ADMIN");
            Role userRole = roleRepo.findByName("USER");
            NguoiDung nguoiDungFirst = nguoiDungRepo.findAll().stream().findFirst().orElse(null);
            NguoiDung nguoiDungSecond = nguoiDungRepo.findById(2).orElse(null);
            GiaDinh giaDinhFirst = giaDinhRepo.findAll().stream().findFirst().orElse(null);

            List<TaiKhoan> taiKhoanList = List.of(
                new TaiKhoan(nguoiDungFirst.getMaNguoiDung(), passwordEncoder.encode("234"), "234", true, null, nguoiDungFirst, new ArrayList<>(List.of(adminRole))),
                new TaiKhoan(nguoiDungSecond.getMaNguoiDung(), passwordEncoder.encode("123"), "123", true, giaDinhFirst, nguoiDungSecond, new ArrayList<>(List.of(userRole)))
            );

            taiKhoanRepo.saveAll(taiKhoanList);
            System.out.println("Save TaiKhoan to Database size = " + taiKhoanRepo.count());
        }

        // Seed data cho TongQuan
        if (tongQuanRepo.count() == 0) {
            List<NguoiDung> nguoiDungList = nguoiDungRepo.findAll();

            for(NguoiDung nguoiDung : nguoiDungList) {
                if(nguoiDung.getMaNguoiDung() == 2) {
                    TongQuan tongQuan = new TongQuan();
                    tongQuan.setDuongHuyet("120/80");
                    tongQuan.setNhipTim("72");
                    tongQuan.setHuyetAp("120/80");
                    tongQuan.setNhietDo("37.5");
                    tongQuan.setChieuCao("170");
                    tongQuan.setCanNang("70");
                    tongQuan.setChiSoBMI("24.2");
                    tongQuan.setNhomMau("O+");
                    tongQuan.setNguoiDung(nguoiDung);

                    tongQuanRepo.save(tongQuan);

                }
                else {
                    TongQuan tongQuan = new TongQuan();
                    tongQuan.setNguoiDung(nguoiDung);

                    tongQuanRepo.save(tongQuan);
                }
            }

            System.out.println("Seed TongQuan with size: " + tongQuanRepo.count());
        }

        // Seed data cho ChiSo
        if(chiSoRepo.count() == 0) {
            List<ChiSo> chiSoList = List.of(
                new ChiSo(null, "canNang", "Cân nặng", "Kg", true),
                new ChiSo(null, "chiSoBMI", "Chỉ số BMI", "", true),
                new ChiSo(null, "chieuCao", "Chiều cao", "Cm", true),
                new ChiSo(null, "duongHuyet", "Đường huyết", "mg/dL", true),
                new ChiSo(null, "huyetAp", "Huyết áp", "mmHg", true),
                new ChiSo(null, "nhietDo", "Nhiệt độ cơ thể", "°C", true),
                new ChiSo(null, "nhipTim", "Nhịp tim", "bpm", true),
                new ChiSo(null, "nhomMau", "Nhóm máu", "", true)
            );

            chiSoRepo.saveAll(chiSoList);
            System.out.println("Save ChiSo to Database size = " + chiSoRepo.count());
        }

        // Seed data cho ChiTietChiSo
        if(chiTietChiSoRepo.count() == 0) {
            TongQuan tongQuan = tongQuanRepo.findById(2).orElse(null);
            ChiSo chiSo = chiSoRepo.findById(4).orElse(null);
            int giaTriDo = 60;

            if (tongQuan != null && chiSo != null) {
                List<ChiTietChiSo> chiTietChiSoList = new ArrayList<>();

                LocalDate today = LocalDate.now();

                for (int i = 0; i < 10; i++) {
                    LocalDate thoiGianDoLocalDate = today.minusDays(i);
                    Date thoiGianDo = java.sql.Date.valueOf(thoiGianDoLocalDate);

                    ChiTietChiSo chiTietChiSo = new ChiTietChiSo(
                            chiSo.getMaChiSo(),
                            tongQuan.getMaTongQuan(),
                            thoiGianDo,
                            String.valueOf(giaTriDo + 10*i),
                            null,
                            null
                    );

                    chiTietChiSoList.add(chiTietChiSo);
                }

                chiTietChiSoRepo.saveAll(chiTietChiSoList);
                System.out.println("Saved " + chiTietChiSoList.size() + " ChiTietChiSo records to the database.");
            }
        }

        // Seed TiemChung
        if(tiemChungRepo.count() == 0) {
            List<TiemChung> tiemChungList = List.of(
                new TiemChung(null, "Vaccine COVID-19", "Bệnh viện Trung Ương", 2, true),
                new TiemChung(null, "Vaccine Cúm", "Trung tâm Y tế Quận 1", 1, true),
                new TiemChung(null, "Vaccine Sởi", "Trung tâm Y tế Quận 7", 1, false),
                new TiemChung(null, "Vaccine Thủy Đậu", "Bệnh viện Nhi Đồng 2", 1, true),
                new TiemChung(null, "Vaccine Viêm Gan B", "Trung tâm Y tế Quận 4", 3, true),
                new TiemChung(null, "Vaccine HPV", "Bệnh viện Phụ Sản", 2, true),
                new TiemChung(null, "Vaccine Bạch Hầu", "Trung tâm Y tế Quận 3", 1, false),
                new TiemChung(null, "Vaccine Viêm Phổi", "Bệnh viện Đa Khoa", 2, true),
                new TiemChung(null, "Vaccine Rotavirus", "Trung tâm Y tế Quận 10", 2, true),
                new TiemChung(null, "Vaccine Uốn Ván", "Bệnh viện Đa Khoa", 1, true),
                new TiemChung(null, "Vaccine COVID-19 Pfizer", "Trung tâm Y tế Quận 9", 2, true),
                new TiemChung(null, "Vaccine Bại Liệt", "Trung tâm Y tế Quận 8", 1, true),
                new TiemChung(null, "Vaccine Sởi Quai Bị", "Bệnh viện Nhi Đồng 1", 2, true),
                new TiemChung(null, "Vaccine Tả", "Trung tâm Y tế Quận 7", 1, false),
                new TiemChung(null, "Vaccine Zoster", "Trung tâm Y tế Quận 6", 2, true),
                new TiemChung(null, "Vaccine Hib", "Trung tâm Y tế Quận 5", 1, true),
                new TiemChung(null, "Vaccine Cúm H1N1", "Bệnh viện Đa Khoa", 1, true),
                new TiemChung(null, "Vaccine Dengue", "Trung tâm Y tế Quận 11", 1, false),
                new TiemChung(null, "Vaccine Men B", "Trung tâm Y tế Quận 12", 2, true),
                new TiemChung(null, "Vaccine Viêm Màng Não", "Bệnh viện Nhi Đồng 2", 2, true)
            );

            tiemChungRepo.saveAll(tiemChungList);
            System.out.println("Save TiemChung to Database size = " + tiemChungRepo.count());
        }

        // Seed ChiTietTiemChung
        if(chiTietTiemChungRepo.count() == 0) {
            NguoiDung nguoiDung = nguoiDungRepo.findById(2).orElse(null);
            List<TiemChung> tiemChungList = tiemChungRepo.findAll();

            if (nguoiDung != null && nguoiDung.getMaNguoiDung() != null && !tiemChungList.isEmpty()) {
                List<ChiTietTiemChung> chiTietTiemChungList = List.of(
                        new ChiTietTiemChung(tiemChungList.get(2).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 1, 15, 10, 0), "Bác sĩ Nguyễn Văn A", "1", true, tiemChungList.get(2), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(9).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 1, 20, 10, 0), "Bác sĩ Lê Thị B", "1", true, tiemChungList.get(9), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(1).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 2, 1, 10, 0), "Bác sĩ Trần Văn C", "1", false, tiemChungList.get(1), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(3).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 2, 5, 10, 0), "Bác sĩ Nguyễn Thị D", "1", true, tiemChungList.get(3), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(4).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 3, 10, 10, 0), "Bác sĩ Lê Văn E", "1", true, tiemChungList.get(4), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(5).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 3, 20, 10, 0), "Bác sĩ Trần Thị F", "1", true, tiemChungList.get(5), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(6).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 4, 1, 10, 0), "Bác sĩ Nguyễn Văn G", "1", false, tiemChungList.get(6), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(7).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 4, 15, 10, 0), "Bác sĩ Lê Thị H", "2", true, tiemChungList.get(7), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(10).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 5, 1, 10, 0), "Bác sĩ Trần Văn I", "1", true, tiemChungList.get(10), nguoiDung),
                        new ChiTietTiemChung(tiemChungList.get(10).getMaTiemChung(), nguoiDung.getMaNguoiDung(), LocalDateTime.of(2024, 5, 20, 10, 0), "Bác sĩ Nguyễn Văn J", "2", true, tiemChungList.get(10), nguoiDung)
                );

                chiTietTiemChungRepo.saveAll(chiTietTiemChungList);
                System.out.println("Saved " + chiTietTiemChungList.size() + " ChiTietTiemChung records to the database.");
            }
        }

        // Seed data cho KhamBenh
        if (khamBenhRepo.count() == 0) {
            List<KhamBenh> khamBenhList = List.of(
                new KhamBenh(null, "Bệnh viện Đa khoa TP.HCM", LocalDateTime.of(2024, 12, 10, 13, 20), true, nguoiDungRepo.findById(2).orElse(null), new HashSet<>(), new HashSet<>()),
                new KhamBenh(null, "Bệnh viện Y học Cổ truyền", LocalDateTime.of(2025, 1, 15, 3, 30), true, nguoiDungRepo.findById(2).orElse(null), new HashSet<>(), new HashSet<>()),
                new KhamBenh(null, "Bệnh viện Nhi Đồng", LocalDateTime.of(2024, 11, 30, 12, 0), true, nguoiDungRepo.findById(3).orElse(null), new HashSet<>(), new HashSet<>())
            );

            khamBenhRepo.saveAll(khamBenhList);
            System.out.println("Saved " + khamBenhList.size() + " KhamBenh records to the database.");
        }

        // Seed ChiTietKhamBenh
        if (chiTietKhamBenhRepo.count() == 0) {
            KhamBenh khamBenh = khamBenhRepo.findAll().stream().findFirst().orElse(null);

            List<ChiTietKhamBenh> chiTietKhamBenhList = List.of(
                    new ChiTietKhamBenh(null, "Khoa Nội", LocalDateTime.of(2024, 12, 10, 14, 0), "BS. Nguyễn Văn A", "Xét nghiệm máu", "Cảm cúm", "O", "Ở lại viện", true, khamBenh, new HashSet<>(), new HashSet<>()),
                    new ChiTietKhamBenh(null, "Khoa Nhi", LocalDateTime.of(2024, 12, 10, 14, 30), "BS. Trần Văn B", "Siêu âm bụng", "Viêm ruột", "A", "Ở lại viện", true, khamBenh, new HashSet<>(), new HashSet<>()),
                    new ChiTietKhamBenh(null, "Khoa Ngoại", LocalDateTime.of(2024, 12, 10, 15, 16), "BS. Lê Thị C", "CT Scan", "Chấn thương đầu", "B", "Ở lại viện", true, khamBenh, new HashSet<>(), new HashSet<>())
            );

            chiTietKhamBenhRepo.saveAll(chiTietKhamBenhList);
            System.out.println("Saved " + chiTietKhamBenhList.size() + " ChiTietKhamBenh records to the database.");
        }

        // Seed XetNghiem
        if (xetNghiemRepo.count() == 0) {
            ChiTietKhamBenh chiTietKhamBenh = chiTietKhamBenhRepo.findAll().stream().findFirst().orElse(null);

            if (chiTietKhamBenh != null) {
                List<XetNghiem> xetNghiemList = List.of(
                        new XetNghiem(null, "Xét nghiệm máu", "Bình thường", true, chiTietKhamBenh.getMaChiTietKhamBenh(), chiTietKhamBenh),
                        new XetNghiem(null, "Xét nghiệm nước tiểu", "Không phát hiện bất thường", true, chiTietKhamBenh.getMaChiTietKhamBenh(), chiTietKhamBenh),
                        new XetNghiem(null, "Chụp X-quang", "Có dấu hiệu viêm", false, chiTietKhamBenh.getMaChiTietKhamBenh(), chiTietKhamBenh)
                );

                xetNghiemRepo.saveAll(xetNghiemList);
                System.out.println("Saved " + xetNghiemList.size() + " XetNghiem records to the database.");
            } else {
                System.out.println("No ChiTietKhamBenh records found to associate with XetNghiem.");
            }
        }

        // Seed DonThuoc
        if (donThuocRepo.count() == 0) {
            List<DonThuoc> donThuocList = List.of(
                new DonThuoc(null, "Paracetamol", 500, "viên", true),
                new DonThuoc(null, "Ibuprofen", 200, "viên", true),
                new DonThuoc(null, "Amoxicillin", 250, "viên", true),
                new DonThuoc(null, "Cefixime", 100, "viên", true),
                new DonThuoc(null, "Vitamin C", 1000, "viên", true),
                new DonThuoc(null, "Metformin", 500, "viên", true),
                new DonThuoc(null, "Atorvastatin", 10, "viên", true),
                new DonThuoc(null, "Omeprazole", 20, "viên", true),
                new DonThuoc(null, "Losartan", 50, "viên", true),
                new DonThuoc(null, "Aspirin", 81, "viên", true),
                new DonThuoc(null, "Clopidogrel", 75, "gói", true),
                new DonThuoc(null, "Furosemide", 40, "gói", true),
                new DonThuoc(null, "Hydrochlorothiazide", 25, "gói", true),
                new DonThuoc(null, "Captopril", 25, "gói", true),
                new DonThuoc(null, "Diclofenac", 50, "gói", true),
                new DonThuoc(null, "Prednisolone", 5, "gói", true),
                new DonThuoc(null, "Azithromycin", 500, "gói", true),
                new DonThuoc(null, "Levofloxacin", 500, "gói", true),
                new DonThuoc(null, "Doxycycline", 100, "gói", true),
                new DonThuoc(null, "Cetirizine", 10, "gói", true)
            );

            donThuocRepo.saveAll(donThuocList);
            System.out.println("Saved " + donThuocList.size() + " DonThuoc records to the database.");
        }

        // Seed ChiTietDonThuoc
        if (chiTietDonThuocRepo.count() == 0) {
            List<ChiTietKhamBenh> chiTietKhamBenhList = chiTietKhamBenhRepo.findAll();
            List<DonThuoc> donThuocList = donThuocRepo.findAll();

            List<ChiTietDonThuoc> chiTietDonThuocList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                ChiTietKhamBenh chiTietKhamBenh = chiTietKhamBenhList.get(i % chiTietKhamBenhList.size());
                DonThuoc donThuoc = donThuocList.get(i % donThuocList.size());

                chiTietDonThuocList.add(new ChiTietDonThuoc(
                        chiTietKhamBenh.getMaChiTietKhamBenh(),
                        donThuoc.getMaDonThuoc(),
                        (int) (Math.random() * 10) + 1,
                        (int) (Math.random() * 3) + 1,
                        true,
                        chiTietKhamBenh,
                        donThuoc
                ));
            }

            chiTietDonThuocRepo.saveAll(chiTietDonThuocList);
            System.out.println("Saved " + chiTietDonThuocList.size() + " ChiTietDonThuoc records to the database.");
        }

    }
}
