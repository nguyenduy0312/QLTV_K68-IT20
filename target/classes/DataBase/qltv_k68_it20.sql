create table Document 
(
    STT int ,
	MaSach varchar(50) not null,
	TenSach text not null,
	TacGia varchar(5000) not null,
    QRCode blob,
    TheLoaiSach varchar(5000) not null,
	NhaXuatBan varchar(5000) not null,
	SoLuong int not null,
    SoNgayMuon int not null,
    Picture mediumblob,
	constraint pk_MaSach primary key (MaSach)
);
 drop table Document;

create table Borrowing
(   
    STT int,
	MaMuon varchar(50) not null,
	personID varchar(50) not null,
	MaSach varchar(50) not null,
	NgayMuon date not null,
	NgayHenTra date not null,
	NgayTra date not null,

	constraint pk_MaMuon primary key (MaMuon),
	constraint fk_MaSach foreign key (MaSach) references Document(MaSach) on update cascade on delete cascade
);
drop table Borrowing;

create table user
(
    STT int,
	personID varchar(50) not null,
	HoTen varchar(100) not null,
	NgaySinh date not null,
	GioiTinh varchar(50) not null,
	DiaChi varchar(100) not null,
    Email varchar(100) not null unique,
	SoDienThoai int,
    TenDangNhap VARCHAR(50) NOT NULL unique,
    MatKhau VARCHAR(255) NOT NULL,
	constraint pk_personID primary key (personID)
);
-- drop table user
alter table user
modify Email varchar(255);

create table admin
(
    STT int,
	personID varchar(50) not null,
	HoTen varchar(100) not null,
	NgaySinh date not null,
	GioiTinh varchar(50) not null,
	DiaChi varchar(100) not null,
    Email varchar(100) not null unique,
	SoDienThoai int,
    TenDangNhap VARCHAR(50) NOT NULL unique,
    MatKhau VARCHAR(255) NOT NULL,
	constraint pk_personID primary key (personID)
);
-- drop table admin

INSERT INTO user (STT, personID, HoTen, NgaySinh, GioiTinh, DiaChi, Email, SoDienThoai, TenDangNhap, MatKhau) 
VALUES
(1, 'P001', 'Nguyen Van A', '1990-01-01', 'Nam', '123 Le Loi, TP HCM', 'nguyenvana@gmail.com', 1234567890, 'user1', 'password1'),
(2, 'P002', 'Tran Thi B', '1992-02-02', 'Nu', '456 Nguyen Trai, Ha Noi', 'tranthib@gmail.com', 1234567891, 'user2', 'password2'),
(3, 'P003', 'Le Van C', '1993-03-03', 'Nam', '789 Tran Hung Dao, Da Nang', 'levanc@gmail.com', 1234567892, 'user3', 'password3'),
(4, 'P004', 'Pham Thi D', '1994-04-04', 'Nu', '101 Bach Dang, Can Tho', 'phamthid@gmail.com', 1234567893, 'user4', 'password4'),
(5, 'P005', 'Hoang Van E', '1995-05-05', 'Nam', '102 Le Thanh Ton, Hai Phong', 'hoangvane@gmail.com', 1234567894, 'user5', 'password5'),
(6, 'P006', 'Nguyen Thi F', '1996-06-06', 'Nu', '103 Hoang Dieu, Vung Tau', 'nguyenthif@gmail.com', 1234567895, 'user6', 'password6'),
(7, 'P007', 'Tran Van G', '1997-07-07', 'Nam', '104 Dien Bien Phu, Hue', 'tranvang@gmail.com', 1234567896, 'user7', 'password7'),
(8, 'P008', 'Pham Van H', '1998-08-08', 'Nam', '105 Nguyen Van Cu, Nha Trang', 'phamvanh@gmail.com', 1234567897, 'user8', 'password8'),
(9, 'P009', 'Le Thi I', '1999-09-09', 'Nu', '106 Vo Van Tan, Quy Nhon', 'lethii@gmail.com', 1234567898, 'user9', 'password9'),
(10, 'P010', 'Do Van J', '2000-10-10', 'Nam', '107 Phan Chu Trinh, Thanh Hoa', 'dovanj@gmail.com', 1234567899, 'user10', 'password10');

INSERT INTO admin (STT, personID, HoTen, NgaySinh, GioiTinh, DiaChi, Email, SoDienThoai, TenDangNhap, MatKhau)
VALUES

    (1, '1', 'Đào Huy Hoàng', '2005-12-23', 'Nam', 'Ý Yên, Nam Định', 'hoangdh2312@gmail.com', 0352712366, 'admin1', 'password1'),
    (1, '2', 'Nguyễn Văn Duy', '2005-12-10', 'Nam', 'Hưng Yên', 'duynv@gmail.com', 0352712365, 'admin2', 'password2'),
    (1, '3', 'Nguyễn Văn Hoàng', '2005-10-30', 'Nam', 'Thái Bình', 'hoangnv@gmail.com', 0352712367, 'admin3', 'password3');