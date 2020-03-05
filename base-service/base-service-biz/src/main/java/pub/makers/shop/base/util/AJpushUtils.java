//package pub.makers.shop.base.util;
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.dubbo.common.json.JSONObject;
//
//import cn.jiguang.common.resp.APIConnectionException;
//import cn.jiguang.common.resp.APIRequestException;
//import cn.jpush.api.JPushClient;
//import cn.jpush.api.push.model.Message;
//import cn.jpush.api.push.model.Options;
//import cn.jpush.api.push.model.Platform;
//import cn.jpush.api.push.model.PushPayload;
//import cn.jpush.api.push.model.audience.Audience;
//import cn.jpush.api.push.model.audience.AudienceTarget;
//import cn.jpush.api.push.model.notification.AndroidNotification;
//import cn.jpush.api.push.model.notification.IosNotification;
//import cn.jpush.api.push.model.notification.Notification;
//import pub.makers.shop.base.utils.IdGenerator;
//
///**
// * Created by Administrator on 2016/12/14.
// */
//@Component
//@PropertySource("classpath:/config/jpush_debug.properties")
//public class AJpushUtils {
//
//    @Value("${debug}")
//    private String debug;
//    @Value("${appKey}")
//    private String appKey; // 必填，例如466f7032ac604e02fb7bda89
//    @Value("${masterSecret}")
//    private String masterSecret ;// "13ac09b17715bd117163d8a1";//必填，每个应用都对应一个masterSecret
//    public JPushClient jpushClient;
//    public static Integer isDebug;
//    static Logger logger = LoggerFactory.getLogger(AJpushUtils.class);
//    /**
//     * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
//     * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
//     * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒)。
//     */
//    private static long timeToLive = 60 * 60 * 24;
//
//    public static void main(String[] args) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("msg_id",IdGenerator.getDefault().nextId());
//        jsonObject.put("icon","http://odv2wjw8i.bkt.clouddn.com/1481791118598d21d221.png");
//        jsonObject.put("title","系统消息");
//        jsonObject.put("content","新的早晨开始了");
//        jsonObject.put("deletable",1);
//        jsonObject.put("type",0);
//        jsonObject.put("date",System.currentTimeMillis());
//        JSONObject json = new JSONObject();
//        json.put("reason","");
//        json.put("request_id","");
//        jsonObject.put("extra",json);
//        PushPayload payload = PushPayload.newBuilder()
//                .setOptions(Options.newBuilder()
//                        .setApnsProduction(true)  //是否生产环境
//                        .build())
//                .setPlatform(Platform.all())
//                .setAudience(Audience.all())
//                .setMessage(Message.newBuilder().setTitle("测试").setMsgContent(jsonObject.toString()).build()).build();
//        try {
//             new AJpushUtils().jpushClient.sendPush(payload);
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        } catch (APIRequestException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * youchalian推送指定用户id消息
//     * @param userId
//     * @param msg_content
//     */
//    public void sendByAlias(String userId,String msg_content, String type, String targetId) {
//        if(isDebug==0) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("msg_id", IdGenerator.getDefault().nextId());
//            jsonObject.put("icon", "http://odv2wjw8i.bkt.clouddn.com/1481791118598d21d221.png");
//            jsonObject.put("title", "系统消息");
//            jsonObject.put("content", msg_content);
//            jsonObject.put("deletable", 1);
//            jsonObject.put("type", 0);
//            jsonObject.put("date", System.currentTimeMillis());
//            JSONObject json = new JSONObject();
//            json.put("reason", type);
//            json.put("request_id", targetId);
//            jsonObject.put("extra", json);
//            try {
//                jpushClient.sendAndroidMessageWithAlias("系统消息", jsonObject.toString(), userId);
//            } catch (APIConnectionException e) {
//                e.printStackTrace();
//            } catch (APIRequestException e) {
//                e.printStackTrace();
//            }
//            try {
//                jpushClient.sendIosMessageWithAlias("系统消息", jsonObject.toString(), userId);
//            } catch (APIConnectionException e) {
//                e.printStackTrace();
//            } catch (APIRequestException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * youchalian 推送所有用户消息
//     * @param msg_content
//     */
//    public void sendAll(String msg_content, String type, String targetId) {
//        if(isDebug==0) {
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("msg_id",IdGenerator.getDefault().nextId());
//                jsonObject.put("icon","http://odv2wjw8i.bkt.clouddn.com/1481791118598d21d221.png");
//                jsonObject.put("title","系统消息");
//                jsonObject.put("content",msg_content);
//                jsonObject.put("deletable",1);
//                jsonObject.put("type",0);
//                jsonObject.put("date",System.currentTimeMillis());
//                JSONObject json = new JSONObject();
//                json.put("reason", type);
//                json.put("request_id", targetId);
//                jsonObject.put("extra",json);
//                jpushClient.sendMessageAll(jsonObject.toString());
//            } catch (APIConnectionException e) {
//                e.printStackTrace();
//            } catch (APIRequestException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static PushPayload buildPushObject_android_and_ios(String notification_title, String msg_title, String msg_content, String extrasparam) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.android_ios())
//                .setAudience(Audience.all())
//                .setNotification(Notification.newBuilder()
//                        .setAlert(notification_title)
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//                                .setAlert(notification_title)
//                                .setTitle(notification_title)
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("androidNotification extras key",extrasparam)
//                                .build()
//                        )
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                //传一个IosAlert对象，指定apns title、title、subtitle等
//                                .setAlert(notification_title)
//                                //直接传alert
//                                //此项是指定此推送的badge自动加1
//                                .incrBadge(1)
//                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound("sound.caf")
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("iosNotification extras key",extrasparam)
//                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                // .setContentAvailable(true)
//
//                                .build()
//                        )
//                        .build()
//                )
//                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(msg_content)
//                        .setTitle(msg_title)
//                        .addExtra("message extras key",extrasparam)
//                        .build())
//
//                .setOptions(Options.newBuilder()
//                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        .setApnsProduction(false)
//                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
//                        .setTimeToLive(86400)
//                        .build()
//                )
//                .build();
//    }
//    /**
//     *  进行推送的关键在于构建一个 PushPayload 对象。以下示例一般的构建对象的用法。
//     快捷地构建推送对象：所有平台，所有设备，内容为 content 的通知。
//     */
//    public static PushPayload buildPushObject_all_all_alert(String content) {
//        return PushPayload.alertAll(content);
//    }
//
//    /**
//     * 构建推送对象：所有平台，推送目标是别名为 user，通知内容为 content。
//     * @return
//     */
//    public static PushPayload buildPushObject_all_alias_alert(String user,String content) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.alias(user))
//                .setNotification(Notification.alert(content))
//                .build();
//    }
//
//    /**
//     * 构建推送对象：平台是 Android，目标为 user 的设备，内容是 Android 通知 content，并且标题为 TITLE。
//     * @return
//     */
//    public static PushPayload buildPushObject_android_tag_alertWithTitle(String user,String title,String content) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.android())
//                .setAudience(Audience.tag(user))
//                .setNotification(Notification.android(content, title, null))
//                .build();
//    }
//
//    /**
//     * 构建推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的交集，推送内容同时包括通知与消息
//     * - 通知信息是 ALERT，角标数字为 5，通知声音为 "happy"，
//     * 并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。
//     * 通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。
//     * APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
//     * @return
//     */
//    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String alert,String content) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.ios())
//                .setAudience(Audience.tag_and("tag1", "tag_all"))
//                .setNotification(Notification.newBuilder()
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                .setAlert(alert)
//                                .setBadge(5)
//                                .setSound("happy")
//                                .addExtra("from", "JPush")
//                                .build())
//                        .build())
//                .setMessage(Message.content(content))
//                .setOptions(Options.newBuilder()
//                        .setApnsProduction(true)
//                        .build())
//                .build();
//    }
//
//    /**
//     * 构建推送对象：平台是 Andorid 与 iOS，推送目标是
//     * （"tag1" 与 "tag2" 的并集）交（"alias1" 与 "alias2" 的并集），
//     * 推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
//     * @return
//     */
//    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.android_ios())
//                .setAudience(Audience.newBuilder()
//                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
//                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
//                        .build())
//                .setMessage(Message.newBuilder()
//                        .setMsgContent("MSG_CONTENT")
//                        .addExtra("from", "JPush")
//                        .build())
//                .build();
//    }
//
//    private static PushPayload buildPushObject_all_registrationId_alertWithTitle(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
//
//        System.out.println("----------buildPushObject_all_all_alert");
//        //创建一个IosAlert对象，可指定APNs的alert、title等字段
//        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
//
//        return PushPayload.newBuilder()
//                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.all())
//                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                .setAudience(Audience.registrationId(registrationId))
//                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        //指定当前推送的android通知
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//
//                                .setAlert(notification_title)
//                                .setTitle(notification_title)
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("androidNotification extras key",extrasparam)
//
//                                .build())
//                        //指定当前推送的iOS通知
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                //传一个IosAlert对象，指定apns title、title、subtitle等
//                                .setAlert(notification_title)
//                                //直接传alert
//                                //此项是指定此推送的badge自动加1
//                                .incrBadge(1)
//                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound("sound.caf")
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("iosNotification extras key",extrasparam)
//                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                //取消此注释，消息推送时ios将无法在锁屏情况接收
//                                // .setContentAvailable(true)
//
//                                .build())
//
//
//                        .build())
//                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                .setMessage(Message.newBuilder()
//
//                        .setMsgContent(msg_content)
//
//                        .setTitle(msg_title)
//
//                        .addExtra("message extras key",extrasparam)
//
//                        .build())
//
//                .setOptions(Options.newBuilder()
//                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        .setApnsProduction(false)
//                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
//                        .setTimeToLive(86400)
//
//                        .build())
//
//                .build();
//
//    }
//
//    private static PushPayload buildPushObject_android_all_alertWithTitle(String notification_title, String msg_title, String msg_content, String extrasparam) {
//        System.out.println("----------buildPushObject_android_registrationId_alertWithTitle");
//        return PushPayload.newBuilder()
//                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.android())
//                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                .setAudience(Audience.all())
//                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        //指定当前推送的android通知
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//                                .setAlert(notification_title)
//                                .setTitle(notification_title)
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("androidNotification extras key",extrasparam)
//                                .build())
//                        .build()
//                )
//                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(msg_content)
//                        .setTitle(msg_title)
//                        .addExtra("message extras key",extrasparam)
//                        .build())
//
//                .setOptions(Options.newBuilder()
//                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        .setApnsProduction(false)
//                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
//                        .setTimeToLive(86400)
//                        .build())
//                .build();
//    }
//
//    private static PushPayload buildPushObject_ios_all_alertWithTitle( String notification_title, String msg_title, String msg_content, String extrasparam) {
//        System.out.println("----------buildPushObject_ios_registrationId_alertWithTitle");
//        return PushPayload.newBuilder()
//                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.all())
//                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                .setAudience(Audience.alias("315632689586937856"))
//                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        //指定当前推送的android通知
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                //传一个IosAlert对象，指定apns title、title、subtitle等
//                                .setAlert(notification_title)
//                                //直接传alert
//                                //此项是指定此推送的badge自动加1
//                                .incrBadge(1)
//                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound("sound.caf")
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("iosNotification extras key",extrasparam)
//                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                // .setContentAvailable(true)
//
//                                .build())
//                        .build()
//                )
//                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(msg_content)
//                        .setTitle(msg_title)
//                        .addExtra("message extras key",extrasparam)
//                        .build())
//
//                .setOptions(Options.newBuilder()
//                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        .setApnsProduction(false)
//                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
//                        .setTimeToLive(86400)
//                        .build())
//                .build();
//    }
//
//    @PostConstruct
//    public void init(){
//        jpushClient = new JPushClient(masterSecret, appKey,5);
//        isDebug = Integer.parseInt(debug);
//        logger.info("jpush_appKey:"+appKey);
//        logger.info("masterSecret:"+masterSecret);
//    }
//}
