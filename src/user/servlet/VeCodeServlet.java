package user.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/veCodeServlet")
public class VeCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 100;
        int height = 50;
        // 1.图片的宽度,图片的高度,图片的颜色类型RGB
        BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 2.通过图片对象获取画笔
        Graphics graphics = bImage.getGraphics();
        // 3.把画笔颜色设置为粉色
        graphics.setColor(Color.PINK);
        // 4.填充背景颜色
        graphics.fillRect(0,0,width,height);
        // 5.把画笔颜色设置为黑色
        graphics.setColor(Color.BLACK);
        // 6.设置字体
        graphics.setFont(new Font("黑体",Font.BOLD,20));
        //=============================================================//

        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        int code = 0;
        for (int i = 0; i < 4; i++) {
            code = ran.nextInt(10);//随机生成0到9的数字

            //把随机的数添加到图片中
            graphics.drawString(code + "", 20 + 20 * i, 30);
            sb.append(code);
        }
      /*  System.out.println("随机生成的验证码："+sb);*/

        //把验证码存进session里面
        HttpSession session = request.getSession();
        session.setAttribute("code",sb.toString());

        request.getSession().setAttribute("vecode",sb.toString());

        // 8.改变画笔颜色为绿色
        graphics.setColor(Color.GREEN);
        // 9.绘制干扰线
        for (int i = 0;i < 10;i++){
            // 干扰线x轴的开始,y轴的开始,x轴的结束,y轴的结束
            graphics.drawLine(ran.nextInt(width),ran.nextInt(height),ran.nextInt(width),ran.nextInt(height));
        }
        // 10.把绘制好的验证码图片通过 IO 流发送到请求方
        ImageIO.write(bImage,"jpg",response.getOutputStream());
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);

    }
}