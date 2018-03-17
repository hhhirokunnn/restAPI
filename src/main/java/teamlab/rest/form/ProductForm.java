package teamlab.rest.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * ProductのFormクラス
 * @author mukaihiroto
 *
 */
public class ProductForm {

    private int id;

    private String title;

    private String description;
    
    private Long price;
    
    private MultipartFile uploadFile;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    
    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }


}
