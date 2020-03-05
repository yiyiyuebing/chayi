package pub.makers.shop.cargo.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pub.makers.shop.cargo.entity.CargoMaterialLibrary;
import pub.makers.shop.store.vo.ImageVo;

/**
 * Created by daiwenfa on 2017/6/6.
 */
public class CargoMaterialLibraryVo implements Serializable{
    private String id;
    private String MaterialName;
    private String MaterialDocument;
    private String Image;
    private String isValid;
    private String relevanceCargoId;
    private Date dateCreated;
    private Date lastUpdated;
    private String createBy;
    private String updateBy;
    private String remark;
    private String relevanceCargoName;
    private List<CargoVo> relevanceCargos;
    private List<ImageVo> imageVos;

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public String getMaterialDocument() {
        return MaterialDocument;
    }

    public void setMaterialDocument(String materialDocument) {
        MaterialDocument = materialDocument;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getRelevanceCargoId() {
        return relevanceCargoId;
    }

    public void setRelevanceCargoId(String relevanceCargoId) {
        this.relevanceCargoId = relevanceCargoId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

	public List<CargoVo> getRelevanceCargos() {
		return relevanceCargos;
	}

	public void setRelevanceCargos(List<CargoVo> relevanceCargos) {
		this.relevanceCargos = relevanceCargos;
	}

    public String getRelevanceCargoName() {
        return relevanceCargoName;
    }

    public void setRelevanceCargoName(String relevanceCargoName) {
        this.relevanceCargoName = relevanceCargoName;
    }

    public List<ImageVo> getImageVos() {
		return imageVos;
	}

	public void setImageVos(List<ImageVo> imageVos) {
		this.imageVos = imageVos;
	}

	public static CargoMaterialLibraryVo fromCargoMaterialLibrary(CargoMaterialLibrary library){
		CargoMaterialLibraryVo vo = new CargoMaterialLibraryVo();
		vo.setId(library.getId() == null ? null : library.getId().toString());
		vo.setMaterialName(library.getMaterialName());
		vo.setMaterialDocument(library.getMaterialDocument());
		vo.setImage(library.getImage());
		vo.setIsValid(library.getIsValid());
		vo.setRelevanceCargoId(library.getRelevanceCargoId());
		vo.setDateCreated(library.getDateCreated());
		vo.setLastUpdated(library.getLastUpdated());
		vo.setCreateBy(library.getCreateBy() == null ? null : library.getCreateBy().toString());
		vo.setUpdateBy(library.getUpdateBy() == null ? null : library.getUpdateBy().toString());
		return vo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
