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
	personID varchar(50) not null,
	HoTen varchar(100) not null,
	NgaySinh date not null,
	GioiTinh varchar(50) not null,
	DiaChi varchar(100) not null,
    Email varchar(100) not null unique,
	SoDienThoai varchar(15),
    TenDangNhap VARCHAR(50) NOT NULL unique,
    MatKhau VARCHAR(255) NOT NULL,
    Picture mediumblob,
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


INSERT INTO admin (STT, personID, HoTen, NgaySinh, GioiTinh, DiaChi, Email, SoDienThoai, TenDangNhap, MatKhau)
VALUES

    (1, '1', 'Đào Huy Hoàng', '2005-12-23', 'Nam', 'Ý Yên, Nam Định', 'hoangdh2312@gmail.com', 0352712366, 'admin1', 'password1'),
    (1, '2', 'Nguyễn Văn Duy', '2005-12-10', 'Nam', 'Hưng Yên', 'duynv@gmail.com', 0352712365, 'admin2', 'password2'),
    (1, '3', 'Nguyễn Văn Hoàng', '2005-10-30', 'Nam', 'Thái Bình', 'hoangnv@gmail.com', 0352712367, 'admin3', 'password3');