use master
go
create database DSV
go
USE DSV
go
--3. tạo bảng Users
create table Users
(
    Username varchar(50) primary key,
    Password varchar(50) not null,
    role varchar(50)
)
go
-- thêm dữ liệu vào Users
insert into Users values('tula','123','admin')
insert into Users values('kiet','123','admin')
insert into Users values('hoang','123','user')
go
select * from Users
go
--4. tạo bảng Students
create table Students
(
    MaSV char(7) primary key,
    HoTen nVarchar(50) not null,
    Email Varchar(50),
    SoDT Varchar(15),
    GioiTinh bit,
    DiaChi nVarchar(50),
    Hinh Varchar(50)
)
go
--5. tạo bảng Grade
create table Grade
(
    id int identity(1,1) primary key,
    MaSV char(7) references Students(MaSV),
    TiengAnh int,
    TinHoc int,
    GDTC int
)
go
-- Nhập bảng Students
insert into Students values('PS01',N'Nguyễn Đinh Hữu Giang','giang@gmail.com','0907774677',1,N'123 Hai Bà Trưng','giang.jpg')
go
insert into Students values('PS02',N'Nguyễn Huy Hoàng','hoang@gmail.com','0907774677',0,N'456 Hai Bà Trưng','hoang.jpg')
go
 insert into Students values('PS03',N'Phạm Mạnh Tài','tai@gmail.com','0907774677',0,N'200 Hai Bà Trưng','tai.jpg')
go
select * from Students
go
-- Nhập bảng Grade
insert into Grade(masv,tienganh,tinhoc,GDTC) values('PS01',7,8,9)
insert into Grade(masv,tienganh,tinhoc,GDTC) values('PS02',10,9,9)
insert into Grade(masv,tienganh,tinhoc,GDTC) values('PS03',5,10,6)
go

select * from Grade

