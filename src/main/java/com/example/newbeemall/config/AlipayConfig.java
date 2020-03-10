package com.example.newbeemall.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

@Configuration
public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	/*// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021001140683839";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDMu1Ui+/ufh+CSiLYq2vhOV5H7G2UOy/UGV2O9z8v7Ktb7PHa7yozUh/NOiDUAqmXfxhjdOlwodS3ojI13J7uW24vAozo2coLQF9wDUFovz+lv0gV87nBeZyq+GyO5wampWlXyV5CP6ZXi0eV/oBFjFU5EcQSBXf+ti3/zLfjKSDCHwrCgowW6g2EyIol8Ne6b6E8MyoCadstdFZ/ayqlI1h8GaR7Z/icjQ70Clh5cECCTy9DdHDSKf6gnZfiZCh/1KQeAz9Vq+ZlJ6xrvbfi/Gj1RLwgIAY1Uxf+6m6hIpRh38fgfihjskzK4PBcdT+KvWzb7kSJa2IuQJdUWTvZnAgMBAAECggEBAI5Ns+qiPY7wkj8oOX7mPuutYLGqo/6587EZsyFvOzh02YM9zsXPDz521L6EXFr/FdqJxiSXeRFCCcv3TilBe9lq9uPKUR8hTx23R90hz94l5Pstf1UMIxzrFsGW+akXHIezW8Qb60astNs4zA1+xDcbyxA1gk0tehc1vJTaInDuqFLBG4vbf+z2jpojYNTCa6l2y/TGlLHN9iNrzMMQREH8qfdSBG+gdb9BeDPtAX7B2vazs0uuip98BCmY7g9eRUDu3WJowJD3fthT/3vWBmrUUmnlMmldwi5g0HiZ1LwVZ7Jyslv0zWyuRVjO1Rom2vrBLOXrceiJ+6VmN+GlBiECgYEA5SO/Ch/r2Jtb++ipZhNNbAsCbDIp55bAhKLPeyy8ongWGgulfBbqNA+s5bX7EdxmydGnEbIF+1bjpOKtr/P11TV+8Tmq1SNLfxLm41N5ooeWokbQkiI+fYyqdnuF2BVYnYEx6Zq4FRyFLdrbAutly8RmkKrK/zWEGJJaSTGlZjECgYEA5LshYhqkcON6118H7VRVJ+Em9K1IlqyrHwnLfsFY9agboObY/hSiKatLlVTcKdRzk1klAsofi4a7/Gc2vjigQBNTe9OfYlva3BmWGQbrXEl+TgUBFdtrp0TbRMQ5DDcKb/YIx0Lo/gKlasB1ZSMvYZoUcWeBFmIeww4PRPjFSBcCgYEAuqmgGxNeAZquGEy0kt7ulzFwy8shr/Ny6ZnoA6OyF3hUKOuL58aTC84pPwg8e5Rcd9Q+kX/At7tUvyGZ9Fh8zy9BNimQLQkPm3k+dbmuwORLpe6iQil7VrOcueBubFUXV1fD3VWY2DnflDdRatNwncpk8zzvGW/zKEExI6c54kECgYEAm3zWUcF4vcD19ciFNAGbLdPxzmypprvUS/I1ooI/Pie8OGIVaouOPTNT2zznuYvR0X3NGhta3FeIHSnw3djSnxxEKS4P8CYvHmZKUdMRVhgjw1fFiB00Dm5RfGrDuRkFgFzJVOOYTWxxFKOmak1D3V6HvRu2dgfXj5NUL/fI7JsCgYEA4o9CytxrZbbPm5O3xqEWipdO23rYDbv9jeT3oS6WIe9s7NePBSkOGeT8nPGOWYf3y1xygC7PCB0qSHVxS8zlyvTT5dG1z/UL8tKoISLJ506Tyq9UkLtnQifGnRrpcs4AvNVnwvjpjDKwUlJkL85CUwTmZxjo0PJlJfXe5xQvMeQ=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkeA4mB5ieXeWsypXs4TZr2qyYdx1epBfktJtSpKpI3ESp2tRRJrl38q9ZX0OFYshfgkNy9+IXEMWoYBWhcgyONd8PXvYfve/SoCdu7shuMVw+VfHRGdCF/aOow2qX+O3kroEf+8z1r8emhOn+HthBJj0YgH8rEWFblFLgung51922ANTFnAxSEvfDr0g/zUB6M1b2MSuOA1UOaKQEaEUSi14lSY/KFyeO5mMfSegAae9Q7iCHB1XxPJCkHJvpmQyP1absnSpaby2cCLeG2qPL3dBMJ0Abgp3mWLy2stl05o6o5Pmx2BJAeYhAfyoki/LclyYkITeckr50l7A3hy8AwIDAQAB";*/

	public static String app_id="2016101700707383";
    public static String merchant_private_key="MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDMu1Ui+/ufh+CSiLYq2vhOV5H7G2UOy/UGV2O9z8v7Ktb7PHa7yozUh/NOiDUAqmXfxhjdOlwodS3ojI13J7uW24vAozo2coLQF9wDUFovz+lv0gV87nBeZyq+GyO5wampWlXyV5CP6ZXi0eV/oBFjFU5EcQSBXf+ti3/zLfjKSDCHwrCgowW6g2EyIol8Ne6b6E8MyoCadstdFZ/ayqlI1h8GaR7Z/icjQ70Clh5cECCTy9DdHDSKf6gnZfiZCh/1KQeAz9Vq+ZlJ6xrvbfi/Gj1RLwgIAY1Uxf+6m6hIpRh38fgfihjskzK4PBcdT+KvWzb7kSJa2IuQJdUWTvZnAgMBAAECggEBAI5Ns+qiPY7wkj8oOX7mPuutYLGqo/6587EZsyFvOzh02YM9zsXPDz521L6EXFr/FdqJxiSXeRFCCcv3TilBe9lq9uPKUR8hTx23R90hz94l5Pstf1UMIxzrFsGW+akXHIezW8Qb60astNs4zA1+xDcbyxA1gk0tehc1vJTaInDuqFLBG4vbf+z2jpojYNTCa6l2y/TGlLHN9iNrzMMQREH8qfdSBG+gdb9BeDPtAX7B2vazs0uuip98BCmY7g9eRUDu3WJowJD3fthT/3vWBmrUUmnlMmldwi5g0HiZ1LwVZ7Jyslv0zWyuRVjO1Rom2vrBLOXrceiJ+6VmN+GlBiECgYEA5SO/Ch/r2Jtb++ipZhNNbAsCbDIp55bAhKLPeyy8ongWGgulfBbqNA+s5bX7EdxmydGnEbIF+1bjpOKtr/P11TV+8Tmq1SNLfxLm41N5ooeWokbQkiI+fYyqdnuF2BVYnYEx6Zq4FRyFLdrbAutly8RmkKrK/zWEGJJaSTGlZjECgYEA5LshYhqkcON6118H7VRVJ+Em9K1IlqyrHwnLfsFY9agboObY/hSiKatLlVTcKdRzk1klAsofi4a7/Gc2vjigQBNTe9OfYlva3BmWGQbrXEl+TgUBFdtrp0TbRMQ5DDcKb/YIx0Lo/gKlasB1ZSMvYZoUcWeBFmIeww4PRPjFSBcCgYEAuqmgGxNeAZquGEy0kt7ulzFwy8shr/Ny6ZnoA6OyF3hUKOuL58aTC84pPwg8e5Rcd9Q+kX/At7tUvyGZ9Fh8zy9BNimQLQkPm3k+dbmuwORLpe6iQil7VrOcueBubFUXV1fD3VWY2DnflDdRatNwncpk8zzvGW/zKEExI6c54kECgYEAm3zWUcF4vcD19ciFNAGbLdPxzmypprvUS/I1ooI/Pie8OGIVaouOPTNT2zznuYvR0X3NGhta3FeIHSnw3djSnxxEKS4P8CYvHmZKUdMRVhgjw1fFiB00Dm5RfGrDuRkFgFzJVOOYTWxxFKOmak1D3V6HvRu2dgfXj5NUL/fI7JsCgYEA4o9CytxrZbbPm5O3xqEWipdO23rYDbv9jeT3oS6WIe9s7NePBSkOGeT8nPGOWYf3y1xygC7PCB0qSHVxS8zlyvTT5dG1z/UL8tKoISLJ506Tyq9UkLtnQifGnRrpcs4AvNVnwvjpjDKwUlJkL85CUwTmZxjo0PJlJfXe5xQvMeQ=";
    public static String alipay_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhl/jDZdzVIpCcTD44hpjPyTp1Recsd17AgJ9whSWs4hVUOnKnITznp52yb3sjSg2QetOBlfUr6VA0b/BNWh8BlyrKOqxbFbzTr/au7OYltzQpwyg7lyzXjl5ncP5qClJk1TsBcfK3oFC9Lmcg8T6L5h07pUWRG00Q6uhTNEIAriz6d30YS02uqyhOEFleoenudoLR375m75+yBfZNNFCkJcSjhhvCJDgYR9AheRIMxfqHMVpULi9UvdNtfIInK5pIVXBXvrO/eRGrNHEW0b0Dx+vyLlZQz+OIfngah4H4+jaxo+dyTPEYiKH21yzKmFqR8sXbm6uThvObx42L0ngYQIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8081/alipay/notify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8081/alipay/return";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";

    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
    }
//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

