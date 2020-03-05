package pub.makers.shop.tongji.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.afterSale.enums.OrderAsType;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.tongji.entity.TjShopTotalKpi;
import pub.makers.shop.tongji.pojo.TjShopTotalKpiParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by devpc on 2017/8/14.
 */
@Service(version="1.0.0")
public class TjShopTotalKpiBizServiceImpl implements TjShopTotalKpiBizService{
    private Logger logger = LoggerFactory.getLogger(TjShopTotalKpiBizServiceImpl.class);

    @Resource(name = "tjTradeTotalKpiServiceImpl")
    private TjShopTotalKpiService tjTradeTotalKpiService;

    @Resource(name = "tjPurchaseTotalKpiServiceImpl")
    private TjShopTotalKpiService tjPurchaseTotalKpiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Map<String, Object> selectTodayTotalKpi(OrderBizType orderBizType) {

        //totalRecords != null ? totalRecords.intValue() : 0
        TjShopTotalKpiService tjShopTotalKpiService = getTjShopTotalKpiService(orderBizType);

        TjShopTotalKpiParam tjShopTotalKpiParam = new TjShopTotalKpiParam();  //传入参数              /**改改***/

        tjShopTotalKpiParam.setPurchseOrTrade(getTjShopParam(orderBizType));                //传入判断是哪张表的参数

        Date date = new Date();
        SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
        String creatDate = matter1.format(date);
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往后减少一天.整数往后推,负数往前移动
        String lastDate = matter1.format(calendar.getTime());//这个时间就是日期往前推一天的结果
        calendar.setTime(date);
        calendar.add(calendar.DATE,-7);//把日期往后减少7天.整数往后推,负数往前移动
        String lastWeek = matter1.format(calendar.getTime());//这个时间就是日期往前推7天的结果

        TjShopTotalKpi tjShopTotalKpiToday = todayInfo(tjShopTotalKpiParam);  //今天的数据

        TjShopTotalKpi tjShopTotalKpiLastDate = tjShopTotalKpiService.get(Conds.get().eq("date_created",lastDate));
        if( tjShopTotalKpiLastDate == null){
            tjShopTotalKpiLastDate = lastDateInfo(tjShopTotalKpiParam);
            tjShopTotalKpiLastDate.setId(IdGenerator.getDefault().nextId()+"");
            tjShopTotalKpiService.insert(tjShopTotalKpiLastDate);
        }

        TjShopTotalKpi tjShopTotalKpiLastWeek = tjShopTotalKpiService.get(Conds.get().eq("date_created",lastWeek));
        if( tjShopTotalKpiLastWeek == null){
            tjShopTotalKpiLastWeek = lastWeekInfo(tjShopTotalKpiParam);
            tjShopTotalKpiLastWeek.setId(IdGenerator.getDefault().nextId()+"");
            tjShopTotalKpiService.insert(tjShopTotalKpiLastWeek);
        }

        //成交转换率  客单价  //退款金额             昨日
       /* tjShopTotalKpiToday.setCvr(new BigDecimal(Double.toString((double)tjShopTotalKpiToday.getOrderNum()/(double)tjShopTotalKpiToday.getVisitorNum())));
        tjShopTotalKpiToday.setPct((tjShopTotalKpiToday.getTotalPay()).divide(new BigDecimal(tjShopTotalKpiToday.getOrderNum()), 0, BigDecimal.ROUND_HALF_EVEN));*/

        //与昨日上周对比支付总额
        tjShopTotalKpiToday.setTotalPayYd(tjShopTotalKpiLastDate.getTotalPay());//昨日
        if (tjShopTotalKpiLastDate.getTotalPay() != BigDecimal.ZERO && tjShopTotalKpiLastDate.getTotalPay().compareTo(new BigDecimal(0.00)) != 0) {
            tjShopTotalKpiToday.setTotalPayYdDb((tjShopTotalKpiToday.getTotalPay().subtract(tjShopTotalKpiLastDate.getTotalPay())).divide(tjShopTotalKpiLastDate.getTotalPay(),2, BigDecimal.ROUND_HALF_UP));//付款昨日增长率
        }
        else {
            tjShopTotalKpiToday.setTotalPayYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setTotalPayPw(tjShopTotalKpiLastWeek.getTotalPay());//上周
        if (tjShopTotalKpiLastWeek.getTotalPay() != BigDecimal.ZERO && tjShopTotalKpiLastWeek.getTotalPay().compareTo(new BigDecimal(0.00)) != 0) {
            tjShopTotalKpiToday.setTotalPayPwDb((tjShopTotalKpiToday.getTotalPay().subtract(tjShopTotalKpiLastWeek.getTotalPay())).divide(tjShopTotalKpiLastWeek.getTotalPay(),2, BigDecimal.ROUND_HALF_UP));
        }else {
            tjShopTotalKpiToday.setTotalPayPwDb(new BigDecimal(0));
        }


        //访客
        tjShopTotalKpiToday.setVisitorNumYd(tjShopTotalKpiLastDate.getVisitorNum());
        if (tjShopTotalKpiLastDate.getVisitorNum() != 0 ) {
            tjShopTotalKpiToday.setVisitorNumYdDb(new BigDecimal((tjShopTotalKpiToday.getVisitorNum() - tjShopTotalKpiLastDate.getVisitorNum())/(double)tjShopTotalKpiLastDate.getVisitorNum()));
        }else {
            tjShopTotalKpiToday.setVisitorNumYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setVisitorNumPw(tjShopTotalKpiLastWeek.getVisitorNum());
        if (tjShopTotalKpiLastWeek.getVisitorNum() != 0) {
            tjShopTotalKpiToday.setVisitorNumDb(new BigDecimal( (tjShopTotalKpiToday.getVisitorNum() - tjShopTotalKpiLastWeek.getVisitorNum()) / (double)tjShopTotalKpiLastWeek.getVisitorNum()));
        }else {
            tjShopTotalKpiToday.setVisitorNumDb(new BigDecimal(0));
        }

        //浏览量
        tjShopTotalKpiToday.setPvYd(tjShopTotalKpiLastDate.getPv());
        if (tjShopTotalKpiLastDate.getPv() != 0) {
            tjShopTotalKpiToday.setPvYdDb(new BigDecimal( (tjShopTotalKpiToday.getPv() - tjShopTotalKpiLastDate.getPv()) / (double)tjShopTotalKpiLastDate.getPv()));
        }else {
            tjShopTotalKpiToday.setPvYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setPvPw(tjShopTotalKpiLastWeek.getPv());
        if (tjShopTotalKpiLastWeek.getPv() != 0) {
            tjShopTotalKpiToday.setPvPwDb(new BigDecimal( (tjShopTotalKpiToday.getPv() - tjShopTotalKpiLastWeek.getPv()) / (double)tjShopTotalKpiLastWeek.getPv() ));
        }else {
            tjShopTotalKpiToday.setPvPwDb(new BigDecimal(0));
        }

        //买家数
        tjShopTotalKpiToday.setBuyerNumYd(tjShopTotalKpiLastDate.getBuyerNum());
        if (tjShopTotalKpiLastDate.getBuyerNum() != 0) {
            tjShopTotalKpiToday.setBuyerNumYdDb(new BigDecimal((tjShopTotalKpiToday.getBuyerNum() - tjShopTotalKpiLastDate.getBuyerNum()) /(double) tjShopTotalKpiLastDate.getBuyerNum() ));
        }else {
            tjShopTotalKpiToday.setBuyerNumYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setBuyerNumPw(tjShopTotalKpiLastWeek.getBuyerNum());
        if (tjShopTotalKpiLastWeek.getBuyerNum() != 0) {
            tjShopTotalKpiToday.setBuyerNumPwDb(new BigDecimal( (tjShopTotalKpiToday.getBuyerNum() - tjShopTotalKpiLastWeek.getBuyerNum() ) / (double)tjShopTotalKpiLastWeek.getBuyerNum() ));
        }else {
            tjShopTotalKpiToday.setBuyerNumPwDb(new BigDecimal(0));
        }

        //支付订单
        tjShopTotalKpiToday.setOrderNumYd(tjShopTotalKpiLastDate.getOrderNum());
        if (tjShopTotalKpiLastDate.getOrderNum() != 0) {
            tjShopTotalKpiToday.setOrderNumYdDb(new BigDecimal( (tjShopTotalKpiToday.getOrderNum() - tjShopTotalKpiLastDate.getOrderNum()) / (double)tjShopTotalKpiLastDate.getOrderNum() ));
        }else {
            tjShopTotalKpiToday.setOrderNumYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setOrderNumPw(tjShopTotalKpiLastWeek.getOrderNum());
        if (tjShopTotalKpiLastWeek.getOrderNum() != 0) {
            tjShopTotalKpiToday.setOrderNumPwDb(new BigDecimal( (tjShopTotalKpiToday.getOrderNum() - tjShopTotalKpiLastWeek.getOrderNum()) / (double)tjShopTotalKpiLastWeek.getOrderNum() ));
        }else {
            tjShopTotalKpiToday.setOrderNumPwDb(new BigDecimal(0));
        }

        //成交店铺
        tjShopTotalKpiToday.setShopNumYd(tjShopTotalKpiLastDate.getShopNum());
        if (tjShopTotalKpiLastDate.getShopNum() != 0 ) {
            tjShopTotalKpiToday.setShopNumYdDb(new BigDecimal((tjShopTotalKpiToday.getShopNum() - tjShopTotalKpiLastDate.getShopNum()) /(double)  tjShopTotalKpiLastDate.getShopNum() ));
        }else {
            tjShopTotalKpiToday.setShopNumYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setShopNumPw(tjShopTotalKpiLastWeek.getShopNum());
        if (tjShopTotalKpiLastWeek.getShopNum() != 0 ) {
            tjShopTotalKpiToday.setShopNumPwDb(new BigDecimal((tjShopTotalKpiToday.getShopNum() - tjShopTotalKpiLastWeek.getShopNum() ) / (double) tjShopTotalKpiLastWeek.getShopNum()));
        }else {
            tjShopTotalKpiToday.setShopNumPwDb(new BigDecimal(0));
        }

        //成交转换率
        tjShopTotalKpiToday.setCvrYd(tjShopTotalKpiLastDate.getCvr());//昨日
        if (tjShopTotalKpiLastDate.getCvr() != BigDecimal.ZERO && tjShopTotalKpiLastDate.getCvr().compareTo(new BigDecimal(0.00)) != 0) {
            tjShopTotalKpiToday.setCvrYdDb(((tjShopTotalKpiToday.getCvr().subtract(tjShopTotalKpiLastDate.getCvr())).divide(tjShopTotalKpiLastDate.getCvr(),2, BigDecimal.ROUND_HALF_UP)));//付款昨日增长率
        }else {
            tjShopTotalKpiToday.setCvrYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setCvrPw(tjShopTotalKpiLastWeek.getCvr());//上周
        if (tjShopTotalKpiLastWeek.getCvr() != BigDecimal.ZERO && tjShopTotalKpiLastWeek.getCvr().compareTo(new BigDecimal(0.00)) != 0){
            tjShopTotalKpiToday.setCvrPwDb(((tjShopTotalKpiToday.getCvr().subtract(tjShopTotalKpiLastWeek.getCvr())).divide(tjShopTotalKpiLastWeek.getCvr(),2, BigDecimal.ROUND_HALF_UP)));
        }else {
            tjShopTotalKpiToday.setCvrPwDb(new BigDecimal(0));
        }


        //客单价
        tjShopTotalKpiToday.setPctYd(tjShopTotalKpiLastDate.getPct());
        if (tjShopTotalKpiLastDate.getPct() != BigDecimal.ZERO && tjShopTotalKpiLastDate.getPct().compareTo(new BigDecimal(0.00)) != 0) {
            tjShopTotalKpiToday.setPctYdDb(((tjShopTotalKpiToday.getPct().subtract(tjShopTotalKpiLastDate.getPct())).divide(tjShopTotalKpiLastDate.getPct(),2, BigDecimal.ROUND_HALF_UP)));
        }else {
            tjShopTotalKpiToday.setPctYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setPctYw(tjShopTotalKpiLastWeek.getPct());
        if (tjShopTotalKpiLastWeek.getPct() != BigDecimal.ZERO && tjShopTotalKpiLastWeek.getPct().compareTo(new BigDecimal(0.00)) != 0){
            tjShopTotalKpiToday.setPctYwDb(((tjShopTotalKpiToday.getPct().subtract(tjShopTotalKpiLastWeek.getPct())).divide(tjShopTotalKpiLastWeek.getPct(), 2, BigDecimal.ROUND_HALF_UP)));
        }else {
            tjShopTotalKpiToday.setPctYwDb(new BigDecimal(0));
        }

        //退款金额
        tjShopTotalKpiToday.setTkjeYd(tjShopTotalKpiLastDate.getTkje());
        if (tjShopTotalKpiLastDate.getTkje() != BigDecimal.ZERO && tjShopTotalKpiLastDate.getTkje().compareTo(new BigDecimal(0.00)) != 0) {
            tjShopTotalKpiToday.setTkjeYdDb(((tjShopTotalKpiToday.getTkje().subtract(tjShopTotalKpiLastDate.getTkje())).divide(tjShopTotalKpiLastDate.getTkje(), 2, BigDecimal.ROUND_HALF_UP)));
        }else {
            tjShopTotalKpiToday.setTkjeYdDb(new BigDecimal(0));
        }
        tjShopTotalKpiToday.setTkjePw(tjShopTotalKpiLastWeek.getTkje());
        if (tjShopTotalKpiLastWeek.getTkje() != BigDecimal.ZERO && tjShopTotalKpiLastWeek.getTkje().compareTo(new BigDecimal(0.00)) != 0){
            tjShopTotalKpiToday.setTkjePwDb((tjShopTotalKpiToday.getTkje().subtract(tjShopTotalKpiLastWeek.getTkje()).divide(tjShopTotalKpiLastWeek.getTkje(),2, BigDecimal.ROUND_HALF_UP)));
        }else {
            tjShopTotalKpiToday.setTkjePwDb(new BigDecimal(0));
        }


        tjShopTotalKpiToday.setIsValid("T");
        tjShopTotalKpiToday.setDelFlag("F");
        try {
            if (tjShopTotalKpiService.get(Conds.get().eq("date_created",creatDate)) != null){   //修改
                Date savedate = new Date();
                savedate = matter1.parse(creatDate);
                tjShopTotalKpiToday.setLastUpdated(savedate);
                tjShopTotalKpiToday.setId(tjShopTotalKpiService.get(Conds.get().eq("date_created",creatDate)).getId());
                tjShopTotalKpiService.update(tjShopTotalKpiToday);
            }else{                                                                                   //增加
                Date savedate = new Date();
                savedate = matter1.parse(creatDate);
                tjShopTotalKpiToday.setDateCreated(savedate);
                tjShopTotalKpiToday.setLastUpdated(savedate);
                tjShopTotalKpiToday.setStatDate(creatDate);
                tjShopTotalKpiToday.setId(IdGenerator.getDefault().nextId()+"");
                tjShopTotalKpiService.insert(tjShopTotalKpiToday);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("DateSource",tjShopTotalKpiToday);
        return map;
    }

    public TjShopTotalKpi todayInfo (TjShopTotalKpiParam tjShopTotalKpiParam) {
        Date date = new Date();
        SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
        String creatDate = matter1.format(date);
        tjShopTotalKpiParam.setStartDate(creatDate);

        TjShopTotalKpi tjShopTotalKpi = getLastTimeInfoFromTable(tjShopTotalKpiParam);

        return  tjShopTotalKpi;    //今天
    }


    public TjShopTotalKpi lastDateInfo(TjShopTotalKpiParam tjShopTotalKpiParam){                     //昨天

        Date date = new Date();
        SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
        String creatDate = matter1.format(date);
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往后减少一天.整数往后推,负数往前移动
        String lastDate = matter1.format(calendar.getTime());//这个时间就是日期往前推一天的结果

        tjShopTotalKpiParam.setStartDate(lastDate);
        tjShopTotalKpiParam.setEndDate(creatDate);

        TjShopTotalKpi tjShopTotalKpi = getLastTimeInfoFromTable(tjShopTotalKpiParam);   //保存昨日数据
        try {
            Date savedate = new Date();
            savedate = matter1.parse(lastDate);
            tjShopTotalKpi.setDateCreated(savedate);
            tjShopTotalKpi.setStatDate(lastDate);
            tjShopTotalKpi.setLastUpdated(savedate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }

        return tjShopTotalKpi;

    }

    public TjShopTotalKpi lastWeekInfo(TjShopTotalKpiParam tjShopTotalKpiParam){                             //上周
        Date date = new Date();
        SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
        String creatDate = matter1.format(date);
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);
        calendar.add(calendar.DATE,-7);//把日期往后减少一天.整数往后推,负数往前移动
        String lastWeek = matter1.format(calendar.getTime());//这个时间就是日期往前推一天的结果
        calendar.add(calendar.DATE,-6);//把日期往后减少一天.整数往后推,负数往前移动
        String lastWeekEnd = matter1.format(calendar.getTime());//这个时间就是日期往前推一天的结果

        tjShopTotalKpiParam.setStartDate(lastWeek);
        tjShopTotalKpiParam.setEndDate(lastWeekEnd);

        TjShopTotalKpi tjShopTotalKpi = getLastTimeInfoFromTable(tjShopTotalKpiParam);   //保存上周数据
        try {
            Date savedate = new Date();
            savedate = matter1.parse(lastWeek);
            tjShopTotalKpi.setDateCreated(savedate);
            tjShopTotalKpi.setStatDate(lastWeek);
            tjShopTotalKpi.setLastUpdated(savedate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return tjShopTotalKpi;

    }


    public TjShopTotalKpi getLastTimeInfoFromTable (TjShopTotalKpiParam tjShopTotalKpiParam ) {
        TjShopTotalKpi tjShopTotalKpi = new TjShopTotalKpi();

        try {
            tjShopTotalKpiParam.setPresellStatus("totalShop");
            String countShop = FreeMarkerHelper.getValueFromTpl("sql/tjShop/countTradeShopOrOrderOrBuyerOrUserOrPayedOrVisitor.sql", tjShopTotalKpiParam);
            Integer totalShop = jdbcTemplate.queryForObject(countShop, Integer.class); //成交商店总数
            tjShopTotalKpi.setShopNum(totalShop != null ? totalShop.intValue() : 0);

            tjShopTotalKpiParam.setPresellStatus("totalOrder");
            String countOrder = FreeMarkerHelper.getValueFromTpl("sql/tjShop/countTradeShopOrOrderOrBuyerOrUserOrPayedOrVisitor.sql", tjShopTotalKpiParam);
            Integer totalOrder = jdbcTemplate.queryForObject(countOrder, Integer.class); //成交订单总数
            tjShopTotalKpi.setOrderNum(totalOrder != null ? totalOrder.intValue() : 0);

            tjShopTotalKpiParam.setPresellStatus("totalBuyer");
            String countBuyer = FreeMarkerHelper.getValueFromTpl("sql/tjShop/countTradeShopOrOrderOrBuyerOrUserOrPayedOrVisitor.sql", tjShopTotalKpiParam);
            Long totalBuyer = jdbcTemplate.queryForLong(countBuyer);
//            Integer totalBuyer = jdbcTemplate.queryForObject(countBuyer, Integer.class); //成交订单买家总数
            tjShopTotalKpi.setBuyerNum(totalBuyer != null ? totalBuyer.intValue() : 0);

            tjShopTotalKpiParam.setPresellStatus("totalUser");
            String countUser = FreeMarkerHelper.getValueFromTpl("sql/tjShop/countTradeShopOrOrderOrBuyerOrUserOrPayedOrVisitor.sql", tjShopTotalKpiParam);
            //Integer totalUser = jdbcTemplate.queryForObject(countUser, Integer.class); //浏览量
            Long totalUser = jdbcTemplate.queryForLong(countUser);
            tjShopTotalKpi.setPv(totalUser != null ? totalUser.intValue() : 0);

            tjShopTotalKpiParam.setPresellStatus("totalPayed");
            String countPayed = FreeMarkerHelper.getValueFromTpl("sql/tjShop/countTradeShopOrOrderOrBuyerOrUserOrPayedOrVisitor.sql", tjShopTotalKpiParam);
            BigDecimal totalPayed = jdbcTemplate.queryForObject(countPayed, BigDecimal.class); //支付总额
            tjShopTotalKpi.setTotalPay(totalPayed);

            tjShopTotalKpiParam.setPresellStatus("totalVisitor");
            String countVisitor = FreeMarkerHelper.getValueFromTpl("sql/tjShop/countTradeShopOrOrderOrBuyerOrUserOrPayedOrVisitor.sql", tjShopTotalKpiParam);
            //Integer totalVisitor = jdbcTemplate.queryForObject(countShop, Integer.class); //访客量
            Long totalVisitor = jdbcTemplate.queryForLong(countVisitor);
            tjShopTotalKpi.setVisitorNum(totalVisitor != null ? totalVisitor.intValue() : 0);

            tjShopTotalKpiParam.setPresellStatus("tuiKuan");
            String countTkje = FreeMarkerHelper.getValueFromTpl("sql/tjShop/countTradeShopOrOrderOrBuyerOrUserOrPayedOrVisitor.sql", tjShopTotalKpiParam);
            BigDecimal totaltkje = jdbcTemplate.queryForObject(countTkje, BigDecimal.class); //退款金额
            tjShopTotalKpi.setTkje(totaltkje);

            if (tjShopTotalKpi.getVisitorNum() != 0) {
                tjShopTotalKpi.setCvr(new BigDecimal(Double.toString(tjShopTotalKpi.getOrderNum() / (double) tjShopTotalKpi.getVisitorNum()))); //成交转换率
            }else
                tjShopTotalKpi.setCvr(BigDecimal.ZERO);
           if (tjShopTotalKpi.getOrderNum() != 0) {
               tjShopTotalKpi.setPct((tjShopTotalKpi.getTotalPay()).divide(new BigDecimal(tjShopTotalKpi.getOrderNum()), 2, BigDecimal.ROUND_HALF_UP));// 客单价
           }else
               tjShopTotalKpi.setPct(BigDecimal.ZERO);
        }catch (Exception e){
            logger.info(e.getMessage());
        }

        return tjShopTotalKpi;
    }

    private TjShopTotalKpiService getTjShopTotalKpiService(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return tjPurchaseTotalKpiService;
        }else{
            return tjTradeTotalKpiService;
        }

    }

    private String getTjShopParam(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return "purchase";
        }else{
            return "trade";
        }
    }
}
