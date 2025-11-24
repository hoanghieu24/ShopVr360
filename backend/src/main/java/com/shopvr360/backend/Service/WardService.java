package com.shopvr360.backend.Service;

import com.shopvr360.backend.entity.TableBaiviet;
import com.shopvr360.backend.entity.TableDonhang;
import com.shopvr360.backend.entity.TableQuan;
import com.shopvr360.backend.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class WardService {

    @Autowired
    private WardRepository wardRepository;

    public List<TableQuan> getAllWard(){
        return wardRepository.findAll();
    }

    public List<TableQuan> getAllQuanOnView() {
        return wardRepository.findAllByHienthi(true);
    }

    public void toggleActive(int id) {
        TableQuan n = wardRepository.findById(id).orElseThrow();
        n.setHienthi(n.getHienthi() == true ? false : true);
        wardRepository.save(n);
    }

    public TableQuan createQuan(Integer idCat, String ten, Boolean hienthi) {
        TableQuan quan = new TableQuan();
        quan.setIdCat(idCat);
        quan.setTen(ten);
        quan.setHienthi(hienthi != null ? hienthi : true);
        quan.setStt(1);
        quan.setPhivanchuyen(20000);
        quan.setCode("TEMP");

        TableQuan saved = wardRepository.save(quan);

        saved.setCode(saved.getId().toString());

        return wardRepository.save(saved);
    }

    public TableQuan updateQuan(Integer id,Integer idCat, String ten, Boolean hienthi) {
        TableQuan quan = wardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quận/huyện id=" + id));
        if (idCat != null) quan.setIdCat(idCat);
        if (ten != null) quan.setTen(ten);
        if (hienthi != null) quan.setHienthi(hienthi);
        return wardRepository.save(quan);
    }

    public List<TableQuan> getAllByTinh(Integer idCat) {
        return wardRepository.findAllByIdCat(idCat);
    }

    public void deleteProduct(Long id) {
        if (!wardRepository.existsById(Math.toIntExact(id))) {
            throw new RuntimeException("Không tìm thấy sản phẩm có id = " + id);
        }
        wardRepository.deleteById(Math.toIntExact(id));
    }

    public void deleteMultiple(List<Integer> ids) {
        List<TableQuan> products = wardRepository.findAllById(ids);
        if (products.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm nào trong danh sách.");
        }
        wardRepository.deleteAll(products);
    }


}
