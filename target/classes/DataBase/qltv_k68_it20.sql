create table Document 
(
    STT int,
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
	constraint fk_MaSach foreign key (MaSach) references Document(MaSach) on update cascade on delete cascade
);
-- drop table Borrowing;

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





