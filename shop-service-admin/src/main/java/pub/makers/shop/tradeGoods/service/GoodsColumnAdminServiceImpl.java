package pub.makers.shop.tradeGoods.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.GoodsColumn;
import pub.makers.shop.tradeGoods.pojo.GoodsColumnPram;
import pub.makers.shop.tradeGoods.vo.GoodsColumnVo;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**      C:\workspace\shop-service-admin\src\main\resources\tpl\sql\GoodsColumn\queryGoodsColumnListByName.sql
 * Created by devpc on 2017/8/8.
 */
@Service(version = "1.0.0")
public class GoodsColumnAdminServiceImpl implements GoodsColumnMgrBizService {

    private Logger logger = LoggerFactory.getLogger(GoodsColumnAdminServiceImpl.class);
    @Autowired
    private GoodsColumnService goodsColumnService;

    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResultList<GoodsColumnVo> selectGoodsColumnByColUmnName(GoodsColumnPram goodsColumnPram, Paging paging) {        //查询所有
        Map<String, Object> data = Maps.newHashMap();
        data.put("columnName",goodsColumnPram.getColumnName());
        String sql = FreeMarkerHelper.getValueFromTpl("sql/GoodsColumn/queryGoodsColumnListByName.sql", data);
        RowMapper<GoodsColumnVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(GoodsColumnVo.class);
        ResultList<GoodsColumnVo> resultList = new ResultList<GoodsColumnVo>();
        try {
            List<GoodsColumnVo> list = jdbcTemplate.query(sql,rowMapper,paging.getPs(),paging.getPn());

            String countSql = FreeMarkerHelper.getValueFromTpl("sql/GoodsColumn/countGoodsColumn.sql", data);
            Number total = jdbcTemplate.queryForObject(countSql,null,Integer.class);
             for(int i = 0;i < list.size(); i++){                        //查询图片
                if(list.get(i).getSlideshowImages() != null){
                    ImageGroupVo imageGroupVo =cargoImageBizService.getImageGroup(list.get(i).getSlideshowImages()+"");//轮播图
                    list.get(i).setImageGroupVo(imageGroupVo);
                }
                if(list.get(i).getIntroduceImage() != null){      //(Long.parseLong(list.get(i).getIntroduceImage()));
                    ImageVo imageVo = cargoImageBizService.getImageByImageId(list.get(i).getIntroduceImage());//介绍图
                    list.get(i).setImageVo(imageVo);
                }
            }
            resultList.setResultList(list);
            resultList.setTotalRecords(total!=null?total.intValue():0);

        } catch (Exception e) {
            logger.error("查询商品栏目异常:", e);
        }
        return resultList;
    }

    @Override
    public boolean editGoodsColumn(GoodsColumnVo goodsColumnVo, String userId){            //修改
        GoodsColumn goodsColumn = new GoodsColumn();
        goodsColumn.setId(Long.parseLong(goodsColumnVo.getId()));
        List<String> imageUrlList = new ArrayList<String>();
        if (!goodsColumnVo.getImageGroupVo().getImages().isEmpty()){
            for (int i = 0; i <goodsColumnVo.getImageGroupVo().getImages().size(); i++){
                imageUrlList.add(goodsColumnVo.getImageGroupVo().getImages().get(i).getUrl());
            }
        }

        try {
            if(!imageUrlList.isEmpty()){                                                            //修改group
                cargoImageBizService.deleteImgByGroupId(goodsColumnVo.getImageGroupVo().getGroupId());
                Long groupId = cargoImageBizService.saveGroupImage(imageUrlList,userId);
                goodsColumnVo.getImageGroupVo().setGroupId(String.valueOf(groupId));
                goodsColumn.setSlideshowImages(groupId);
            }else if(goodsColumnVo.getImageGroupVo().getGroupId() != null){
                cargoImageBizService.deleteImgByGroupId(goodsColumnVo.getImageGroupVo().getGroupId());
                goodsColumnVo.getImageGroupVo().setGroupId("");
            }

            cargoImageBizService.deleteImage(goodsColumnVo.getShowpictureId());                                                 //修改详情图
            ImageVo imageVo = new ImageVo();
            imageVo.setId(goodsColumnVo.getShowpictureId());
            imageVo.setPicUrl(goodsColumnVo.getShowpicture());
            String showPictureId = cargoImageBizService.saveImage(imageVo,userId);
            goodsColumn.setShowpicture(showPictureId);

            cargoImageBizService.deleteImage(goodsColumnVo.getImageVo().getId());                                      //介绍图
            String introduceImageId = cargoImageBizService.saveImage(goodsColumnVo.getImageVo(),userId);
            goodsColumn.setIntroduceImage(Long.parseLong(introduceImageId));

            goodsColumn.setColumnName(goodsColumnVo.getColumnName());
            goodsColumn.setOrderBy(goodsColumnVo.getOrderBy());
            goodsColumn.setStatus(goodsColumnVo.getStatus());
            goodsColumn.setUpdateBy(Long.parseLong(userId));

            goodsColumnService.update(goodsColumn);
            goodsColumnService.update(Update.byId(goodsColumn.getId()).set("showPicture",showPictureId));//showpicture死活修改不了，只能再来一次
        }catch (Exception e) {
        logger.error("修改信息:", e);
    }

        return true;
    }

}
