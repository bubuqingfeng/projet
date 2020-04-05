package user.servise;

import user.domain.Goods;
import user.domain.PageBean;

public interface GoodsService{



    /**
     * 根据当前页码和每页要显示的记录数，查询分页
     * @param currentPage
     * @param rows
     * @return
     */
    PageBean<Goods> findByPage(int currentPage, int rows);

}
