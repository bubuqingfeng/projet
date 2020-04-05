package user.servise.impl;

import user.dao.GoodsDao;
import user.dao.GoodsDaoImpl;
import user.domain.Goods;
import user.domain.PageBean;
import user.servise.GoodsService;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {
    private GoodsDao  Dao = new GoodsDaoImpl();

    @Override
    public PageBean<Goods> findByPage(int currentPage, int rows) {
        //1,根据当前页码和每页要显示的记录数，计算开始索引
        int start = (currentPage-1)*rows;
        //2,查询总记录数
        int totalCount = Dao.findTotalCount();
        //3，根据分页参数查询分页集合
        List<Goods> goodsList = Dao.findByPage(start, rows);

        int totalPage = totalCount%rows==0?totalCount/rows:totalCount/rows+1;

        if(currentPage<=0){
            currentPage=1;
        }
        /*if(currentPage==totalPage){
            currentPage=totalPage;
        }*/

        //5,封装PageBean
        PageBean<Goods> page = new PageBean<>();
        //设置当前页码
        page.setCurrentPage(currentPage);
        //设置每页显示的记录数
        page.setRows(rows);
        //设置总记录数
        page.setTotalCount(totalCount);
        //设置总页数
        page.setTotalPage(totalPage);
        //设置集合
        page.setList(goodsList);

        return page;
    }
}
