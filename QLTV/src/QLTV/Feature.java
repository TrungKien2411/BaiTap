package QLTV;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Feature {
    private final DbConnect helper = new DbConnect();
    
    //Viết hàm tìm kiếm sách theo tên sách
    public ResultSet FilterSach(String[] stringsSQL, String TenSach){
        String sql = "SELECT * FROM SACH "
                + "WHERE TenSach LIKE N'%" + TenSach +"%'";
        return helper.GET(sql);
    }

    //Viết hàm tìm kiếm đọc giả
    public ResultSet FilterDocGia(String[] stringsSQL, String TenDG) throws SQLException {
        String sql = "SELECT * FROM DOCGIA "
                + "WHERE TenDG LIKE N'%" + TenDG +"%'";
        return helper.GET(sql);
    }   
    
    //Viết hàm tìm kiếm phiếu mượn 
    public ResultSet FilterPM(String[] stringsSQL, String MaDG) throws SQLException {
        String sql = "SELECT * FROM PHIEUMUON "
                + "WHERE MaDG LIKE N'%" + MaDG +"%'" ;
        return helper.GET(sql);
    } 
    // Hàm thống kê số lượng sách
    public int SQLThongKeSach() throws SQLException{
        String sql = "SELECT SUM(SoLuong) FROM SACH";
        
        ResultSet result = helper.GET(sql);
        result.next();
        return result.getInt(1);
    }  
    
    // Hàm thống kê số lượng đọc giả
    public int SQLThongKeDocGia() throws SQLException {
        String sql = "SELECT COUNT(MaDG) FROM DOCGIA";
        
        ResultSet result = helper.GET(sql);
        result.next();
        return result.getInt(1);
    }
    
    // hàm thống kê số lượng phiếu mượn
    public int SQLThongKePhieuMuon() throws SQLException {
        String sql = "SELECT COUNT(MaPM) FROM PHIEUMUON";
        
        ResultSet result = helper.GET(sql);
        result.next();
        return result.getInt(1);
    }

}
