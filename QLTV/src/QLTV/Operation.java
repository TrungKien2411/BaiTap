
package QLTV;

public class Operation {
    // Viết các câu lệnh thao tác với SQL thêm, sửa, xóa 
    public static String INSERTSQLSach = "INSERT INTO SACH(MaSach, TenSach, TheLoai, TacGia, TinhTrang, SoLuong) VALUES(?,?,?,?,?,?)";
    public static String UPDATESQLSach = "UPDATE SACH SET TenSach =?, TheLoai=?, TacGia=?, TinhTrang=?,SoLuong=? WHERE MaSach=?";
    public static String DELETESQLSach = "DELETE FROM SACH WHERE MaSach=?";
    public static String INSERTSQLDocGia = "INSERT INTO DOCGIA(MaDG, TenDG, DiaChi, Email, SDT) VALUES(?,?,?,?,?)";
    public static String UPDATESQLDocGia = "UPDATE DOCGIA SET TenDG =?, DiaChi=?, Email=?,SDT=? WHERE MaDG=?";
    public static String DELETESQLDocGia = "DELETE FROM DOCGIA WHERE MaDG=?";
    public static String INSERTSQLPM = "INSERT INTO PHIEUMUON(MaPM, MaSach, MaDG, Ngay) VALUES(?,?,?,?)";
    public static String UPDATESQLPM = "UPDATE PHIEUMUON SET MaSach =?, MaDG=?, Ngay=? WHERE MaPM=?";
    public static String DELETESQLPM = "DELETE FROM PHIEUMUON WHERE MaPM=?";
}
