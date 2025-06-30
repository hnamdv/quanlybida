/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.daointer;

import MODEl.Nhanvien;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface NhanVienDAO {
       List<Nhanvien> getAll();
    Nhanvien findById(String maNV);
}
