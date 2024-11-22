package model;

import DAO.DocumentDAO;
import util.Date;

import java.time.LocalDate;

public class BorrowReturn {
    private String maMuon;
    private String maNguoiMuon;
    private String maSach;
    private String tenSach;
    private Date ngayMuon;
    private Date ngayHenTra;
    private Date ngayTra;

    public BorrowReturn() {;}
    // Constructor
    public BorrowReturn(String maMuon, String maNguoiMuon, String maSach, Date ngayMuon, Date ngayHenTra, Date ngayTra) {
        this.maMuon = maMuon;
        this.maNguoiMuon = maNguoiMuon;
        this.maSach = maSach;
        this.ngayMuon = ngayMuon;
        this.ngayHenTra = ngayHenTra;
        this.ngayTra = ngayTra;
    }

    public String getMaMuon() {
        return maMuon;
    }

    public void setMaMuon(String maMuon) {
        this.maMuon = maMuon;
    }

    public String getMaNguoiMuon() {
        return maNguoiMuon;
    }

    public void setMaNguoiMuon(String maNguoiMuon) {
        this.maNguoiMuon = maNguoiMuon;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getNgayHenTra() {
        return ngayHenTra;
    }

    public void setNgayHenTra(Date ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

}
