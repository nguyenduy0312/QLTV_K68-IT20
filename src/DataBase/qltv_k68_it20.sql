create table Person
(
    STT int,
	personID varchar(50) not null,
	HoTen varchar(100) not null,
	NgaySinh date not null,
	GioiTinh varchar(50) not null,
	DiaChi varchar(100) not null,
    Email varchar(100) not null unique,
	SoDienThoai int,
	constraint pk_personID primary key (personID)
);
-- drop table person;

create table Document 
(
    STT int,
	MaSach varchar(50) not null,
	TenSach varchar(50) not null,
	TacGia varchar(50) not null,
    QRCode blob,
    TheLoaiSach varchar(50) not null,
	NhaXuatBan varchar(50) not null,
	SoLuong int not null,
    SoNgayMuon int not null,
	constraint pk_MaSach primary key (MaSach)
);
-- drop table Document;

create table Borrowing
(   
    STT int,
	MaMuon varchar(50) not null,
	personID varchar(50) not null,
	MaSach varchar(50) not null,
	NgayMuon date not null,
	NgayHenTra date not null,
	NgayTra date not null,
	TrangThai varchar(50) not null,
	constraint pk_MaMuon primary key (MaMuon),
	constraint fk_personID foreign key (personID) references Person(personID) on update cascade on delete cascade,
	constraint fk_MaSach foreign key (MaSach) references Document(MaSach) on update cascade on delete cascade
);
-- drop table Borrowing;

create table Account 
(
    STT int,
    TenDangNhap VARCHAR(50) NOT NULL unique,
    MatKhau VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL unique ,
    constraint pk_TenDangNhap PRIMARY KEY (TenDangNhap),
    constraint fk_Email foreign key (Email) references Person(Email) on update cascade on delete cascade
);
-- drop table account;

-- insert into Person (STT,personID, HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, email)
-- values
-- 	(1,'P001', 'Nguyễn Văn A', '1990-01-15', 'Nam', '123 Đường Lê Lợi, Quận 1, TPHCM', 123456789, 'nguyenvana@example.com'),
-- 	(2,'P002', 'Trần Thị B', '1985-05-20', 'Nữ', '456 Đường Nguyễn Huệ, Quận 2, TPHCM', 987654321, 'tranthib@example.com'),
-- 	(3,'P003', 'Lê Văn C', '1992-08-30', 'Nam', '789 Đường Trần Hưng Đạo, Quận 3, TPHCM', 456789123, 'levanc@example.com');
-- -- them du lieu Person
--
-- insert into Document (STT, MaSach, TenSach, TacGia, TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon)
-- values
--     (1, 'Mac1', 'Triết học 1', 'Đại học Văn hóa', 'Sách', 'Giáo dục', 50, 30),
--     (2, 'Mac2', 'Triết học 2', 'Đại học Văn hóa', 'Báo', 'Giáo dục', 40, 15),
--     (3, 'Mac3', 'Triết học 3', 'Đại học Xã hội', 'Tạp chí', 'Văn học', 30, 10),
--     (4, 'C#', 'Ngôn ngữ lập trình C#', 'Đại học Công nghệ', 'Luận văn', 'Kỹ thuật', 20, 7),
--     (5, 'Logic', 'Logic học đại cương', 'Đại học Luật', 'Sách', 'Tổng hợp', 25, 20),
--     (6, 'PLDC', 'Pháp luật đại cương', 'Đại học Luật TPHCM', 'Báo', 'Chính trị', 15, 5),
--     (7, 'KTMT', 'Kiến trúc Máy tính', 'Đại học Kỹ thuật', 'Tạp chí', 'Kỹ thuật', 10, 12),
--     (8, 'CSDL', 'Cơ sở dữ liệu', 'Đại học Thông tin', 'Luận văn', 'Thông tin', 35, 30),
--     (9, 'MMT', 'Mạng Máy tính', 'Đại học Khoa học', 'Sách', 'Tự nhiên', 25, 25),
--     (10, 'TKW', 'Thiết kế web', 'Đại học Tự nhiên', 'Tạp chí', 'Khoa học', 15, 15),
--     (11, 'HMT', 'Hệ Mặt Trời', 'Khoa học Môi Trường', 'Sách', 'Giáo dục', 22, 20),
--     (12, 'VHL', 'Vật lý học', 'Đại học Khoa học Tự nhiên', 'Sách', 'Khoa học', 45, 30),
--     (13, 'TCH', 'Toán cao cấp', 'Đại học Khoa học', 'Sách', 'Giáo dục', 30, 25),
--     (14, 'CNTT', 'Công nghệ thông tin', 'Đại học Công nghệ', 'Sách', 'Kỹ thuật', 50, 40),
--     (15, 'VH', 'Văn học Việt Nam', 'Đại học Văn học', 'Sách', 'Văn học', 28, 12),
--     (16, 'KHMT', 'Khoa học máy tính', 'Đại học Bách khoa', 'Luận văn', 'Kỹ thuật', 10, 5),
--     (17, 'CTTT', 'Công nghệ truyền thông', 'Đại học Khoa học', 'Tạp chí', 'Khoa học', 60, 35),
--     (18, 'LT', 'Lập trình thuật toán', 'Đại học Khoa học', 'Sách', 'Kỹ thuật', 15, 7),
--     (19, 'KTH', 'Kỹ thuật hệ thống', 'Đại học Bách khoa', 'Sách', 'Kỹ thuật', 20, 10),
--     (20, 'PDL', 'Phát triển lãnh đạo', 'Đại học Kinh tế', 'Sách', 'Kinh tế', 33, 22),
--     (21, 'QLDA', 'Quản lý dự án', 'Đại học Kinh tế', 'Sách', 'Kinh tế', 25, 18);
-- -- Them du lieu Document
--
-- insert into Account (STT,TenDangNhap, MatKhau, Email)
-- values
-- 	(1,'user1', 'password1', 'nguyenvana@example.com'),
-- 	(2,'user2', 'password2', 'tranthib@example.com'),
-- 	(3,'user3', 'password3', 'levanc@example.com');
-- -- Them du lieu vao Account
--
