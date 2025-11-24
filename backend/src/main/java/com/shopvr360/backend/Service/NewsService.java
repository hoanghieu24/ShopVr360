package com.shopvr360.backend.Service;

import com.shopvr360.backend.dto.NewsRequest;
import com.shopvr360.backend.dto.ProductListRequest;
import com.shopvr360.backend.entity.TableBaiviet;
import com.shopvr360.backend.entity.TableProductList;
import com.shopvr360.backend.repository.NewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<TableBaiviet> getTableBaiviet() {
       return newsRepository.findAllByHienthi(1);
    }

    public List<TableBaiviet> getTinTuc(String type, int hienthi){
        return newsRepository.findAllByTypeAndHienthi(type ,hienthi);
    }

    public TableBaiviet createBaiViet(NewsRequest newsRequest ) {
        TableBaiviet tableBaiviet = modelMapper.map(newsRequest, TableBaiviet.class);
        tableBaiviet.setId(null);

        if(newsRequest.getPhoto() != null ) tableBaiviet.setPhoto(newsRequest.getPhoto());
        tableBaiviet.setStt(
                newsRequest.getStt() != null ? newsRequest.getStt() : 1L
        );
        setDefaultFields(tableBaiviet , newsRequest);

        return  newsRepository.save(tableBaiviet);
    }

    public TableBaiviet updateProduct(Long id, NewsRequest request) throws IOException {
        TableBaiviet tableBaiviet = newsRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Product not found"));


        modelMapper.map(request, tableBaiviet);

        setDefaultFields(tableBaiviet, request);

        if (request.getPhoto() != null) tableBaiviet.setThumb((request.getPhoto()));

        tableBaiviet.setNgaysua((int) (System.currentTimeMillis() / 1000));

        return newsRepository.save(tableBaiviet);
    }

    public void toggleActive(int id) {
        TableBaiviet n = newsRepository.findById(id).orElseThrow();
        n.setHienthi(n.getHienthi() == 1 ? 0 : 1);
        newsRepository.save(n);
    }

    public void toggleOutstanding(int id) {
        TableBaiviet n = newsRepository.findById(id).orElseThrow();
        n.setNoibat(n.getNoibat() == 1 ? 0 : 1);
        newsRepository.save(n);
    }

    public void deleteProduct(Long id) {
        if (!newsRepository.existsById(Math.toIntExact(id))) {
            throw new RuntimeException("Không tìm thấy sản phẩm có id = " + id);
        }
        newsRepository.deleteById(Math.toIntExact(id));
    }

    public void deleteMultiple(List<Integer> ids) {
        List<TableBaiviet> products = newsRepository.findAllById(ids);
        if (products.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm nào trong danh sách.");
        }
        newsRepository.deleteAll(products);
    }

    private void setDefaultFields(TableBaiviet list, NewsRequest req) {


        list.setNoibat(0);
        list.setIdCat(0);
        list.setIdList(0);
        list.setIdItem(0);
        list.setIdSub(0);
        list.setMotaVi("");
        list.setMotaEn("");
        list.setMotaCn("");

        list.setTitle(req.getTen_vi());
        list.setTenVi(req.getTen_vi());
        list.setTenEn(req.getTen_en());
        list.setNameVi(req.getTen_vi());
        list.setNameEn(req.getTen_en());

        list.setDescription(req.getDescription() != null ? req.getDescription() : "");
        list.setKeywords(req.getKeywords() != null ? req.getKeywords() : "");
        list.setHienthi(1);
        list.setNgaytao((int) Instant.now().getEpochSecond());
        list.setNgaysua((int) Instant.now().getEpochSecond());

        if (list.getThumb() == null) list.setThumb("");
        if (list.getThumb2() == null) list.setThumb2("");
        if (list.getPhoto2() == null) list.setPhoto2("");

        if (list.getMetadata1() == null) list.setMetadata1("");
        if (list.getMetadata2() == null) list.setMetadata2("");
        if (list.getMetadata3() == null) list.setMetadata3("");
        if (list.getMetadata4() == null) list.setMetadata4("");
        if (list.getFileDownload() == null) list.setFileDownload("");
        if (list.getTenCn() == null) list.setTenCn("");
        if (list.getNoidungCn() == null) list.setNoidungCn("");
        if (list.getNoidungEn() == null) list.setNoidungEn("");
        if (list.getLuotxem() == null) list.setLuotxem(0);
    }

}
