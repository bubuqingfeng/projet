package user.dao;

import user.domain.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsDao {
    //所有商品的展示
    List<Goods> getList();

    //商品的增加
    int add(Goods goods);

    //根据ID查询一条记录
    Goods getGoodsId(int id);

    //编辑一条记录
    int edit(Goods goods);

    //删除商品
    int delete(String[] ids);

    int findTotalCount();

    List<Goods> findByPage(int start, int rows);

    List<Map<String, Object>> findByPage(int start, int rows, Map<String, String[]> condition);


    int findTotalCount(Map<String, String[]> condition);

    List<Map<String, Object>> getListNew();

}


