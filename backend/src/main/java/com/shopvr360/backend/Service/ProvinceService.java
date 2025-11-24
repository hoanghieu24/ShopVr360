package com.shopvr360.backend.Service;

import com.shopvr360.backend.entity.TableBaiviet;
import com.shopvr360.backend.entity.TableProduct;
import com.shopvr360.backend.entity.TableTinh;
import com.shopvr360.backend.repository.ProvinceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public TableTinh createTinhThanh(String ten, Long stt, Integer phivanchuyen) {
        TableTinh tableTinh = new TableTinh();

        tableTinh.setTen(ten);
        tableTinh.setStt(stt != null ? stt.intValue() : 1);
        tableTinh.setHienthi(1);
        tableTinh.setPhivanchuyen(20000);

        return provinceRepository.save(tableTinh);
    }

    public TableTinh updateTinhThanh(Integer id, String ten, Integer phivanchuyen, Integer stt, Integer hienthi) {
        TableTinh tableTinh = provinceRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Province not found"));

        if (ten != null) tableTinh.setTen(ten);
        if (phivanchuyen != null) tableTinh.setPhivanchuyen(phivanchuyen);
        if (stt != null) tableTinh.setStt(stt);
        if (hienthi != null) tableTinh.setHienthi(hienthi);

        return provinceRepository.save(tableTinh);
    }

    public void toggleActive(int id) {
        TableTinh n = provinceRepository.findById((long) id).orElseThrow();
        n.setHienthi(n.getHienthi() == 1 ? 0 : 1);
        provinceRepository.save(n);
    }

    public void deleteProduct(Long id) {
        if (!provinceRepository.existsById((long) Math.toIntExact(id))) {
            throw new RuntimeException("Không tìm thấy sản phẩm có id = " + id);
        }
        provinceRepository.deleteById((long) Math.toIntExact(id));
    }

    public void deleteMultiple(List<Long> ids) {
        List<TableTinh> products = provinceRepository.findAllById(ids);
        if (products.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm nào trong danh sách.");
        }
        provinceRepository.deleteAll(products);
    }





    public List<TableTinh> getAllProvinces() {
        return provinceRepository.findAll();
    }
    public List<TableTinh> getAllTinhByHienThi() {
        return provinceRepository.findAllByHienthi(1);
    }
}
