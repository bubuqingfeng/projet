package user.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import user.dao.GoodsDao;
import user.dao.GoodsDaoImpl;
import user.domain.Goods;
import user.domain.PageBean;
import user.servise.GoodsService;
import user.servise.impl.GoodsServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/goodsListServlet")
public class GoodsListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String method = request.getParameter("method");
        if ("list".equals(method)) {
            list(request, response);
        } else if ("toAdd".equals(method)) {
            toAdd(request, response);
        } else if ("add".equals(method)) {
            add(request, response);
        } else if ("toEdit".equals(method)) {
            toEdit(request, response);
        } else if ("edit".equals(method)) {
            edit(request, response);
        } else if ("remove".equals(method)) {
            remove(request, response);
        }else if("findByPage".equals(method)){
            findByPage(request,response);
        }else if("findByPageMVC".equals(method)){
            findByPageMVC(request,response);
        }

    }

    private void findByPageMVC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = request.getParameter("currentPage");//当前页码
        String rows = request.getParameter("rows");//每页显示的行数

        //设置默认的当前页码
        if(currentPage==null||"".equals(currentPage)){
            currentPage = "1";
        }
        //设置默认显示的行数
        if(rows==null||"".equals(rows)){
            rows="5";
        }

        //获取分页对象
        GoodsService  goodsService = new GoodsServiceImpl();
        PageBean<Goods> pageBean = goodsService.findByPage(Integer.parseInt(currentPage), Integer.parseInt(rows));

        request.setAttribute("pb",pageBean);
        //转发
        request.getRequestDispatcher("/goodsListMVC.jsp").forward(request,response);

    }

    private void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = request.getParameter("currentPage");//当前页码
        String rows = request.getParameter("rows");//每页显示条数

        //设置默认的参数
        if(currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if(rows == null || "".equals(rows)){
            rows = "5";
        }

        //获取条件查询参数
        Map<String, String[]> condition = request.getParameterMap();
        int _currentPage = Integer.parseInt(currentPage);
        int _rows = Integer.parseInt(rows);

        if(_currentPage <=0) {
            _currentPage = 1;
        }
        //1.创建空的PageBean对象
        PageBean<Map<String, Object>> pb = new PageBean<Map<String, Object>>();
        //2.设置参数
        pb.setCurrentPage(_currentPage);
        pb.setRows(_rows);


        GoodsDao infoDao = new GoodsDaoImpl();

        //3.调用dao查询总记录数
        int totalCount = infoDao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //4.调用dao查询List集合
        //计算开始的记录索引
        int start = (_currentPage - 1) * _rows;
        List<Map<String, Object>> list = infoDao.findByPage(start,_rows,condition);
        pb.setList(list);

        //5.计算总页码
        int totalPage = (totalCount % _rows)  == 0 ? totalCount/_rows : (totalCount/_rows) + 1;
        pb.setTotalPage(totalPage);


        //3.将PageBean存入request
        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);//将查询条件存入request
        //4.转发到list.jsp
        request.getRequestDispatcher("/goodsList.jsp").forward(request,response);

    }

    //进行删除（包括批量删除）
    private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] choiceids = request.getParameterValues("choiceid");
        GoodsDao goodsDao = new GoodsDaoImpl();
        int result = goodsDao.delete(choiceids);
        if (result > 0) {
            findByPageMVC(request, response);
            /* response.sendRedirect("/goodsList.jsp");*/
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String g_id = request.getParameter("g_id");
        GoodsDao goodsDao = new GoodsDaoImpl();
        Goods goods = goodsDao.getGoodsId(Integer.parseInt(g_id));
        request.setAttribute("goods", goods);
        request.getRequestDispatcher("/goodsEdit.jsp").forward(request, response);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        //获取磁盘工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //根据工厂获取上传对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        //用上传对象的解析请求
        List<FileItem> list = null;
        try {
            list = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //封装对象
        Goods goods = new Goods();
        boolean isUpload = false;//是否上传
        if (list != null) {
            for (FileItem fileItem : list) {

                //判断是否是上传文件
                if (!fileItem.isFormField()) {   //fileItem.isFormField()为真就是普通的文本域
                    String fileName = upload(request, response, fileItem);
                    if (fileName != null) {
                        goods.setG_goods_pic(fileName);
                        isUpload = true;
                    }
                } else {
                    //如果是普通字段
                    String fieldName = fileItem.getFieldName();
                    String fieldNameValue = fileItem.getString("utf-8");

                    if ("g_goods_name".equals(fieldName)) {
                        goods.setG_goods_name(fieldNameValue);
                    }
                    if ("g_goods_price".equals(fieldName)) {
                        goods.setG_goods_price(Double.parseDouble(fieldNameValue));//把数字转化成字符
                    }
                    if ("g_goods_description".equals(fieldName)) {
                        goods.setG_goods_description(fieldNameValue);
                    }
                    if ("g_goods_stock".equals(fieldName)) {
                        goods.setG_goods_stock(Integer.parseInt(fieldNameValue));
                    }
                    if ("is_delete".equals(fieldName)) {
                        goods.setIs_delete(fieldNameValue);
                    }
                    //没有上传
                    if ("pic".equals(fieldName) && !isUpload) {
                        goods.setG_goods_pic(fieldNameValue);
                    }
                    if ("g_id".equals(fieldName)) {
                        goods.setG_id(Integer.parseInt(fieldNameValue));
                    }
                }
            }
        }
       /* String name = request.getParameter("name");
        String img = request.getParameter("img");
        String pri = request.getParameter("pri");
        String des = request.getParameter("des");
        String stock = request.getParameter("stock");*/

        //调用DAO层的相应更新方法
        GoodsDao goodsDao = new GoodsDaoImpl();
        int result = goodsDao.edit(goods);
        if (result > 0) {
            //跳转到列表页面
            findByPageMVC(request, response);
            /*    response.sendRedirect("/goodsList,jsp");*/
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }


    private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/goodsAdd.jsp").forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        //创建磁盘工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //根据工厂获取上传对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        //根据上传对象解析
        List<FileItem> list = null;   //获得一个集合
        try {
            list = upload.parseRequest(request);//每一个FileItem对应一个表单的输入项
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //封装对象
        Goods goods = new Goods();
        if (list != null) {
            for (FileItem fileItem : list) {
                System.out.println(fileItem);

                if (!fileItem.isFormField()) {   //判断是否是上传文件
                    String fileName = upload(request, response, fileItem);//获得上传文件的名字
                   /* String fileName = fileItem.getFieldName();*/   //这样子只能获取普通的字段
                    goods.setG_goods_pic(fileName);
                } else {
                    //普通字段的文本字段
                    String fieldName = fileItem.getFieldName();
                    String fieldNameValue = fileItem.getString("utf-8");//解决中文乱码的情况

                    if ("g_goods_name".equals(fieldName)) {
                        goods.setG_goods_name(fieldNameValue);
                    }
                    if ("g_goods_price".equals(fieldName)) {
                        goods.setG_goods_price(Integer.parseInt(fieldNameValue));
                    }
                    if ("g_goods_description".equals(fieldName)) {
                        goods.setG_goods_description(fieldNameValue);
                    }
                    if ("g_goods_stock".equals(fieldName)) {
                        goods.setG_goods_stock(Integer.parseInt(fieldNameValue));
                    }
                }
            }
        }

        /*//获取参数
        String g_goods_name = request.getParameter("g_goods_name");
        String g_goods_price = request.getParameter("g_goods_price");
        String g_goods_description = request.getParameter("g_goods_description");
        String g_goods_stock = request.getParameter("g_goods_stock");
*/
        //调用DAO层的相应的新增的方法
        GoodsDao goodsDao = new GoodsDaoImpl();
        int result = goodsDao.add(goods);
        System.out.println("受影响的result" + result);

        if (result > 0) {
            //表示保存成功，跳转到列表页面
            findByPageMVC(request,response);
           /* response.sendRedirect("/goodsList.jsp");*/
        } else {
            //重定向
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    //上传封装对象
    private String upload(HttpServletRequest request, HttpServletResponse response, FileItem fileItem) throws IOException {
        InputStream is = fileItem.getInputStream();
        //在服务器的部署目录创建相应的路径
        ServletContext servletContext = request.getServletContext();
        String uploadPath = servletContext.getRealPath("/uploadFiles");  //上传的目录
        File file = new File(uploadPath);
        //如果目录不存在，那么创建目录
        if (!file.exists()) {
            file.mkdir();  //创建目录
        }
        //解决文件重名问题
        UUID uuid = UUID.randomUUID(); //获得随机码，这样子就不会出现重名的问题
        String fileName = null;
        if (fileItem.getName() != null && !fileItem.getName().equals("")) {
            fileName = uuid.toString() + "_" + fileItem.getName();
        }


        //最终的保存路径
        String filePath = uploadPath + File.separator + fileName;

        //创建输出流
        FileOutputStream fos = new FileOutputStream(filePath);

        //文件读写操作
        IOUtils.copy(is, fos);

        //关闭资源
        fos.close();
        is.close();

        //返回文件名
        return fileName;
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GoodsDao goodsDao = new GoodsDaoImpl();
        List<Goods> list = goodsDao.getList();
        request.setAttribute("goodsList", list);

        List<Map<String, Object>> listNew = goodsDao.getListNew();
        request.setAttribute("listNew",listNew);
        request.getRequestDispatcher("/goodsList.jsp").forward(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);

    }
}
