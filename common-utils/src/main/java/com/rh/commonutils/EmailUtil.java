package com.rh.commonutils;

import com.rh.commonutils.dto.SendEmailDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/3/4
 **/
public class EmailUtil {

    public static void sendEmail(SendEmailDTO sendEmailDTO) throws EmailException {
        HtmlEmail email = new HtmlEmail();//可以发送html类型的邮件
        email.setHostName(sendEmailDTO.getEmailHost());//指定要使用的邮件服务器
        email.setAuthentication(sendEmailDTO.getEmailName(), sendEmailDTO.getEmailPwd());//使用的邮件服务器需提供已注册的用户名、密码
        email.setCharset(sendEmailDTO.getEmailCharset());
        email.setFrom(sendEmailDTO.getEmailFrom());//设置发件人
        email.addTo(sendEmailDTO.getEmailTo());//设置收件人

        // 需要开启 SSL 服务
//        email.setSSLOnConnect(true);
//        email.setSSLCheckServerIdentity(true);
//        email.setStartTLSEnabled(true);
//        email.setStartTLSRequired(true);
//        email.setSslSmtpPort("465");
        if (StringUtils.isNotEmpty(sendEmailDTO.getEmailCcs())) {
            String[] emailCcArr = sendEmailDTO.getEmailCcs().split(",");
            for (String emailCc : emailCcArr) {
                if (StringUtils.isNotEmpty(emailCc)) {
                    email.addCc(emailCc); // 设置抄送人
                }
            }
        }
        HSSFWorkbook workbook = ExcelUtil.exportEmailData(sendEmailDTO.getData());
        email.setSubject(sendEmailDTO.getContext());//设置主题
        String filePathName = "/opt/data/xx.xls";
        File file = new File(filePathName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        email.attach(file);// 添加附件
        email.setTextMsg(sendEmailDTO.getContext());
        email.setSentDate(new Date());
        //发送邮件
        email.send();
    }
}
