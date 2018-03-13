package teamlab.rest.dto;

/**
 * ProductのDtoクラス
 * @author mukaihiroto
 *
 */
public class ProductDto {
	
	private int id;

	private String title;

	private String description;
	
	private Long price;
	
	private String picPath;

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

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public ProductDto(){}
	
}
