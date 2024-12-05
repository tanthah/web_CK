package Email;

import javax.mail.MessagingException;

public class Email {

    public static boolean sendEmail(String email) {
        String to = email;
        String from = "thanhndt2004@gmail.com";
        String subject = "Welcome to our email list";
        String body =  "kính gửi"+ email +" \n"
                +"Chúng tôi xin chân thành cảm ơn quý khách đã tin tưởng và đăng ký tài khoản trên \n"
                + "Tài khoản của quý khách đã được kích hoạt thành công, và giờ đây quý khách có thể:\n" 
                +"Dễ dàng đặt món từ hàng trăm nhà hàng đối tác.\n"
                +"Nhận ưu đãi độc quyền chỉ dành cho thành viên.\n" 
                +"Theo dõi trạng thái đơn hàng mọi lúc mọi nơi. "
                + "Nếu cần hỗ trợ thêm, quý khách vui lòng liên hệ "
                +"với chúng tôi qua [số hotline hoặc email hỗ trợ]."
                +" Đội ngũ của chúng tôi luôn sẵn sàng phục vụ quý khách!";
        boolean isBodyHTML = false;

        try {
            MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
            return true; // Gửi email thành công
        } catch (MessagingException e) {
            return false; // Gửi email thất bại
        }
    }
}
